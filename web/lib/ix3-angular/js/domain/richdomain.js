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
(function (undefined) {
    "use strict";

    /**
     * Enriquece objetos de dominio con nuevos métodos y transformaciones
     */
    RichDomain.$inject = ['transformers'];
    function RichDomain(transformers) {

        this.extend = function (object) {
            transform(object, transformers);
        };


        function transform(object, transformers, propertyPath) {
            //OJO:Comporbar primeri si es un array pq un array tambien es un objeto
            if (angular.isArray(object)) {
                for (var i = 0; i < object.length; i++) {
                    propertyPath = propertyPath || object[i]['$className'];
                    transform(object[i], transformers, propertyPath);
                }

            } else if ((typeof (object) === "object") && (object !== null)) {
                propertyPath = propertyPath || object['$className'];

                for (var key in object) {
                    if (!object.hasOwnProperty(key)) {
                        continue;
                    }
                    var value = object[key];
                    if (typeof (value) === "object") {
                        transform(value, transformers, propertyPath + "." + key);
                    }
                }
                applyTransforms(object, transformers, propertyPath);
            }

        }

        function applyTransforms(object, transformers, propertyPath) {
            var className = object['$className'];

            //aplicamos los transformadores globales
            for (var i = 0; i < transformers.global.length; i++) {
                transformers.global[i](object, propertyPath);
            }

            if (className) {
                var entityTransformers = transformers.entity[className];
                if (entityTransformers) {
                    for (var i = 0; i < transformers.entity[className].length; i++) {
                        transformers.entity[className][i](object, propertyPath);
                    }
                }
            }
        }


    }


    RichDomainProvider.$inject = [];
    function RichDomainProvider() {

        var transformers = {
            entity: {},
            global: []
        };

        this.addEntityTransformer = function (entityName, transformer) {
            if (!transformers.entity[entityName]) {
                transformers.entity[entityName] = [];
            }

            transformers.entity[entityName].push(transformer);
        };

        this.addGlobalTransformer = function (transformer) {
            transformers.global.push(transformer);
        };

        this.$get = ['$injector', function ($injector) {

                //Ahora hacemos la llamada para generar las funciones reales , estas primeras llamadas solo se hacen una vez
                var realTransformers = {
                    entity: {},
                    global: []
                };

                for (var i = 0; i < transformers.global.length; i++) {
                    var realFn = $injector.invoke(transformers.global[i], undefined);

                    realTransformers.global.push(realFn);
                }

                for (var entityName in transformers.entity) {

                    realTransformers.entity[entityName] = [];

                    for (var i = 0; i < transformers.entity[entityName].length; i++) {
                        var realFn = $injector.invoke(transformers.entity[entityName][i], undefined);

                        realTransformers.entity[entityName].push(realFn);
                    }

                }

                var locals = {
                    transformers: realTransformers
                };
                return $injector.instantiate(RichDomain, locals);
            }];

    }

    angular.module("es.logongas.ix3").provider("richDomain", RichDomainProvider);

})();


