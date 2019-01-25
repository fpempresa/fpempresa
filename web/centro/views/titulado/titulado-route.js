app.config(['$stateProvider', 'crudRoutesProvider', function ($stateProvider, crudRoutesProvider) {

        $stateProvider.state('lateralmenu.titulado_search_', {
            url: "/titulado/search",
            templateUrl: 'views/titulado/search.html',
            controller: 'TituladoSearchController'
        });

    }]);