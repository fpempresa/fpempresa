"use strict";


/**
{
    bootstrap: {
        version:3
    },
    server: {
        api:"/api"
    },
    format: {
        date: {
            default:"shortDate"
        }
    },
    pages: {
        login:undefined,
        forbidden:undefined,
        error:undefined
    }
}
 */
angular.module('es.logongas.ix3').factory('ix3Config', ['ix3UserConfig', function (ix3UserConfig) {
        
        var ix3Config=angular.copy(ix3UserConfig);
        
        ix3UserConfig.bootstrap = ix3UserConfig.bootstrap || {};
        ix3UserConfig.server = ix3UserConfig.server || {};
        ix3UserConfig.format = ix3UserConfig.format || {};
        ix3UserConfig.format.date = ix3UserConfig.format.date || {};
        ix3UserConfig.pages = ix3UserConfig.pages || {};
        
        
        //Los valores por defecto
        ix3Config.bootstrap.version = ix3Config.bootstrap.version || 3;
        ix3Config.server.api = ix3Config.server.api || "/api";
        ix3Config.format.date.default = ix3Config.format.date.default || "shortDate";
        
        
        return ix3UserConfig;
    }]);
