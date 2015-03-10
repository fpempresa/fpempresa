angular.module("common").config(['repositoryFactoryProvider', function (repositoryFactoryProvider) {

        repositoryFactoryProvider.addExtendRepository("Usuario", ['repository', '$q','richDomain', function (repository, $q, richDomain) {
                repository.updateEstadoUsuario = function (idIdentity, estadoUsuario,expand) {
                    var deferred = $q.defer();

                    this.remoteDAO.updateEstadoUsuario(idIdentity, estadoUsuario,expand).then(function (data) {
                        richDomain.extend(data);
                        deferred.resolve(data);
                    }, function (data) {
                        richDomain.extend(data);
                        deferred.reject(data);
                    });

                    return deferred.promise;

                };
            }]);

    }]);