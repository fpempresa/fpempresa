"use strict";
angular.module("es.logongas.ix3").directive('ix3Label', ['$document', 'directiveUtil', 'metadataEntities', function ($document, directiveUtil, metadataEntities) {

        return {
            restrict: 'A',
            require: "^ix3Form",
            compile: function (element, attributes) {
                return {
                    pre: function ($scope, element, attributes, ix3FormController) {
                        var metadataProperty;
                        if (attributes.ix3MetaDataProperty) {
                            metadataProperty = metadataEntities.getMetadataProperty(attributes.ix3MetaDataProperty);
                            if (!metadataProperty) {
                                throw Error("No existe la metainformación de :" + attributes.ix3MetaDataProperty);
                            }
                        } else {
                            var forId = attributes.for;
                            var inputElement = $document[0].getElementById(forId);
                            if (!inputElement) {
                                throw new Error("No existe el elemento input al que hace referencia el for del label:" + forId);
                            }

                            var forElementIx3MetaDataProperty = directiveUtil.getAttributeValueFromNormalizedName(inputElement, "ix3MetaDataProperty");
                            
                            if (forElementIx3MetaDataProperty) {
                                metadataProperty = metadataEntities.getMetadataProperty(forElementIx3MetaDataProperty);
                                if (!metadataProperty) {
                                    throw Error("No existe la metainformación de :" + forElementIx3MetaDataProperty);
                                }
                            } else {
                                var forElementNgModel = directiveUtil.getAttributeValueFromNormalizedName(inputElement, "ngModel");



                                var propertyName = forElementNgModel.replace(new RegExp("^" + ix3FormController.getConfig().modelPropertyName + "\."), "");
                                var metadata = metadataEntities.getMetadata(ix3FormController.getConfig().entity);

                                if (!metadata) {
                                    throw Error("No existe la metainformación de la entidad :" + ix3FormController.getConfig().entity);
                                }

                                metadataProperty = metadata.getMetadataProperty(propertyName);

                                if (!metadataProperty) {
                                    throw Error("No existe la metainformación de la propiedad :" + ix3FormController.getConfig().entity + "." + propertyName);
                                }
                            }
                        }

                        var value = metadataProperty.label;
                        if ((value === undefined) || (value === null) || (value.trim() === "")) {
                            //Si no está el label usamos el nombre de la propia propiedad
                            value = propertyName;
                        }
                        value = value.charAt(0).toUpperCase() + value.slice(1);

                        element.text(value);

                    },
                    post: function ($scope, element, attributes) {
                    }
                };
            }
        };
    }]);