"use strict";


app.controller("ProfesorSearchController", ['$scope', 'genericControllerCrudList', 'controllerParams', 'metadataEntities', function ($scope, genericControllerCrudList, controllerParams, metadataEntities) {
        genericControllerCrudList.extendScope($scope, controllerParams);
        $scope.prefixRoute="/profesor";
        $scope.page.pageSize = 20;
        $scope.orderby = [
            {fieldName: 'nombre', orderDirection: 'ASC'},
            {fieldName: 'ape1', orderDirection: 'ASC'},
            {fieldName: 'ape2', orderDirection: 'ASC'}
        ];


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
            dialog.create($scope.getContextPath() + "/common/presentation/view/util/cambiarcontrasenya.html", $scope.model);
        };

    }]);

app.controller("ProfesorViewController", ['$scope', 'genericControllerCrudDetail', 'controllerParams', function ($scope, genericControllerCrudDetail, controllerParams) {
        genericControllerCrudDetail.extendScope($scope, controllerParams);
    }]);
