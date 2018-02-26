// 邮箱校验规则
var myReg=/^[a-zA-Z0-9_-]+@([a-zA-Z0-9]+\.)+(com|cn|net|org)$/;
var sendTime = 30;

$("#register-btn").on("click", function () {
    var pickName = $("#pickName").val().trim();
    var password = $("#password").val().trim();
    var password2 = $("#password2").val().trim();
    var checkCode = $("#checkCode").val().trim();
    var email = $("#email").val().trim();
    if (pickName==""){
        layer.msg('用户名不能为空！', {offset: 't', anim: 6});return;
    }
    if (password==""){
        layer.msg('密码不能为空！', {offset: 't', anim: 6});return;
    }
    if (password != password2){
        layer.msg('两次密码不一致！', {offset: 't', anim: 6});return;
    }
    if (email==""){
        layer.msg('邮箱不能为空！', {offset: 't', anim: 6});return;
    }
    if(!myReg.test(email)){
        layer.msg('邮箱格式有误！', {offset: 't', anim: 6});return;
    }
    if (password.length > 12 || password.length < 6){
        layer.msg('密码长度应该在6~12位！', {offset: 't', anim: 6});return;
    }
    if (checkCode==""){
        layer.msg('验证码不能为空！', {offset: 't', anim: 6});return;
    }
    // if (!$("#isRead").is(':checked')){
    //     layer.msg('同意企业注册协议后方可申请！', {offset: 't', anim: 6});return;
    // }
    layer.load("注册中……");
    $.ajax({
        url: "/user/regist",
        type: "post",
        data:{
            "pickName": pickName,
            "password": password,
            "email": email,
            "checkCode": checkCode
        },
        success:function(data){
            layer.closeAll();
            var data = eval(data);
            if (data.success){
                layer.msg("注册成功");
            } else {
                layer.msg(data.desc, {offset: 't', anim: 6});return;
            }
        },error:function () {
            layer.closeAll();
            layer.msg("请求失败！");
        }
    });
})

$("#getRedEmailCode-Btn").on('click', function () {
    var email = $("#email").val().trim();
    if (email==""){
        layer.msg('邮箱不能为空！', {offset: 't', anim: 6});return;
    }
    if(!myReg.test(email)){
        layer.msg('邮箱格式有误！', {offset: 't', anim: 6});return;
    }
    controEmailCodeBtn();
    $.ajax({
        url: "/user/regCode/send",
        type: "post",
        data:{
            "email" : email
        },
        success:function(data){
            layer.closeAll();
            var data = eval(data);
            if (data.success){
                layer.msg("验证码已发送");
            } else {
                layer.msg(data.desc, {offset: 't', anim: 6});return;
            }
        },error:function () {
            layer.closeAll();
            layer.msg("请求失败！");
        }
    })
})
function controEmailCodeBtn(){
    if (sendTime > 0){
        $("#getRedEmailCode-Btn").attr("disabled", true);
        $("#getRedEmailCode-Btn").css("background-color", "#2491aa");
        $("#getRedEmailCode-Btn").html("剩余时间"+sendTime+"秒");
        --sendTime;
        setTimeout("controEmailCodeBtn()", 1000);
    } else {
        $("#getRedEmailCode-Btn").attr("disabled", false);
        $("#getRedEmailCode-Btn").css("background-color", "#32caed");
        $("#getRedEmailCode-Btn").html("重新获取验证码");
        sendTime = 30;
        return;
    }
}
