/**
 *   FPempresa
 *   Copyright (C) 2020  Lorenzo González
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Affero General Public License as
 *   published by the Free Software Foundation, either version 3 of the
 *   License, or (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Affero General Public License for more details.
 *
 *   You should have received a copy of the GNU Affero General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
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