"use strict";

app.controller("CurriculumTituloIdiomaSearchController", ['$scope', 'genericControllerCrudList', 'controllerParams', function($scope, genericControllerCrudList, controllerParams) {
        genericControllerCrudList.extendScope($scope, controllerParams);
        
        $scope.orderby=[
            {fieldName:"fecha",orderDirection:"ASC"}
        ];
        
        $scope.search();
    }]);

