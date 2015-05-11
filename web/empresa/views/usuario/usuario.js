"use strict";
app.config(['$stateProvider', 'crudRoutesProvider', function ($stateProvider, crudRoutesProvider) {

        $stateProvider.state('lateralmenu.usuario_search_', {
            url: "/usuario/search",
            templateUrl: 'views/usuario/search.html',
            controller: 'UsuarioSearchController',
            resolve: crudRoutesProvider.getResolve("Usuario","empresa") 
        });
        
        $stateProvider.state('lateralmenu.usuario_edit_', {
            url: "/usuario/edit/:id",
            templateUrl: 'views/usuario/detail.html',
            controller: 'UsuarioNewEditController',
            resolve: crudRoutesProvider.getResolve("Usuario","empresa","EDIT")
        }); 

    }]);

app.controller("UsuarioSearchController", ['$scope', 'genericControllerCrudList', 'controllerParams', 'metadataEntities', function ($scope, genericControllerCrudList, controllerParams, metadataEntities) {
        genericControllerCrudList.extendScope($scope, controllerParams);
        $scope.page.pageSize = 20;

        $scope.filters['empresa.idEmpresa']=$scope.user.empresa.idEmpresa;

        $scope.search();
    }]);


app.controller("UsuarioNewEditController", ['$scope', 'genericControllerCrudDetail', 'controllerParams', 'dialog', function ($scope, genericControllerCrudDetail, controllerParams, dialog) {
        genericControllerCrudDetail.extendScope($scope, controllerParams);

        $scope.postCreate = function () {
            //Cuando los crea el administrador siempre están aceptados por defecto
            $scope.model.estadoUsuario = "ACEPTADO";
        }

        $scope.preInsert = function () {
            //Al insertar un usuario siempre debe aceptar las politicas
            //Lo hacemos aqui pq al ser el administrador el que los crea suponemos que el usuario las ha aceptado
            $scope.model.aceptarCondicionesPolitica = true;
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

app.controller("UsuarioViewController", ['$scope', 'genericControllerCrudDetail', 'controllerParams', function ($scope, genericControllerCrudDetail, controllerParams) {
        genericControllerCrudDetail.extendScope($scope, controllerParams);
    }]);

app.controller("UsuarioDeleteController", ['$scope', 'genericControllerCrudDetail', 'controllerParams', function ($scope, genericControllerCrudDetail, controllerParams) {
        genericControllerCrudDetail.extendScope($scope, controllerParams);
    }]);