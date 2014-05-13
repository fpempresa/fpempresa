"use strict";

angular.lazy.controller("LoginController", ['$scope', 'session', function($scope, session) {

        $scope.dialog.open({
            title:"Entrar",
            width:400,
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


    }]);