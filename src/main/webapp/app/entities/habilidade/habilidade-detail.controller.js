(function() {
    'use strict';

    angular
        .module('magicApp')
        .controller('HabilidadeDetailController', HabilidadeDetailController);

    HabilidadeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Habilidade'];

    function HabilidadeDetailController($scope, $rootScope, $stateParams, previousState, entity, Habilidade) {
        var vm = this;

        vm.habilidade = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('magicApp:habilidadeUpdate', function(event, result) {
            vm.habilidade = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
