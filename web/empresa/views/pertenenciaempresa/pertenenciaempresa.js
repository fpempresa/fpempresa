

app.controller("PertenenciaEmpresaController", ['$scope', '$q', '$location', 'controllerParams', 'genericControllerCrudList', 'serviceFactory', 'goPage', 'session', function ($scope, $q, $location, controllerParams, genericControllerCrudList, serviceFactory, goPage, session) {
        genericControllerCrudList.extendScope($scope, controllerParams);
        $scope.page.pageSize = 20;

        if ($scope.user && $scope.user.empresa) {
            $scope.filters.$ne.idEmpresa = $scope.user.empresa.idEmpresa; //No debe salir la propia empresa a la que pertenece.
        }
        $scope.filters.$isnull.centro=true;

        $scope.buttonPertenenciaEmpresa = function (idEmpresa) {
            $scope.pertenenciaEmpresa(idEmpresa);
        };
        $scope.pertenenciaEmpresa = function (idEmpresa) {
            var usuarioService = serviceFactory.getService("Usuario");

            var promise = $scope.service.get(idEmpresa);
            promise.then(function (empresa) {
                return empresa;
            }).then(function (empresa) {

                var ok = confirm("¿Realmente quieres cambiar la empresa  '" + empresa.toString() + "'?.\nNo podras volver a entrar hasta que acepten o rechacen tu solicitud");
                
                if (ok === true) {
                    return  usuarioService.get($scope.user.idIdentity);
                } else {
                    //Tenemos que cancelar la promesa para que se ejecute el catch!!
                    return $q.reject([]);
                }
            }).then(function (usuario) {
                usuario.empresa = {
                    idEmpresa: idEmpresa
                };                
                return usuarioService.update(usuario.idIdentity, usuario);
            }).then(function () {
                return session.logout();
            }).then(function () {
                goPage.homeApp();               
            }).catch(function (businessMessages) {
                $scope.businessMessages = businessMessages;
            });
        };
        
        
        $scope.crearNuevaEmpresa=function() {
            $location.url("/empresa/new");
        };

        $scope.search();

    }]);