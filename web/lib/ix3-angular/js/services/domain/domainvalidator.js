(function (undefined) {
    "use strict";

    /**
     * Valida un objeto de dominio
     */
    DomainValidator.$inyect = ['$injector', '$q', '$interpolate'];
    function DomainValidator($injector, $q, $interpolate) {

        this.validate = function (domainObject, action) {
            var deferred = $q.defer();
            var businessMessages;

            if ((domainObject) && (Array.isArray(domainObject.$validators))) {
                for (var i = 0; i < domainObject.$validators.length; i++) {
                    var validator = domainObject.$validators[i];

                    var businessMessage = resolveValidator(domainObject, validator, action);
                    if (businessMessage) {

                        //Solo creamos el array si hay algún mensaje.
                        //Se hace pq si no hay mensajes se debe retornar "undefined"
                        if (!businessMessages) {
                            businessMessages = [];
                        }

                        businessMessages.push(businessMessage);
                    }

                }
            }

            if (businessMessages) {
                deferred.reject(businessMessages);
            } else {
                deferred.resolve();
            }


            return deferred.promise;

        };


        /**
         * Ejecuta la validación.
         * @param {type} domainObject
         * @param {type} validator
         * @param {type} action
         * @returns {businessMessage|undefined} Si la regla tiene existo retorna "undefined" sino retorna un objeto BusinessMessage
         */
        function resolveValidator(domainObject, validator, action) {

            if (needExecute(validator.executeInActions, action)) {

                var success = $injector.invoke(validator.rule, domainObject);

                if (success === false) {

                    //El mensaje se puede interpolar
                    var message = $interpolate(validator.message)(domainObject);

                    var businessMessage = {
                        label: validator.label,
                        propertyName: validator.propertyName,
                        message: message
                    };

                    return businessMessage;
                } else {
                    return;
                }
            } else {
                return;
            }
        }


        /**
         * Calcula si es necesario o no ejecutar una validación
         * @param {type} executeInActions Array de String con las acciones en las que hay que ejecutar la regla. Si no hay array será en todas.
         * @param {type} action Nombre de la acción que se está realizando en este momento
         * @returns {Boolean} Retorna ''true'' si hay que ejecutarla y ''false'' si no hay que ejcutarla.
         */
        function needExecute(executeInActions, action) {
            if (Array.isArray(executeInActions)) {
                for (var i = 0; i < executeInActions.length; i++) {
                    if (executeInActions[i] === action) {
                        return true;
                    }
                }
                return false;
            } else {
                return true;
            }

        }

    }


    DomainValidatorProvider.$inyect = [];
    function DomainValidatorProvider() {
        this.$get = ['$injector', function ($injector) {
                var locals = {
                };

                return $injector.instantiate(DomainValidator, locals);
            }];
    }

    angular.module("es.logongas.ix3").provider("domainValidator", DomainValidatorProvider);

})();


