"use strict";



app.controller("LoginController", ['$scope', 'session', 'goPage', 'dialog', function ($scope, session, goPage, dialog) {
        $scope.businessMessages = null;

        $scope.login = function (email, password) {
            session.login(email, password).then(function (user) {
                goPage.homeUsuario();
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