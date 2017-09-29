/**
 * Created by lvxin on 2017/4/25.
 */
// 定义当前angularjs的实例
var app = angular.module('page',['ngResource', 'ngRoute']);

// 设置location
app.config(['$locationProvider', function($locationProvider) {
    $locationProvider.html5Mode({
        enabled: true,
        requireBase: false
    });
}]);

// 操作dashboard的controller
app.controller('dashboard', ['$scope', '$location', '$http',  function($scope, $location, $http){

    $scope.accessInfo = "";

    var access = $location.search().access;
    if(access == "denied"){
        $scope.accessInfo = "当前用户没有权限登陆！";
    }
    if(access == "fail"){
        $scope.accessInfo = "用户密码错误！";
    }

}]);