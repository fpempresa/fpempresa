"use strict";


app.config(['$stateProvider', 'crudRoutesProvider', function ($stateProvider, crudRoutesProvider) {
        $stateProvider.state('lateralmenu.oferta_search_todas', {
            url: "/oferta/search_todas",
            templateUrl: 'views/oferta/search.html',
            controller: 'OfertaTodasSearchController',
            resolve: crudRoutesProvider.getResolve("Oferta", "municipio,municipio.provincia,familia,empresa,ciclos,ciclos")
        });

        $stateProvider.state("lateralmenu.oferta_view_todas", {
            url: '/oferta/view_todas/:id',
            templateUrl: 'views/oferta/detail.html',
            controller: 'OfertaViewController',
            resolve: angular.extend(crudRoutesProvider.getResolve("Oferta", "municipio,municipio.provincia,familia,empresa,ciclos,ciclos", "VIEW"), {inscrito: function () {
                    return false;
                }})
        });

        $stateProvider.state('lateralmenu.oferta_search_inscrito', {
            url: "/oferta/search_inscrito",
            templateUrl: 'views/oferta/search.html',
            controller: 'OfertaInscritoSearchController',
            resolve: crudRoutesProvider.getResolve("Oferta", "municipio,municipio.provincia,familia,empresa,ciclos,ciclos")
        });

        $stateProvider.state("lateralmenu.oferta_view_inscrito", {
            url: '/oferta/view_inscrito/:id',
            templateUrl: 'views/oferta/detail.html',
            controller: 'OfertaViewController',
            resolve: angular.extend(crudRoutesProvider.getResolve("Oferta", "municipio,municipio.provincia,familia,empresa,ciclos,ciclos", "VIEW"), {inscrito: function () {
                    return true;
                }})
        });



    }]);


app.controller("OfertaTodasSearchController", ['$scope', 'genericControllerCrudList', 'controllerParams', 'dialog', '$location', function ($scope, genericControllerCrudList, controllerParams, dialog, $location) {
        genericControllerCrudList.extendScope($scope, controllerParams);
        $scope.namedSearch = "getOfertasUsuarioTitulado";
        $scope.namedSearchParameters.usuario = $scope.user.idIdentity;

        $scope.buttonView = function (idOferta) {
            $location.path("/oferta/view_todas/" + idOferta).search({});
        };

        $scope.search();
    }]);

app.controller("OfertaInscritoSearchController", ['$scope', 'genericControllerCrudList', 'controllerParams', 'dialog', '$location', function ($scope, genericControllerCrudList, controllerParams, dialog, $location) {
        genericControllerCrudList.extendScope($scope, controllerParams);
        $scope.namedSearch = "getOfertasInscritoUsuarioTitulado";
        $scope.namedSearchParameters.usuario = $scope.user.idIdentity;

        $scope.buttonView = function (idOferta) {
            $location.path("/oferta/view_inscrito/" + idOferta).search({});
        };

        $scope.search();
    }]);




app.controller("OfertaViewController", ['$scope', 'genericControllerCrudDetail', 'controllerParams', 'dialog', 'metadataEntities', 'serviceFactory', 'inscrito', function ($scope, genericControllerCrudDetail, controllerParams, dialog, metadataEntities, serviceFactory, inscrito) {
        genericControllerCrudDetail.extendScope($scope, controllerParams);
        $scope.inscrito = inscrito;

        $scope.inscribirseOferta = function () {
            var candidatoService = serviceFactory.getService("Candidato");

            var candidato = {
                oferta: $scope.model,
                usuario: $scope.user
            };


            candidatoService.insert(candidato).then(function (candidato) {
                $scope.inscrito = true;
                $scope.businessMessages = [];
                alert("Se ha inscrito correctamente en la oferta");
            }, function (businessMessages) {
                $scope.businessMessages = businessMessages;
            });
        };

        $scope.desinscribirseOferta = function () {
            var candidatoService = serviceFactory.getService("Candidato");
            var query = {
                filter: {
                    'oferta.idOferta': $scope.model.idOferta,
                    'usuario.idIdentity': $scope.user.idIdentity
                }
            }

            candidatoService.search(query).then(function (candidatos) {

                if (candidatos && candidatos.length === 1) {
                    candidatoService.delete(candidatos[0].idCandidato).then(function () {
                        $scope.inscrito = false;
                        $scope.businessMessages = [];
                        alert("Te has borrado correctamente de la oferta");
                    }, function (businessMessages) {
                        $scope.businessMessages = businessMessages;
                    });
                } else {
                    $scope.businessMessages = [{message: "No estabas inscrito en la oferta"}];
                }

            }, function (businessMessages) {
                $scope.businessMessages = businessMessages;
            });


        };

    }]);



