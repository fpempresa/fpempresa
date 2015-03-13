"use strict";


app.run(['$rootScope', 'session', 'richDomain', function ($rootScope, session, richDomain) {
        //Guardamos la información que hemos obtenido directamente del servidor
        $rootScope.getContextPath = getContextPath;
        richDomain.extend(user);
        session.setUser(user);       
              
        
    }]);


