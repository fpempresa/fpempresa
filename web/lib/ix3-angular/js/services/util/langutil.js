"use strict";

/**
 * 
 */
angular.module("es.logongas.ix3").factory("langUtil", [function () {

        return {
            getFunctionName: getFunctionName
        };

        function getFunctionName(fn) {
            return fn.toString().substr(0, fn.toString().indexOf("(")).replace("function ", "");
        }
    }]);




