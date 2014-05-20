"use strict";
/**
 * Servicio para ir a la página de inicio de un usuario
 */
angular.module("es.logongas.ix3").service("goPage", ['session', '$window', 'daoFactory','dialog', function(session, $window, daoFactory,dialog) {

        function goHomeUsuario(usuario) {
            if (usuario.tipoUsuario === "TITULADO") {
                var tituladoDAO = daoFactory.getDAO("Titulado");
                tituladoDAO.get(usuario.idIdentity, function(titulado) {
                    if (titulado) {
                        $window.location.href = getContextPath() + "/titulado/index.html#/";
                    } else {
                        $window.location.href = getContextPath() + "/titulado/index.html#/titulado/new/usuario.idIdentity/" + usuario.idIdentity;
                    }

                })
            } else if (usuario.tipoUsuario === "EMPRESA") {
                alert("Aun no está hecha la funcionalidad de empresas");
            } else if (usuario.tipoUsuario === "CENTRO") {
                alert("Aun no está hecha la funcionalidad de centros");
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
                    $window.location.href = getContextPath() + "/site/index.html#/createaccount/register/"+tipoUsuario;
                } else {
                    $window.location.href = getContextPath() + "/site/index.html#/createaccount/init";
                }
            },
            login:function() {
                dialog.create(getContextPath() + "/shared/login/index").then(function(usuario) {
                    goHomeUsuario(usuario);           
                });
            }
        };
    }]);