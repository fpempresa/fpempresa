"use strict";


app.run(['$rootScope', function ($rootScope) {
        //Para que desde el HTML se pueda acceder al contextPath
        $rootScope.getContextPath = getContextPath;

        if (user) {
            $rootScope.user = user;
        } else {
            alert("No has iniciado sesión o tu sesión ha caducado");
            window.location.href = getContextPath() + "/site/index.html";            
        }

    }]);


