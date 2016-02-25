"user strict";

app.controller("LateralMenuController", ['$scope', '$location', '$state', 'dialog', 'ix3Configuration', function ($scope, $location, $state, dialog, ix3Configuration) {
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

        $scope.ix3Configuration=ix3Configuration;
        
        $scope.fotoIndex=0;
        $scope.updateFoto=function() {
            $scope.fotoIndex=$scope.fotoIndex+1;
        }
        $scope.failUpdateFoto=function() {
            alert("No ha sido posible subir la foto.\nPrueba con otra o hazla mas pequeña.")
        }

    }]);