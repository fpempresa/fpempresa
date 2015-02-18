angular.module("common").config(['remoteDAOFactoryProvider',function(remoteDAOFactoryProvider) {
        
        remoteDAOFactoryProvider.addExtendRemoteDAO("Usuario",['remoteDAO',function(remoteDAO) {
            remoteDAO.updateEstadoUsuario=function(idIdentity,estadoUsuario,expand) {
                var deferred = this.$q.defer();

                var params = {};
                if (expand) {
                    params.$expand = expand;
                }

                var config = {
                    method: 'PATCH',
                    url: this.baseUrl + '/' + this.entityName + "/" + idIdentity + "/estadoUsuario/"+estadoUsuario,
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
                        throw new Error("Fallo al insertar la entidad:" + status + "\n" + data);
                    }
                });

                return deferred.promise;
            };
        }]);
        
}]);