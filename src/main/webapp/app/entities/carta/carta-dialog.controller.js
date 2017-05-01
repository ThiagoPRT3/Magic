(function() {
    'use strict';

    angular
        .module('magicApp')
        .controller('CartaDialogController', CartaDialogController);

    CartaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Carta', 'CartaHabilidade'];

    function CartaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Carta, CartaHabilidade) {
        var vm = this;

        vm.carta = entity;
        vm.clear = clear;
        vm.save = save;
        vm.cartahabilidades = CartaHabilidade.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.carta.id !== null) {
                Carta.update(vm.carta, onSaveSuccess, onSaveError);
            } else {
                Carta.save(vm.carta, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('magicApp:cartaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
