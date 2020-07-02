app.controller("HeaderController", ['$scope', 'goPage', function ($scope, goPage) {
        $scope.homeUsuario = function () {
            goPage.homeUsuario();
        }
        $scope.createAccount = function () {
            goPage.createAccount();
        };
    }]);
