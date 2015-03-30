(function (undefined) {
    "use strict";

    BuscarEmpresaController.$inject = ['$scope', 'genericControllerCrudList', 'currentDialog', 'controllerParams'];
    function BuscarEmpresaController($scope, genericControllerCrudList, currentDialog, controllerParams) {
        genericControllerCrudList.extendScope($scope, controllerParams);
        $scope.page.pageSize = 10;
        angular.extend($scope.filters, currentDialog.params);

        currentDialog.open({
            width: 600,
            height: 800,
            title: "Buscar empresa"
        });



        $scope.buttonCancel = function () {
            currentDialog.closeCancel();
        };

        $scope.buttonSeleccionar = function (idEmpresa) {
            currentDialog.closeOK(idEmpresa);
        };

    }

    angular.module("common").controller("BuscarEmpresaController", BuscarEmpresaController);


    angular.module("common").config(['dialogProvider', 'getContextPath', 'crudRoutesProvider', function (dialogProvider, getContextPath, crudRoutesProvider) {
            
        dialogProvider.when('buscarEmpresas', {
            templateUrl: getContextPath() + "/common/presentation/view/empresa/buscarempresa.html",
            controller: 'BuscarEmpresaController',
            resolve: crudRoutesProvider.getResolve('Empresa', 'direccion.municipio,direccion.municipio.provincia')
        });
                    
    }]);




})();