(function() {
    'use strict';

    angular
        .module('magicApp')
        .controller('CartaController', CartaController);

    CartaController.$inject = ['$scope', '$state', 'Carta'];

    function CartaController ($scope, $state, Carta) {
        var vm = this;
        
        vm.cartas = [];

        loadAll();

        function loadAll() {
            Carta.query(function(result) {
                vm.cartas = result;
            });
        }
    }
})();
