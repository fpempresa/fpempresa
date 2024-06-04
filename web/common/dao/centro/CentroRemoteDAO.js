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

        remoteDAOFactoryProvider.addExtendRemoteDAO("Centro", ['remoteDAO', function (remoteDAO) {

                remoteDAO.getCertificadosAnyoCentro = function (idCentro, expand) {
                    var deferred = this.$q.defer();
                    
                    var params = {};
                    if (expand) {
                        params.$expand = expand;
                    }
            
                    var config = {
                        method: 'GET',
                        url: this.baseUrl + '/' + this.entityName + "/" + idCentro + "/Certificado/Anyo",
                        data: null
                    };

                    this.$http(config).then(function (response) {
                        deferred.resolve(response.data);
                    }).catch(function (response) {
                        if (response.status === 400) {
                            deferred.reject(response.data);
                        } else {
                            throw new Error("Fallo al getCertificadosAnyoCentro la entidad:" + response.status + "\n" + idCentro);
                        }
                    });

                    return deferred.promise;
                };

                remoteDAO.getCertificadosCicloCentro = function (idCentro, anyo, expand) {
                    var deferred = this.$q.defer();
                    
                    var params = {};
                    if (expand) {
                        params.$expand = expand;
                    }
            
                    var config = {
                        method: 'GET',
                        url: this.baseUrl + '/' + this.entityName + "/" + idCentro + "/Certificado/Anyo/"+anyo,
                        data: null
                    };

                    this.$http(config).then(function (response) {
                        deferred.resolve(response.data);
                    }).catch(function (response) {
                        if (response.status === 400) {
                            deferred.reject(response.data);
                        } else {
                            throw new Error("Fallo al getCertificadosCicloCentro la entidad:" + response.status + "\n" + idCentro + "," + anyo);
                        }
                    });

                    return deferred.promise;
                };
                
                remoteDAO.getCertificadosTituloCentro = function (idCentro, anyo, idCiclo, expand) {
                    var deferred = this.$q.defer();
                    
                    var params = {};
                    if (expand) {
                        params.$expand = expand;
                    }
            
                    var config = {
                        method: 'GET',
                        url: this.baseUrl + '/' + this.entityName + "/" + idCentro + "/Certificado/Anyo/"+anyo+"/Ciclo/"+idCiclo,
                        data: null
                    };

                    this.$http(config).then(function (response) {
                        deferred.resolve(response.data);
                    }).catch(function (response) {
                        if (response.status === 400) {
                            deferred.reject(response.data);
                        } else {
                            throw new Error("Fallo al getCertificadosTituloCentro la entidad:" + response.status + "\n" + idCentro + "," + anyo);
                        }
                    });

                    return deferred.promise;
                };   
                
                remoteDAO.certificarTituloCentro = function (idCentro, anyo, idCiclo, tipoDocumento, nif, certificadoTitulo, idFormacionAcademica, expand) {
                    var deferred = this.$q.defer();
                    
                    var params = {};
                    if (expand) {
                        params.$expand = expand;
                    }
            
                    var config = {
                        method: 'PATCH',
                        url: this.baseUrl + '/' + this.entityName + "/" + idCentro + "/Certificado/Anyo/"+anyo+"/Ciclo/"+idCiclo+"/identificacion/"+tipoDocumento + "/" + nif+"/"+certificadoTitulo+"/"+idFormacionAcademica,
                        data: null
                    };

                    this.$http(config).then(function (response) {
                        deferred.resolve(null);
                    }).catch(function (response) {
                        if (response.status === 400) {
                            deferred.reject(response.data);
                        } else {
                            throw new Error("Fallo al certificarTituloCentro la entidad:" + response.status + "\n" + idCentro + "," + anyo+","+nif+","+certificadoTitulo);
                        }
                    });

                    return deferred.promise;
                };   
                
            }]);

    }]);