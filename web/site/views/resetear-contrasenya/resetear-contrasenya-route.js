app.config(['$stateProvider', function ($stateProvider) {
        $stateProvider.state('olvido-contrasenya-formulario', {
            url: '/resetear-contrasenya/:token',
            templateUrl: 'views/resetear-contrasenya/resetear-contrasenya.html',
            controller: 'ResetearContrasenyaController'
        });
    }]);