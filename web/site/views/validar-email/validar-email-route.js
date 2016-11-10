app.config(['$stateProvider', function ($stateProvider) {
        $stateProvider.state('validar-email', {
            url: '/validar-email/:token',
            templateUrl: function (params) {
                return 'views/validar-email/validar-email.jsp?token=' + params.token;
            }
        });
    }]);