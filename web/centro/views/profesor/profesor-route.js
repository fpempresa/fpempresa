app.config(['$stateProvider', 'crudRoutesProvider', function ($stateProvider, crudRoutesProvider) {

        crudRoutesProvider.addAllRoutes({
            entity: "Usuario",
            expand: "centro",
            crudName:"profesor"
        });
        
    }]);