/* 
 * FPempresa Copyright (C) 2021 Lorenzo González
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Affero General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
angular.module("common").config(['remoteDAOFactoryProvider', function (remoteDAOFactoryProvider) {

        remoteDAOFactoryProvider.addExtendRemoteDAO("Oferta", ['remoteDAO', function (remoteDAO) {

                  remoteDAO.cerrarOferta = function (idOferta, token) {
                    var deferred = this.$q.defer();
                    var config = {
                        method: 'POST',
                        url: this.baseUrl + '/' + this.entityName + "/cerrarOferta/" + idOferta + "/" + token
                    };
                    this.$http(config).success(function () {
                        deferred.resolve(null);
                    }).error(function (data, status) {
                        if (status === 400) {
                            deferred.reject(data);
                        } else {
                            throw new Error("Fallo al enviar la peticion de cancelar cerrar la oferta:" + status + "\n" + data);
                        }
                    });
                    return deferred.promise;
                };
                
            }]);

    }]);

