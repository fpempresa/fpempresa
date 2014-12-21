
"use strict";

app.run(['richDomain','metadataEntities', function (richDomain, metadataEntities) {
        
        function getNombreIdioma() {
            if (this.idioma==="OTRO") {
                return this.otroIdioma;
            } else {
                return this.idioma;
            }
        }            

        function getNivelIdiomaDescription() {
            return metadataEntities.getMetadataProperty(this.$propertyPath+".nivelIdioma").getValueDescription(this.nivelIdioma);
        }

        richDomain.addEntityTransformer("TituloIdioma",function (className, object) {
            object['getNombreIdioma']=getNombreIdioma;
            object['getNivelIdiomaDescription']=getNivelIdiomaDescription;
        });
        
        
        
        
        
        
    }]);