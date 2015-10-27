"use strict";


app.controller("ProfesorSearchController", ['$scope', 'genericControllerCrudList', 'controllerParams', 'schemaEntities', function ($scope, genericControllerCrudList, controllerParams, schemaEntities) {
        genericControllerCrudList.extendScope($scope, controllerParams);
        $scope.prefixRoute="/profesor";
        $scope.page.pageSize = 20;

        $scope.filters['centro.idCentro']=$scope.user.centro.idCentro;

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


        $scope.cambiarContrasenya = function () {
            dialog.create('cambiarContrasenya', $scope.model);
        };

    }]);

app.controller("ProfesorViewController", ['$scope', 'genericControllerCrudDetail', 'controllerParams', function ($scope, genericControllerCrudDetail, controllerParams) {
        genericControllerCrudDetail.extendScope($scope, controllerParams);
    }]);
