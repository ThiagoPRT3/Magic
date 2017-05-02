(function() {
    'use strict';

    angular
        .module('magicApp')
        .controller('HabilidadeDeleteController',HabilidadeDeleteController);

    HabilidadeDeleteController.$inject = ['$uibModalInstance', 'entity', 'Habilidade'];

    function HabilidadeDeleteController($uibModalInstance, entity, Habilidade) {
        var vm = this;

        vm.habilidade = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Habilidade.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
