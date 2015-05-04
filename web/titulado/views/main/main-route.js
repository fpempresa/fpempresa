app.config(['$stateProvider','crudRoutesProvider', function ($stateProvider, crudRoutesProvider) {        
        $stateProvider.state('lateralmenu.main', {
            url: "/",
            templateUrl: 'views/main/main.html',
            controller: 'MainController',
            resolve: crudRoutesProvider.getResolve("Titulado","titulosIdiomas,experienciasLaborales,formacionesAcademicas.centro,usuario,direccion.municipio.provincia","VIEW")
        });         
    }]);