(function() {
    'use strict';

    angular
        .module('magicApp')
        .controller('CartaHabilidadeController', CartaHabilidadeController);

    CartaHabilidadeController.$inject = ['$scope', '$state', 'CartaHabilidade'];

    function CartaHabilidadeController ($scope, $state, CartaHabilidade) {
        var vm = this;
        
        vm.cartaHabilidades = [];

        loadAll();

        function loadAll() {
            CartaHabilidade.query(function(result) {
                vm.cartaHabilidades = result;
            });
        }
    }
})();
