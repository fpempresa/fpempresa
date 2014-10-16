"use strict";

(function() {


    function CRUDRoutes($routeProvider) {
        this.addAllRoutes = function(entity,expand) {

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
                    crud: ['$route', '$q', 'remoteServiceFactory', 'notify','crudList', function($route, $q, remoteServiceFactory, notify,crudList) {
                            var deferred = $q.defer();
                            var remoteService = remoteServiceFactory.getRemoteService(entity);
                            remoteService.metadata(expand).then(function(metadata) {
                                var controllerConfig={
                                        entity : entity,
                                        parentProperty : $route.current.params.parentProperty,
                                        parentId : $route.current.params.parentId,
                                        metadata : {}
                                };
                                controllerConfig.metadata[entity]=metadata;
                                
                                var crud={
                                    extendScope:function(scope) {
                                        crudList.extendsScope(scope,controllerConfig);
                                    }
                                }
                                deferred.resolve(crud);
                            }, function(businessMessages) {
                                notify.warning(businessMessages);
                                deferred.reject(businessMessages);
                            });

                            return deferred.promise;

                        }]                    
                    
                }
            });


            $routeProvider.when('/' + lowerEntityName + '/new/:parentProperty?/:parentId?', {
                templateUrl: lowerEntityName + '/detail.' + fileExtension,
                controller: upperCamelEntityName + 'NewEditController',
                resolve: {
                    crud: ['$route', '$q', 'remoteServiceFactory', 'notify','crudDetail', function($route, $q, remoteServiceFactory, notify,crudDetail) {
                            var deferred = $q.defer();
                            var remoteService = remoteServiceFactory.getRemoteService(entity);
                            remoteService.metadata(expand).then(function(metadata) {
                                var controllerConfig={
                                        entity : entity,
                                        controllerAction : "NEW",
                                        id : null,
                                        parentProperty : $route.current.params.parentProperty,
                                        parentId : $route.current.params.parentId,
                                        metadata : {}
                                };
                                controllerConfig.metadata[entity]=metadata;
                                
                                
                                var crud={
                                    extendScope:function(scope) {
                                        crudDetail.extendsScope(scope,controllerConfig);
                                    }
                                }
                                deferred.resolve(crud);
                                
                            }, function(businessMessages) {
                                notify.warning(businessMessages);
                                deferred.reject(businessMessages);
                            });

                            return deferred.promise;

                        }]
                }
            });
            $routeProvider.when('/' + lowerEntityName + '/view/:id/:parentProperty?/:parentId?', {
                templateUrl: lowerEntityName + '/detail.' + fileExtension,
                controller: upperCamelEntityName + 'ViewController',
                resolve: {
                    crud: ['$route', '$q', 'remoteServiceFactory', 'notify','crudDetail', function($route, $q, remoteServiceFactory, notify,crudDetail) {
                            var deferred = $q.defer();
                            var remoteService = remoteServiceFactory.getRemoteService(entity);
                            remoteService.metadata(expand).then(function(metadata) {
                                var controllerConfig={
                                        entity : entity,
                                        controllerAction : "VIEW",
                                        id : $route.current.params.id,
                                        parentProperty : $route.current.params.parentProperty,
                                        parentId : $route.current.params.parentId,
                                        metadata : {}
                                };
                                controllerConfig.metadata[entity]=metadata;
                                
                                var crud={
                                    extendScope:function(scope) {
                                        crudDetail.extendsScope(scope,controllerConfig);
                                    }
                                }
                                deferred.resolve(crud);
                                
                            }, function(businessMessages) {
                                notify.warning(businessMessages);
                                deferred.reject(businessMessages);
                            });

                            return deferred.promise;

                        }]
                }
            });
            $routeProvider.when('/' + lowerEntityName + '/edit/:id/:parentProperty?/:parentId?', {
                templateUrl: lowerEntityName + '/detail.' + fileExtension,
                controller: upperCamelEntityName + 'NewEditController',
                resolve: {
                    crud: ['$route', '$q', 'remoteServiceFactory', 'notify','crudDetail', function($route, $q, remoteServiceFactory, notify,crudDetail) {
                            var deferred = $q.defer();
                            var remoteService = remoteServiceFactory.getRemoteService(entity);
                            remoteService.metadata(expand).then(function(metadata) {
                                var controllerConfig={
                                        entity : entity,
                                        controllerAction : "EDIT",
                                        id : $route.current.params.id,
                                        parentProperty : $route.current.params.parentProperty,
                                        parentId : $route.current.params.parentId,
                                        metadata : {}
                                };
                                controllerConfig.metadata[entity]=metadata;
                                
                                var crud={
                                    extendScope:function(scope) {
                                        crudDetail.extendsScope(scope,controllerConfig);
                                    }
                                }
                                deferred.resolve(crud);
                                
                            }, function(businessMessages) {
                                notify.warning(businessMessages);
                                deferred.reject(businessMessages);
                            });

                            return deferred.promise;

                        }]
                }
            });
            $routeProvider.when('/' + lowerEntityName + '/delete/:id/:parentProperty?/:parentId?', {
                templateUrl: lowerEntityName + '/detail.' + fileExtension,
                controller: upperCamelEntityName + 'DeleteController',
                resolve: {
                    crud: ['$route', '$q', 'remoteServiceFactory', 'notify','crudDetail', function($route, $q, remoteServiceFactory, notify,crudDetail) {
                            var deferred = $q.defer();
                            var remoteService = remoteServiceFactory.getRemoteService(entity);
                            remoteService.metadata(expand).then(function(metadata) {
                                var controllerConfig={
                                        entity : entity,
                                        controllerAction : "DELETE",
                                        id : $route.current.params.id,
                                        parentProperty : $route.current.params.parentProperty,
                                        parentId : $route.current.params.parentId,
                                        metadata : {}
                                };
                                controllerConfig.metadata[entity]=metadata;
                                
                                var crud={
                                    extendScope:function(scope) {
                                        crudDetail.extendsScope(scope,controllerConfig);
                                    }
                                }
                                deferred.resolve(crud);
                                
                            }, function(businessMessages) {
                                notify.warning(businessMessages);
                                deferred.reject(businessMessages);
                            });

                            return deferred.promise;

                        }]
                }
            });
        };
        this.$get=function() {
            //Realmente no queremos nada aqui ero angular nos obliga.
            return {};
        }

    }

    angular.module('es.logongas.ix3').provider("crudRoutes", ['$routeProvider',function($routeProvider) {
            return new CRUDRoutes($routeProvider);
        }]);


})();