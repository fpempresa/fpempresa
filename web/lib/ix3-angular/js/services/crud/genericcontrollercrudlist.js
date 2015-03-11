"use strict";

(function() {
    
    GenericControllerCrudList.$inject=['repositoryFactory', '$location','metadataEntities'];
    function GenericControllerCrudList(repositoryFactory, $location, metadataEntities) {
        this.extendScope = function(scope, controllerParams) {
            scope.models = {};
            scope.filters = [];
            scope.orderby = []; //Array con objetos con las propiedades fieldName y orderDirection. La propiedad orderDirection soporta los valores "ASC" y "DESC"
            scope.page = {};
            scope.businessMessages = null;
            angular.extend(scope, controllerParams);
            scope.repository = repositoryFactory.getRepository(scope.entity);
            scope.idName = metadataEntities.getMetadata(scope.entity).primaryKeyPropertyName;
            //Paginacion y busqueda
            if (!scope.page.pageNumber) {
                scope.page.pageNumber = 0;
            }
            scope.page.totalPages = undefined;
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



            scope.search = function() {
                var filters=scope.filters; 

                if (scope.parentProperty && scope.parentId) {
                    //Tenemos que hacer una copia pq sino cada vez que se busca se añadiría el filtro del "padre".
                    filters=angular.copy(scope.filters);
                    var parentFilter={};
                    parentFilter[scope.parentProperty] = scope.parentId;
                    filters.push(parentFilter);
                }

                scope.repository.search(filters, scope.orderby, scope.expand, scope.page.pageNumber, scope.page.pageSize).then(function(data) {
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
                }, function(businessMessages) {
                    scope.businessMessages = businessMessages;
                });
            };




            scope.buttonSearch = function() {
                scope.page.pageNumber = 0;
                scope.search();
            };
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



        };
    }

    angular.module('es.logongas.ix3').service("genericControllerCrudList", GenericControllerCrudList);

}());

