
(function() {
    "use strict";

    function CRUD(daoFactory, $window, validator, $location, $rootScope) {
        this.extendsScopeSearchController = function(scope, controllerConfig) {
            scope.idName = undefined; //Por defecto es "id"+entity
            scope.models = {};
            scope.filter = {};
            scope.orderby = []; //Array con objetos con las propiedades fieldName y orderDirection. La propiedad orderDirection soporta los valores "ASC" y "DESC"
            scope.metadata = {};
            scope.businessMessages=null;
            
            scope.search = function() {
                if (scope.parentProperty && scope.parentId) {
                    scope.filter[scope.parentProperty] = scope.parentId;
                }


                scope.dao.search(scope.filter, scope.orderby, function(data) {
                    if (angular.isArray(data)) {
                        scope.models = data;
                    } else {
                        //Si no es un array es un objeto "Page" Y lo comprobamos
                        if (data.hasOwnProperty("pageNumber") && data.hasOwnProperty("content") && data.hasOwnProperty("totalPages")) {
                            //Comprobamos este IF pq puede hbaer varias peticiones AJAX en curso y solo queremos la actual
                            if (scope.page.pageNumber === data.pageNumber) {
                                scope.models = data.content;
                                scope.page.totalPages = data.totalPages;
                            }
                        } else {
                            throw Error("Los datos retornados por el servidor no son un objeto 'Page'");
                        }
                    }
                }, function(error) {
                    if (error.status === 400) {
                        scope.businessMessages = error.data;
                    } else {
                        scope.businessMessages = [{
                                propertyName: null,
                                message: "Estado HTTP:" + error.status + "\n" + error.data
                            }];
                    }
                }, undefined, scope.page.pageNumber, scope.page.pageSize);
            };
            scope.getMetadata = function(entity, expand,fnOK, fnError) {
                fnOK = fnOK || function() {
                };
                fnError = fnError || function() {
                };                
                
                daoFactory.getDAO(entity).metadata(function(data) {
                    scope.metadata[entity] = data;
                    fnOK();
                }, function(error) {
                    if (error.status === 400) {
                        scope.businessMessages = error.data;
                    } else {
                        scope.businessMessages = [{
                                propertyName: null,
                                message: "Estado HTTP:" + error.status + "\n" + error.data
                            }];
                    }
                    fnError();
                }, expand);
            };            
            scope.buttonSearch = function() {
                scope.page.pageNumber = 0;
                scope.search();
            };

            /**
             * Obtiene el path a navegar para una acción  un formulario
             * @param {String} actionName La accion:"new","edit","delete" o "view". Corresponde a las parte del path de las rutas.
             * @param {String} entity El nombre de la entidad 
             * @param {Object} pk El valor de la clave primaria
             * @param {String} parentProperty El nombre de la propiedad padre que se asocia
             * @param {Object} parentId El valor de la propiedad 'parentProperty'
             * @returns {String} El Path a navegar. No se incluye la "#".
             */
            function getPathAction(actionName, entity, pk, parentProperty, parentId) {
                var path = "/" + entity.toLowerCase() + "/" + actionName;
                if (pk) {
                    path = path + "/" + pk;
                }
                if ((parentProperty) && (parentId)) {
                    if (typeof (parentId) !== "string") {
                        throw Error("El tipo del argumento parentId debe ser un String pq es el nombre de una propiedad y no su valor");
                    }

                    path = path + "/" + parentProperty + "/" + parentId;
                }
                return path;
            }

            scope.buttonNew = function() {
                var newPath = getPathAction("new", scope.entity, undefined, scope.parentProperty, scope.parentId);
                $location.path(newPath).search({});
            };
            scope.buttonEdit = function(id) {
                var newPath = getPathAction("edit", scope.entity, id, scope.parentProperty, scope.parentId);
                $location.path(newPath).search({});
            };
            scope.buttonDelete = function(id) {
                var newPath = getPathAction("delete", scope.entity, id, scope.parentProperty, scope.parentId);
                $location.path(newPath).search({});
            };
            scope.buttonView = function(id) {
                var newPath = getPathAction("view", scope.entity, id, scope.parentProperty, scope.parentId);
                $location.path(newPath).search({});
            };

            angular.extend(scope, controllerConfig);
            if (!scope.page) {
                scope.page = {};
            }
            if (!scope.filter) {
                scope.filter = {};
            }
            if (!scope.page.pageNumber) {
                scope.page.pageNumber = 0;
            }
            scope.page.totalPages = undefined;
            if (!scope.idName) {
                scope.idName = "id" + scope.entity;
            }
            scope.dao = daoFactory.getDAO(scope.entity);

            scope.$watch("page.pageNumber", function(newValue, oldValue) {
                if (newValue === oldValue) {
                    return;
                }
                scope.search();
            });
            scope.$watch("page.pageSize", function(newValue, oldValue) {
                if (newValue === oldValue) {
                    return;
                }
                scope.page.pageNumber = 0;
                scope.search();
            });
            scope.$watch("order", function(newValue, oldValue) {
                if (newValue === oldValue) {
                    return;
                }
                scope.page.pageNumber = 0;
                scope.search();
            }, true);

            scope.getMetadata(scope.entity, scope.expand);

        };

        this.extendDetailController = function(scope, controllerConfig) {
            scope.labelButtonOK = null;
            scope.labelButtonCancel = null;
            scope.model = {};
            scope.models = {};
            scope.metadata = {};
            scope.businessMessages=null;
            scope.setValue = function(obj, key, newValue) {
                var keys = key.split('.');
                for (var i = 0; i < keys.length - 1; i++) {
                    if (!obj[keys[i]]) {
                        obj[keys[i]] = {};
                    }
                    obj = obj[keys[i]];
                }
                obj[keys[keys.length - 1]] = newValue;
            };
            scope.getMetadata = function(entity, expand,fnOK, fnError) {
                fnOK = fnOK || function() {
                };
                fnError = fnError || function() {
                };                
                
                daoFactory.getDAO(entity).metadata(function(data) {
                    scope.metadata[entity] = data;
                    fnOK();
                }, function(error) {
                    if (error.status === 400) {
                        scope.businessMessages = error.data;
                    } else {
                        scope.businessMessages = [{
                                propertyName: null,
                                message: "Estado HTTP:" + error.status + "\n" + error.data
                            }];
                    }
                    fnError();
                }, expand);
            };
            scope.get = function(fnOK, fnError) {
                fnOK = fnOK || function() {
                };
                fnError = fnError || function() {
                };
                scope.dao.get(scope.id, function(data) {
                    scope.model = data;
                    scope.businessMessages = null;
                    fnOK();
                }, function(error) {
                    if (error.status === 400) {
                        scope.businessMessages = error.data;
                    } else {
                        scope.businessMessages = [{
                                propertyName: null,
                                message: "Estado HTTP:" + error.status + "\n" + error.data
                            }];
                    }
                    fnError();
                }, scope.expand);
            };
            scope.create = function(fnOK, fnError) {
                fnOK = fnOK || function() {
                };
                fnError = fnError || function() {
                };
                var parent = {};
                if ((scope.parentProperty) && (scope.parentId)) {
                    parent[scope.parentProperty] = scope.parentId;
                }

                scope.dao.create(function(data) {
                    scope.model = data;
                    scope.businessMessages = null;
                    fnOK();
                }, function(error) {
                    if (error.status === 400) {
                        scope.businessMessages = error.data;
                    } else {
                        scope.businessMessages = [{
                                propertyName: null,
                                message: "Estado HTTP:" + error.status + "\n" + error.data
                            }];
                    }
                    fnError();
                }, scope.expand, parent);
            };
            scope.insert = function(fnOK, fnError) {
                fnOK = fnOK || scope.finishOK || function() {
                };
                fnError = fnError || function() {
                };
                scope.businessMessages = validator.validateForm(scope.mainForm);
                if (scope.businessMessages.length === 0) {
                    scope.dao.insert(scope.model, function(data) {
                        scope.model = data;
                        scope.businessMessages = null;
                        scope.controllerAction = "EDIT";
                        scope.id = scope.model[scope.idName];
                        fnOK();
                    }, function(error) {
                        if (error.status === 400) {
                            scope.businessMessages = error.data;
                        } else {
                            scope.businessMessages = [{
                                    propertyName: null,
                                    message: "Estado HTTP:" + error.status + "\n" + error.data
                                }];
                        }
                        fnError();
                    }, scope.expand);
                }
            };
            scope.update = function(fnOK, fnError) {
                fnOK = fnOK || scope.finishOK || function() {
                };
                fnError = fnError || function() {
                };
                scope.businessMessages = validator.validateForm(scope.mainForm);
                if (scope.businessMessages.length === 0) {
                    scope.dao.update(scope.id, scope.model, function(data) {
                        scope.model = data;
                        scope.businessMessages = null;
                        fnOK();
                    }, function(error) {
                        if (error.status === 400) {
                            scope.businessMessages = error.data;
                        } else {
                            scope.businessMessages = [{
                                    propertyName: null,
                                    message: "Estado HTTP:" + error.status + "\n" + error.data
                                }];
                        }
                        fnError();
                    }, scope.expand);
                }
            };
            scope.delete = function(fnOK, fnError) {
                fnOK = fnOK || scope.finishOK || function() {
                };
                fnError = fnError || function() {
                };
                scope.dao.delete(scope.id, function(data) {
                    scope.businessMessages = null;
                    fnOK();
                }, function(error) {
                    if (error.status === 400) {
                        scope.businessMessages = error.data;
                    } else {
                        scope.businessMessages = [{
                                propertyName: null,
                                message: "Estado HTTP:" + error.status + "\n" + error.data
                            }];
                    }
                    fnError();
                });
            };
            scope.finishOK = function() {
                $window.history.back();
            };
            scope.finishCancel = function() {
                $window.history.back();
            };

            /**
             * Obtiene el path a navegar para una acción "hija" de un formulario
             * @param {Scope} scope El scope para obtener los datos de la PK
             * @param {String} actionName La accion:"new","edit","delete" o "view". Corresponde a las parte del path de las rutas.
             * @param {String} entity El nombre de la entidad 
             * @param {Object} pk El valor de la clave primaria
             * @param {String} parentProperty El nombre de la propiedad padre que se asocia
             * @param {Object} parentId El valor de la propiedad 'parentProperty'
             * @returns {String} El Path a navegar. No se incluye la "#".
             */
            function getPathChildAction(scope, actionName, entity, pk, parentProperty, parentId) {
                var path = "/" + entity.toLowerCase() + "/" + actionName;
                if (pk) {
                    path = path + "/" + pk;
                }
                if ((parentProperty) && (parentId)) {
                    if (typeof (parentId) !== "string") {
                        throw Error("El tipo del argumento parentId debe ser un String pq es el nombre de una propiedad y no su valor");
                    }
                    path = path + "/" + parentProperty + "/" + scope.$eval(parentId);
                }
                return path;
            }


            scope.allowChildAction = function(actionName) {
                var allow;

                switch (scope.controllerAction) {
                    case "NEW":
                        allow = true;
                        break;
                    case "EDIT":
                        allow = true;
                        break;
                    case "VIEW":
                        //Solo se permite la accion view
                        if (actionName === "view") {
                            allow = true;
                        } else {
                            allow = false;
                        }
                        break;
                    case "DELETE":
                        //Solo se permite la accion view
                        if (actionName === "view") {
                            allow = true;
                        } else {
                            allow = false;
                        }
                        break;
                    default:
                        throw Error("scope.controllerAction desconocida:" + scope.controllerAction);
                }

                return allow;
            };

            scope.childAction = function(actionName, entity, pk, parentProperty, parentId) {

                //comprobar si podemos hacer esa accion;
                if (!scope.allowChildAction(actionName)) {
                    scope.businessMessages = [{
                            propertyName: null,
                            message: "No es posible realizar un :'" + actionName + "'"
                        }];

                    return;
                }

                switch (scope.controllerAction) {
                    case "NEW":
                        //Antes de hacer cualquier accion hay que insertar la fila
                        scope.insert(function() {
                            //Tenemos OBLIGATORIAMENTE AQUI que calcular el valor del path pq al ser un INSERT
                            //aun no había clave primaria y aqui lo volvemos a calcular
                            $location.path(getPathChildAction(scope, actionName, entity, pk, parentProperty, parentId)).search({});
                        });
                        break;
                    case "EDIT":
                        //Antes de hacer cualquier accion hay que actualizar la fila
                        scope.update(function() {
                            $location.path(getPathChildAction(scope, actionName, entity, pk, parentProperty, parentId)).search({});
                        });
                        break;
                    case "VIEW":
                        $location.path(getPathChildAction(scope, actionName, entity, pk, parentProperty, parentId)).search({});
                        break;
                    case "DELETE":
                        $location.path(getPathChildAction(scope, actionName, entity, pk, parentProperty, parentId)).search({});
                        break;
                    default:
                        throw Error("scope.controllerAction desconocida:" + scope.controllerAction);
                }
            };

            scope.buttonDefaultChild = function(entity, pk, parentProperty, parentId) {
                var actionName;
                switch (scope.controllerAction) {
                    case "NEW":
                        actionName = "edit";
                        break;
                    case "EDIT":
                        actionName = "edit";
                        break;
                    case "VIEW":
                        actionName = "view";
                        break;
                    case "DELETE":
                        actionName = "view";
                        break;
                    default:
                        throw Error("scope.controllerAction desconocida:" + scope.controllerAction);
                }
                scope.childAction(actionName, entity, pk, parentProperty, parentId);
            };
            scope.buttonNewChild = function(entity, pk, parentProperty, parentId) {
                scope.childAction("new", entity, pk, parentProperty, parentId);
            };
            scope.buttonEditChild = function(entity, pk, parentProperty, parentId) {
                scope.childAction("edit", entity, pk, parentProperty, parentId);
            };
            scope.buttonDeleteChild = function(entity, pk, parentProperty, parentId) {
                scope.childAction("delete", entity, pk, parentProperty, parentId);
            };
            scope.buttonViewChild = function(entity, pk, parentProperty, parentId) {
                scope.childAction("view", entity, pk, parentProperty, parentId);
            };
            angular.extend(scope, controllerConfig);
            if (!scope.idName) {
                scope.idName = "id" + scope.entity;
            }


            scope.buttonCancel = function() {
                scope.finishCancel();
            };

            scope.buttonOK = function() {
                switch (scope.controllerAction) {
                    case "NEW":
                        scope.insert();
                        break;
                    case "EDIT":
                        scope.update();
                        break;
                    case "VIEW":
                        scope.finishOK();
                        break;
                    case "DELETE":
                        scope.delete();
                        break;
                    default:
                        throw Error("scope.controllerAction desconocida:" + scope.controllerAction);
                }

            };

            scope.$watch("controllerAction", function(controllerAction) {
                switch (controllerAction) {
                    case "NEW":
                        scope.labelButtonOK = "Guardar";
                        scope.labelButtonCancel = "Salir";
                        break;
                    case "EDIT":
                        scope.labelButtonOK = "Actualizar";
                        scope.labelButtonCancel = "Salir";
                        break;
                    case "VIEW":
                        scope.labelButtonOK = "Salir";
                        scope.labelButtonCancel = "";
                        break;
                    case "DELETE":
                        scope.labelButtonOK = "Borrar";
                        scope.labelButtonCancel = "Salir";
                        break;
                    default:
                        throw Error("scope.controllerAction desconocida:" + scope.controllerAction);
                }
            });

            //Accion a realizar al iniciar el controlador
            scope.initialAction = function() {
                switch (scope.controllerAction) {
                    case "NEW":
                        scope.create();
                        break;
                    case "EDIT":
                        scope.get();
                        break;
                    case "VIEW":
                        scope.get();
                        break;
                    case "DELETE":
                        scope.get();
                        break;
                    default:
                        throw Error("scope.controllerAction desconocida:" + scope.controllerAction);
                }
            };

            scope.dao = daoFactory.getDAO(scope.entity);
            scope.getMetadata(scope.entity, scope.expand,function() {
                scope.initialAction();
            });

        };


    }

    function CRUDProvider($routeProvider) {
        this.addAllRoutes = function(entity, fileExtension) {

            if (!entity) {
                throw Error("El argumento 'entity' no puede estar vacio");
            }

            fileExtension = fileExtension || "html";
            var lowerEntityName = entity.toLowerCase();
            var camelEntityName = entity.charAt(0).toLowerCase() + entity.slice(1);
            var upperCamelEntityName = entity.charAt(0).toUpperCase() + entity.slice(1);


            $routeProvider.when('/' + lowerEntityName + '/search/:parentProperty?/:parentId?', {
                templateUrl: lowerEntityName + '/search.' + fileExtension,
                controller: upperCamelEntityName + 'SearchController',
                reloadOnSearch: false,
                resolve: {
                    crudState: ['$route', 'crud', function($route, crud) {
                            return {
                                extendsScopeController: function(scope, controllerConfig) {
                                    controllerConfig = controllerConfig || {};
                                    controllerConfig.entity = entity;
                                    controllerConfig.parentProperty = $route.current.params.parentProperty;
                                    controllerConfig.parentId = $route.current.params.parentId;
                                    crud.extendsScopeSearchController(scope, controllerConfig);
                                }
                            };
                        }]
                }
            });


            $routeProvider.when('/' + lowerEntityName + '/new/:parentProperty?/:parentId?', {
                templateUrl: lowerEntityName + '/detail.' + fileExtension,
                controller: upperCamelEntityName + 'NewEditController',
                resolve: {
                    crudState: ['$route', 'crud', function($route, crud) {
                            return {
                                extendsScopeController: function(scope, controllerConfig) {
                                    controllerConfig = controllerConfig || {};
                                    controllerConfig.entity = entity;
                                    controllerConfig.controllerAction = "NEW";
                                    controllerConfig.id = null;
                                    controllerConfig.parentProperty = $route.current.params.parentProperty;
                                    controllerConfig.parentId = $route.current.params.parentId;
                                    crud.extendDetailController(scope, controllerConfig);
                                }
                            };
                        }]
                }
            });
            $routeProvider.when('/' + lowerEntityName + '/view/:id/:parentProperty?/:parentId?', {
                templateUrl: lowerEntityName + '/detail.' + fileExtension,
                controller: upperCamelEntityName + 'ViewController',
                resolve: {
                    crudState: ['$route', 'crud', function($route, crud) {
                            return {
                                extendsScopeController: function(scope, controllerConfig) {
                                    controllerConfig = controllerConfig || {};
                                    controllerConfig.entity = entity;
                                    controllerConfig.controllerAction = "VIEW";
                                    controllerConfig.id = $route.current.params.id;
                                    controllerConfig.parentProperty = $route.current.params.parentProperty;
                                    controllerConfig.parentId = $route.current.params.parentId;
                                    crud.extendDetailController(scope, controllerConfig);
                                }
                            };
                        }]
                }
            });
            $routeProvider.when('/' + lowerEntityName + '/edit/:id/:parentProperty?/:parentId?', {
                templateUrl: lowerEntityName + '/detail.' + fileExtension,
                controller: upperCamelEntityName + 'NewEditController',
                resolve: {
                    crudState: ['$route', 'crud', function($route, crud) {
                            return {
                                extendsScopeController: function(scope, controllerConfig) {
                                    controllerConfig = controllerConfig || {};
                                    controllerConfig.entity = entity;
                                    controllerConfig.controllerAction = "EDIT";
                                    controllerConfig.id = $route.current.params.id;
                                    controllerConfig.parentProperty = $route.current.params.parentProperty;
                                    controllerConfig.parentId = $route.current.params.parentId;
                                    crud.extendDetailController(scope, controllerConfig);
                                }
                            };
                        }]
                }
            });
            $routeProvider.when('/' + lowerEntityName + '/delete/:id/:parentProperty?/:parentId?', {
                templateUrl: lowerEntityName + '/detail.' + fileExtension,
                controller: upperCamelEntityName + 'DeleteController',
                resolve: {
                    crudState: ['$route', 'crud', function($route, crud) {
                            return {
                                extendsScopeController: function(scope, controllerConfig) {
                                    controllerConfig = controllerConfig || {};
                                    controllerConfig.entity = entity;
                                    controllerConfig.controllerAction = "DELETE";
                                    controllerConfig.id = $route.current.params.id;
                                    controllerConfig.parentProperty = $route.current.params.parentProperty;
                                    controllerConfig.parentId = $route.current.params.parentId;
                                    crud.extendDetailController(scope, controllerConfig);
                                }
                            };
                        }]
                }
            });
        };
        this.$get = ['daoFactory', '$window', 'validator', '$location', '$rootScope', function(daoFactory, $window, validator, $location, $rootScope) {
                return new CRUD(daoFactory, $window, validator, $location, $rootScope);
            }];
    }

    angular.module('es.logongas.ix3').provider("crud", ['$routeProvider', function($routeProvider) {
            return new CRUDProvider($routeProvider);
        }]);



})();