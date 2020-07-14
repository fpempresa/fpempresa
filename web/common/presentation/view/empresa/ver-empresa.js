(function (undefined) {
    "use strict";

    VerEmpresaController.$inject = ['$scope', 'genericControllerCrudDetail', 'currentDialog', 'controllerParams', '$q', '$timeout'];
    function VerEmpresaController($scope, genericControllerCrudDetail, currentDialog, controllerParams, $q, $timeout) {
        controllerParams.get =function() {
            var defered = $q.defer();

            $timeout(function() {
                var data=currentDialog.params.empresa;
                defered.resolve(data);
            },1);

            return defered.promise;
        };
        
        
        genericControllerCrudDetail.extendScope($scope, controllerParams);


        currentDialog.open({
            width: 850,
            title: "Empresa"
        });

        $scope.finishOK = function () {
            currentDialog.closeCancel();
        };

    }

    angular.module("common").controller("VerEmpresaController", VerEmpresaController);


    angular.module("common").config(['dialogProvider', 'getContextPath', 'crudRoutesProvider', function (dialogProvider, getContextPath, crudRoutesProvider) {

            dialogProvider.when('verEmpresa', {
                templateUrl: getContextPath() + "/common/presentation/view/empresa/ver-empresa.html",
                controller: 'VerEmpresaController',
                resolve: crudRoutesProvider.getResolve('Empresa', 'direccion.municipio,direccion.municipio.provincia', 'VIEW')
            });

        }]);




})();