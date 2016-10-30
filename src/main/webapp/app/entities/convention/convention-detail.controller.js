(function() {
    'use strict';

    angular
        .module('taaGliProjectApp')
        .controller('ConventionDetailController', ConventionDetailController);

    ConventionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Convention'];

    function ConventionDetailController($scope, $rootScope, $stateParams, previousState, entity, Convention) {
        var vm = this;

        vm.convention = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('taaGliProjectApp:conventionUpdate', function(event, result) {
            vm.convention = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
