app.controller("HeaderController", [function () {
        //Esto es para que se cierre el menu cuando está en un movil.
        $('.navbar-collapse ul li a').click(function () {
            $('.navbar-toggle:visible').click();
        });
    }]);


app.run(['$rootScope', '$location', '$timeout', function ($rootScope, $location, $timeout) {

        //Esto es para mostrar o ocultar la barra de navegación según si estmaos al principio o no.
        $rootScope.$on('$routeChangeSuccess', function (event, currentRoute, previousRoute) {
            if (currentRoute.originalPath === "/") {
                showHideMenu($timeout);
            } else {
                showNavBar();
            }
        });

        $(window).scroll(function () {
            if ($location.path() === "/") {
                showHideMenu($timeout);
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
            showHideMenu($timeout);
        }, 1);
    }


}

function showNavBar() {
    var element = $(".navbar-fixed-top");
    var cssClass = "top-nav-collapse";
    //if (!element.hasClass(cssClass)) {
    element.addClass(cssClass);
    //}
}

function hideNavBar() {
    var element = $(".navbar-fixed-top");
    var cssClass = "top-nav-collapse";
    //if (element.hasClass(cssClass)) {
    element.removeClass(cssClass);
    //}
}



