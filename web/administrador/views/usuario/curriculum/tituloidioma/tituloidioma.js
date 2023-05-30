/* 
 * FPempresa Copyright (C) 2023 Lorenzo González
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Affero General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
"use strict";
app.config(['crudRoutesProvider', function(crudRoutesProvider) {
        crudRoutesProvider.addAllRoutes({
            entity:"TituloIdioma",
            crudName:"usuario.curriculum.TituloIdioma"
        });
    }]);

app.controller("UsuarioCurriculumTituloIdiomaSearchController", ['$scope', 'genericControllerCrudList', 'controllerParams', function($scope, genericControllerCrudList, controllerParams) {
        genericControllerCrudList.extendScope($scope, controllerParams);
        
        $scope.orderby=[
            {fieldName:"fecha",orderDirection:"ASC"}
        ];
        
        $scope.search();
    }]);


app.controller("UsuarioCurriculumTituloIdiomaNewEditController", ['$scope', 'genericControllerCrudDetail', 'controllerParams', function($scope, genericControllerCrudDetail, controllerParams) {
        genericControllerCrudDetail.extendScope($scope, controllerParams);
    }]);

app.controller("UsuarioCurriculumTituloIdiomaViewController", ['$scope', 'genericControllerCrudDetail', 'controllerParams', function($scope, genericControllerCrudDetail, controllerParams) {
        genericControllerCrudDetail.extendScope($scope, controllerParams);
    }]);

app.controller("UsuarioCurriculumTituloIdiomaDeleteController", ['$scope', 'genericControllerCrudDetail', 'controllerParams', function($scope, genericControllerCrudDetail, controllerParams) {
        genericControllerCrudDetail.extendScope($scope, controllerParams);
    }]);