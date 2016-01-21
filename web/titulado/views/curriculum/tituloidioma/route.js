"use strict";
app.config(['crudRoutesProvider', function(crudRoutesProvider) {
        crudRoutesProvider.addAllRoutes({
            entity:"TituloIdioma",
            crudName:"curriculum.TituloIdioma"
        });
    }]);
