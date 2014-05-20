
app.config(['$routeProvider', function($routeProvider) {
        $routeProvider.when('/createaccount/init', {
            templateUrl: 'createaccount/init.html',
            controller: 'CreateAccountInitController'
        });
    }]);


app.controller('CreateAccountInitController', ['$scope', '$location', 'daoFactory',  function($scope,  $location, daoFactory) {
        var usuarioDAO = daoFactory.getDAO("Usuario");
        $scope.model = {};
        $scope.businessMessages = null;

        usuarioDAO.create(function(usuario) {
            $scope.model.tipoUsuario = usuario.tipoUsuario;
        }, function() {
            alert("Fallo al crear los datos");
        });

        $scope.next = function() {
            $location.path("/createaccount/register/" + $scope.model.tipoUsuario);
        };
    }]);
