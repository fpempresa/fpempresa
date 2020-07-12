app.config(['$stateProvider', function ($stateProvider) {
        $stateProvider.state('enviar-mail-resetear-contrasenya', {
            url: '/enviar-mail-resetear-contrasenya',
            templateUrl: 'views/resetear-contrasenya/enviar-mail-resetear-contrasenya.html',
            controller: 'EnviarMailResetearContrasenyaController'
        });
        
        $stateProvider.state('olvido-contrasenya-formulario', {
            url: '/resetear-contrasenya/:token',
            templateUrl: 'views/resetear-contrasenya/resetear-contrasenya.html',
            controller: 'ResetearContrasenyaController'
        });        
    }]);