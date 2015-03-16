"use strict";


app.run(['$rootScope', 'session', 'richDomain','$location', function ($rootScope, session, richDomain, $location) {
        //Guardamos la información que hemos obtenido directamente del servidor
        $rootScope.getContextPath = getContextPath;
        richDomain.extend(user);
        session.setUser(user);       
              
        //Obligamos a ir a la página de los datos del titulado si  
        //aun no ha puesto los datos del titulado
        $rootScope.$on("$stateChangeSuccess", function (event, toState, toParams, fromState, fromParams) {
            if (!$rootScope.user.centro) {
                $location.url("/pertenenciacentro");
            }
        });
    }]);


