"user strict";

app.controller("LateralMenuController", ['$scope', '$state','$location', function ($scope, $state,$location) {
        $scope.isItemSelected = function (option) {
            if (option) {
                var regExp=new RegExp(option);
                if (regExp.test($location.url()) === true) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        };

    }]);