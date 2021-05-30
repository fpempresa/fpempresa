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
angular.module("common").config(['repositoryFactoryProvider', function (repositoryFactoryProvider) {

        repositoryFactoryProvider.addExtendRepository("Centro", ['repository', '$q', 'richDomain', function (repository, $q, richDomain) {



                repository.getCertificadosAnyoCentro = function (idCentro, expand) {
                    var deferred = $q.defer();
                    this.remoteDAO.getCertificadosAnyoCentro(idCentro, expand).then(function (data) {
                        deferred.resolve(data);
                    }, function (data) {
                        richDomain.extend(data);
                        deferred.reject(data);
                    });
                    return deferred.promise;
                };
                
                repository.getCertificadosCicloCentro = function (idCentro, anyo, expand) {
                    var deferred = $q.defer();
                    this.remoteDAO.getCertificadosCicloCentro(idCentro, anyo, expand).then(function (data) {
                        deferred.resolve(data);
                    }, function (data) {
                        richDomain.extend(data);
                        deferred.reject(data);
                    });
                    return deferred.promise;
                };
                
                repository.getCertificadosTituloCentro = function (idCentro, anyo, idCiclo, expand) {
                    var deferred = $q.defer();
                    this.remoteDAO.getCertificadosTituloCentro(idCentro, anyo, idCiclo, expand).then(function (data) {
                        deferred.resolve(data);
                    }, function (data) {
                        richDomain.extend(data);
                        deferred.reject(data);
                    });
                    return deferred.promise;
                };
                
                repository.certificarTituloCentro = function (idCentro, anyo, idCiclo, tipoDocumento, nif, certificadoTitulo, expand) {
                    var deferred = $q.defer();
                    this.remoteDAO.certificarTituloCentro(idCentro, anyo, idCiclo, tipoDocumento, nif, certificadoTitulo, expand).then(function (data) {
                        deferred.resolve(data);
                    }, function (data) {
                        richDomain.extend(data);
                        deferred.reject(data);
                    });
                    return deferred.promise;
                };
            }]);

    }]);