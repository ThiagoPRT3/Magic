(function() {
    'use strict';

    angular
        .module('magicApp')
        .controller('CartaHabilidadeDialogController', CartaHabilidadeDialogController);

    CartaHabilidadeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CartaHabilidade'];

    function CartaHabilidadeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CartaHabilidade) {
        var vm = this;

        vm.cartaHabilidade = entity;
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
            if (vm.cartaHabilidade.id !== null) {
                CartaHabilidade.update(vm.cartaHabilidade, onSaveSuccess, onSaveError);
            } else {
                CartaHabilidade.save(vm.cartaHabilidade, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('magicApp:cartaHabilidadeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
