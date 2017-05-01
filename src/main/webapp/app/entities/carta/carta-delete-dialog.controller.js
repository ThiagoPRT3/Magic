(function() {
    'use strict';

    angular
        .module('magicApp')
        .controller('CartaDeleteController',CartaDeleteController);

    CartaDeleteController.$inject = ['$uibModalInstance', 'entity', 'Carta'];

    function CartaDeleteController($uibModalInstance, entity, Carta) {
        var vm = this;

        vm.carta = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Carta.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
