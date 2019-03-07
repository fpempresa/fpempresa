app.config(['$stateProvider', 'crudRoutesProvider', function ($stateProvider, crudRoutesProvider) {
        $stateProvider.state('lateralmenu.main', {
            url: "/",
            templateUrl: 'views/main/main.html',
            controller: 'MainController'
        });
    }]);


MainController.$inject = ['$scope', '$http', 'ix3Configuration'];
function MainController($scope, $http, ix3Configuration) {
    $scope.businessMessages = [];

        $scope.anyo=null;
        $scope.anyos=[];
        var currentYear=(new Date()).getFullYear();
        for(var anyo=currentYear;anyo>=1980;anyo--) {
            $scope.anyos.push(anyo);
        } 

        if ($scope.user && $scope.user.centro) {
            getEstadisticas($scope.user.centro,$scope.anyo,$scope.anyo);
        }
    
        $scope.buttonSearch=function() {
            getEstadisticas($scope.user.centro,$scope.anyo,$scope.anyo);
        }
    
        function getEstadisticas(centro,anyoInicio,anyoFin) {
            $http({
                method: "GET",
                url: ix3Configuration.server.api + "/Estadisticas/centro/" + centro.idCentro + "?anyoInicio=" + (anyoInicio==null?'':anyoInicio) +"&anyoFin=" + (anyoFin==null?'':anyoFin)
            }).then(function (chartData) {
                $scope.chartData = chartData.data;
                $scope.chartData['numeroCentros'] = [{valor: chartData.data.numeroCentros}];
            }, function (businessMessages) {
                $scope.businessMessages = businessMessages;
            });
        }     
    
}
app.controller("MainController", MainController);