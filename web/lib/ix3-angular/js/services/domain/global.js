"use strict";

angular.module('es.logongas.ix3').run(['richDomain', function (richDomain) {

        //Para transformar los Strign en fechas
        richDomain.addGlobalTransformer(function (className, object) {
            for (var key in object) {
                if (!object.hasOwnProperty(key)) {
                    continue;
                }
                var value = object[key];
                if ((typeof value === "string") && (value.length === 28)) {
                    var date = moment(value, "YYYY-MM-DDTHH:mm:ss.SSSZZ", true);
                    if (date.isValid()) {
                        object[key] = date.toDate();
                    }
                }
            }
        });

        function toStringGlobal() {
            return this["$toString"];
        }

        
        richDomain.addGlobalTransformer(function (className, object) {

            //Añadir el método toString() para que use la propiedad "$toString", pero solo si existe
            if (typeof (object['$toString']) === "string") {
                //Definimos nuestra propia función "toString"
                object['toString'] = toStringGlobal;
            }

        });

    }]);