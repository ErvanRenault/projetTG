(function() {
    'use strict';

    angular
        .module('taaGliProjectApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('search', {
            parent: 'app',
            url: '/search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Search'
            },
            views: {
                'content@': {
                    templateUrl: 'app/search/search.html',
                    controller: 'SearchController',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();
