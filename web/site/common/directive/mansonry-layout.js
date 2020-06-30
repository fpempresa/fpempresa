"use strict";

angular.module("common").directive('mansonryLayout', [function() {
        
        function mansonry(element) {
            setTimeout(function(){ 
                new Masonry(element, {
                        itemSelector: '.l-masonry__area',
                        columnWidth: 350,
                        isFitWidth: true
                    }); 
            }, 500);
            
        }
        
        return {
            restrict: 'A',
            link: function($scope, element, attributes) {
                if (attributes.mansonryLayout) {
                    $scope.$watch(attributes.mansonryLayout, function (newValue, oldValue) {
                        if (newValue === oldValue) {
                            return;
                        }
                        mansonry(element[0]);
                    });
                } else {
                    mansonry(element[0]);
                }
            }
        };
    }]);