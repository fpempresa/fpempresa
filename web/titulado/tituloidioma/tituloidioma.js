"use strict";
app.config(['crudProvider', function(crudProvider) {
        crudProvider.addAllRoutes("TituloIdioma");
    }]);

app.controller("TituloIdiomaSearchController", ['$scope', 'crudState', function($scope, crudState) {
        crudState.extendsScopeController($scope, {
        });

        $scope.search();

    }]);


app.controller("TituloIdiomaNewEditController", ['$scope', 'crudState', '$location', function($scope, crudState, $location) {
        crudState.extendsScopeController($scope, {
        });
    }]);

app.controller("TituloIdiomaViewController", ['$scope', 'crudState', '$location', function($scope, crudState, $location) {
        crudState.extendsScopeController($scope, {
        });


    }]);
app.controller("TituloIdiomaDeleteController", ['$scope', 'crudState', '$location', function($scope, crudState, $location) {
        crudState.extendsScopeController($scope, {
        });


    }]);