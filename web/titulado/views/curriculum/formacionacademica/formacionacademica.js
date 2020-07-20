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
            expand:"ciclo.familia,ciclo.grado",
            crudName:"curriculum.FormacionAcademica"
        });
    }]);

app.controller("CurriculumFormacionAcademicaSearchController", ['$scope', 'genericControllerCrudList','controllerParams', function($scope, genericControllerCrudList,controllerParams) {
        genericControllerCrudList.extendScope($scope, controllerParams);    
        
        $scope.orderby=[
            {fieldName:"fecha",orderDirection:"ASC"}
        ];
        
        $scope.search();
    }]);


app.controller("CurriculumFormacionAcademicaNewEditController", ['$scope', 'genericControllerCrudDetail', 'controllerParams', function($scope, genericControllerCrudDetail, controllerParams) {
        genericControllerCrudDetail.extendScope($scope, controllerParams);
    }]);

app.controller("CurriculumFormacionAcademicaViewController", ['$scope', 'genericControllerCrudDetail', 'controllerParams', function($scope, genericControllerCrudDetail, controllerParams) {
        genericControllerCrudDetail.extendScope($scope, controllerParams);
    }]);

app.controller("CurriculumFormacionAcademicaDeleteController", ['$scope', 'genericControllerCrudDetail', 'controllerParams', function($scope, genericControllerCrudDetail, controllerParams) {
        genericControllerCrudDetail.extendScope($scope, controllerParams);
    }]);