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
        
        $scope.borrarCuenta = function () {
            if (confirm("¿Esta seguro que desea borrar la cuenta y todos sus datos de EmpleaFP?")) {
                alert('Para borrar todos sus datos de EmpleaFP debe enviar un correo electronico a: \nsoporte@empleafp.com\nindicando en el asunto que desea darse de baja')
            }
        };        

    }]);