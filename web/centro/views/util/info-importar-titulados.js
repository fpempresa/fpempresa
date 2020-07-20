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

    MostrarCodigosMunicipioDialogController.$inject = ['$scope', 'serviceFactory',  'currentDialog'];
    function MostrarCodigosMunicipioDialogController($scope, serviceFactory, currentDialog) {
        $scope.businessMessages = [];

        currentDialog.open({
            width: 800,
            height: 'auto',
            title: "Ayuda para importar titulados"
        });

        $scope.buttonCancel = function () {
            currentDialog.closeCancel();
        };

        $scope.buttonOK = function () {
            currentDialog.closeOK();
        };

    }

    angular.module("app").controller("MostrarCodigosMunicipioDialogController", MostrarCodigosMunicipioDialogController);

    angular.module("app").config(['dialogProvider', 'getContextPath', function (dialogProvider, getContextPath) {

            dialogProvider.when('mostrarCodigosMunicipio', {
                templateUrl: getContextPath() + "/centro/views/util/info-importar-titulados.jsp",
                controller: 'MostrarCodigosMunicipioDialogController'
            });

        }]);

})();