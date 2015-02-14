"use strict";


app.run(['$rootScope', 'session', '$location', 'repositoryFactory', function ($rootScope, session, $location, repositoryFactory) {
        //Guardamos la información que hemos obtenido directamente del servidor
        $rootScope.getContextPath = getContextPath;
        session.setUser(user);


        //Obligamos a ir a la página de los datos del titulado si  
        //aun no ha puesto los datos del titulado
        $rootScope.$on("$stateChangeSuccess", function (event, toState, toParams, fromState, fromParams) {
            if (!$rootScope.user.titulado) {
                $location.url("/titulado/new");
            }
        });

    }]);


