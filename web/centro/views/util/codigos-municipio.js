(function (undefined) {
    "use strict";

    MostrarCodigosMunicipioDialogController.$inject = ['$scope', 'serviceFactory',  'currentDialog'];
    function MostrarCodigosMunicipioDialogController($scope, serviceFactory, currentDialog) {
        $scope.businessMessages = [];

        currentDialog.open({
            width: 1300,
            height: 'auto',
            title: "Codigos municipio"
        });

        $scope.buttonCancel = function () {
            currentDialog.closeCancel();
        };

        $scope.buttonOK = function () {
            currentDialog.closeOK();
        };

    }

    angular.module("app").controller("MostrarCodigosMunicipioDialogController", MostrarCodigosMunicipioDialogController);

    angular.module("app").config(['dialogProvider', 'getContextPath', function (dialogProvider, getContextPath) {

            dialogProvider.when('mostrarCodigosMunicipio', {
                templateUrl: getContextPath() + "/centro/views/util/codigos-municipio.html",
                controller: 'MostrarCodigosMunicipioDialogController'
            });

        }]);

})();