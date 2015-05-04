(function () {
    "use strict";

    angular.module("es.logongas.ix3").directive('ix3Form', [function () {

            return {
                restrict: 'A',
                controller:['$scope','$element','$attrs',function($scope, $element, $attrs) {
                    var config=$attrs.ix3Form || "{entity:entity}";
                    
                    this.config=$scope.$eval(config);
                    
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

