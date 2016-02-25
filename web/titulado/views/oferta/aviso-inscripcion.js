(function (undefined) {
    "use strict";

    AvisoInscripcionController.$inject = ['$scope', 'currentDialog'];
    function AvisoInscripcionController($scope, currentDialog) {
        $scope.datosContacto = currentDialog.params.datosContacto;
        $scope.mensaje = currentDialog.params.mensaje;
        $scope.titulo = currentDialog.params.titulo;

        $scope.buttonCancel = function () {
            currentDialog.closeCancel();
        };       
        
        
        currentDialog.open({
            width: 600,
            height: 440,
            title: "Aviso"
        });        
    }

    angular.module("common").controller("AvisoInscripcionController", AvisoInscripcionController);


    angular.module("common").config(['dialogProvider', 'getContextPath', 'crudRoutesProvider', function (dialogProvider, getContextPath, crudRoutesProvider) {

            dialogProvider.when('avisoInscripcion', {
                templateUrl: getContextPath() + "/titulado/views/oferta/aviso-inscripcion.html",
                controller: 'AvisoInscripcionController'
            });

        }]);




})();