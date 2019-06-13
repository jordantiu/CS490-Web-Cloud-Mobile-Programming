const angularTodo = angular.module('angularTodo', []);

angularTodo.controller('angularTodoCtrl', ['$scope', function ($scope) {
    $scope.items = ["Do Laundry", "Wash dishes", "Dust", "Vacuum"];
    $scope.submitNewItem = function () {
        const newItem = this.newItem.trim();
        if (newItem !== "" && this.items.indexOf(newItem) === -1) {
            $scope.items.push(newItem);
            $("#newItem").val("");
        }
    };

    $scope.completeItem = function (index) {
        const id = "#item" + index;
        $(id).addClass("completed");
        $(id + " .todo-list-item__complete").remove();

        // doesn't work -> had to use hidden class
        // $(id).append("<div class='todo-list-item__trash' ng-click='deleteItem(1)'>Trash</div>");
        // const newDiv = "<div class='todo-list-item__trash' ng-click='deleteItem("+index+")'>Trash</div>";
        // $(id).append($compile(newDiv)(scope));
        // scope.apply();

        // $(id + " .todo-list-item__trash").removeClass("hidden");
    };

    $scope.deleteItem = function (index) {
        $scope.items.splice((parseInt(index) - 1), 1);
        const id = "#item" + index;

        if(id)

        $(id).remove();
    };
}]);
