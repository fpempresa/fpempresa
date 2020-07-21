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

        richDomain.addEntityTransformer("Schema", ['langUtil', function (langUtil) {

                var Schema = {
                    getSchemaProperty: function (propertyName) {
                        propertyName = propertyName || "";
                        if (propertyName.indexOf(",") >= 0) {
                            throw new Error("No se permiten comas en el nombre de la propiedad");
                        }

                        var keys = langUtil.splitValues(propertyName, ".");
                        var current = this;
                        for (var i = 0; i < keys.length; i++) {
                            current = current.properties[keys[i]];

                            if (current === undefined) {
                                break;
                            }
                        }
                        if (current === undefined) {
                            return null;
                        } else {
                            return current;
                        }
                    }
                };


                return function (object, propertyPath) {
                    angular.extend(object, Schema)
                };

            }]);








    }]);