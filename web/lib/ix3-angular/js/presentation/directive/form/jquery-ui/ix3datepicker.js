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

angular.module("es.logongas.ix3.datepicker.jquery").directive('ix3Datepicker', ['dateFormat', function (dateFormat) {
        return {
            restrict: 'A',
            require:'ngModel',
            link: function ($scope, element, attributes, controller) {
                var format;
                if (!attributes.ix3Date) {
                    format = dateFormat.getJQueryDatepickerFormatFromAngularJSFormat(dateFormat.getAngularFormatFromPredefined(dateFormat.getDefaultDateFormat()));
                } else {
                    format = dateFormat.getJQueryDatepickerFormatFromAngularJSFormat(dateFormat.getAngularFormatFromPredefined(attributes.ix3Date));
                }

                element.datepicker({
                    dateFormat: format,
                    onSelect: function () {
                        $scope.$evalAsync(function () {
                            controller.$setViewValue($(element).val());
                            element.blur();
                        });
                    }
                });

                var nextElement = element.next();
                if ((nextElement) && (nextElement.length > 0)) {
                    nextElement.css({
                        cursor: "pointer"
                    })
                    nextElement.on("click", function () {
                        element.datepicker("show");
                    });
                }

            }
        };
    }]);
