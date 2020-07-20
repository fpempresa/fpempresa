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
            entity: "CertificadoTitulo",
            expand: "centro,ciclo"
        });
    }]);

app.controller("CertificadoTituloSearchController", ['$scope', 'genericControllerCrudList', 'controllerParams', function ($scope, genericControllerCrudList, controllerParams) {
        controllerParams.parentProperty = 'centro.idCentro';
        controllerParams.parentId = "" + $scope.user.centro.idCentro;
        genericControllerCrudList.extendScope($scope, controllerParams);
        $scope.page.pageSize = 20;

        $scope.preSearch = function (filters) {
            if (filters['ciclo.familia.idFamilia']) {
                filters['ciclo.familia.idFamilia'] = filters['ciclo.familia.idFamilia'].idFamilia;
            }
            if (filters['ciclo.idCiclo']) {
                filters['ciclo.idCiclo'] = filters['ciclo.idCiclo'].idCiclo;
            }
        }

        $scope.search();
    }]);


app.controller("CertificadoTituloNewEditController", ['$scope', 'genericControllerCrudDetail', 'controllerParams', function ($scope, genericControllerCrudDetail, controllerParams) {
        genericControllerCrudDetail.extendScope($scope, controllerParams);


    }]);

app.controller("CertificadoTituloViewController", ['$scope', 'genericControllerCrudDetail', 'controllerParams', function ($scope, genericControllerCrudDetail, controllerParams) {
        genericControllerCrudDetail.extendScope($scope, controllerParams);


    }]);

app.controller("CertificadoTituloDeleteController", ['$scope', 'genericControllerCrudDetail', 'controllerParams', function ($scope, genericControllerCrudDetail, controllerParams) {
        genericControllerCrudDetail.extendScope($scope, controllerParams);

    }]);