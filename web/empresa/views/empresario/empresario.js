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
app.config(['$stateProvider', 'crudRoutesProvider', function ($stateProvider, crudRoutesProvider) {

        crudRoutesProvider.addAllRoutes({
            entity: "Usuario",
            expand: "empresa",
            crudName:"empresario"
        });
        

    }]);

app.controller("EmpresarioSearchController", ['$scope', 'genericControllerCrudList', 'controllerParams', 'schemaEntities', function ($scope, genericControllerCrudList, controllerParams, schemaEntities) {
        genericControllerCrudList.extendScope($scope, controllerParams);
        $scope.page.pageSize = 20;

        $scope.filters['empresa.idEmpresa']=$scope.user.empresa.idEmpresa;
        $scope.filters['tipoUsuario']='EMPRESA';

        $scope.search();
    }]);


app.controller("EmpresarioNewEditController", ['$scope', 'genericControllerCrudDetail', 'controllerParams', 'dialog', function ($scope, genericControllerCrudDetail, controllerParams, dialog) {
        genericControllerCrudDetail.extendScope($scope, controllerParams);

        $scope.postCreate = function () {

        };

        $scope.preInsert = function () {
            $scope.model.empresa=$scope.user.empresa;
            $scope.model.tipoUsuario='EMPRESA';
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

    }]);

app.controller("EmpresarioViewController", ['$scope', 'genericControllerCrudDetail', 'controllerParams', function ($scope, genericControllerCrudDetail, controllerParams) {
        genericControllerCrudDetail.extendScope($scope, controllerParams);
    }]);

app.controller("EmpresarioDeleteController", ['$scope', 'genericControllerCrudDetail', 'controllerParams', function ($scope, genericControllerCrudDetail, controllerParams) {
        genericControllerCrudDetail.extendScope($scope, controllerParams);
    }]);