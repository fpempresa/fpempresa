"use strict";

angular.module("es.logongas.ix3").directive('ix3Options', ['repositoryFactory', 'metadataEntities', '$q', '$filter', function (repositoryFactory, metadataEntities, $q, $filter) {


        return {
            restrict: 'A',
            scope: true,
            require: ['^ix3Form', 'ngModel', '^select'],
            compile: function (element, attributes) {

                return {
                    pre: function ($scope, element, attributes, arrControllers) {
                        var ix3FormController = arrControllers[0];
                        var ngModelController = arrControllers[1];
                        var ngSelectController = arrControllers[2];

                        var filters = attributes.ix3Options;
                        var ix3OptionsDepend = attributes.ix3OptionsDepend;
                        var ix3OptionsDefault = attributes.ix3OptionsDefault;
                        var propertyName = attributes.ngModel.replace(/^model\./, "")
                        var metadata = metadataEntities.getMetadata(ix3FormController.getConfig().entity);
                        var metadataProperty = metadata.getMetadataProperty(propertyName);


                        if (angular.isArray(metadataProperty.values)) {
                            if (ix3OptionsDepend) {
                                $scope.values = [];
                            } else {
                                $scope.values = metadataProperty.values.slice();
                            }
                        } else {
                            $scope.values = [];
                        }

                        if (ix3OptionsDepend) {

                            $scope.$watch(attributes.ngModel, function (newDepend, oldDepend) {
                                if (newDepend === oldDepend) {
                                    return;
                                }

                                var currentValue = getModelValue($scope, attributes, ngModelController);
                                if ($scope.values.indexOf(currentValue) >= 0) {
                                    setModelValue($scope, attributes, ngModelController, angular.copy(currentValue));
                                }
                            })

                            $scope.$watch(ix3OptionsDepend, function (newDepend, oldDepend) {
                                if (angular.equals(newDepend, oldDepend) === true) {
                                    return;
                                }


                                var promise;
                                if (metadataProperty.shortLength === true) {
                                    //La lista de posibles valores está en los metadatos , así que no hace falta ir al servidor
                                    promise = getFilteredValuesFromMetadata(newDepend, $scope.$eval(ix3OptionsDefault), metadataProperty);
                                } else {
                                    //Los datos hay que ir a buscarlos al servidor
                                    promise = getFilteredValuesFromServer(newDepend, $scope.$eval(ix3OptionsDefault), metadataProperty);
                                }

                                promise.then(function (values) {
                                    $scope.values = values.slice();

                                    {
                                        //Al cambiar la lista de valores, debemos volver a poner siempre un nuevo valor
                                        var currentValue = getModelValue($scope, attributes, ngModelController);
                                        var valueFromArray = getValueFromArrayByPrimaryKey($scope.values, currentValue, metadataProperty.primaryKeyPropertyName);
                                        if (valueFromArray === null) {
                                            //No lo ponemos a null, pq al depender de otros valores de los que dependen se borrarían tambien.
                                            //Asi que quitamos la clave primaria y las clave naturales y así no se ve pero se mantiene todo
                                            setModelValue($scope, attributes, ngModelController, cloneObjectWithClearEntityKeys(currentValue, metadataProperty));
                                        } else {
                                            //Aqui se carga el valor del "<select>"
                                            setModelValue($scope, attributes, ngModelController, angular.copy(currentValue));
                                        }
                                    }

                                }, function (businessMessages) {
                                    //Si hay un error borramos la lista y el valor dependiente
                                    $scope.values = [];
                                    setModelValue($scope, attributes, ngModelController, null);
                                    $scope.$parent.businessMessages = businessMessages;
                                });

                            }, true);
                        }

                        var ngOptions;
                        if (metadataProperty.type === "OBJECT") {
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
                    post: function ($scope, element, attributes) {
                    }
                };
            }
        };

        function getFilteredValuesFromMetadata(depend, ix3OptionsDefault, metadataProperty) {
            var values = metadataProperty.values;

            var promise = $q.when(filterValues(values, depend, ix3OptionsDefault, metadataProperty));

            return promise;
        }


        function filterValues(values, depend, ix3OptionsDefault, metadataProperty) {
            var filterValues = [];

            for (var i = 0; i < values.length; i++) {
                var value = values[i];
                var add = true;
                for (var dependPropertyName in depend) {
                    if (!depend.hasOwnProperty(dependPropertyName)) {
                        continue;
                    }

                    var dependMetadataProperty = metadataProperty.properties[dependPropertyName];
                    var primaryKeyPropertyName = dependMetadataProperty.primaryKeyPropertyName;
                    if (depend[dependPropertyName]) {
                        var primaryKeyValue = depend[dependPropertyName][primaryKeyPropertyName];
                        if (value[dependPropertyName]) {
                            if (value[dependPropertyName][primaryKeyPropertyName] !== primaryKeyValue) {
                                add = false;
                                break;
                            }
                        } else {
                            add = false;
                            break;
                        }
                    } else {
                        //Si no hay valor , veamos que hacemos
                        if ((ix3OptionsDefault) && (typeof (ix3OptionsDefault) === "object") && (ix3OptionsDefault.hasOwnProperty(dependPropertyName))) {
                            var defaultValue = ix3OptionsDefault[dependPropertyName];
                            if ((typeof (defaultValue) === "undefined") || (defaultValue === null)) {
                                //No filtramos por esta dependencia
                                console.log("No filtramos por:" + dependPropertyName);
                                continue;
                            } else {
                                //Aqui es que nos han puesto exactamente el valor por defecto, y puede ser un solo valor o un array
                                if (angular.isArray(defaultValue)) {
                                    var algunoIgual = false;

                                    for (var j = 0; j < defaultValue.length; j++) {
                                        if (value[dependPropertyName][primaryKeyPropertyName] === defaultValue[j]) {
                                            algunoIgual = true;
                                            break;
                                        }
                                    }

                                    if (algunoIgual === false) {
                                        add = false;
                                        break;
                                    }

                                } else {
                                    if (value[dependPropertyName][primaryKeyPropertyName] !== defaultValue) {
                                        add = false;
                                        break;
                                    }
                                }
                            }
                        } else {
                            //Si no hay valor y no hay opcion por defecto seguro que no añadimos este elemento
                            add = false;
                            break;
                        }
                    }
                }
                if (add === true) {
                    filterValues.push(value);
                }

            }

            return filterValues;
        }


        function getFilteredValuesFromServer(depend, ix3OptionsDefault, metadataProperty) {
            var filter = {};
            for (var propertyName in depend) {
                if (!depend.hasOwnProperty(propertyName)) {
                    continue;
                }

                var dependMetadataProperty = metadataProperty.properties[propertyName];
                var primaryKeyPropertyName = dependMetadataProperty.primaryKeyPropertyName;
                var primaryKeyValue = depend[propertyName][primaryKeyPropertyName];
                filter[propertyName + "." + primaryKeyPropertyName] = primaryKeyValue;

            }

            var repository = repositoryFactory.getRepository(metadataProperty.className);
            var promise = repository.search(filter);

            return promise;
        }


        function getValueFromArrayByPrimaryKey(values, valueToFind, primaryKeyPropertyName) {
            if ((valueToFind) && (valueToFind[primaryKeyPropertyName])) {
                for (var i = 0; i < values.length; i++) {
                    var value = values[i];
                    if (value[primaryKeyPropertyName] === valueToFind[primaryKeyPropertyName]) {
                        return value;
                    }
                }
            }

            return null;
        }

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
        function setModelValue(scope, attributes, ngModelController, value) {
            //TODO: Usar ngModelController para establecer el valor
            var scopeAccessName = "$parent." + attributes.ngModel;
            setValue(scope, scopeAccessName, value);
        }
        function getModelValue(scope, attributes, ngModelController) {
            //TODO: Usar ngModelController para leer el valor
            var scopeAccessName = "$parent." + attributes.ngModel;
            var value = scope.$eval(scopeAccessName);

            return value;
        }


        //Clonea un objeto pero sin copiar los valores de su clave primarias y de sus claves naturales 
        function cloneObjectWithClearEntityKeys(obj, metadataProperty) {

            function isPropertyPrimaryKeyOrNaturalKey(propertyName, metadataProperty) {
                if (propertyName === metadataProperty.primaryKeyPropertyName) {
                    return true;
                }

                if (angular.isArray(metadataProperty.naturalKeyPropertiesName)) {
                    for (var i = 0; i < metadataProperty.naturalKeyPropertiesName.length; i++) {
                        if (metadataProperty.naturalKeyPropertiesName[i] === propertyName) {
                            return true;
                        }
                    }
                }

                return false;
            }

            if (obj) {
                var newValue = {};

                for (var key in obj) {
                    if (!obj.hasOwnProperty(key)) {
                        continue;
                    }
                    var value = obj[key];

                    if (isPropertyPrimaryKeyOrNaturalKey(key, metadataProperty) === false) {
                        newValue[key] = value;
                    }

                }

                delete newValue.toString;

                return newValue;

            } else {
                return obj;
            }
        }


    }]);