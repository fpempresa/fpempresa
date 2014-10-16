"use strict";

app.config(['crudRoutesProvider', function(crudRoutesProvider) {
        crudRoutesProvider.addAllRoutes("Titulado", "usuario,direccion.municipio");
    }]);

app.controller("TituladoNewEditController", ['$scope', 'crud', '$location', function($scope, crud, $location) {
        crud.extendScope($scope);
        $scope.finishOK = function() {
            $location.path("/");
        };
    }]);
