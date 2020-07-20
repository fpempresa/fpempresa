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

app.config(['$stateProvider', function ($stateProvider) {
        $stateProvider.state('createaccount/init', {
            url:'/createaccount/init',
            templateUrl: 'views/createaccount/init.html',
            controller: 'CreateAccountInitController'
        });
        $stateProvider.state('createaccount/init/tipoUsuario', {
            url:'/createaccount/init/:tipoUsuario',
            templateUrl: 'views/createaccount/init.html',
            controller: 'CreateAccountInitController'
        });
        $stateProvider.state('/createaccount/end', {
            url:'/createaccount/end/:tipoUsuario',
            templateUrl: 'views/createaccount/end.html',
            controller: 'CreateAccountEndController'
        });

    }]);

