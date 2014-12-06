"use strict";


app.run(['$rootScope','$route', 'routeScroll', function ($rootScope, $route, routeScroll) {
    //Para que desde el HTML se pueda acceder al contextPath
    $rootScope.getContextPath = getContextPath;

}]);


app.run(['$rootScope','$location', 'routeScroll','animateScroll', function ($rootScope, $location, routeScroll, animateScroll) {
    //Permitirmos que se pueda poner en la ruta el parámetro "scrollTo" 
    //para que al llegar a una ruta se mueva hasta el elemento.
    routeScroll.enable();

    //Permite navegar a una ruta y a un Scroll dentro de ella
    //sirve pq si el usuario hace Scroll, la ruta no cambia
    //pero al volver a navegar hay que volver a moverse hasta el scroll.
    $rootScope.navigateWithScroll = function (page, scroll) {  
        var url;
        if (scroll) {
            url=page+"?"+routeScroll.getParamName()+"="+scroll;
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


}]);

