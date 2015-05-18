"user strict";

app.controller("LateralMenuController", ['$scope', '$location', '$state', 'dialog', function ($scope, $location, $state, dialog) {
        $scope.isItemSelected = function (option) {
            if (option) {
                var regExp = new RegExp(option);
                if (regExp.test($location.url()) === true) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        };


        $scope.cambiarContrasenya = function () {
            dialog.create('cambiarContrasenya', $scope.user);
        }

    }]);