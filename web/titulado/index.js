"use strict";

app.controller("IndexController",['$scope','session',function($scope,session){
                session.logged().then(function(user) {
                    $scope.user=user;
                },function() {
                    
                });
}]);