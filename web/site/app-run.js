"use strict";


app.run(['$rootScope', 'session','richDomain', function ($rootScope, session,richDomain) {
        //Guardamos la información que hemos obtenido directamente del servidor
        $rootScope.getContextPath = getContextPath;
        richDomain.extend(user);
        session.setUser(user);
}]);


app.run(['$rootScope', 'routeScroll', function ($rootScope, routeScroll) {
    //Permitirmos que se pueda poner en la ruta el parámetro "scrollTo" 
    //para que al llegar a una ruta se mueva hasta el elemento.
    routeScroll.enable();

    //Permite navegar a una ruta y a un Scroll dentro de ella
    //Y usarlo directamente desde el HTML
    $rootScope.navigateWithScroll = function (page, scroll) {  
        routeScroll.navigateWithScroll(page, scroll);
    };


}]);

