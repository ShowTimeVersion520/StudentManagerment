// 获取展示表格
var table = $('#tb_user');

// 全局变量
var apiUrl = "/api/v1/scs";//TODO:this

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

    $scope.process = "初始化控件";

    $scope.fractions = [
        {id:"-2147483648", value: "全部"},
        {id:"0", value: "不及格"}
    ];
    $scope.sorts = [
        {id:"fraction", value: "成绩"},
        {id:"gradeRanking", value: "全级排名"},
        {id:"classRanking", value: "班级排名"},
        {id: "studentNumber", value:"学号"}
    ];
    $scope.sortDirections = [
        {id:"ASC", value: "升序"},
        {id:"DESC", value: "降序"}
    ];
//    $http({
//    method: 'GET',
//    url:"/api/v1/scs/studentNumbers/all"
//    }).then(function successCallback(response) {
//    $scope.studentNumbers = response.data.data;
//    $scope.process = "";
//    }, function errorCallback(response) {
//    $scope.process = "提交数据失败！";
//    });
//    $http({
//    method: 'GET',
//    url:"/api/v1/scs/studentNames/all"
//    }).then(function successCallback(response) {
//    $scope.studentNames = response.data.data;
//    $scope.process = "";
//    }, function errorCallback(response) {
//    $scope.process = "提交数据失败！";
//    });
//    $http({
//    method: 'GET',
//    url:"/api/v1/scs/courseNumbers/all"
//    }).then(function successCallback(response) {
//    $scope.courseNumbers = response.data.data;
//    $scope.process = "";
//    }, function errorCallback(response) {
//    $scope.process = "提交数据失败！";
//    });
//    $http({
//    method: 'GET',
//    url:"/api/v1/scs/courseNames/all"
//    }).then(function successCallback(response) {
//    $scope.courseNames = response.data.data;
//    $scope.process = "";
//    }, function errorCallback(response) {
//    $scope.process = "提交数据失败！";
//    });
//    $http({
//    method: 'GET',
//    url:"/api/v1/scs/fractions/all"
//    }).then(function successCallback(response) {
//    $scope.fractions = response.data.data;
//    $scope.process = "";
//    }, function errorCallback(response) {
//    $scope.process = "提交数据失败！";
//    });
   $http({
   method: 'GET',
   url:"/api/v1/students/grades/all"
   }).then(function successCallback(response) {
   $scope.grades = response.data.data;
   $scope.process = "";
   }, function errorCallback(response) {
   $scope.process = "提交数据失败！";
   });
   $http({
   method: 'GET',
   url:"/api/v1/students/classNames/all"
   }).then(function successCallback(response) {
   $scope.classNames = response.data.data;
   $scope.process = "";
   }, function errorCallback(response) {
   $scope.process = "提交数据失败！";
   });
    // 定义form的数据存储地方
    $scope.formData = {
        enabled: "" // 初始化select的值，1为启用，参考 $scope.enableds
                    //,studentNumber:""
                    //,studentName:""
                    //,courseNumber:""
                    //,courseName:""
                    ,fraction:"-2147483648"
                    //,gradeRanking:""
                    //,classRanking:""
                    ,sortDirection:"ASC"
                    ,sort:"studentNumber"
        ,className:""
        ,grade:""
            };


    $scope.search = function () {
        // 刷新表格
        table.bootstrapTable('refresh',{'url':apiUrl});
    }

    $scope.process = "初始化表格";
    // ********************************* Bootstrap Table ********************************* //
    // 初始化页面bootstrap-table
    // 详情可以查询 ： http://bootstrap-table.wenzhixin.net.cn/zh-cn/documentation/
    myBootstrapTable = function() {
        table.bootstrapTable({
            url: apiUrl, //请求后台的URL（*）
            contentType: "application/json",
            method: 'get', //请求方式（*）
            dataType: 'json', //跨域
            toolbar: '#toolbar', //工具按钮用哪个容器
            striped: true, //是否显示行间隔色
            cache: false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true, //是否显示分页（*）
            sortable: false, //是否启用排序
            sortOrder: "asc", //排序方式
            queryParams: queryParams, //传递参数（*） 自定义参数
            queryParamsType: 'limit',// 值为limit是调用自定义参数定义,值为空是默认传递 pageSize: params.pageSize（页面大小），pageNumber: params.pageNumber, （页码）
            responseHandler: responseHandler, //处理返回数据
            sidePagination: "server", //分页方式：client客户端分页，server服务端分页（*）
            pageNumber: 1, //初始化加载第一页，默认第一页
            pageSize: 10, //每页的记录行数（*）
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
            field: 'state',
            checkbox: true,
            align: 'center'
            },
                        {
                field: 'studentNumber',
                title: '学号',
                align: 'left',
                width: 165,
            },
                        {
                field: 'studentName',
                title: '学生姓名',
                align: 'left',
                width: 165,
            },
                        {
                field: 'courseNumber',
                title: '课程号',
                align: 'left',
                width: 165,
            },
                        {
                field: 'courseName',
                title: '课程名称',
                align: 'left',
                width: 165,
            },
                        {
                field: 'fraction',
                title: '成绩',
                align: 'left',
                width: 165,
            },
                        {
                field: 'gradeRanking',
                title: '全级排名',
                align: 'left',
                width: 165,
            },
                        {
                field: 'classRanking',
                title: '班级排名',
                align: 'left',
                width: 165,
            },
                        {
            field: 'ss',
            title: '操作',
            align: 'center',
            formatter: function(value, row, index) {
                return [
                    '<button class="edit btn btn-success btn-sm" type="button">',
                        '<i class="glyphicon glyphicon-edit"></i>&nbsp编辑',
                        '</button>  ',
                    "<button class='remove btn btn-success btn-sm' type='button'>",
                        '<i class="glyphicon glyphicon-remove"></i>&nbsp删除',
                        '</button>  ',
                ].join('');
            },
            events: {
                //事件
                'click .remove': function(e, value, row, index) {
                    //formatter中点击删除按钮事件
                    //机制：click表示点击，.remove表示class名
                    //$table.bootstrapTable('check', index);
                    getDelete(row.id);

                },
                'click .edit': function(e, value, row, index) {
                    //formatter中点击删除按钮事件
                    //机制：click表示点击，.remove表示class名
                    window.location.href = "/admin/sc/scedit.html?id=" + row.id;//TODO:this
                },
            }
        }, ]
    });
};

    // 分页设置，设置和服务器交互的分页字段
    queryParams = function(params) {
        var pageNumber= (params.offset / params.limit); // 页码从0开始

        var temp = {
            //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            pageSize: params.limit,  //页面大小
            pageNumber: pageNumber  //页码
                            ,studentNumber: $scope.formData.studentNumber
                            ,studentName: $scope.formData.studentName
                            ,courseNumber: $scope.formData.courseNumber
                            ,courseName: $scope.formData.courseName
                            ,fraction: $scope.formData.fraction
                            ,gradeRanking: $scope.formData.gradeRanking
                            ,classRanking: $scope.formData.classRanking
            ,sort: $scope.formData.sort
            ,sortDirection: $scope.formData.sortDirection
            ,className: $scope.formData.className
            ,grade: $scope.formData.grade
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

    // 删除方法 - 调用模态框，这样用旧的模板，直接使用jquery进行处理，后期可改进
    function getDelete(id) {
        if(id == "" || id == null) { //判断传回的id是否有数据
            alert("请选择行");
        } else {
            $("#myModal").modal('show'); //显示模态框
            //重新绑定模型防止事件每次重新连接
            $('#myModal').unbind().modal();
            $('#save').unbind().on('click');
            $('#qx').unbind().on('click');

            $("#save").on('click',function() { //当确定按钮被点击执行事件
                $.ajax({
                    url: apiUrl + "/" + id,
                    type: 'delete',
                    success: function(data) {
                        $("#myModal").modal('hide'); //删除成功后关闭模态框
                        table.bootstrapTable('refresh'); //删除后重新刷新表格
                    },
                    error: function(error) {
                        $scope.process = "获取数据失败！";
                    }
                });
            });
            $("#qx").click(function() { //点击取消按钮关闭模态框
                $("#myModal").modal('hide');
            });
    }
    }

    // 批量删除
    // 获取表格中选中的所有id
    function getIdSelections() {
        return $.map(table.bootstrapTable('getSelections'), function(row) {
            return row.id
        });
    };

    // 批量删除 - 调用模态框，这样用旧的模板，直接使用jquery进行处理，后期可改进
    $("#delete-multi-btn").click(function() {
        var ids = getIdSelections();
        if(ids == "" || ids == null) { //判断传回的id是否有数据
            alert("请选择行");
        } else {
            $("#myModal").modal('show'); //显示模态框
            //重新绑定模型防止事件每次重新连接
            $('#myModal').unbind().modal();
            $('#save').unbind().on('click');
            $('#qx').unbind().on('click');

            $("#save").click(function() { //当确定按钮被点击执行事件
                $.ajax({
                    url: apiUrl,
                    contentType: "text/plain",
                    data: "" + ids,
                    type: 'delete',
                    success: function(message) {
                        $("#myModal").modal('hide'); //删除成功后关闭模态框
                        table.bootstrapTable('refresh'); //删除后重新刷新表格
                    },
                    error: function(error) {
                        $scope.process = "获取数据失败！";
                    }
                });
            });
            $("#qx").click(function() { //点击取消按钮关闭模态框
                $("#myModal").modal('hide');
            });

        }
    });

    // 批量生成排名 - 调用模态框，这样用旧的模板，直接使用jquery进行处理，后期可改进
    $("#update-ranking-multi-btn").click(function() {
        $.ajax({
            url: apiUrl + "/ranking/update",
            contentType: "text/plain",
            type: 'put',
            success: function(message) {
                table.bootstrapTable('refresh'); //删除后重新刷新表格
            },
            error: function(error) {
                $scope.process = "更新数据失败！";
            }
        });
    });

    // 调用bootstrap-table
    myBootstrapTable();

// ********************************* Bootstrap Table ********************************* //
    $scope.process = "";
}]);

