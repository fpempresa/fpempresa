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
app.config(['crudRoutesProvider', function(crudRoutesProvider) {
        crudRoutesProvider.addAllRoutes({
            entity:"Empresa",
            expand:"direccion.municipio,direccion.municipio.provincia,centro.direccion.municipio,centro.direccion.municipio.provincia"
        });
    }]);

app.controller("EmpresaSearchController", ['$scope', 'genericControllerCrudList','controllerParams', '$state', function($scope, genericControllerCrudList,controllerParams, $state) {
        genericControllerCrudList.extendScope($scope, controllerParams);      
        $scope.page.pageSize=20;
        
        $scope.buttonRepresentantesEmpresa=function(idEmpresa) {
            var parameters={
                parentProperty:'tipoUsuario',
                parentId:'EMPRESA',
                $filters:{
                    $eq:{
                        'empresa.idEmpresa':idEmpresa
                    }
                }
            };
            
            $state.go("lateralmenu.usuario_search_parent",parameters);
        };
        
        
        $scope.search();
    }]);


app.controller("EmpresaNewEditController", ['$scope', 'genericControllerCrudDetail', 'controllerParams', '$state', function($scope, genericControllerCrudDetail, controllerParams, $state) {
        genericControllerCrudDetail.extendScope($scope, controllerParams);
        
        $scope.buttonRepresentantesEmpresa=function(idEmpresa) {
            var parameters={
                parentProperty:'tipoUsuario',
                parentId:'EMPRESA',
                $filters:{
                    $eq:{
                        'empresa.idEmpresa':idEmpresa
                    }
                }
            };
            
            $state.go("lateralmenu.usuario_search_parent",parameters);
        };
    }]);

app.controller("EmpresaViewController", ['$scope', 'genericControllerCrudDetail', 'controllerParams', '$state', function($scope, genericControllerCrudDetail, controllerParams, $state) {
        genericControllerCrudDetail.extendScope($scope, controllerParams);
        
        $scope.buttonRepresentantesEmpresa=function(idEmpresa) {
            var parameters={
                parentProperty:'tipoUsuario',
                parentId:'EMPRESA',
                $filters:{
                    $eq:{
                        'empresa.idEmpresa':idEmpresa
                    }
                }
            };
            
            $state.go("lateralmenu.usuario_search_parent",parameters);
        };        
        
    }]);

app.controller("EmpresaDeleteController", ['$scope', 'genericControllerCrudDetail', 'controllerParams', '$state', function($scope, genericControllerCrudDetail, controllerParams, $state) {
        genericControllerCrudDetail.extendScope($scope, controllerParams);
        
        $scope.buttonRepresentantesEmpresa=function(idEmpresa) {
            var parameters={
                parentProperty:'tipoUsuario',
                parentId:'EMPRESA',
                $filters:{
                    $eq:{
                        'empresa.idEmpresa':idEmpresa
                    }
                }
            };
            
            $state.go("lateralmenu.usuario_search_parent",parameters);
        };        
        
    }]);