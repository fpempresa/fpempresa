var app = angular.module('app', ['ngRoute','es.logongas.ix3','es.logongas.ix3.datepicker.jquery']);

app.config(['$routeProvider','daoFactoryProvider',function($routeProvider,daoFactoryProvider) {
    $routeProvider.otherwise({
        redirectTo: '/'
    });
    
    daoFactoryProvider.setBaseURL("../api");
        
}]);

app.constant("bootstrap",{
    version:3
});