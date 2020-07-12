"use strict";
app.config(['crudRoutesProvider', function (crudRoutesProvider) {
        crudRoutesProvider.addEditRoute({
            entity: "Empresa",
            expand: "direccion.municipio,direccion.municipio.provincia"
        });
        crudRoutesProvider.addNewRoute({
            entity: "Empresa",
            expand: "direccion.municipio,direccion.municipio.provincia"
        });
    }]);


app.controller("EmpresaNewEditController", ['$scope', '$window', '$location', 'genericControllerCrudDetail', 'controllerParams', 'session', function ($scope, $window, $location, genericControllerCrudDetail, controllerParams, session) {
        genericControllerCrudDetail.extendScope($scope, controllerParams);


        $scope.postCreate = function () {
            $scope.model.contacto={};
            $scope.model.contacto.persona=$scope.user.nombre + " " + $scope.user.apellidos;
            $scope.model.contacto.email=$scope.user.email;
        }

        $scope.finishOK = function (oldControllerAction) {
            if (oldControllerAction === "NEW") {
                $window.location.assign($window.location.pathname);
            } else {
                setTimeout(function () { //http://stackoverflow.com/questions/13853844/angular-js-ie-error-10-digest-iterations-reached-aborting
                    $window.history.back(); 
                }, 0);
            }
        };

    }]);
