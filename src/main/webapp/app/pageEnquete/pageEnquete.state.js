/**
 * Created by ervan on 30/10/16.
 */
(function() {
    'use strict';

    angular
        .module('taaGliProjectApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('pageEnquete', {
            parent: 'app',
            url: '/ajoutEnquetePerso',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/pageEnquete/pageEnquete.html',
                    controller: 'EnqueteController',
                    controllerAs: 'vm'
                }
            }
        })
            .state('pageEnquete.new', {
                parent: 'app',
                url: '/newEnquete',
                data: {
                    authorities: ["ROLE_STUDENT"]
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/pageEnquete/formEnquete.html',
                        controller: 'FormEnqueteController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    nomSociete: null,
                                    phone: null,
                                    email: null,
                                    stay: null,
                                    internshipSalary: null,
                                    salary: null,
                                    comment: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function() {
                        $state.go('pageEnquete', null, { reload: 'pageEnquete' });
                    }, function() {
                        $state.go('pageEnquete');
                    });
                }]
            })
        ;
    }
})();

