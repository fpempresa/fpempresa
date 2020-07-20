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

    VerEmpresaController.$inject = ['$scope', 'genericControllerCrudDetail', 'currentDialog', 'controllerParams', '$q', '$timeout'];
    function VerEmpresaController($scope, genericControllerCrudDetail, currentDialog, controllerParams, $q, $timeout) {
        controllerParams.get =function() {
            var defered = $q.defer();

            $timeout(function() {
                var data=currentDialog.params.empresa;
                defered.resolve(data);
            },1);

            return defered.promise;
        };
        
        
        genericControllerCrudDetail.extendScope($scope, controllerParams);


        currentDialog.open({
            width: 850,
            title: "Empresa"
        });

        $scope.finishOK = function () {
            currentDialog.closeCancel();
        };

    }

    angular.module("common").controller("VerEmpresaController", VerEmpresaController);


    angular.module("common").config(['dialogProvider', 'getContextPath', 'crudRoutesProvider', function (dialogProvider, getContextPath, crudRoutesProvider) {

            dialogProvider.when('verEmpresa', {
                templateUrl: getContextPath() + "/common/presentation/view/empresa/ver-empresa.html",
                controller: 'VerEmpresaController',
                resolve: crudRoutesProvider.getResolve('Empresa', 'direccion.municipio,direccion.municipio.provincia', 'VIEW')
            });

        }]);




})();