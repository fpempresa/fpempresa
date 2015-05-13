"use strict";

angular.module("common").config(['richDomainProvider', function (richDomain) {

        richDomain.addEntityTransformer("Centro", ['metadataEntities', function (metadataEntities) {

                return function (object, propertyPath) {
                    object.toString=function() {
                        return this.nombreComercial;
                    }
                };
            }]);

    }]);

