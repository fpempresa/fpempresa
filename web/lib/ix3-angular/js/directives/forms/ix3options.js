"use strict";

angular.module("es.logongas.ix3").directive('ix3Options', ['repositoryFactory', 'metadataEntities', function(repositoryFactory, metadataEntities) {
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

        return {
            restrict: 'A',
            scope: true,
            require:"^ix3Form",
            compile: function(element, attributes) {

                return {
                    pre: function($scope, element, attributes,ix3FormController) {
                        var filters = attributes.ix3Options;

                        var propertyName = attributes.ngModel.replace(/^model\./, "");
                        var metadata=metadataEntities.getMetadata(ix3FormController.getConfig().entity);
                        var metadataProperty=metadata.getMetadataProperty(propertyName);
                        var scopeAccessName= "$parent." + attributes.ngModel;

                        

                        if (angular.isArray(metadataProperty.values)) {
                            $scope.values = metadataProperty.values;
                        } else {
                            $scope.values = [];
                        }

                        var ngOptions;
                        if (metadataProperty.type === "OBJECT") {

                            if ((metadataProperty.dependProperty) && (metadataProperty.dependProperty.length > 0)) {
                                var repository = repositoryFactory.getRepository(metadataProperty.className);
                                var parts = propertyName.split(".");
                                parts[parts.length - 1] = metadataProperty.dependProperty;
                                var dependProperty = {
                                    name: parts.join("."),
                                    metadata: metadata.getMetadataProperty(parts.join(".")),
                                    scopeAccessName: "model." + parts.join(".")
                                };
                                var filterProperty = {
                                    name: metadataProperty.dependProperty, //OJO:El nombre es relativo a la propiedad principal y no desde el principio
                                    metadata: metadata.getMetadataProperty(propertyName + "." + metadataProperty.dependProperty)
                                };
                                $scope.$watch(dependProperty.scopeAccessName + "." + dependProperty.metadata.primaryKeyPropertyName, function(newValue, oldValue) {
                                    if (newValue === oldValue) {
                                        return;
                                    }

                                    if (newValue) {
                                        var filter = {};
                                        filter[filterProperty.name + "." + filterProperty.metadata.primaryKeyPropertyName] = newValue;
                                        repository.search(filter).then(function(values) {
                                            $scope.values = values;


                                            var item = null;
                                            var currentObject = $scope.$eval(scopeAccessName);
                                            if ((currentObject) && (currentObject[metadataProperty.primaryKeyPropertyName])) {
                                                var primaryKey = currentObject[metadataProperty.primaryKeyPropertyName];

                                                for (var i = 0; i < values.length; i++) {
                                                    var value = values[i];
                                                    if (value[metadataProperty.primaryKeyPropertyName] === primaryKey) {
                                                        item = value;
                                                        break;
                                                    }
                                                }

                                                if (item === null) {
                                                    setValue($scope, scopeAccessName, null);
                                                } else {
                                                    setValue($scope, scopeAccessName, item);
                                                }
                                            }
                                        }, function(businessMessages) {
                                            //Si hay un error borramos la lista y el valor dependiente
                                            $scope.values = [];
                                            setValue($scope, scopeAccessName, null);
                                            $scope.$parent.businessMessages = businessMessages;
                                        });
                                    } else {
                                        //Si no hay valor padre, borramos la lista y el valor dependiente
                                        $scope.values = [];
                                        setValue($scope, scopeAccessName, null);
                                    }
                                });
                            }


                            if ((filters) && (filters.trim() !== "")) {
                                ngOptions = "value.toString() for value in values | " + filters + " track by value." + metadataProperty.primaryKeyPropertyName + "";
                            } else {
                                ngOptions = "value.toString() for value in values track by value." + metadataProperty.primaryKeyPropertyName + "";
                            }

                        } else {
                            ngOptions = "value.key as value.description for value in values ";
                        }

                        attributes.ngOptions = ngOptions;

                    },
                    post: function($scope, element, attributes) {
                    }
                };
            }
        };
    }]);