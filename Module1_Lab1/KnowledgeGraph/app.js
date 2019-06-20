// 'use strict';

// Declare app level module which depends on views, and components
angular.module('KnownledgeGraph', [])
    .controller('Ctrl', function ($scope, $http) {

        $scope.getVenues = function () {
            var placeEntered = document.getElementById("txt_placeName").value;

        $scope.venueList = [];

            if (placeEntered != null && placeEntered != "") {

                //This is the API that gives the list of venues based on the place and search query.
                var handler = $http.get("https://kgsearch.googleapis.com/v1/entities:search?query="+ placeEntered +"&key=AIzaSyDddRpJCfqYBuisKshdaC3kMZAZQSvZxhI&limit=1&indent=True");

                handler.success(function (data) {
                    if (data != null) {
                        placeEntered = "";

                    }
                })
                handler.error(function (data) {
                    alert("There was some error processing your request. Please try after some time.");
                });
            }
        }
    });
