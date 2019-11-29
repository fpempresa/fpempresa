"use strict";

app.controller("TituladoSearchController", ['$scope', '$http', 'ix3Configuration','dialog', function ($scope, $http, ix3Configuration,dialog) {
        $scope.businessMessages = [];
        
        $scope.fail = {};
        $scope.apiUrl = ix3Configuration.server.api;
        $scope.mostrarCodigosMunicipio = function () {
            dialog.create('mostrarCodigosMunicipio');
        };
        
        $scope.mostrarCartelRGPD=function() {
            var date=new Date('2019-01-26'); //Es la fecha en la que se quito lo de ver los datos de los usuarios
            
            if ($scope.user.fecha>=date) {
                return false;
            } else {
                return true;
            }
        };
        
        $scope.failImportJson = function (data) {
            if (data && data.jqXHR) {
                if (data.jqXHR.status === 500) {
                    alert("Uf, esto es embarazoso pero \n ha ocurrido un error al procesar la petición:\n"+data.jqXHR.responseText);
                } else {
                    if (data.jqXHR.responseText) {
                        $scope.businessMessages = JSON.parse(data.jqXHR.responseText);
                    }
                }
            }
        };
        $scope.updateList = function () {
            alert("El listado de Titulados se importó correctamente");
            $scope.search();
        };
        
        $scope.anyo=null;
        $scope.anyos=[];
        var currentYear=(new Date()).getFullYear();
        for(var anyo=currentYear;anyo>=1980;anyo--) {
            $scope.anyos.push(anyo);
        }        
        
        
        if ($scope.user && $scope.user.centro) {
            $scope.centro=$scope.user.centro;
            getEstadisticasByCentro($scope,$scope.user.centro,$scope.anyo,$scope.anyo);
        }
        

        
        function getEstadisticasByCentro($scope,centro,anyoInicio,anyoFin) {
            $http({
                method: "GET",
                url: ix3Configuration.server.api + "/Estadisticas/centro/" + centro.idCentro + "?expand=titulosFPPorFamilia.tituladosPorCiclo&anyoInicio=" + (anyoInicio==null?'':anyoInicio) +"&anyoFin=" + (anyoFin==null?'':anyoFin)
            }).then(function (estadisticas) {
                $scope.centro.estadisticas = estadisticas.data;
            });
        } 
        
        $scope.buttonSearch=function() {
            getEstadisticasByCentro($scope,$scope.user.centro,$scope.anyo,$scope.anyo);
        }
        
    }]);
