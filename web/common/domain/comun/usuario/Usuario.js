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
"use strict";

angular.module("common").config(['richDomainProvider', function (richDomain) {

        richDomain.addEntityTransformer("Usuario", ['schemaEntities', function (schemaEntities) {
                var Usuario = {
                    getNombreCompleto: function () {
                        return this.nombre + (this.apellidos ? " " + this.apellidos : "");
                    },
                    getEstadoUsuarioDescription: function () {
                        return schemaEntities.getSchemaProperty(this.$propertyPath + ".estadoUsuario").getValueDescription(this.estadoUsuario);
                    },
                    getTipoUsuarioDescription: function () {
                        return schemaEntities.getSchemaProperty(this.$propertyPath + ".tipoUsuario").getValueDescription(this.tipoUsuario);
                    },
                    $validators: [
                        {
                            label: 'Confirmar Contraseña',
                            message: 'No es igual a la contraseña',
                            executeInActions: ['INSERT'],
                            stopOnFail:true,
                            rule: function () {
                                if (this.password === this.confirmPassword) {
                                    return true;
                                } else {
                                    return false;
                                }
                            }
                        },
                        {
                            label: "Terminos de uso y privacidad",
                            message: 'Debe aceptar los terminos de uso y la política de privacidad',
                            executeInActions: ['INSERT'],
                            stopOnFail:true,
                            rule: function () {
                                if (this.aceptarCondicionesPolitica === true) {
                                    return true;
                                } else {
                                    return false;
                                }
                            }
                        },
                        {
                            label: "Exactitud, veracidad y ofertas",
                            message: 'Debe aceptar que los centros educativos puedan comprobar los datos académicos así como conocer en que ofertas me he inscrito',
                            executeInActions: ['INSERT'],
                            stopOnFail:true,
                            rule: function () {
                                if (this.tipoUsuario === 'TITULADO') {
                                    if (this.aceptarVerificarTitulo === true) {
                                        return true;
                                    } else {
                                        return false;
                                    }
                                } else {
                                    return true;
                                }
                            }
                        },
                        {
                            label: "Envio de correos",
                            message: 'Si no marcas esta opción no te llegarán por correo las ofertas de empleo',
                            executeInActions: ['INSERT'],
                            rule: function () {
                                if (this.tipoUsuario!=="TITULADO") {
                                    return true;
                                }
                                
                                if (this.__avisadoDebeAceptarEnvioCorreos===true) {
                                    return true;
                                }
                                
                                this.__avisadoDebeAceptarEnvioCorreos=true;
                                
                                if (this.aceptarEnvioCorreos === true) {
                                    return true;
                                } else {
                                    return false;
                                }
                            }
                        }
                    ]
                };

                return function (object, propertyPath) {
                    object.$propertyPath = propertyPath;
                    angular.extend(object, Usuario);
                };
            }]);

    }]);