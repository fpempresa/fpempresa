"use strict";

app.config(['$stateProvider','$urlRouterProvider', function($stateProvider,$urlRouterProvider) {
        $urlRouterProvider.otherwise('/centro/edit/'+user.centro.idCentro);
        $stateProvider.state("lateralmenu",{
            templateUrl:"fragments/lateralmenu/lateralmenu.html",
            controller:"LateralMenuController"
        });        
    }]);
