"use strict";

angular.module("es.logongas.ix3").provider("daoFactory", ['RestangularProvider', function(RestangularProvider) {
        this._baseURL;
        this.setBaseURL = function(baseURL) {
            if (!baseURL) {
                throw Error("El argumento baseURL no puede estar vacío");
            }
            if (typeof (baseURL) !== "string") {
                throw Error("El argumento baseURL debe ser un String");
            }

            this._baseURL = baseURL;
            RestangularProvider.setBaseUrl(this._baseURL);
        };


        /**
         * Esta es la clase DAO verdaderaque genera el Factory
         * @param {String} entityName Nombre de la entidad 
         * @param {Restangular} Restangular El servicio que realmente hace las peticiones REST
         */
        function DAO(entityName, Restangular) {
            this.entityName = entityName;
            this.Restangular = Restangular;
        }

        DAO.prototype.create = function(fnOK, fnError, expand, parent) {
            var params = {};
            if (parent) {
                angular.extend(params, parent);
            }
            if (expand) {
                params.$expand = expand;
            }
            this.Restangular.one(this.entityName, '$create').get(params).then(fnOK, fnError);
        };
        DAO.prototype.get = function(id, fnOK, fnError, expand) {
            var params = {};
            if (expand) {
                params.$expand = expand;
            }
            this.Restangular.one(this.entityName, id).get(params).then(fnOK, fnError);
        };
        DAO.prototype.insert = function(entity, fnOK, fnError, expand) {
            var params = {};
            if (expand) {
                params.$expand = expand;
            }
            this.Restangular.one(this.entityName).customPOST(entity, undefined, params).then(fnOK, fnError);
        };
        DAO.prototype.update = function(id, entity, fnOK, fnError, expand) {
            var params = {};
            if (expand) {
                params.$expand = expand;
            }
            this.Restangular.one(this.entityName, id).customPUT(entity, undefined, params).then(fnOK, fnError);
        };
        DAO.prototype.delete = function(id, fnOK, fnError) {
            this.Restangular.one(this.entityName, id).customDELETE().then(fnOK, fnError);
        };
        DAO.prototype.search = function(filter, order, fnOK, fnError, expand, pageNumber, pageSize) {
            var params = {};
            if (filter) {
                angular.extend(params, filter);
            }
            if (order) {
                params.$orderby = "";
                for (var i = 0; i < order.length; i++) {
                    var simpleOrder = order[i];
                    if (params.$orderby !== "") {
                        params.$orderby = params.$orderby + ",";
                    }
                    params.$orderby = params.$orderby + simpleOrder.fieldName + " " + simpleOrder.orderDirection;
                }
            }

            if (expand) {
                params.$expand = expand;
            }
            if ((pageNumber >= 0) && (pageSize > 0)) {
                params.$pagenumber = pageNumber;
                params.$pagesize = pageSize;
            }

            this.Restangular.all(this.entityName).getList(params).then(fnOK, fnError);
        };

        DAO.prototype.getChild = function(id, child, fnOK, fnError, expand) {
            var params = {};
            if (expand) {
                params.$expand = expand;
            }
            this.Restangular.one(this.entityName, id).getList(child, params).then(fnOK, fnError);
        };

        DAO.prototype.metadata = function(fnOK, fnError, expand) {
            var params = {};
            if (expand) {
                params.$expand = expand;
            }
            this.Restangular.one(this.entityName, '$metadata').get(params).then(fnOK, fnError);
        };

        this.$get = ['Restangular', function(Restangular) {
                return {
                    getDAO: function(entityName) {
                        var dao = new DAO(entityName, Restangular);
                        return dao;

                    }
                };

            }];

    }]);


