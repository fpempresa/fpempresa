
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
                        showNavBar();
                    }
                });
            } else {
               showNavBar();
            }
        });

    }]);



function showHideMenu($timeout) {
    var offset = $(".navbar").offset();

    if (offset) {
        if (offset.top > 80) {
            showNavBar();
        } else {
            hideNavBar();
        }
    } else {
        $timeout(function () {
            offset = $(".navbar").offset();
            if (offset.top > 80) {
                showNavBar();
            } else {
                hideNavBar();
            }
        });
    }


}

function showNavBar() {
    var element=$(".navbar-fixed-top");
    var cssClass="top-nav-collapse";
    if (!element.hasClass(cssClass)) {
        element.addClass(cssClass);
    }
}

function hideNavBar() {
    var element=$(".navbar-fixed-top");
    var cssClass="top-nav-collapse";
    if (element.hasClass(cssClass)) {
        element.removeClass(cssClass);
    }    
}


$('.navbar-collapse ul li a').click(function () {
    $('.navbar-toggle:visible').click();
});

