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

(function () {
    "use strict";

    angular.module("es.logongas.ix3").directive('ix3Form', [function () {

            return {
                restrict: 'A',
                controller:['$scope','$element','$attrs',function($scope, $element, $attrs) {
                    var config=$attrs.ix3Form || "{}";
                    
                    this.config=$scope.$eval(config);
                    
                    if (!this.config.entity) {
                        this.config.entity=$scope.entity;
                    }
                    if (!this.config.modelPropertyName) {
                        this.config.modelPropertyName="model";
                    }                    
                    
                    
                    this.getConfig=function() {
                        return this.config;
                    }
                    
                }],
                compile: function (element, attributes) {
                    return {
                        pre: function (scope, iElement, iAttrs) {

                        },
                        post: function (scope, iElement, iAttrs, controller) {
                            
                        }
                    }
                }
            };
        }]);


})();

