/*
 * ix3 Copyright 2014-2020 Lorenzo.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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