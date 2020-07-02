"use strict";
/**
 * Servicio para notificar mensajes de forma asincrona al usuario
 */
angular.module("es.logongas.ix3").service("notify", [function() {
        
        var defaultDelay=8000;
        
        function getDelay(delay) {
            
            
            if (delay) {
                return delay;
            } else {
                return defaultDelay;
            }
        }
        
        return {
            info: function(title, message,delay) {
                new PNotify({
                    title: title,
                    text: message,
                    type: 'info',
                    delay: getDelay(delay)
                });
            },
            warning: function(title, message,delay) {
                new PNotify({
                    title: title,
                    text: message,
                    delay: getDelay(delay)
                });
            },
            error: function(title, message,delay) {
                new PNotify({
                    title: title,
                    text: message,
                    type: 'error',
                    delay: getDelay(delay)
                });
            }
        };
    }]);