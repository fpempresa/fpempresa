app.config(['$stateProvider', 'crudRoutesProvider', function ($stateProvider, crudRoutesProvider) {

        $stateProvider.state('lateralmenu.profesor_search_', {
            url: "/profesor/search",
            templateUrl: 'views/profesor/search.html',
            controller: 'ProfesorSearchController',
            resolve: crudRoutesProvider.getResolve("Usuario","centro") 
        });
        
        $stateProvider.state('lateralmenu.profesor_edit_', {
            url: "/profesor/edit/:id",
            templateUrl: 'views/profesor/detail.html',
            controller: 'ProfesorNewEditController',
            resolve: crudRoutesProvider.getResolve("Usuario","centro","EDIT")
        }); 

    }]);