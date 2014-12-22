"use strict";
/**
 * Servicio para gestionar la sesión en el servidor
 */
angular.module("es.logongas.ix3").service("session", ['$http','apiurl','$q','$rootScope', function($http,apiurl,$q,$rootScope) {
        return {
            login: function(login, password) {
                var deferred = $q.defer();
                var config = {
                    method: 'POST',
                    url: apiurl + '/session',
                    data:jQuery.param({
                        login: login,
                        password: password
                    }),
                    headers:{
                        'Content-Type':'application/x-www-form-urlencoded'
                    }
                };

                $http(config).success(function(user, status, headers, config) {
                    deferred.resolve(user);
                    if (user) {
                        $rootScope.$broadcast("ix3.session.login",user);
                    }
                }).error(function(data, status, headers, config) {
                    if (status===400) {
                        deferred.reject(data);
                    } else {
                        throw new Error("Fallo al obtener los datos:"+status+"\n"+data);
                    }                   
                });
                
                return deferred.promise;
            },
            logout: function() {
                var deferred = $q.defer();
                
                var config = {
                    method: 'DELETE',
                    url: apiurl + '/session'
                };

                $http(config).success(function(data, status, headers, config) {
                    deferred.resolve(data);
                    $rootScope.$broadcast("ix3.session.logout",undefined);
                }).error(function(data, status, headers, config) {
                    if (status===400) {
                        deferred.reject(data);
                    } else {
                        throw new Error("Fallo al obtener los datos:"+status+"\n"+data);
                    } 
                });
                
                return deferred.promise;
            },
            logged: function() {
                var deferred = $q.defer();
                
                var config = {
                    method: 'GET',
                    url: apiurl + '/session'
                };

                $http(config).success(function(data, status, headers, config) {
                    deferred.resolve(data);
                }).error(function(data, status, headers, config) {
                    if (status===400) {
                        deferred.reject(data);
                    } else {
                        throw new Error("Fallo al obtener los datos:"+status+"\n"+data);
                    } 
                });
                
                return deferred.promise;
            }
        };
    }]);