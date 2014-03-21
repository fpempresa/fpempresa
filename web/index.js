"user strict";

var app = angular.module("app", []);

app.controller("IndexController", ['$scope', '$window', function($scope, $window) {
        $scope.login = {};

        $scope.buttonLogin = function() {
            $window.location.href = "titulado/index.html";
        };

        $scope.showLogin = function() {
            
            $('#loginModal').on('shown', function() {
                $("#inputLogin").focus();
            })
            $('#loginModal').modal();
        }
    }]);


app.constant("bootstrap",{
    version:3
});