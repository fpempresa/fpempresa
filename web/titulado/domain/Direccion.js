"use strict";

app.run(['richDomain','metadataEntities', function (richDomain,metadataEntities) {

        function getTipoViaDescription() {
            return metadataEntities.getMetadataProperty(this.$propertyPath+".tipoVia").getValueDescription(this.tipoVia);
        };
       
        

        richDomain.addEntityTransformer("Direccion",function (className, object) {
            object['getTipoViaDescription']=getTipoViaDescription;
        });

        
    }]);