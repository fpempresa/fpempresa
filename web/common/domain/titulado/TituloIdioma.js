"use strict";

angular.module("common").config(['richDomainProvider', function (richDomain) {

        richDomain.addEntityTransformer("TituloIdioma", ['metadataEntities', function (metadataEntities) {
                var TituloIdioma = {
                    getNombreIdioma: function () {
                        if (this.idioma === "OTRO") {
                            return this.otroIdioma;
                        } else {
                            return metadataEntities.getMetadataProperty(this.$propertyPath + ".idioma").getValueDescription(this.idioma);
                        }
                    },
                    getNivelIdiomaDescription: function () {
                        return metadataEntities.getMetadataProperty(this.$propertyPath + ".nivelIdioma").getValueDescription(this.nivelIdioma);
                    }
                };


                return function (object, propertyPath) {
                    object.$propertyPath=propertyPath;
                    angular.extend(object, TituloIdioma);
                };
            }]);

    }]);
