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

        $scope.finishOK = function (oldControllerAction) {
            if (oldControllerAction === "NEW") {
                $window.location.assign($window.location.pathname);
            } else {
                $window.history.back();
            }
        };

    }]);
