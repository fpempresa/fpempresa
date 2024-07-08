/**
 *   FPempresa
 *   Copyright (C) 2020  Lorenzo González
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Affero General Public License as
 *   published by the Free Software Foundation, either version 3 of the
 *   License, or (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Affero General Public License for more details.
 *
 *   You should have received a copy of the GNU Affero General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
app.config(['$stateProvider', 'crudRoutesProvider', function ($stateProvider, crudRoutesProvider) {
        $stateProvider.state('lateralmenu.main', {
            url: "/",
            templateUrl: 'views/main/main.html',
            controller: 'MainController',
            resolve: crudRoutesProvider.getResolve("FormacionAcademica", "")
        });
    }]);


MainController.$inject = ['$scope', '$http', 'ix3Configuration','genericControllerCrudList', 'controllerParams','$timeout'];
function MainController($scope, $http, ix3Configuration, genericControllerCrudList, controllerParams, $timeout) {
    genericControllerCrudList.extendScope($scope, controllerParams);
    $scope.businessMessages = [];
    $scope.groupByEstadisticaValues=[
        {
            codigo:"Ninguna",
            descripcion:"Sin agrupar"
        },
        {
            codigo:"Ubicacion",
            descripcion:"Ubicación geográfica"
        },
        {
            codigo:"CatalogoAcademico",
            descripcion:"Catalogo academico"
        },
        {
            codigo:"Anyos",
            descripcion:"Anualmente"
        }       
    ]
    
    var ahora = moment();
    var hace6meses = ahora.subtract(6, 'months');
    
    $scope.filters['fechaDesde']=hace6meses.toDate();
    $scope.filters['fechaHasta']=new Date();
    $scope.filters['groupByEstadistica']={
            codigo:"Ubicacion",
            descripcion:"Ubicación geográfica"
        };

    

    function loadGraficos(nombreEstadistica, groupByEstadistica, filterDesde, filterHasta, filterComunidadAutonoma, filterProvincia, filterFamilia, filterCiclo) {
        return $http({
            method: "GET",
            url: ix3Configuration.server.api + "/Estadisticas/administrador",
            params: {
                nombreEstadistica:nombreEstadistica,
                groupByEstadistica: groupByEstadistica,
                desde:filterDesde,
                hasta:filterHasta, 
                comunidadAutonoma:filterComunidadAutonoma, 
                provincia:filterProvincia, 
                familia:filterFamilia, 
                ciclo:filterCiclo
            }
        });
    }


    function loadDatosGraficaOfertas(groupByEstadistica,filterDesde, filterHasta, filterComunidadAutonoma, filterProvincia, filterFamilia, filterCiclo) {
        var promise=loadGraficos("Ofertas", groupByEstadistica, filterDesde, filterHasta, filterComunidadAutonoma, filterProvincia, filterFamilia, filterCiclo);
        
        promise.then(function (chartData) {
            $scope.chartOfertas=chartData.data;
            $scope.chartOfertas.sumDataValues=sumDataValues($scope.chartOfertas.dataValues);
            
        }, function (businessMessages) {
            $scope.businessMessages = businessMessages;
        });
    }
    
    
    function loadDatosGraficaCandidatos(groupByEstadistica,filterDesde, filterHasta, filterComunidadAutonoma, filterProvincia, filterFamilia, filterCiclo) {
        var promise=loadGraficos("Candidatos", groupByEstadistica, filterDesde, filterHasta, filterComunidadAutonoma, filterProvincia, filterFamilia, filterCiclo);
        
        promise.then(function (chartData) {
            $scope.chartCandidatos=chartData.data;
            $scope.chartCandidatos.sumDataValues=sumDataValues($scope.chartCandidatos.dataValues);
            
        }, function (businessMessages) {
            $scope.businessMessages = businessMessages;
        });
    }  
    
    function loadDatosGraficaEmpresas(groupByEstadistica,filterDesde, filterHasta, filterComunidadAutonoma, filterProvincia, filterFamilia, filterCiclo) {
        var promise=loadGraficos("Empresas", groupByEstadistica, filterDesde, filterHasta, filterComunidadAutonoma, filterProvincia, filterFamilia, filterCiclo);
        
        promise.then(function (chartData) {
            $scope.chartEmpresas=chartData.data;
            $scope.chartEmpresas.sumDataValues=sumDataValues($scope.chartEmpresas.dataValues);
            
        }, function (businessMessages) {
            $scope.businessMessages = businessMessages;
        });
    }  
    function loadDatosGraficaCentros(groupByEstadistica,filterDesde, filterHasta, filterComunidadAutonoma, filterProvincia, filterFamilia, filterCiclo) {
        var promise=loadGraficos("Centros", groupByEstadistica, filterDesde, filterHasta, filterComunidadAutonoma, filterProvincia, filterFamilia, filterCiclo);
        
        promise.then(function (chartData) {
            $scope.chartCentros=chartData.data;
            $scope.chartCentros.sumDataValues=sumDataValues($scope.chartCentros.dataValues);
            
        }, function (businessMessages) {
            $scope.businessMessages = businessMessages;
        });
    }         
    function loadDatosGraficaTitulados(groupByEstadistica,filterDesde, filterHasta, filterComunidadAutonoma, filterProvincia, filterFamilia, filterCiclo) {
        var promise=loadGraficos("Titulados", groupByEstadistica, filterDesde, filterHasta, filterComunidadAutonoma, filterProvincia, filterFamilia, filterCiclo);
        
        promise.then(function (chartData) {
            $scope.chartTitulados=chartData.data;
            $scope.chartTitulados.sumDataValues=sumDataValues($scope.chartTitulados.dataValues);
            
        }, function (businessMessages) {
            $scope.businessMessages = businessMessages;
        });
    } 

    function sumDataValues(dataValues) {
        var sumValue=0;
        for(var i=0;i<dataValues.length;i++) {
            sumValue=sumValue+dataValues[i].value;
        }            
            
        return sumValue;
    }

    $scope.buttonMostrar=function() {
        var groupByEstadistica;
        var filterDesde="";
        var filterHasta="";
        var filterComunidadAutonoma="";
        var filterProvincia="";
        var filterFamilia="";
        var filterCiclo="";

        groupByEstadistica=$scope.filters['groupByEstadistica'].codigo;      
        if ($scope.filters['fechaDesde']) {
            filterDesde=$scope.filters['fechaDesde'].toISOString();
        }
        if ($scope.filters['fechaHasta']) {
            filterHasta=$scope.filters['fechaHasta'].toISOString();
        } 
        if ($scope.filters['comunidadAutonoma']) {
            filterComunidadAutonoma=$scope.filters['comunidadAutonoma'].idComunidadAutonoma;
        }         
        if ($scope.filters['provincia']) {
            filterProvincia=$scope.filters['provincia'].idProvincia;
        } 
        if ($scope.filters['familia']) {
            filterFamilia=$scope.filters['familia'].idFamilia;
        } 
        if ($scope.filters['ciclo']) {
            filterCiclo=$scope.filters['ciclo'].idCiclo;
        }         


        loadDatosGraficaOfertas(groupByEstadistica, filterDesde, filterHasta, filterComunidadAutonoma, filterProvincia, filterFamilia, filterCiclo);
        loadDatosGraficaCandidatos(groupByEstadistica, filterDesde, filterHasta, filterComunidadAutonoma, filterProvincia, filterFamilia, filterCiclo);
        loadDatosGraficaEmpresas(groupByEstadistica, filterDesde, filterHasta, filterComunidadAutonoma, filterProvincia, filterFamilia, filterCiclo);
        loadDatosGraficaCentros(groupByEstadistica, filterDesde, filterHasta, filterComunidadAutonoma, filterProvincia, filterFamilia, filterCiclo);
        loadDatosGraficaTitulados(groupByEstadistica, filterDesde, filterHasta, filterComunidadAutonoma, filterProvincia, filterFamilia, filterCiclo);

    };
    
    
    $timeout(function() {
        $scope.buttonMostrar();
    }, 0);

}
app.controller("MainController", MainController);