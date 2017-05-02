(function() {
    'use strict';

    angular
        .module('magicApp')
        .controller('CartaDetailController', CartaDetailController);

    CartaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Carta'];

    function CartaDetailController($scope, $rootScope, $stateParams, previousState, entity, Carta) {
        var vm = this;

        vm.carta = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('magicApp:cartaUpdate', function(event, result) {
            vm.carta = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
