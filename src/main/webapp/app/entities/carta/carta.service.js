(function() {
    'use strict';
    angular
        .module('magicApp')
        .factory('Carta', Carta);

    Carta.$inject = ['$resource'];

    function Carta ($resource) {
        var resourceUrl =  'api/cartas/:id';

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
