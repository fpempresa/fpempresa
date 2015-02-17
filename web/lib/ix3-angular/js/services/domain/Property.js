"use strict";

angular.module('es.logongas.ix3').run(['richDomain', 'langUtil', function (richDomain, langUtil) {



        function getValueDescription(value) {
            if (value) {
                if (angular.isArray(this.values)) {
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
        
        //Obtener los metadatos de una propiedad
        function getMetadataProperty(propertyName) {
            propertyName = propertyName || "";
            if (propertyName.indexOf(",") >= 0) {
                throw new Error("No se permiten comas en el nombre de la propiedad");
            }

            var keys = langUtil.splitValues(propertyName, ".");
            var current = this;
            for (var i = 0; i < keys.length; i++) {
                current = current.properties[keys[i]];

                if (current === undefined) {
                    break;
                }
            }
            if (current === undefined) {
                return null;
            } else {
                return current;
            }
        }


        richDomain.addEntityTransformer("Property", function (className, object) {
            object['getValueDescription'] = getValueDescription;
            object['getMetadataProperty'] = getMetadataProperty;
        });



    }]);