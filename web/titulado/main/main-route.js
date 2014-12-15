app.config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/', {
            templateUrl: 'main/main.html',
            controller: 'MainController',
            resolve: {
                metadata: ['metadataEntities', function (metadataEntities) {
                        return metadataEntities.load("Titulado", "titulosIdiomas,experienciasLaborales,formacionesAcademicas.centro,usuario");
                    }]
            }
        });
    }]);