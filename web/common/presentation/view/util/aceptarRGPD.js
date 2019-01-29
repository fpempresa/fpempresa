(function (undefined) {
    "use strict";

    AceptarRGPDController.$inject = ['$scope', 'serviceFactory', 'formValidator','currentDialog'];
    function AceptarRGPDController($scope, serviceFactory, formValidator,currentDialog) {
        $scope.businessMessages = [];
        $scope.usuario=currentDialog.params;
        
        currentDialog.open({
            width: 550,
            height: 'auto',
            title: "Aceptar RGPD"
        });



        $scope.buttonCancel = function () {
            currentDialog.closeCancel();
        };

        $scope.buttonOK = function () {
            var usuarioService = serviceFactory.getService("Usuario");

            $scope.businessMessages = formValidator.validate($scope.mainForm, $scope.$validators);
            if ($scope.businessMessages.length === 0) {
                usuarioService.aceptarRGPD($scope.user.idIdentity).then(function () {
                    currentDialog.closeOK();
                }, function (businessMessages) {
                    $scope.businessMessages = businessMessages;
                });
            }
        };


        $scope.$validators = [
        ]

    }

    angular.module("common").controller("AceptarRGPDController", AceptarRGPDController);

    angular.module("common").config(['dialogProvider', 'getContextPath', function (dialogProvider, getContextPath) {
            
        dialogProvider.when('aceptarRGPD', {
            templateUrl: getContextPath() + "/common/presentation/view/util/aceptarRGPD.html",
            controller: 'AceptarRGPDController'
        });
                    
    }]);

})();