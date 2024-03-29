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
angular.module("es.logongas.ix3").factory("directiveUtil", [function () {
        
        return {
            normalizeName:normalizeName,
            getAttributeValueFromNormalizedName:getAttributeValueFromNormalizedName
        };
        
        /**
         * copied from angular.js v1.3.0-beta.13
         * (c) 2010-2012 Google, Inc. http://angularjs.org
         * License: MIT
         * modified by lorenzo gonzalez
         * Converts all accepted directives format into proper directive name.
         * All of these will become 'myDirective':
         *   my:Directive
         *   my-directive
         *   x-my-directive
         *   data-my:directive
         *
         * Also there is special case for Moz prefix starting with upper case letter.
         * @param name Name to normalize
         */
        function normalizeName(name) {
            var PREFIX_REGEXP = /^((?:x|data)[\:\-_])/i;
            var snakeCaseName=name.replace(PREFIX_REGEXP, '');
            return camelCase(snakeCaseName);
        }

        /**
         * copied from angular.js v1.3.0-beta.13
         * (c) 2010-2012 Google, Inc. http://angularjs.org
         * License: MIT
         * modified by lorenzo gonzalez
         * Converts snake_case to camelCase.
         * Also there is special case for Moz prefix starting with upper case letter.
         * @param name Name to normalize
         */
        function camelCase(name) {
            var SPECIAL_CHARS_REGEXP = /([\:\-\_]+(.))/g;
            var MOZ_HACK_REGEXP = /^moz([A-Z])/;   
            
            return name.replace(SPECIAL_CHARS_REGEXP, function (_, separator, letter, offset) { return offset ? letter.toUpperCase() : letter;}).replace(MOZ_HACK_REGEXP, 'Moz$1');
        }

        function getAttributeValueFromNormalizedName(element, atributeName) {
            for (var i = 0; i < element.attributes.length; i++) {
                var attribute = element.attributes.item(i);
                var normalizedAttribute = normalizeName(attribute.nodeName);
                if (normalizedAttribute === atributeName) {
                    return attribute.value;
                }
            }

            return undefined;
        }


    }]);




