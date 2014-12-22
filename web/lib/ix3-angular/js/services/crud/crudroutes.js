"use strict";

(function () {

    CRUDRoutes.$inject = ['$routeProvider'];
    function CRUDRoutes($routeProvider) {

        this.addAllRoutes = function (entity, expand) {
            //Al ser todas las rutas no permitimos la variable "route" ya que tendría valores distintos para cada una
            this.addSearchRoute(entity, expand);
            this.addNewRoute(entity, expand);
            this.addEditRoute(entity, expand);
            this.addDeleteRoute(entity, expand);
            this.addViewRoute(entity, expand);
        };


        this.addSearchRoute = function (entity, expand, route) {

            if (!entity) {
                throw Error("El argumento 'entity' no puede estar vacio");
            }

            var lowerEntityName = entity.toLowerCase();
            var upperCamelEntityName = entity.charAt(0).toUpperCase() + entity.slice(1);

            var path = '/' + lowerEntityName + '/search/:parentProperty?/:parentId?';
            var definitiveRoute = {
                templateUrl: lowerEntityName + '/search.html',
                controller: upperCamelEntityName + 'SearchController',
                reloadOnSearch: false
            }
            var resolve = {
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

            angular.extend(definitiveRoute, route);
            definitiveRoute.resolve = definitiveRoute.resolve || {};
            angular.extend(definitiveRoute.resolve, resolve);

            $routeProvider.when(path, definitiveRoute);
        }
        this.addNewRoute = function (entity, expand, route) {

            if (!entity) {
                throw Error("El argumento 'entity' no puede estar vacio");
            }

            var lowerEntityName = entity.toLowerCase();
            var upperCamelEntityName = entity.charAt(0).toUpperCase() + entity.slice(1);

            var path = '/' + lowerEntityName + '/new/:parentProperty?/:parentId?';

            var definitiveRoute = {
                templateUrl: lowerEntityName + '/detail.html',
                controller: upperCamelEntityName + 'NewEditController'
            }

            var resolve = {
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

            angular.extend(definitiveRoute, route);
            definitiveRoute.resolve = definitiveRoute.resolve || {};
            angular.extend(definitiveRoute.resolve, resolve);

            $routeProvider.when(path, definitiveRoute);

        }
        this.addViewRoute = function (entity, expand, route) {

            if (!entity) {
                throw Error("El argumento 'entity' no puede estar vacio");
            }

            var lowerEntityName = entity.toLowerCase();
            var upperCamelEntityName = entity.charAt(0).toUpperCase() + entity.slice(1);


            var path = '/' + lowerEntityName + '/view/:id/:parentProperty?/:parentId?';

            var definitiveRoute = {
                templateUrl: lowerEntityName + '/detail.html',
                controller: upperCamelEntityName + 'ViewController'
            }

            var resolve = {
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
            };

            angular.extend(definitiveRoute, route);
            definitiveRoute.resolve = definitiveRoute.resolve || {};
            angular.extend(definitiveRoute.resolve, resolve);

            $routeProvider.when(path, definitiveRoute);
        }
        this.addEditRoute = function (entity, expand, route) {

            if (!entity) {
                throw Error("El argumento 'entity' no puede estar vacio");
            }

            var lowerEntityName = entity.toLowerCase();
            var upperCamelEntityName = entity.charAt(0).toUpperCase() + entity.slice(1);

            var path = '/' + lowerEntityName + '/edit/:id/:parentProperty?/:parentId?';

            var definitiveRoute = {
                templateUrl: lowerEntityName + '/detail.html',
                controller: upperCamelEntityName + 'NewEditController'
            }

            var resolve = {
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

            angular.extend(definitiveRoute, route);
            definitiveRoute.resolve = definitiveRoute.resolve || {};
            angular.extend(definitiveRoute.resolve, resolve);

            $routeProvider.when(path, definitiveRoute);
        }
        this.addDeleteRoute = function (entity, expand, route) {

            if (!entity) {
                throw Error("El argumento 'entity' no puede estar vacio");
            }

            var lowerEntityName = entity.toLowerCase();
            var upperCamelEntityName = entity.charAt(0).toUpperCase() + entity.slice(1);

            var path = '/' + lowerEntityName + '/delete/:id/:parentProperty?/:parentId?';

            var definitiveRoute = {
                templateUrl: lowerEntityName + '/detail.html',
                controller: upperCamelEntityName + 'DeleteController'
            }


            var resolve = {
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
            };

            angular.extend(definitiveRoute, route);
            definitiveRoute.resolve = definitiveRoute.resolve || {};
            angular.extend(definitiveRoute.resolve, resolve);

            $routeProvider.when(path, definitiveRoute);
        };
        this.$get = function () {
            //Realmente no queremos nada aqui pero angular nos obliga.
            return {};
        };

    }

    angular.module('es.logongas.ix3').provider("crudRoutes", CRUDRoutes);


})();