"use strict";

app.controller("TituladoSearchController", ['$scope', '$http', 'ix3Configuration','dialog', function ($scope, $http, ix3Configuration,dialog) {
        $scope.businessMessages = [];
        
        $scope.fail = {};
        $scope.apiUrl = ix3Configuration.server.api;
        $scope.mostrarCodigosMunicipio = function () {
            dialog.create('mostrarCodigosMunicipio');
        };
        $scope.failImportJson = function (data) {
            if (data && data.jqXHR && data.jqXHR.responseText)
                $scope.businessMessages = JSON.parse(data.jqXHR.responseText);
        };
        $scope.updateList = function () {
            alert("El listado de Titulados se importó correctamente");
            $scope.search();
        };
        if ($scope.user && $scope.user.centro) {
            $scope.centro=$scope.user.centro;
            getEstadisticasByCentro($scope,$scope.user.centro);
        }
        
        
        
        function getEstadisticasByCentro($scope,centro) {
            $http({
                method: "GET",
                url: ix3Configuration.server.api + "/Estadisticas/centro/" + centro.idCentro + "?expand=tituladosPorFamilia.tituladosPorCiclo"
            }).then(function (estadisticas) {
                $scope.centro.estadisticas = estadisticas.data;
            });
        }        
        
    }]);
