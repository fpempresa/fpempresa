app.config(['$stateProvider', function($stateProvider) {
        $stateProvider.state('/info/centro-educativo', {
            url:'/info/centro-educativo',
            templateUrl: 'views/info/centro-educativo.html'
        });        
        $stateProvider.state('/info/empresa', {
            url:'/info/empresa',
            templateUrl: 'views/info/empresa.html'
        });         
        $stateProvider.state('/info/nosotros', {
            url:'/info/nosotros',
            templateUrl: 'views/info/nosotros.html'
        });
        $stateProvider.state('/info/privacidad', {
            url:'/info/privacidad',
            templateUrl: 'views/info/privacidad.html'
        });        
        $stateProvider.state('/info/proyecto', {
            url:'/info/proyecto',
            templateUrl: 'views/info/proyecto.html'
        });   
        $stateProvider.state('/info/titulado', {
            url:'/info/titulado',
            templateUrl: 'views/info/titulado.html',
            controller: 'TituladoController'
            
        });   
          
    }]);
