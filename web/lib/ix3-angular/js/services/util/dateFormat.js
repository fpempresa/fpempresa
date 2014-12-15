(function () {
    "use strict";

    function DateFormatService($locale,defaultDateFormat) {
        var _defaultDateFormat=defaultDateFormat;
        var _$locale=$locale;

        /**
         * Obtiene el formato de fecha de Angular correspondiente a un "localizable format"
         * @param {String} angularjsFormat Un formato de fecha de AngularJS
         * @returns {String} Si es un "localizable format" retorna es Spring correspondiente , sino retorna el mismo String
         */
        this.getAngularFormatFromPredefined = function (angularjsFormat) {
            var newFormat;
            if (angularjsFormat === "fullDate") {
                newFormat = _$locale.DATETIME_FORMATS.fullDate;
            } else if (angularjsFormat === "longDate") {
                newFormat = _$locale.DATETIME_FORMATS.longDate;
            } else if (angularjsFormat === "medium") {
                newFormat = _$locale.DATETIME_FORMATS.medium;
            } else if (angularjsFormat === "mediumDate") {
                newFormat = _$locale.DATETIME_FORMATS.mediumDate;
            } else if (angularjsFormat === "mediumTime") {
                newFormat = _$locale.DATETIME_FORMATS.mediumTime;
            } else if (angularjsFormat === "short") {
                newFormat = _$locale.DATETIME_FORMATS.short;
            } else if (angularjsFormat === "shortDate") {
                newFormat = _$locale.DATETIME_FORMATS.shortDate;
            } else if (angularjsFormat === "shortTime") {
                newFormat = _$locale.DATETIME_FORMATS.shortTime;
            } else {
                newFormat = angularjsFormat;
            }

            return newFormat;
        };

        /**
         * Tranforma un formato de fecha de AngularJS en un formato de fecha de moment.js
         * @param {String} angularjsFormat El formato de fecha de AngularJS que NO sea un "localizable format"
         * @returns {String} Formato de fecha de moment.js
         */
        this.getMomentFormatFromAngularJSFormat = function (angularjsFormat) {
            var format = angularjsFormat;
            var inLiteral = false;
            var newFormat = "";
            var c;
            var newc;
            var prevY;
            var nextY;
            for (var i = 0; i < format.length; i++) {
                c = format.charAt(i);
                if ((c === "'") && (inLiteral === false)) {
                    newc = "[";
                    inLiteral = true;
                } else if ((c === "'") && (inLiteral === true)) {
                    newc = "]";
                    inLiteral = false;
                } else if (inLiteral === true) {
                    newc = c;
                } else if (c === "d") {
                    newc = "D";
                } else if (c === "a") {
                    newc = "A";
                } else if (c === "y") {
                    if (i === 0) {
                        prevY = false;
                    } else {
                        prevY = (format.charAt(i - 1) === "y");
                    }

                    if (i === (format.length - 1)) {
                        nextY = false;
                    } else {
                        nextY = (format.charAt(i + 1) === "y");
                    }

                    if ((prevY === false) && (nextY === false)) {
                        newc = "YYYY";
                    } else {
                        newc = "Y";
                    }


                } else if (c === "E") {
                    newc = "d";
                } else if (c === "Z") {
                    newc = "ZZ";
                } else {
                    newc = c;
                }

                newFormat = newFormat + newc;
            }

            return newFormat.replace(/sss/g, "SSS");
        };

        /**
         * Tranforma un formato de fecha de AngularJS en un formato de fecha de jQuery UI Datepicker
         * @param {String} angularjsFormat El formato de fecha de AngularJS que NO sea un "localizable format"
         * @returns {String} Formato de fecha de jQuery UI Datepicker
         */
        this.getJQueryDatepickerFormatFromAngularJSFormat = function (angularjsFormat) {
            var newFormat = angularjsFormat;
            newFormat = newFormat.replace(/([^M]|^)(M)([^M]|$)/g, function (match, p1, p2, p3, offset, string) {
                return p1 + "m" + p3;
            });
            newFormat = newFormat.replace(/([^M]|^)(MM)([^M]|$)/g, function (match, p1, p2, p3, offset, string) {
                return p1 + "mm" + p3;
            });
            newFormat = newFormat.replace(/([^M]|^)(MMM)([^M]|$)/g, function (match, p1, p2, p3, offset, string) {
                return p1 + "M" + p3;
            });
            newFormat = newFormat.replace(/([^M]|^)(MMMM)([^M]|$)/g, function (match, p1, p2, p3, offset, string) {
                return p1 + "MM" + p3;
            });
            newFormat = newFormat.replace(/([^y]|^)(yy)([^y]|$)/g, function (match, p1, p2, p3, offset, string) {
                return p1 + "@#@#" + p3;
            });
            newFormat = newFormat.replace(/([^y]|^)(yyyy)([^y]|$)/g, function (match, p1, p2, p3, offset, string) {
                return p1 + "yy" + p3;
            });
            newFormat = newFormat.replace(/([^y]|^)(y)([^y]|$)/g, function (match, p1, p2, p3, offset, string) {
                return p1 + "yy" + p3;
            });
            newFormat = newFormat.replace(/@#@#/g, "y");

            return newFormat;

        };

        this.getDefaultDateFormat = function () {
            return _defaultDateFormat;
        };

    }

    function DateFormatProvider() {
        this._defaultDateFormat="shortDate";
        this.setDefaultDateFormat=function(defaultDateFormat) {
            this._defaultDateFormat=defaultDateFormat;
        }
        this.$get=['$locale',function($locale) {
            return new  DateFormatService($locale,this._defaultDateFormat);
        }];

    }



    angular.module("es.logongas.ix3").provider("dateFormat",DateFormatProvider);

})();




