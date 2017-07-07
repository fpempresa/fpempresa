app.config(['$stateProvider', 'crudRoutesProvider', function ($stateProvider, crudRoutesProvider) {
        $stateProvider.state('lateralmenu.main', {
            url: "/",
            templateUrl: 'views/main/main.html',
            controller: 'MainController'
        });
    }]);


MainController.$inject = ['$scope', '$http', 'ix3Configuration'];
function MainController($scope, $http, ix3Configuration) {
    $scope.businessMessages = [];

    if ($scope.user && $scope.user.centro) {
        $http({
            method: "GET",
            url: ix3Configuration.server.api + "/Estadisticas/centro/" + $scope.user.centro.idCentro
        }).then(function (chartData) {
            $scope.chartData = chartData.data;
            $scope.chartData['numeroCentros'] = [{valor: chartData.data.numeroCentros}];
        }, function (businessMessages) {
            $scope.businessMessages = businessMessages;
        });
    }
}
app.controller("MainController", MainController);