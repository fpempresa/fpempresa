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
angular.module("common").config(['serviceFactoryProvider', function (serviceFactoryProvider) {

        serviceFactoryProvider.addExtendService("Centro", ['service', '$q','session', function (service, $q,session) {

                service.update = function (id, entity, expand) {
                    var deferred = $q.defer();

                    this.repository.update(id, entity, expand).then(function (centro) {
                        if ((session.getUser().centro) && (centro.idCentro===session.getUser().centro.idCentro)) {
                            session.getUser().centro=centro;
                        }
                        deferred.resolve(centro);
                    }, function (businessMessages) {
                        deferred.reject(businessMessages);
                    });

                    return deferred.promise;
                };

                service.get = function (id, expand) {
                    var deferred = $q.defer();

                    this.repository.get(id, expand).then(function (centro) {
                        if ((session.getUser().centro) && (centro.idCentro===session.getUser().centro.idCentro)) {
                            session.getUser().centro=centro;
                        }
                        
                        deferred.resolve(centro);
                    }, function (businessMessages) {
                        deferred.reject(businessMessages);
                    });

                    return deferred.promise;
                };

                service.getCertificadosAnyoCentro = function (idCentro, expand) {
                    var deferred = $q.defer();

                    this.repository.getCertificadosAnyoCentro(idCentro, expand).then(function (data) {
                        deferred.resolve(data);
                    }, function (businessMessages) {
                        deferred.reject(businessMessages);
                    });

                    return deferred.promise;
                };
                service.getCertificadosCicloCentro = function (idCentro,anyo, expand) {
                    var deferred = $q.defer();

                    this.repository.getCertificadosCicloCentro(idCentro, anyo, expand).then(function (data) {
                        deferred.resolve(data);
                    }, function (businessMessages) {
                        deferred.reject(businessMessages);
                    });

                    return deferred.promise;
                };
                service.getCertificadosTituloCentro = function (idCentro,anyo, idCiclo, expand) {
                    var deferred = $q.defer();

                    this.repository.getCertificadosTituloCentro(idCentro, anyo, idCiclo, expand).then(function (data) {
                        deferred.resolve(data);
                    }, function (businessMessages) {
                        deferred.reject(businessMessages);
                    });

                    return deferred.promise;
                };
                
                service.certificarTituloCentro = function (idCentro,anyo, idCiclo, tipoDocumento, nif,certificadoTitulo, idFormacionAcademica, expand) {
                    var deferred = $q.defer();

                    this.repository.certificarTituloCentro(idCentro, anyo, idCiclo, tipoDocumento, nif,certificadoTitulo, idFormacionAcademica, expand).then(function (data) {
                        deferred.resolve(null);
                    }, function (businessMessages) {
                        deferred.reject(businessMessages);
                    });

                    return deferred.promise;
                };                

            }]);

    }]);