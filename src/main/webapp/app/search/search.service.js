(function() {
    'use strict';
    angular
        .module('taaGliProjectApp')
        .factory('Search', Search);

    Search.$inject = ['$resource'];

    function Search ($resource, DateUtils) {
        var resourceUrl =  'api/search/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.birthDate = DateUtils.convertDateTimeFromServer(data.birthDate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
