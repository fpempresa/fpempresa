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
app.config(['$stateProvider', function($stateProvider) {
        $stateProvider.state('/legal/aviso-legal', {
            url:'/legal/aviso-legal',
            templateUrl: 'views/legal/aviso-legal.html'
        });
        $stateProvider.state('/legal/cookies', {
            url:'/legal/cookies',
            templateUrl: 'views/legal/cookies.html'
        });        
        $stateProvider.state('/legal/politica-privacidad', {
            url:'/legal/politica-privacidad',
            templateUrl: 'views/legal/politica-privacidad.html'
        }); 
        $stateProvider.state('/legal/terminos-uso', {
            url:'/legal/terminos-uso',
            templateUrl: 'views/legal/terminos-uso.html'
        });
        
    }]);
