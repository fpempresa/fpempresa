"user strict";

var app = angular.module('app', ['ngRoute', 'es.logongas.ix3', 'es.logongas.ix3.datepicker.jquery']);

app.config(['$routeProvider', '$controllerProvider', function($routeProvider) {
        $routeProvider.otherwise({
            redirectTo: '/'
        });

    }]);

app.constant("bootstrap", {
    version: 3
});


