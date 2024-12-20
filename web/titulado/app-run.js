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


app.run(['session', 'richDomain', function (session, richDomain) {
        richDomain.extend(user);
        session.setUser(user);
    }]);


app.run(['$rootScope', '$location', '$transitions', function ($rootScope, $location, $transitions) {
    //Obligamos a ir a la página de los datos del titulado si  
    //aun no ha puesto los datos del titulado
    $transitions.onSuccess({ },function(transition) {   
        if (!$rootScope.user.titulado) {
            $location.url("/curriculum/titulado/new");
        }
    });        

}]);


