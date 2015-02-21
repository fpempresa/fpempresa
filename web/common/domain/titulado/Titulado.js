"use strict";

angular.module("common").config(['richDomainProvider', function (richDomain) {

        richDomain.addEntityTransformer("Titulado", ['metadataEntities', function (metadataEntities) {
                var Titulado = {
                    getTipoDocumentoDescription: function () {
                        return metadataEntities.getMetadataProperty(this.$propertyPath + ".tipoDocumento").getValueDescription(this.tipoDocumento);
                    }
                };

                return function (object, propertyPath) {
                    object.$propertyPath=propertyPath;                    
                    angular.extend(object, Titulado);
                };
            }]);

    }]);