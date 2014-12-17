(function (undefined) {
    "use strict";

    /**
     * Enriquece objetos de dominio con nuevos métodos y transformaciones
     */
    function RichDomain() {
        var that = this;
        this.transformers = {
            entity: {},
            global: []
        }


        this.addEntityTransformer = function (entityName, transformer) {
            if (!this.transformers.entity[entityName]) {
                this.transformers.entity[entityName] = [];
            }

            this.transformers.entity[entityName].push(transformer);
        }

        this.addGlobalTransformer = function (transformer) {
            this.transformers.global.push(transformer);
        }


        this.extend = function (object) {
            transform(object, this.transformers);
        };


        function transform(object, transformers) {
            //OJO:Comporbar primeri si es un array pq un array tambien es un objeto
             if (angular.isArray(object)) {
                for (var i = 0; i < object.length; i++) {
                    transform(object[i], transformers);
                }

            } else if ((typeof (object) === "object") && (object!==null)) {
                for (var key in object) {
                    if (!object.hasOwnProperty(key)) {
                        continue;
                    }
                    var value = object[key];
                    if (typeof (value) === "object") {
                        transform(value, transformers);
                    }
                }
                applyTransforms(object, transformers);
            }

        }

        function applyTransforms(object, transformers) {
            var className = object['$className'];

            //aplicamos los transformadores globales
            for (var i = 0; i < transformers.global.length; i++) {
                transformers.global[i](className, object);
            }

            if (className) {
                var entityTransformers = transformers.entity[className];
                if (entityTransformers) {

                    for (var i = 0; i < transformers.entity[className].length; i++) {
                        transformers.entity[className][i](className, object);
                    }
                }
            }
        }


    }

    angular.module("es.logongas.ix3").service("richDomain", RichDomain);

})();


