"use strict";

angular.module('es.logongas.ix3').run(['richDomain', function (richDomain) {

        //Para transformar los Strign en fechas
        richDomain.addGlobalTransformer(function (className, object) {
            for (var key in object) {
                if (!object.hasOwnProperty(key)) {
                    continue;
                }
                var value = object[key];
                if ((typeof value === "string") && (value.length === 28)) {
                    var date = moment(value, "YYYY-MM-DDTHH:mm:ss.SSSZZ", true);
                    if (date.isValid()) {
                        object[key] = date.toDate();
                    }
                }
            }
        });

        function toStringGlobal() {
            return this["$toString"];
        }

        //Añadir el método toString() para que use la propiedad "$toString", pero solo si existe
        richDomain.addGlobalTransformer(function (className, object) {

            if (typeof (object['$toString']) === "string") {
                //Definimos nuestra propia función "toString"
                object['toString'] = toStringGlobal;
            }
        });



        //Obtener los metadatos de una propiedad
        function getMetadataProperty(propertyName) {
            propertyName = propertyName || "";
            if (propertyName.indexOf(",") >= 0) {
                throw new Error("No se permiten comas en el nombre de la propiedad");
            }

            var keys = propertyName.split('.');
            var current = this;

            if (!((keys.length === 1) && (keys[0] === ""))) {
                for (var i = 0; i < keys.length; i++) {
                    current = current.properties[keys[i]];

                    if (current === undefined) {
                        return null;
                    }
                }
            }
            if (current === undefined) {
                return null;
            } else {
                return current;
            }
        }



        //Funciones de utilidades de los metadatos
        richDomain.addEntityTransformer("Metadata", function (className, object) {
            object['getMetadataProperty'] = getMetadataProperty;
        });


        function getValueDescription(value) {
            if (value) {
                if ((this.dependProperty === null) && (angular.isArray(this.values))) {
                    var description = undefined;
                    var values = this.values;
                    if (this.type === "OBJECT") {
                        for (var i = 0; i < values.length; i++) {
                            if (values[i][this.primaryKeyPropertyName] === value) {
                                description = values[i].toString();
                                break;
                            }
                        }
                    } else {
                        for (var i = 0; i < values.length; i++) {
                            if (values[i].key === value) {
                                description = values[i].description;
                                break;
                            }
                        }
                    }

                    if (typeof (description) === "undefined") {
                        return value;
                    } else {
                        return description;
                    }
                } else {
                    return value;
                }
            } else {
                return value;
            }
        }


        richDomain.addEntityTransformer("Property", function (className, object) {
            object['getValueDescription'] = getValueDescription;
        });



    }]);