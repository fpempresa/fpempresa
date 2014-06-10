"use strict";

(function() {

    function CRUDDetail(remoteServiceFactory, $window, validator, $location) {

        this.extendsScope = function(scope, controllerConfig) {
            scope.labelButtonOK = null;
            scope.labelButtonCancel = null;
            scope.model = {};
            scope.models = {};
            scope.businessMessages = null;
            angular.extend(scope, controllerConfig);
            scope.remoteService = remoteServiceFactory.getRemoteService(scope.entity);
            scope.idName = scope.metadata[scope.entity].primaryKeyPropertyName;

            scope.get = function(fnOK, fnError) {
                fnOK = fnOK || function() {
                };
                fnError = fnError || function() {
                };
                scope.remoteService.get(scope.id, scope.expand).then(function(data) {
                    scope.model = data;
                    scope.businessMessages = null;
                    fnOK();
                }, function(businessMessages) {
                    scope.businessMessages = businessMessages;
                    fnError();
                });
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

                scope.remoteService.create(scope.expand, parent).then(function(data) {
                    scope.model = data;
                    scope.businessMessages = null;
                    fnOK();
                }, function(businessMessages) {
                    scope.businessMessages = businessMessages;
                    fnError();
                });
            };
            scope.insert = function(fnOK, fnError) {
                fnOK = fnOK || scope.finishOK || function() {
                };
                fnError = fnError || function() {
                };
                scope.businessMessages = validator.validateForm(scope.mainForm);
                if (scope.businessMessages.length === 0) {
                    scope.remoteService.insert(scope.model, scope.expand).then(function(data) {
                        scope.model = data;
                        scope.businessMessages = null;
                        scope.controllerAction = "EDIT";
                        scope.id = scope.model[scope.idName];
                        fnOK();
                    }, function(businessMessages) {
                        scope.businessMessages = businessMessages;
                        fnError();
                    });
                }
            };
            scope.update = function(fnOK, fnError) {
                fnOK = fnOK || scope.finishOK || function() {
                };
                fnError = fnError || function() {
                };
                scope.businessMessages = validator.validateForm(scope.mainForm);
                if (scope.businessMessages.length === 0) {
                    scope.remoteService.update(scope.id, scope.model, scope.expand).then(function(data) {
                        scope.model = data;
                        scope.businessMessages = null;
                        fnOK();
                    }, function(businessMessages) {
                        scope.businessMessages = businessMessages;
                        fnError();
                    });
                }
            };
            scope.delete = function(fnOK, fnError) {
                fnOK = fnOK || scope.finishOK || function() {
                };
                fnError = fnError || function() {
                };
                scope.remoteService.delete(scope.id).then(function(data) {
                    scope.businessMessages = null;
                    fnOK();
                }, function(businessMessages) {
                    scope.businessMessages = businessMessages;
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


    }

    angular.module('es.logongas.ix3').factory("crudDetail", ['remoteServiceFactory', '$window', 'validator', '$location', function(remoteServiceFactory, $window, validator, $location) {
            return new CRUDDetail(remoteServiceFactory, $window, validator, $location);
        }]);

})();