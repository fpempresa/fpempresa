"use strict";
app.config(['crudRoutesProvider', function (crudRoutesProvider) {
        crudRoutesProvider.addAllRoutes({
            entity: "Usuario",
            expand: "centro.direccion.municipio.provincia,empresa.direccion.municipio.provincia"
        });
    }]);

app.controller("UsuarioSearchController", ['$scope', 'genericControllerCrudList', 'controllerParams', 'metadataEntities', function ($scope, genericControllerCrudList, controllerParams, metadataEntities) {
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
            email__LIKE__: null
        };

        $scope.updateEstadoUsuario = function (idIdentity, estadoUsuario) {
            $scope.repository.updateEstadoUsuario(idIdentity, estadoUsuario).then(function (data) {
                $scope.search();
            }, function (businessMessages) {
                $scope.businessMessages = businessMessages;
            });
        }


        if ($scope.parentProperty === "tipoUsuario") {
            $scope.tipoUsuarioDescription = "de tipo \"" + metadataEntities.getMetadataProperty("Usuario.tipoUsuario").getValueDescription($scope.parentId) + "\"";
        } else {
            $scope.tipoUsuarioDescription = "";
        }



        $scope.search();
    }]);


app.controller("UsuarioNewEditController", ['$scope', 'genericControllerCrudDetail', 'controllerParams','dialog', function ($scope, genericControllerCrudDetail, controllerParams,dialog) {
        genericControllerCrudDetail.extendScope($scope, controllerParams);

        $scope.postCreate=function() {
            //Cuando los crea el administrador siempre están aceptados por defecto
            $scope.model.estadoUsuario="ACEPTADO";          
        }

        $scope.preInsert=function() {
            //Al insertar un usuario siempre debe aceptar las politicas
            //Lo hacemos aqui pq al ser el administrador el que los crea suponemos que el usuario las ha aceptado
            $scope.model.aceptarCondicionesPolitica=true; 
        };


        $scope.updateEstadoUsuario = function (estadoUsuario) {
            $scope.doUpdate().then(function () {

                $scope.repository.updateEstadoUsuario($scope.model.idIdentity, estadoUsuario, $scope.expand).then(function (data) {
                    $scope.model = data;
                    $scope.businessMessages = null;
                }, function (businessMessages) {
                    $scope.businessMessages = businessMessages;
                });

            });
        };
        
        $scope.cambiarContrasenya=function() {
            dialog.create($scope.getContextPath()+"/common/views/util/cambiarcontrasenya.html",$scope.model);
        };

    }]);

app.controller("UsuarioViewController", ['$scope', 'genericControllerCrudDetail', 'controllerParams', function ($scope, genericControllerCrudDetail, controllerParams) {
        genericControllerCrudDetail.extendScope($scope, controllerParams);
    }]);

app.controller("UsuarioDeleteController", ['$scope', 'genericControllerCrudDetail', 'controllerParams', function ($scope, genericControllerCrudDetail, controllerParams) {
        genericControllerCrudDetail.extendScope($scope, controllerParams);
    }]);