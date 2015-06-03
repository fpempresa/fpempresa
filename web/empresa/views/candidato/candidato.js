"use strict";

CandidatoViewController.$inject = ['$scope', 'genericControllerCrudDetail', 'currentDialog', 'controllerParams', 'ix3Configuration', 'ageCalculator'];
function CandidatoViewController($scope, genericControllerCrudDetail, currentDialog, controllerParams, ix3Configuration, ageCalculator) {
    genericControllerCrudDetail.extendScope($scope, controllerParams);
    $scope.ix3Configuration=ix3Configuration;
    $scope.ageCalculator=ageCalculator;
    currentDialog.open({
        width: 1000,
        height: 700,
        title: "Curriculum"
    });

    

    $scope.buttonCancel = function () {
        currentDialog.closeCancel();
    };

    $scope.buttonRechazar = function () {
        $scope.model.rechazado=true;
        
        $scope.doUpdate().then(function() {
            currentDialog.closeOK();
        });
        
    };
    $scope.buttonAceptar = function () {
        $scope.model.rechazado=false;
        
        $scope.doUpdate().then(function() {
            currentDialog.closeOK();
        });
        
    };    
    
}
angular.module("common").controller("CandidatoViewController", CandidatoViewController);


angular.module("common").config(['dialogProvider', 'getContextPath', 'crudRoutesProvider', function (dialogProvider, getContextPath, crudRoutesProvider) {

        dialogProvider.when('viewCandidato', {
            templateUrl: getContextPath() + "/empresa/views/candidato/candidato.html",
            controller: 'CandidatoViewController',
            resolve: crudRoutesProvider.getResolve('Candidato', 'usuario.titulado.titulosIdiomas,usuario.titulado.experienciasLaborales,usuario.titulado.formacionesAcademicas.centro,usuario.titulado.direccion.municipio.provincia','VIEW')
        });

    }]);
