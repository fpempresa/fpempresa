app.config(['$stateProvider', 'crudRoutesProvider', function ($stateProvider, crudRoutesProvider) {

        $stateProvider.state('lateralmenu.titulado_search', {
            url: "/titulado/search",
            templateUrl: 'views/titulado/search.html',
            controller: 'TituladoSearchController',
            resolve: crudRoutesProvider.getResolve("Usuario","titulado.direccion.municipio.provincia") 
        });
        
        $stateProvider.state('lateralmenu.titulado_view', {
            url: "/titulado/view/:id",
            templateUrl: 'views/titulado/detail.html',
            controller: 'TituladoViewController',
            resolve: crudRoutesProvider.getResolve("Usuario","titulado.direccion.municipio.provincia,titulado.formacionesAcademicas,titulado.experienciasLaborales,titulado.titulosIdiomas","VIEW")
        }); 

    }]);