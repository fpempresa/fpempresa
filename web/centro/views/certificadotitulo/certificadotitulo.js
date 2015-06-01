"use strict";
app.config(['crudRoutesProvider', function (crudRoutesProvider) {
        crudRoutesProvider.addAllRoutes({
            entity: "CertificadoTitulo",
            expand: "centro,ciclo"
        });
    }]);

app.controller("CertificadoTituloSearchController", ['$scope', 'genericControllerCrudList', 'controllerParams', function ($scope, genericControllerCrudList, controllerParams) {
        controllerParams.parentProperty = 'centro.idCentro';
        controllerParams.parentId = "" + $scope.user.centro.idCentro;
        genericControllerCrudList.extendScope($scope, controllerParams);
        $scope.page.pageSize = 20;

        $scope.preSearch = function (filters) {
            if (filters['ciclo.familia.idFamilia']) {
                filters['ciclo.familia.idFamilia'] = filters['ciclo.familia.idFamilia'].idFamilia;
            }
            if (filters['ciclo.idCiclo']) {
                filters['ciclo.idCiclo'] = filters['ciclo.idCiclo'].idCiclo;
            }
        }

        $scope.search();
    }]);


app.controller("CertificadoTituloNewEditController", ['$scope', 'genericControllerCrudDetail', 'controllerParams', function ($scope, genericControllerCrudDetail, controllerParams) {
        genericControllerCrudDetail.extendScope($scope, controllerParams);


    }]);

app.controller("CertificadoTituloViewController", ['$scope', 'genericControllerCrudDetail', 'controllerParams', function ($scope, genericControllerCrudDetail, controllerParams) {
        genericControllerCrudDetail.extendScope($scope, controllerParams);


    }]);

app.controller("CertificadoTituloDeleteController", ['$scope', 'genericControllerCrudDetail', 'controllerParams', function ($scope, genericControllerCrudDetail, controllerParams) {
        genericControllerCrudDetail.extendScope($scope, controllerParams);

    }]);