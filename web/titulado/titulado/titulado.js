"use strict";

app.config(['crudProvider', function(crudProvider) {
        crudProvider.addAllRoutes("Titulado");
    }]);

app.controller("TituladoNewEditController", ['$scope', 'crudState','$location', function($scope, crudState,$location) {
        crudState.extendsScopeController($scope,{
            expand:"usuario,direccion.municipio"
        });
        $scope.finishOK = function() {
                $location.path("/");
        };
    }]);

app.controller("TituladoViewController", ['$scope', 'crudState','$location', function($scope, crudState,$location) {
        crudState.extendsScopeController($scope,{
            expand:"usuario"
        });
         
        
    }]);
app.controller("TituladoDeleteController", ['$scope', 'crudState','$location', function($scope, crudState,$location) {
        crudState.extendsScopeController($scope,{
            expand:"usuario,direccion.municipio"
        });
         
        
    }]);