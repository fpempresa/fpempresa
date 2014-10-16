"use strict";
app.config(['crudRoutesProvider', function(crudRoutesProvider) {
        crudRoutesProvider.addAllRoutes("ExperienciaLaboral");
    }]);

app.controller("ExperienciaLaboralSearchController", ['$scope', 'crud', '$location', function($scope, crud, $location) {
        crud.extendScope($scope);

        $scope.search();
    }]);

app.controller("ExperienciaLaboralNewEditController", ['$scope', 'crud', '$location', function($scope, crud, $location) {
        crud.extendScope($scope);
    }]);

app.controller("ExperienciaLaboralViewController", ['$scope', 'crud', '$location', function($scope, crud, $location) {
        crud.extendScope($scope);
    }]);

app.controller("ExperienciaLaboralDeleteController", ['$scope', 'crud', '$location', function($scope, crud, $location) {
        crud.extendScope($scope);
    }]);