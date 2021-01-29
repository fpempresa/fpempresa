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
angular.module("common").config(['remoteDAOFactoryProvider', function (remoteDAOFactoryProvider) {

        remoteDAOFactoryProvider.addExtendRemoteDAO("Usuario", ['remoteDAO', function (remoteDAO) {
                remoteDAO.updateEstadoUsuario = function (idIdentity, estadoUsuario, expand) {
                    var deferred = this.$q.defer();

                    var params = {};
                    if (expand) {
                        params.$expand = expand;
                    }

                    var config = {
                        method: 'PATCH',
                        url: this.baseUrl + '/' + this.entityName + "/" + idIdentity + "/estadoUsuario/" + estadoUsuario,
                        params: params
                    };

                    this.$http(config).success(function (data, status, headers, config) {
                        if (status === 204) {
                            //El 204 (no content) realmente es un null
                            deferred.resolve(null);
                        } else {
                            deferred.resolve(data);
                        }
                    }).error(function (data, status, headers, config) {
                        if (status === 400) {
                            deferred.reject(data);
                        } else {
                            throw new Error("Fallo al insertar la entidad:" + status + "\n" + data);
                        }
                    });

                    return deferred.promise;
                };

                remoteDAO.updateCentro = function (idIdentity, centro, expand) {
                    var deferred = this.$q.defer();

                    var params = {};
                    if (expand) {
                        params.$expand = expand;
                    }

                    var config = {
                        method: 'PATCH',
                        url: this.baseUrl + '/' + this.entityName + "/" + idIdentity + "/centro/" + centro.idCentro,
                        params: params
                    };

                    this.$http(config).success(function (data, status, headers, config) {
                        if (status === 204) {
                            //El 204 (no content) realmente es un null
                            deferred.resolve(null);
                        } else {
                            deferred.resolve(data);
                        }
                    }).error(function (data, status, headers, config) {
                        if (status === 400) {
                            deferred.reject(data);
                        } else {
                            throw new Error("Fallo al actualizar el centro:" + status + "\n" + data);
                        }
                    });

                    return deferred.promise;
                };


                remoteDAO.updatePassword = function (idIdentity, currentPassword, newPassword) {
                    var deferred = this.$q.defer();

                    var data = {
                        currentPassword: currentPassword,
                        newPassword: newPassword
                    };

                    var config = {
                        method: 'PATCH',
                        url: this.baseUrl + '/' + this.entityName + "/" + idIdentity + "/updatePassword",
                        data: data
                    };

                    this.$http(config).success(function (data, status, headers, config) {
                        deferred.resolve(null);
                    }).error(function (data, status, headers, config) {
                        if (status === 400) {
                            deferred.reject(data);
                        } else {
                            throw new Error("Fallo al insertar la entidad:" + status + "\n" + data);
                        }
                    });

                    return deferred.promise;
                };

                remoteDAO.enviarMailResetearContrasenya = function (email) {
                    var deferred = this.$q.defer();
                    var config = {
                        method: 'POST',
                        url: this.baseUrl + '/' + this.entityName + "/enviarMailResetearContrasenya/" + email
                    };
                    this.$http(config).success(function () {
                        deferred.resolve(null);
                    }).error(function (data, status) {
                        if (status === 400) {
                            deferred.reject(data);
                        } else {
                            throw new Error("Fallo al enviar la peticion de cambio de contaseña:" + status + "\n" + data);
                        }
                    });
                    return deferred.promise;
                };

                remoteDAO.validarEmail = function (idIdentity, claveValidarEmail) {
                    var deferred = this.$q.defer();
                    var config = {
                        method: 'POST',
                        url: this.baseUrl + '/' + this.entityName + "/validarEmail/" + idIdentity + "/" + claveValidarEmail
                    };
                    this.$http(config).success(function () {
                        deferred.resolve(null);
                    }).error(function (data, status) {
                        if (status === 400) {
                            deferred.reject(data);
                        } else {
                            throw new Error("Fallo al enviar la peticion de cambio de contaseña:" + status + "\n" + data);
                        }
                    });
                    return deferred.promise;
                };


                remoteDAO.resetearContrasenya = function (idIdentity, claveResetearContrasenya, nuevaContrasenya) {
                    var deferred = this.$q.defer();
                    var data = {
                        idIdentity: idIdentity,
                        claveResetearContrasenya: claveResetearContrasenya,
                        nuevaContrasenya: nuevaContrasenya
                    };
                    var config = {
                        method: 'POST',
                        url: this.baseUrl + '/' + this.entityName + "/resetearContrasenya",
                        data: data
                    };
                    this.$http(config).success(function () {
                        deferred.resolve(null);
                    }).error(function (data, status) {
                        if (status === 400) {
                            deferred.reject(data);
                        } else {
                            throw new Error("Fallo al enviar la peticion de cambio de contraseña:" + status + "\n" + data);
                        }
                    });
                    return deferred.promise;
                };

                remoteDAO.enviarMensajeSoporte = function (mensajeSoporte) {
                    var deferred = this.$q.defer();


                    var config = {
                        method: 'POST',
                        url: this.baseUrl + '/' + this.entityName + "/mensajeSoporte",
                        data: mensajeSoporte
                    };

                    this.$http(config).success(function (data, status, headers, config) {
                        deferred.resolve(null);
                    }).error(function (data, status, headers, config) {
                        if (status === 400) {
                            deferred.reject(data);
                        } else {
                            throw new Error("Fallo la petición al servidor:" + status + "\n" + data);
                        }
                    });

                    return deferred.promise;
                };
                
                
                remoteDAO.notificarUsuarioInactivo = function (idIdentity) {
                    var deferred = this.$q.defer();

                    var config = {
                        method: 'PATCH',
                        url: this.baseUrl + '/' + this.entityName + "/" + idIdentity + "/notificarUsuarioInactivo",
                        data: null
                    };

                    this.$http(config).success(function (data, status, headers, config) {
                        deferred.resolve(data);
                    }).error(function (data, status, headers, config) {
                        if (status === 400) {
                            deferred.reject(data);
                        } else {
                            throw new Error("Fallo al notificarUsuarioInactivo la entidad:" + status + "\n" + idIdentity);
                        }
                    });

                    return deferred.promise;
                };
                
                remoteDAO.softDelete = function (idIdentity) {
                    var deferred = this.$q.defer();

                    var config = {
                        method: 'PATCH',
                        url: this.baseUrl + '/' + this.entityName + "/" + idIdentity + "/softDelete",
                        data: null
                    };

                    this.$http(config).success(function (data, status, headers, config) {
                        deferred.resolve(data);
                    }).error(function (data, status, headers, config) {
                        if (status === 400) {
                            deferred.reject(data);
                        } else {
                            throw new Error("Fallo al softDelete de la entidad:" + status + "\n" + idIdentity);
                        }
                    });

                    return deferred.promise;
                };
                
            }]);

    }]);