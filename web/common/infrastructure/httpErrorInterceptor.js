/**
 *   FPempresa
 *   Copyright (C) 2021  Lorenzo González
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

/**
 * Intercepta las llamadas de $http y si es un 403 redirige a la pantalla de login
 */

angular.module("common").config(['$httpProvider', function ($httpProvider) {
        $httpProvider.interceptors.push(['$q', function ($q) {
                return {
                    'responseError': function (response) {
                        if (response.status === 403) {
                            alert("La sesión ha caducado");
                            window.location.href = getContextPath() + "/site/index.html#!/login";
                            response.data=[
                                {
                                    propertyName:null,
                                    message: "La sesión ha caducado." + response.statusText
                                }
                            ]
                            response.status = 400;
                        } else if (response.status === 500) {
                            response.data=[
                                {
                                    propertyName:null,
                                    message: "Ha ocurrido un fallo en la petición al servidor"
                                }
                            ]
                            response.status = 400;
                        }
                        return $q.reject(response);
                    }
                };
            }]);

    }]);