layer.load();
$.ajax({
    url: "/feedback/query/all",
    type: "get",
    data:{
    },
    success: function (data) {
        layer.closeAll();
        var data = eval(data).data;
        $("#feedBackContent-div").empty();
        $.each(data, function (index, value) {
            var content = "<div style=\"line-height: 26px; line-height:40px; height: 50px;border-bottom: 1px solid #eeeeee\"><img src=\""+value.photo+"\" class=\"photo\"> <span class=\"pickName\">"+value.pickName+"</span>";
            if(value.type == "问题"){
            content += "<span class=\"layui-badge\" style=\"float: left;margin-top: 12px; margin-left: 20px\" >"+value.type+"</span>";
            } else {
                content += "<span class=\"layui-badge layui-bg-green\" style=\"float: left;margin-top: 12px; margin-left: 20px\" >"+value.type+"</span>";
            }
            content += "<span class=\"layui-badge layui-bg-gray\" style=\"float: right; margin-top: 12px\">"+value.createTime+"</span> </div> <div style=\"margin: 10px auto; text-align: left; line-height: 25px; color: #434343\">"+value.content+"</div>";
            $("#feedBackContent-div").append(content);
        })
    }, error: function () {
        layer.closeAll();
        layer.msg("数据加载请求失败！");
    }
})


$("#add-feedback-btn").on("click", function () {
    var content = $("#content").val();
    var type = $("#type").val();
    if (content == ""){
        layer.msg('意见反馈内容不能为空！', {offset: 't', anim: 6});return;
    }
    layer.load();
    $.ajax({
        url: "/feedback/add",
        type: "post",
        data:{
            "content": content,
            "type": type
        },
        success: function (data) {
            layer.closeAll();
            var data = eval(data);
            if (data.success){
                location.reload()
            } else {
                layer.msg(data.desc, {offset: 't', anim: 6});return;
            }
        }, error:function () {
            layer.closeAll();
            layer.msg("请求失败");
        }
    })
})