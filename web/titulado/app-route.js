"use strict";

app.config(['$stateProvider','$urlRouterProvider', function($stateProvider,$urlRouterProvider) {
        $urlRouterProvider.otherwise('/');
        $stateProvider.state("cols1",{
            templateUrl:"fragments/cols/cols1.html"
        });
        $stateProvider.state("cols2",{
            templateUrl:"fragments/cols/cols2.html"
        });        
    }]);
