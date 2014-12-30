"use strict";

(function () {

    CRUDRoutes.$inject = ['$stateProvider'];
    function CRUDRoutes($stateProvider) {

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

            var path1 = '/' + lowerEntityName + '/search';
            var path2 = '/' + lowerEntityName + '/search/:parentProperty/:parentId';
            var definitiveRoute = {
                templateUrl: lowerEntityName + '/search.html',
                controller: upperCamelEntityName + 'SearchController',
                reloadOnSearch: false
            };
            var resolve = {
                controllerParams: ['$stateParams', function ($stateParams) {
                        return {
                            entity: entity,
                            parentProperty: $stateParams.parentProperty,
                            parentId: $stateParams.parentId,
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

            var routeWithoutParent=angular.copy(definitiveRoute);
            routeWithoutParent.url=path1;
            $stateProvider.state(path1, routeWithoutParent);
            
            var routeWithParent=angular.copy(definitiveRoute);
            routeWithParent.url=path2;
            $stateProvider.state(path2, routeWithParent);            
        };
        
        this.addNewRoute = function (entity, expand, route) {

            if (!entity) {
                throw Error("El argumento 'entity' no puede estar vacio");
            }

            var lowerEntityName = entity.toLowerCase();
            var upperCamelEntityName = entity.charAt(0).toUpperCase() + entity.slice(1);

            var path1 = '/' + lowerEntityName + '/new';
            var path2 = '/' + lowerEntityName + '/new/:parentProperty/:parentId';

            var definitiveRoute = {
                templateUrl: lowerEntityName + '/detail.html',
                controller: upperCamelEntityName + 'NewEditController'
            };

            var resolve = {
                controllerParams: ['$stateParams', function ($stateParams) {
                        return {
                            entity: entity,
                            controllerAction: "NEW",
                            id: null,
                            parentProperty: $stateParams.parentProperty,
                            parentId: $stateParams.parentId,
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

            var routeWithoutParent=angular.copy(definitiveRoute);
            routeWithoutParent.url=path1;
            $stateProvider.state(path1, routeWithoutParent);
            
            var routeWithParent=angular.copy(definitiveRoute);
            routeWithParent.url=path2;
            $stateProvider.state(path2, routeWithParent);  

        };
        this.addViewRoute = function (entity, expand, route) {

            if (!entity) {
                throw Error("El argumento 'entity' no puede estar vacio");
            }

            var lowerEntityName = entity.toLowerCase();
            var upperCamelEntityName = entity.charAt(0).toUpperCase() + entity.slice(1);


            var path1 = '/' + lowerEntityName + '/view/:id';
            var path2 = '/' + lowerEntityName + '/view/:id/:parentProperty/:parentId';

            var definitiveRoute = {
                templateUrl: lowerEntityName + '/detail.html',
                controller: upperCamelEntityName + 'ViewController'
            }

            var resolve = {
                controllerParams: ['$stateParams', function ($stateParams) {
                        return {
                            entity: entity,
                            controllerAction: "VIEW",
                            id: $stateParams.id,
                            parentProperty: $stateParams.parentProperty,
                            parentId: $stateParams.parentId,
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

            var routeWithoutParent=angular.copy(definitiveRoute);
            routeWithoutParent.url=path1;
            $stateProvider.state(path1, routeWithoutParent);
            
            var routeWithParent=angular.copy(definitiveRoute);
            routeWithParent.url=path2;
            $stateProvider.state(path2, routeWithParent);  
        };
        this.addEditRoute = function (entity, expand, route) {

            if (!entity) {
                throw Error("El argumento 'entity' no puede estar vacio");
            }

            var lowerEntityName = entity.toLowerCase();
            var upperCamelEntityName = entity.charAt(0).toUpperCase() + entity.slice(1);

            var path1 = '/' + lowerEntityName + '/edit/:id';
            var path2 = '/' + lowerEntityName + '/edit/:id/:parentProperty/:parentId';

            var definitiveRoute = {
                templateUrl: lowerEntityName + '/detail.html',
                controller: upperCamelEntityName + 'NewEditController'
            }

            var resolve = {
                controllerParams: ['$stateParams', function ($stateParams) {
                        return {
                            entity: entity,
                            controllerAction: "EDIT",
                            id: $stateParams.id,
                            parentProperty: $stateParams.parentProperty,
                            parentId: $stateParams.parentId,
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

            var routeWithoutParent=angular.copy(definitiveRoute);
            routeWithoutParent.url=path1;
            $stateProvider.state(path1, routeWithoutParent);
            
            var routeWithParent=angular.copy(definitiveRoute);
            routeWithParent.url=path2;
            $stateProvider.state(path2, routeWithParent);  
        };
        this.addDeleteRoute = function (entity, expand, route) {

            if (!entity) {
                throw Error("El argumento 'entity' no puede estar vacio");
            }

            var lowerEntityName = entity.toLowerCase();
            var upperCamelEntityName = entity.charAt(0).toUpperCase() + entity.slice(1);

            var path1 = '/' + lowerEntityName + '/delete/:id';
            var path2 = '/' + lowerEntityName + '/delete/:id/:parentProperty/:parentId';

            var definitiveRoute = {
                templateUrl: lowerEntityName + '/detail.html',
                controller: upperCamelEntityName + 'DeleteController'
            };


            var resolve = {
                controllerParams: ['$stateParams', function ($stateParams) {
                        return {
                            entity: entity,
                            controllerAction: "DELETE",
                            id: $stateParams.id,
                            parentProperty: $stateParams.parentProperty,
                            parentId: $stateParams.parentId,
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

            var routeWithoutParent=angular.copy(definitiveRoute);
            routeWithoutParent.url=path1;
            $stateProvider.state(path1, routeWithoutParent);
            
            var routeWithParent=angular.copy(definitiveRoute);
            routeWithParent.url=path2;
            $stateProvider.state(path2, routeWithParent);  
        };
        this.$get = function () {
            //Realmente no queremos nada aqui pero angular nos obliga.
            return {};
        };

    }

    angular.module('es.logongas.ix3').provider("crudRoutes", CRUDRoutes);


})();