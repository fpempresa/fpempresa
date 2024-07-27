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


(function (undefined) {
    "use strict";

    angular.module("common").run(['$transitions', 'animateScroll', function ($transitions, animateScroll) {


        $transitions.onSuccess({ },function(transition) {
            if (transition.to().scroll)  {
                animateScroll.scroll(transition.to().scroll);
            }
                
         });



    }]);


/**
 * Servicio de Animaciones para moverse por la página
 */
AnimateScroll.$inject=['$timeout'];
function AnimateScroll($timeout) {

    /**
     * Hace un Scroll animado hasta un elemento
     * @param {Element || String} element Elemento hasta el que se hace un Scroll. Puede ser el propio elemento o un String con el "id" del elemento
     */
    var scroll = function (element) {


        $timeout(function () {
            if (typeof (element) === "string") {
                element = $("#" + element);
            }
            
            $('html, body').stop().animate({
                scrollTop: element.offset().top-40
            }, 1500, 'easeOutSine');
        },400);

    }


    return {
        scroll: scroll
    }
}
angular.module("common").factory("animateScroll", AnimateScroll);


})();






