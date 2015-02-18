angular.module("common").config(['repositoryFactoryProvider', function (repositoryFactoryProvider) {

        repositoryFactoryProvider.addExtendRepository("Usuario", ['repository', function (repository) {
                repository.updateEstadoUsuario = function (idIdentity, estadoUsuario,expand) {
                    var that=this;
                    var deferred = this.$q.defer();

                    this.remoteDAO.updateEstadoUsuario(idIdentity, estadoUsuario,expand).then(function (data) {
                        that.richDomain.extend(data);
                        deferred.resolve(data);
                    }, function (data) {
                        that.richDomain.extend(data);
                        deferred.reject(data);
                    });

                    return deferred.promise;

                };
            }]);

    }]);