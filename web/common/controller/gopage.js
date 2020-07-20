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
/**
 * Servicio para ir a la página de inicio de un usuario
 */
angular.module("common").service("goPage", ['session', '$window', '$rootScope', function(session, $window, $rootScope) {

        function goHomeUsuario(usuario) {
            if (usuario.tipoUsuario === "TITULADO") {
                $window.location.href = getContextPath() + "/titulado/index.html";
            } else if (usuario.tipoUsuario === "EMPRESA") {
                $window.location.href = getContextPath() + "/empresa/index.html";
            } else if (usuario.tipoUsuario === "CENTRO") {
                $window.location.href = getContextPath() + "/centro/index.html";
            } else if (usuario.tipoUsuario === "ADMINISTRADOR") {
                $window.location.href = getContextPath() + "/administrador/index.html";             
            } else {
                goHomeApp();
            }
        }
        function goHomeApp() {
            $window.location.href = getContextPath()+"/site/index.html#/";
        }               


        return {
            homeUsuario: function(usuario) {
                if (usuario) {
                    goHomeUsuario(usuario);
                }else if ($rootScope.user) {
                    goHomeUsuario($rootScope.user); 
                } else {
                    session.logged().then(function(usuario) {
                        if (usuario) {
                            goHomeUsuario(usuario);
                        } else {
                            goHomeApp();
                        }
                    }, function() {
                        goHomeApp();
                    });
                }
            },
            homeApp:function() {
                goHomeApp();
            },
            createAccount:function(tipoUsuario) {
                if (tipoUsuario) {
                    $window.location.href = getContextPath() + "/site/index.html#/createaccount/init/"+tipoUsuario;
                } else {
                    $window.location.href = getContextPath() + "/site/index.html#/createaccount/init";
                }
            },
            login:function() {
                    $window.location.href = getContextPath() + "/site/index.html#/login";  
            },
            soporte:function() {
                    $window.location.href = getContextPath() + "/site/index.html#/docs/soporte";  
            },
        };
    }]);
