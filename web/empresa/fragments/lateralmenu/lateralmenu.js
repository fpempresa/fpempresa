"user strict";

app.controller("LateralMenuController", ['$scope', '$state', '$location', 'dialog', function ($scope, $state, $location, dialog) {
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

        $scope.getUrlIfEmpresa=function(url) {
            if ( ($scope.user) && ($scope.user.empresa) ) {
                return url;
            } else {
                "";
            }
        };

        $scope.cambiarContrasenya = function () {
            if ( ($scope.user) && ($scope.user.empresa) ){
                dialog.create("cambiarContrasenya", $scope.user);
            }
        };

    }]);