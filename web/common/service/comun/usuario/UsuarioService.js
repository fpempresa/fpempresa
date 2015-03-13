angular.module("common").config(['serviceFactoryProvider', function (serviceFactoryProvider) {

        serviceFactoryProvider.addExtendService("Usuario", ['service', '$q', function (service, $q) {
                service.updateEstadoUsuario = function (idIdentity, estadoUsuario,expand) {
                    var deferred = $q.defer();

                    this.repository.updateEstadoUsuario(idIdentity, estadoUsuario,expand).then(function (data) {
                        deferred.resolve(data);
                    }, function (data) {
                        deferred.reject(data);
                    });

                    return deferred.promise;
                };
                
                service.updatePassword = function (idIdentity, currentPassword,newPassword) {
                    var deferred = $q.defer();

                    this.repository.updatePassword(idIdentity, currentPassword,newPassword).then(function () {
                        deferred.resolve();
                    }, function (data) {
                        deferred.reject(data);
                    });

                    return deferred.promise;
                };
                  
                
            }]);

    }]);