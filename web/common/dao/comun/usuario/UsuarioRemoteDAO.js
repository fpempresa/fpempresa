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

        remoteDAOFactoryProvider.addExtendRemoteDAO("Usuario", ['remoteDAO', '$httpParamSerializer', function (remoteDAO, $httpParamSerializer) {
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

                    this.$http(config).then(function (response) {
                        if (response.status === 204) {
                            //El 204 (no content) realmente es un null
                            deferred.resolve(null);
                        } else {
                            deferred.resolve(response.data);
                        }
                    }).catch(function (response) {
                        if (response.status === 400) {
                            deferred.reject(response.data);
                        } else {
                            throw new Error("Fallo al insertar la entidad:" + response.status + "\n" + response.data);
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

                    this.$http(config).then(function (response) {
                        if (response.status === 204) {
                            //El 204 (no content) realmente es un null
                            deferred.resolve(null);
                        } else {
                            deferred.resolve(response.data);
                        }
                    }).catch(function (response) {
                        if (response.status === 400) {
                            deferred.reject(response.data);
                        } else {
                            throw new Error("Fallo al actualizar el centro:" + response.status + "\n" + response.data);
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

                    this.$http(config).then(function (response) {
                        deferred.resolve(null);
                    }).catch(function (response) {
                        if (response.status === 400) {
                            deferred.reject(response.data);
                        } else {
                            throw new Error("Fallo al insertar la entidad:" + response.status + "\n" + response.data);
                        }
                    });

                    return deferred.promise;
                };

                remoteDAO.enviarMailResetearContrasenya = function (email, captchaWord, keyCaptcha) {
                    var deferred = this.$q.defer();
                    var url=this.baseUrl + '/' + this.entityName + "/enviarMailResetearContrasenya";
                    var params={
                        email:email,
                        captchaWord: captchaWord,
                        keyCaptcha:keyCaptcha
                    }
                    var config = {
                        headers: {
                          'Content-Type': 'application/x-www-form-urlencoded'
                        }                      
                    };
                    this.$http.post(url,$httpParamSerializer(params),config).then(function () {
                        deferred.resolve(null);
                    }).catch(function (response) {
                        if (response.status === 400) {
                            deferred.reject(response.data);
                        } else {
                            throw new Error("Fallo al enviar la peticion de cambio de contaseña:" + response.status + "\n" + response.data);
                        }
                    });
                    return deferred.promise;
                };

                remoteDAO.enviarMailValidarEMail = function (email, captchaWord, keyCaptcha) {
                    var deferred = this.$q.defer();
                    
                    var url=this.baseUrl + '/' + this.entityName + "/enviarMailValidarEMail";
                    var params={
                            email:email,
                            captchaWord: captchaWord,
                            keyCaptcha:keyCaptcha
                        }   
                    var config = {
                        headers: {
                          'Content-Type': 'application/x-www-form-urlencoded'
                        }                      
                    };
                    this.$http.post(url,$httpParamSerializer(params),config).then(function () {
                        deferred.resolve(null);
                    }).catch(function (response) {
                        if (response.status === 400) {
                            deferred.reject(response.data);
                        } else {
                            throw new Error("Fallo al enviar la peticion de enviar mail de validación de EMail:" + response.status + "\n" + response.data);
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
                    this.$http(config).then(function () {
                        deferred.resolve(null);
                    }).catch(function (response) {
                        if (response.status === 400) {
                            deferred.reject(response.data);
                        } else {
                            throw new Error("Fallo al enviar la peticion de cambio de contaseña:" + response.status + "\n" + response.data);
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
                    this.$http(config).then(function () {
                        deferred.resolve(null);
                    }).catch(function (response) {
                        if (response.status === 400) {
                            deferred.reject(response.data);
                        } else {
                            throw new Error("Fallo al enviar la peticion de cambio de contraseña:" + response.status + "\n" + response.data);
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

                    this.$http(config).then(function (response) {
                        deferred.resolve(null);
                    }).catch(function (response) {
                        if (response.status === 400) {
                            deferred.reject(response.data);
                        } else {
                            throw new Error("Fallo la petición al servidor:" + response.status + "\n" + response.data);
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

                    this.$http(config).then(function (response) {
                        deferred.resolve(response.data);
                    }).catch(function (response) {
                        if (response.status === 400) {
                            deferred.reject(response.data);
                        } else {
                            throw new Error("Fallo al notificarUsuarioInactivo la entidad:" + response.status + "\n" + idIdentity);
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

                    this.$http(config).then(function (response) {
                        deferred.resolve(response.data);
                    }).catch(function (response) {
                        if (response.status === 400) {
                            deferred.reject(response.data);
                        } else {
                            throw new Error("Fallo al softDelete de la entidad:" + response.status + "\n" + idIdentity);
                        }
                    });

                    return deferred.promise;
                };
                
                remoteDAO.cancelarSuscripcion = function (idIdentity, token) {
                    var deferred = this.$q.defer();
                    var config = {
                        method: 'POST',
                        url: this.baseUrl + '/' + this.entityName + "/cancelarSuscripcion/" + idIdentity + "/" + token
                    };
                    this.$http(config).then(function () {
                        deferred.resolve(null);
                    }).catch(function (response) {
                        if (response.status === 400) {
                            deferred.reject(response.data);
                        } else {
                            throw new Error("Fallo al enviar la peticion de cancelar la Suscripcion:" + response.status + "\n" + response.data);
                        }
                    });
                    return deferred.promise;
                };
                
            }]);

    }]);