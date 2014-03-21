"use strict";

angular.module('es.logongas.ix3').directive('ix3Input', ['bootstrap', '$compile', function(bootstrap, $compile) {

        /**
         * Obtiene si el objeto values tiene algun valor
         * @param {type} values El objeto del que se averigua si tiene "values"
         * @returns {Boolean} Retorna 'true' si tiene values
         */
        function hasValues(values) {
            if (values===null) {
                return false;
            } else {
               if (angular.isArray(values)===false) {
                  throw Error("El contenido de la propiedad Values debe ser un array pero es:"+typeof(values)); 
               } 
               
               return true;
            }
        }

        function getValidationAttributes(metadata) {
            var validationAttributes;

            if (metadata.required === true) {
                validationAttributes = validationAttributes + ' ng-required="true" ';
            }
            if ((metadata.pattern) && (metadata.type === "STRING")) {
                validationAttributes = validationAttributes + ' ng-pattern="/' + metadata.pattern + '/" ';
            }
            if ((metadata.minimum) && (metadata.type === "INTEGER")) {
                validationAttributes = validationAttributes + ' min="' + metadata.minimum + '" ';
            }
            if ((metadata.maximum) && (metadata.type === "INTEGER")) {
                validationAttributes = validationAttributes + ' max="' + metadata.maximum + '" ';
            }
            if ((metadata.minLength) && (metadata.type === "STRING")) {
                validationAttributes = validationAttributes + ' ng-minlength="' + metadata.minLength + '" ';
            }
            if ((metadata.maxlength) && (metadata.type === "STRING")) {
                validationAttributes = validationAttributes + ' ng-maxlength="' + metadata.maxLength + '" ';
            }


            return validationAttributes;
        }


        function getHTMLSelect(name, metadata) {
            var cssClassLabelCol;
            var cssClassControlLabel;
            var cssClassDivInputCol;
            if (bootstrap.version >= 3) {
                cssClassLabelCol = "col-sm-3";
                cssClassControlLabel = "control-label";
                cssClassDivInputCol = "col-sm-9";
            } else {
                cssClassLabelCol = "";
                cssClassControlLabel = "control-label";
                cssClassDivInputCol = "controls";
            }

            var attributes = getValidationAttributes(metadata);

            var label = metadata.label.charAt(0).toUpperCase() + metadata.label.slice(1);

            var id = "field." + name + "." + (new Date()).getTime();

            var ngOptions;
            var ngModel;
            if (metadata.type === "OBJECT") {
                ngOptions = "value.key." + metadata.primaryKeyPropertyName + " as value.description for value in metadata.values";
                ngModel="model." + name+ "."+ metadata.primaryKeyPropertyName;          
            } else {
                ngOptions = "value.key as value.description for value in metadata.values";
                ngModel="model." + name;
            }

            var html = ' <label for="' + id + '" class="' + cssClassLabelCol + '  ' + cssClassControlLabel + '">' + label + '</label>\n' +
                    '    <div class="' + cssClassDivInputCol + '">\n' +
                    '        <select id="' + id + '" ng-model="' + ngModel + '" name="' + name + '" ' + attributes + ' ng-options="' + ngOptions + '" >\n' +
                    '           <option value="">--- Elige opci&oacute;n ---</option>' +
                    '        </select>\n' +
                    '    </div>\n';
            return html;
        }

        function getHTMLScalar(name, metadata) {
            var cssClassLabelCol;
            var cssClassControlLabel;
            var cssClassDivInputCol;
            if (bootstrap.version >= 3) {
                cssClassLabelCol = "col-sm-3";
                cssClassControlLabel = "control-label";
                cssClassDivInputCol = "col-sm-9";
            } else {
                cssClassLabelCol = "control-label";
                cssClassControlLabel = "";
                cssClassDivInputCol = "controls";
            }

            var attributes;
            switch (metadata.type) {
                case "STRING":
                    attributes = '';
                    break;
                case "INTEGER":
                    attributes = ' ix3-integer ';
                    break;
                case "NUMBER":
                    attributes = ' ix3-number ';
                    break;
                case "DATE":
                    attributes = ' ix3-date ix3-datepicker ';
                    break;
                default:
                    throw Error("El tipo de datos no es valido para un escalar:" + metadata.type);
            }

            attributes = attributes + getValidationAttributes(metadata);

            var id = "field." + name + "." + (new Date()).getTime();
            var label = metadata.label.charAt(0).toUpperCase() + metadata.label.slice(1);

            var html = '<label for="' + id + '" class="' + cssClassLabelCol + '  ' + cssClassControlLabel + '">' + label + '</label>\n' +
                    '    <div class="' + cssClassDivInputCol + '">\n' +
                    '        <input type="text" id="' + id + '" ng-model="model.' + name + '" name="' + name + '" ' + attributes + ' ></input>\n' +
                    '    </div>\n';
            return html;
        }

        function getHTMLBoolean(name, metadata) {
            var cssClassDiv;
            if (bootstrap.version >= 3) {
                cssClassDiv = "col-sm-offset-3 col-sm-9";

            } else {
                cssClassDiv = "controls";
            }

            var attributes = getValidationAttributes(metadata);

            var id = "field." + name + "." + (new Date()).getTime();
            var label = metadata.label.charAt(0).toUpperCase() + metadata.label.slice(1);

            var html = '<div class="' + cssClassDiv + '">' +
                    '<div class="checkbox">' +
                    '<label style="white-space:nowrap;height:30px"><input type="checkbox" id="' + id + '" ng-model="model.' + name + '" name="' + name + '" ' + attributes + ' >' + label + '</label>' +
                    '</div>' +
                    '</div>';


            return html;
        }

        function getHTMLSearch(name, metadata) {
            var cssClassLabelCol;
            var cssClassControlLabel;
            var cssClassDivInputCol;
            if (bootstrap.version >= 3) {
                cssClassLabelCol = "col-sm-3";
                cssClassControlLabel = "control-label";
                cssClassDivInputCol = "col-sm-9";
            } else {
                cssClassLabelCol = "control-label";
                cssClassControlLabel = "";
                cssClassDivInputCol = "controls";
            }

            var attributes;
            attributes = attributes + getValidationAttributes(metadata);

            var id = "field." + name + "." + (new Date()).getTime();
            var label = metadata.label.charAt(0).toUpperCase() + metadata.label.slice(1);

            var html = '<label for="' + id + '" class="' + cssClassLabelCol + '  ' + cssClassControlLabel + '">' + label + '</label>\n' +
                    '    <div class="' + cssClassDivInputCol + '">\n' +
                    '        <input type="text" id="' + id + '" ng-model="model.' + name + '" name="' + name + '" ' + attributes + ' ></input>\n' +
                    '    </div>\n';
            return html;
        }



        function getHTML(name, metadata) {
            var html;

            if ((hasValues(metadata.values) === true) || (metadata.dependProperties.length > 0) || (metadata.urlValues !== null)) {
                html = getHTMLSelect(name, metadata);
            } else {
                switch (metadata.type) {
                    case "OBJECT":
                        html = getHTMLSearch(name, metadata);
                        break;
                    case "STRING":
                        html = getHTMLScalar(name, metadata);
                        break;
                    case "INTEGER":
                        html = getHTMLScalar(name, metadata);
                        break;
                    case "NUMBER":
                        html = getHTMLScalar(name, metadata);
                        break;
                    case "BOOLEAN":
                        html = getHTMLBoolean(name, metadata);
                        break;
                    case "DATE":
                        html = getHTMLScalar(name, metadata);
                        break;
                    default:
                        throw Error("El tipo de datos no es valido:" + metadata.type);
                }
            }

            return html;
        }

        function getMetadataProperty(obj, key) {
            var keys = key.split('.');
            for (var i = 0; i < keys.length; i++) {
                if (!obj.properties[keys[i]]) {
                    throw Error("No xiste el objeto:" + key);
                }
                obj = obj.properties[keys[i]];

            }
            return obj;
        }
        function setValue(obj, key, newValue) {
            var keys = key.split('.');
            for (var i = 0; i < keys.length - 1; i++) {
                if (!obj[keys[i]]) {
                    obj[keys[i]] = {};
                }
                obj = obj[keys[i]];

            }
            obj[keys[keys.length - 1]] = newValue;
        }

        return {
            restrict: 'E',
            template: '<div  ng-class="{\'form-group\':bootstrap.version===3,\'control-group\':bootstrap.version===2}"></div>',
            replace: true,
            scope: {
                name: "@model"
            },
            link: function($scope, element, attributes) {
                $scope.bootstrap = bootstrap;
                $scope.entity = $scope.$parent.entity;
                $scope.model = {};
                setValue($scope.model, $scope.name, null);

                var watchMetadata = "$parent.metadata." + $scope.entity;

                $scope.$watch(watchMetadata, function(newValue, oldValue) {
                    if (newValue === oldValue) {
                        return;
                    }
                    var metadata = newValue;
                    if (typeof (metadata) !== "object") {
                        return;
                    }
                    $scope.metadata = getMetadataProperty(metadata, $scope.name);
                    element.html(getHTML($scope.name, $scope.metadata));
                    $compile(element)($scope);

                    var watchInputProperty;
                    var watchParentProperty;
                    if (metadata.type === "OBJECT") {
                        watchInputProperty="model." + $scope.name+"."+metadata.primaryKeyPropertyName;
                        watchParentProperty="$parent.model." + $scope.name+"."+metadata.primaryKeyPropertyName;;
                    } else {
                        watchInputProperty="model." + $scope.name;
                        watchParentProperty="$parent.model." + $scope.name;
                    }
                    
                    $scope.$watch(watchInputProperty, function(newValue, oldValue) {
                        if (newValue === oldValue) {
                            return;
                        }
                        setValue($scope, watchParentProperty, newValue);
                    });
                    $scope.$watch(watchParentProperty, function(newValue, oldValue) {
                        if (newValue === oldValue) {
                            return;
                        }
                        setValue($scope, watchInputProperty, newValue);
                    });


                });



            }
        };
    }]);
