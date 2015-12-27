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

        $scope.filters['empresa.idEmpresa'] = $scope.user.empresa.idEmpresa;

        $scope.search();
    }]);


app.controller("OfertaNewEditController", ['$scope', 'genericControllerCrudDetail', 'controllerParams', '$location', 'schemaEntities', 'serviceFactory', 'session', 'dialog', function ($scope, genericControllerCrudDetail, controllerParams, $location, schemaEntities, serviceFactory, session, dialog) {
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