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

        $scope.filters.$eq['empresa.idEmpresa'] = $scope.user.empresa.idEmpresa;

        $scope.search();
    }]);


app.controller("OfertaNewEditController", ['$scope', 'genericControllerCrudDetail', 'controllerParams', '$location', 'metadataEntities', 'serviceFactory', 'session', 'dialog', function ($scope, genericControllerCrudDetail, controllerParams, $location, metadataEntities, serviceFactory, session, dialog) {
        genericControllerCrudDetail.extendScope($scope, controllerParams);

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
            
            $scope.filters['empresa.idEmpresa'] = $scope.model.empresa.idEmpresa;



            serviceFactory.getService("Candidato").search($scope.filters, $scope.orderby, "usuario", $scope.page.pageNumber, $scope.page.pageSize).then(function (data) {
                if (angular.isArray(data)) {
                    $scope.candidatos = data;
                } else {
                    //Si no es un array es un objeto "Page" Y lo comprobamos
                    if (data.hasOwnProperty("pageNumber") && data.hasOwnProperty("content") && data.hasOwnProperty("totalPages")) {
                        //Comprobamos este IF pq puede hbaer varias peticiones AJAX en curso y solo queremos la actual
                        if ($scope.page.pageNumber === data.pageNumber) {
                            $scope.candidatos = data.content;
                            $scope.page.totalPages = data.totalPages;
                        }
                    } else {
                        throw Error("Los datos retornados por el servidor no son un objeto 'Page'");
                    }
                }
            }, function (businessMessages) {
                $scope.businessMessages = businessMessages;
            });
        }

        $scope.verCandidato = function (idCandidato) {
            dialog.create("viewCandidato", {
                id: idCandidato
            }).then(function() {
                $scope.search();
            });
        };

        $scope.postCreate = function () {
            $scope.model.empresa = session.getUser().empresa;
        };

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
                var filters = {
                    'familia.idFamilia': newFamilia.idFamilia
                };
                var orderby = [
                    {fieldName: "grado", orderDirection: "DESC"},
                    {fieldName: "leyEducativa", orderDirection: "DESC"},
                    {fieldName: "descripcion", orderDirection: "ASC"}
                ];

                serviceCiclo.search(filters, orderby).then(function (ciclos) {
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



app.controller("OfertaDeleteController", ['$scope', 'genericControllerCrudDetail', 'controllerParams', 'dialog', 'metadataEntities', 'serviceFactory', function ($scope, genericControllerCrudDetail, controllerParams, dialog, metadataEntities, serviceFactory) {
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
                var filters = {
                    'familia.idFamilia': newFamilia.idFamilia
                };
                var orderby = [
                    {fieldName: "grado", orderDirection: "DESC"},
                    {fieldName: "leyEducativa", orderDirection: "DESC"},
                    {fieldName: "descripcion", orderDirection: "ASC"}
                ];

                serviceCiclo.search(filters, orderby).then(function (ciclos) {
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