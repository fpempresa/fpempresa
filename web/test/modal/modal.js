"use strict";

angular.lazy.controller("LoginController", ['$scope', 'dialog', function($scope, dialog) {

        $scope.dialog.open({
            title:"Nueva Ventana:" + Math.random()
        });
        
        $scope.recibidoPrevia=$scope.dialog.data;

        $scope.devolver = function() {
                $scope.dialog.closeOK($scope.devuelto);
        };

        $scope.cancel = function() {
            $scope.dialog.closeCancel(undefined);
        };

        $scope.newDialog = function() {
            dialog.create(getContextPath()+"/test/modal/modal.html",$scope.enviado).then(function(resultado){
                if (resultado) {
                    $scope.recibidoSiguiente=resultado;
                }
            });
        };

    }]);