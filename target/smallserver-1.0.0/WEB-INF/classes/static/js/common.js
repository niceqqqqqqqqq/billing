var CookieKeyEnum= {
    managerMenuUrl: "manager_menu_url",   // 后台管理菜单
    headerMenuId: "header_menu_id"    // 前台header菜单
}

// feedback
var feedbackContent = "<img src=\"/images/default/ewm.PNG\" class=\"ewm-feedback\">";
$(".feedback-btn").on('click', function () {
    layer.tips(feedbackContent, '.feedback-btn', {
        tips: [3, '#fcfcfc']
    });
})

// 通用菜单切换方法
function getMenuBody(btnId, divId, count, now){
    for(var i = 1; i <= count; i++){
        $("#"+divId+i).hide();
        $("#"+btnId+i).css("border-bottom", "1px solid #c4c4c4");
        $("#"+btnId+i).css("color", "#454545");
    }
    $("#"+divId+now).fadeIn(200);
    $("#"+btnId+now).css("border-bottom", "1px solid #dc5462");
    $("#"+btnId+now).css("color", "black");
    return;
}

// layui 表达使用配置
layui.use(['form', 'layedit', 'laydate'], function(){
    var form = layui.form
        ,layer = layui.layer
        ,layedit = layui.layedit
        ,laydate = layui.laydate;
    //日期
    laydate.render({
        elem: '#add_income_time'
    });
});