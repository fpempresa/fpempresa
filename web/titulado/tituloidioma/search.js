"use strict";

app.controller("TituloIdiomaSearchController", ['$scope', 'crud', '$location', function($scope, crud, $location) {
        crud.extendScope($scope);

        $scope.search();
    }]);

