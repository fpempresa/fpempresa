"use strict";

app.config(['crudRoutesProvider', function (crudRoutesProvider) {
        crudRoutesProvider.addNewRoute({
            entity: "Titulado", 
            expand: "usuario,direccion.municipio",
            htmlBasePath:"views/curriculum"
        });
        crudRoutesProvider.addEditRoute({
            entity: "Titulado", 
            expand: "usuario,direccion.municipio",
            htmlBasePath:"views/curriculum"
        });
    }]);

app.controller("TituladoNewEditController", ['$scope', '$location', 'genericControllerCrudDetail', 'controllerParams', function ($scope, $location, genericControllerCrudDetail, controllerParams) {
        genericControllerCrudDetail.extendScope($scope, controllerParams);
        $scope.finishOK = function () {
            $location.path("/");
        };
    }]);
