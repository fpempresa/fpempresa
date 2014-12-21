"use strict";

app.controller("TituloIdiomaSearchController", ['$scope', 'genericControllerCrudList', 'controllerParams', function($scope, genericControllerCrudList, controllerParams) {
        genericControllerCrudList.extendScope($scope, controllerParams);

        $scope.search();
    }]);

