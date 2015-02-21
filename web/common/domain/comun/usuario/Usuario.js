"use strict";

angular.module("common").config(['richDomainProvider', function (richDomain) {

        richDomain.addEntityTransformer("Usuario", ['metadataEntities', function (metadataEntities) {
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

                return function (object, propertyPath) {
                    object.$propertyPath=propertyPath;                    
                    angular.extend(object, Usuario);
                };
            }]);

    }]);