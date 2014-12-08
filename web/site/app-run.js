"use strict";


app.run(['$rootScope','$route', 'routeScroll', function ($rootScope, $route, routeScroll) {
    //Para que desde el HTML se pueda acceder al contextPath
    $rootScope.getContextPath = getContextPath;

}]);


app.run(['$rootScope', 'routeScroll', function ($rootScope, routeScroll) {
    //Permitirmos que se pueda poner en la ruta el parámetro "scrollTo" 
    //para que al llegar a una ruta se mueva hasta el elemento.
    routeScroll.enable();

    //Permite navegar a una ruta y a un Scroll dentro de ella
    $rootScope.navigateWithScroll = function (page, scroll) {  
        routeScroll.navigateWithScroll(page, scroll);
    };


}]);

