"use strict";
app.config(['crudRoutesProvider', function (crudRoutesProvider) {
        crudRoutesProvider.addEditRoute({
            entity: "Usuario",
            expand: "centro.direccion.municipio.provincia,empresa.direccion.municipio.provincia"
        });
    }]);




app.controller("UsuarioNewEditController", ['$scope', 'genericControllerCrudDetail', 'controllerParams', 'dialog', function ($scope, genericControllerCrudDetail, controllerParams, dialog) {
        genericControllerCrudDetail.extendScope($scope, controllerParams);


        $scope.cambiarContrasenya = function () {
            dialog.create('cambiarContrasenya', $scope.model);
        };

    }]);