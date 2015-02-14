

app.controller('MainController', ['$scope', 'repositoryFactory', function ($scope, repositoryFactory) {

        $scope.id = $scope.user.titulado.idTitulado;
        $scope.entity = "Titulado";
        $scope.businessMessages=null;

        var repository=repositoryFactory.getRepository($scope.entity);
        repository.get($scope.id, "titulosIdiomas,experienciasLaborales,formacionesAcademicas.centro,usuario,direccion.municipio.provincia").then(function(data) {
            $scope.model = data;
            $scope.businessMessages = null;
        }, function(businessMessages) {
            $scope.businessMessages = businessMessages;
        });

    }]);