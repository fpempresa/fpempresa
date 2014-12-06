"use strict";



app.controller("LoginController", ['$scope', 'session','$window','goPage', function($scope, session,$window,goPage) {
        $scope.businessMessages=null;

        $scope.login = function(email, password) {
            session.login(email, password).then(function(user) {
                goPage.homeUsuario();
            }, function(businessMessages) {
                $scope.businessMessages = businessMessages;
            });
        };


        $scope.register = function() {
            goPage.createAccount();  
        };        

    }]);