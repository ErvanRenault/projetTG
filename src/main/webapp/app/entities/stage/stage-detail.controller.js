(function() {
    'use strict';

    angular
        .module('taaGliProjectApp')
        .controller('StageDetailController', StageDetailController);

    StageDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Stage', 'Convention', 'Company'];

    function StageDetailController($scope, $rootScope, $stateParams, previousState, entity, Stage, Convention, Company) {
        var vm = this;

        vm.stage = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('taaGliProjectApp:stageUpdate', function(event, result) {
            vm.stage = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
