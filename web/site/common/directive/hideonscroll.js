"user strict";

(function () {

    app.directive("fpeHideOnScroll", ['$window', '$rootScope', '$location', function ($window, $rootScope, $location) {
            var directiveDefinitionObject = {
                scope: {
                    config: "=fpeHideOnScroll"
                },
                compile: function (tElement, tAttrs) {
                    return {
                        pre: function (scope, iElement, iAttrs, controller, transcludeFn) {
                        },
                        post: function (scope, iElement, iAttrs, controller, transcludeFn) {
                            var defaultConfig = {
                                cssClassName: "top-nav-collapse",
                                threshold: 80,
                                applyInPath: "/"
                            }

                            scope.config = scope.config || defaultConfig;

                            showHide(iElement, scope.config, $location.path());

                            $rootScope.$on('$routeChangeSuccess', function (event, currentRoute, previousRoute) {
                                showHide(iElement, scope.config, currentRoute.originalPath);
                            });

                            $($window).scroll(function () {
                                showHide(iElement, scope.config, $location.path());
                            });
                        }
                    };
                }
            };

            return directiveDefinitionObject;
        }]);

    function showHide(iElement, config, currentPath) {
        if (currentPath === config.applyInPath) {
            var offset = iElement.offset();
            if (offset.top > config.threshold) {
                iElement.addClass(config.cssClassName);
            } else {
                iElement.removeClass(config.cssClassName);
            }
        } else {
            iElement.addClass(config.cssClassName);
        }
    }

})();