$.ajax({
    url: "/session/user",
    type: "get",
    success:function(data){
        var data = eval(data);
        if (data.success && "" != data.data.pickName){
            $("#pickName").html(data.data.pickName);
            $("#photo").attr("src",data.data.photo);
            if (data.data.role == 1){
                $("#m10").show();
            }
        } else {
           location.href="/to/common/login";
        }
    }, error:function () {
        location.href="/to/common/login";
    }
})

// 点击后端管理菜单触发事件
function clickMenu(url) {
    setCookieDafaultOneDate(CookieKeyEnum.managerMenuUrl, url);
}
//------------- my web cookie deal method define start ------------- //
function setCookieSetDate(key, value, n) {
    var oDate = new Date();
    oDate.setDate(oDate.getDate() + 1); // 默认web端cookie生命周期为1小时
    document.cookie = key + '=' + value + ';expires=' + oDate;
}
function setCookieDafaultOneDate(key, value) {
    setCookieSetDate(key, value, 1);
}
function removeCookie(key) {
    setCookieSetDate(key, '', -1);//这里只需要把Cookie保质期退回一天便可以删除
}
function getCookie(key) {
    var cookieString = document.cookie == "" ? window.parent.document.cookie : document.cookie;
    var cookieArr = cookieString.split(';');
    for(var i = 0; i < cookieArr.length; i++) {
        var arr = cookieArr[i].split('=');
        if(arr[0] == key) {
            return arr[1];
        }
    }
    return false;
}
