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
            entity:"FormacionAcademica",
            expand:"ciclo.familia,ciclo.grado,centro.direccion.municipio.provincia",
            crudName:"curriculum.FormacionAcademica"
        });
    }]);

app.controller("CurriculumFormacionAcademicaSearchController", ['$scope', 'genericControllerCrudList','controllerParams','session', function($scope, genericControllerCrudList,controllerParams,session) {
        genericControllerCrudList.extendScope($scope, controllerParams);    
        
        $scope.orderby=[
            {fieldName:"fecha",orderDirection:"ASC"}
        ];
        
        $scope.search();
        
        $scope.postSearch=function(formacionesAcademicas) {
            session.getUser().titulado.formacionesAcademicas=formacionesAcademicas;
        }
    }]);


app.controller("CurriculumFormacionAcademicaNewEditController", ['$scope', 'genericControllerCrudDetail', 'controllerParams', function($scope, genericControllerCrudDetail, controllerParams) {
        genericControllerCrudDetail.extendScope($scope, controllerParams);

        var anioActual = new Date().getFullYear();
        $scope.aniosDisponibles = [];
        for (var i = anioActual; i >= anioActual - 60; i--) {
            $scope.aniosDisponibles.push(i);
        }

        $scope.onAnioChange = function() {
            if ($scope.anioObtencion) {
                $scope.model.fecha = new Date($scope.anioObtencion, 0, 1).getTime();
            } else {
                $scope.model.fecha = null;
            }
        };

        $scope.postGet = function() {
            $scope.anioObtencion = $scope.model.fecha ? new Date($scope.model.fecha).getFullYear() : null;
        };

    }]);

app.controller("CurriculumFormacionAcademicaViewController", ['$scope', 'genericControllerCrudDetail', 'controllerParams', function($scope, genericControllerCrudDetail, controllerParams) {
        genericControllerCrudDetail.extendScope($scope, controllerParams);
    }]);

app.controller("CurriculumFormacionAcademicaDeleteController", ['$scope', 'genericControllerCrudDetail', 'controllerParams', function($scope, genericControllerCrudDetail, controllerParams) {
        genericControllerCrudDetail.extendScope($scope, controllerParams);
    }]);