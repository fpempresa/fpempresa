"use strict";
app.config(['crudRoutesProvider', function (crudRoutesProvider) {
        crudRoutesProvider.addAllRoutes({
            entity: "Oferta",
            expand: "municipio,municipio.provincia,familia,empresa,ciclos,ciclos,fecha"
        });
    }]);

app.controller("OfertaSearchController", ['$scope', 'genericControllerCrudList', 'controllerParams', 'dialog','$http','notify','ix3Configuration',  'serviceFactory',  function ($scope, genericControllerCrudList, controllerParams, dialog, $http,notify,ix3Configuration, serviceFactory) {
        genericControllerCrudList.extendScope($scope, controllerParams);
        $scope.page.pageSize = 20;
        $scope.orderby = [
            {fieldName: "fecha", orderDirection: "DESC"}
        ];
        $scope.search();
        
        $scope.buttonNotificar=function(idOferta) {
            var baseUrl=ix3Configuration.server.api;
            var config = {
                method: 'PATCH',
                url: baseUrl + "/Oferta/" + idOferta + "/notificacionOferta"
            };
            $http(config).success(function () {
                notify.info("Notificación Oferta","Correcta:" + idOferta);
            }).error(function (data, status) {
                notify.info("Notificación Oferta","Fallo:"+idOferta+"\n"+data);
            });
        };
        
        $scope.postSearch=function(ofertas) {
           
            ofertas.forEach(function(oferta) {
                $scope.getNumCandidatosOferta(oferta);
            });
        };
        
        $scope.getNumCandidatosOferta = function (oferta) {

            var query = {
                filters: {
                    'oferta':oferta.idOferta
                },
                namedSearch:"getNumCandidatosOferta"
            }

            serviceFactory.getService("Candidato").search(query).then(function (data) {
                oferta.numeroCandidatos = data;
            }, function (businessMessages) {
                $scope.businessMessages = businessMessages;
            });
        }        
        
        
    }]);


app.controller("OfertaNewEditController", ['$scope', 'genericControllerCrudDetail', 'controllerParams', 'dialog', 'schemaEntities', 'serviceFactory', function ($scope, genericControllerCrudDetail, controllerParams, dialog, schemaEntities, serviceFactory) {
        genericControllerCrudDetail.extendScope($scope, controllerParams);

        $scope.buscarEmpresa = function () {

            var params = {
                initialSearch: true
            };

            dialog.create('buscarEmpresas', params).then(function (empresa) {
                if (empresa) {
                    $scope.model.empresa = empresa;
                }
            });
        };
        
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
        
        $scope.checkTodosCiclos = function (todosCiclos) {

            if (todosCiclos) {
                $scope.model.ciclos = angular.copy($scope.ciclos);
            } else {
                $scope.model.ciclos = [];
            }

        };

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
        }

    }]);

app.controller("OfertaViewController", ['$scope', 'genericControllerCrudDetail', 'controllerParams', 'dialog', 'schemaEntities', 'serviceFactory', function ($scope, genericControllerCrudDetail, controllerParams, dialog, schemaEntities, serviceFactory) {
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
                };

                serviceCiclo.search(query).then(function (ciclos) {
                    $scope.ciclos = ciclos;
                }, function (businessMessages) {
                    $scope.businessMessages = businessMessages;
                });

            } else {
                $scope.ciclos = [];
            }
        });

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

        $scope.compareCiclo = function (cicloA, cicloB) {
            if (cicloA && cicloB) {
                return (cicloA.idCiclo === cicloB.idCiclo);
            } else {
                return false;
            }
        }

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
        }

    }]);