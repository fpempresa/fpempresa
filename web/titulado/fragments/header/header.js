app.controller("HeaderController", ['$scope','session','goPage',function ($scope,session,goPage) {
        $scope.logout=function() {
            session.logout().then(function() {
                goPage.homeApp();
            }, function(businessMessages) {
                $scope.businessMessages = businessMessages;
            });
        }
}]);
