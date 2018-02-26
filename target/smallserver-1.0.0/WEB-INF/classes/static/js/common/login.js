$.ajax({
    url: "/user/valid.login",
    type: "get",
    data: {},
    success: function (data) {
        var data = eval(data);
        if (data.success) {
            location.href = "/to/console/home";
        }
    }, error: function () {}
})


$("#fundPwd").on('click', function () {
    layer.open({
        type: 1,
        shade: false,
        title: false, //不显示标题
        content: "<div style=\"line-height: 38px; color: white; background: #5FB878;padding: 0px 25px; text-align: left;\">只能通过添加站上微信来找回密码喽！<br/> 1. 点击右上角意见反馈，微信扫描二维码<br/> 2. 验证信息填写 '钱呢：个人用户名'</div>" //捕获的元素，注意：最好该指定的元素要存放在body最外层，否则可能被其它的相对元素所影响
    });
})


$("#login-btn").on("click", function () {
    var pickName = $("#pickName").val().trim();
    var password = $("#password").val().trim();
    var checkCode = $("#checkCode").val().trim();
    if (pickName==""){
        layer.msg('登录帐号不能为空！', {offset: 't', anim: 6});return;
    }
    if (password==""){
        layer.msg('登录密码不能为空！', {offset: 't', anim: 6});return;
    }
    if (checkCode==""){
        layer.msg('验证码不能为空！', {offset: 't', anim: 6});return;
    }
    layer.load("登录中……");
    $.ajax({
        url: "/user/login",
        type: "get",
        data:{
            "pickName": pickName,
            "password": password,
            "checkCode": checkCode
        },
        success:function(data){
            layer.closeAll();
            var data = eval(data);
            if (data.success){
                location.href="/to/console/home";
            } else {
                layer.msg(data.desc, {offset: 't', anim: 6});return;
            }
        },error:function () {
            layer.closeAll();
            layer.msg("请求失败！");
        }
    })
})