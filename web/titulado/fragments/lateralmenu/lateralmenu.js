"user strict";

app.controller("LateralMenuController", ['$scope', '$state','dialog', function ($scope, $state, dialog) {
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
        
        
        $scope.cambiarContrasenya=function() {
            dialog.create($scope.getContextPath()+"/common/presentation/view/util/cambiarcontrasenya.html",$scope.user);
        }

    }]);