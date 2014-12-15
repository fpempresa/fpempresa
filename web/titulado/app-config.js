"use strict";


app.config(['remoteDAOFactoryProvider', 'dateFormatProvider', function (remoteDAOFactoryProvider, dateFormatProvider) {
        remoteDAOFactoryProvider.setBaseUrl("../api");
        dateFormatProvider.setDefaultDateFormat("dd/MM/yyyy");
        
        
    }]);