"use strict";

/**
 * Servicio para permitir que al navegar entre rutas
 * se pueda navegar hasta un elemento.
 * El Scroll es animado.
 */
function RouteScroll($rootScope, $anchorScroll,animateScroll) {

    var _paramName = "$scrollTo";

    var getParamName = function () {
        return _paramName;
    }

    var enable = function (paramName) {
        _paramName = paramName || _paramName;

        $rootScope.$on('$routeChangeSuccess', function (event, currentRoute, previousRoute) {
            var scrollTo = currentRoute.params[_paramName];

            //Si cambiamos de página hacemos nos ponemos en la parte superior de la página pero si vamos a la misma no se hace
            if (currentRoute && previousRoute) {
                if (currentRoute.originalPath !== previousRoute.originalPath) {
                    $anchorScroll.yOffset = 0;
                    $anchorScroll();
                }
            }

            //Permitimos movernos a un elemento con "id" usando el parámetro "scrollTo".
            if (scrollTo) {
                animateScroll.toElement(scrollTo);
            }

        });
    }


    return {
        getParamName: getParamName,
        enable: enable
    }

}
RouteScroll.$inject = ['$rootScope', '$anchorScroll','animateScroll'];

app.factory("routeScroll", RouteScroll);