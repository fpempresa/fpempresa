/*
 * ix3 Copyright 2014-2020 Lorenzo.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * Servicio para gestionar la sesión en el servidor
 */
angular.module("es.logongas.ix3").factory("session", ['$http', 'ix3Configuration', '$q', '$rootScope', function ($http, ix3Configuration, $q, $rootScope) {

        return {
            login: login,
            logout: logout,
            logged: logged,
            setUser: setUser,
            getUser: getUser
        }

        var currentUser=null;

        function login(login, password) {
            var that=this;
            var deferred = $q.defer();
            var config = {
                method: 'POST',
                url: ix3Configuration.session.url + '/session',
                data: jQuery.param({
                    login: login,
                    password: password,
                    $expand:ix3Configuration.session.expand
                }),
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'                   
                }
            };

            $http(config).success(function (user, status, headers, config) {
                that.setUser(user);
                deferred.resolve(user);
            }).error(function (data, status, headers, config) {
                if (status === 400) {
                    deferred.reject(data);
                } else {
                    throw new Error("Fallo al obtener los datos:" + status + "\n" + data);
                }
            });

            return deferred.promise;
        }
        function logout() {
            var that=this;
            var deferred = $q.defer();

            var config = {
                method: 'DELETE',
                url: ix3Configuration.session.url + '/session'
            };

            $http(config).success(function (data, status, headers, config) {
                that.setUser(null);
                deferred.resolve(data);
            }).error(function (data, status, headers, config) {
                if (status === 400) {
                    deferred.reject(data);
                } else {
                    throw new Error("Fallo al obtener los datos:" + status + "\n" + data);
                }
            });

            return deferred.promise;
        }
        function logged() {
            var that=this;
            var deferred = $q.defer();

            var config = {
                method: 'GET',
                url: ix3Configuration.session.url + '/session',
                params: {
                    $expand:ix3Configuration.session.expand
                }
            };

            $http(config).success(function (user, status, headers, config) {
                that.setUser(user);
                deferred.resolve(user);
            }).error(function (data, status, headers, config) {
                if (status === 400) {
                    deferred.reject(data);
                } else {
                    throw new Error("Fallo al obtener los datos:" + status + "\n" + data);
                }
            });

            return deferred.promise;
        }

        function setUser(user) {
            if (user) {
                currentUser=user;
                $rootScope.user = user;
                $rootScope.$broadcast("ix3.session.login", user);
            } else {
                currentUser=null;
                $rootScope.user = null;
                $rootScope.$broadcast("ix3.session.logout", null);
            }
        }
        
        function getUser() {
            return currentUser;
        }

    }]);


