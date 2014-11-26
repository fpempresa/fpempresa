
app.controller("LoginController", ['$scope', 'session', 'dialog', '$window', 'goPage', function ($scope, session, dialog, $window, goPage) {
        $scope.usuario = null;
        session.logged().then(function (usuario) {
            $scope.usuario = usuario;
        }, function () {
            $scope.usuario = null;
        });

        $scope.$on("ix3.session.login", function (event, usuario) {
            $scope.usuario = usuario;
        })
        $scope.$on("ix3.session.logout", function (event) {
            $scope.usuario = null;
        })

        $scope.login = function () {
            goPage.login();
        };

        $scope.logout = function () {
            session.logout().then(function () {
                goPage.homeApp();
            });

        };

        $scope.homeUsuario = function () {
            goPage.homeUsuario();
        }

        $scope.createAccount = function () {
            goPage.createAccount();
        }

        $scope.goPageHomeApp = function () {
            goPage.homeApp();
        }
    }])



app.run(['$rootScope', '$location', '$timeout', function ($rootScope, $location, $timeout) {

        $rootScope.$on('$routeChangeSuccess', function (newRoute, oldRoute) {
            if ($location.path() === "/") {
                showHideMenu($timeout);
                $(window).scroll(function () {
                    if ($location.path() === "/") {
                        showHideMenu($timeout);
                    } else {
                        $(".navbar-fixed-top").addClass("top-nav-collapse");
                    }
                });
            } else {
                $(".navbar-fixed-top").addClass("top-nav-collapse");
            }
        });

    }]);



function showHideMenu($timeout) {
    var offset = $(".navbar").offset();

    if (offset) {
        if (offset.top > 80) {
            $(".navbar-fixed-top").addClass("top-nav-collapse");
        } else {
            $(".navbar-fixed-top").removeClass("top-nav-collapse");
        }
    } else {
        $timeout(function () {
            offset = $(".navbar").offset();
            if (offset.top > 80) {
                $(".navbar-fixed-top").addClass("top-nav-collapse");
            } else {
                $(".navbar-fixed-top").removeClass("top-nav-collapse");
            }
        });
    }


}


$('.navbar-collapse ul li a').click(function () {
    $('.navbar-toggle:visible').click();
});

