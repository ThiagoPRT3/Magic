(function() {
    'use strict';

    angular
        .module('magicApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('habilidade', {
            parent: 'entity',
            url: '/habilidade',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Habilidades'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/habilidade/habilidades.html',
                    controller: 'HabilidadeController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('habilidade-detail', {
            parent: 'entity',
            url: '/habilidade/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Habilidade'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/habilidade/habilidade-detail.html',
                    controller: 'HabilidadeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Habilidade', function($stateParams, Habilidade) {
                    return Habilidade.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'habilidade',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('habilidade-detail.edit', {
            parent: 'habilidade-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/habilidade/habilidade-dialog.html',
                    controller: 'HabilidadeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Habilidade', function(Habilidade) {
                            return Habilidade.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('habilidade.new', {
            parent: 'habilidade',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/habilidade/habilidade-dialog.html',
                    controller: 'HabilidadeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                habilidadeNomeBr: null,
                                habilidadeNameIng: null,
                                descricao: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('habilidade', null, { reload: true });
                }, function() {
                    $state.go('habilidade');
                });
            }]
        })
        .state('habilidade.edit', {
            parent: 'habilidade',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/habilidade/habilidade-dialog.html',
                    controller: 'HabilidadeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Habilidade', function(Habilidade) {
                            return Habilidade.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('habilidade', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('habilidade.delete', {
            parent: 'habilidade',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/habilidade/habilidade-delete-dialog.html',
                    controller: 'HabilidadeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Habilidade', function(Habilidade) {
                            return Habilidade.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('habilidade', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
