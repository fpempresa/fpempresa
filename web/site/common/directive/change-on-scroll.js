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

angular.module("common").directive('changeOnScroll', [function () {

        function countUp(element) {

            var value = parseInt(element.innerHTML.replace(/,/g, '').replace(/\./g, ''));
            
            if (value<=0) {
                return;
            }

            var initialValue;
            if (value > 1000) {
                initialValue = value - 1000;
            } else {
                initialValue = value;
            }

            var options = {
                duration: 1,
                useEasing: true,
                useGrouping: true,
                separator: '.',
                startVal: initialValue
            };
            new CountUp(element, value, options).start();

        }

        function changeClass(element) {

            var className = element.getAttribute("data-scroll-class");
            element.classList.add(className);

        }



        return {
            restrict: 'A',
            link: function ($scope, element, attributes) {
                var elementScroll = element[0];

                var offset;
                if (elementScroll.getAttribute("data-scroll-offset")) {
                    offset = elementScroll.getAttribute("data-scroll-offset");
                } else {
                    offset = "10%";
                }

                var handlerFactory = function (elementScroll) {
                    return  function (direction) {

                        var elementsClass = elementScroll.querySelectorAll('[data-scroll-class]');
                        for (var i = 0; i < elementsClass.length; i++) {
                            var elementClass = elementsClass[i];
                            changeClass(elementClass);
                        }


                        var elementsUpCount = elementScroll.querySelectorAll('[data-scroll-up-count]');
                        for (var i = 0; i < elementsUpCount.length; i++) {
                            var elementUpCount = elementsUpCount[i];
                            countUp(elementUpCount);
                        }

                        this.destroy();
                    };
                };

                new Waypoint({
                    element: elementScroll,
                    handler: handlerFactory(elementScroll),
                    offset: offset
                });

            }
        };
    }]);