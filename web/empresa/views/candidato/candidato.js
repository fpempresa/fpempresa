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


angular.module("common").config(['$stateProvider', 'dialogProvider', 'getContextPath', 'crudRoutesProvider', function ($stateProvider, dialogProvider, getContextPath, crudRoutesProvider) {

        $stateProvider.state('lateralmenu.oferta_search_todas', {
            url: "/candidato/search/:parentProperty/:parentId",
            templateUrl: 'views/candidato/search.html',
            controller: 'CandidatoSearchController',
            resolve: crudRoutesProvider.getResolve("Candidato", "oferta,usuario.titulado")
        });


        dialogProvider.when('viewCandidato', {
            templateUrl: getContextPath() + "/empresa/views/candidato/detail.html",
            controller: 'CandidatoViewController',
            resolve: crudRoutesProvider.getResolve('Candidato', 'usuario.titulado.titulosIdiomas,usuario.titulado.experienciasLaborales,usuario.titulado.formacionesAcademicas.centro,usuario.titulado.direccion.municipio.provincia', 'VIEW')
        });

    }]);




app.controller("CandidatoSearchController", ['$scope', 'genericControllerCrudList', 'controllerParams', 'dialog', '$location', 'serviceFactory', function ($scope, genericControllerCrudList, controllerParams, dialog, $location, serviceFactory) {
        genericControllerCrudList.extendScope($scope, controllerParams);
        $scope.namedSearch = "getCandidatosOferta";
        $scope.page.pageSize = 20;
        $scope.filters.ocultarRechazados=false;
        $scope.filters.certificados=false;
        $scope.filters.maxAnyoTitulo=200;        
        $scope.anyos = [
            {
                anyo: 200,
                descripcion: "-- Fecha de obtención del título --"
            },
            {
                anyo: 0,
                descripcion: "Obtenido este año"
            },
            {
                anyo: 1,
                descripcion: "Obtenido como máximo el año pasado"
            },
            {
                anyo: 2,
                descripcion: "Obtenido como máximo hace 2 años"
            },
            {
                anyo: 3,
                descripcion: "Obtenido como máximo hace 3 años"
            }        
        ];
        

        

        serviceFactory.getService("Oferta").get($scope.parentId).then(function (data) {
            $scope.oferta = data;
        }, function (businessMessages) {
            $scope.businessMessages = businessMessages;
        });

        $scope.verCandidato = function (idCandidato) {
            dialog.create("viewCandidato", {
                id: idCandidato
            }).then(function () {
                $scope.search();
            });
        };

        $scope.search();

    }]);

CandidatoViewController.$inject = ['$scope', 'genericControllerCrudDetail', 'currentDialog', 'controllerParams', 'ix3Configuration', 'ageCalculator'];
function CandidatoViewController($scope, genericControllerCrudDetail, currentDialog, controllerParams, ix3Configuration, ageCalculator) {
    genericControllerCrudDetail.extendScope($scope, controllerParams);
    $scope.ix3Configuration = ix3Configuration;
    $scope.ageCalculator = ageCalculator;
    currentDialog.open({
        width: 1000,
        height: 700,
        title: "Curriculum"
    });



    $scope.buttonCancel = function () {
        currentDialog.closeCancel();
    };

    $scope.buttonRechazar = function () {
        $scope.model.rechazado = true;

        $scope.doUpdate().then(function () {
            currentDialog.closeOK();
        });

    };
    $scope.buttonAceptar = function () {
        $scope.model.rechazado = false;

        $scope.doUpdate().then(function () {
            currentDialog.closeOK();
        });

    };

}
angular.module("common").controller("CandidatoViewController", CandidatoViewController);