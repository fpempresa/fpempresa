app.config(['$stateProvider', 'crudRoutesProvider', function ($stateProvider, crudRoutesProvider) {
        
    $stateProvider.state("lateralmenu.ultimoacceso", {
        url: '/usuario/ultimoacceso',
        templateUrl: 'views/usuario/ultimoacceso.html',
        controller: 'UsuarioUltimoAccesoSearchController',
        resolve: crudRoutesProvider.getResolve("Usuario")
    });

               
        
    }]);


app.controller("UsuarioUltimoAccesoSearchController", ['$scope', 'genericControllerCrudList', 'controllerParams', 'schemaEntities', function ($scope, genericControllerCrudList, controllerParams, schemaEntities) {
        genericControllerCrudList.extendScope($scope, controllerParams);
        $scope.page.pageSize = 20;
        $scope.filters['$isnull']={};
        $scope.filters['$isnull'].fechaUltimoAcceso=false;
        
        var anyoPasado = new Date();
        anyoPasado.setFullYear( anyoPasado.getFullYear() - 1 );
        $scope.filters['$dle']={};
        $scope.filters['$dle'].fechaUltimoAcceso=anyoPasado;
        
        $scope.orderby = [
            {fieldName: "fechaUltimoAcceso", orderDirection: "ASC"},
            {fieldName: "fecha", orderDirection: "ASC"}
        ];  
        
        $scope.search();
    }]);