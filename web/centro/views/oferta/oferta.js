"use strict";
app.config(['crudRoutesProvider', function(crudRoutesProvider) {
        crudRoutesProvider.addAllRoutes({
            entity:"Oferta",
            expand:"direccion.municipio,direccion.municipio.provincia"
        });
    }]);

app.controller("OfertaSearchController", ['$scope', 'genericControllerCrudList','controllerParams', function($scope, genericControllerCrudList,controllerParams) {
        genericControllerCrudList.extendScope($scope, controllerParams);      
        $scope.page.pageSize=20;
        
        $scope.filters.$eq['empresa.centro.idCentro']=$scope.user.centro.idCentro;
        
        $scope.search();
    }]);


app.controller("OfertaNewEditController", ['$scope', 'genericControllerCrudDetail', 'controllerParams', function($scope, genericControllerCrudDetail, controllerParams) {
        genericControllerCrudDetail.extendScope($scope, controllerParams);
    }]);

app.controller("OfertaViewController", ['$scope', 'genericControllerCrudDetail', 'controllerParams', function($scope, genericControllerCrudDetail, controllerParams) {
        genericControllerCrudDetail.extendScope($scope, controllerParams);
    }]);

app.controller("OfertaDeleteController", ['$scope', 'genericControllerCrudDetail', 'controllerParams', function($scope, genericControllerCrudDetail, controllerParams) {
        genericControllerCrudDetail.extendScope($scope, controllerParams);
    }]);