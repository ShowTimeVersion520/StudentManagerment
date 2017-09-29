angular.module("myTorinosrc",[])
    .controller("welcome", function ($scope,$http) {
        $scope.username = localStorage.getItem("username");

    });