"use strict";


app.config(['$stateProvider', 'crudRoutesProvider', function ($stateProvider, crudRoutesProvider) {
        $stateProvider.state('lateralmenu.oferta_search_todas', {
            url: "/oferta/search_todas",
            templateUrl: 'views/oferta/search.html',
            controller: 'OfertaTodasSearchController',
            resolve: crudRoutesProvider.getResolve("Oferta", "municipio,municipio.provincia,familia,empresa,ciclos,ciclos,fecha")
        });

        $stateProvider.state("lateralmenu.oferta_view_todas", {
            url: '/oferta/view_todas/:id',
            templateUrl: 'views/oferta/detail.html',
            controller: 'OfertaViewController',
            resolve: angular.extend(crudRoutesProvider.getResolve("Oferta", "municipio,municipio.provincia,familia,empresa,ciclos,ciclos,fecha", "VIEW"), {inscrito: function () {
                    return false;
                }})
        });

        $stateProvider.state('lateralmenu.oferta_search_inscrito', {
            url: "/oferta/search_inscrito",
            templateUrl: 'views/oferta/search.html',
            controller: 'OfertaInscritoSearchController',
            resolve: crudRoutesProvider.getResolve("Oferta", "municipio,municipio.provincia,familia,empresa,ciclos,ciclos,fecha")
        });

        $stateProvider.state("lateralmenu.oferta_view_inscrito", {
            url: '/oferta/view_inscrito/:id',
            templateUrl: 'views/oferta/detail.html',
            controller: 'OfertaViewController',
            resolve: angular.extend(crudRoutesProvider.getResolve("Oferta", "municipio,municipio.provincia,familia,empresa,ciclos,ciclos,fecha", "VIEW"), {inscrito: function () {
                    return true;
                }})
        });



    }]);


app.controller("OfertaTodasSearchController", ['$scope', 'genericControllerCrudList', 'controllerParams', 'dialog', '$location', function ($scope, genericControllerCrudList, controllerParams, dialog, $location) {
        genericControllerCrudList.extendScope($scope, controllerParams);

        $scope.namedSearch = "getOfertasUsuarioTitulado";
        $scope.filters.usuario = $scope.user.idIdentity;
        $scope.page.pageSize = 20;
        
        $scope.preSearch = function (filters) {
            if (filters.provincia) {
                filters.provincia = filters.provincia.idProvincia;
            }
        }

        $scope.buttonView = function (idOferta) {
            $location.path("/oferta/view_todas/" + idOferta).search({});
        };
        $scope.orderby = [
            {fieldName: "fecha", orderDirection: "DESC"}
        ];

        $scope.search();

    }]);


app.controller("OfertaInscritoSearchController", ['$scope', 'genericControllerCrudList', 'controllerParams', 'dialog', '$location', function ($scope, genericControllerCrudList, controllerParams, dialog, $location) {
        genericControllerCrudList.extendScope($scope, controllerParams);
        $scope.namedSearch = "getOfertasInscritoUsuarioTitulado";
        $scope.filters.usuario = $scope.user.idIdentity;
        $scope.preSearch = function (filters) {
            if (filters.provincia) {
                filters.provincia = filters.provincia.idProvincia;
            }
        };

        $scope.buttonView = function (idOferta) {
            $location.path("/oferta/view_inscrito/" + idOferta).search({});
        };

        $scope.search();
    }]);




app.controller("OfertaViewController", ['$scope', '$q', 'genericControllerCrudDetail', 'controllerParams', 'dialog', 'schemaEntities', 'serviceFactory', 'inscrito', function ($scope, $q, genericControllerCrudDetail, controllerParams, dialog, schemaEntities, serviceFactory, inscrito) {
        genericControllerCrudDetail.extendScope($scope, controllerParams);
        $scope.inscrito = inscrito;

        $scope.verEmpresa = function (id) {

            var params = {
                id: id
            };

            dialog.create('verEmpresa', params);
        };


        $scope.buttonInscribirseOferta = function () {

            if ($scope.model.empresa.centro) {
                var promise = dialog.create('avisoInscripcion', {
                    titulo: "Inscripción en la oferta",
                    mensaje: "Aunque estés inscrito en la oferta debes ponerte en contacto directamente con las empresa a través de los datos que se muestran a continuación para que la empresa puedar ver tus datos",
                    datosContacto: $scope.model.contacto.textoLibre
                });

                promise["finally"](function () {
                    $scope.inscribirseOferta();
                });
            } else {
                $scope.inscribirseOferta().then(function () {
                    alert("Te has inscrito correctamente en la oferta. La empresa recibirá tu curriculum");
                });
            }

        }

        $scope.inscribirseOferta = function () {
            var defered = $q.defer();

            var candidatoService = serviceFactory.getService("Candidato");
            var candidato = {
                oferta: $scope.model,
                usuario: $scope.user
            };
            candidatoService.insert(candidato).then(function (candidato) {
                $scope.inscrito = true;
                $scope.businessMessages = [];
                defered.resolve(candidato);
            }, function (businessMessages) {
                $scope.businessMessages = businessMessages;
                defered.reject(businessMessages);
            });

            return defered.promise;
        };


        $scope.buttonDesinscribirseOferta = function () {

            if ($scope.model.empresa.centro) {
                var promise = dialog.create('avisoInscripcion', {
                    titulo: "Desinscripción de la oferta",
                    mensaje: "Aunque te desinscribas de la oferta recuerda que tus datos los debistes de enviar tu directamente a la información de contacto que aparece en el siguiente campo.",
                    datosContacto: $scope.model.contacto.textoLibre
                });

                promise["finally"](function () {
                    $scope.desinscribirseOferta();
                });
            } else {
                $scope.desinscribirseOferta().then(function () {
                    alert("Te has borrado correctamente de la oferta. La empresa ya no podrá ver tu curriculum");
                });
            }


        }

        $scope.desinscribirseOferta = function () {
            var defered = $q.defer();

            var candidatoService = serviceFactory.getService("Candidato");
            var query = {
                filters: {
                    'oferta.idOferta': $scope.model.idOferta,
                    'usuario.idIdentity': $scope.user.idIdentity
                }
            }

            candidatoService.search(query).then(function (candidatos) {

                if (candidatos && candidatos.length === 1) {
                    candidatoService.delete(candidatos[0].idCandidato).then(function () {
                        $scope.inscrito = false;
                        $scope.businessMessages = [];

                        defered.resolve();
                    }, function (businessMessages) {
                        $scope.businessMessages = businessMessages;
                        defered.reject(businessMessages);
                    });
                } else {
                    var businessMessages = [{message: "No estabas inscrito en la oferta"}];
                    $scope.businessMessages = businessMessages;
                    defered.reject(businessMessages);
                }

            }, function (businessMessages) {
                $scope.businessMessages = businessMessages;
            });

            return defered.promise;
        };

    }]);



