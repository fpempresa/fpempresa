(function (undefined) {
    "use strict";

    function Metadata(repositoryFactory, $q) {
        //Aqui guardamos cada uno los metadatos de cada entidad
        var metadatasStorage = new MetadatasStorage();

        var metadata = {
            load: load,
            getMetadata: getMetadata
        };

        return metadata;


        function load(entityName, expands) {
            var deferred = $q.defer();

            var arrExpands = splitValues(expands, ",");

            var exists = metadatasStorage.existsMetadata(entityName, arrExpands);
            if (exists === true) {
                deferred.resolve(metadatasStorage.getMetadata(entityName));
            } else {
                var repository = repositoryFactory.getRepository(entityName);
                repository.metadata(expands).then(function (newMetadata) {
                    //Ahora hay que fusionar lo que acaba de llegar con lo que hay
                    if (metadatasStorage.getMetadata(entityName) === null) {
                        //No hay nada así que añadimos todo lo que acabamos de cargar
                        metadatasStorage.addMetadata(entityName, newMetadata);
                    } else {
                        //Ahora hay que añadir parte por parte de lo que falta.
                        var currentMetadata = metadatasStorage.getMetadata(entityName);

                        for (var i = 0; i < arrExpands.length; i++) {
                            var expand = arrExpands[i];
                            //Vamos a comprobar el expand que falta
                            if (metadatasStorage.existsMetadata(entityName, expand) === false) {
                                //La variable existExpand es la parte que si que existe
                                var existExpand = metadatasStorage.getBestExistsProperty(entityName, expand);
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


                    deferred.resolve(metadatasStorage.getMetadata(entityName));
                }, function (data) {
                    deferred.reject(data);
                });
            }

            return deferred.promise;
        }


        function getMetadata(entityName) {
            return metadatasStorage.getMetadata(entityName);
        }


    }
    Metadata.$inyect = ["repositoryFactory", "$q"];


    angular.module("es.logongas.ix3").factory("metadata", Metadata);



    /**
     * Clase para gestionar varios objetos Metadata
     * Y guarda cada uno de los metadatos de cada entidad.
     */
    function MetadatasStorage() {

        /**
         * Aqui guardamos cada uno de los metadatos de cada entidad
         */
        this.entities = {};

        this.addMetadata = function (entityName, metadata) {
            if (this.getMetadata(entityName) === null) {
                this.entities[entityName] = metadata;
            }
        };

        this.getMetadata = function (entityName) {
            var metadata = this.entities[entityName];
            if (metadata === undefined) {
                return null;
            } else {
                return metadata;
            }
        };


        /**
         * 
         * @param {String} entityName Nombre de la entidad
         * @param {Array[String]} arrExpands Los expandido que debe estar el metadata. Se permiten varios valores , por eso es un array
         * @returns {Boolean} Si esta entidad existe y está expanjdida como dicen todos y cada uno de los valores de "expands"
         */
        this.existsMetadata = function (entityName, arrExpands) {
            var metadata = this.getMetadata(entityName);
            if (metadata === null) {
                return false;
            }

            for (var i = 0; i < arrExpands.length; i++) {
                if (metadata.getMetadataProperty(arrExpands[i]) === null) {
                    return false;
                }
            }

            return true;
        };

        /**
         * Dado un "expand" obtiene el "expand" mas cercado que tiene esa propiedad.
         * Sirva para saber que parte ya está cargada de ese expand.
         * @param {type} entityName Nombre de la entidad
         * @param {type} propertyName Es como expand pero con un solo valor.
         * @returns {String}
         */
        this.getBestExistsProperty = function (entityName, propertyName) {
            propertyName = propertyName || "";
            if (propertyName.indexOf(",") >= 0) {
                throw new Error("No se permiten comas en el nombre de la propiedad");
            }

            var propertyExists = "";
            var current = this.getMetadata(entityName);

            var keys = splitValues(propertyName, ".");
            for (var i = 0; i < keys.length; i++) {
                current = current.properties[keys[i]];
                if (current === undefined) {
                    return propertyExists;
                } else {
                    //El operador ternario es para el primer elemento que no lleva "." al concatenar.
                    propertyExists = propertyExists + (propertyExists === "" ? "" : ".") + keys[i];
                }
            }

            return propertyExists;
        };


    }

    /**
     * Hace un split pero si solo hay un elemento en el array y es "", retorna un array vacio.
     * Si no hay nada retorna un array sin elementos.
     * @param {String} values
     * @param {String} separator El separador
     * @returns {Array[String]}
     */
    function splitValues(values, separator) {
        values = values || "";
        var arrValues = values.split(separator);
        if ((arrValues.length === 1) && (arrValues[0] === "")) {
            return [];
        } else {
            return arrValues;
        }
    }


})();