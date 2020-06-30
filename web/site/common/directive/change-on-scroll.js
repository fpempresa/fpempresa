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