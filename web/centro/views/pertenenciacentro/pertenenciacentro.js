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


app.controller("PertenenciaCentroController", ['$scope', '$q', 'controllerParams', 'genericControllerCrudList', 'serviceFactory', 'goPage', 'session','ix3Configuration', function ($scope, $q, controllerParams, genericControllerCrudList, serviceFactory, goPage, session, ix3Configuration) {
        genericControllerCrudList.extendScope($scope, controllerParams);
        $scope.page.pageSize = 100;
        $scope.correoSoporte=ix3Configuration.serverConfig['app.correoSoporte'];

        $scope.filters.$ne.idCentro = 0;
        if ($scope.user && $scope.user.centro) {
            $scope.filters.$ne.idCentro = $scope.user.centro.idCentro; //No debe salir el propio centro al que pertenece.
        }
        $scope.filters.estadoCentro = "PERTENECE_A_FPEMPRESA";
        $scope.orderby = [
            {fieldName: "direccion.municipio.descripcion", orderDirection: "ASC"},
            {fieldName: "nombre", orderDirection: "ASC"}          
        ];

        $scope.$watch("filters['direccion.municipio.provincia.idProvincia']", function (idProvincia, idProvincia) {
            $scope.search();
        });
        

        $scope.preSearch = function (filters) {
            if (filters['direccion.municipio.provincia.idProvincia']) {
                filters['direccion.municipio.provincia.idProvincia'] = filters['direccion.municipio.provincia.idProvincia'].idProvincia;
            } else {
                filters['direccion.municipio.provincia.idProvincia']=-1
            }
        }

        $scope.buttonPertenenciaCentro = function (centro) {
            $scope.pertenenciaCentro(centro);
        };
        $scope.pertenenciaCentro = function (centro) {
            var ok = confirm("Confirma que quieres pertenecer al centro  '" + centro.toString() + "'");

            if (ok === true) {
                var usuarioService = serviceFactory.getService("Usuario");
                usuarioService.get($scope.user.idIdentity).then(function (usuario) {
                    return usuarioService.updateCentro(usuario.idIdentity, centro);
                }).then(function () {
                    return session.logout();
                }).then(function () {
                    goPage.homeApp();
                }).catch(function (businessMessages) {
                    $scope.businessMessages = businessMessages;
                });

            };
        };

    }]);