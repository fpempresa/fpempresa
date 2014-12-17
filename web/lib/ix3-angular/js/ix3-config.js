"use strict";



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
