"use strict";

/**
 * configuramos que si viene un String con forma de fecha en una petición http la
 * transformamos en un objeto Date. http://aboutcode.net/2013/07/27/json-date-parsing-angularjs.html
 */
angular.module('es.logongas.ix3').config(["$httpProvider", function($httpProvider) {
        function convertDateStringsToDates(input) {
            if (typeof input !== "object") {
                return;
            }

            for (var key in input) {
                if (!input.hasOwnProperty(key)) {
                    continue;
                }
                var value = input[key];
                if ((typeof value === "string") && (value.length === 28)) {
                    var date = moment(value, "YYYY-MM-DDTHH:mm:ss.SSSZZ", true);
                    if (date.isValid()) {
                        input[key] = date.toDate();
                    }
                } else if (typeof (value) === "object") {
                    convertDateStringsToDates(value);
                }
            }
        }

        $httpProvider.defaults.transformResponse.push(function(responseData) {
            convertDateStringsToDates(responseData);
            return responseData;
        });
    }]);

angular.module('es.logongas.ix3').config(["$controllerProvider", "$compileProvider", "$filterProvider", "$provide", function($controllerProvider, $compileProvider, $filterProvider, $provide) {
        //Esto es para poder cargar a posteriori recursos de angular.
        //Se usa en las ventanas modales.
        angular.lazy = {
            controller: $controllerProvider.register,
            directive: $compileProvider.directive,
            filter: $filterProvider.register,
            factory: $provide.factory,
            service: $provide.service
        };
    }]);

angular.module('es.logongas.ix3').run(['$rootScope', function($rootScope) {
        $rootScope.getContextPath = getContextPath;
    }]);

angular.module('es.logongas.ix3').value("apiurl", getContextPath() + "/api");
