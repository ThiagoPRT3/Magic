(function() {
    'use strict';

    angular
        .module('magicApp')
        .controller('HabilidadeController', HabilidadeController);

    HabilidadeController.$inject = ['$scope', '$state', 'Habilidade'];

    function HabilidadeController ($scope, $state, Habilidade) {
        var vm = this;
        
        vm.habilidades = [];

        loadAll();

        function loadAll() {
            Habilidade.query(function(result) {
                vm.habilidades = result;
            });
        }
    }
})();
