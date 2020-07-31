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
        
    $stateProvider.state("lateralmenu.ultimoacceso", {
        url: '/usuario/ultimoacceso',
        templateUrl: 'views/usuario/ultimoacceso.html',
        controller: 'UsuarioUltimoAccesoSearchController',
        resolve: crudRoutesProvider.getResolve("Usuario")
    });

               
        
    }]);


app.controller("UsuarioUltimoAccesoSearchController", ['$scope', 'genericControllerCrudList', 'controllerParams', 'schemaEntities','serviceFactory','notify', function ($scope, genericControllerCrudList, controllerParams, schemaEntities,serviceFactory,notify) {
        genericControllerCrudList.extendScope($scope, controllerParams);
        $scope.page.pageSize = 500;
        $scope.procesando=null;
        var serviceCiclo = serviceFactory.getService("Usuario");


        $scope.buttonNotificarUsuarioInactivo=function(usuario) {
            serviceCiclo.notificarUsuarioInactivo(usuario.idIdentity).then(function () {
                notify.info("Notificado","Se han notificado al usuario");
                $scope.search();
            }, function (businessMessages) {
                $scope.businessMessages = businessMessages;
            });
        };
        
        $scope.buttonSoftDelete=function(usuario) {
            serviceCiclo.softDelete(usuario.idIdentity).then(function () {
                notify.info("Soft Delete","Se ha borrado el usuario");
                $scope.search();
            }, function (businessMessages) {
                $scope.businessMessages = businessMessages;
            });
        };             
        
        
        $scope.buttonNotificarUsuarioInactivoAllPage=function() {
            $scope.notificarUsuarioInactivoAllPage($scope.models,0);
        };        
        
        
        $scope.notificarUsuarioInactivoAllPage = function (usuarios,index) {

            if (!(usuarios && usuarios.length>index)) {
                $scope.procesando=null;
                notify.info("Notificado","Se han notificado " + usuarios.length + " usuarios");
                $scope.search();
                return;
            }
            
            var usuario=usuarios[index];

            $scope.procesando="Notificando al usuario " + (index+1) + " de " + usuarios.length;
            serviceCiclo.notificarUsuarioInactivo(usuario.idIdentity).then(function () {
                $scope.notificarUsuarioInactivoAllPage(usuarios,index+1);
            }, function (businessMessages) {
                $scope.procesando=null;
                $scope.businessMessages = businessMessages;
            });
        }
        
        
        $scope.buttonSoftDeleteAllPage=function() {
            $scope.softDeleteAllPage($scope.models,0);
        };
        
        
        $scope.softDeleteAllPage = function (usuarios,index) {

            if (!(usuarios && usuarios.length>index)) {
                $scope.procesando=null;
                notify.info("Soft Delete","Se han borrado " + usuarios.length + " usuarios");
                $scope.search();
                return;
            }
            
            var usuario=usuarios[index];

            $scope.procesando="Soft Delete del usuario " + (index+1) + " de " + usuarios.length;
            serviceCiclo.softDelete(usuario.idIdentity).then(function () {
                $scope.softDeleteAllPage(usuarios,index+1);
            }, function (businessMessages) {
                $scope.procesando=null;
                $scope.businessMessages = businessMessages;
            });
        }
        
        
        $scope.preSearch = function (filters) {
            
            $scope.procesando="Por favor espere.Obteniendo datos......";
            
            if ($scope.tipoBusqueda==="USUARIOS_NOTIFICAR") {
               
                filters['$eq']={};
                filters['$eq'].tipoUsuario="TITULADO";
               
                filters['$isnull']={};
                filters['$isnull'].fechaEnvioCorreoAvisoBorrarUsuario=true;
                

                filters['$dle']={};
                filters['$dle'].fechaUltimoAcceso=moment().subtract(1, 'y').toDate();
        
            } else if ($scope.tipoBusqueda==="USUARIOS_BORRAR") {
               
                filters['$eq']={};
                filters['$eq'].tipoUsuario="TITULADO";
               
                filters['$isnull']={};

                filters['$dle']={};
                filters['$dle'].fechaEnvioCorreoAvisoBorrarUsuario=moment().subtract(15, 'd').toDate();
               
            } else {
                throw Error("El tipo de búsqueda no es válido");
            }
            
        }
        
        $scope.postSearch = function (models) {
            $scope.procesando=null;
        }
        
        $scope.$watch("tipoBusqueda", function (newTipoBusqueda, oldTipoBusqueda) {

            if (newTipoBusqueda === oldTipoBusqueda) {
                return;
            }
            $scope.page.totalPages = 0;
            $scope.page.pageNumber=0;
            $scope.models=[];
        });

        $scope.tipoBusqueda="USUARIOS_NOTIFICAR";           
        
    }]);