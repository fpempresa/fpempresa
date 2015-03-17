

app.controller("PertenenciaCentroController", ['$scope', '$q', 'genericControllerCrudList', 'serviceFactory', 'goPage', 'session', function ($scope, $q, genericControllerCrudList, serviceFactory, goPage, session) {
        var controllerParams = {
            entity: "Centro",
            expand: "direccion.municipio.provincia"
        };

        genericControllerCrudList.extendScope($scope, controllerParams);
        $scope.page.pageSize = 20;
        $scope.orderby = [
            {fieldName: 'nombre', orderDirection: 'ASC'},
            {fieldName: 'direccion.municipio.provincia.descripcion', orderDirection: 'ASC'},
            {fieldName: 'direccion.municipio.descripcion', orderDirection: 'ASC'}
        ];

        $scope.filters.$gt.idCentro = 0;
        $scope.filters.estadoCentro = "PERTENECE_A_FPEMPRESA";

        $scope.search();

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



    }]);