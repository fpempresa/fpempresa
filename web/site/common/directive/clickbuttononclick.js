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
"user strict";


//Esta directiva es un apaño para ocultar un códido de jQuery
//Solo sirve para que se genere un "click" sobre el elemento indicado 
//en la directiva
//El objetivo real de crear la directiva es para que:
//Se cierre el menu superior cuando está en un movil.
//Es decir al pulsar sobre una opción se cierra el propio menu
//TODO: En vez de poner la directiva en cada link <a> sería mejor ponerlo e¡solo en el botón y el él, busque los elementos <a>
app.directive("fpeClickButtonOnClick", ['$window', '$rootScope', '$location', function ($window, $rootScope, $location) {
        var directiveDefinitionObject = {
            restrict:"A",
            compile: function (tElement, tAttrs) {
                return {
                    pre: function (scope, iElement, iAttrs, controller, transcludeFn) {
                    },
                    post: function (scope, iElement, iAttrs, controller, transcludeFn) {
                        iElement.on("click",function() {
                            $("#"+iAttrs.fpeClickButtonOnClick+":visible").click();
                        });
                    }
                };
            }
        };

        return directiveDefinitionObject;
    }]);


