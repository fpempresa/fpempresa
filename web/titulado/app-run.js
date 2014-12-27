"use strict";


app.run(['$rootScope', 'session', function ($rootScope, session) {
        //Obtenemos la información que hemos obtenido directamente del servidor
        $rootScope.getContextPath = getContextPath;
        session.setUser(user);
        
        if (!$rootScope.user) {
            alert("No has iniciado sesión o tu sesión ha caducado");
            window.location.href = getContextPath() + "/site/index.html#/login";
        }

    }]);


