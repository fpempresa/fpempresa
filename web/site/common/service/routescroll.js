"use strict";

/**
 * Servicio para permitir que al navegar entre rutas
 * se pueda navegar hasta un elemento.
 * El Scroll es animado.
 */
function RouteScroll($rootScope,$location, $anchorScroll, animateScroll) {

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

    //Permite navegar a una ruta y a un Scroll dentro de ella
    //sirve pq si el usuario hace Scroll, la ruta no cambia
    //pero al volver a navegar hay que volver a moverse hasta el scroll.
    var navigateWithScroll=function (page, scroll) {  
        var url;
        if (scroll) {
            url=page+"?"+_paramName+"="+scroll;
        } else {
            url=page;
        }
        
        if ($location.url()===url) {
            if (scroll) {
                //Vamos a la misma URL pero como hay Scroll
                //puede que el usuario se haya desplazado 
                //y no estemos en el lugar al que queremos ir
                //por eso hay qye volver a hacer el Scroll
                animateScroll.toElement(scroll);
            } else {
                //si no hay scroll
                //y queremos ir a la misma URL entonces
                //seguro que no hay que hacer nada de nada
            }
        } else {
            $location.url(url);
        }
    };


    return {
        getParamName: getParamName,
        enable: enable,
        navigateWithScroll:navigateWithScroll
    }

}
RouteScroll.$inject = ['$rootScope','$location', '$anchorScroll', 'animateScroll'];

app.factory("routeScroll", RouteScroll);