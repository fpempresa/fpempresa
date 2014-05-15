app.controller("LoginController", ['$scope','$rootScope', 'session', 'dialog','$window', function($scope,$rootScope, session, dialog,$window) {
        $rootScope.user = null;
        session.logged().then(function(user) {
            $rootScope.user = user;
        }, function() {
            $rootScope.user = null;
        });

        $scope.showLogin = function() {
            dialog.create(getContextPath() + "/shared/login/index").then(function(user) {
                $rootScope.user = user;
            });
        };

        $scope.logout = function() {
            session.logout().then(function() {
                $rootScope.user = null;
            });

        };
        
        $scope.createAccount=function() {
            $window.location.href=getContextPath() + "/site/index.html#/createaccount";            
        }
    }])