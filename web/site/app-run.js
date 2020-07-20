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


app.run(['$rootScope', function ($rootScope) {
        //Está hecho tan cumplicado para evitar que hagan spam con el correo
        var c3 = "\u0040";
        var c5 = "fp.com";
        var c2 = "te";
        var c1 = "sopor";
        var c4 = "emplea";

        $rootScope.correoSoporte = c1 + c2 + c3 + c4 + c5;
    }]);

