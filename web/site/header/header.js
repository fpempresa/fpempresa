app.controller("HeaderController", [function () {
        //Esto es para que se cierre el menu cuando está en un movil.
        //TODO: Ponerlo en una directiva. La dificultad no es la directiva sino que NO hay que simular el click del botón de "navbar-toggle". Sino hacer lo que hace él.
        $('.navbar-collapse ul li a').click(function () {
            $('.navbar-toggle:visible').click();
        });
    }]);
