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

    if ($scope.user && $scope.user.empresa) {
        $http({
            method: "GET",
            url: ix3Configuration.server.api + "/Estadisticas/empresa/" + $scope.user.empresa.idEmpresa
        }).then(function (chartData) {
            $scope.chartData=chartData.data;
        }, function (businessMessages) {
            $scope.businessMessages = businessMessages;
        });
    }

}
app.controller("MainController", MainController);