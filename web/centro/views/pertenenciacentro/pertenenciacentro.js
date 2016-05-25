

app.controller("PertenenciaCentroController", ['$scope', '$q', 'controllerParams', 'genericControllerCrudList', 'serviceFactory', 'goPage', 'session', function ($scope, $q, controllerParams, genericControllerCrudList, serviceFactory, goPage, session) {
        genericControllerCrudList.extendScope($scope, controllerParams);
        $scope.page.pageSize = 20;

        $scope.filters.$gt.idCentro = 0;
        if ($scope.user && $scope.user.centro) {
            $scope.filters.$ne.idCentro = $scope.user.centro.idCentro; //No debe salir el propio centro al que pertenece.
        }
        $scope.filters.estadoCentro = "PERTENECE_A_FPEMPRESA";

        $scope.buttonPertenenciaCentro = function (centro) {
            $scope.pertenenciaCentro(centro);
        };
        $scope.pertenenciaCentro = function (centro) {
            var ok = confirm("¿Realmente quieres cambiar al centro  '" + centro.toString() + "'?.\nNo podras volver a entrar hasta que acepten o rechacen tu solicitud");

            if (ok === true) {
                var usuarioService = serviceFactory.getService("Usuario");
                usuarioService.get($scope.user.idIdentity).then(function (usuario) {
                    return usuarioService.updateCentro(usuario.idIdentity, centro);
                }).then(function () {
                    return session.logout();
                }).then(function () {
                    goPage.homeApp();
                }).catch(function (businessMessages) {
                    $scope.businessMessages = businessMessages;
                });

            };
        };
        $scope.search();

    }]);