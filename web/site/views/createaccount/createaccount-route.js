
app.config(['$stateProvider', function ($stateProvider) {
        $stateProvider.state('createaccount/init', {
            url:'/createaccount/init',
            templateUrl: 'views/createaccount/init.html',
            controller: 'CreateAccountInitController'
        });
        $stateProvider.state('createaccount/init/tipoUsuario', {
            url:'/createaccount/init/:tipoUsuario',
            templateUrl: 'views/createaccount/init.html',
            controller: 'CreateAccountInitController'
        });
        $stateProvider.state('/createaccount/end', {
            url:'/createaccount/end/:tipoUsuario',
            templateUrl: 'views/createaccount/end.html',
            controller: 'CreateAccountEndController'
        });

    }]);

