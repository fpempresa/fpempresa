
app.config(['$routeProvider', function($routeProvider) {
        $routeProvider.when('/createaccount/:tipoUsuario?/:fin?', {
            templateUrl: 'createaccount/createaccount.html',
            controller: 'CreateAccountController'
        });
    }]);


app.controller('CreateAccountController', ['$scope','$routeParams','$location', function($scope,$routeParams,$location) {
        $scope.state={};
        
        if ($routeParams.tipoUsuario) {
            $scope.state.tipoUsuario=$routeParams.tipoUsuario;
            if ($routeParams.fin) {
                $scope.state.step=2;
            } else {
                $scope.state.step=1;
            }
        } else {
            $scope.state.step=0;
            $scope.state.tipoUsuario=null;
        }
        $scope.next=function() {
            switch ($scope.state.step) {
                case 0:
                    $location.path("/createaccount/"+$scope.state.tipoUsuario);
                    break;
                case 1:
                    $location.path("/createaccount/"+$scope.state.tipoUsuario+"/fin");
                    break;
                default:
                    throw Error("El paso no es valido:"+$scope.state.step);
            }
        }
      
        
}]);
