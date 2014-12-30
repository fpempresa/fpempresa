app.config(['$stateProvider', function($stateProvider) {
        $stateProvider.state('/docs/faq', {
            url:'/docs/faq',
            templateUrl: 'docs/faq.html'
        });
        $stateProvider.state('/docs/contacto', {
            url:'/docs/contacto',
            templateUrl: 'docs/contacto.html'
        });
        $stateProvider.state('/docs/privacidad', {
            url:'/docs/privacidad',
            templateUrl: 'docs/privacidad.html'
        });
        $stateProvider.state('/docs/terminos', {
            url:'/docs/terminos',
            templateUrl: 'docs/terminos.html'
        });
        $stateProvider.state('/docs/asociacion', {
            url:'/docs/asociacion',
            templateUrl: 'docs/asociacion.html'
        });
    }]);
