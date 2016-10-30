(function() {
    'use strict';

    angular
        .module('taaGliProjectApp')
        .controller('StudentDetailController', StudentDetailController);

    StudentDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Student', 'Stage', 'Enquete', 'Teacher', 'Contact'];

    function StudentDetailController($scope, $rootScope, $stateParams, previousState, entity, Student, Stage, Enquete, Teacher, Contact) {
        var vm = this;

        vm.student = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('taaGliProjectApp:studentUpdate', function(event, result) {
            vm.student = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
