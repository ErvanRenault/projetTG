(function() {
    'use strict';

    angular
        .module('taaGliProjectApp')
        .controller('TeacherController', TeacherController);

    TeacherController.$inject = ['$scope', '$state', 'Teacher'];

    function TeacherController ($scope, $state, Teacher) {
        var vm = this;
        
        vm.teachers = [];

        loadAll();

        function loadAll() {
            Teacher.query(function(result) {
                vm.teachers = result;
            });
        }
    }
})();
