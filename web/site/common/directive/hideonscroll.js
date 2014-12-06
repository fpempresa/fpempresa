"user strict";

(function () {

    app.directive("fpeHideOnScroll", ['$window', function ($window) {
            var directiveDefinitionObject = {
                template: function (tElement, tAttrs) {
                    return "";
                },
                compile: function (tElement, tAttrs) {
                    return {
                        pre: function (scope, iElement, iAttrs, controller, transcludeFn) {
                        },
                        post: function (scope, iElement, iAttrs, controller, transcludeFn) {
                            applyStyleOnScroll(iElement, "top-nav-collapse");

                            $($window).scroll(function () {
                                applyStyleOnScroll(iElement, "top-nav-collapse");
                            });
                        }
                    };
                }
            };

            return directiveDefinitionObject;
        }]);

    function applyStyleOnScroll(iElement, cssClassName) {
        var offset = iElement.offset();

        if (offset.top > 80) {
            showNavBar(iElement);
            iElement.addClass(cssClassName);
        } else {
            iElement.removeClass(cssClassName);
        }
    }



})();