app.run(['$rootScope', function($rootScope) {
        $rootScope.getContextPath = getContextPath;
    }]);

app.constant("apiurl", getContextPath() + "/api");