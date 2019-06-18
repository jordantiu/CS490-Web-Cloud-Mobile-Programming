// 'use strict';

// Declare app level module which depends on views, and components
angular.module('myApp', [])

    .controller('View1Ctrl', function ($scope, $http) {
        $scope.venueList = new Array();
        $scope.mostRecentReview;
        $scope.getVenues = function () {
            var placeEntered = document.getElementById("txt_placeName").value;
            var searchQuery = document.getElementById("txt_searchFilter").value;


            //Creates a new array when Get venues button is clicked more than once
            $scope.venueList = [];

            if (placeEntered != null && placeEntered != "" && searchQuery != null && searchQuery != "") {

                //This is the API that gives the list of venues based on the place and search query.
                var handler = $http.get("https://api.foursquare.com/v2/venues/search" +
                    "?client_id=ZJTME2U4H1TM5FNCTLQLJRNOBXCYETQRN3OMSJEUYF5S0IX2" +
                    "&client_secret=DDMPWVCQ5AV2Y4D1LP4D2PRVT3BJBC2V22AN50YEQFHYPQL0" +
                    "&v=20160215&limit=5" +
                    "&near=" + placeEntered +
                    "&query=" + searchQuery);

                handler.success(function (data) {
                    if (data != null && data.response != null && data.response.venues != undefined && data.response.venues != null) {

                        //Declare variable location (block scope; within the scope of curly brackets)
                        let location;

                        data.response.venues.forEach(function (venue) {

                            //location contains values for city, state, and postalCode(Zip Code)
                            location = venue.location.address + ", " + venue.location.city + ", " + venue.location.state + ", " + venue.location.postalCode;

                            //push to venueList array
                            $scope.venueList.push({name: venue.name, id:venue.id, location:location});
                        })

                        // Tie an array named "venueList" to the scope which is an array of objects.
                        // Each object should have key value pairs where the keys are "name", "id" , "location" and values are their corresponding values from the response
                        // Marks will be distributed between logic, implementation and UI

                    }
                })
                handler.error(function (data) {
                    alert("There was some error processing your request. Please try after some time.");
                });
            }
        }
    });