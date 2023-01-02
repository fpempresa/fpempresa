/* 
 * FPempresa Copyright (C) 2023 Lorenzo González
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Affero General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */


(function () {
    "use strict";



    angular.module('es.logongas.ix3').filter("ix3When", ['langUtil', function (langUtil) {

            return function (items, fieldName, compationOperatorName,valueCompare) {
                
                
                var compationOperatorFunctions={
                    "==":function(a,b) { return a==b;},
                    "===":function(a,b) { return a===b;},                    
                    "!=":function(a,b) { return a!=b;},
                    "!==":function(a,b) { return a!==b;},                    
                    ">":function(a,b) { return a>b;},
                    ">=":function(a,b) { return a>=b;},
                    "<":function(a,b) { return a<b;},
                    "<=":function(a,b) { return a<=b;}                  
                };
                
                var compationOperatorFunction=compationOperatorFunctions[compationOperatorName];
                
                
                
                var filteredItems=[];
                for(var i=0;i<items.length;i++) {
                    var item=items[i];
                    var value=langUtil.getValue(item,fieldName);
                    
                    var realValueCompare;
                    if (isNaN(valueCompare)) {
                        realValueCompare=langUtil.getValue(item,valueCompare);
                    } else {
                        realValueCompare=Number(valueCompare);
                    }
                    
                    if (compationOperatorFunction(value,realValueCompare)) {
                       filteredItems.push(item);
                    }
                    
                }
                
                 
                 
                return filteredItems;
                
            };
        }]);

})();