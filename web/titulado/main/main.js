

app.controller('MainController', ['$scope', 'repositoryFactory', '$filter', function ($scope, repositoryFactory, $filter) {
        $scope.businessMessages = null;


        $scope.id = $scope.user.idIdentity;
        $scope.expand = "titulosIdiomas,experienciasLaborales,formacionesAcademicas.centro,usuario";
        $scope.entity = "Titulado";
        $scope.repository = repositoryFactory.getRepository($scope.entity);
        $scope.metadata = {};
        $scope.businessMessages=null;

        $scope.repository.get($scope.id, $scope.expand).then(function(data) {
            $scope.model = data;
            $scope.businessMessages = null;
        }, function(businessMessages) {
            $scope.businessMessages = businessMessages;
        });

        $scope.repository.metadata($scope.expand).then(function(data) {
            $scope.metadata[$scope.entity] = data;
            $scope.businessMessages = null;
        }, function(businessMessages) {
            $scope.businessMessages = businessMessages;
        });

        $scope.getTipoTitulo = function(formacionAcademica) {
            if (!$scope.metadata.Titulado) {
                return "";
            }
            var values = $filter('filter')($scope.metadata.Titulado.properties.formacionesAcademicas.properties.tipoFormacionAcademica.values, {key: formacionAcademica.tipoFormacionAcademica});
            return values[0].description;
        };

    }]);