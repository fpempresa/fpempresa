app.controller('MainController', ['$scope', 'goPage', function ($scope, goPage) {
        $scope.login = function () {
            goPage.login();
        };

    }]);



