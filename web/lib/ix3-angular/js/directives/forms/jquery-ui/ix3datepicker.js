"use strict";

angular.module("es.logongas.ix3.datepicker.jquery").directive('ix3Datepicker', ['dateFormat', function(dateFormat) {
        return {
            restrict: 'A',
            link: function($scope, element, attributes) {
                var format;

                if (!attributes.ix3Date) {
                    format = dateFormat.getJQueryDatepickerFormatFromAngularJSFormat(dateFormat.getAngularFormatFromPredefined(dateFormat.getDefaultDateFormat()));
                } else {
                    format = dateFormat.getJQueryDatepickerFormatFromAngularJSFormat(dateFormat.getAngularFormatFromPredefined(attributes.ix3Date));
                }

                element.datepicker({
                    dateFormat: format,
                    onSelect: function() {
                        $(element).trigger('input');
                    }
                });

                var nextElement=element.next();
                if ((nextElement) && (nextElement.length>0)) {
                    nextElement.css({
                        cursor: "pointer"
                    })
                    nextElement.on("click",function() {
                        element.datepicker( "show" );
                    });
                }

            }
        };
    }]);
