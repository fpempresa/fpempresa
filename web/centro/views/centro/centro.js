"use strict";
app.config(['crudRoutesProvider', function (crudRoutesProvider) {
        crudRoutesProvider.addEditRoute({
            entity: "Centro",
            expand: "direccion.municipio,direccion.municipio.provincia"
        });
    }]);



app.controller("CentroNewEditController", ['$scope', 'genericControllerCrudDetail', 'controllerParams', function ($scope, genericControllerCrudDetail, controllerParams) {
        genericControllerCrudDetail.extendScope($scope, controllerParams);
        $scope.finishOK = function () {
            alert("Los datos han sido actualizados correctamente");
        };

    }]);

