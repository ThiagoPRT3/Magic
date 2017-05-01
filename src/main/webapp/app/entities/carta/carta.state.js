(function() {
    'use strict';

    angular
        .module('magicApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('carta', {
            parent: 'entity',
            url: '/carta?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Cartas'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/carta/cartas.html',
                    controller: 'CartaController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }]
            }
        })
        .state('carta-detail', {
            parent: 'entity',
            url: '/carta/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Carta'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/carta/carta-detail.html',
                    controller: 'CartaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Carta', function($stateParams, Carta) {
                    return Carta.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'carta',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('carta-detail.edit', {
            parent: 'carta-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/carta/carta-dialog.html',
                    controller: 'CartaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Carta', function(Carta) {
                            return Carta.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('carta.new', {
            parent: 'carta',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/carta/carta-dialog.html',
                    controller: 'CartaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                carta_nome_br: null,
                                carta_name_en: null,
                                edicao: null,
                                tipo: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('carta', null, { reload: true });
                }, function() {
                    $state.go('carta');
                });
            }]
        })
        .state('carta.edit', {
            parent: 'carta',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/carta/carta-dialog.html',
                    controller: 'CartaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Carta', function(Carta) {
                            return Carta.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('carta', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('carta.delete', {
            parent: 'carta',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/carta/carta-delete-dialog.html',
                    controller: 'CartaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Carta', function(Carta) {
                            return Carta.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('carta', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
