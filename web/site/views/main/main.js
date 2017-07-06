app.controller('MainController', ['$scope', 'goPage', 'ix3Configuration', '$http', function ($scope, goPage, ix3Configuration, $http) {
        $scope.login = function () {
            goPage.login();
        };

        $scope.createAccount = function () {
            goPage.createAccount();
        };

        $http({
            method: "GET",
            url: ix3Configuration.server.api + "/Estadisticas/publicas/"
        }).then(function (chartData) {
            $scope.chartData = chartData.data;
            console.log(chartData);
            $scope.chartData['numeroEmpresas'] = [{valor: chartData.data.numeroEmpresas}];
        }, function (businessMessages) {
            $scope.businessMessages = businessMessages;
        });


    }]);



