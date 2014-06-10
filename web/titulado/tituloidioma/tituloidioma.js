"use strict";
app.config(['crudRoutesProvider', function(crudRoutesProvider) {
        crudRoutesProvider.addAllRoutes("TituloIdioma");
    }]);

app.controller("TituloIdiomaSearchController", ['$scope', 'crud', '$location', function($scope, crud, $location) {
        crud.extendScope($scope);

        $scope.search();
    }]);

app.controller("TituloIdiomaNewEditController", ['$scope', 'crud', '$location', function($scope, crud, $location) {
        crud.extendScope($scope);
    }]);

app.controller("TituloIdiomaViewController", ['$scope', 'crud', '$location', function($scope, crud, $location) {
        crud.extendScope($scope);
    }]);

app.controller("TituloIdiomaDeleteController", ['$scope', 'crud', '$location', function($scope, crud, $location) {
        crud.extendScope($scope);
    }]);