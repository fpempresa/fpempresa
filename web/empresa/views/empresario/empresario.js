"use strict";
app.config(['$stateProvider', 'crudRoutesProvider', function ($stateProvider, crudRoutesProvider) {

        crudRoutesProvider.addAllRoutes({
            entity: "Usuario",
            expand: "empresa",
            crudName:"empresario"
        });
        

    }]);

app.controller("EmpresarioSearchController", ['$scope', 'genericControllerCrudList', 'controllerParams', 'schemaEntities', function ($scope, genericControllerCrudList, controllerParams, schemaEntities) {
        genericControllerCrudList.extendScope($scope, controllerParams);
        $scope.page.pageSize = 20;

        $scope.filters['empresa.idEmpresa']=$scope.user.empresa.idEmpresa;
        $scope.filters['tipoUsuario']='EMPRESA';

        $scope.search();
    }]);


app.controller("EmpresarioNewEditController", ['$scope', 'genericControllerCrudDetail', 'controllerParams', 'dialog', function ($scope, genericControllerCrudDetail, controllerParams, dialog) {
        genericControllerCrudDetail.extendScope($scope, controllerParams);

        $scope.postCreate = function () {

        };

        $scope.preInsert = function () {
            $scope.model.empresa=$scope.user.empresa;
            $scope.model.tipoUsuario='EMPRESA';
            $scope.model.aceptarCondicionesPolitica = true;
            $scope.model.aceptarEnvioCorreos = true;
            $scope.model.aceptarVerificarTitulo = true;            
        };


        $scope.updateEstadoUsuario = function (estadoUsuario) {
            $scope.doUpdate().then(function () {

                $scope.service.updateEstadoUsuario($scope.model.idIdentity, estadoUsuario, $scope.expand).then(function (data) {
                    $scope.model = data;
                    $scope.businessMessages = null;
                }, function (businessMessages) {
                    $scope.businessMessages = businessMessages;
                });

            });
        };

        $scope.cambiarContrasenya = function () {
            dialog.create('cambiarContrasenya', $scope.model);
        };

    }]);

app.controller("EmpresarioViewController", ['$scope', 'genericControllerCrudDetail', 'controllerParams', function ($scope, genericControllerCrudDetail, controllerParams) {
        genericControllerCrudDetail.extendScope($scope, controllerParams);
    }]);

app.controller("EmpresarioDeleteController", ['$scope', 'genericControllerCrudDetail', 'controllerParams', function ($scope, genericControllerCrudDetail, controllerParams) {
        genericControllerCrudDetail.extendScope($scope, controllerParams);
    }]);