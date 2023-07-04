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
            entity: "Centro",
            expand: "direccion.municipio,direccion.municipio.provincia"
        });
    }]);

app.controller("CentroSearchController", ['$scope', 'genericControllerCrudList', 'controllerParams', function ($scope, genericControllerCrudList, controllerParams) {
        genericControllerCrudList.extendScope($scope, controllerParams);
        $scope.page.pageSize = 20;

        $scope.filters.$gt.idCentro=0; 
        
        $scope.orderby = [
            {fieldName: "nombre", orderDirection: "ASC"}          
        ];

        
        $scope.preSearch = function (filters) {
            if (filters['direccion.municipio.provincia.idProvincia']) {
                filters['direccion.municipio.provincia.idProvincia'] = filters['direccion.municipio.provincia.idProvincia'].idProvincia;
            }
        }
        
        $scope.search();
    }]);


app.controller("CentroNewEditController", ['$scope', 'genericControllerCrudDetail', 'controllerParams', function ($scope, genericControllerCrudDetail, controllerParams) {
        genericControllerCrudDetail.extendScope($scope, controllerParams);
        
        
        $scope.postCreate = function () {
            $scope.model.estadoCentro="PERTENECE_A_FPEMPRESA";
        };
        
    }]);

app.controller("CentroViewController", ['$scope', 'genericControllerCrudDetail', 'controllerParams', function ($scope, genericControllerCrudDetail, controllerParams) {
        genericControllerCrudDetail.extendScope($scope, controllerParams);
    }]);

app.controller("CentroDeleteController", ['$scope', 'genericControllerCrudDetail', 'controllerParams', function ($scope, genericControllerCrudDetail, controllerParams) {
        genericControllerCrudDetail.extendScope($scope, controllerParams);
    }]);