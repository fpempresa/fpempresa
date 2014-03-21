"use strict";

angular.module("es.logongas.ix3").directive('ix3Clear', function() {
    return {
        restrict: 'A',
        link: function($scope, element, attributes) {
            function setValue(obj, key, newValue) {
                var keys = key.split('.');
                for (var i = 0; i < keys.length - 1; i++) {
                    if (!obj[keys[i]]) {
                        obj[keys[i]] = {};
                    }
                    obj = obj[keys[i]];

                }
                obj[keys[keys.length - 1]] = newValue;
            };


            var clear = attributes.ix3Clear;
            var clearValue = attributes.ix3ClearValue;
            var ngModel = attributes.ngModel;
            if (clearValue === undefined) {
                clearValue = "null";//Es un String pq luego se hace un "$eval"
            }

            if ($scope.$eval(clear) === true) {
                setValue($scope, ngModel, $scope.$eval(clearValue));
            }

            $scope.$watch(clear, function(newValue, oldValue) {
                if (newValue === true) {
                    setValue($scope, ngModel, $scope.$eval(clearValue));
                }
            });

        }
    };
});


