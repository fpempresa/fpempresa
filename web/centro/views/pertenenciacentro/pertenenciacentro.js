

app.controller("PertenenciaCentroController", ['$scope', '$q', 'controllerParams', 'genericControllerCrudList', 'serviceFactory', 'goPage', 'session', function ($scope, $q, controllerParams, genericControllerCrudList, serviceFactory, goPage, session) {
        genericControllerCrudList.extendScope($scope, controllerParams);
        $scope.page.pageSize = 20;

        $scope.filters.$gt.idCentro = 0;
        $scope.filters.$ne.idCentro = $scope.user.centro.idCentro; //No debe salir el propio centro al que pertenece.
        $scope.filters.estadoCentro = "PERTENECE_A_FPEMPRESA";

        $scope.buttonPertenenciaCentro = function (idCentro) {
            $scope.pertenenciaCentro(idCentro);
        };
        $scope.pertenenciaCentro = function (idCentro) {
            var usuarioService = serviceFactory.getService("Usuario");

            var promise = $scope.service.get(idCentro);
            promise.then(function (centro) {
                return centro;
            }).then(function (centro) {

                var ok = confirm("¿Realmente quieres cambiar al centro  '" + centro.toString() + "'?.\nNo podras volver a entrar hasta que acepten o rechacen tu solicitud");
                
                if (ok === true) {
                    return  usuarioService.get($scope.user.idIdentity);
                } else {
                    //Tenemos que cancelar la promesa para que se ejecute el catch!!
                    return $q.reject([]);
                }
            }).then(function (usuario) {
                usuario.centro = {
                    idCentro: idCentro
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

        $scope.search();

    }]);