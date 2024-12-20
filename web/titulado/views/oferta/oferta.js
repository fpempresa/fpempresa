/**
 *   FPempresa
 *   Copyright (C) 2020  Lorenzo González
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Affero General Public License as
 *   published by the Free Software Foundation, either version 3 of the
 *   License, or (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Affero General Public License for more details.
 *
 *   You should have received a copy of the GNU Affero General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
"use strict";


app.config(['$stateProvider', 'crudRoutesProvider', function ($stateProvider, crudRoutesProvider) {
        $stateProvider.state('lateralmenu.oferta_search_todas', {
            url: "/oferta/search_todas",
            templateUrl: 'views/oferta/search.html',
            controller: 'OfertaTodasSearchController',
            resolve: crudRoutesProvider.getResolve("Oferta", "municipio,municipio.provincia,familia,empresa.direccion.municipio.provincia,ciclos,ciclos,fecha")
        });

        $stateProvider.state("lateralmenu.oferta_view_todas", {
            url: '/oferta/view_todas/:id',
            templateUrl: 'views/oferta/detail.html',
            controller: 'OfertaViewController',
            resolve: angular.extend(crudRoutesProvider.getResolve("Oferta", "municipio,municipio.provincia,familia,empresa.direccion.municipio.provincia,ciclos,ciclos,fecha", "VIEW"), {inscrito: function () {
                    return false;
                }})
        });

        $stateProvider.state('lateralmenu.oferta_search_inscrito', {
            url: "/oferta/search_inscrito",
            templateUrl: 'views/oferta/search.html',
            controller: 'OfertaInscritoSearchController',
            resolve: crudRoutesProvider.getResolve("Oferta", "municipio,municipio.provincia,familia,empresa.direccion.municipio.provincia,ciclos,ciclos,fecha")
        });

        $stateProvider.state("lateralmenu.oferta_view_inscrito", {
            url: '/oferta/view_inscrito/:id',
            templateUrl: 'views/oferta/detail.html',
            controller: 'OfertaViewController',
            resolve: angular.extend(crudRoutesProvider.getResolve("Oferta", "municipio,municipio.provincia,familia,empresa.direccion.municipio.provincia,ciclos,ciclos,fecha", "VIEW"), {inscrito: function () {
                    return true;
                }})
        });



    }]);


app.controller("OfertaTodasSearchController", ['$scope', 'genericControllerCrudList', 'controllerParams', 'dialog', '$location', 'session',function ($scope, genericControllerCrudList, controllerParams, dialog, $location, session) {
        genericControllerCrudList.extendScope($scope, controllerParams);
        $scope.inscrito = false;
        $scope.namedSearch = "getOfertasUsuarioTitulado";
        $scope.filters.usuario = $scope.user.idIdentity;
        $scope.filters.fechaInicio=moment().subtract(180,"days").toDate();
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
           
        ];

        $scope.search();
        
        
        function hasAnyCicloFP(formacionesAcademicas) {
            for (var i=0;i<formacionesAcademicas.length;i++) {
                if (formacionesAcademicas[i].tipoFormacionAcademica==="CICLO_FORMATIVO") {
                    return true;
                }
            }
            
            return false;
        }
        
        $scope.hasAnyCicloFP=hasAnyCicloFP(session.getUser().titulado.formacionesAcademicas);

    }]);


app.controller("OfertaInscritoSearchController", ['$scope', 'genericControllerCrudList', 'controllerParams', 'dialog', '$location', function ($scope, genericControllerCrudList, controllerParams, dialog, $location) {
        genericControllerCrudList.extendScope($scope, controllerParams);
        $scope.inscrito = true;
        $scope.namedSearch = "getOfertasInscritoUsuarioTitulado";
        $scope.filters.usuario = $scope.user.idIdentity;
        $scope.filters.fechaInicio=moment().subtract(180,"days").toDate();
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

        $scope.verEmpresa = function (empresa) {
            var params = {
                empresa: empresa
            };

            dialog.create('verEmpresa', params);
        };


        $scope.buttonInscribirseOferta = function () {

            if ($scope.model.empresa.centro) {
                var promise = dialog.create('avisoInscripcion', {
                    datosContacto: $scope.model.contacto.textoLibre
                });

                promise.then(function () {
                    $scope.inscribirseOferta();
                });
            } else {
                $scope.inscribirseOferta().then(function () {
                    alert("Te has inscrito correctamente en la oferta. La empresa ha recibido un correo electrónico con tu curriculum");
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
                var promise = dialog.create('avisoDesinscripcion', {
                    datosContacto: $scope.model.contacto.textoLibre
                });

                promise.then(function () {
                    $scope.desinscribirseOferta();
                });
            } else {
                $scope.desinscribirseOferta().then(function () {
                    alert("Te has desinscrito correctamente de la oferta");
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



