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
                        var propertyName = attributes.ngModel.replace(/^model\./, "");
                        var entity=ix3FormController.getConfig().entity;
                        var metadataProperty=metadataEntities.getMetadata(entity).getMetadataProperty(propertyName);
                        
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

