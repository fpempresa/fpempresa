"use strict";

angular.module("es.logongas.ix3").directive('ix3Validation', [function() {
        function getMetadataPropertyName(key) {
            var keys = key.split('.');
            var name="";
            for (var i = 0; i < keys.length; i++) {
                if (name!=="") {
                    name=name+".";
                }
                name = name+"properties['" + keys[i]+"']";
            }
            return name;
        }
        
        return {
            restrict: 'A',
            compile: function(element, attributes) {
                var property=attributes.ngModel.replace("model.","");
                var metadataPropertyName=getMetadataPropertyName(property);

                

                //attributes.ngRequired="metadata[entity]." + metadataPropertyName + ".required";
                //attributes.ngPattern="new RegExp(metadata[entity]." + metadataPropertyName + ".pattern$)";
                //attributes.ngMaxlength="metadata[entity]." + metadataPropertyName + ".maxLength";
                //attributes.ngMinlength="metadata[entity]." + metadataPropertyName + ".minLength";
                //attributes.max="metadata[entity]." + metadataPropertyName + ".maximum";
                //attributes.min="metadata[entity]." + metadataPropertyName + ".minimum";
            }
        };
    }]);

