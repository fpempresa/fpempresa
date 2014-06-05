app.config(['$routeProvider', function($routeProvider) {
        $routeProvider.when('/docs/faq', {
            templateUrl: 'docs/faq.html'
        });
        $routeProvider.when('/docs/contacto', {
            templateUrl: 'docs/contacto.html'
        });
        $routeProvider.when('/docs/privacidad', {
            templateUrl: 'docs/privacidad.html'
        });
        $routeProvider.when('/docs/terminos', {
            templateUrl: 'docs/terminos.html'
        });
        $routeProvider.when('/docs/asociacion', {
            templateUrl: 'docs/asociacion.html'
        });
    }]);
