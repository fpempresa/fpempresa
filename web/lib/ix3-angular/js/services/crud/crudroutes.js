"use strict";

(function () {

    CRUDRoutes.$inject=['$routeProvider'];
    function CRUDRoutes($routeProvider) {
        this.addAllRoutes = function (entity, expand) {

            if (!entity) {
                throw Error("El argumento 'entity' no puede estar vacio");
            }

            var fileExtension = "html";
            var lowerEntityName = entity.toLowerCase();
            var camelEntityName = entity.charAt(0).toLowerCase() + entity.slice(1);
            var upperCamelEntityName = entity.charAt(0).toUpperCase() + entity.slice(1);


            $routeProvider.when('/' + lowerEntityName + '/search/:parentProperty?/:parentId?', {
                templateUrl: lowerEntityName + '/search.' + fileExtension,
                controller: upperCamelEntityName + 'SearchController',
                reloadOnSearch: false,
                resolve: {
                    controllerParams: ['$route', function ($route) {
                            return {
                                entity: entity,
                                parentProperty: $route.current.params.parentProperty,
                                parentId: $route.current.params.parentId,
                                expand: expand
                            };
                        }],
                    metadata: ['metadataEntities', function (metadataEntities) {
                            return metadataEntities.load(entity, expand);
                        }]

                }
            });


            $routeProvider.when('/' + lowerEntityName + '/new/:parentProperty?/:parentId?', {
                templateUrl: lowerEntityName + '/detail.' + fileExtension,
                controller: upperCamelEntityName + 'NewEditController',
                resolve: {
                    controllerParams: ['$route', function ($route) {
                            return {
                                entity: entity,
                                controllerAction: "NEW",
                                id: null,
                                parentProperty: $route.current.params.parentProperty,
                                parentId: $route.current.params.parentId,
                                expand: expand
                            };
                        }],
                    metadata: ['metadataEntities', function (metadataEntities) {
                            return metadataEntities.load(entity, expand);
                        }]
                }
            });
            $routeProvider.when('/' + lowerEntityName + '/view/:id/:parentProperty?/:parentId?', {
                templateUrl: lowerEntityName + '/detail.' + fileExtension,
                controller: upperCamelEntityName + 'ViewController',
                resolve: {
                    controllerParams: ['$route', function ($route) {
                            return {
                                entity: entity,
                                controllerAction: "VIEW",
                                id: $route.current.params.id,
                                parentProperty: $route.current.params.parentProperty,
                                parentId: $route.current.params.parentId,
                                expand: expand
                            };

                        }],
                    metadata: ['metadataEntities', function (metadataEntities) {
                            return metadataEntities.load(entity, expand);
                        }]
                }
            });
            $routeProvider.when('/' + lowerEntityName + '/edit/:id/:parentProperty?/:parentId?', {
                templateUrl: lowerEntityName + '/detail.' + fileExtension,
                controller: upperCamelEntityName + 'NewEditController',
                resolve: {
                    controllerParams: ['$route', function ($route) {
                            return {
                                entity: entity,
                                controllerAction: "EDIT",
                                id: $route.current.params.id,
                                parentProperty: $route.current.params.parentProperty,
                                parentId: $route.current.params.parentId,
                                expand: expand
                            };

                        }],
                    metadata: ['metadataEntities', function (metadataEntities) {
                            return metadataEntities.load(entity, expand);
                        }]
                }
            });
            $routeProvider.when('/' + lowerEntityName + '/delete/:id/:parentProperty?/:parentId?', {
                templateUrl: lowerEntityName + '/detail.' + fileExtension,
                controller: upperCamelEntityName + 'DeleteController',
                resolve: {
                    controllerParams: ['$route', function ($route) {
                            return {
                                entity: entity,
                                controllerAction: "DELETE",
                                id: $route.current.params.id,
                                parentProperty: $route.current.params.parentProperty,
                                parentId: $route.current.params.parentId,
                                expand: expand
                            };

                        }],
                    metadata: ['metadataEntities', function (metadataEntities) {
                            return metadataEntities.load(entity, expand);
                        }]
                }
            });
        };
        this.$get = function () {
            //Realmente no queremos nada aqui ero angular nos obliga.
            return {};
        }

    }

    angular.module('es.logongas.ix3').provider("crudRoutes", CRUDRoutes);


})();