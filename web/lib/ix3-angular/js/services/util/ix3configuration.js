"use strict";


/**
        {
            bootstrap: {
                version: 3
            },
            server: {
                api: "/api"
            },
            format: {
                date: {
                    default: "shortDate"
                }
            },
            pages: {
                login: {//O 'absUrl' o 'url' , Uno de los 2
                    absUrl: undefined,
                    url: undefined
                },
                forbidden: {//O 'absUrl' o 'url' , Uno de los 2
                    absUrl: undefined,
                    url: undefined
                }
            },
            security: {
                defaultStatus: 200,
                acl: [
                    ['user', 'state', 'params', function (user, state, params) {
                            return //200,401 o 403
                        }]
                ]
            }
        };
 */
angular.module('es.logongas.ix3').factory('ix3Configuration', ['$injector', function ($injector) {

        var ix3Configuration;
        //No obligamos a que el usuario defina su propia configuración
        if ($injector.has("ix3UserConfiguration")) {
            ix3Configuration = angular.copy($injector.get("ix3UserConfiguration"));
        } else {
            ix3Configuration = {};
        }

        ix3Configuration.bootstrap = ix3Configuration.bootstrap || {};

        ix3Configuration.server = ix3Configuration.server || {};

        ix3Configuration.format = ix3Configuration.format || {};
        ix3Configuration.format.date = ix3Configuration.format.date || {};

        ix3Configuration.pages = ix3Configuration.pages || {};
        ix3Configuration.pages.login = ix3Configuration.pages.login || {};
        ix3Configuration.pages.forbidden = ix3Configuration.pages.forbidden || {};

        ix3Configuration.security = ix3Configuration.security || {};
        ix3Configuration.security.acl = ix3Configuration.security.acl || [];


        //Los valores por defecto
        ix3Configuration.bootstrap.version = ix3Configuration.bootstrap.version || 3;
        ix3Configuration.server.api = ix3Configuration.server.api || "./api";
        ix3Configuration.format.date.default = ix3Configuration.format.date.default || "shortDate";
        ix3Configuration.security.defaultStatus = ix3Configuration.security.defaultStatus || 200;

        return ix3Configuration;
    }]);
