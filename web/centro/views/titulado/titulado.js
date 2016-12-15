"use strict";

app.controller("TituladoSearchController", ['$scope','$http', 'genericControllerCrudList', 'controllerParams','ix3Configuration', function ($scope, $http, genericControllerCrudList, controllerParams,ix3Configuration) {
        genericControllerCrudList.extendScope($scope, controllerParams);
        $scope.page.pageSize = 20;
        $scope.distinct = true;
        $scope.filters.tipoUsuario = "TITULADO";
        $scope.filters['titulado.formacionesAcademicas.centro.idCentro'] = $scope.user.centro.idCentro;
        $scope.search();
        $scope.apiUrl=ix3Configuration.server.api;
        $scope.failImportCsv=function(){
            alert("Fallo al importar el archivo");
        };
        $scope.updateList=function(){
            alert("Archivo subido con éxito, TODO: Update list");
        }
    }]);


app.controller("TituladoViewController", ['$scope', 'genericControllerCrudDetail', 'controllerParams', 'ix3Configuration', 'ageCalculator', function ($scope, genericControllerCrudDetail, controllerParams, ix3Configuration, ageCalculator) {
        genericControllerCrudDetail.extendScope($scope, controllerParams);
        $scope.ix3Configuration = ix3Configuration;
        $scope.ageCalculator = ageCalculator;
    }]);

