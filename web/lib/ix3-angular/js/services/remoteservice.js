"use strict";

angular.module("es.logongas.ix3").provider("remoteServiceFactory", [function() {
        this.baseURL;
        this.setBaseURL = function(baseURL) {
            if (!baseURL) {
                throw Error("El argumento baseURL no puede estar vacío");
            }
            if (typeof (baseURL) !== "string") {
                throw Error("El argumento baseURL debe ser un String");
            }

            this.baseURL = baseURL;
        };

        /**
         * Esta es la clase RemoteService verdadera que genera el Factory
         * @param {String} entityName Nombre de la entidad 
         * @param {String} baseURL La url en la que se encuentran los servicios.
         */
        function RemoteService(entityName, baseURL,$http,$q) {
            this.entityName = entityName;
            this.baseURL = baseURL;
            this.$http=$http;
            this.$q=$q;
        }

        RemoteService.prototype.create = function(expand, parent) {
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
                url: this.baseURL + '/' + this.entityName + "/$create",
                params: params
            };

            this.$http(config).success(function(data, status, headers, config) {
                deferred.resolve(data);
            }).error(function(data, status, headers, config) {
                if (status === 400) {
                    deferred.reject(data);
                } else {
                    throw new Error("Fallo al crear la entidad los datos:" + status + "\n" + data);
                }
            });

            return deferred.promise;
        };
        RemoteService.prototype.get = function(id, expand) {
            var deferred = this.$q.defer();

            var params = {};
            if (expand) {
                params.$expand = expand;
            }

            var config = {
                method: 'GET',
                url: this.baseURL + '/' + this.entityName + "/" + id,
                params: params
            };

            this.$http(config).success(function(data, status, headers, config) {
                deferred.resolve(data);
            }).error(function(data, status, headers, config) {
                if (status === 400) {
                    deferred.reject(data);
                } else {
                    throw new Error("Fallo al obtener la entidad:" + status + "\n" + data);
                }
            });

            return deferred.promise;
        };
        RemoteService.prototype.insert = function(entity,  expand) {
            var deferred = this.$q.defer();

            var params = {};
            if (expand) {
                params.$expand = expand;
            }

            var config = {
                method: 'POST',
                url: this.baseURL + '/' + this.entityName + "/",
                params: params,
                data:entity
            };

            this.$http(config).success(function(data, status, headers, config) {
                deferred.resolve(data);
            }).error(function(data, status, headers, config) {
                if (status === 400) {
                    deferred.reject(data);
                } else {
                    throw new Error("Fallo al insertar la entidad:" + status + "\n" + data);
                }
            });

            return deferred.promise;
        };
        RemoteService.prototype.update = function(id, entity, fnOK, fnError, expand) {
            var deferred = this.$q.defer();

            var params = {};
            if (expand) {
                params.$expand = expand;
            }

            var config = {
                method: 'PUT',
                url: this.baseURL + '/' + this.entityName + "/" + id,
                params: params,
                data:entity
            };

            this.$http(config).success(function(data, status, headers, config) {
                deferred.resolve(data);
            }).error(function(data, status, headers, config) {
                if (status === 400) {
                    deferred.reject(data);
                } else {
                    throw new Error("Fallo al insertar la entidad:" + status + "\n" + data);
                }
            });

            return deferred.promise;
        };
        RemoteService.prototype.delete = function(id) {
            var deferred = this.$q.defer();

            var params = {};

            var config = {
                method: 'DELETE',
                url: this.baseURL + '/' + this.entityName + "/" + id,
                params: params
            };

            this.$http(config).success(function(data, status, headers, config) {
                deferred.resolve(data);
            }).error(function(data, status, headers, config) {
                if (status === 400) {
                    deferred.reject(data);
                } else {
                    throw new Error("Fallo al borrar la entidad:" + status + "\n" + data);
                }
            });

            return deferred.promise;
        };
        RemoteService.prototype.search = function(filter, order, expand, pageNumber, pageSize) {
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
                url: this.baseURL + '/' + this.entityName,
                params: params
            };

            this.$http(config).success(function(data, status, headers, config) {
                deferred.resolve(data);
            }).error(function(data, status, headers, config) {
                if (status === 400) {
                    deferred.reject(data);
                } else {
                    throw new Error("Fallo al buscar los datos:" + status + "\n" + data);
                }
            });

            return deferred.promise;
        };

        RemoteService.prototype.getChild = function(id, child, expand) {
            var deferred = this.$q.defer();

            var params = {};
            if (expand) {
                params.$expand = expand;
            }

            var config = {
                method: 'GET',
                url: this.baseURL + '/' + this.entityName + "/" + id + "/" + child,
                params: params
            };

            this.$http(config).success(function(data, status, headers, config) {
                deferred.resolve(data);
            }).error(function(data, status, headers, config) {
                if (status === 400) {
                    deferred.reject(data);
                } else {
                    throw new Error("Fallo al obtener la entidad hija:" + status + "\n" + data);
                }
            });

            return deferred.promise;
        };

        RemoteService.prototype.metadata = function(expand) {
            var deferred = this.$q.defer();

            var params = {};
            if (expand) {
                params.$expand = expand;
            }

            var config = {
                method: 'GET',
                url: this.baseURL + '/' + this.entityName + "/$metadata",
                params: params
            };

            this.$http(config).success(function(data, status, headers, config) {
                deferred.resolve(data);
            }).error(function(data, status, headers, config) {
                if (status === 400) {
                    deferred.reject(data);
                } else {
                    throw new Error("Fallo al obtener los metadatos:" + status + "\n" + data);
                }
            });

            return deferred.promise;
        };

        this.$get = ['$http','$q',function($http,$q) {
                var baseURL=this.baseURL;
                return {
                    getRemoteService: function(entityName) {
                        var remoteService = new RemoteService(entityName,baseURL,$http,$q);
                        return remoteService;
                    }
                };

            }];

    }]);




