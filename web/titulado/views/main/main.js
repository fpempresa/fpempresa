
app.controller('MainController', ['$scope','$http','ix3Configuration', 'genericControllerCrudDetail', 'controllerParams', 'ageCalculator', function ($scope, $http, ix3Configuration, genericControllerCrudDetail, controllerParams, ageCalculator) {
        if ($scope.user && $scope.user.titulado && $scope.user.titulado.idTitulado) {
            controllerParams.id = $scope.user.titulado.idTitulado;
        } else {
            controllerParams.id = 0;
        }
        genericControllerCrudDetail.extendScope($scope, controllerParams);
        $scope.ageCalculator = ageCalculator;

        $scope.businessMessages = [];

        if ($scope.user && $scope.user.titulado) {
            $http({
                method: "GET",
                url: ix3Configuration.server.api + "/Estadisticas/titulado/" + $scope.user.titulado.idTitulado
            }).then(function (chartData) {
                $scope.chartData = chartData.data;
                $scope.chartData['numeroEmpresas'] = [{valor: chartData.data.numeroEmpresas}];
            }, function (businessMessages) {
                $scope.businessMessages = businessMessages;
            });
        }
    }]);
