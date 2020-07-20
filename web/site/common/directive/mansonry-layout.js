/**
 *   FPempresa
 *   Copyright (C) 2020  Lorenzo González
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Affero General Public License as
 *   published by the Free Software Foundation, either version 3 of the
 *   License, or (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Affero General Public License for more details.
 *
 *   You should have received a copy of the GNU Affero General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
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