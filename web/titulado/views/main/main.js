

app.controller('MainController', ['$scope', 'genericControllerCrudDetail', 'controllerParams', function ($scope, genericControllerCrudDetail, controllerParams) {
        controllerParams.id = $scope.user.titulado.idTitulado;
        genericControllerCrudDetail.extendScope($scope, controllerParams);
    }]);