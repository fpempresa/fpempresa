

app.controller('MainController', ['$scope', 'serviceFactory', function ($scope, serviceFactory) {

        $scope.id = $scope.user.titulado.idTitulado;
        $scope.entity = "Titulado";
        $scope.businessMessages=null;

        var service=serviceFactory.getService($scope.entity);
        service.get($scope.id, "titulosIdiomas,experienciasLaborales,formacionesAcademicas.centro,usuario,direccion.municipio.provincia").then(function(data) {
            $scope.model = data;
            $scope.businessMessages = null;
        }, function(businessMessages) {
            $scope.businessMessages = businessMessages;
        });

    }]);