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
angular.module("common").config(['repositoryFactoryProvider', function (repositoryFactoryProvider) {

        repositoryFactoryProvider.addExtendRepository("Usuario", ['repository', '$q', 'richDomain', function (repository, $q, richDomain) {

                repository.updateEstadoUsuario = function (idIdentity, estadoUsuario, expand) {
                    var deferred = $q.defer();

                    this.remoteDAO.updateEstadoUsuario(idIdentity, estadoUsuario, expand).then(function (data) {
                        richDomain.extend(data);
                        deferred.resolve(data);
                    }, function (data) {
                        richDomain.extend(data);
                        deferred.reject(data);
                    });

                    return deferred.promise;

                };

                repository.updateCentro = function (idIdentity, centro, expand) {
                    var deferred = $q.defer();

                    this.remoteDAO.updateCentro(idIdentity, centro, expand).then(function (data) {
                        richDomain.extend(data);
                        deferred.resolve(data);
                    }, function (data) {
                        richDomain.extend(data);
                        deferred.reject(data);
                    });

                    return deferred.promise;

                };

                repository.updatePassword = function (idIdentity, currentPassword, newPassword) {
                    var deferred = $q.defer();

                    this.remoteDAO.updatePassword(idIdentity, currentPassword, newPassword).then(function () {
                        deferred.resolve();
                    }, function (data) {
                        richDomain.extend(data);
                        deferred.reject(data);
                    });

                    return deferred.promise;

                };

                repository.enviarMailResetearContrasenya = function (email, captchaWord, keyCaptcha) {
                    var deferred = $q.defer();
                    this.remoteDAO.enviarMailResetearContrasenya(email, captchaWord, keyCaptcha).then(function () {
                        deferred.resolve();
                    }, function (data) {
                        richDomain.extend(data);
                        deferred.reject(data);
                    });
                    return deferred.promise;
                };

                repository.enviarMailValidarEMail = function (email, captchaWord, keyCaptcha) {
                    var deferred = $q.defer();
                    this.remoteDAO.enviarMailValidarEMail(email, captchaWord, keyCaptcha).then(function () {
                        deferred.resolve();
                    }, function (data) {
                        richDomain.extend(data);
                        deferred.reject(data);
                    });
                    return deferred.promise;
                };

                repository.validarEmail = function (idIdentity, claveValidarEmail) {
                    var deferred = $q.defer();
                    this.remoteDAO.validarEmail(idIdentity, claveValidarEmail).then(function () {
                        deferred.resolve();
                    }, function (data) {
                        richDomain.extend(data);
                        deferred.reject(data);
                    });
                    return deferred.promise;
                };

                repository.resetearContrasenya = function (idIdentity, claveResetearContrasenya, nuevaContrasenya) {
                    var deferred = $q.defer();
                    this.remoteDAO.resetearContrasenya(idIdentity, claveResetearContrasenya, nuevaContrasenya).then(function () {
                        deferred.resolve();
                    }, function (data) {
                        richDomain.extend(data);
                        deferred.reject(data);
                    });
                    return deferred.promise;
                };

                repository.enviarMensajeSoporte = function (mensajeSoporte) {
                    var deferred = $q.defer();
                    this.remoteDAO.enviarMensajeSoporte(mensajeSoporte).then(function () {
                        deferred.resolve();
                    }, function (data) {
                        richDomain.extend(data);
                        deferred.reject(data);
                    });
                    return deferred.promise;
                };
                
                
                repository.notificarUsuarioInactivo = function (idIdentity) {
                    var deferred = $q.defer();
                    this.remoteDAO.notificarUsuarioInactivo(idIdentity).then(function (data) {
                        deferred.resolve(data);
                    }, function (data) {
                        richDomain.extend(data);
                        deferred.reject(data);
                    });
                    return deferred.promise;
                };
                repository.softDelete = function (idIdentity) {
                    var deferred = $q.defer();
                    this.remoteDAO.softDelete(idIdentity).then(function (data) {
                        deferred.resolve(data);
                    }, function (data) {
                        richDomain.extend(data);
                        deferred.reject(data);
                    });
                    return deferred.promise;
                };
                
                repository.cancelarSuscripcion = function (idIdentity, token) {
                    var deferred = $q.defer();
                    this.remoteDAO.cancelarSuscripcion(idIdentity, token).then(function () {
                        deferred.resolve();
                    }, function (data) {
                        richDomain.extend(data);
                        deferred.reject(data);
                    });
                    return deferred.promise;
                };

            }]);

    }]);