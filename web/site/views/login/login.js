"use strict";



app.controller("LoginController", ['$scope', 'session', 'goPage', 'dialog', function ($scope, session, goPage, dialog) {
        $scope.businessMessages = null;

        //Si entramos en esta página , siempre nos deslogeamos
        session.logout();  


        $scope.login = function (email, password) {

            session.login(email, password).then(function (user) {
                if ($scope.user.aceptadoRGPD===true) {
                   goPage.homeUsuario(); 
                } else {
                    dialog.create("aceptarRGPD").then(function(){
                        goPage.homeUsuario();
                    },function() {
                        
                        session.logout().then(function() {
                            goPage.homeApp();
                        }, function(businessMessages) {
                            $scope.businessMessages = businessMessages;
                        });                        

                    });
                }

            }, function (businessMessages) {
                $scope.businessMessages = businessMessages;
            });
        };

        $scope.olvidadoContrasenya = function () {
            dialog.create('enviarMailResetearContrasenya');
        };


        $scope.createAccount = function () {
            goPage.createAccount();
        };

    }]);