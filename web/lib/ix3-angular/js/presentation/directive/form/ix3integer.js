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

angular.module("es.logongas.ix3").directive('ix3Integer', [function() {
        
        
        return {
            restrict: 'A',
            require: 'ngModel',
            link: function($scope, element, attributes, ngModelController) {

                var regExpInteger = new RegExp("^[-+]?\\d*$");
                var undefined;

                ngModelController.$formatters.push(function(value) {
                    if (value === null) {
                        ngModelController.$setValidity('integer', true);
                        return "";
                    } else if (typeof (value) === "undefined") {
                        ngModelController.$setValidity('integer', true);
                        return "";
                    } else if (regExpInteger.test(value+"")===true) {
                        ngModelController.$setValidity('integer', true);
                        return value;
                    } else {
                        ngModelController.$setValidity('integer', false);
                        return "";
                    }
                });
                ngModelController.$parsers.unshift(function(value) {
                    if (value) {
                        if (regExpInteger.test(value+"")===true) {
                            ngModelController.$setValidity('integer', true);
                            return parseInt(value+"");
                        } else {
                            ngModelController.$setValidity('integer', false);
                            return undefined;
                        }
                    }
                });
            }
        };
    }]);
angular.module("es.logongas.ix3").config(['formValidatorProvider', function(formValidatorProvider) {
        //Incluir el mensaje de la nueva directiva de validacion
        formValidatorProvider.addErrorMensajePattern("integer", "El dato debe ser un número");
    }]);