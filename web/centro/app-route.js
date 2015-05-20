"use strict";

app.config(['$stateProvider','$urlRouterProvider', function($stateProvider,$urlRouterProvider) {
        var idCentro;
        if (user && user.centro && user.centro.idCentro) {
            idCentro=user.centro.idCentro;
        } else {
            idCentro=0;
        }
        
        $urlRouterProvider.otherwise('/');
        $stateProvider.state("lateralmenu",{
            templateUrl:"fragments/lateralmenu/lateralmenu.html",
            controller:"LateralMenuController"
        });        
    }]);
