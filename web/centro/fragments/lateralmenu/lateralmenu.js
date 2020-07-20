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

app.controller("LateralMenuController", ['$scope', '$state', '$location', 'dialog', function ($scope, $state, $location, dialog) {
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

        $scope.cambiarContrasenya = function () {
            dialog.create("cambiarContrasenya", $scope.user);
        };

    }]);