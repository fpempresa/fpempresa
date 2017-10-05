"use strict";

app.config(['$stateProvider', 'crudRoutesProvider', function ($stateProvider, crudRoutesProvider) {
        $stateProvider.state('lateralmenu.estadisticas', {
            url: "/estadisticas/centros",
            templateUrl: 'views/estadisticas/centro.html',
            controller: 'EstadisticasCentroController'
        });
    }]);



app.controller("EstadisticasCentroController", ['$scope','$http','ix3Configuration', function ($scope, $http, ix3Configuration) {
        $scope.businessMessages = [];
        
        $http({
            method: "GET",
            url: ix3Configuration.server.api + "/Estadisticas/administrador"
        }).then(function (chartData) {
            $scope.chartData = chartData.data;
            console.log($scope.chartData);


        }, function (businessMessages) {
            $scope.businessMessages = businessMessages;
        });

    }]);
