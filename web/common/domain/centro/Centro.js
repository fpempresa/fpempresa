"use strict";

angular.module("common").config(['richDomainProvider', function (richDomain) {

        richDomain.addEntityTransformer("Centro", ['metadataEntities', function (metadataEntities) {
                var Centro = {
                    getEstadoCentroDescription: function () {
                        return metadataEntities.getMetadataProperty(this.$propertyPath + ".estadoCentro").getValueDescription(this.estadoCentro);
                    }
                };

                return function (object, propertyPath) {
                    object.$propertyPath=propertyPath;
                    angular.extend(object, Centro);
                };
            }]);

    }]);

