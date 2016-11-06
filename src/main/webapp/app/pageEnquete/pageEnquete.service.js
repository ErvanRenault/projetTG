/**
 * Created by ervan on 31/10/16.
 */
(function() {
    'use strict';
    angular
        .module('taaGliProjectApp')
        .factory('Enquete', Enquete);

    Enquete.$inject = ['$resource'];

    function Enquete ($resource) {
        var resourceUrl =  'api/enquetes/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
