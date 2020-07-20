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
app.config(['$stateProvider', 'crudRoutesProvider', function ($stateProvider, crudRoutesProvider) {
        
    $stateProvider.state("lateralmenu.ultimoacceso", {
        url: '/usuario/ultimoacceso',
        templateUrl: 'views/usuario/ultimoacceso.html',
        controller: 'UsuarioUltimoAccesoSearchController',
        resolve: crudRoutesProvider.getResolve("Usuario")
    });

               
        
    }]);


app.controller("UsuarioUltimoAccesoSearchController", ['$scope', 'genericControllerCrudList', 'controllerParams', 'schemaEntities', function ($scope, genericControllerCrudList, controllerParams, schemaEntities) {
        genericControllerCrudList.extendScope($scope, controllerParams);
        $scope.page.pageSize = 20;
        $scope.filters['$isnull']={};
        $scope.filters['$isnull'].fechaUltimoAcceso=false;
        
        var anyoPasado = new Date();
        anyoPasado.setFullYear( anyoPasado.getFullYear() - 1 );
        $scope.filters['$dle']={};
        $scope.filters['$dle'].fechaUltimoAcceso=anyoPasado;
        
        $scope.orderby = [
            {fieldName: "fechaUltimoAcceso", orderDirection: "ASC"},
            {fieldName: "fecha", orderDirection: "ASC"}
        ];  
        
        $scope.search();
    }]);