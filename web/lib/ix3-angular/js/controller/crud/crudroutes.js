"use strict";

(function () {

    CRUDRoutes.$inject = ['$stateProvider', 'ix3ConfigurationProvider'];
    function CRUDRoutes($stateProvider, ix3ConfigurationProvider) {

        this.defaultParentState = ix3ConfigurationProvider.crud.defaultParentState;
        this.defaultHtmlBasePath = ix3ConfigurationProvider.crud.defaultHtmlBasePath;

        this.addAllRoutes = function (config) {
            this.addSearchRoute(config);
            this.addNewRoute(config);
            this.addEditRoute(config);
            this.addDeleteRoute(config);
            this.addViewRoute(config);
        };


        this.addSearchRoute = function (config) {
            if (!config) {
                throw Error("El parámetro 'config' no puede estar vacio");
            }            
            if (!config.entity) {
                throw Error("El parámetro 'config.entity' no puede estar vacio");
            }            


            var htmlBasePath = config.htmlBasePath || this.defaultHtmlBasePath;
            var parentState = config.parentState || this.defaultParentState;


            $stateProvider.state(parentState + "." + config.entity.toLowerCase() + "_search_parent", {
                url: '/' + config.entity.toLowerCase() + '/search/:parentProperty/:parentId',
                templateUrl: htmlBasePath + "/" + config.entity.toLowerCase() + '/search.html',
                controller: config.entity + 'SearchController',
                resolve: this.getResolve(config.entity, config.expand)
            });

            $stateProvider.state(parentState + "." + config.entity.toLowerCase() + "_search", {
                url: '/' + config.entity.toLowerCase() + '/search',
                templateUrl: htmlBasePath + "/" + config.entity.toLowerCase() + '/search.html',
                controller: config.entity + 'SearchController',
                resolve: this.getResolve(config.entity, config.expand)
            });

        };


        this.addNewRoute = function (config) {
            if (!config) {
                throw Error("El parámetro 'config' no puede estar vacio");
            }            
            if (!config.entity) {
                throw Error("El parámetro 'config.entity' no puede estar vacio");
            }            


            var htmlBasePath = config.htmlBasePath || this.defaultHtmlBasePath;
            var parentState = config.parentState || this.defaultParentState;


            $stateProvider.state(parentState + "." + config.entity.toLowerCase() + "_new_parent", {
                url: '/' + config.entity.toLowerCase() + '/new/:parentProperty/:parentId',
                templateUrl: htmlBasePath + "/" + config.entity.toLowerCase() + '/detail.html',
                controller: config.entity + 'NewEditController',
                resolve: this.getResolve(config.entity, config.expand, "NEW")
            });

            $stateProvider.state(parentState + "." + config.entity.toLowerCase() + "_new_", {
                url: '/' + config.entity.toLowerCase() + '/new',
                templateUrl: htmlBasePath + "/" + config.entity.toLowerCase() + '/detail.html',
                controller: config.entity + 'NewEditController',
                resolve: this.getResolve(config.entity, config.expand, "NEW")
            });

        };
        
        
        this.addViewRoute = function (config) {
            if (!config) {
                throw Error("El parámetro 'config' no puede estar vacio");
            }            
            if (!config.entity) {
                throw Error("El parámetro 'config.entity' no puede estar vacio");
            }             
            
            
            var htmlBasePath = config.htmlBasePath || this.defaultHtmlBasePath;
            var parentState = config.parentState || this.defaultParentState;


            $stateProvider.state(parentState + "." + config.entity.toLowerCase() + "_view_parent", {
                url: '/' + config.entity.toLowerCase() + '/view/:id/:parentProperty/:parentId',
                templateUrl: htmlBasePath + "/" + config.entity.toLowerCase() + '/detail.html',
                controller: config.entity + 'ViewController',
                resolve: this.getResolve(config.entity, config.expand, "VIEW")
            });

            $stateProvider.state(parentState + "." + config.entity.toLowerCase() + "_view_", {
                url: '/' + config.entity.toLowerCase() + '/view/:id',
                templateUrl: htmlBasePath + "/" + config.entity.toLowerCase() + '/detail.html',
                controller: config.entity + 'ViewController',
                resolve: this.getResolve(config.entity, config.expand, "VIEW")
            });
        };
        
        
        this.addEditRoute = function (config) {
            if (!config) {
                throw Error("El parámetro 'config' no puede estar vacio");
            }            
            if (!config.entity) {
                throw Error("El parámetro 'config.entity' no puede estar vacio");
            }             


            var htmlBasePath = config.htmlBasePath || this.defaultHtmlBasePath;
            var parentState = config.parentState || this.defaultParentState;


            $stateProvider.state(parentState + "." + config.entity.toLowerCase() + "_edit_parent", {
                url: '/' + config.entity.toLowerCase() + '/edit/:id/:parentProperty/:parentId',
                templateUrl: htmlBasePath + "/" + config.entity.toLowerCase() + '/detail.html',
                controller: config.entity + 'NewEditController',
                resolve: this.getResolve(config.entity, config.expand, "EDIT")
            });

            $stateProvider.state(parentState + "." + config.entity.toLowerCase() + "_edit_", {
                url: '/' + config.entity.toLowerCase() + '/edit/:id',
                templateUrl: htmlBasePath + "/" + config.entity.toLowerCase() + '/detail.html',
                controller: config.entity + 'NewEditController',
                resolve: this.getResolve(config.entity, config.expand, "EDIT")
            });
        };
        
        
        this.addDeleteRoute = function (config) {
            if (!config) {
                throw Error("El parámetro 'config' no puede estar vacio");
            }            
            if (!config.entity) {
                throw Error("El parámetro 'config.entity' no puede estar vacio");
            } 


            var htmlBasePath = config.htmlBasePath || this.defaultHtmlBasePath;
            var parentState = config.parentState || this.defaultParentState;


            $stateProvider.state(parentState + "." + config.entity.toLowerCase() + "_delete_parent", {
                url: '/' + config.entity.toLowerCase() + '/delete/:id/:parentProperty/:parentId',
                templateUrl: htmlBasePath + "/" + config.entity.toLowerCase() + '/detail.html',
                controller: config.entity + 'DeleteController',
                resolve: this.getResolve(config.entity, config.expand, "DELETE")
            });

            $stateProvider.state(parentState + "." + config.entity.toLowerCase() + "_delete_", {
                url: '/' + config.entity.toLowerCase() + '/delete/:id',
                templateUrl: htmlBasePath + "/" + config.entity.toLowerCase() + '/detail.html',
                controller: config.entity + 'DeleteController',
                resolve: this.getResolve(config.entity, config.expand, "DELETE")
            });
        };
        
        
        this.getResolve = function (entity, expand, controllerAction) {
            var resolve;
            if (controllerAction) {
                resolve = {
                    controllerParams: ['$stateParams', function ($stateParams) {
                            return {
                                entity: entity,
                                controllerAction: controllerAction,
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
            } else {
                //El resolve del "search"
                resolve = {
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
            }
            return resolve;
        };        
        
        
        this.$get = function () {
            //Realmente no queremos nada aqui pero angular nos obliga.
            return {};
        };

    }

    angular.module('es.logongas.ix3').provider("crudRoutes", CRUDRoutes);


})();