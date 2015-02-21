"use strict";

angular.module("common").config(['richDomainProvider', function (richDomain) {

        richDomain.addEntityTransformer("Direccion", ['metadataEntities', function (metadataEntities) {
                var Direccion = {
                    getTipoViaDescription: function () {
                        return metadataEntities.getMetadataProperty(this.$propertyPath + ".tipoVia").getValueDescription(this.tipoVia);
                    }
                };

                return function (object, propertyPath) {
                    object.$propertyPath=propertyPath;                    
                    angular.extend(object, Direccion);
                };
            }]);

    }]);