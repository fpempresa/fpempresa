"use strict";
/**
 * Servicio para crear ventana modales
 */
angular.module("es.logongas.ix3").service("dialog", ['$rootScope', '$compile', '$http', '$q', function($rootScope, $compile, $http, $q) {
        var dialogCache = [];

        return {
            /**
             * Crea una nueva ventana modal pero no se muestra.
             * @param {URL} url La URL de la ventan modal. Sin la extensión del fichero , ya que carga el ".js" y el ".html"
             * @param {Object} data Dato a pasar a la ventana modal
             * @returns {Promise} Un objeto Promise para obtener el resultado de la ventana.
             */
            create: function(url, data) {
                var deferred = $q.defer();
                var jsURL = url + '.js';
                var htmlURL = url + '.html';


                /**
                 * Función para cargar el Script con Cache
                 * @param {URL} url La URL de JavaScript a cargar
                 * @param {function} success La función a llamar cuando se ha cargado
                 * @param {function} error La función a llamar si falla
                 * @returns {void}
                 */
                function getScript(url, success, error) {
                    jQuery.ajax({
                        type: "GET",
                        url: url,
                        success: success,
                        error: error,
                        dataType: "script",
                        cache: false
                    });
                }



                getScript(jsURL, function() {
                    $http({
                        method: 'GET',
                        url: htmlURL
                    }).success(function(dialogTemplate, status, headers, config) {
                        var dialogElement=$("<div></div>");

                        function destroyDialog(scope, element) {
                            scope.$destroy();
                            element.dialog("close");
                            element.dialog("destroy");
                            element.remove();
                        }

                        dialogElement.dialog({
                            closeOnEscape: true,
                            modal: true,
                            autoOpen: false,
                            create: function(event, ui) {
                                $(dialogElement).closest('div.ui-dialog')
                                        .find('button.ui-dialog-titlebar-close')
                                        .click(function(e) {
                                            //Se llama con el botón de laspa de la ventana
                                            e.preventDefault();
                                            destroyDialog(dialogScope, dialogElement);
                                            deferred.reject(undefined);
                                        });

                            }

                        });

                        var dialogScope = $rootScope.$new();
                        dialogScope.dialog = {
                            closeOK: function(returnValue) {
                                destroyDialog(dialogScope, dialogElement);
                                deferred.resolve(returnValue);
                            },
                            closeCancel: function() {
                                destroyDialog(dialogScope, dialogElement);
                                deferred.reject(undefined);
                            },
                            open: function(options) {
                                if (options) {
                                    for (var propertyName in options) {
                                        var value = options[propertyName];
                                        dialogElement.dialog("option", propertyName, value);
                                    }
                                }
                                dialogElement.dialog("open");
                            },
                            data: data
                        };

                        dialogElement.html(dialogTemplate);
                        $compile(dialogElement.contents())(dialogScope);

                    }).error(function(data, status, headers, config) {
                        throw new Error("Fallo al cargar el HTML\n" + jsURL + "\n" + data);
                    });

                }, function(jqXHR, textStatus, errorThrown) {
                    throw new Error("Fallo al cargar el JavaScript\n" + htmlURL + "\n" + errorThrown);
                });

                return deferred.promise;
            }
        };
    }]);