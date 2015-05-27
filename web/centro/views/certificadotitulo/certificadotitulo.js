"use strict";
app.config(['crudRoutesProvider', function(crudRoutesProvider) {
        crudRoutesProvider.addAllRoutes({
            entity:"CertificadoTitulo",
            expand:"centro,ciclo"
        });
    }]);

app.controller("CertificadoTituloSearchController", ['$scope', 'genericControllerCrudList','controllerParams', function($scope, genericControllerCrudList,controllerParams) {
        controllerParams.parentProperty='centro.idCentro';
        controllerParams.parentId=""+$scope.user.centro.idCentro;        
        genericControllerCrudList.extendScope($scope, controllerParams);    
        $scope.page.pageSize = 20;
        $scope.search();
    }]);


app.controller("CertificadoTituloNewEditController", ['$scope', 'genericControllerCrudDetail', 'controllerParams', function($scope, genericControllerCrudDetail, controllerParams) {      
        genericControllerCrudDetail.extendScope($scope, controllerParams);
        
        $scope.bulk={
            allowCheck:false,
            enabled:false
        }
        if ($scope.controllerAction==="NEW") {
            $scope.bulk.allowCheck=true;
        }

    }]);

app.controller("CertificadoTituloViewController", ['$scope', 'genericControllerCrudDetail', 'controllerParams', function($scope, genericControllerCrudDetail, controllerParams) {
        genericControllerCrudDetail.extendScope($scope, controllerParams);
        
        $scope.bulk={
            allowCheck:false,
            enabled:false
        }        
    }]);

app.controller("CertificadoTituloDeleteController", ['$scope', 'genericControllerCrudDetail', 'controllerParams', function($scope, genericControllerCrudDetail, controllerParams) {
        genericControllerCrudDetail.extendScope($scope, controllerParams);
        
        $scope.bulk={
            allowCheck:false,
            enabled:false
        }        
    }]);