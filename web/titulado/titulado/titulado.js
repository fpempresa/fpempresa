"use strict";

app.config(['crudRoutesProvider', function(crudRoutesProvider) {
        crudRoutesProvider.addNewRoute("Titulado", "usuario,direccion.municipio");
        crudRoutesProvider.addEditRoute("Titulado", "usuario,direccion.municipio");
    }]);

app.controller("TituladoNewEditController", ['$scope', '$location', 'genericControllerCrudDetail', 'controllerParams', function($scope, $location, genericControllerCrudDetail, controllerParams) {
        genericControllerCrudDetail.extendScope($scope, controllerParams);
        $scope.finishOK = function() {
            $location.path("/");
        };
    }]);
