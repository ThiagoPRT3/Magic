(function() {
    'use strict';

    angular
        .module('magicApp')
        .controller('CartaHabilidadeDetailController', CartaHabilidadeDetailController);

    CartaHabilidadeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CartaHabilidade'];

    function CartaHabilidadeDetailController($scope, $rootScope, $stateParams, previousState, entity, CartaHabilidade) {
        var vm = this;

        vm.cartaHabilidade = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('magicApp:cartaHabilidadeUpdate', function(event, result) {
            vm.cartaHabilidade = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
