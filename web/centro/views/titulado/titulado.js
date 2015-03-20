"use strict";

app.controller("TituladoSearchController", ['$scope', 'genericControllerCrudList', 'controllerParams', function ($scope, genericControllerCrudList, controllerParams) {
        genericControllerCrudList.extendScope($scope, controllerParams);
        $scope.prefixRoute="/titulado";
        $scope.page.pageSize = 20;

        $scope.filters.tipoUsuario="TITULADO";
        $scope.filters['titulado.formacionesAcademicas.centro.idCentro']=$scope.user.centro.idCentro;
        
        $scope.search();
    }]);


app.controller("TituladoViewController", ['$scope', 'genericControllerCrudDetail', 'controllerParams', function ($scope, genericControllerCrudDetail, controllerParams) {
        genericControllerCrudDetail.extendScope($scope, controllerParams);
    }]);

