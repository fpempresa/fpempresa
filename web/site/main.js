app.config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/', {
            templateUrl: 'main.html',
            controller: 'MainController'
        });
    }]);


app.controller('MainController', ['$scope', 'goPage', function ($scope, goPage) {
        $scope.createAccount = function (tipoUsuario) {
            goPage.createAccount(tipoUsuario);
        };

        $scope.login = function () {
            goPage.login();
        };

    }]);


app.run(['$rootScope', '$routeParams', '$timeout', function ($rootScope, $routeParams, $timeout) {
        $rootScope.$on('$routeChangeSuccess', function (newRoute, oldRoute) {

            if ($routeParams.scrollTo) {
                var offset = $("#" + $routeParams.scrollTo).offset();

                if (offset) {
                    $('html, body').stop().animate({
                        scrollTop: offset.top
                    }, 1500, 'easeInOutExpo');
                } else {
                    $timeout(function () {
                        $('html, body').stop().animate({
                            scrollTop: $("#" + $routeParams.scrollTo).offset().top
                        }, 1500, 'easeInOutExpo');
                    });
                }
            }

        });


    }]);
