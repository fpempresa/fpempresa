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
                            rule: function () {
                                if (this.aceptarCondicionesPolitica === true) {
                                    return true;
                                } else {
                                    return false;
                                }
                            }
                        },
                        {
                            label: "Envio de correos",
                            message: 'Debe aceptar que se envien correos electrónicos',
                            executeInActions: ['INSERT'],
                            rule: function () {
                                if (this.aceptarEnvioCorreos === true) {
                                    return true;
                                } else {
                                    return false;
                                }
                            }
                        },                      
                        {
                            label: "Exactitud y veracidad",
                            message: 'Debe aceptar que los centros educativos puedan comprobar los datos académicos',
                            executeInActions: ['INSERT'],
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
                        }

                    ]
                };

                return function (object, propertyPath) {
                    object.$propertyPath = propertyPath;
                    angular.extend(object, Usuario);
                };
            }]);

    }]);