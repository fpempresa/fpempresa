"use strict";


app.config(['remoteDAOFactoryProvider', 'repositoryFactoryProvider', function (remoteDAOFactoryProvider, repositoryFactoryProvider) {
        remoteDAOFactoryProvider.setBaseUrl("../api");
        repositoryFactoryProvider.addEntityTransformer("TituloIdioma",function (className, object) {
            object.otroIdioma=object.otroIdioma+"KK";
        });
    }]);