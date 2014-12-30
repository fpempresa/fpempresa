
app.config(['$stateProvider', function ($stateProvider) {
        $stateProvider.state('createaccount/init', {
            url:'/createaccount/init/:tipoUsuario?',
            templateUrl: 'createaccount/init.html',
            controller: 'CreateAccountInitController'
        });

        $stateProvider.state('/createaccount/register', {
            url:'/createaccount/register/:tipoUsuario',
            templateUrl: 'createaccount/register.html',
            controller: 'CreateAccountRegisterController'
        });


        $stateProvider.state('/createaccount/end', {
            url:'/createaccount/end/:tipoUsuario',
            templateUrl: 'createaccount/end.html',
            controller: 'CreateAccountEndController'
        });

    }]);

