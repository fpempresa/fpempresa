app.controller("LoginController", ['$scope','session', 'dialog','$window','goPage', function($scope, session, dialog,$window,goPage) {
        $scope.usuario = null;
        session.logged().then(function(usuario) {
            $scope.usuario = usuario;
        }, function() {
            $scope.usuario = null;
        });

        $scope.$on("ix3.session.login",function(event,usuario) {
            $scope.usuario = usuario;
        })
        $scope.$on("ix3.session.logout",function(event) {
            $scope.usuario = null;
        })
        
        $scope.login = function() {
            goPage.login();
        };

        $scope.logout = function() {
            session.logout().then(function() {
                goPage.homeApp();
            });

        };
        
        $scope.createAccount=function() {
            goPage.createAccount();            
        }
        
        $scope.goPageHomeApp=function() {
            goPage.homeApp();
        }
    }])