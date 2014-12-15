"use strict";
app.config(['crudRoutesProvider', function(crudRoutesProvider) {
        crudRoutesProvider.addAllRoutes("FormacionAcademica");
    }]);

app.controller("FormacionAcademicaSearchController", ['$scope', 'crud','$filter', function($scope, crud,$filter) {
        crud.extendScope($scope);      

        $scope.search();

    }]);


app.controller("FormacionAcademicaNewEditController", ['$scope', 'crud', '$location', function($scope, crud, $location) {
        crud.extendScope($scope);
    }]);

app.controller("FormacionAcademicaViewController", ['$scope', 'crud', '$location', function($scope, crud, $location) {
        crud.extendScope($scope);
    }]);

app.controller("FormacionAcademicaDeleteController", ['$scope', 'crud', '$location', function($scope, crud, $location) {
        crud.extendScope($scope);
    }]);