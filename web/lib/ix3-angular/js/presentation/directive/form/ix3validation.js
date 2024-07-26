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

angular.module("es.logongas.ix3").directive('ix3Validation', ['$compile','schemaEntities', function($compile, schemaEntities) {

        return {
            restrict: 'A',
            terminal: true,
            require:"^ix3Form",
            priority: 10000,
            compile: function(element, attributes) {
                return {
                    pre: function($scope, element, attributes) {

                    },
                    post: function($scope, element, attributes, ix3FormController) {
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

                        //Expresion regular
                        var pattern=schemaProperty.pattern;
                        if (pattern!==null) {
                            element.attr("ng-pattern",new RegExp("^("+pattern+")$") );
                            element.attr("ix3-pattern",pattern);//Se pone este porque sino al interpolar el mensaje sale raro la RegExp al no se un String
                            var patternMessage=schemaProperty.patternMessage;
                            if (patternMessage) {
                                element.attr("ix3-pattern-message",patternMessage);
                            }
                        }
                        
                        //Valor requirido
                        var required=schemaProperty.required;
                        if (required===true) {
                            element.attr("ng-required",required);
                        }
                        
                        //Tamaño máximo
                        var maxLength=schemaProperty.maxLength;
                        if (maxLength!==null) {
                            element.attr("ng-maxlength",maxLength);
                        }
                        
                        //Tamaño mínimo
                        var minLength=schemaProperty.minLength;
                        if (minLength!==null) {
                            element.attr("ng-minlength",minLength);
                        }                           
                        
                        
                        //Valor máximo
                        var maximum=schemaProperty.maximum;
                        if (maximum!==null) {
                            element.attr("max",maximum);
                        }
                        
                        //Valor mínimo
                        var minimum=schemaProperty.minimum;
                        if (minimum!==null) {
                            element.attr("min",minimum);
                        }                        
                        
                        var format=schemaProperty.format;
                        if (format==="URL") {
                            element.attr("type","url");
                        } else if (format==="EMAIL") {
                            element.attr("type","email");
                        }                          
                        
                        element.removeAttr("ix3-validation"); //Se quita para evitar el bucle infinito de compilaciones
                        element.removeAttr("data-ix3-validation"); //Se quita para evitar el bucle infinito de compilaciones
                        element.removeAttr("x-ix3-validation"); //Se quita para evitar el bucle infinito de compilaciones
                        $compile(element)($scope);
                    }
                }
            }
        };
    }]);

