"use strict";
app.config(['crudRoutesProvider', function(crudRoutesProvider) {
        crudRoutesProvider.addAllRoutes("FormacionAcademica");
    }]);

app.controller("FormacionAcademicaSearchController", ['$scope', 'crud','$filter', function($scope, crud,$filter) {
        crud.extendScope($scope);
        
        $scope.getTipoTitulo = function(formacionAcademica) {
            if (!$scope.metadata.FormacionAcademica) {
                return "";
            }
            var values = $filter('filter')($scope.metadata.FormacionAcademica.properties.tipoFormacionAcademica.values,{key:formacionAcademica.tipoFormacionAcademica});
            return values[0].description;
        };        

        $scope.search();

    }]);


app.controller("FormacionAcademicaNewEditController", ['$scope', 'crud', '$location', function($scope, crud, $location) {
        crud.extendScope($scope);
    }]);

app.controller("FormacionAcademicaViewController", ['$scope', 'crud', '$location', function($scope, crud, $location) {
        crud.extendScope($scope);
    }]);

app.controller("FormacionAcademicaDeleteController", ['$scope', 'crud', '$location', function($scope, crud, $location) {
        crud.extendScope($scope);
    }]);