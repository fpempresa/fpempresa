(function (undefined) {
    "use strict";

    /**
     * Esta es la clase Repository verdadera que genera el RepositoryFactory
     * @param {String} entityName Nombre de la entidad 
     * @param {remoteDAO} remoteDAO El DAO para acceder a los datos
     * @param {Transformers} transformers funciones para transformar los objetos
     * @param {Http} $http Servicio de Http de AngularJS
     * @param {Q} $q Servicio de promesas de AngularJS
     */
    function Repository(entityName, remoteDAO, transformers, $http, $q) {
        var that = this;
        this.entityName = entityName;
        this.remoteDAO = remoteDAO;
        this.transformers = transformers;
        this.$http = $http;
        this.$q = $q;


        this.create = function (expand, parent) {
            return this.remoteDAO.create(expand, parent);
        };
        this.get = function (id, expand) {
            var deferred = this.$q.defer();

            this.remoteDAO.get(id, expand).then(function (data) {
                transform(data, that.transformers);
                deferred.resolve(data);
            }, function (data) {
                transform(data, that.transformers);
                deferred.reject(data);
            });

            return deferred.promise;
        };
        this.insert = function (entity, expand) {
            var deferred = this.$q.defer();

            this.remoteDAO.insert(entity, expand).then(function (data) {
                transform(data, that.transformers);
                deferred.resolve(data);
            }, function (data) {
                transform(data, that.transformers);
                deferred.reject(data);
            });

            return deferred.promise;            
        };
        this.update = function (id, entity, expand) {
            var deferred = this.$q.defer();

            this.remoteDAO.update(id, entity, expand).then(function (data) {
                transform(data, that.transformers);
                deferred.resolve(data);
            }, function (data) {
                transform(data, that.transformers);
                deferred.reject(data);
            });

            return deferred.promise;             
        };
        this.delete = function (id) {
            var deferred = this.$q.defer();

            this.remoteDAO.delete(id).then(function (data) {
                transform(data, that.transformers);
                deferred.resolve(data);
            }, function (data) {
                transform(data, that.transformers);
                deferred.reject(data);
            });

            return deferred.promise;             
        };
        this.search = function (filter, order, expand, pageNumber, pageSize) {
            var deferred = this.$q.defer();

            this.remoteDAO.search(filter, order, expand, pageNumber, pageSize).then(function (data) {
                transform(data, that.transformers);
                deferred.resolve(data);
            }, function (data) {
                transform(data, that.transformers);
                deferred.reject(data);
            });

            return deferred.promise;             
        };
        this.getChild = function (id, child, expand) {
            var deferred = this.$q.defer();

            this.remoteDAO.getChild(id, child, expand).then(function (data) {
                transform(data, that.transformers);
                deferred.resolve(data);
            }, function (data) {
                transform(data, that.transformers);
                deferred.reject(data);
            });

            return deferred.promise;             
        };

        this.metadata = function (expand) {
            var deferred = this.$q.defer();

            this.remoteDAO.metadata(expand).then(function (data) {
                transform(data, that.transformers);
                deferred.resolve(data);
            }, function (data) {
                transform(data, that.transformers);
                deferred.reject(data);
            });

            return deferred.promise;             
        };

        function transform(object, transformers) {
            if ((typeof (object) === "object") && (object!==null)) {
                for (var key in object) {
                    if (!object.hasOwnProperty(key)) {
                        continue;
                    }
                    var value = object[key];
                    if (typeof (value) === "object") {
                        transform(value, transformers);
                    } else if (angular.isArray(value)) {
                        transform(value, transformers);
                    }
                }
                applyTransforms(object, transformers);
            } else if (angular.isArray(object)) {
                for (var i = 0; i < object.length; i++) {
                    transform(object[i], transformers);
                }

            }

        }

        function applyTransforms(object, transformers) {
            var className = object['$className'];

            //aplicamos los transformadores globales
            for (var i = 0; i < transformers.global.length; i++) {
                transformers.global[i](className, object);
            }

            if (className) {
                console.log(className);
                var entityTransformers = transformers.entity[className];
                if (entityTransformers) {

                    for (var i = 0; i < transformers.entity[className].length; i++) {
                        transformers.entity[className][i](className, object);
                    }
                }
            }
        }


    }

    function RepositoryFactory(remoteDAOFactory, transformers, $http, $q) {
        this.getRepository = function (entityName) {
            var remoteDAO = remoteDAOFactory.getRemoteDAO(entityName);

            return new Repository(entityName, remoteDAO, transformers, $http, $q);
        };
    }


    function RepositoryFactoryProvider() {

        this.transformers = {
            entity: {},
            global: []
        }


        this.addEntityTransformer = function (entityName, transformer) {
            if (!this.transformers.entity[entityName]) {
                this.transformers.entity[entityName] = [];
            }

            this.transformers.entity[entityName].push(transformer);
        }

        this.addGlobalTransformer = function (transformer) {
            this.transformers.global.push(transformer);
        }

        this.$get = ['remoteDAOFactory', '$http', '$q', function (remoteDAOFactory, $http, $q) {
                return new RepositoryFactory(remoteDAOFactory, this.transformers, $http, $q);
            }];
    }

    angular.module("es.logongas.ix3").provider("repositoryFactory", RepositoryFactoryProvider);

})();


