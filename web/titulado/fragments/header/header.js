app.controller("HeaderController", ['$scope','session','goPage','dialog',function ($scope,session,goPage,dialog) {
        $scope.logout=function() {
            session.logout().then(function() {
                goPage.homeApp();
            }, function(businessMessages) {
                $scope.businessMessages = businessMessages;
            });
        }
        $scope.soporte=function() {
                goPage.soporte();

        }  
        $scope.cambiarContrasenya = function () {
            dialog.create('cambiarContrasenya', $scope.user);
        }
}]);
