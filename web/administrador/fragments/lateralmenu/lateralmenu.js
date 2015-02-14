"user strict";

app.controller("LateralMenuController", ['$scope', '$state', function ($scope, $state) {
        $scope.isItemSelected = function (option) {
            if (option) {
                if ($state.current.name.indexOf(option) >= 0) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        };

    }]);