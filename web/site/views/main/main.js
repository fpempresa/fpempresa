/**
 *   FPempresa
 *   Copyright (C) 2020  Lorenzo González
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Affero General Public License as
 *   published by the Free Software Foundation, either version 3 of the
 *   License, or (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Affero General Public License for more details.
 *
 *   You should have received a copy of the GNU Affero General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
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
            notify.error(undefined,"Accede en EmpleaFP para ver la oferta",3000);
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



