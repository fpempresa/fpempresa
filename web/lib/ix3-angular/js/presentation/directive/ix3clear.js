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

angular.module("es.logongas.ix3").directive('ix3Clear', ['langUtil',function(langUtil) {
    return {
        restrict: 'A',
        link: function($scope, element, attributes) {



            var clear = attributes.ix3Clear;
            var clearValue = attributes.ix3ClearValue;
            var ngModel = attributes.ngModel;
            if (clearValue === undefined) {
                clearValue = "null";//Es un String pq luego se hace un "$eval"
            }

            if ($scope.$eval(clear) === true) {
                langUtil.setValue($scope, ngModel, $scope.$eval(clearValue));
            }

            $scope.$watch(clear, function(newValue, oldValue) {
                if (newValue === true) {
                    langUtil.setValue($scope, ngModel, $scope.$eval(clearValue));
                }
            });

        }
    };
}]);


