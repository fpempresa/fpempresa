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
        crudRoutesProvider.addAllRoutes({
            entity: "Usuario",
            expand: "centro.direccion.municipio.provincia,empresa.direccion.municipio.provincia"
        });
    }]);

app.controller("UsuarioSearchController", ['$scope', 'genericControllerCrudList', 'controllerParams', 'schemaEntities', function ($scope, genericControllerCrudList, controllerParams, schemaEntities) {
        genericControllerCrudList.extendScope($scope, controllerParams);
        $scope.page.pageSize = 20;

        $scope.updateEstadoUsuario = function (idIdentity, estadoUsuario) {
            $scope.service.updateEstadoUsuario(idIdentity, estadoUsuario).then(function (data) {
                $scope.search();
            }, function (businessMessages) {
                $scope.businessMessages = businessMessages;
            });
        }


        if ($scope.parentProperty === "tipoUsuario") {
            $scope.tipoUsuarioDescription = "de tipo \"" + schemaEntities.getSchemaProperty("Usuario.tipoUsuario").getValueDescription($scope.parentId) + "\"";
        } else {
            $scope.tipoUsuarioDescription = "";
        }



        $scope.search();
    }]);


app.controller("UsuarioNewEditController", ['$scope', 'genericControllerCrudDetail', 'controllerParams', 'dialog', function ($scope, genericControllerCrudDetail, controllerParams, dialog) {
        genericControllerCrudDetail.extendScope($scope, controllerParams);

        $scope.postCreate = function () {
            //Cuando los crea el administrador siempre están aceptados por defecto
            $scope.model.estadoUsuario = "ACEPTADO";
        }

        $scope.preInsert = function () {
            //Al insertar un usuario siempre debe aceptar las politicas
            //Lo hacemos aqui pq al ser el administrador el que los crea suponemos que el usuario las ha aceptado
            $scope.model.aceptarCondicionesPolitica = true;
            $scope.model.aceptarEnvioCorreos = true;
            $scope.model.aceptarVerificarTitulo = true;
        };


        $scope.updateEstadoUsuario = function (estadoUsuario) {
            $scope.doUpdate().then(function () {
                $scope.service.updateEstadoUsuario($scope.model.idIdentity, estadoUsuario, $scope.expand).then(function (data) {
                    $scope.model = data;
                    $scope.businessMessages = null;
                }, function (businessMessages) {
                    $scope.businessMessages = businessMessages;
                });

            });
        };

        $scope.cambiarContrasenya = function () {
            dialog.create('cambiarContrasenya', $scope.model);
        };
        
        $scope.buttonDesbloquear = function () {
            $scope.model.lockedUntil=null;
            $scope.model.numFailedLogins=0;
            
        };        

    }]);

app.controller("UsuarioViewController", ['$scope', 'genericControllerCrudDetail', 'controllerParams', function ($scope, genericControllerCrudDetail, controllerParams) {
        genericControllerCrudDetail.extendScope($scope, controllerParams);
    }]);

app.controller("UsuarioDeleteController", ['$scope', 'genericControllerCrudDetail', 'controllerParams', function ($scope, genericControllerCrudDetail, controllerParams) {
        genericControllerCrudDetail.extendScope($scope, controllerParams);
    }]);