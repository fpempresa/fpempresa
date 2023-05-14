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


app.controller("ProfesorSearchController", ['$scope', 'genericControllerCrudList', 'controllerParams', 'schemaEntities', function ($scope, genericControllerCrudList, controllerParams, schemaEntities) {
        genericControllerCrudList.extendScope($scope, controllerParams);
        $scope.page.pageSize = 20;

        $scope.filters['centro.idCentro']=$scope.user.centro.idCentro;
        $scope.filters['tipoUsuario']="CENTRO";
        $scope.filters['estadoUsuario$ne']="RECHAZADO";

        $scope.updateEstadoUsuario = function (idIdentity, estadoUsuario) {
            $scope.service.updateEstadoUsuario(idIdentity, estadoUsuario).then(function (data) {
                $scope.search();
            }, function (businessMessages) {
                $scope.businessMessages = businessMessages;
            });
        }

        $scope.search();
    }]);


app.controller("ProfesorNewEditController", ['$scope', 'genericControllerCrudDetail', 'controllerParams', 'dialog', function ($scope, genericControllerCrudDetail, controllerParams, dialog) {
        genericControllerCrudDetail.extendScope($scope, controllerParams);

        $scope.postCreate = function () {
            //Cuando los crea el administrador siempre están aceptados por defecto
            $scope.model.estadoUsuario = "ACEPTADO";
            $scope.model.tipoUsuario="CENTRO"; 
            $scope.model.centro=$scope.user.centro;
        }

        $scope.preInsert = function () {
            //Al insertar un usuario siempre debe aceptar las politicas
            $scope.model.aceptarCondicionesPolitica = true;
            $scope.model.aceptarEnvioCorreos = true;
            $scope.model.aceptarVerificarTitulo = true;
        };
        
        $scope.cambiarContrasenya = function () {
            dialog.create('cambiarContrasenya', $scope.model);
        };

    }]);

app.controller("ProfesorViewController", ['$scope', 'genericControllerCrudDetail', 'controllerParams', function ($scope, genericControllerCrudDetail, controllerParams) {
        genericControllerCrudDetail.extendScope($scope, controllerParams);
    }]);
app.controller("ProfesorDeleteController", ['$scope', 'genericControllerCrudDetail', 'controllerParams', function ($scope, genericControllerCrudDetail, controllerParams) {
        genericControllerCrudDetail.extendScope($scope, controllerParams);
    }]);