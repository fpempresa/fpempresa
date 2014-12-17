(function (undefined) {
    "use strict";

    /**
     * Esta es la clase Repository verdadera que genera el RepositoryFactory
     * @param {String} entityName Nombre de la entidad 
     * @param {remoteDAO} remoteDAO El DAO para acceder a los datos
     * @param {RichDomain} richDomain Añadir nuevos métodos a los objetos de negocio que se leen
     * @param {Http} $http Servicio de Http de AngularJS
     * @param {Q} $q Servicio de promesas de AngularJS
     */
    function Repository(entityName, remoteDAO, richDomain, $http, $q) {
        var that = this;
        this.entityName = entityName;
        this.remoteDAO = remoteDAO;
        this.richDomain = richDomain;
        this.$http = $http;
        this.$q = $q;


        this.create = function (expand, parent) {
            return this.remoteDAO.create(expand, parent);
        };
        this.get = function (id, expand) {
            var deferred = this.$q.defer();

            this.remoteDAO.get(id, expand).then(function (data) {
                richDomain.extend(data);
                deferred.resolve(data);
            }, function (data) {
                richDomain.extend(data);
                deferred.reject(data);
            });

            return deferred.promise;
        };
        this.insert = function (entity, expand) {
            var deferred = this.$q.defer();

            this.remoteDAO.insert(entity, expand).then(function (data) {
                richDomain.extend(data);
                deferred.resolve(data);
            }, function (data) {
                richDomain.extend(data);
                deferred.reject(data);
            });

            return deferred.promise;            
        };
        this.update = function (id, entity, expand) {
            var deferred = this.$q.defer();

            this.remoteDAO.update(id, entity, expand).then(function (data) {
                richDomain.extend(data);
                deferred.resolve(data);
            }, function (data) {
                richDomain.extend(data);
                deferred.reject(data);
            });

            return deferred.promise;             
        };
        this.delete = function (id) {
            var deferred = this.$q.defer();

            this.remoteDAO.delete(id).then(function (data) {
                richDomain.extend(data);
                deferred.resolve(data);
            }, function (data) {
                richDomain.extend(data);
                deferred.reject(data);
            });

            return deferred.promise;             
        };
        this.search = function (filter, order, expand, pageNumber, pageSize) {
            var deferred = this.$q.defer();

            this.remoteDAO.search(filter, order, expand, pageNumber, pageSize).then(function (data) {
                richDomain.extend(data);
                deferred.resolve(data);
            }, function (data) {
                richDomain.extend(data);
                deferred.reject(data);
            });

            return deferred.promise;             
        };
        this.getChild = function (id, child, expand) {
            var deferred = this.$q.defer();

            this.remoteDAO.getChild(id, child, expand).then(function (data) {
                richDomain.extend(data);
                deferred.resolve(data);
            }, function (data) {
                richDomain.extend(data);
                deferred.reject(data);
            });

            return deferred.promise;             
        };

        this.metadata = function (expand) {
            var deferred = this.$q.defer();

            this.remoteDAO.metadata(expand).then(function (data) {
                richDomain.extend(data);
                deferred.resolve(data);
            }, function (data) {
                richDomain.extend(data);
                deferred.reject(data);
            });

            return deferred.promise;             
        };


    }

    RepositoryFactory.$inyect=['remoteDAOFactory', 'richDomain', '$http', '$q'];
    function RepositoryFactory(remoteDAOFactory, richDomain, $http, $q) {
        this.getRepository = function (entityName) {
            var remoteDAO = remoteDAOFactory.getRemoteDAO(entityName);

            return new Repository(entityName, remoteDAO, richDomain, $http, $q);
        };
    }



    angular.module("es.logongas.ix3").service("repositoryFactory", RepositoryFactory);

})();


