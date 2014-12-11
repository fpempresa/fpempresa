
app.config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/createaccount/init/:tipoUsuario?', {
            templateUrl: 'createaccount/init.html',
            controller: 'CreateAccountInitController'
        });

        $routeProvider.when('/createaccount/register/:tipoUsuario', {
            templateUrl: 'createaccount/register.html',
            controller: 'CreateAccountRegisterController'
        });


        $routeProvider.when('/createaccount/end/:tipoUsuario', {
            templateUrl: 'createaccount/end.html',
            controller: 'CreateAccountEndController'
        });

    }]);

