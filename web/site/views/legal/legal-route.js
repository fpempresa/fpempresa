app.config(['$stateProvider', function($stateProvider) {
        $stateProvider.state('/legal/aviso-legal', {
            url:'/legal/aviso-legal',
            templateUrl: 'views/legal/aviso-legal.html'
        });
        $stateProvider.state('/legal/cookies', {
            url:'/legal/cookies',
            templateUrl: 'views/legal/cookies.html'
        });        
        $stateProvider.state('/legal/politica-privacidad', {
            url:'/legal/politica-privacidad',
            templateUrl: 'views/legal/politica-privacidad.html'
        }); 
        $stateProvider.state('/legal/terminos-uso', {
            url:'/legal/terminos-uso',
            templateUrl: 'views/legal/terminos-uso.html'
        });
        
    }]);
