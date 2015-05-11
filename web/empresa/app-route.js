"use strict";

app.config(['$stateProvider','$urlRouterProvider', function($stateProvider,$urlRouterProvider) {
        var idEmpresa;
        if (user && user.empresa && user.empresa.idEmpresa) {
            idEmpresa=user.empresa.idEmpresa;
        } else {
            idEmpresa=0;
        }
        
        $urlRouterProvider.otherwise('/empresa/edit/'+idEmpresa);
        $stateProvider.state("lateralmenu",{
            templateUrl:"fragments/lateralmenu/lateralmenu.html",
            controller:"LateralMenuController"
        });        
    }]);
