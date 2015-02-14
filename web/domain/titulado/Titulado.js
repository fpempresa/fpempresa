"use strict";

app.run(['richDomain','metadataEntities', function (richDomain,metadataEntities) {

        function getTipoDocumentoDescription() {
            return metadataEntities.getMetadataProperty(this.$propertyPath+".tipoDocumento").getValueDescription(this.tipoDocumento);
        };
       
        

        richDomain.addEntityTransformer("Titulado",function (className, object) {
            object['getTipoDocumentoDescription']=getTipoDocumentoDescription;
        });

        
    }]);