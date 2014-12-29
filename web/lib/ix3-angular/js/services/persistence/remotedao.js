(function (undefined) {
    "use strict";

    /**
     * Esta es la clase RemoteDAO verdadera que genera el RemoteDAOFactory
     * @param {String} entityName Nombre de la entidad 
     * @param {String} baseUrl La url en la que se encuentran los servicios.
     * @param {Http} $http Servicio de Http de AngularJS
     * @param {Q} $q Servicio de promesas de AngularJS
     */
    function RemoteDAO(entityName, baseUrl, $http, $q) {
        this.entityName = entityName;
        this.baseUrl = baseUrl;
        this.$http = $http;
        this.$q = $q;


        this.create = function (expand, parent) {
            var deferred = this.$q.defer();

            var params = {};
            if (parent) {
                angular.extend(params, parent);
            }
            if (expand) {
                params.$expand = expand;
            }

            var config = {
                method: 'GET',
                url: this.baseUrl + '/' + this.entityName + "/$create",
                params: params
            };

            this.$http(config).success(function (data, status, headers, config) {
                if (status === 204) {
                    //El 204 (no content) realmente es un null
                    deferred.resolve(null);
                } else {
                    deferred.resolve(data);
                }
            }).error(function (data, status, headers, config) {
                if (status === 400) {
                    deferred.reject(data);
                } else {
                    throw new Error("Fallo al crear la entidad los datos:" + status + "\n" + data);
                }
            });

            return deferred.promise;
        };
        this.get = function (id, expand) {
            var deferred = this.$q.defer();

            var params = {};
            if (expand) {
                params.$expand = expand;
            }

            var config = {
                method: 'GET',
                url: this.baseUrl + '/' + this.entityName + "/" + id,
                params: params
            };

            this.$http(config).success(function (data, status, headers, config) {
                if (status === 204) {
                    //El 204 (no content) realmente es un null
                    deferred.resolve(null);
                } else {
                    deferred.resolve(data);
                }
            }).error(function (data, status, headers, config) {
                if (status === 400) {
                    deferred.reject(data);
                } else {
                    throw new Error("Fallo al obtener la entidad:" + status + "\n" + data);
                }
            });

            return deferred.promise;
        };
        this.insert = function (entity, expand) {
            var deferred = this.$q.defer();

            var params = {};
            if (expand) {
                params.$expand = expand;
            }

            var config = {
                method: 'POST',
                url: this.baseUrl + '/' + this.entityName + "/",
                params: params,
                data: entity
            };

            this.$http(config).success(function (data, status, headers, config) {
                if (status === 204) {
                    //El 204 (no content) realmente es un null
                    deferred.resolve(null);
                } else {
                    deferred.resolve(data);
                }
            }).error(function (data, status, headers, config) {
                if (status === 400) {
                    deferred.reject(data);
                } else {
                    throw new Error("Fallo al insertar la entidad:" + status + "\n" + data);
                }
            });

            return deferred.promise;
        };
        this.update = function (id, entity, expand) {
            var deferred = this.$q.defer();

            var params = {};
            if (expand) {
                params.$expand = expand;
            }

            var config = {
                method: 'PUT',
                url: this.baseUrl + '/' + this.entityName + "/" + id,
                params: params,
                data: entity
            };

            this.$http(config).success(function (data, status, headers, config) {
                if (status === 204) {
                    //El 204 (no content) realmente es un null
                    deferred.resolve(null);
                } else {
                    deferred.resolve(data);
                }
            }).error(function (data, status, headers, config) {
                if (status === 400) {
                    deferred.reject(data);
                } else {
                    throw new Error("Fallo al insertar la entidad:" + status + "\n" + data);
                }
            });

            return deferred.promise;
        };
        this.delete = function (id) {
            var deferred = this.$q.defer();

            var params = {};

            var config = {
                method: 'DELETE',
                url: this.baseUrl + '/' + this.entityName + "/" + id,
                params: params
            };

            this.$http(config).success(function (data, status, headers, config) {
                if (status === 204) {
                    //El 204 (no content) realmente es un null
                    deferred.resolve(null);
                } else {
                    deferred.resolve(data);
                }
            }).error(function (data, status, headers, config) {
                if (status === 400) {
                    deferred.reject(data);
                } else {
                    throw new Error("Fallo al borrar la entidad:" + status + "\n" + data);
                }
            });

            return deferred.promise;
        };
        this.search = function (filter, order, expand, pageNumber, pageSize) {
            var deferred = this.$q.defer();

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

            var config = {
                method: 'GET',
                url: this.baseUrl + '/' + this.entityName,
                params: params
            };

            this.$http(config).success(function (data, status, headers, config) {
                if (status === 204) {
                    //El 204 (no content) realmente es un null
                    deferred.resolve(null);
                } else {
                    deferred.resolve(data);
                }
            }).error(function (data, status, headers, config) {
                if (status === 400) {
                    deferred.reject(data);
                } else {
                    throw new Error("Fallo al buscar los datos:" + status + "\n" + data);
                }
            });

            return deferred.promise;
        };

        this.getChild = function (id, child, expand) {
            var deferred = this.$q.defer();

            var params = {};
            if (expand) {
                params.$expand = expand;
            }

            var config = {
                method: 'GET',
                url: this.baseUrl + '/' + this.entityName + "/" + id + "/" + child,
                params: params
            };

            this.$http(config).success(function (data, status, headers, config) {
                if (status === 204) {
                    //El 204 (no content) realmente es un array vacio
                    deferred.resolve(null);
                } else {
                    deferred.resolve(data);
                }
            }).error(function (data, status, headers, config) {
                if (status === 400) {
                    deferred.reject(data);
                } else {
                    throw new Error("Fallo al obtener la entidad hija:" + status + "\n" + data);
                }
            });

            return deferred.promise;
        };

        this.metadata = function (expand) {
            var deferred = this.$q.defer();

            var params = {};
            if (expand) {
                params.$expand = expand;
            }

            var config = {
                method: 'GET',
                url: this.baseUrl + '/' + this.entityName + "/$metadata",
                params: params
            };

            this.$http(config).success(function (data, status, headers, config) {
                if (status === 204) {
                    //El 204 (no content) realmente es un null
                    deferred.resolve(null);
                } else {
                    deferred.resolve(data);
                }
            }).error(function (data, status, headers, config) {
                if (status === 400) {
                    deferred.reject(data);
                } else {
                    throw new Error("Fallo al obtener los metadatos:" + status + "\n" + data);
                }
            });

            return deferred.promise;
        };

    }

    RemoteDAOFactory.$inject = ['ix3Config', '$http', '$q'];
    function RemoteDAOFactory(ix3Config, $http, $q) {

        return {
            getRemoteDAO: getRemoteDAO
        }

        function getRemoteDAO(entityName) {
            return new RemoteDAO(entityName, ix3Config.server.api, $http, $q);
        }
    }

    angular.module("es.logongas.ix3").factory("remoteDAOFactory", RemoteDAOFactory);

})();