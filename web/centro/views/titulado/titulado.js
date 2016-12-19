"use strict";

app.controller("TituladoSearchController", ['$scope', '$http', 'genericControllerCrudList', 'controllerParams', 'ix3Configuration', 'dialog', function ($scope, $http, genericControllerCrudList, controllerParams, ix3Configuration, dialog) {
        genericControllerCrudList.extendScope($scope, controllerParams);
        $scope.page.pageSize = 20;
        $scope.distinct = true;
        $scope.filters.tipoUsuario = "TITULADO";
        $scope.filters['titulado.formacionesAcademicas.centro.idCentro'] = $scope.user.centro.idCentro;
        $scope.search();
        $scope.apiUrl = ix3Configuration.server.api;
        $scope.mostrarCodigosMunicipio = function () {
            dialog.create('mostrarCodigosMunicipio');
        };
        $scope.failImportCsv = function (data) {
            throw new Error("Ocurrió un error al subir el CSV ");
        };
        $scope.updateList = function () {
            console.info("TODO: Actualizar lista titulados cuando importas CSV");
        };
    }]);


app.controller("TituladoViewController", ['$scope', 'genericControllerCrudDetail', 'controllerParams', 'ix3Configuration', 'ageCalculator', function ($scope, genericControllerCrudDetail, controllerParams, ix3Configuration, ageCalculator) {
        genericControllerCrudDetail.extendScope($scope, controllerParams);
        $scope.ix3Configuration = ix3Configuration;
        $scope.ageCalculator = ageCalculator;
    }]);

