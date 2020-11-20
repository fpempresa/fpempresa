/*
 * ix3 Copyright 2014-2020 Lorenzo.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

"use strict";

angular.module("es.logongas.ix3").directive('ix3Options', ['serviceFactory', 'schemaEntities', '$q', 'langUtil', '$parse', function (serviceFactory, schemaEntities, $q, langUtil, $parse) {


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
                        var description = attributes.ix3Description;
                        var ix3OptionsDepend = attributes.ix3OptionsDepend;
                        var ix3OptionsDefault = attributes.ix3OptionsDefault;
                        
                        var schemaProperty;
                        if (attributes.ix3SchemaProperty) {
                            schemaProperty = schemaEntities.getSchemaProperty(attributes.ix3SchemaProperty);
                            if (!schemaProperty) {
                                throw Error("No existe la metainformación de :" + attributes.ix3SchemaProperty);
                            }
                        } else {
                            var propertyName = attributes.ngModel.replace(new RegExp("^" + ix3FormController.getConfig().modelPropertyName + "\."), "");
                            var schema = schemaEntities.getSchema(ix3FormController.getConfig().entity);
                            
                            if (!schema) {
                                throw Error("No existe la metainformación de la entidad :" + ix3FormController.getConfig().entity);
                            }
                            
                            schemaProperty = schema.getSchemaProperty(propertyName);
                            
                            if (!schemaProperty) {
                                throw Error("No existe la metainformación de la propiedad :" + ix3FormController.getConfig().entity + "." + propertyName);
                            }
                        }




                        if (angular.isArray(schemaProperty.values)) {
                            if (ix3OptionsDepend) {
                                $scope.values = [];
                            } else {
                                //El slice es para hacer un "clone" del array de forma rápida
                                $scope.values = schemaProperty.values.slice();
                            }
                        } else {
                            $scope.values = [];
                        }
                        
                        if (ix3OptionsDepend) {
                            $scope.$watch(attributes.ngModel, function (newValue, oldValue) {
                                if (newValue === oldValue) {
                                    return;
                                }
                                if ((isChangeIx3OptionsDepend(oldValue,$scope, attributes,schemaProperty)===true) && (typeof(newValue)==='object') && (newValue!==null)) {
                                    //Si ha cambiaso el depende no hace falta que hagamos nada pq luego lo hace el Watch del depend
                                    //PERO debe haber un valor pq si no hay valor si que se tiene que ejecutar esta función
                                    return;
                                }                               
                                
                                var currentValue=getModelValue($scope, attributes);
                                var nullValue;
                                var oldValueClearEntityKeys = cloneObjectWithClearEntityKeys(oldValue, schemaProperty);
                                if (angular.equals(oldValueClearEntityKeys, oldValue) === false) {
                                    nullValue=oldValueClearEntityKeys;
                                } else {
                                    nullValue=undefined;
                                }
                                setModelValueCheckingIsInValues($scope,currentValue,nullValue,attributes,schemaProperty);
                                
                            }, true);

                            $scope.$watch(ix3OptionsDepend, function (newDepend, oldDepend) {
                                if (angular.equals(newDepend, oldDepend) === true) {
                                    return;
                                }
                                var currentValue = getModelValue($scope, attributes);
                                var nullValue = cloneObjectWithClearEntityKeys(getModelValue($scope, attributes), schemaProperty);
                                var dependValue=newDepend;
                                
                                var promise=getFilteredValues($scope,dependValue, ix3OptionsDefault, schemaProperty);

                                promise.then(function (values) {
                                    $scope.values = values.slice();

                                    setModelValueCheckingIsInValues($scope,currentValue,nullValue,attributes,schemaProperty);

                                }, function (businessMessages) {
                                    //Si hay un error borramos la lista y el valor dependiente
                                    $scope.values = [];
                                    setModelValue($scope, attributes, null);
                                    $scope.$parent.businessMessages = businessMessages;
                                });

                            }, true);
                        }

                        var ngOptions;
                        if (schemaProperty.type === "OBJECT") {
                            var realFilters="";
                            if ((filters) && (filters.trim() !== "")) {
                                realFilters="| " + filters;
                            }
                            var realDescription="value.toString()";
                            if ((description) && (description.trim() !== "")) {
                                realDescription=description;
                            }                            
                                
                            ngOptions = realDescription + " for value in values " + realFilters + " track by value." + schemaProperty.primaryKeyPropertyName + "";

                        } else {
                            if ((filters) && (filters.trim() !== "")) {
                                ngOptions = "value.key as value.description for value in values | " + filters;
                            } else {
                                ngOptions = "value.key as value.description for value in values ";
                            }


                        }

                        attributes.ngOptions = ngOptions;

                    },
                    post: function ($scope, element, attributes) {
                    }
                };
            }
        };

        /*****************************************/
        /*** BEGIN: Cargar la lista de valores ***/
        /*****************************************/


            /**
             * Indica si ha cambiado el Ix3OptionsDepend
             * @param {type} otherValue El otro valor del <select> con el que se compara el valor actual
             * @param {type} scope
             * @param {type} attributes
             * @param {type} schemaProperty
             * @returns {Boolean}
             */
            function isChangeIx3OptionsDepend(otherValue,scope,attributes, schemaProperty) {
                var currentEvalIx3OptionsDepend=getEvalCurrentIx3OptionsDepend(scope,attributes);
                var otherEvalIx3OptionsDepend=getEvalOtherIx3OptionsDepend(otherValue,scope,attributes);
                
                
                if (currentEvalIx3OptionsDepend===otherEvalIx3OptionsDepend) {
                    return false;
                }

                if (typeof(otherEvalIx3OptionsDepend)!=='object')  {
                    return false;
                }
                
                if (otherEvalIx3OptionsDepend===null)  {
                    return false;
                }                

                for(var key in currentEvalIx3OptionsDepend){
                    var primaryKeyPropertyName=schemaProperty.getSchemaProperty(key).primaryKeyPropertyName;
                    var fullPrimaryKeyPropertyName=key+"."+primaryKeyPropertyName;

                    var existsKeyNew=langUtil.hasOwnProperty(currentEvalIx3OptionsDepend,fullPrimaryKeyPropertyName);
                    var existsKeyOld=langUtil.hasOwnProperty(otherEvalIx3OptionsDepend,fullPrimaryKeyPropertyName);

                    if ((existsKeyNew===false) && (existsKeyOld===false)) {
                        //Por ahora son iguales.
                    } else if ((existsKeyNew===false) && (existsKeyOld===true)) {
                        //Si que hay un cambio
                        return true;
                    } else if ((existsKeyNew===true) && (existsKeyOld===false)) {
                        //Si que hay un cambio
                        return true;
                    } else if ((existsKeyNew===true) && (existsKeyOld===true)) {
                        //Estan las 2 claves, veamos si sus valores son iguales.
                        var currentValue=langUtil.getValue( currentEvalIx3OptionsDepend,fullPrimaryKeyPropertyName);
                        var otherValue=langUtil.getValue( otherEvalIx3OptionsDepend,fullPrimaryKeyPropertyName);
                        if (currentValue!==otherValue) {
                            return true;
                        }
                    } else {
                        throw Error("Error de lógica: existsKeyNew=" + existsKeyNew + " , existsKeyOld=" + existsKeyOld);
                    }
                }        

                return false;
            }

            function getEvalCurrentIx3OptionsDepend(scope,attributes) {
                var ix3OptionsDepend = attributes.ix3OptionsDepend;
                var currentEvalIx3OptionsDepend=scope.$eval(ix3OptionsDepend);
                
                return currentEvalIx3OptionsDepend;
            }
            function getEvalOtherIx3OptionsDepend(otherValue,scope,attributes) {
                var ix3OptionsDepend = attributes.ix3OptionsDepend;
                var locals=langUtil.setValue({},attributes.ngModel,otherValue);
                var evalOtherIx3OptionsDepend=scope.$eval(ix3OptionsDepend,locals);
                    
                return evalOtherIx3OptionsDepend;
            }

            /**
             * Este método retorna la lista de valores del "<select>" pero lo hace de los metadatos o sdel servidor. Según lo que necesite.
             * @public
             * @param {type} $scope 
             * @param {type} depend El objeto del que dependen
             * @param {type} ix3OptionsDefault LAs opciones de cuando no hay datos en el objeto del que dependen
             * @param {type} schemaProperty
             * @returns {Promise} Una promesa con los datos
             */
            function getFilteredValues($scope,depend, ix3OptionsDefault, schemaProperty) {
                var promise;

                if (angular.isArray(schemaProperty.values)) {
                    //La lista de posibles valores está en los metadatos , así que no hace falta ir al servidor
                    promise = getFilteredValuesFromSchema(depend, $scope.$eval(ix3OptionsDefault), schemaProperty);
                } else {
                    //Los datos hay que ir a buscarlos al servidor
                    promise = getFilteredValuesFromServer(depend, $scope.$eval(ix3OptionsDefault), schemaProperty);
                }

                return promise;
             }
            /**
             * Este método retorna la lista de valores del "<select>" pero SOLO si están en los metadatos.
             * @private
             * @param {type} depend El objeto del que dependen
             * @param {type} ix3OptionsDefault LAs opciones de cuando no hay datos en el objeto del que dependen
             * @param {type} schemaProperty 
             * @returns {Promise} Una promesa con los datos
             */
            function getFilteredValuesFromSchema(depend, ix3OptionsDefault, schemaProperty) {
                var filterValues;

                if (isImpossibleFilter(depend, ix3OptionsDefault, schemaProperty)) {
                    filterValues = [];
                } else {
                    filterValues = [];
                    var values = schemaProperty.values;
                    for (var i = 0; i < values.length; i++) {
                        var value = values[i];
                        if (isValueInFilterList(value, depend, ix3OptionsDefault, schemaProperty)) {
                            filterValues.push(value);
                        }
                    }
                }
         
                var promise = $q.when(filterValues);

                return promise;
            }

            /**
             * Retorna si un valor debe o no estar en la lista de valores del "<select>"
             * @param {type} value
             * @param {type} depend
             * @param {type} ix3OptionsDefault
             * @param {type} schemaProperty
             * @returns {Boolean}
             */
            function isValueInFilterList(value, depend, ix3OptionsDefault, schemaProperty) {
                var add = true;

                for (var dependPropertyName in depend) {
                    if (!depend.hasOwnProperty(dependPropertyName)) {
                        continue;
                    }

                    var dependSchemaProperty = schemaProperty.getSchemaProperty(dependPropertyName);
                    var primaryKeyPropertyName = dependSchemaProperty.primaryKeyPropertyName;
                    if (depend[dependPropertyName]) {
                        var primaryKeyValue = depend[dependPropertyName][primaryKeyPropertyName];
                        if (langUtil.hasOwnProperty(value,dependPropertyName)===true) {
                            if (langUtil.getValue(value,dependPropertyName)) {
                                if (langUtil.getValue(value,dependPropertyName)[primaryKeyPropertyName] !== primaryKeyValue) {
                                    add = false;
                                    break;
                                }
                            } else {
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
                                //No filtramos por esta dependencia pq vale null o undefined
                                continue;
                            } else {
                                //Aqui es que nos han puesto exactamente el valor por defecto, y puede ser un solo valor o un array
                                if (angular.isArray(defaultValue)) {
                                    var algunoIgual = false;

                                    for (var i = 0; i < defaultValue.length; i++) {
                                        if (langUtil.getValue(value,dependPropertyName)[primaryKeyPropertyName] === defaultValue[i]) {
                                            algunoIgual = true;
                                            break;
                                        }
                                    }

                                    if (algunoIgual === false) {
                                        add = false;
                                        break;
                                    }

                                } else {
                                    if (langUtil.getValue(value,dependPropertyName)[primaryKeyPropertyName] !== defaultValue) {
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

                return add;
            }

            /**
             * Este método retorna la lista de valores del "<select>" pero los busca en el servidor
             * @param {type} depend El objeto del que dependen
             * @param {type} ix3OptionsDefault LAs opciones de cuando no hay datos en el objeto del que dependen
             * @param {type} schemaProperty 
             * @returns {Promise} Una promesa con los datos
             */
            function getFilteredValuesFromServer(depend, ix3OptionsDefault, schemaProperty) {
                if (isImpossibleFilter(depend, ix3OptionsDefault, schemaProperty)) {
                    var promise = $q.when([]);
                    return promise;
                } else {
                    var filters = {};
                    var expand = "";
                    for (var dependPropertyName in depend) {                   
                        if (!depend.hasOwnProperty(dependPropertyName)) {
                            continue;
                        }

                        var primaryKeyPropertyName = schemaProperty.getSchemaProperty(dependPropertyName).primaryKeyPropertyName;

                        if ((depend[dependPropertyName]) && (depend[dependPropertyName][primaryKeyPropertyName])) {
                            var primaryKeyValue = depend[dependPropertyName][primaryKeyPropertyName];
                            filters[dependPropertyName + "." + primaryKeyPropertyName] = primaryKeyValue;
                        } else {
                            if ((ix3OptionsDefault) && (typeof (ix3OptionsDefault) === "object") && (ix3OptionsDefault.hasOwnProperty(dependPropertyName))) {
                                var defaultValue = ix3OptionsDefault[dependPropertyName];
                                if ((typeof (defaultValue) === "undefined") || (defaultValue === null)) {
                                    //No filtramos por esta dependencia
                                    continue;
                                } else {
                                    //Aqui es que nos han puesto exactamente el valor por defecto
                                    filters[dependPropertyName + "." + primaryKeyPropertyName] = defaultValue;
                                }
                            } else {
                                //Hemos encontrado un filtro que hace que ya no 
                            }
                        }

                        if (expand === "") {
                            expand = dependPropertyName;
                        } else {
                            expand = expand + "," + dependPropertyName;
                        }
                    }

                    var service = serviceFactory.getService(schemaProperty.className);
                    var query = {
                        filters:filters,
                        expand:expand
                    };
                    var promise = service.search(query);

                    return promise;
                }
            }


            /**
             * Si El filtro es imposible de cumplir por ningun elemento retorna "true"
             * Esto se da si los valroes de los que depende son null o si no hay valores por defecto
             * Y así nos ahorramos una llamada al servidor
             * @param {type} depend
             * @param {type} ix3OptionsDefault
             * @param {type} schemaProperty
             * @returns {Boolean}
             */
            function isImpossibleFilter(depend, ix3OptionsDefault, schemaProperty) {
                var impossibleFilter = false;

                for (var dependPropertyName in depend) {
                    if (!depend.hasOwnProperty(dependPropertyName)) {
                        continue;
                    }

                    var primaryKeyPropertyName = schemaProperty.getSchemaProperty(dependPropertyName).primaryKeyPropertyName;

                    if ((depend[dependPropertyName]) && (depend[dependPropertyName][primaryKeyPropertyName])) {
                        continue;
                    } else {
                        if ((ix3OptionsDefault) && (typeof (ix3OptionsDefault) === "object") && (ix3OptionsDefault.hasOwnProperty(dependPropertyName))) {
                            continue;
                        } else {
                            //Hemos encontrado un filtro que no se puede cumplir
                            impossibleFilter = true;
                            break;
                        }
                    }
                }

                return impossibleFilter;
            }
        
        /***************************************/        
        /*** END: Cargar la lista de valores ***/ 
        /***************************************/



        /***********************************/        
        /*** BEGIN: set y get del modelo ***/ 
        /***********************************/

            function setModelValueCheckingIsInValues(scope,currentValue,nullValue,attributes,schemaProperty) {
                var valueFromArray = getValueFromArrayByPrimaryKey(scope.values, currentValue, schemaProperty.primaryKeyPropertyName);
                if (valueFromArray!==null) {
                    setModelValue(scope, attributes, angular.copy(currentValue));
                } else {
                    //Hay valores en el Array de valores pero el dato no está en ese array

                    if ((typeof(nullValue)==='object') && (nullValue!==null)) {
                        setModelValue(scope, attributes, angular.copy(nullValue));
                    }
                }
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


            function setModelValue(scope, attributes, value) {
                var scopeAccessName = "$parent." + attributes.ngModel;
                var setter=$parse(scopeAccessName).assign;
                setter(scope, value);
            }
            function getModelValue(scope, attributes) {
                var scopeAccessName = "$parent." + attributes.ngModel;
                var getter=$parse(scopeAccessName);
                var value = getter(scope);  

                return value;
            }
        
        /*********************************/        
        /*** END: set y get del modelo ***/ 
        /*********************************/

        //Clonea un objeto pero sin copiar los valores de su clave primarias y de sus claves naturales 
        function cloneObjectWithClearEntityKeys(obj, schemaProperty) {

            function isPropertyPrimaryKeyOrNaturalKey(propertyName, schemaProperty) {
                if (propertyName === schemaProperty.primaryKeyPropertyName) {
                    return true;
                }

                if (angular.isArray(schemaProperty.naturalKeyPropertiesName)) {
                    for (var i = 0; i < schemaProperty.naturalKeyPropertiesName.length; i++) {
                        if (schemaProperty.naturalKeyPropertiesName[i] === propertyName) {
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

                    if (isPropertyPrimaryKeyOrNaturalKey(key, schemaProperty) === false) {
                        newValue[key] = value;
                    }

                }

                return newValue;

            } else {
                return obj;
            }
        }
        

    }]);