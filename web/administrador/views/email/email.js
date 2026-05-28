/**
 *   FPempresa
 *   Copyright (C) 2026  Lorenzo González
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
"use strict";

app.config(['$stateProvider', function ($stateProvider) {
        $stateProvider.state('lateralmenu.email', {
            url: "/email",
            templateUrl: 'views/email/email.html',
            controller: 'EmailController'
        });
    }]);

app.controller("EmailController", ['$scope', '$http', 'ix3Configuration', function ($scope, $http, ix3Configuration) {
        $scope.businessMessages = [];
        $scope.enviado = false;
        $scope.email = {to: '', subject: '', body: ''};

        $scope.enviarCorreo = function () {
            $scope.businessMessages = [];
            $scope.enviado = false;

            $http({
                method: "POST",
                url: ix3Configuration.server.api + "/email",
                data: $scope.email
            }).then(function () {
                $scope.enviado = true;
            }, function (response) {
                $scope.businessMessages = response.data;
            });
        };
    }]);
