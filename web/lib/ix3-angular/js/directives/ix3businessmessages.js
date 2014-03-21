"use strict";

angular.module('es.logongas.ix3').directive('ix3BusinessMessages', ['bootstrap', function(bootstrap) {
        return {
            restrict: 'E',
            replace:true,
            template: '<div data-ng-show="$parent.businessMessages.length > 0">' +
                    '       <br />' +
                    '       <div ng-class="{\'alert-error\':bootstrap.version===2,\'alert-danger\':bootstrap.version>=3}" class="alert"  >' +
                    '           <button type="button" class="close" ng-click="$parent.businessMessages=[]">&times;</button>' +
                    '           <strong>Se han producido los siguientes errores:</strong>' +
                    '           <ul >' +
                    '               <li data-ng-repeat="businessMessage in $parent.businessMessages">' +
                    '                   <strong data-ng-hide="(businessMessage.propertyName == null) && (businessMessage.label == null)">{{businessMessage.label || businessMessage.propertyName}}:&nbsp;&nbsp;</strong>{{businessMessage.message}}' +
                    '               </li>' +
                    '           </ul>' +
                    '       </div>' +
                    '</div>',
            scope: {
            },
            link: function($scope, element, attributes) {
                $scope.bootstrap = bootstrap;
            }
        };
    }]);
