(function (undefined) {
    "use strict";
    function Metadata(repositoryFactory, $q) {
        //Aqui guardamos cada uno los metadatos de cada entidad
        var metadataCache = {
            entities: {},
            getBestExistsProperty: function (entityName, propertyName) {
                propertyName = propertyName || "";
                if (propertyName.indexOf(",") >= 0) {
                    throw new Error("No se permiten comas en el nombre de la propiedad");
                }

                var keys = propertyName.split('.');
                var propertyExists = "";
                var current = this.getMetadata(entityName);
                if (!((keys.length === 1) && (keys[0] === ""))) {
                    for (var i = 0; i < keys.length; i++) {
                        current = current.properties[keys[i]];
                        if (current === undefined) {
                            return propertyExists;
                        } else {
                            propertyExists = propertyExists + (propertyExists === "" ? "" : ".") + keys[i];
                        }
                    }
                }
                return propertyExists;
            },
            existsMetadata: function (entityName, expands) {
                var metadata = this.getMetadata(entityName);
                if (metadata === null) {
                    return false;
                }

                expands = expands || "";
                var arrExpand = expands.split(",");
                if (!((arrExpand.length === 1) && (arrExpand[0] === ""))) {
                    for (var i = 0; i < arrExpand.length; i++) {
                        if (metadata.getMetadataProperty(arrExpand[i]) === null) {
                            return false;
                        }
                    }
                }
                return true;
            },
            getMetadata: function (entityName) {
                var metadata = this.entities[entityName];
                if (metadata === undefined) {
                    return null;
                } else {
                    return metadata;
                }
            },
            addMetadata: function (entityName, metadata) {
                if (this.getMetadata(entityName) === null) {
                    this.entities[entityName] = metadata;
                }
            }
        };
        var metadata = {
            load: load,
            getMetadata: getMetadata
        };
        return metadata;
        function load(entityName, expands) {
            var deferred = $q.defer();
            var exists = metadataCache.existsMetadata(entityName, expands);
            if (exists === true) {
                deferred.resolve(metadataCache.getMetadata(entityName));
            } else {
                var repository = repositoryFactory.getRepository(entityName);
                repository.metadata(expands).then(function (newMetadata) {
                    //Ahora hay que fusionar lo que acaba de llegar con lo que hay
                    if (metadataCache.getMetadata(entityName) === null) {
                        //No hay nada así que añadimos todo lo que acabamos de cargar
                        metadataCache.addMetadata(entityName, newMetadata);
                    } else {
                        //Ahora hay que añadir parte por parte de lo que falta.
                        var currentMetadata = metadataCache.getMetadata(entityName);
                        expands = expands || "";
                        var arrExpand = expands.split(",");
                        if (!((arrExpand.length === 1) && (arrExpand[0] === ""))) {
                            for (var i = 0; i < arrExpand.length; i++) {
                                var expand = arrExpand[i];
                                //Vamos a comprobar el expand que falta
                                if (metadataCache.existsMetadata(entityName, expand) === false) {
                                    //La variable existExpand es la parte que si que existe
                                    var existExpand = metadataCache.getBestExistsProperty(entityName,expand);
                                    var nextProperty;
                                    if (existExpand === "") {
                                        nextProperty = expand;
                                    } else {
                                        nextProperty = expand.substring(existExpand.length + 1);
                                    }
                                    nextProperty = nextProperty.split(".")[0];
                                    currentMetadata.getMetadataProperty(existExpand).properties[nextProperty] = newMetadata.getMetadataProperty(existExpand).properties[nextProperty];
                                }
                            }
                        }

                    }


                    deferred.resolve(metadataCache.getMetadata(entityName));
                }, function (data) {
                    deferred.reject(data);
                });
            }

            return deferred.promise;
        }


        function getMetadata(entityName) {
            return metadataCache.getMetadata(entityName);
        }


    }
    Metadata.$inyect = ["repositoryFactory", "$q"];
    angular.module("es.logongas.ix3").factory("metadata", Metadata);
})();