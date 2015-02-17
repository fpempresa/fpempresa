"use strict";

angular.module("common").run(['richDomain', 'metadataEntities', function (richDomain, metadataEntities) {

        var Usuario = {
            getNombreCompleto: function () {
                return this.nombre + (this.ape1 ? " " + this.ape1 : "") + (this.ape2 ? " " + this.ape2 : "");
            },
            getEstadoUsuarioDescription: function () {
                return metadataEntities.getMetadataProperty(this.$propertyPath + ".estadoUsuario").getValueDescription(this.estadoUsuario);
            },
            getTipoUsuarioDescription: function () {
                return metadataEntities.getMetadataProperty(this.$propertyPath + ".tipoUsuario").getValueDescription(this.tipoUsuario);
            }
        };


        richDomain.addEntityTransformer("Usuario", function (className, object) {
            angular.extend(object, Usuario);
        });

    }]);
