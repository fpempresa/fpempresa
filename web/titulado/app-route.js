"use strict";

app.config(['$stateProvider','$urlRouterProvider', function($stateProvider,$urlRouterProvider) {
        $urlRouterProvider.otherwise('/');
        $stateProvider.state("lateralmenu",{
            templateUrl:"fragments/lateralmenu/lateralmenu.html"
        });        
    }]);
