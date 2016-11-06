(function() {
    'use strict';

    angular
        .module('taaGliProjectApp')
        .controller('enqueteController', EnqueteController);

    EnqueteController.$inject = ['$scope', 'Principal', 'LoginService', '$uibModalInstance', '$state', 'Enquete' ];

    function EnqueteController ($scope, Principal, LoginService, $state, $uibModalInstance, entity, Enquete) {

        var vm = this;

        vm.enquetes = [];

        loadAll();

        function loadAll() {
            Enquete.query(function(result) {
                vm.enquetes = result;
            });
        }

    }
})();
