"use strict";
app.config(['crudRoutesProvider', function (crudRoutesProvider) {
        crudRoutesProvider.addAllRoutes({
            entity: "Usuario",
            expand: "centro.direccion.municipio.provincia,empresa.direccion.municipio.provincia"
        });
    }]);

app.controller("UsuarioSearchController", ['$scope', 'genericControllerCrudList', 'controllerParams', function ($scope, genericControllerCrudList, controllerParams) {
        genericControllerCrudList.extendScope($scope, controllerParams);
        $scope.page.pageSize = 20;
        $scope.orderby = [
            {fieldName: 'nombre', orderDirection: 'ASC'},
            {fieldName: 'ape1', orderDirection: 'ASC'},
            {fieldName: 'ape2', orderDirection: 'ASC'}
        ];

        $scope.filter = {
            nombre__LIKE__: null,
            ape1__LIKE__: null,
            ape2__LIKE__: null,
            email__LIKE__: null,
            tipoUsuario:['CENTRO','ADMINISTRADOR']
        };

        $scope.updateEstadoUsuario = function (idIdentity, estadoUsuario) {
            $scope.repository.updateEstadoUsuario(idIdentity, estadoUsuario).then(function (data) {
                $scope.search();
            }, function (businessMessages) {
                $scope.businessMessages = businessMessages;
            });
        }

        $scope.search();
    }]);


app.controller("UsuarioNewEditController", ['$scope', 'genericControllerCrudDetail', 'controllerParams', function ($scope, genericControllerCrudDetail, controllerParams) {
        genericControllerCrudDetail.extendScope($scope, controllerParams);

        $scope.updateEstadoUsuario = function (estadoUsuario) {
            $scope.doUpdate().then(function () {

                $scope.repository.updateEstadoUsuario($scope.model.idIdentity, estadoUsuario, $scope.expand).then(function (data) {
                    $scope.model = data;
                    $scope.businessMessages = null;
                }, function (businessMessages) {
                    $scope.businessMessages = businessMessages;
                });

            });
        }

    }]);

app.controller("UsuarioViewController", ['$scope', 'genericControllerCrudDetail', 'controllerParams', function ($scope, genericControllerCrudDetail, controllerParams) {
        genericControllerCrudDetail.extendScope($scope, controllerParams);
    }]);

app.controller("UsuarioDeleteController", ['$scope', 'genericControllerCrudDetail', 'controllerParams', function ($scope, genericControllerCrudDetail, controllerParams) {
        genericControllerCrudDetail.extendScope($scope, controllerParams);
    }]);