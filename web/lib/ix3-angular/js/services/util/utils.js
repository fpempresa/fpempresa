"use strict";

/**
 * 
 */
angular.module("es.logongas.ix3").service("utils", [function() {
        var PREFIX_REGEXP = /^(x[\:\-_]|data[\:\-_])/i;
        var SPECIAL_CHARS_REGEXP = /([\:\-\_]+(.))/g;
        var MOZ_HACK_REGEXP = /^moz([A-Z])/;
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
        function directiveNormalize(name) {
            return camelCase(name.replace(PREFIX_REGEXP, ''));
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
            return name.
                    replace(SPECIAL_CHARS_REGEXP, function(_, separator, letter, offset) {
                        return offset ? letter.toUpperCase() : letter;
                    }).
                    replace(MOZ_HACK_REGEXP, 'Moz$1');
        }


        return {
            getAttributeValueFronNormalizedName: function(element, atributeName) {
                for (var i = 0; i < element.attributes.length; i++) {
                    var attribute = element.attributes.item(i);
                    var normalizedAttribute = directiveNormalize(attribute.nodeName);
                    if (normalizedAttribute === atributeName) {
                        return attribute.nodeValue;
                    }
                }

                return undefined;
            }
        };
    }]);




