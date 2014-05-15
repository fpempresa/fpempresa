"use strict";

angular.lazy.controller("LoginController", ['$scope', 'session','$window', function($scope, session,$window) {

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
            $window.location.href=getContextPath() + "/createaccount.html";   
        };        

    }]);