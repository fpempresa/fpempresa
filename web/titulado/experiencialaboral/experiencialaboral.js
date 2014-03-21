"use strict";
app.config(['crudProvider', function(crudProvider) {
        crudProvider.addAllRoutes("ExperienciaLaboral");
    }]);

app.controller("ExperienciaLaboralSearchController", ['$scope', 'crudState', function($scope, crudState) {
        crudState.extendsScopeController($scope, {
        });

        $scope.search();

    }]);


app.controller("ExperienciaLaboralNewEditController", ['$scope', 'crudState', '$location', function($scope, crudState, $location) {
        crudState.extendsScopeController($scope, {
        });
    }]);

app.controller("ExperienciaLaboralViewController", ['$scope', 'crudState', '$location', function($scope, crudState, $location) {
        crudState.extendsScopeController($scope, {
        });


    }]);
app.controller("ExperienciaLaboralDeleteController", ['$scope', 'crudState', '$location', function($scope, crudState, $location) {
        crudState.extendsScopeController($scope, {
        });


    }]);