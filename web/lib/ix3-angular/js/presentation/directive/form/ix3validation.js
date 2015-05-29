"use strict";

angular.module("es.logongas.ix3").directive('ix3Validation', ['$compile','metadataEntities', function($compile, metadataEntities) {

        return {
            restrict: 'A',
            terminal: true,
            require:"^ix3Form",
            priority: 10000,
            compile: function(element, attributes) {
                return {
                    pre: function($scope, element, attributes) {

                    },
                    post: function($scope, element, attributes, ix3FormController) {
                        var metadataProperty;
                        if (attributes.ix3MetaDataProperty) {
                            metadataProperty = metadataEntities.getMetadataProperty(attributes.ix3MetaDataProperty);
                            if (!metadataProperty) {
                                throw Error("No existe la metainformación de :" + attributes.ix3MetaDataProperty);
                            }
                        } else {
                            var propertyName = attributes.ngModel.replace(new RegExp("^" + ix3FormController.getConfig().modelPropertyName + "\."), "");
                            var metadata = metadataEntities.getMetadata(ix3FormController.getConfig().entity);
                            
                            if (!metadata) {
                                throw Error("No existe la metainformación de la entidad :" + ix3FormController.getConfig().entity);
                            }
                            
                            metadataProperty = metadata.getMetadataProperty(propertyName);
                            
                            if (!metadataProperty) {
                                throw Error("No existe la metainformación de la propiedad :" + ix3FormController.getConfig().entity + "." + propertyName);
                            }
                        }                        

                        //Expresion regular
                        var pattern=metadataProperty.pattern;
                        if (pattern!==null) {
                            element.attr("ng-pattern",new RegExp("^("+pattern+")$") );
                        }
                        
                        //Valor requirido
                        var required=metadataProperty.required;
                        if (required===true) {
                            element.attr("ng-required",required);
                        }
                        
                        //Tamaño máximo
                        var maxLength=metadataProperty.maxLength;
                        if (maxLength!==null) {
                            element.attr("ng-maxlength",maxLength);
                        }
                        
                        //Tamaño mínimo
                        var minLength=metadataProperty.minLength;
                        if (minLength!==null) {
                            element.attr("ng-minlength",minLength);
                        }                           
                        
                        
                        //Valor máximo
                        var maximum=metadataProperty.maximum;
                        if (maximum!==null) {
                            element.attr("max",maximum);
                        }
                        
                        //Valor mínimo
                        var minimum=metadataProperty.minimum;
                        if (minimum!==null) {
                            element.attr("min",minimum);
                        }                        
                        
                        var format=metadataProperty.format;
                        if (format==="URL") {
                            element.attr("type","url");
                        } else if (format==="EMAIL") {
                            element.attr("type","email");
                        }                          
                        
                        element.removeAttr("ix3-validation"); //Se quita para evitar el bucle infinito de compilaciones
                        element.removeAttr("data-ix3-validation"); //Se quita para evitar el bucle infinito de compilaciones
                        element.removeAttr("x-ix3-validation"); //Se quita para evitar el bucle infinito de compilaciones
                        $compile(element)($scope);
                    }
                }
            }
        };
    }]);

