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
(function (undefined) {
    "use strict";

    AvisoInscripcionController.$inject = ['$scope', 'currentDialog'];
    function AvisoInscripcionController($scope, currentDialog) {
        $scope.datosContacto = currentDialog.params.datosContacto;
        $scope.mensaje = currentDialog.params.mensaje;
        $scope.titulo = currentDialog.params.titulo;

        $scope.buttonCancel = function () {
            currentDialog.closeCancel();
        };       
        
        
        currentDialog.open({
            width: 600,
            height: 440,
            title: "Aviso"
        });        
    }

    angular.module("common").controller("AvisoInscripcionController", AvisoInscripcionController);


    angular.module("common").config(['dialogProvider', 'getContextPath', 'crudRoutesProvider', function (dialogProvider, getContextPath, crudRoutesProvider) {

            dialogProvider.when('avisoInscripcion', {
                templateUrl: getContextPath() + "/titulado/views/oferta/aviso-inscripcion.html",
                controller: 'AvisoInscripcionController'
            });

        }]);




})();