/*
 * ix3 Copyright 2014-2020 Lorenzo.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
"use strict";

(function() {
    

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
            },
            session: {
                expand:""
            },
            crud: {
                parentState:"",
                parentPathViews:""
            }
        };
 */
angular.module('es.logongas.ix3.configuration').provider('ix3Configuration', ['$injector', function ($injector) {

        var ix3UserConfiguration;
        if ($injector.has("ix3UserConfiguration")) {
            ix3UserConfiguration = $injector.get("ix3UserConfiguration");
        }
            
        var ix3Configuration=getIx3Configuration(ix3UserConfiguration);
        
        //Esto lo hacemos así para que el provider tenga las mismas propiedades que la ix3Configuration
        //con lo que es transparente usar uno u otro.
        angular.copy(ix3Configuration,this);
        
        this.$get=function() {
            return ix3Configuration;
        };
        
    }]);


function getIx3Configuration(ix3UserConfiguration) {

        var ix3Configuration;
        if (ix3UserConfiguration) {
            ix3Configuration=angular.copy(ix3UserConfiguration);
        } else {
            ix3Configuration={};
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
        
        ix3Configuration.session = ix3Configuration.session || {};        
        
        ix3Configuration.crud = ix3Configuration.crud || {};

        //Los valores por defecto
        ix3Configuration.bootstrap.version = ix3Configuration.bootstrap.version || 3;
        ix3Configuration.server.api = ix3Configuration.server.api || "./api";
        ix3Configuration.format.date.default = ix3Configuration.format.date.default || "shortDate";
        ix3Configuration.security.defaultStatus = ix3Configuration.security.defaultStatus || 200;

        return ix3Configuration;
}

})();