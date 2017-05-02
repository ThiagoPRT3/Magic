(function() {
    'use strict';

    angular
        .module('magicApp')
        .controller('CartaHabilidadeDeleteController',CartaHabilidadeDeleteController);

    CartaHabilidadeDeleteController.$inject = ['$uibModalInstance', 'entity', 'CartaHabilidade'];

    function CartaHabilidadeDeleteController($uibModalInstance, entity, CartaHabilidade) {
        var vm = this;

        vm.cartaHabilidade = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CartaHabilidade.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
