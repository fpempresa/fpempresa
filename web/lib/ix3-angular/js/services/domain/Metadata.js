"use strict";

angular.module('es.logongas.ix3').run(['richDomain', 'langUtil', function (richDomain, langUtil) {

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



        //Funciones de utilidades de los metadatos
        richDomain.addEntityTransformer("Metadata", function (className, object) {
            object['getMetadataProperty'] = getMetadataProperty;
        });




    }]);