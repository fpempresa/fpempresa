/**
 *   FPempresa
 *   Copyright (C) 2020  Lorenzo González
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Affero General Public License as
 *   published by the Free Software Foundation, either version 3 of the
 *   License, or (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Affero General Public License for more details.
 *
 *   You should have received a copy of the GNU Affero General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
"use strict";

angular.module('es.logongas.ix3.configuration').constant("ix3UserConfiguration", {
    bootstrap: {
        version: 3
    },
    server: {
        api: getContextPath() + "/api/administrador"
    },
    format: {
        date: {
            default: "dd/MM/yyyy"
        }
    },
    pages: {
        login: {
            absUrl: getContextPath() + "/site/index.html#/login"
        },
        forbidden: {
            url: "/forbidden"
        }
    },
    security: {
        defaultStatus: 401,
        acl: [
            ['user', 'state', 'params', function (user, state, params) {
                    if (user) {
                        if (user.tipoUsuario === "ADMINISTRADOR") {
                            return 200;
                        } else {
                            //Si no es un titulado prohibimos el acceso
                            return 403;
                        }
                    } else {
                        //Si no hay usuario, tiene que autenticarse
                        return 401;
                    }
                }]
        ]
    },
    session: {
        url: getContextPath() + "/api",        
        expand:"empresa,centro,titulado.direccion.municipio.provincia"
    },
    crud: {
        parentState:"lateralmenu",
        parentPathViews:"views"
    }
});