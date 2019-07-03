"use strict";


app.controller("ProfesorSearchController", ['$scope', 'genericControllerCrudList', 'controllerParams', 'schemaEntities', function ($scope, genericControllerCrudList, controllerParams, schemaEntities) {
        genericControllerCrudList.extendScope($scope, controllerParams);
        $scope.page.pageSize = 20;

        $scope.filters['centro.idCentro']=$scope.user.centro.idCentro;
        $scope.filters['tipoUsuario']="CENTRO";

        $scope.updateEstadoUsuario = function (idIdentity, estadoUsuario) {
            $scope.service.updateEstadoUsuario(idIdentity, estadoUsuario).then(function (data) {
                $scope.search();
            }, function (businessMessages) {
                $scope.businessMessages = businessMessages;
            });
        }

        $scope.search();
    }]);


app.controller("ProfesorNewEditController", ['$scope', 'genericControllerCrudDetail', 'controllerParams', 'dialog', function ($scope, genericControllerCrudDetail, controllerParams, dialog) {
        genericControllerCrudDetail.extendScope($scope, controllerParams);

        $scope.postCreate = function () {
            //Cuando los crea el administrador siempre están aceptados por defecto
            $scope.model.estadoUsuario = "ACEPTADO";
            $scope.model.tipoUsuario="CENTRO"; 
            $scope.model.centro=$scope.user.centro;
        }

        $scope.preInsert = function () {
            //Al insertar un usuario siempre debe aceptar las politicas
            $scope.model.aceptarCondicionesPolitica = true;
            $scope.model.aceptarEnvioCorreos = true;
            $scope.model.aceptarVerificarTitulo = true;
        };
        
        $scope.cambiarContrasenya = function () {
            dialog.create('cambiarContrasenya', $scope.model);
        };

    }]);

app.controller("ProfesorViewController", ['$scope', 'genericControllerCrudDetail', 'controllerParams', function ($scope, genericControllerCrudDetail, controllerParams) {
        genericControllerCrudDetail.extendScope($scope, controllerParams);
    }]);
app.controller("ProfesorDeleteController", ['$scope', 'genericControllerCrudDetail', 'controllerParams', function ($scope, genericControllerCrudDetail, controllerParams) {
        genericControllerCrudDetail.extendScope($scope, controllerParams);
    }]);