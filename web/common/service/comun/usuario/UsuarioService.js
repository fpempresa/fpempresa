angular.module("common").config(['serviceFactoryProvider', function (serviceFactoryProvider) {

        serviceFactoryProvider.addExtendService("Usuario", ['service', '$q','richDomain', function (service, $q, richDomain) {
                service.updateEstadoUsuario = function (idIdentity, estadoUsuario,expand) {
                    var deferred = $q.defer();

                    this.repository.updateEstadoUsuario(idIdentity, estadoUsuario,expand).then(function (data) {
                        richDomain.extend(data);
                        deferred.resolve(data);
                    }, function (data) {
                        richDomain.extend(data);
                        deferred.reject(data);
                    });

                    return deferred.promise;

                };
                
                service.updatePassword = function (idIdentity, currentPassword,newPassword) {
                    var deferred = $q.defer();

                    this.repository.updatePassword(idIdentity, currentPassword,newPassword).then(function () {
                        deferred.resolve();
                    }, function (data) {
                        richDomain.extend(data);
                        deferred.reject(data);
                    });

                    return deferred.promise;

                };
                                
                
            }]);

    }]);