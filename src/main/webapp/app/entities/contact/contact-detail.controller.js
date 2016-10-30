(function() {
    'use strict';

    angular
        .module('taaGliProjectApp')
        .controller('ContactDetailController', ContactDetailController);

    ContactDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Contact', 'Company', 'Student'];

    function ContactDetailController($scope, $rootScope, $stateParams, previousState, entity, Contact, Company, Student) {
        var vm = this;

        vm.contact = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('taaGliProjectApp:contactUpdate', function(event, result) {
            vm.contact = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
