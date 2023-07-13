/* 
 * FPempresa Copyright (C) 2023 Lorenzo González
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Affero General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
angular.module("common").config(['repositoryFactoryProvider', function (repositoryFactoryProvider) {

    RepositoryImplCaptcha.$inject = ['entityName', 'remoteDAOFactory', 'richDomain', '$q'];
    function RepositoryImplCaptcha(entityName, remoteDAOFactory, richDomain, $q) {
        this.entityName = entityName;
        this.remoteDAO = remoteDAOFactory.getRemoteDAO(this.entityName);
        
        this.getCaptcha = function () {
            var deferred = $q.defer();
            this.remoteDAO.getCaptcha().then(function (captcha) {
                richDomain.extend(captcha);
                deferred.resolve(captcha);
            }, function (captcha) {
                richDomain.extend(captcha);
                deferred.reject(captcha);
            });
            return deferred.promise;
        };
    }

    repositoryFactoryProvider.addRepository("Captcha", RepositoryImplCaptcha);

}]);

