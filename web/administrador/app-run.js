"use strict";


app.run(['$rootScope', 'session', '$location', 'repositoryFactory', function ($rootScope, session, $location, repositoryFactory) {
        //Guardamos la información que hemos obtenido directamente del servidor
        $rootScope.getContextPath = getContextPath;
        session.setUser(user);       
              
        
    }]);


