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
angular.module("es.logongas.ix3").directive('ix3Label', ['$document', 'directiveUtil', 'schemaEntities', function ($document, directiveUtil, schemaEntities) {

        return {
            restrict: 'A',
            require: "^ix3Form",
            compile: function (element, attributes) {
                return {
                    pre: function ($scope, element, attributes, ix3FormController) {
                        var schemaProperty;
                        if (attributes.ix3SchemaProperty) {
                            schemaProperty = schemaEntities.getSchemaProperty(attributes.ix3SchemaProperty);
                            if (!schemaProperty) {
                                throw Error("No existe la metainformación de :" + attributes.ix3SchemaProperty);
                            }
                        } else {
                            var forId = attributes.for;
                            var inputElement = $document[0].getElementById(forId);
                            if (!inputElement) {
                                throw new Error("No existe el elemento input al que hace referencia el for del label:" + forId);
                            }

                            var forElementIx3SchemaProperty = directiveUtil.getAttributeValueFromNormalizedName(inputElement, "ix3SchemaProperty");
                            
                            if (forElementIx3SchemaProperty) {
                                schemaProperty = schemaEntities.getSchemaProperty(forElementIx3SchemaProperty);
                                if (!schemaProperty) {
                                    throw Error("No existe la metainformación de :" + forElementIx3SchemaProperty);
                                }
                            } else {
                                var forElementNgModel = directiveUtil.getAttributeValueFromNormalizedName(inputElement, "ngModel");



                                var propertyName = forElementNgModel.replace(new RegExp("^" + ix3FormController.getConfig().modelPropertyName + "\."), "");
                                var schema = schemaEntities.getSchema(ix3FormController.getConfig().entity);

                                if (!schema) {
                                    throw Error("No existe la metainformación de la entidad :" + ix3FormController.getConfig().entity);
                                }

                                schemaProperty = schema.getSchemaProperty(propertyName);

                                if (!schemaProperty) {
                                    throw Error("No existe la metainformación de la propiedad :" + ix3FormController.getConfig().entity + "." + propertyName);
                                }
                            }
                        }

                        var value = schemaProperty.label;
                        if ((value === undefined) || (value === null) || (value.trim() === "")) {
                            //Si no está el label usamos el nombre de la propia propiedad
                            value = propertyName;
                        }
                        value = value.charAt(0).toUpperCase() + value.slice(1);

                        element.text(value);

                    },
                    post: function ($scope, element, attributes) {
                    }
                };
            }
        };
    }]);