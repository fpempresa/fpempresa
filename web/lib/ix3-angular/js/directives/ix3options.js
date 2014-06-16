"use strict";

angular.module("es.logongas.ix3").directive('ix3Options', ['remoteServiceFactory', function(remoteServiceFactory) {
        function setValue(obj, key, newValue) {
            var keys = key.split('.');
            for (var i = 0; i < keys.length - 1; i++) {
                if (!obj[keys[i]]) {
                    obj[keys[i]] = {};
                }
                obj = obj[keys[i]];

            }
            obj[keys[keys.length - 1]] = newValue;
        }


        function getMetadataPropertyName(key) {
            var keys = key.split('.');
            var name = "";
            for (var i = 0; i < keys.length; i++) {
                if (name !== "") {
                    name = name + ".";
                }
                name = name + "properties['" + keys[i] + "']";
            }
            return name;
        }

        return {
            restrict: 'A',
            scope: true,
            compile: function(element, attributes) {
                attributes.ngOptions = ""; //Ponemos esta aqui para que luego la compile.
                return {
                    pre: function($scope, element, attributes) {
                        var filters = attributes.ix3Options;
                        
                        var modelProperty={
                            name:attributes.ngModel.replace("model.", ""),
                            metadata: $scope.$eval("metadata[entity]." + getMetadataPropertyName(attributes.ngModel.replace("model.", ""))),
                            scopeAccessName:attributes.ngModel
                        }

                        if (angular.isArray(modelProperty.metadata.values)) {
                            $scope.values = modelProperty.metadata.values;
                        } else {
                            $scope.values = [];
                        }

                        var ngOptions;
                        if (modelProperty.metadata.type === "OBJECT") {

                            if ((modelProperty.metadata.dependProperty) && (modelProperty.metadata.dependProperty.length>0)) {
                                var remoteService = remoteServiceFactory.getRemoteService(modelProperty.metadata.className);
                                    var parts=modelProperty.name.split(".");
                                    parts[parts.length-1]=modelProperty.metadata.dependProperty;
                                    var dependProperty={
                                        name:parts.join("."),
                                        metadata:$scope.$eval("metadata[entity]." + getMetadataPropertyName(parts.join("."))),
                                        scopeAccessName:"model."+parts.join(".")
                                    }
                                    var filterProperty={
                                        name:modelProperty.metadata.dependProperty, //OJO:El nombre es relativo a la propiedad principal y no desde el principio
                                        metadata:$scope.$eval("metadata[entity]." + getMetadataPropertyName(modelProperty.name+"."+modelProperty.metadata.dependProperty)),
                                    };
                                    $scope.$watch(dependProperty.scopeAccessName+"."+dependProperty.metadata.primaryKeyPropertyName, function(newValue, oldValue) {
                                        if (newValue === oldValue) {
                                            return;
                                        }

                                        if (newValue) {
                                            var filter = {};
                                            filter[filterProperty.name+"."+filterProperty.metadata.primaryKeyPropertyName]=newValue;
                                            remoteService.search(filter).then(function(values) {
                                                $scope.values = values;
                                                
                                                
                                                var exist=false;
                                                var primaryKey=$scope.$eval( modelProperty.scopeAccessName)[modelProperty.metadata.primaryKeyPropertyName];
                                                
                                                for(var i=0;i<values.length;i++) {
                                                    var value=values[i];
                                                    if (value[modelProperty.metadata.primaryKeyPropertyName]===primaryKey) {
                                                        exist=true;
                                                        break;
                                                    }
                                                }
                                                
                                                if (exist===false) {
                                                    setValue($scope.$parent, modelProperty.scopeAccessName, null);
                                                }
                                            }, function(businessMessages) {
                                                //Si hay un error borramos la lista y el valor dependiente
                                                $scope.values = [];
                                                setValue($scope.$parent, modelProperty.scopeAccessName, null);
                                                $scope.$parent.businessMessages = businessMessages;
                                            });
                                        } else {
                                            //Si no hay valor padre, borramos la lista y el valor dependiente
                                            $scope.values = [];
                                            setValue($scope.$parent, modelProperty.scopeAccessName, null);
                                        }
                                    });
                            }

                           
                            if ((filters) && (filters.trim()!=="")) {
                                ngOptions="value as value.toString for value in values | " + filters + " track by value." + modelProperty.metadata.primaryKeyPropertyName + "";
                            } else {
                                ngOptions ="value as value.toString for value in values track by value." + modelProperty.metadata.primaryKeyPropertyName + "";
                            }

                        } else {
                            ngOptions = "value.key as value.description for value in values ";
                        }

                        attributes.ngOptions = ngOptions;

                    },
                    post: function($scope, element, attributes) {
                    }
                }
            }
        }
    }]);