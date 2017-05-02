(function() {
    'use strict';

    angular
        .module('magicApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('carta-habilidade', {
            parent: 'entity',
            url: '/carta-habilidade',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'CartaHabilidades'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/carta-habilidade/carta-habilidades.html',
                    controller: 'CartaHabilidadeController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('carta-habilidade-detail', {
            parent: 'entity',
            url: '/carta-habilidade/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'CartaHabilidade'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/carta-habilidade/carta-habilidade-detail.html',
                    controller: 'CartaHabilidadeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'CartaHabilidade', function($stateParams, CartaHabilidade) {
                    return CartaHabilidade.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'carta-habilidade',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('carta-habilidade-detail.edit', {
            parent: 'carta-habilidade-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/carta-habilidade/carta-habilidade-dialog.html',
                    controller: 'CartaHabilidadeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CartaHabilidade', function(CartaHabilidade) {
                            return CartaHabilidade.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('carta-habilidade.new', {
            parent: 'carta-habilidade',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/carta-habilidade/carta-habilidade-dialog.html',
                    controller: 'CartaHabilidadeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                cartaNome: null,
                                habilidadeNome: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('carta-habilidade', null, { reload: true });
                }, function() {
                    $state.go('carta-habilidade');
                });
            }]
        })
        .state('carta-habilidade.edit', {
            parent: 'carta-habilidade',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/carta-habilidade/carta-habilidade-dialog.html',
                    controller: 'CartaHabilidadeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CartaHabilidade', function(CartaHabilidade) {
                            return CartaHabilidade.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('carta-habilidade', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('carta-habilidade.delete', {
            parent: 'carta-habilidade',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/carta-habilidade/carta-habilidade-delete-dialog.html',
                    controller: 'CartaHabilidadeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CartaHabilidade', function(CartaHabilidade) {
                            return CartaHabilidade.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('carta-habilidade', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
