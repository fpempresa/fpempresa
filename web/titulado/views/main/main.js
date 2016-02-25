

app.controller('MainController', ['$scope', 'genericControllerCrudDetail', 'controllerParams', 'ageCalculator', function ($scope, genericControllerCrudDetail, controllerParams, ageCalculator) {
        if ($scope.user && $scope.user.titulado && $scope.user.titulado.idTitulado) {
            controllerParams.id = $scope.user.titulado.idTitulado;
        } else {
            controllerParams.id = 0;
        }
        genericControllerCrudDetail.extendScope($scope, controllerParams);
        $scope.ageCalculator=ageCalculator;
    }]);