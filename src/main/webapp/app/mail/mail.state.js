(function() {
    'use strict';

    angular
        .module('taaGliProjectApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
            .state('mail', {
                parent: 'app',
                url: '/mail',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'mail'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/mail/mail.html',
                        controller: 'MailController',
                        controllerAs: 'vm'
                    }
                }
            })
    }
})
