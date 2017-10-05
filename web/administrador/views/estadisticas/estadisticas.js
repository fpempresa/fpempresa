"use strict";

app.config(['crudRoutesProvider', function (crudRoutesProvider) {
        crudRoutesProvider.addAllRoutes({
            entity: "Estadisticas",
            expand: "tituladosPorFamilia,ofertasPorFamilia,candidatosPorFamilia"
        });
    }]);


app.controller("EstadisticasCentroController", ['$scope', function ($scope) {
       console.log('Entra');
    }]);
