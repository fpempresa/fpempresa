app.controller("HeaderController", [function () {
        //Esto es para que se cierre el menu cuando está en un movil.
        $('.navbar-collapse ul li a').click(function () {
            $('.navbar-toggle:visible').click();
        });
    }]);
