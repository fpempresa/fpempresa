"use strict";
/**
 * Servicio para ir a la página de inicio de un usuario
 */
angular.module("common").service("goPage", ['session', '$window', 'repositoryFactory','$rootScope', function(session, $window, repositoryFactory, $rootScope) {

        function goHomeUsuario(usuario) {
            if (usuario.tipoUsuario === "TITULADO") {
                $window.location.href = getContextPath() + "/titulado/index.html";
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
                //alert("El registro de nuevos usuarios en la bolsa de trabajo no está habilitado actualmente");
                if (tipoUsuario) {
                    $window.location.href = getContextPath() + "/site/index.html#/createaccount/register/"+tipoUsuario;
                } else {
                    $window.location.href = getContextPath() + "/site/index.html#/createaccount/init";
                }
            },
            login:function() {
                    $window.location.href = getContextPath() + "/site/index.html#/login";  
            }
        };
    }]);