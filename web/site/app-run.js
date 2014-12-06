"use strict";


app.run(['$rootScope','routeScroll', function ($rootScope,routeScroll) {
    //Permitirmos que se pueda poner en la ruta el parámetro "scrollTo" 
    //para que al llegar a una ruta se mueva hasta el elemento.
    routeScroll.enable("scrollTo");
    
    
    //Para que desde el HTML se pueda acceder al contextPath
    $rootScope.getContextPath = getContextPath;
    
}]);

