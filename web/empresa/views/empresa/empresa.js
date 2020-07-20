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
"use strict";
app.config(['crudRoutesProvider', function (crudRoutesProvider) {
        crudRoutesProvider.addEditRoute({
            entity: "Empresa",
            expand: "direccion.municipio,direccion.municipio.provincia"
        });
        crudRoutesProvider.addNewRoute({
            entity: "Empresa",
            expand: "direccion.municipio,direccion.municipio.provincia"
        });
    }]);


app.controller("EmpresaNewEditController", ['$scope', '$window', '$location', 'genericControllerCrudDetail', 'controllerParams', 'session', function ($scope, $window, $location, genericControllerCrudDetail, controllerParams, session) {
        genericControllerCrudDetail.extendScope($scope, controllerParams);


        $scope.postCreate = function () {
            $scope.model.contacto={};
            $scope.model.contacto.persona=$scope.user.nombre + " " + $scope.user.apellidos;
            $scope.model.contacto.email=$scope.user.email;
        }

        $scope.finishOK = function (oldControllerAction) {
            if (oldControllerAction === "NEW") {
                $window.location.assign($window.location.pathname);
            } else {
                setTimeout(function () { //http://stackoverflow.com/questions/13853844/angular-js-ie-error-10-digest-iterations-reached-aborting
                    $window.history.back(); 
                }, 0);
            }
        };

    }]);
