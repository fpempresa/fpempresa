"use strict";

angular.lazy.controller("LoginController", ['$scope', 'session','$window','goPage', function($scope, session,$window,goPage) {
        $scope.businessMessages=null;

        $scope.dialog.open({
            title:"Inicio de sesión",
            width:430,
            height:"auto"
        });

        $scope.login = function(email, password) {
            session.login(email, password).then(function(user) {
                $scope.dialog.closeOK(user);
            }, function(businessMessages) {
                $scope.businessMessages = businessMessages;
            });
        };

        $scope.cancel = function() {
            $scope.dialog.closeCancel();
        };

        $scope.register = function() {
            $scope.dialog.closeCancel();
            goPage.createAccount();  
        };        

    }]);