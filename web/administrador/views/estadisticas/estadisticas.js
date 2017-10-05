"use strict";

app.config(['$stateProvider', 'crudRoutesProvider', function ($stateProvider, crudRoutesProvider) {
        $stateProvider.state('lateralmenu.estadisticas', {
            url: "/estadisticas/centros",
            templateUrl: 'views/estadisticas/centro.html',
            controller: 'EstadisticasCentroController'
        });
    }]);



app.controller("EstadisticasCentroController", ['$scope', function ($scope) {
       console.log('Entra');
    }]);
