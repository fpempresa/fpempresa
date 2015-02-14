
"use strict";

angular.module("common").run(['richDomain','metadataEntities', function (richDomain,metadataEntities) {
        
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
        
        function getTipoFormacionAcademicaDescription() {
            return metadataEntities.getMetadataProperty(this.$propertyPath+".tipoFormacionAcademica").getValueDescription(this.tipoFormacionAcademica);
        }

        richDomain.addEntityTransformer("FormacionAcademica",function (className, object) {
            object['toString']=toStringFormacionAcademica;
            object['getNombreTitulo']=getNombreTitulo;
            object['getNombreCentro']=getNombreCentro;
            object['getTipoFormacionAcademicaDescription']=getTipoFormacionAcademicaDescription;
        });
        
        
        
        
        
        
    }]);