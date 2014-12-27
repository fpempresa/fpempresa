"use strict";
/**
 * Servicio para gestionar la sesión en el servidor
 */
angular.module("es.logongas.ix3").factory("session", ['$http', 'apiurl', '$q', '$rootScope', function ($http, apiurl, $q, $rootScope) {

        return {
            login: login,
            logout: logout,
            logged: logged,
            setUser: setUser
        }


        function login(login, password) {
            var that=this;
            var deferred = $q.defer();
            var config = {
                method: 'POST',
                url: apiurl + '/session',
                data: jQuery.param({
                    login: login,
                    password: password
                }),
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                }
            };

            $http(config).success(function (user, status, headers, config) {
                deferred.resolve(user);
                that.setUser(user);
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
                url: apiurl + '/session'
            };

            $http(config).success(function (data, status, headers, config) {
                deferred.resolve(data);
                that.setUser(null);
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
                url: apiurl + '/session'
            };

            $http(config).success(function (user, status, headers, config) {
                deferred.resolve(user);
                that.setUser(user);
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
                $rootScope.user = user;
                $rootScope.$broadcast("ix3.session.login", user);
            } else {
                $rootScope.user = null;
                $rootScope.$broadcast("ix3.session.logout", null);
            }
        }

    }]);


