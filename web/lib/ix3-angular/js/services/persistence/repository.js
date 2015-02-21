(function (undefined) {
    "use strict";

    /**
     * Esta es la clase Repository verdadera que genera el RepositoryFactory
     * @param {String} entityName Nombre de la entidad 
     * @param {remoteDAO} remoteDAOFactory La factoria del DAO para acceder a los datos
     * @param {RichDomain} richDomain Añadir nuevos métodos a los objetos de negocio que se leen
     * @param {Q} $q Servicio de promesas de AngularJS
     */
    Repository.$inyect = ['entityName', 'remoteDAOFactory', 'richDomain', '$q'];
    function Repository(entityName, remoteDAOFactory, richDomain, $q) {
        var that = this;
        this.$q = $q;
        this.entityName = entityName;
        this.remoteDAO = remoteDAOFactory.getRemoteDAO(this.entityName);
        this.richDomain = richDomain;


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


    RepositoryFactory.$inyect = ['extendRepository', '$injector'];
    function RepositoryFactory(extendRepository, $injector) {

        var repositories = {
        };

        this.getRepository = function (entityName) {

            if (!repositories[entityName]) {
                var locals = {
                    entityName: entityName
                }
                repositories[entityName] = $injector.instantiate(Repository, locals)

                if (extendRepository[entityName]) {
                    var locals = {
                        repository: repositories[entityName]
                    };

                    for(var i=0;i<extendRepository[entityName].length;i++) {
                        $injector.invoke(extendRepository[entityName][i], undefined, locals);
                    }
                }

            }

            return repositories[entityName];
        }
    }


    RepositoryFactoryProvider.$inyect = [];
    function RepositoryFactoryProvider() {

        var extendRepository = {
        };

        this.addExtendRepository = function (entityName, fn) {
            if (!extendRepository[entityName]) {
                extendRepository[entityName]=[];
            }
            extendRepository[entityName].push(fn);
        };

        this.$get = ['$injector', function ($injector) {
                var locals = {
                    extendRepository: extendRepository
                };
                return $injector.instantiate(RepositoryFactory, locals);
            }];

    }



    angular.module("es.logongas.ix3").provider("repositoryFactory", RepositoryFactoryProvider);

})();


