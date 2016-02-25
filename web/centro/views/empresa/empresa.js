"use strict";
app.config(['crudRoutesProvider', function(crudRoutesProvider) {
        crudRoutesProvider.addAllRoutes({
            entity:"Empresa",
            expand:"direccion.municipio,direccion.municipio.provincia"
        });
    }]);

app.controller("EmpresaSearchController", ['$scope', 'genericControllerCrudList','controllerParams', function($scope, genericControllerCrudList,controllerParams) {
        genericControllerCrudList.extendScope($scope, controllerParams);      
        $scope.page.pageSize=20;
        
        $scope.filters['centro.idCentro']=$scope.user.centro.idCentro;
        
        $scope.search();
    }]);


app.controller("EmpresaNewEditController", ['$scope', 'genericControllerCrudDetail', 'controllerParams', function($scope, genericControllerCrudDetail, controllerParams) {
        genericControllerCrudDetail.extendScope($scope, controllerParams);
        
        $scope.postCreate = function () {
            $scope.model.centro = $scope.user.centro;
        };
        
    }]);

app.controller("EmpresaViewController", ['$scope', 'genericControllerCrudDetail', 'controllerParams', function($scope, genericControllerCrudDetail, controllerParams) {
        genericControllerCrudDetail.extendScope($scope, controllerParams);
    }]);

app.controller("EmpresaDeleteController", ['$scope', 'genericControllerCrudDetail', 'controllerParams', function($scope, genericControllerCrudDetail, controllerParams) {
        genericControllerCrudDetail.extendScope($scope, controllerParams);
    }]);