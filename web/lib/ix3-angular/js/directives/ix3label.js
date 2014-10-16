"use strict";
angular.module("es.logongas.ix3").directive('ix3Label', ['$document','utils',function($document,utils) {

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
            scope: true,
            compile: function(element, attributes) {
                return {
                    pre: function($scope, element, attributes) {
                        var model = attributes.ix3Label;
                        if (model.trim() === "") {
                            var forId=attributes.for;
                            if ((forId === undefined) || (forId === null) || (forId.trim() === ""))  {
                                throw new Error("No existe el modelo ni en el atributo 'ix3-label' ni en el atributo 'for'")
                            }
                            var inputElement=$document[0].getElementById(forId);
                            if ((inputElement === undefined) || (inputElement === null) )  {
                                throw new Error("No existe el elemento input al que hace referencia el for del label:"+forId)
                            }
                            model=utils.getAttributeValueFronNormalizedName(inputElement,"ngModel");
                            if ((model === undefined) || (model === null) || (model.trim() === ""))  {
                                throw new Error("No existe o no tiene valor el atribut ngModel del elemento:"+forId);
                            }
                            
                        }

                        var modelProperty = {
                            name: model.replace(/^model\./, ""),
                            metadata: $scope.$eval("metadata[entity]." + getMetadataPropertyName(model.replace(/^model\./, ""))),
                            scopeAccessName: model
                        };

                        var value=modelProperty.metadata.label;
                        if ((value === undefined) || (value === null) || (value.trim() === ""))  {
                            //Si no está el label usamos el nombre de la propia propiedad
                            value=modelProperty.name;
                        } else {
                            //La primera letra siempre se pone en mayúculas
                            value=value.charAt(0).toUpperCase() + value.slice(1);
                        }
                        element.text(value); 
                        
                    },
                    post: function($scope, element, attributes) {
                    }
                };
            }
        };
    }]);