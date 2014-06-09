"user strict";

var app = angular.module('app', ['ngRoute', 'es.logongas.ix3', 'es.logongas.ix3.datepicker.jquery']);

app.config(['$routeProvider', 'remoteServiceFactoryProvider', '$controllerProvider', function($routeProvider, remoteServiceFactoryProvider) {
        $routeProvider.otherwise({
            redirectTo: '/'
        });

        remoteServiceFactoryProvider.setBaseURL("../api");

    }]);

app.constant("bootstrap", {
    version: 3
});


