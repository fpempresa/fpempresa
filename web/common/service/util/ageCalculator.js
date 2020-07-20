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


function ageCalculator(birthDate) {
    
    if (!birthDate) {
        return 0;
    }
    
    var now = new Date();
    var age = now.getFullYear() - birthDate.getFullYear();
    var m = now.getMonth() - birthDate.getMonth();
    if ((birthDate.getMonth() > now.getMonth()) || (m === 0 && now.getDate() < birthDate.getDate())) {
        //aun falta algún mes para que sea su cumpleaños
        age--;
    } else if (birthDate.getMonth() === now.getMonth()) {
        //Estamos en el mes de su cumpleaños
        if (now.getDate() < birthDate.getDate()) {
            
            age--;
        } else {
            //Acaba de ser su cumpleaños este mes
        }
        
    } else {
        //No hacemos nada pq ya ha sido su cumpleaños este año.
    }
    return age;
}

angular.module("common").value("ageCalculator",ageCalculator);