"user strict";

app.controller("IndexController",['$scope',function($scope) {
        
}]);


// Para el carrousel de la página principal
$(function() {
    var jcarousel = $('#main-carousel');
    jcarousel.carousel({
        interval: 5000
    });
    jcarousel.on('click', function(e) {
        jcarousel.carousel('pause');
    });
});