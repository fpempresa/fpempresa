app.config(['$stateProvider', function($stateProvider) {
        
        $stateProvider.state('/info/titulado', {
            url:'/info/titulado',
            templateUrl: 'views/info/titulado.html',
            controller: 'TituladoController'
        }); 
        $stateProvider.state('/info/centro-educativo', {
            url:'/info/centro-educativo',
            templateUrl: 'views/info/centro-educativo.html',
            controller: 'CentroController'
        });        
        $stateProvider.state('/info/empresa', {
            url:'/info/empresa',
            templateUrl: 'views/info/empresa.html',
            controller: 'EmpresaController'
        });      
        
        
        $stateProvider.state('/info/proyecto', {
            url:'/info/proyecto',
            templateUrl: 'views/info/proyecto.html'
        });
        $stateProvider.state('/info/privacidad', {
            url:'/info/privacidad',
            templateUrl: 'views/info/privacidad.html'
        });        
   
  
          
    }]);


app.controller('TituladoController', ['$scope', 'goPage', function ($scope, goPage) {
        $scope.numImage = Math.floor(Math.random() * 6);
        
        $scope.createAccount = function () {
            goPage.createAccount('TITULADO');
        };
        
    }]);

app.controller('EmpresaController', ['$scope', 'goPage', function ($scope, goPage) {
        $scope.numImage = Math.floor(Math.random() * 6);
        
        $scope.createAccount = function () {
            goPage.createAccount('EMPRESA');
        };
        
    }]);

app.controller('CentroController', ['$scope', 'goPage', function ($scope, goPage) {
        $scope.numImage = Math.floor(Math.random() * 6);
        
        $scope.createAccount = function () {
            goPage.createAccount('CENTRO');
        };
        
    }]);