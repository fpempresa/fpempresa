angular.module("common").config(['serviceFactoryProvider', function (serviceFactoryProvider) {

        serviceFactoryProvider.addExtendService("Titulado", ['service', '$q','repositoryFactory', function (service, $q, repositoryFactory) {
                
                service.usuarioRepository=repositoryFactory.getRepository("Usuario");
                                
                
                service.get = function (id, expand) {
                    var deferred = $q.defer();

                    service.repository.get(id, expand).then(function (titulado) {
                        
                        service.usuarioRepository.search({"titulado.idTitulado":titulado.idTitulado}).then(function (usuarios) {

                            if (usuarios.length>1) {
                                throw new Error("Existe mas de un Usuario para el titulado:" + titulado.idTitulado);
                            }

                            if (usuarios.length===1) {
                                titulado.usuario=usuarios[0];
                            }
                            
                            deferred.resolve(titulado);

                        }, function (data) {
                            deferred.reject(data);
                        });

                        
                    }, function (data) {
                        deferred.reject(data);
                    });

                    return deferred.promise;
                };
                
            }]);

    }]);
