(function() {
    'use strict';

    angular
        .module('magicApp')
        .controller('HabilidadeDialogController', HabilidadeDialogController);

    HabilidadeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Habilidade'];

    function HabilidadeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Habilidade) {
        var vm = this;

        vm.habilidade = entity;
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
            if (vm.habilidade.id !== null) {
                Habilidade.update(vm.habilidade, onSaveSuccess, onSaveError);
            } else {
                Habilidade.save(vm.habilidade, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('magicApp:habilidadeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
