(function() {
    'use strict';

    angular
        .module('taaGliProjectApp')
        .controller('EnqueteDialogController', EnqueteDialogController);

    EnqueteDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Enquete'];

    function EnqueteDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Enquete) {
        var vm = this;

        vm.enquete = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.enquete.id !== null) {
                Enquete.update(vm.enquete, onSaveSuccess, onSaveError);
            } else {
                Enquete.save(vm.enquete, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('taaGliProjectApp:enqueteUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
