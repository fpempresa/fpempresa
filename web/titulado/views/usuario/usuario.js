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




app.controller("UsuarioNewEditController", ['$scope', 'genericControllerCrudDetail', 'controllerParams', 'dialog', 'goPage', function ($scope, genericControllerCrudDetail, controllerParams, dialog, goPage) {
        genericControllerCrudDetail.extendScope($scope, controllerParams);


        $scope.cambiarContrasenya = function () {
            dialog.create('cambiarContrasenya', $scope.model);
        };

        $scope.borrarCuenta = function () {
            sweetAlert({
                title: "¿Estas seguro que deseas borrar tu cuenta de EmpleaFP?",
                text: "Una vez borrada la cuenta no será posible recuperar tus datos",
                type: 'error',
                confirmButtonText: 'Borrar',
                confirmButtonColor: '#d9534f',
                showCancelButton: true,
                cancelButtonText: 'Cancelar',
                cancelButtonColor: '#005594',
                focusInCancelButton:true
            }, function (borrar) {
                if (borrar) {
                    setTimeout(function () {
                        sweetAlert({
                            title: "Confirma que deseas borrar la cuenta de EmpleaFP",
                            text: "Una vez borrada la cuenta no será posible recuperar tus datos",
                            type: 'warning',                            
                            confirmButtonText: 'Confirmar',
                            confirmButtonColor: '#d9534f',
                            showCancelButton: true,                            
                            cancelButtonText: 'Cancelar',
                            cancelButtonColor: '#005594',
                            focusInCancelButton:true
                        }, function (borrar) {
                            if (borrar) {
                                $scope.$apply(function () {
                                    $scope.id=54;
                                    
                                    $scope.delete().then(function (data) {
                                        sweetAlert({
                                            title: "Tu cuenta de EmpleaFP ha sido borrada con éxito",
                                            type: 'success',
                                            confirmButtonText: 'Aceptar',
                                            confirmButtonColor: '#005594'
                                        }, function () {
                                            setTimeout(function () {
                                                goPage.homeApp();
                                            },400);
                                        });
                                    }, function (businessMessages) {
                                        $scope.businessMessages = businessMessages;
                                    });
                                });
                            }
                        });
                    }, 500);
                }
            });
        };

    }]);