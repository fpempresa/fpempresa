"use strict";

angular.module('es.logongas.ix3').config(['repositoryFactoryProvider', function (repositoryFactoryProvider) {

        //Para transformar los Strign en fechas
        repositoryFactoryProvider.addGlobalTransformer(function (className, object) {
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
        repositoryFactoryProvider.addGlobalTransformer(function (className, object) {

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
            
            if (!((keys.length===1) && (keys[0]===""))) {
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
        repositoryFactoryProvider.addEntityTransformer("Metadata", function (className, object) {
            object['getMetadataProperty'] = getMetadataProperty;
        });


    }]);

angular.module('es.logongas.ix3').config(["$controllerProvider", "$compileProvider", "$filterProvider", "$provide", function ($controllerProvider, $compileProvider, $filterProvider, $provide) {
        //Esto es para poder cargar a posteriori recursos de angular.
        //Se usa en las ventanas modales.
        angular.lazy = {
            controller: $controllerProvider.register,
            directive: $compileProvider.directive,
            filter: $filterProvider.register,
            factory: $provide.factory,
            service: $provide.service
        };
    }]);
