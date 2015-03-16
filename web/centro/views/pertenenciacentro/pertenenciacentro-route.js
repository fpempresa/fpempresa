app.config(['$stateProvider', function ($stateProvider) {
        $stateProvider.state('lateralmenu.pertenenciacentro', {
            url: "/pertenenciacentro",
            templateUrl: 'views/pertenenciacentro/pertenenciacentro.html',
            controller: 'PertenenciaCentroController',
            resolve: {
                metadata: ['metadataEntities', function (metadataEntities) {
                        return metadataEntities.load("Centro");
                    }]
            }
        });
    }]);