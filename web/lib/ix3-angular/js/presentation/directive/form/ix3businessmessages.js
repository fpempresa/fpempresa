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

angular.module('es.logongas.ix3').directive('ix3BusinessMessages', ['ix3Configuration', function(ix3Configuration) {
        return {
            restrict: 'E',
            replace:true,
            template: '<div data-ng-show="realScope.businessMessages.length > 0">' +
                    '       <div ng-class="{\'alert-error\':bootstrap.version===2,\'alert-danger\':bootstrap.version>=3,\'c-messages\':bootstrap.version===-1}" style="text-align:left" class="alert"  >' +
                    '           <button type="button" class="close" ng-click="realScope.businessMessages=[]">&times;</button>' +
                    '           <strong ng-hide="bootstrap.version===-1">Se han producido los siguientes errores:</strong>' +
                    '           <ul >' +
                    '               <li data-ng-repeat="businessMessage in realScope.businessMessages">' +
                    '                   <strong data-ng-hide="((businessMessage.propertyName == null) || (businessMessage.propertyName == \'\')) && ((businessMessage.label == null) || (businessMessage.label == \'\'))">{{businessMessage.label || businessMessage.propertyName}}:&nbsp;&nbsp;</strong>{{businessMessage.message}}' +
                    '               </li>' +
                    '           </ul>' +
                    '       </div>' +
                    '</div>',
            scope: {
            },
            link: function($scope, element, attributes) {
                $scope.bootstrap = ix3Configuration.bootstrap;
                
                $scope.realScope=null;
                var scope=$scope;
                while (scope.$parent) {
                    scope=scope.$parent;
                    if (scope.hasOwnProperty('businessMessages')) {
                        $scope.realScope=scope;
                        break;
                    }
                }
                
                if ($scope.realScope==null) {
                    throw Error("No existe la propiedad businessMessages en el scope.");
                }
            }
        };
    }]);
