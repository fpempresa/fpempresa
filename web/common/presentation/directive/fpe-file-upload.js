/**
 *   FPempresa
 *   Copyright (C) 2020  Lorenzo González
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Affero General Public License as
 *   published by the Free Software Foundation, either version 3 of the
 *   License, or (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Affero General Public License for more details.
 *
 *   You should have received a copy of the GNU Affero General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
"use strict";

angular.module("common").directive('fpeFileUpload', ['ix3Configuration', function (ix3Configuration) {
        return {
            restrict: 'E',
            scope: {
                text: "@",
                url: "@",
                success: "&",
                fail: "&",
                disabled: "="
            },
            templateUrl: getContextPath() + '/common/presentation/directive/fpe-file-upload.html',
            link: function ($scope, element, attributes) {
                var mainProgressElement = $('.progress', element);

                mainProgressElement.css({visibility: "hidden"});
                $(element).fileupload({
                    url: $scope.url,
                    method: "POST",
                    progressall: function (e, data) {
                        mainProgressElement.css({visibility: "visible"});
                        var progress = parseInt(data.loaded / data.total * 100, 10);
                        $('.progress-bar', element).css('width', progress + '%');
                    },
                    always: function () {
                        mainProgressElement.css({visibility: "hidden"});
                        $('.progress-bar', element).css('width', 0 + '%');
                    },
                    done: function (e, data) {
                        $scope.$evalAsync($scope.success);
                    },
                    fail: function (e, data) {
                        $scope.$evalAsync($scope.fail);
                        $scope.fail({data: data});
                    }
                });
            }
        };
    }]);



