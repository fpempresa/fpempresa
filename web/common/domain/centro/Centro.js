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

angular.module("common").config(['richDomainProvider', function (richDomain) {

        richDomain.addEntityTransformer("Centro", ['schemaEntities', function (schemaEntities) {
                var Centro = {
                    getEstadoCentroDescription: function () {
                        return schemaEntities.getSchemaProperty(this.$propertyPath + ".estadoCentro").getValueDescription(this.estadoCentro);
                    }
                };

                return function (object, propertyPath) {
                    object.$propertyPath=propertyPath;
                    angular.extend(object, Centro);
                };
            }]);

    }]);

