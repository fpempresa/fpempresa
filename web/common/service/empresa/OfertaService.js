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
angular.module("common").config(['serviceFactoryProvider', function (serviceFactoryProvider) {

        serviceFactoryProvider.addExtendService("Oferta", ['service', '$q','session', function (service, $q,session) {

                service.cerrarOferta = function (idOferta, token) {
                    var deferred = $q.defer();
                    this.repository.cerrarOferta(idOferta, token
                            ).then(function () {
                        deferred.resolve();
                    }, function (data) {
                        deferred.reject(data);
                    });
                    return deferred.promise;
                };

            }]);

    }]);