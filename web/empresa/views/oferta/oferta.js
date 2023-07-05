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
app.config(['crudRoutesProvider', function (crudRoutesProvider) {
        crudRoutesProvider.addAllRoutes({
            entity: "Oferta",
            expand: "municipio,municipio.provincia,familia,empresa,ciclos,ciclos"
        });
    }]);

app.controller("OfertaSearchController", ['$scope', 'genericControllerCrudList', 'controllerParams', '$location', function ($scope, genericControllerCrudList, controllerParams, $location) {
        genericControllerCrudList.extendScope($scope, controllerParams);
        
        $scope.page.pageSize = 20;
        $scope.orderby = [
            {fieldName: "fecha", orderDirection: "DESC"}
        ];
        $scope.filters['empresa.idEmpresa'] = $scope.user.empresa.idEmpresa;

        $scope.search();
    }]);


app.controller("OfertaNewEditController", ['$scope', 'genericControllerCrudDetail', 'controllerParams', '$location', 'schemaEntities', 'serviceFactory', 'session', 'dialog', function ($scope, genericControllerCrudDetail, controllerParams, $location, schemaEntities, serviceFactory, session, dialog) {
        genericControllerCrudDetail.extendScope($scope, controllerParams);
        
        $scope.numeroCandidatos=0;
        $scope.filters = {
            $eq: {},
            $ne: {},
            $gt: {},
            $ge: {},
            $lt: {},
            $le: {},
            $like: {},
            $llike: {},
            $liker: {},
            $lliker: {}
        };
        $scope.orderby = [];

        $scope.page = {
            pageNumber: 0,
            pageSize: 10
        }


        $scope.postGet = function () {
            $scope.search();
        };

        $scope.search = function () {

            var query = {
                filters: {
                    'oferta':$scope.model.idOferta
                },
                namedSearch:"getNumCandidatosOferta"
            }

            serviceFactory.getService("Candidato").search(query).then(function (data) {
                $scope.numeroCandidatos = data;
            }, function (businessMessages) {
                $scope.businessMessages = businessMessages;
            });
        }



        $scope.postCreate = function () {
            $scope.model.empresa = session.getUser().empresa;
            var municipio=angular.copy(session.getUser().empresa.direccion.municipio);            
            $scope.model.municipio = municipio;            
        };

        $scope.postInsert = function() {
            sweetAlert({
                title: "La oferta ha sido creada con éxito",
                text: "Se ha enviado un correo con los datos de esta oferta a los titulados",
                type: 'success',
                confirmButtonText: 'Aceptar',
                confirmButtonColor: '#005594'
            }, function () {
            });
        }


        $scope.checkTodosCiclos = function (todosCiclos) {

            if (todosCiclos) {
                $scope.model.ciclos = angular.copy($scope.ciclos);
            } else {
                $scope.model.ciclos = [];
            }

        };
        
        
        
        function existsCiclo(ciclos,ciclo) {
            for(var i=0;i<ciclos.length;i++) {
                if ($scope.compareCiclo(ciclos[i],ciclo)) {
                    return true;
                }
            }            
            
            return false;
        }
        
        function equalsCiclos(ciclosA,ciclosB) {
            if (!(Array.isArray(ciclosA) && Array.isArray(ciclosB))) {
                return false;
            }
            
            if (ciclosA.length!==ciclosB.length) {
                return false;
            }
            
            for(var i=0;i<ciclosA.length;i++) {
                if (existsCiclo(ciclosB,ciclosA[i])===false) {
                    return false;
                }
            }
            
            return true;
            
        }
                
        
        $scope.$watchCollection("model.ciclos", function (newCiclos, oldCiclos) {        
            if (newCiclos === oldCiclos) {
                return;
            }
            
            if (equalsCiclos(newCiclos,$scope.ciclos)) {
                $scope.todosCiclos=true;
            } else {
                $scope.todosCiclos=false;
            }

        });
        

        $scope.$watch("model.familia", function (newFamilia, oldFamilia) {

            if (newFamilia === oldFamilia) {
                return;
            }

            if (oldFamilia) {
                $scope.model.ciclos = [];
            }
            if (newFamilia) {
                var serviceCiclo = serviceFactory.getService("Ciclo");
                var query = {
                    filters: {
                        'familia.idFamilia': newFamilia.idFamilia
                    },
                    orderby: [
                        {fieldName: "grado", orderDirection: "DESC"},
                        {fieldName: "leyEducativa", orderDirection: "DESC"},
                        {fieldName: "descripcion", orderDirection: "ASC"}
                    ]
                };

                serviceCiclo.search(query).then(function (ciclos) {
                    $scope.ciclos = ciclos;
                    if (equalsCiclos($scope.model.ciclos,$scope.ciclos)) {
                        $scope.todosCiclos=true;
                    } else {
                        $scope.todosCiclos=false;
                    }
                }, function (businessMessages) {
                    $scope.businessMessages = businessMessages;
                });

            } else {
                $scope.ciclos = [];
            }
        });

        $scope.compareCiclo = function (cicloA, cicloB) {
            if (cicloA && cicloB) {
                return (cicloA.idCiclo === cicloB.idCiclo);
            } else {
                return false;
            }
        }

        $scope.$watch("page.pageNumber", function (newValue, oldValue) {
            if (newValue === oldValue) {
                return;
            }
            $scope.search();
        });
        $scope.$watch("page.pageSize", function (newValue, oldValue) {
            if (newValue === oldValue) {
                return;
            }
            $scope.page.pageNumber = 0;
            $scope.search();
        });
        $scope.$watch("orderby", function (newValue, oldValue) {
            if (newValue === oldValue) {
                return;
            }
            $scope.page.pageNumber = 0;
            $scope.search();
        }, true);

        $scope.$watch("filters", function (newValue, oldValue) {
            if (newValue === oldValue) {
                return;
            }
            $scope.page.pageNumber = 0;
            $scope.search();
        }, true);
        $scope.buttonSearch = function () {
            $scope.page.pageNumber = 0;
            $scope.search();
        };


    }]);



app.controller("OfertaDeleteController", ['$scope', 'genericControllerCrudDetail', 'controllerParams', 'dialog', 'schemaEntities', 'serviceFactory', function ($scope, genericControllerCrudDetail, controllerParams, dialog, schemaEntities, serviceFactory) {
        genericControllerCrudDetail.extendScope($scope, controllerParams);

        $scope.$watch("model.familia", function (newFamilia, oldFamilia) {

            if (newFamilia === oldFamilia) {
                return;
            }

            if (oldFamilia) {
                $scope.model.ciclos = [];
            }
            if (newFamilia) {
                var serviceCiclo = serviceFactory.getService("Ciclo");
                var query = {
                    filters: {
                        'familia.idFamilia': newFamilia.idFamilia
                    },
                    orderby: [
                        {fieldName: "grado", orderDirection: "DESC"},
                        {fieldName: "leyEducativa", orderDirection: "DESC"},
                        {fieldName: "descripcion", orderDirection: "ASC"}
                    ]
                }
                serviceCiclo.search(query).then(function (ciclos) {
                    $scope.ciclos = ciclos;
                }, function (businessMessages) {
                    $scope.businessMessages = businessMessages;
                });

            } else {
                $scope.ciclos = [];
            }
        });

        $scope.compareCiclo = function (cicloA, cicloB) {
            if (cicloA && cicloB) {
                return (cicloA.idCiclo === cicloB.idCiclo);
            } else {
                return false;
            }
        };

    }]);