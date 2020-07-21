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

/**
 * 
 */
angular.module("es.logongas.ix3").factory("langUtil", [function () {

        return {
            getFunctionName: getFunctionName,
            splitValues:splitValues,
            setValue:setValue,
            getValue:getValue,
            hasOwnProperty:hasOwnProperty
        };

        /**
         * Obtiene el nombre de una función. Si la función es anonima reotrna ""
         * @param {Function} fn La función de la que se obtiene el nobmre
         * @returns {String} El nombre de la función
         */
        function getFunctionName(fn) {
            return fn.toString().substr(0, fn.toString().indexOf("(")).replace("function ", "");
        }
        
        
        /**
         * Hace un split pero si solo hay un elemento en el array y es "", retorna un array vacio.
         * Si no hay nada retorna un array sin elementos.
         * @param {String} values Es String con los distintos elementos
         * @param {String} separator El separador
         * @returns {Array[String]}
         */
        function splitValues(values, separator) {
            values = values || "";
            var arrValues = values.split(separator);
            if ((arrValues.length === 1) && (arrValues[0] === "")) {
                return [];
            } else {
                return arrValues;
            }
        }        
        
        /**
         * Asigna un valor a un objeto pero el valor de la propiedad puede tener subpropiedades anidadas.
         * @param {type} obj El objeto al que se le pone el valor
         * @param {type} key El nombre de la propiedad. Se prmiten cosas como "prop1.prop2.prop3"
         * @param {type} newValue El nuevo valor
         */
        function setValue(obj, key, newValue) {
            var keys = key.split('.');
            var newObj=obj;
            for (var i = 0; i < keys.length - 1; i++) {
                if (!newObj[keys[i]]) {
                    newObj[keys[i]] = {};
                }
                newObj = newObj[keys[i]];

            }
            newObj[keys[keys.length - 1]] = newValue;
            
            return obj;
        }
        
        /**
         * Es como la función de JS de hasOwnProperty pero funciona con nombres de propiedades anidadas.
         * Ej. Funciona con "a.b.c". Comprobaría que existe la propieda "a" que es un objeto , dentro la propiedad "b" y dentro de "b" la propiedad "c"
         * @param {Object} obj
         * @param {String} propertyNames
         * @returns {boolean}
         */
        function getValue(obj,propertyNames) {
            
            if (typeof(propertyNames)!=='string') {
                throw Error("El parámetro 'propertyNames' debe ser un String pero es del tipo:" + typeof(propertyNames));
            }
            
            var keys = propertyNames.split('.');
            var newObj=obj;
            for (var i = 0; i < keys.length; i++) {
                var key=keys[i];
                
                if (typeof(newObj)!=='object') {
                    throw Error("No es un objeto la propiedad " + key + " de '" + propertyNames + "' en el objeto " + JSON.stringify(obj) );
                }
                if (newObj===null) {
                    throw Error("El objeto es null en la propiedad " + key + " de '" + propertyNames + "' en el objeto " + JSON.stringify(obj) );
                }                
                
                if (Object.prototype.hasOwnProperty.call(newObj, key)==false) {
                    throw Error("No existe la propiedad " + key + " de '" + propertyNames + "' en el objeto " + JSON.stringify(obj) );
                }
                
                
                newObj = newObj[key];

            }
            
            return newObj;
        }
        
        /**
         * Es como la función de JS de hasOwnProperty pero funciona con nombres de propiedades anidadas.
         * Ej. Funciona con "a.b.c". Comprobaría que existe la propieda "a" que es un objeto , dentro la propiedad "b" y dentro de "b" la propiedad "c"
         * @param {Object} obj
         * @param {String} propertyNames
         * @returns {boolean}
         */
        function hasOwnProperty(obj,propertyNames) {
            
            if (typeof(propertyNames)!=='string') {
                throw Error("El parámetro 'propertyNames' debe ser un String pero es del tipo:" + typeof(propertyNames));
            }
            
            var keys = propertyNames.split('.');
            var newObj=obj;
            for (var i = 0; i < keys.length; i++) {
                var key=keys[i];
                
                if (typeof(newObj)!=='object') {
                    return false;
                }
                if (newObj===null) {
                    return false;
                }                
                
                if (Object.prototype.hasOwnProperty.call(newObj, key)==false) {
                    return false;
                }
                
                
                newObj = newObj[key];

            }
            
            return true;
        }
        
        
    }]);


