"use strict";

angular.module('es.logongas.ix3').run(['richDomain', 'langUtil', function (richDomain, langUtil) {



        function getValueDescription(value) {
            if (value) {
                if ((this.dependProperty === null) && (angular.isArray(this.values))) {
                    var description = undefined;
                    var values = this.values;
                    if (this.type === "OBJECT") {
                        for (var i = 0; i < values.length; i++) {
                            if (values[i][this.primaryKeyPropertyName] === value) {
                                description = values[i].toString();
                                break;
                            }
                        }
                    } else {
                        for (var i = 0; i < values.length; i++) {
                            if (values[i].key === value) {
                                description = values[i].description;
                                break;
                            }
                        }
                    }

                    if (typeof (description) === "undefined") {
                        return value;
                    } else {
                        return description;
                    }
                } else {
                    return value;
                }
            } else {
                return value;
            }
        }


        richDomain.addEntityTransformer("Property", function (className, object) {
            object['getValueDescription'] = getValueDescription;
        });



    }]);