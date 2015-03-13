"use strict";

angular.lazy.controller("LoginController", ['$scope', 'dialog', '$timeout', function($scope, dialog, $timeout) {

        $scope.dialog.open({
            title: "Nueva Ventana:" + Math.random()
        });

        $scope.recibidoPrevia = $scope.dialog.data;





        $scope.newDialog = function() {
            dialog.create(getContextPath() + "/test/modalmemory/modal.html", 10).then(function(resultado) {
                if (resultado === 20) {
                    $scope.newDialog();
                }
            });
        };

        if ($scope.recibidoPrevia === 10) {
            $timeout(function(){$scope.dialog.closeOK(20)}, 2000)
        } else {
            $scope.newDialog();
        }

    }]);