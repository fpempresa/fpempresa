"use strict";


app.run(['$rootScope', 'session', function ($rootScope, session) {
        //Guardamos la información que hemos obtenido directamente del servidor
        $rootScope.getContextPath = getContextPath;
        session.setUser(user);
    }]);


