var app = angular.module('wikiApp', []);

app.factory('wikiAPI', function ($http) {
    var wikiURI = 'http://en.wikipedia.org/w/api.php?format=json&action=query&generator=search&gsrnamespace=0&gsrlimit=10&prop=pageimages|extracts&pilimit=max&exintro&explaintext&exsentences=1&exlimit=max&callback=JSON_CALLBACK&gsrsearch=';

    return function (text) {
        return $http.jsonp(wikiURI + encodeURIComponent(text));
    };
});

app.controller('wikiCtrl', function ($scope, wikiAPI) {
    var page = 'http://en.wikipedia.org/?curid=';

    $scope.submit = function () {
        $scope.isLoading = true;
        $scope.errorMessage = '';

        wikiAPI($scope.searchText).success(function (data) {
            if (typeof data.query === 'undefined') {
                $scope.isLoading = false;
                $scope.errorMessage = 'Not Found';
                $scope.pages = [];
                return;
            }

            $scope.pages = Object.keys(data.query.pages).map(function (k) {
                $scope.isLoading = false;
                var i = data.query.pages[k];
                return {title: i.title, body: i.extract, page: page + i.pageid}
            });
        });
    }
});

