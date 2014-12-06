"use strict";


app.run(['$rootScope', '$timeout', '$anchorScroll', function ($rootScope,  $timeout, $anchorScroll) {

        $rootScope.$on('$routeChangeSuccess', function (event, currentRoute, previousRoute) {


            //Si cambiamos de página hacemos nos ponemos en la parte superior de la página pero si vamos a la misma no se hace
            if (currentRoute && previousRoute) {
                if (currentRoute.originalPath !== previousRoute.originalPath) {
                    $anchorScroll.yOffset = 0;
                    $anchorScroll();
                }
            }

            //Permitimos movernos a un elemento con "id" usando el parámetro "scrollTo".
            if (currentRoute.params.scrollTo) {
                var offset = $("#" + currentRoute.params.scrollTo).offset();

                if (offset) {
                    $('html, body').stop().animate({
                        scrollTop: offset.top
                    }, 1500, 'easeInOutExpo');
                } else {
                    $timeout(function () {
                        offset = $("#" + currentRoute.params.scrollTo).offset();
                        $('html, body').stop().animate({
                            scrollTop: offset.top
                        }, 1500, 'easeInOutExpo');
                    });
                }
            }

        });


    }]);