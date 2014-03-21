"use strict";
app.config(['crudProvider', function(crudProvider) {
        crudProvider.addAllRoutes("FormacionAcademica");
    }]);

app.controller("FormacionAcademicaSearchController", ['$scope', 'crudState','$filter', function($scope, crudState,$filter) {
        crudState.extendsScopeController($scope, {
        });
        
        $scope.getTipoTitulo = function(formacionAcademica) {
            if (!$scope.metadata.FormacionAcademica) {
                return "";
            }
            var values = $filter('filter')($scope.metadata.FormacionAcademica.properties.tipoFormacionAcademica.values,{key:formacionAcademica.tipoFormacionAcademica});
            return values[0].description;
        };        
        $scope.getNombreCentro = function(formacionAcademica) {
            if (formacionAcademica.tipoFormacionAcademica === "CICLO_FORMATIVO") {
                if (formacionAcademica.centro.idCentro < 0) {
                    return formacionAcademica.otroCentro;
                } else {
                    return formacionAcademica.centro.toString;
                }
            } else {
                return formacionAcademica.otroCentro;
            }
        };
        $scope.getNombreTitulo = function(formacionAcademica) {
            if (formacionAcademica.tipoFormacionAcademica === "CICLO_FORMATIVO") {
                return formacionAcademica.ciclo.toString;
            } else {
                return formacionAcademica.otroTitulo;
            }
        };
        $scope.search();

    }]);


app.controller("FormacionAcademicaNewEditController", ['$scope', 'crudState', '$location', function($scope, crudState, $location) {
        crudState.extendsScopeController($scope, {
        });
    }]);

app.controller("FormacionAcademicaViewController", ['$scope', 'crudState', '$location', function($scope, crudState, $location) {
        crudState.extendsScopeController($scope, {
        });


    }]);
app.controller("FormacionAcademicaDeleteController", ['$scope', 'crudState', '$location', function($scope, crudState, $location) {
        crudState.extendsScopeController($scope, {
        });


    }]);