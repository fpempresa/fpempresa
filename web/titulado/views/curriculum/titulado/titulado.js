"use strict";

app.config(['crudRoutesProvider', function (crudRoutesProvider) {
        crudRoutesProvider.addEditRoute({
            entity: "Titulado", 
            expand: "direccion.municipio"
        });
        crudRoutesProvider.addNewRoute({
            entity: "Titulado", 
            expand: "direccion.municipio"
        });        
    }]);

app.controller("TituladoNewEditController", ['$scope', '$location', 'genericControllerCrudDetail', 'controllerParams','session', function ($scope, $location, genericControllerCrudDetail, controllerParams,session) {
        genericControllerCrudDetail.extendScope($scope, controllerParams);
        $scope.finishOK = function () {
            session.logged().then(function() {
                $location.path("/");
            });
        };
    }]);
