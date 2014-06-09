
app.config(['$routeProvider', function($routeProvider) {
        $routeProvider.when('/createaccount/init', {
            templateUrl: 'createaccount/init.html',
            controller: 'CreateAccountInitController'
        });
    }]);


app.controller('CreateAccountInitController', ['$scope', '$location', 'remoteServiceFactory',  function($scope,  $location, remoteServiceFactory) {
        var usuarioRemoteService = remoteServiceFactory.getRemoteService("Usuario");
        $scope.model = {};
        $scope.businessMessages = null;

        usuarioRemoteService.create().then(function(usuario) {
            $scope.model.tipoUsuario = usuario.tipoUsuario;
        }, function(businessMessages) {
            $scope.businessMessages = businessMessages;
        });

        $scope.next = function() {
            $location.path("/createaccount/register/" + $scope.model.tipoUsuario);
        };
    }]);
