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

    if ($scope.user && $scope.user.empresa) {
        $http({
            method: "GET",
            url: ix3Configuration.server.api + "/Estadisticas/empresa/" + $scope.user.empresa.idEmpresa
        }).then(function (chartData) {
            $scope.chartData=chartData.data;
        }, function (businessMessages) {
            $scope.businessMessages = businessMessages;
        });
    }

}
app.controller("MainController", MainController);