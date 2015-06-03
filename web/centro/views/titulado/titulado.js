"use strict";

app.controller("TituladoSearchController", ['$scope', 'genericControllerCrudList', 'controllerParams', function ($scope, genericControllerCrudList, controllerParams) {
        genericControllerCrudList.extendScope($scope, controllerParams);
        $scope.prefixRoute="/titulado";
        $scope.page.pageSize = 20;
        $scope.distinct=true;
        $scope.filters.tipoUsuario="TITULADO";
        $scope.filters['titulado.formacionesAcademicas.centro.idCentro']=$scope.user.centro.idCentro;
        
        $scope.search();
    }]);


app.controller("TituladoViewController", ['$scope', 'genericControllerCrudDetail', 'controllerParams', 'ix3Configuration', function ($scope, genericControllerCrudDetail, controllerParams, ix3Configuration) {
        genericControllerCrudDetail.extendScope($scope, controllerParams);
         $scope.ix3Configuration=ix3Configuration;
    }]);

