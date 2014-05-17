
app.config(['$routeProvider', function($routeProvider) {
        $routeProvider.when('/createaccount/:tipoUsuario?/:fin?', {
            templateUrl: 'createaccount/createaccount.html',
            controller: 'CreateAccountController'
        });
    }]);


app.controller('CreateAccountController', ['$scope','$routeParams','$location','daoFactory','goPage',function($scope,$routeParams,$location,daoFactory,goPage) {
        var usuarioDAO=daoFactory.getDAO("Usuario");
        $scope.state={};
        $scope.model={};
        $scope.businessMessages=null;
        
        if ($routeParams.tipoUsuario) {
            $scope.model.tipoUsuario=$routeParams.tipoUsuario;
            if ($routeParams.fin) {
                usuarioDAO.create(function(usuario) {
                    angular.extend($scope.model, usuario);
                },function() {
                    alert("Fallo al crear los datos");
                })
                $scope.state.step=2;
            } else {
                $scope.state.step=1;
            }
        } else {
            $scope.state.step=0;
            $scope.model.tipoUsuario=null;
        }
        $scope.next=function() {
            switch ($scope.state.step) {
                case 0:
                    $location.path("/createaccount/"+$scope.model.tipoUsuario);
                    break;
                case 1:
                    usuarioDAO.insert($scope.model,function() {
                        $location.path("/createaccount/"+$scope.model.tipoUsuario+"/fin");
                    },function(error) {
                        if (error.status === 400) {
                            $scope.businessMessages = error.data;
                        } else {
                            $scope.businessMessages = [{
                                    propertyName: null,
                                    message: "Estado HTTP:" + error.status + "\n" + error.data
                                }];
                        }
                    });
                    break;
                default:
                    throw Error("El paso no es valido:"+$scope.state.step);
            }
        }
        $scope.login=function() {
            goPage.login();            
        }
        
}]);
