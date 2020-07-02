app.controller('MainController', ['$scope', 'goPage', 'ix3Configuration', '$http','notify', function ($scope, goPage, ix3Configuration, $http,notify) {
        $scope.login = function () {
            goPage.login();
        };
        $scope.familiasOfertas = [];
        $scope.nuestrosNumeros={
            numOfertas:0,
            numeroTitulosFP:0
        };
        $scope.createAccount = function () {
            goPage.createAccount();
        };
        
        $scope.masonry=function() {
            setTimeout(function(){ 
                new Masonry(document.getElementById("masonry"), {
                        itemSelector: '.l-masonry__area',
                        columnWidth: 350,
                        isFitWidth: true
                    }); 
            }, 500);
 
        };
        
        $scope.notifyOferta=function() {
            notify.error(undefined,"Entra en empleaFP para ver la oferta",3000);
        }
        
        $http({
            method: "GET",
            url: ix3Configuration.server.api + "/Estadisticas/familiasOfertas/"
        }).then(function (familiasOfertas) {
            $scope.familiasOfertas = familiasOfertas.data;
        }, function (businessMessages) {
            $scope.businessMessages = businessMessages;
        });
        
        $http({
            method: "GET",
            url: ix3Configuration.server.api + "/Estadisticas/publicas/"
        }).then(function (chartData) {
            $scope.nuestrosNumeros.numOfertas=chartData.data.numeroOfertas;
            $scope.nuestrosNumeros.numeroTitulosFP=chartData.data.numeroTitulosFP;
        }, function (businessMessages) {
            $scope.businessMessages = businessMessages;
        });
        
    }]);



