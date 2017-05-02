(function() {
    'use strict';
    angular
        .module('magicApp')
        .factory('CartaHabilidade', CartaHabilidade);

    CartaHabilidade.$inject = ['$resource'];

    function CartaHabilidade ($resource) {
        var resourceUrl =  'api/carta-habilidades/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
