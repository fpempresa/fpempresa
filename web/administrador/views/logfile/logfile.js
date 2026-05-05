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
        $stateProvider.state('lateralmenu.logfileSearch', {
            url: "/logfile/search",
            templateUrl: 'views/logfile/search.html',
            controller: 'LogFileSearchController'
        });
        $stateProvider.state('lateralmenu.logfileDetail', {
            url: "/logfile/detail/:idLogFile",
            templateUrl: 'views/logfile/detail.html',
            controller: 'LogFileDetailController'
        });
    }]);

app.controller("LogFileSearchController", ['$scope', '$http', '$state', 'ix3Configuration', function ($scope, $http, $state, ix3Configuration) {
        $scope.businessMessages = [];
        $scope.logFiles = [];
        $scope.tiposLogFile = [];
        $scope.tipoSeleccionado = '____NINGUNO____';

        function getFileType(nombre) {
            var firstDot = nombre.indexOf('.');
            return firstDot > 0 ? nombre.substring(0, firstDot) : nombre;
        }

        function extractDateFromFilename(nombre) {
            // Formato YYYY-MM-DD  (p.ej. catalina.2026-05-05.log)
            var matchFull = nombre.match(/(\d{4})-(\d{2})-(\d{2})/);
            if (matchFull) {
                return new Date(parseInt(matchFull[1], 10), parseInt(matchFull[2], 10) - 1, parseInt(matchFull[3], 10));
            }
            // Formato YYYY-WW  (p.ej. app.2026-18.log) — no seguido de otro dígito ni guión
            var matchWeek = nombre.match(/(\d{4})-(\d{1,2})(?![\d-])/);
            if (matchWeek) {
                var year = parseInt(matchWeek[1], 10);
                var week = parseInt(matchWeek[2], 10);
                return new Date(year, 0, 1 + (week - 1) * 7);
            }
            return null;
        }

        function compareLogFiles(a, b) {
            var dateA = extractDateFromFilename(a.nombre);
            var dateB = extractDateFromFilename(b.nombre);
            if (dateA === null && dateB === null) {
                return a.nombre.localeCompare(b.nombre);
            }
            if (dateA === null) { return -1; }
            if (dateB === null) { return 1; }
            return dateB - dateA;
        }

        function extractTipos(logFiles) {
            var tiposSet = {};
            for (var i = 0; i < logFiles.length; i++) {
                tiposSet[getFileType(logFiles[i].nombre)] = true;
            }
            var tipos = [];
            for (var tipo in tiposSet) {
                if (tiposSet.hasOwnProperty(tipo)) {
                    tipos.push(tipo);
                }
            }
            tipos.sort();
            return tipos;
        }

        $http({
            method: "GET",
            url: ix3Configuration.server.api + "/LogFile"
        }).then(function (response) {
            $scope.logFiles = response.data;
            $scope.logFiles.sort(compareLogFiles);
            $scope.tiposLogFile = extractTipos($scope.logFiles);
        }, function (response) {
            $scope.businessMessages = response.data;
        });

        $scope.filtrarPorTipo = function (logFile) {
            if (!$scope.tipoSeleccionado) {
                return true;
            }
            return getFileType(logFile.nombre) === $scope.tipoSeleccionado;
        };

        $scope.verFichero = function (idLogFile) {
            $state.go('lateralmenu.logfileDetail', {idLogFile: idLogFile});
        };
    }]);

app.controller("LogFileDetailController", ['$scope', '$http', '$stateParams', 'ix3Configuration', function ($scope, $http, $stateParams, ix3Configuration) {
        $scope.businessMessages = [];
        $scope.logFile = null;

        $http({
            method: "GET",
            url: ix3Configuration.server.api + "/LogFile/" + $stateParams.idLogFile
        }).then(function (response) {
            $scope.logFile = response.data;
        }, function (response) {
            $scope.businessMessages = response.data;
        });

        $scope.descargarFichero = function () {
            var blob = new Blob([$scope.logFile.content], {type: 'text/plain'});
            var url = URL.createObjectURL(blob);
            var a = document.createElement('a');
            a.href = url;
            a.download = $scope.logFile.nombre;
            a.click();
            URL.revokeObjectURL(url);
        };
    }]);
