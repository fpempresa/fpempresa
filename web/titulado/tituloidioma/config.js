
"use strict";

angular.module('es.logongas.ix3').config(['repositoryFactoryProvider', function (repositoryFactoryProvider) {
        
        function getNombreIdioma() {
            if (this.idioma==="OTRO") {
                return this.otroIdioma;
            } else {
                return this.idioma;
            }
        }            

        repositoryFactoryProvider.addEntityTransformer("TituloIdioma",function (className, object) {
            object['getNombreIdioma']=getNombreIdioma;
        });
        
        
        
        
        
        
    }]);