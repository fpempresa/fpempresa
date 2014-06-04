"use strict";

angular.module("es.logongas.ix3").directive('ix3OptionsEnum', [function() {
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
            priority: 99999,
            compile: function(element, attributes) {
                var property=attributes.ngModel.replace("model.","");
                var metadataPropertyName=getMetadataPropertyName(property);
                var ngOptions="value.key as value.description for value in metadata[entity]." + metadataPropertyName + ".values ";
                attributes.ngOptions = ngOptions;
            }
        };
    }]);

angular.module("es.logongas.ix3").directive('ix3OptionsLov', [function() {
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
            priority: 99999,
            compile: function(element, attributes) {
                var property=attributes.ngModel.replace("model.","");
                var metadataPropertyName=getMetadataPropertyName(property);
                var ngOptions="value as value.toString for value in metadata[entity]." + metadataPropertyName + ".values track by value[metadata[entity]." + metadataPropertyName + ".primaryKeyPropertyName]";
                attributes.ngOptions = ngOptions;
            }
        };
    }]);
