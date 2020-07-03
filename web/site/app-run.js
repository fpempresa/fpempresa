"use strict";


app.run(['session', 'richDomain', function (session, richDomain) {
        richDomain.extend(user);
        session.setUser(user);
    }]);


app.run(['$rootScope', function ($rootScope) {
        //Está hecho tan cumplicado para evitar que hagan spam con el correo
        var c3 = "\u0040";
        var c5 = "fp.com";
        var c2 = "te";
        var c1 = "sopor";
        var c4 = "emplea";

        $rootScope.correoSoporte = c1 + c2 + c3 + c4 + c5;
    }]);

