
function getImageUrl() {
    return "http://loan.torinosrc.com";
}

function getApiUrl() {
    return "http://120.24.241.243:8090";
}

/**
 * 获取指定的URL参数值
 * URL:http://www.quwan.com/index?name=tyler
 * 参数：paramName URL参数
 * 调用方法:getParam("name")
 * 返回值:tyler
 */
function getParam(paramName) {
    paramValue = "", isFound = !1;
    if (this.location.search.indexOf("?") == 0 && this.location.search.indexOf("=") > 1) {
        arrSource = unescape(this.location.search).substring(1, this.location.search.length).split("&"), i = 0;
        while (i < arrSource.length && !isFound) arrSource[i].indexOf("=") > 0 && arrSource[i].split("=")[0].toLowerCase() == paramName.toLowerCase() && (paramValue = arrSource[i].split("=")[1], isFound = !0), i++
    }
    return paramValue == "" && (paramValue = null), paramValue
}

/**
 * 将form序列化成json对象
 * */
$.fn.serializeObject = function () {
    var d = {};
    var t = this.serializeArray();
    $.each(t, function() {
        d[this.name] = this.value;
    });
    return JSON.stringify(d);
}

/**
 * 将json对象赋值给form
 * 
 * @param {dom} 指定的选择器
 * @param {obj} 需要给form赋值的json对象
 * @method serializeJson
 * */
$.fn.setForm = function(jsonValue){
    var obj = this;
    $.each(jsonValue,function(name,ival){
        var $oinput = obj.find("input[name="+name+"]");
        if($oinput.attr("type")=="checkbox"){
            if(ival !== null){
                var checkboxObj = $("[name="+name+"]");
                var checkArray = ival.split(";");
                for(var i=0;i<checkboxObj.length;i++){
                    for(var j=0;j<checkArray.length;j++){
                        if(checkboxObj[i].value == checkArray[j]){
                            checkboxObj[i].click();
                        }
                    }
                }
            }
        }
        else if($oinput.attr("type")=="radio"){
            $oinput.each(function(){
                var radioObj = $("[name="+name+"]");
                for(var i=0;i<radioObj.length;i++){
                    if(radioObj[i].value == ival){
                        radioObj[i].click();
                    }
                }
            });
        }
        else if($oinput.attr("type")=="textarea"){
            obj.find("[name="+name+"]").html(ival);
        }
        else{
            obj.find("[name="+name+"]").val(ival);
        }
    })
};

/**
 * 将时间格式化成yyyy-mm-dd HH:MM:ss
 *
 * @param {now} 时间
 * @method formatDateTime
 * */
function formatDateTime1(now) {
    var year=now.getYear() + 1900;
    var month=now.getMonth()+1;
    var date=now.getDate();
    var hour=now.getHours();
    var minute=now.getMinutes();
    var second=now.getSeconds();
    return year+"-"+month+"-"+date+" "+hour+":"+minute+":"+second;
};

function formatDateTime(datetime) {
    var year = datetime.getFullYear();
    var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
    var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
    var hour = datetime.getHours()< 10 ? "0" + datetime.getHours() : datetime.getHours();
    var minute = datetime.getMinutes()< 10 ? "0" + datetime.getMinutes() : datetime.getMinutes();
    var second = datetime.getSeconds()< 10 ? "0" + datetime.getSeconds() : datetime.getSeconds();
    return year + "-" + month + "-" + date+" "+hour+":"+minute+":"+second;
}

/**
 * 将时间格式化成yyyy-mm-dd
 *
 * @param {now} 时间
 * @method formatDate
 * */
function formatDate(datetime) {
    var year = datetime.getFullYear();
    var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
    var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
    return year+"-"+month+"-"+date;
};

/**
 * 将json数据转成array
 *
 * @param {data} json数据
 * @method json2Array
 * */
function json2Array(data){
    var len=eval(data).length;
    var arr=[];
    for(var i=0;i<len;i++){
        arr[i] =[]; //js中二维数组必须进行重复的声明，否则会undefind
        arr[i]['url']=data[i].url;
        arr[i]['oldname']=data[i].oldname;
    }
    return arr;
}

/**
 * 统一跳转方法
 *
 * @param url 需要跳转的url
 * @param redirectTime 倒数多长时间
 * @method redirectToUrl
 * */
function redirectToUrl(url, redirectTime) {
    var time = redirectTime;
    var redirectTo = function () {
        $("#dashboard").html("<span class='text-center col-sm-12' style='color: black;'>成功提交数据，" + time + "秒后自动跳转...</span>" +
            "<span class='text-center col-sm-12' style='color: black;'>如果没有自动跳转，可以点击<a href='" + url + "' target='_self'>这里</a>来手动跳转...</span>");
        if(time > 0) {
            time = time - 1;
        } else {
            window.location.href = url;
        }
    };
    redirectTo();
    window.setInterval(redirectTo, 1000);
}