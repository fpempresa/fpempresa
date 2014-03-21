"use strict";

angular.module('es.logongas.ix3').provider("validator", [function() {
        function ValidatorProvider() {
            this.mensajePatterns = {
                required: "No puede estar vacio.",
                email: "No tiene el formato de EMail",
                maxlength: "Debe tener un tamaño menor o igual a {{maxlength}}",
                minlength: "Debe tener un tamaño mayor o igual a {{minlength}}",
                pattern: "No cumple la expresión regular: '{{pattern}}'",
                min: "Debe ser un valor mayor o igual a {{min}}",
                max: "Debe ser un valor menor o igual a {{max}}",
                url: "No tiene el formato de una URL",
                integer: "El valor '{{value}}' no es un número"
            };
            this.getMensajePatterns = function() {
                return this.mensajePatterns;
            };
            this.$get = ['$interpolate', function($interpolate) {
                    return new Validator($interpolate, this.mensajePatterns);
                }];
        }

        function Validator($interpolate, mensajePatterns) {
            var that = this;
            this.mensajePatterns = mensajePatterns;

            function getNormalizeAttributeName(attributeName) {
                var normalizeAttributeName;
                var separator;

                if (attributeName.indexOf("-") >= 0) {
                    separator = "-";
                } else if (attributeName.indexOf(":") >= 0) {
                    separator = ":";
                } else {
                    separator = undefined;
                }

                var parts = attributeName.split(separator);
                normalizeAttributeName = parts[parts.length - 1];

                return normalizeAttributeName;
            }

            function getMessage(inputElement, errorType) {
                var realInputElement = inputElement[0];
                var messagePattern = that.mensajePatterns[errorType];
                if (typeof (messagePattern) === "undefined") {
                    messagePattern = errorType;
                }

                var messageEvaluator = $interpolate(messagePattern);

                var attributes = {
                    value: inputElement.val()
                };

                for (var attributeIndex in realInputElement.attributes) {
                    var attributeName = realInputElement.attributes[attributeIndex].nodeName;
                    if (attributeName) {
                        var value = realInputElement.attributes[attributeIndex].nodeValue;
                        var normalizeAttributeName = getNormalizeAttributeName(attributeName);
                        attributes[normalizeAttributeName] = value;
                    }
                }

                var message = messageEvaluator(attributes);
                return message;
            }

            /**
             * Dado el nombre de un "input" obtiene el label asociado
             * @param {element} inputElement Elemento del que se busca el label
             * @param {string} defaultLabel El label por defecto si no se encuentra ningún otro label
             */
            function getLabel(inputElement, defaultLabel) {
                var label;

                if (inputElement.attr('id')) {
                    var labelElement = $('label[for="' + inputElement.attr('id') + '"]');
                    if (labelElement.length > 0) {
                        label = $(labelElement[0]).text();
                    } else {
                        label = defaultLabel;
                    }
                } else {
                    label = defaultLabel;
                }

                return label;
            }

            this.validateForm = function(angularForm) {
                var businessMessages = [];

                var formElement = $("form[name='" + angularForm.$name + "']");

                for (var propertyName in angularForm) {
                    if (typeof (propertyName) === "string" && propertyName.charAt(0) !== "$") {
                        if (angularForm[propertyName].$error) {
                            for (var errorType in angularForm[propertyName].$error) {
                                if (angularForm[propertyName].$error[errorType] === true) {
                                    var inputElement = $("[name='" + propertyName + "']", formElement);
                                    businessMessages.push({
                                        propertyName: propertyName,
                                        label: getLabel(inputElement, propertyName),
                                        message: getMessage(inputElement, errorType)
                                    });
                                }

                            }
                        }
                    }
                }

                return businessMessages;
            };

        }

        return new ValidatorProvider();

    }]);


