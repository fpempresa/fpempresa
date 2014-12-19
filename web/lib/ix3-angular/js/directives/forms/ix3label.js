"use strict";
angular.module("es.logongas.ix3").directive('ix3Label', ['$document','directiveUtil','metadataEntities',function($document,directiveUtil,metadataEntities) {

        return {
            restrict: 'A',
            require:"^ix3Form",
            compile: function(element, attributes) {
                return {
                    pre: function($scope, element, attributes, ix3FormController) {
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
                            model=directiveUtil.getAttributeValueFromNormalizedName(inputElement,"ngModel");
                            if ((model === undefined) || (model === null) || (model.trim() === ""))  {
                                throw new Error("No existe o no tiene valor el atribut ngModel del elemento:"+forId);
                            }
                            
                        }

                        var propertyName = model.replace(/^model\./, "");
                        var entity=ix3FormController.getConfig().entity;
                        var metadataProperty=metadataEntities.getMetadata(entity).getMetadataProperty(propertyName);

                        var value=metadataProperty.label;
                        if ((value === undefined) || (value === null) || (value.trim() === ""))  {
                            //Si no está el label usamos el nombre de la propia propiedad
                            value=propertyName;
                        }
                        value=value.charAt(0).toUpperCase() + value.slice(1);
                        
                        element.text(value); 
                        
                    },
                    post: function($scope, element, attributes) {
                    }
                };
            }
        };
    }]);