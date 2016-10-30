(function() {
    'use strict';

    angular
        .module('taaGliProjectApp')
        .controller('EnqueteDetailController', EnqueteDetailController);

    EnqueteDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Enquete'];

    function EnqueteDetailController($scope, $rootScope, $stateParams, previousState, entity, Enquete) {
        var vm = this;

        vm.enquete = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('taaGliProjectApp:enqueteUpdate', function(event, result) {
            vm.enquete = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
