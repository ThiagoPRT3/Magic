(function() {
    'use strict';
    angular
        .module('magicApp')
        .factory('Habilidade', Habilidade);

    Habilidade.$inject = ['$resource'];

    function Habilidade ($resource) {
        var resourceUrl =  'api/habilidades/:id';

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
