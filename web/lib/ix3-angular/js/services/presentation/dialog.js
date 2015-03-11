(function (undefined) {

    "use strict";
    /**
     * Servicio para crear ventana modales
     */
    Dialog.$inject = ['$rootScope', '$compile', '$http', '$q'];
    function Dialog($rootScope, $compile, $http, $q) {

        /**
         * Crea una nueva ventana modal pero no se muestra.
         * @param {URL} urlHTML La URL del HTML la ventana modal. 
         * @param {Object} data Dato a pasar a la ventana modal
         * @returns {Promise} Un objeto Promise para obtener el resultado de la ventana.
         */
        this.create = function (urlHTML, data) {
            var deferred = $q.defer();

            $http({
                method: 'GET',
                url: urlHTML
            }).success(function (dialogTemplate, status, headers, config) {
                var dialogElement = $("<div></div>");

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
                    create: function (event, ui) {
                        $(dialogElement).closest('div.ui-dialog')
                                .find('button.ui-dialog-titlebar-close')
                                .click(function (e) {
                                    //Se llama con el botón de laspa de la ventana
                                    e.preventDefault();
                                    destroyDialog(dialogScope, dialogElement);
                                    deferred.reject(undefined);
                                });

                    }

                });

                var dialogScope = $rootScope.$new();
                dialogScope.dialog = {
                    closeOK: function (returnValue) {
                        destroyDialog(dialogScope, dialogElement);
                        deferred.resolve(returnValue);
                    },
                    closeCancel: function () {
                        destroyDialog(dialogScope, dialogElement);
                        deferred.reject(undefined);
                    },
                    open: function (options) {
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

            }).error(function (data, status, headers, config) {
                throw new Error("Fallo al cargar el HTML:" + urlHTML + "\n" + data);
            });


            return deferred.promise;
        }

    }

    angular.module("es.logongas.ix3").service("dialog", Dialog);
})();



