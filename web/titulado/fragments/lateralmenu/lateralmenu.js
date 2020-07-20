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
"user strict";

app.controller("LateralMenuController", ['$scope', '$location', '$state', 'dialog', 'ix3Configuration', function ($scope, $location, $state, dialog, ix3Configuration) {
        $scope.isItemSelected = function (option) {
            if (option) {
                var regExp = new RegExp(option);
                if (regExp.test($location.url()) === true) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        };


        $scope.getUrlIfTitulado=function(url) {
            if ( ($scope.user) && ($scope.user.titulado) ) {
                return url;
            } else {
                "";
            }
        }

        $scope.cambiarContrasenya = function () {
             if ( ($scope.user) && ($scope.user.titulado) ) {
                dialog.create('cambiarContrasenya', $scope.user);
            }
        }

        $scope.ix3Configuration=ix3Configuration;
        
        $scope.fotoIndex=0;
        $scope.updateFoto=function() {
            $scope.fotoIndex=$scope.fotoIndex+1;
        }
        $scope.failUpdateFoto=function() {
            alert("No ha sido posible subir la foto.\nPrueba con otra o hazla mas pequeña.")
        }

    }]);