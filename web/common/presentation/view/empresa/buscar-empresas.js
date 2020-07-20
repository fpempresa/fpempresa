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

    BuscarEmpresaController.$inject = ['$scope', 'genericControllerCrudList', 'currentDialog', 'controllerParams'];
    function BuscarEmpresaController($scope, genericControllerCrudList, currentDialog, controllerParams) {
        genericControllerCrudList.extendScope($scope, controllerParams);
        $scope.page.pageSize = 10;
        angular.extend($scope.filters, currentDialog.params.filters);

        $scope.orderby=[
            {fieldName:"fecha",orderDirection:"DESC"}
        ];

        currentDialog.open({
            width: 600,
            title: "Buscar empresa"
        });



        $scope.buttonCancel = function () {
            currentDialog.closeCancel();
        };

        $scope.buttonSeleccionar = function (empresa) {
            currentDialog.closeOK(empresa);
        };
        
        if (currentDialog.params.initialSearch) {
            $scope.search();
        }

    }

    angular.module("common").controller("BuscarEmpresaController", BuscarEmpresaController);


    angular.module("common").config(['dialogProvider', 'getContextPath', 'crudRoutesProvider', function (dialogProvider, getContextPath, crudRoutesProvider) {
            
        dialogProvider.when('buscarEmpresas', {
            templateUrl: getContextPath() + "/common/presentation/view/empresa/buscar-empresas.html",
            controller: 'BuscarEmpresaController',
            resolve: crudRoutesProvider.getResolve('Empresa', 'direccion.municipio,direccion.municipio.provincia')
        });
                    
    }]);




})();