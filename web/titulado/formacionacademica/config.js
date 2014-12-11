
"use strict";

angular.module('es.logongas.ix3').config(['repositoryFactoryProvider', function (repositoryFactoryProvider) {
        
        function getNombreCentro() {
            if (this.tipoFormacionAcademica === "CICLO_FORMATIVO") {
                if (this.centro.idCentro < 0) {
                    return this.otroCentro;
                } else {
                    return this.centro.toString();
                }
            } else {
                return this.otroCentro;
            }
        };
        function getNombreTitulo() {
            if (this.tipoFormacionAcademica === "CICLO_FORMATIVO") {
                return this.ciclo.toString();
            } else {
                return this.otroTitulo;
            }
        };      
        
        function toStringFormacionAcademica() {
            return this.getNombreTitulo() + "-" + this.getNombreCentro();
        };        
        

        repositoryFactoryProvider.addEntityTransformer("FormacionAcademica",function (className, object) {
            object['toString']=toStringFormacionAcademica;
            object['getNombreTitulo']=getNombreTitulo;
            object['getNombreCentro']=getNombreCentro;
        });
        
        
        
        
        
        
    }]);