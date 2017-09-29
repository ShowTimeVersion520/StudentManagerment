// 获取展示表格
var table = $('#tb_role');

// 定义全局变量
// Role
var apiUrlRole = "/api/v1/sysroles";
// Authority
var apiUrlAuthorities = "/api/v1/sysauthorities";

// 定义当前angularjs的实例
var app = angular.module('page',['ngResource', 'ngRoute']);

// 设置location
app.config(['$locationProvider', function($locationProvider) {
    $locationProvider.html5Mode({
        enabled: true,
        requireBase: false
    });
}]);

// 操作form的controller
app.controller('form', ['$scope', '$location', '$http', '$window', function($scope, $location, $http, $window){
    $scope.process = "查询中，请稍等...";

    $scope.enableds = [
        {id:0, value: "禁用"},
        {id:1,value: "启用"}
    ];

    // 定义form的数据存储地方
    $scope.formData = {
        enabled: 1 // 初始化select的值，1为启用，参考 $scope.enableds
    };

    // 定义submit方法
    $scope.submit = function () {

        // 默认是插入新记录
        url = apiUrlRole;
        method = 'POST';

        // 当id存在时，证明是要更新
        if($scope.formData.id != null && $scope.formData.id != ""){
            url = apiUrlRole + "/" + $scope.formData.id;
            method = 'PUT';
        }

        // 提交数据的方式
        $http({
            method: method,
            url: url,
            data: $scope.formData,
            dataType: "application/json"
        }).then(function successCallback(response) {
            // this callback will be called asynchronously
            // when the response is available
            // The response object has these properties:
            //
            //     data – {string|Object} – The response body transformed with the transform functions.
            //     status – {number} – HTTP status code of the response.
            //     headers – {function([headerName])} – Header getter function.
            //     config – {Object} – The configuration object that was used to generate the request.
            //     statusText – {string} – HTTP status text of the response.

            // 设置跳转时间
            redirectToUrl("/admin/sysrole/index.html", redirectTime);


            // var time = 3
            // var goto = function () {
            //     var gotoUrl = "/admin/sysrole/index.html";
            //     angular.element("#dashboard").html("<span class='text-center col-sm-12' style='color: black;'>" +
            //         "成功提交数据，" + time + "秒后自动跳转...如果没有自动跳转，可以点击<a href='" + gotoUrl + "'>这里</a>来手动跳转...</span>");
            //     if(time > 0) {
            //         time = time - 1;
            //     } else {
            //         $window.location = gotoUrl;
            //     }
            // };
            // goto();
            // $window.setInterval(goto, 1000);


            $scope.process = "提交数据成功";
        }, function errorCallback(response) {
            // called asynchronously if an error occurs
            // or server returns response with an error status.
            $scope.process = "提交数据失败！";
        });
    }

    // ********************************* Bootstrap Table ********************************* //
    // 初始化页面bootstrap-table
    // 详情可以查询 ： http://bootstrap-table.wenzhixin.net.cn/zh-cn/documentation/
    myBootstrapTable = function() {
        table.bootstrapTable({
            // url: apiUrl, //请求后台的URL（*）
            data: '',
            contentType: "application/json",
            method: 'get', //请求方式（*）
            dataType: 'json', //跨域
            toolbar: '#toolbar', //工具按钮用哪个容器
            striped: true, //是否显示行间隔色
            cache: false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: false, //是否显示分页（*）
            sortable: false, //是否启用排序
            sortOrder: "asc", //排序方式
            queryParams: queryParams, //传递参数（*） 自定义参数
            queryParamsType: 'limit',// 值为limit是调用自定义参数定义,值为空是默认传递 pageSize: params.pageSize（页面大小），pageNumber: params.pageNumber, （页码）
            responseHandler: responseHandler, //处理返回数据
            sidePagination: "client", //分页方式：client客户端分页，server服务端分页（*）
            pageNumber: 1, //初始化加载第一页，默认第一页
            pageSize: 25, //每页的记录行数（*）
            pageList: [10, 25, 50, 100], //可供选择的每页的行数（*）
            search: false, //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
            strictSearch: true,
            showColumns: false, //是否显示所有的列
            showRefresh: false, //是否显示刷新按钮
            minimumCountColumns: 2, //最少允许的列数
            clickToSelect: false, //是否启用点击选中行
            // height: 500, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            uniqueId: "id", //每一行的唯一标识，一般为主键列
            showToggle: false, //是否显示详细视图和列表视图的切换按钮
            cardView: false, //是否显示详细视图
            detailView: false, //是否显示父子表
            //checkboxHeader: true,
            columns: [{
                field: 'hasAuthority',
                checkbox: true,
                align: 'center'
            }, {
                field: 'id',
                title: '序号',
                align: 'left',
                visible: false
            }, {
                field: 'name',
                title: '权限',
                align: 'left'
            },{
                field: 'description',
                title: '描述',
                align: 'left'
            } ]
        });
    };

    // 分页设置，设置和服务器交互的分页字段
    queryParams = function(params) {
        var pageNumber= (params.offset / params.limit); // 页码从0开始

        var temp = {
            //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            pageSize: params.limit,  //页面大小
            pageNumber: pageNumber,  //页码
            chineseName: $scope.formData.chineseName,
            enabled: $scope.formData.enabled,

        };
        return temp;
    };

    // 得到服务器数据后，可以在这里进行后期处理
    responseHandler = function(res) {
        // 用于服务端分布，total记录总条数(Integer),rows是具体记录（List）
        var result=new Object;

        result.rows=res.data.content;
        result.total=res.data.totalElements;

        return result;
    };

    // 获取表格中选中的所有id
    function getIdSelections() {
        return $.map(table.bootstrapTable('getSelections'), function(row) {
            return row.id
        });
    };

    // 调用bootstrap-table
    myBootstrapTable();
    // ********************************* Bootstrap Table ********************************* //

    // 获取url上的参数 - id
    var id =  $location.search().id;

    // 初始化页面
    // 当id不存在时，判定为新增
    if(id == null || id == ""){
        $http({
            method: 'GET',
            url:apiUrlAuthorities
        }).then(function successCallback(response) {
            // this callback will be called asynchronously
            // when the response is available
            // The response object has these properties:
            //
            //     data – {string|Object} – The response body transformed with the transform functions.
            //     status – {number} – HTTP status code of the response.
            //     headers – {function([headerName])} – Header getter function.
            //     config – {Object} – The configuration object that was used to generate the request.
            //     statusText – {string} – HTTP status text of the response.

            // 双向绑定权限表格数据
            $scope.formData.sysAuthoritiesWithRole =  response.data.data.content;
            // 装载权限表格数据
            table.bootstrapTable('load', response.data.data.content);
            // 置空提示信息
            $scope.process = "";
        }, function errorCallback(response) {
            // called asynchronously if an error occurs
            // or server returns response with an error status.
            // 错误提示信息
            $scope.process = "提交数据失败！";
        });

        // 返回，跳过后面
        // return;
    }else{
        // 更新时的操作
        $http({
            method: 'GET',
            url:apiUrlRole + '/' + id
            // ,
            // params:{
            //     topicId:$stateParams.topicId
            // }
        }).then(function successCallback(response) {
            // this callback will be called asynchronously
            // when the response is available
            // The response object has these properties:
            //
            //     data – {string|Object} – The response body transformed with the transform functions.
            //     status – {number} – HTTP status code of the response.
            //     headers – {function([headerName])} – Header getter function.
            //     config – {Object} – The configuration object that was used to generate the request.
            //     statusText – {string} – HTTP status text of the response.

            // $scope.id = response.data.id;
            // $scope.englishName = response.data.englishName;
            // $scope.chineseName = response.data.chineseName;
            // $scope.description = response.data.description;
            // $scope.enabled = response.data.enabled;
            $scope.formData = response.data.data;

            table.bootstrapTable('load', response.data.data.sysAuthoritiesWithRole);

            $scope.process = "";


        }, function errorCallback(response) {
            // called asynchronously if an error occurs
            // or server returns response with an error status.
            $scope.process = "提交数据失败！";
        });
    }
}]);

