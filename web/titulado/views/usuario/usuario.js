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