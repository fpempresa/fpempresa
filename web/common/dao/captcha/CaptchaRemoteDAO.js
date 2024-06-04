/* 
 * FPempresa Copyright (C) 2023 Lorenzo González
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

angular.module("common").config(['remoteDAOFactoryProvider','$injector', function (remoteDAOFactoryProvider, $injector) {

        RemoteDAOImplCatpcha.$inject = ['entityName','ix3Configuration', '$http', '$q', '$log'];
        function RemoteDAOImplCatpcha(entityName,ix3Configuration, $http, $q, $log) {
            this.entityName = entityName;
            this.baseUrl = ix3Configuration.server.api;
            this.$http = $http;
            this.$q = $q;


            this.getCaptcha = function () {
                var deferred = this.$q.defer();


                var config = {
                    method: 'GET',
                    url: this.baseUrl + '/' + this.entityName + "/keyCaptcha",
                    data: null
                };

                this.$http(config).then(function (response) {
                    deferred.resolve(response.data);
                }).catch(function (response) {
                    if (response.status === 400) {
                        deferred.reject(response.data);
                    } else {
                        throw new Error("Fallo al getCaptcha la entidad:" + response.status);
                    }
                });

                return deferred.promise;
            };
        }


        remoteDAOFactoryProvider.addRemoteDAO("Captcha", RemoteDAOImplCatpcha);

}]);
