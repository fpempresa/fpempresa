(function () {

    "use strict";

    angular.module("common").config(['serviceFactoryProvider', function (serviceFactoryProvider) {

            serviceFactoryProvider.addExtendService("Titulado", ['service', '$q', 'repositoryFactory', 'langUtil', function (service, $q, repositoryFactory, langUtil) {

                    service.usuarioRepository = repositoryFactory.getRepository("Usuario");

                    function getExpands(expands) {
                        var tituladoExpand = [];
                        var usuarioExpand = [];
                        var expandUsuario = true; //Indica si hay o no que cargar el usuario. siempre vale TRUE pq siempre se expandiria desde el servidor

                        if (expands) {
                            var arrExpands = langUtil.splitValues(expands, ",");

                            for (var i = 0; i < arrExpands.length; i++) {
                                var expand = arrExpands[i];

                                if (expand.indexOf("usuario.")===0) {
                                    expandUsuario = true;

                                    //Le he hemos quedatos la parte de "usuario."
                                    usuarioExpand.push(expand.substr(8));

                                } else if (expand === "usuario") {
                                    expandUsuario = true;
                                } else {
                                    tituladoExpand.push(expand);
                                }
                            }
                        }

                        return {
                            tituladoExpand: tituladoExpand.join(","),
                            usuarioExpand: usuarioExpand.join(","),
                            expandUsuario: expandUsuario
                        }

                    }


                    service.get = function (id, expand) {
                        var deferred = $q.defer();

                        var expands = getExpands(expand);

                        service.repository.get(id, expands.tituladoExpand).then(function (titulado) {

                            if (expands.expandUsuario) {
                                service.usuarioRepository.search({"titulado.idTitulado": titulado.idTitulado}, undefined, expands.usuarioExpand).then(function (usuarios) {

                                    if (usuarios.length > 1) {
                                        throw new Error("Existe mas de un Usuario para el titulado:" + titulado.idTitulado);
                                    }

                                    if (usuarios.length === 1) {
                                        titulado.usuario = usuarios[0];
                                    }

                                    deferred.resolve(titulado);

                                }, function (data) {
                                    deferred.reject(data);
                                });
                            } else {
                                deferred.resolve(titulado);
                            }


                        }, function (data) {
                            deferred.reject(data);
                        });

                        return deferred.promise;
                    };

                }]);

        }]);

})();