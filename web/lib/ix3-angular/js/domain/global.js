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

angular.module('es.logongas.ix3').config(['richDomainProvider', function (richDomain) {



        richDomain.addGlobalTransformer(function () {
            return function (object, propertyPath) {
                //Para transformar los Strign en fechas
                for (var key in object) {
                    if (!object.hasOwnProperty(key)) {
                        continue;
                    }
                    var value = object[key];
                    if ((typeof value === "string") && (value.length === 24) && (value.charAt(10) === "T") && (value.charAt(23) === "Z")) {
                        var date = moment(value, "YYYY-MM-DDTHH:mm:ss.SSSZ", true);
                        if (date.isValid()) {
                            object[key] = date.toDate();
                        }
                    }
                }

            };

        });


        richDomain.addGlobalTransformer(function () {
            function toStringGlobal() {
                return this["$toString"];
            }

            return function (object, propertyPath) {
                //Añadir el método toString() para que use la propiedad "$toString", pero solo si existe
                if (object.hasOwnProperty('$toString')) {
                    //Definimos nuestra propia función "toString"
                    object['toString'] = toStringGlobal;
                }
            };
        });

    }]);