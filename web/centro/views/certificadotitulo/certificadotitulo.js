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
"use strict";
app.config(['$stateProvider', function ($stateProvider) {


        $stateProvider.state('lateralmenu.certificadotitulo_anyos', {
            url: "/certificado/anyo",
            templateUrl: 'views/certificadotitulo/anyos.html',
            controller: 'CentroCertificadoAnyoController'
        });
        $stateProvider.state('lateralmenu.certificadotitulo_titulados', {
            url: "/certificado/anyo/:anyo/ciclo/:idCiclo",
            templateUrl: 'views/certificadotitulo/titulados.html',
            controller: 'CentroCertificadoTituloController'
        });

    }]);

app.controller("CentroCertificadoAnyoController", ['$scope', 'serviceFactory', '$stateParams', function ($scope, serviceFactory, $stateParams) {
        $scope.serviceCentro = serviceFactory.getService("Centro");
        $scope.businessMessages = null;
        $scope.certificadosAnyos = [];

        $scope.centro = $scope.user.centro;

        $scope.loadCertificadoAnyos = function (idCentro) {
            var promise = $scope.serviceCentro.getCertificadosAnyoCentro(idCentro);

            promise.then(function (data) {
                $scope.certificadosAnyos = data;
            }, function (businessMessages) {
                $scope.businessMessages = businessMessages;
            });
        }

        $scope.showHideCertificadoCiclos = function (anyo) {
            var certificadoAnyo = getCertificadoAnyo($scope.certificadosAnyos, anyo);

            if (certificadoAnyo.certificadoCiclos) {
                certificadoAnyo.show = !certificadoAnyo.show;
            } else {
                $scope.loadCertificadoCiclos($scope.centro.idCentro, anyo)
            }

        }

        $scope.loadCertificadoCiclos = function (idCentro, anyo) {
            var promise = $scope.serviceCentro.getCertificadosCicloCentro(idCentro, anyo);

            promise.then(function (data) {
                var certificadoAnyo = getCertificadoAnyo($scope.certificadosAnyos, anyo);
                certificadoAnyo.certificadoCiclos = data;
                generateBackgroundColorCertificadoCiclos(certificadoAnyo.certificadoCiclos);
                certificadoAnyo.show = true;
            }, function (businessMessages) {
                $scope.businessMessages = businessMessages;
            });
        }

        $scope.loadCertificadoAnyos($scope.centro.idCentro);

        $scope.getColorFromIdFamilia=function(idFamilia) {
            var rgbColor=hslToRgb({
                h:220,
                s:30+(idFamilia*2),
                l:50+idFamilia
            })
            
            return "#"+rgbColor;
        }

        function getCertificadoAnyo(certificadosAnyos, anyo) {
            for (var i = 0; i < certificadosAnyos.length; i++) {
                var certificadosAnyo = certificadosAnyos[i];
                if (certificadosAnyo.anyo === anyo) {
                    return certificadosAnyo;
                }
            }

            return undefined;
        }
        
        function generateBackgroundColorCertificadoCiclos(certificadoCiclos) {
            var lastIdFamilia=-1;
            var lastColor=true;
            
            for (var i = 0; i < certificadoCiclos.length; i++) {
                var certificadoCiclo=certificadoCiclos[i];
                if (lastIdFamilia===-1) {
                    lastIdFamilia=certificadoCiclo.idFamilia;
                }
                
                if (lastIdFamilia!==certificadoCiclo.idFamilia) {
                    lastColor=!lastColor;
                }
                
                if (lastColor) {
                    certificadoCiclo.backgroundColor="#E9E7E7";
                } else {
                    certificadoCiclo.backgroundColor="#FAFAFA";
                }
                
                lastIdFamilia=certificadoCiclo.idFamilia;
                
            }

            return undefined;
        }




    }]);


app.controller("CentroCertificadoTituloController", ['$scope', 'serviceFactory', '$stateParams', function ($scope, serviceFactory, $stateParams) {

        $scope.serviceCentro = serviceFactory.getService("Centro");
        $scope.serviceCiclo = serviceFactory.getService("Ciclo");
        $scope.businessMessages = null;

        $scope.centro = $scope.user.centro;
        $scope.anyo = $stateParams.anyo;
        $scope.idCiclo = $stateParams.idCiclo;
        $scope.ciclo = null;
        
        var promiseCiclo=$scope.serviceCiclo.get($scope.idCiclo);
        promiseCiclo.then(function (data) {
            $scope.ciclo = data;
        }, function (businessMessages) {
            $scope.businessMessages = businessMessages;
        });
        

        $scope.loadCertificadosTituloCentro = function (idCentro, anyo, idCiclo) {
            var promise = $scope.serviceCentro.getCertificadosTituloCentro(idCentro, anyo, idCiclo);

            promise.then(function (data) {
                $scope.models = data;
            }, function (businessMessages) {
                $scope.businessMessages = businessMessages;
            });
        }
        
        $scope.certificarTitulo=function(certificadoTitulo) {
            var promise = $scope.serviceCentro.certificarTituloCentro($scope.centro.idCentro, $scope.anyo, $scope.idCiclo,certificadoTitulo.tipoDocumento,certificadoTitulo.nif,certificadoTitulo.certificadoTitulo);

            promise.then(function () {
                
            }, function (businessMessages) {
                $scope.businessMessages = businessMessages;
            });
        }

        $scope.loadCertificadosTituloCentro($scope.centro.idCentro, $scope.anyo, $scope.idCiclo);

    }]);


function hslToRgb(hslColor) {
    var h = hslColor.h / 360;
    var s = hslColor.s / 100;
    var l = hslColor.l / 100;

    var r, g, b;

    if (s == 0) {
        r = g = b = l;
    } else {
        function hue2rgb(p, q, t) {
            if (t < 0)
                t += 1;
            if (t > 1)
                t -= 1;
            if (t < 1 / 6)
                return p + (q - p) * 6 * t;
            if (t < 1 / 2)
                return q;
            if (t < 2 / 3)
                return p + (q - p) * (2 / 3 - t) * 6;
            return p;
        }

        var q = l < 0.5 ? l * (1 + s) : l + s - l * s;
        var p = 2 * l - q;

        r = hue2rgb(p, q, h + 1 / 3);
        g = hue2rgb(p, q, h);
        b = hue2rgb(p, q, h - 1 / 3);
    }
    
    function componentToHex(c) {
      var hex = c.toString(16);
      return hex.length === 1 ? "0" + hex : hex;
    }

    function rgbToHex(rgbColor) {
        return (componentToHex(rgbColor.r) + componentToHex(rgbColor.g) + componentToHex(rgbColor.b)).toUpperCase();
    }

    var rgbColor={
        r: Math.round(r * 255), 
        g: Math.round(g * 255), 
        b: Math.round(b * 255)
    }

    return rgbToHex(rgbColor)
}

