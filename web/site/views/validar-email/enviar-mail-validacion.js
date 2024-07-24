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
(function (undefined) {
    "use strict";

    EnviarMailValidacionController.$inject = ['$scope', 'serviceFactory', 'formValidator','ix3Configuration'];
    function EnviarMailValidacionController($scope, serviceFactory, formValidator, ix3Configuration) {
        $scope.businessMessages = [];
        $scope.ix3Configuration=ix3Configuration;
        var usuarioService = serviceFactory.getService("Usuario");
        var captchaService = serviceFactory.getService("Captcha");


        $scope.enviarMailValidarEMail = function () {
            $scope.businessMessages = formValidator.validate($scope.mainForm, $scope.$validators);
            if ($scope.businessMessages.length === 0) {
                usuarioService.enviarMailValidarEMail($scope.userEmail, $scope.captchaWord, $scope.keyCaptcha).then(function () {
                    $scope.userEmail="";
                    $scope.loadKeyCaptcha();
                    
                    sweetAlert({
                        title: "Te acabamos de enviar el correo.",
                        text: "Ten en cuenta que puede tardar hasta 30 minutos en llegar el correo o puede que llegue a tu carpeta de spam",
                        type: 'success',
                        confirmButtonText: 'Aceptar',
                        confirmButtonColor: '#005594'
                    }, function () {
                    });                     
                    
                    
                }, function (businessMessages) {
                    $scope.loadKeyCaptcha();
                    $scope.businessMessages = businessMessages;
                });
            }
        };
        
        $scope.loadKeyCaptcha=function() {
            captchaService.getCaptcha().then(function (captcha) {
                $scope.keyCaptcha=captcha.keyCaptcha;
                $scope.captchaWord="";
            }, function (businessMessages) {
                $scope.businessMessages = businessMessages;
            });            
        }
        
        
        $scope.loadKeyCaptcha();
        
    }

    angular.module("common").controller("EnviarMailValidacionController", EnviarMailValidacionController);



})();