"use strict";


app.run(['session', 'richDomain', function (session, richDomain) {
        richDomain.extend(user);
        session.setUser(user);
    }]);


app.run(['$rootScope', '$location', function ($rootScope, $location) {
        //Obligamos a ir a la página de los datos del titulado si  
        //aun no ha puesto los datos del titulado
        $rootScope.$on("$stateChangeSuccess", function (event, toState, toParams, fromState, fromParams) {
            if (!$rootScope.user.titulado) {
                $location.url("/curriculum/titulado/new");
            }
        });

    }]);


