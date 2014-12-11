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
            
            if (typeof(object['$toString'])==="string") {
                //Definimos nuestra propia función "toString"
               object['toString']=toStringGlobal;
            }
        });
        
        
        
        
        
    }]);

angular.module('es.logongas.ix3').config(["$controllerProvider", "$compileProvider", "$filterProvider", "$provide", function($controllerProvider, $compileProvider, $filterProvider, $provide) {
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
