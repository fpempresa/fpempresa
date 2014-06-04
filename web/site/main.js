app.config(['$routeProvider', function($routeProvider) {
        $routeProvider.when('/', {
            templateUrl: 'main.html',
            controller: 'MainController'
        });
    }]);


app.controller('MainController', ['$scope', 'goPage', function($scope, goPage) {
        $scope.createAccount = function(tipoUsuario) {
            goPage.createAccount(tipoUsuario);
        };

        $scope.login = function() {
            goPage.login();
        };


        var jcarousel = $('#main-carousel');
        jcarousel.carousel({
            interval: 5000
        });
        jcarousel.on('click', function(e) {
            jcarousel.carousel('pause');
        });
        $scope.carouselPrev = function() {
            jcarousel.carousel('prev');
        }

        $scope.carouselNext = function() {
            jcarousel.carousel('next');
        }
    }]);

