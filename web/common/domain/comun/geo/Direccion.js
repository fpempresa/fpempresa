"use strict";

angular.module("common").config(['richDomainProvider', function (richDomain) {

        richDomain.addEntityTransformer("Direccion", ['metadataEntities', function (metadataEntities) {
                var Direccion = {

                };

                return function (object, propertyPath) {
                    object.$propertyPath=propertyPath;                    
                    angular.extend(object, Direccion);
                };
            }]);

    }]);