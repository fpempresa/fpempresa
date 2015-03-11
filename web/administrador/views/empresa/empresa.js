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
        
        $scope.orderby = [
            {
                fieldName: 'nombreComercial', 
                orderDirection: 'ASC'
            }
        ];
        
        $scope.filters=[
            {
                nombreComercial:"",
                $operator:"LLIKER"
            },
            {
                cif:""
            }
        ];
        
        $scope.search();
    }]);


app.controller("EmpresaNewEditController", ['$scope', 'genericControllerCrudDetail', 'controllerParams','repositoryFactory', function($scope, genericControllerCrudDetail, controllerParams,repositoryFactory) {
        genericControllerCrudDetail.extendScope($scope, controllerParams);
    }]);

app.controller("EmpresaViewController", ['$scope', 'genericControllerCrudDetail', 'controllerParams','repositoryFactory', function($scope, genericControllerCrudDetail, controllerParams,repositoryFactory) {
        genericControllerCrudDetail.extendScope($scope, controllerParams);
    }]);

app.controller("EmpresaDeleteController", ['$scope', 'genericControllerCrudDetail', 'controllerParams','repositoryFactory', function($scope, genericControllerCrudDetail, controllerParams,repositoryFactory) {
        genericControllerCrudDetail.extendScope($scope, controllerParams);
    }]);