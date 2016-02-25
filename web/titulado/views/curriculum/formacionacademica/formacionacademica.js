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