/**
 * Created by karthik on 7/14/17.
 */
var myapp = angular.module('app',[]);
myapp.run(function ($http) {
    // Sends this header with any AJAX request
    $http.defaults.headers.common['Access-Control-Allow-Origin'] = '*';
    // Send this header only in post requests. Specifies you are sending a JSON object
    $http.defaults.headers.post['dataType'] = 'json'
});

myapp.controller('addController',function($scope,$http){
    $scope.addbook = function(){
        console.log($scope.bookName);

        var dataParams = {
            'bookName' : $scope.bookName,
            'authorName' : $scope.authorName,
            'noOfCopies' : $scope.noOfCopies,
            'edition' : $scope.edition,
            'ISBN':$scope.ISBN
        };
        var config = {
            headers : {
                'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;'
            }
        };
        var req = $http.post('http://localhost:8081/create',dataParams);
        req.success(function(data, status, headers, config) {
            $scope.message = data;
            console.log(data);
        });
        req.error(function(data, status, headers, config) {
            alert( "failure message: " + JSON.stringify({data: data}));
        });
    };
});


myapp.controller('homeController',function($scope,$http){

    $scope.getData=function(){
        var req = $http.get('http://localhost:8081/get');
        req.success(function(data, status, headers, config) {
            $scope.bookList = data;
        });
        req.error(function(data, status, headers, config) {
            alert( "failure message: " + JSON.stringify({data: data}));
        });

    };

    $scope.delete = function(id,callback){

        $http.get('http://localhost:8081/delete/'+id)
            .success(function(data){
                console.log("Successfully deleted");
                $scope.getData();
            });
    };

    $scope.update = function(book,callback){
        $http.get('http://localhost:8081/update?id='+book._id+'&bookName='+book.bookName+'&authorName='+book.authorName+'&ISBN='+book.ISBN+'&edition='+book.edition+'&noOfCopies='+book.noOfCopies)
            .success(function(data){
                console.log("Successfully updated");
                $scope.getData();
            });
    }

});

