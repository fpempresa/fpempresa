"use strict";

(function () {

    CRUDRoutes.$inject = ['$stateProvider'];
    function CRUDRoutes($stateProvider) {

        this.defaultParentState="lateralmenu";

        this.addAllRoutes = function (config) {
            //Al ser todas las rutas no permitimos la variable "route" ya que tendría valores distintos para cada una
            delete config.route;
            this.addSearchRoute(config);
            this.addNewRoute(config);
            this.addEditRoute(config);
            this.addDeleteRoute(config);
            this.addViewRoute(config);
        };


        this.addSearchRoute = function (config) {
            var entity = config.entity;
            var expand = config.expand;
            var route = config.route;
            var htmlBasePath = (config.htmlBasePath || ".");
            var parentState=(config.parentState?config.parentState:this.defaultParentState);

            if (!entity) {
                throw Error("El argumento 'entity' no puede estar vacio");
            }

            var lowerEntityName = entity.toLowerCase();
            var upperCamelEntityName = entity.charAt(0).toUpperCase() + entity.slice(1);

            var name1 = parentState + "." + lowerEntityName + "_search";
            var name2 = parentState + "." + lowerEntityName + "_search_parent";
            var path1 = '/' + lowerEntityName + '/search';
            var path2 = '/' + lowerEntityName + '/search/:parentProperty/:parentId';
            var definitiveRoute = {
                templateUrl: htmlBasePath + "/" + lowerEntityName + '/search.html',
                controller: upperCamelEntityName + 'SearchController'
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

            var routeWithoutParent = angular.copy(definitiveRoute);
            routeWithoutParent.url = path1;
            $stateProvider.state(name1, routeWithoutParent);

            var routeWithParent = angular.copy(definitiveRoute);
            routeWithParent.url = path2;
            $stateProvider.state(name2, routeWithParent);
        };

        this.addNewRoute = function (config) {
            var entity = config.entity;
            var expand = config.expand;
            var route = config.route;
            var htmlBasePath = (config.htmlBasePath || ".");
            var parentState=(config.parentState?config.parentState+".":this.defaultParentState+".");
            
            if (!entity) {
                throw Error("El argumento 'entity' no puede estar vacio");
            }

            var lowerEntityName = entity.toLowerCase();
            var upperCamelEntityName = entity.charAt(0).toUpperCase() + entity.slice(1);

            var name1 = parentState + "." + lowerEntityName + "_new";
            var name2 = parentState + "." + lowerEntityName + "_new_parent";
            var path1 = '/' + lowerEntityName + '/new';
            var path2 = '/' + lowerEntityName + '/new/:parentProperty/:parentId';

            var definitiveRoute = {
                templateUrl: htmlBasePath + "/" + lowerEntityName + '/detail.html',
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

            var routeWithoutParent = angular.copy(definitiveRoute);
            routeWithoutParent.url = path1;
            $stateProvider.state(name1, routeWithoutParent);

            var routeWithParent = angular.copy(definitiveRoute);
            routeWithParent.url = path2;
            $stateProvider.state(name2, routeWithParent);

        };
        this.addViewRoute = function (config) {
            var entity = config.entity;
            var expand = config.expand;
            var route = config.route;
            var htmlBasePath = (config.htmlBasePath || ".");
            var parentState=(config.parentState?config.parentState+".":this.defaultParentState+".");

            if (!entity) {
                throw Error("El argumento 'entity' no puede estar vacio");
            }

            var lowerEntityName = entity.toLowerCase();
            var upperCamelEntityName = entity.charAt(0).toUpperCase() + entity.slice(1);

            var name1 = parentState + "." + lowerEntityName + "_view_";
            var name2 = parentState + "." + lowerEntityName + "_view_parent";
            var path1 = '/' + lowerEntityName + '/view/:id';
            var path2 = '/' + lowerEntityName + '/view/:id/:parentProperty/:parentId';

            var definitiveRoute = {
                templateUrl: htmlBasePath + "/" + lowerEntityName + '/detail.html',
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

            var routeWithoutParent = angular.copy(definitiveRoute);
            routeWithoutParent.url = path1;
            $stateProvider.state(name1, routeWithoutParent);

            var routeWithParent = angular.copy(definitiveRoute);
            routeWithParent.url = path2;
            $stateProvider.state(name2, routeWithParent);
        };
        this.addEditRoute = function (config) {
            var entity = config.entity;
            var expand = config.expand;
            var route = config.route;
            var htmlBasePath = (config.htmlBasePath || ".");
            var parentState=(config.parentState?config.parentState+".":this.defaultParentState+".");

            if (!entity) {
                throw Error("El argumento 'entity' no puede estar vacio");
            }

            var lowerEntityName = entity.toLowerCase();
            var upperCamelEntityName = entity.charAt(0).toUpperCase() + entity.slice(1);

            var name1 = parentState + "." + lowerEntityName + "_edit_";
            var name2 = parentState + "." + lowerEntityName + "_edit_parent";
            var path1 = '/' + lowerEntityName + '/edit/:id';
            var path2 = '/' + lowerEntityName + '/edit/:id/:parentProperty/:parentId';

            var definitiveRoute = {
                templateUrl: htmlBasePath + "/" + lowerEntityName + '/detail.html',
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

            var routeWithoutParent = angular.copy(definitiveRoute);
            routeWithoutParent.url = path1;
            $stateProvider.state(name1, routeWithoutParent);

            var routeWithParent = angular.copy(definitiveRoute);
            routeWithParent.url = path2;
            $stateProvider.state(name2, routeWithParent);
        };
        this.addDeleteRoute = function (config) {
            var entity = config.entity;
            var expand = config.expand;
            var route = config.route;
            var htmlBasePath = (config.htmlBasePath || ".");
            var parentState=(config.parentState?config.parentState+".":this.defaultParentState+".");

            if (!entity) {
                throw Error("El argumento 'entity' no puede estar vacio");
            }

            var lowerEntityName = entity.toLowerCase();
            var upperCamelEntityName = entity.charAt(0).toUpperCase() + entity.slice(1);

            var name1 = parentState + "." + lowerEntityName + "_delete_";
            var name2 = parentState + "." + lowerEntityName + "_delete_parent";
            var path1 = '/' + lowerEntityName + '/delete/:id';
            var path2 = '/' + lowerEntityName + '/delete/:id/:parentProperty/:parentId';

            var definitiveRoute = {
                templateUrl: htmlBasePath + "/" + lowerEntityName + '/detail.html',
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

            var routeWithoutParent = angular.copy(definitiveRoute);
            routeWithoutParent.url = path1;
            $stateProvider.state(name1, routeWithoutParent);

            var routeWithParent = angular.copy(definitiveRoute);
            routeWithParent.url = path2;
            $stateProvider.state(name2, routeWithParent);
        };
        this.$get = function () {
            //Realmente no queremos nada aqui pero angular nos obliga.
            return {};
        };

    }

    angular.module('es.logongas.ix3').provider("crudRoutes", CRUDRoutes);


})();