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

(function () {
    "use strict";



    angular.module('es.logongas.ix3').filter("ix3OrderBy", ['$filter','langUtil', function ($filter, langUtil) {

            return function (items, fields, reverse) {
                
                var realFields;
                if (Array.isArray(fields)) {
                    realFields=fields;
                } else {
                    realFields=[];
                    realFields.push(fields);
                }
                
                function compare(item1, item2, property) {
                    
                    var value1=getValue(item1,property);
                    var value2=getValue(item2,property);
                    
                    if (reverse) {
                        return value2.localeCompare(value1);
                    } else {
                        return value1.localeCompare(value2);
                    }
                }
                
                function getValue(obj,property) {
                    if (langUtil.hasOwnProperty(obj,property)) {
                        var value=langUtil.getValue(obj,property);
                        if (typeof(value)==="string") {
                            return value;
                        } else if (typeof(value)==="number") {
                            return value+"";
                        } else if (typeof(value)==="undefined") {
                            return "";
                        } else if (typeof(value)==="object") {
                            if (value===null) {
                                return "";
                            } else {
                                return value.toString();
                            }
                        } else {
                            return "";
                        }
                    } else {
                        return "";
                    }
                }
                
                
                
                items.sort(function (item1, item2) {
                    
                    for(var i=0;i<realFields.length;i++) {
                        
                        var resultCompare=compare(item1,item2,realFields[i]);
                        if (resultCompare!==0) {
                            if (reverse) {
                                
                            }
                            
                            return resultCompare;
                        }
                        
                    }
                    
                    return 0;
                    
                    
                 });
                 
                 
                return items;
                
            };
        }]);

})();