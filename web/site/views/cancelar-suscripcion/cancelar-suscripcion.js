/**
 *   FPempresa
 *   Copyright (C) 2021  Lorenzo González
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

app.controller("CancelarSuscripcionController", ['$scope', 'formValidator', 'serviceFactory', '$stateParams', '$state', function ($scope, formValidator, serviceFactory, $stateParams, $state) {
        var usuarioService = serviceFactory.getService("Usuario");
        $scope.model = {};
        $scope.businessMessages = null;


        usuarioService.cancelarSuscripcion($stateParams.idIdentity,$stateParams.token).then(function () {
             $scope.message="Se ha cancelado tu suscripción correctamente.  Ya no recibirás más correos con nuevas ofertas.";
        }, function (businessMessages) {
            $scope.businessMessages = businessMessages;
        });
     
    }]);