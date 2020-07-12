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
        
        $scope.getUrlIfTitulado=function(url) {
            if ( ($scope.user) && ($scope.user.titulado) ) {
                return url;
            } else {
                "";
            }
        }
        
        $scope.cambiarContrasenya = function () {
            if ( ($scope.user) && ($scope.user.titulado) ) {
                dialog.create('cambiarContrasenya', $scope.user);
            }
        }
}]);
