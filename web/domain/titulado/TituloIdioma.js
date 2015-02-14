
"use strict";

app.run(['richDomain', 'metadataEntities', function (richDomain, metadataEntities) {

        function getNombreIdioma() {
            if (this.idioma === "OTRO") {
                return this.otroIdioma;
            } else {
                return metadataEntities.getMetadataProperty(this.$propertyPath + ".idioma").getValueDescription(this.idioma);
            }
        }

        function getNivelIdiomaDescription() {
            return metadataEntities.getMetadataProperty(this.$propertyPath + ".nivelIdioma").getValueDescription(this.nivelIdioma);
        }

        var TituloIdioma = {
            getNombreIdioma: function () {
                if (this.idioma === "OTRO") {
                    alert("otro");
                    return this.otroIdioma;
                } else {
                    return metadataEntities.getMetadataProperty(this.$propertyPath + ".idioma").getValueDescription(this.idioma);
                }
            },
            getNivelIdiomaDescription: function () {
                return metadataEntities.getMetadataProperty(this.$propertyPath + ".nivelIdioma").getValueDescription(this.nivelIdioma);
            }
        };


        richDomain.addEntityTransformer("TituloIdioma", function (className, object) {
            //object['getNombreIdioma'] = getNombreIdioma;
            //object['getNivelIdiomaDescription'] = getNivelIdiomaDescription;

            angular.extend(object, TituloIdioma);

        });






    }]);