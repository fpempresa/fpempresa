"use strict";

angular.module("es.logongas.ix3").directive('ix3Validation', ['$compile', function($compile) {
        function getMetadataPropertyName(key) {
            var keys = key.split('.');
            var name = "";
            for (var i = 0; i < keys.length; i++) {
                if (name !== "") {
                    name = name + ".";
                }
                name = name + "properties['" + keys[i] + "']";
            }
            return name;
        }

        return {
            restrict: 'A',
            terminal: true,
            priority: 1000,
            compile: function(element, attributes) {
                return {
                    pre: function($scope, element, attributes, controller, transcludeFn) {

                    },
                    post: function($scope, element, attributes) {
                        var property = attributes.ngModel.replace("model.", "");
                        var metadataPropertyName = getMetadataPropertyName(property);
                        
                        //Expresion regular
                        var pattern=$scope.$eval("metadata[entity]." + metadataPropertyName + ".pattern");
                        if (pattern!=null) {
                            element.attr("ng-pattern",new RegExp("^("+pattern+")$") );
                        }
                        
                        //Valor requirido
                        var required=$scope.$eval("metadata[entity]." + metadataPropertyName + ".required");
                        if (required===true) {
                            element.attr("ng-required",required);
                        }
                        
                        //Tamaño máximo
                        var maxLength=$scope.$eval("metadata[entity]." + metadataPropertyName + ".maxLength");
                        if (maxLength!==null) {
                            element.attr("ng-maxlength",maxLength);
                        }
                        
                        //Tamaño mínimo
                        var minLength=$scope.$eval("metadata[entity]." + metadataPropertyName + ".minLength");
                        if (minLength!==null) {
                            element.attr("ng-minlength",minLength);
                        }                           
                        
                        
                        //Valor máximo
                        var maximum=$scope.$eval("metadata[entity]." + metadataPropertyName + ".maximum");
                        if (maximum!==null) {
                            element.attr("max",maximum);
                        }
                        
                        //Valor mínimo
                        var minimum=$scope.$eval("metadata[entity]." + metadataPropertyName + ".minimum");
                        if (minimum!==null) {
                            element.attr("min",minimum);
                        }                        
                        
                        element.removeAttr("ix3-validation"); //Se quita para evitar el bucle infinito de compilaciones
                        element.removeAttr("data-ix3-validation"); //Se quita para evitar el bucle infinito de compilaciones
                        $compile(element)($scope);
                    }
                }
            }
        };
    }]);

