app.config(['$stateProvider', 'crudRoutesProvider', function ($stateProvider, crudRoutesProvider) {
        $stateProvider.state('lateralmenu.pertenenciacentro', {
            url: "/pertenenciacentro",
            templateUrl: 'views/pertenenciacentro/pertenenciacentro.html',
            controller: 'PertenenciaCentroController',
            resolve: crudRoutesProvider.getResolve("Centro","direccion.municipio.provincia") 
        });
    }]);