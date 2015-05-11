app.config(['$stateProvider', 'crudRoutesProvider', function ($stateProvider, crudRoutesProvider) {
        $stateProvider.state('lateralmenu.pertenenciaempresa', {
            url: "/pertenenciaempresa",
            templateUrl: 'views/pertenenciaempresa/pertenenciaempresa.html',
            controller: 'PertenenciaEmpresaController',
            resolve: crudRoutesProvider.getResolve("Empresa","direccion.municipio.provincia") 
        });
    }]);