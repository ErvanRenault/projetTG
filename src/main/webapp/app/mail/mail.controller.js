(function() {
    'use strict';

    angular
        .module('taaGliProjectApp')
        .controller('mailController', mailController);

    mailController.$inject = ['$scope', '$state', 'Student'];

    function mailController ($scope, $state, Student) {
        var vm = this;

        vm.students = [];

        loadAll();

        function loadAll() {
            Student.query(function(result) {
                vm.students = result;
            });
        }
    }
})();
