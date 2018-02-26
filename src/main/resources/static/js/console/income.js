$.ajax({
    url: "/billing/count/query",
    type: "get",
    data:{
        "type": 1
    },
    success: function (data) {
        var data = eval(data).data;
        $(".seven-count").html(data.seven);
        $(".month-count").html(data.month);
        $(".year-count").html(data.year);
        $(".all-count").html(data.all);
    }, error: function () {
    }
})
queryRecord(-7);
function queryRecord(days){
    layer.load();
    $.ajax({
        url: "/billing/record/query",
        type: "get",
        data:{
            "type": 1,
            "days": days,
        },
        success: function (data) {
            layer.closeAll();
            var data = eval(data).data;
            $("#income-record-ul").empty();
            $("#income-record-table").children("tbody").empty();
            $.each(data, function (index, value) {
                // 添加时间轴显示
                var content = "<li class=\"layui-timeline-item\">"+
                    "<i class=\"layui-icon layui-timeline-axis\"></i>"+
                    "<div class=\"layui-timeline-content layui-text\">"+
                    "<h3 class=\"layui-timeline-title\">"+value.addTime+"</h3>"+
                    "<p style=\"line-height: 40px\">"+
                    "<b>"+value.billingType+":<font style=\"color:#ff383d; font-size:16px\">"+value.moneyNum+"</font>元</b></p>"+value.moneyDesc+
                    "<br><button class=\"layui-btn layui-btn-normal layui-btn-sm\" onclick=\"deleteRecord('"+value.id+"')\"><i class=\"layui-icon\"></i></button> <button class=\"layui-btn layui-btn-sm\" onclick=\"showEditDiv('"+value.id+"','"+value.addTime+"','"+value.moneyDesc+"','"+value.billingType+"','"+value.moneyNum+"')\"><i class=\"layui-icon\"></i></button><br><br></div></li>";
                // 添加表格显示
                var tr = "<tr>"+
                    "<td>"+value.addTime+"</td>"+
                    "<td>"+value.billingType+"</td>"+
                    "<td>"+value.moneyNum+"</td>"+
                    "<td style=\"text-align: left\">"+value.moneyDesc+"</td>"+
                    "<td>" +
                    "<button class=\"layui-btn layui-btn-normal layui-btn-sm\" onclick=\"deleteRecord('"+value.id+"')\"><i class=\"layui-icon\"></i></button>" +
                    "</td>" +
                    "<td>" +
                    "<button class=\"layui-btn layui-btn-sm\" onclick=\"showEditDiv('"+value.id+"','"+value.addTime+"','"+value.moneyDesc+"','"+value.billingType+"','"+value.moneyNum+"')\"><i class=\"layui-icon\"></i>" +
                    "</button>" +
                    "</td>" +
                    "</tr>";
                $("#income-record-ul").append(content);
                $("#income-record-table").children("tbody").append(tr);
            });
            var more = "<li class=\"layui-timeline-item\">"+
                "<i class=\"layui-icon layui-timeline-axis\"></i>"+
                "<div class=\"layui-timeline-content layui-text\">"+
                "<a><h4 class=\"layui-timeline-title\">点击加载更多…</h4></a>"+
                "</li>"
            $("#income-record-ul").append(more);
        }, error: function () {
            layer.closeAll();
            layer.msg("数据加载请求失败！");
        }
    })
}

function showEditDiv(id, addTime, moneyDesc, type, moneyNum) {
    $("#id_edit").val(id);
    $("#add_income_time_edit").val(addTime);
    $("#add_income_time_edit").attr("disabled", "disabled");
    $("#billing_type_edit").find("option[text='"+type+"']").attr("selected",true);
    $("#money_num_edit").val(moneyNum);
    $("#money_desc_edit").val(moneyDesc);
    $("#edit-record-div").show(300);
}

function deleteRecord(id){
    layer.load();
    $.ajax({
        url: "/billing/delete",
        type: "post",
        data:{
            "id": id
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
            layer.msg("删除失败");
        }
    })
}

$("#addIncomeRecordBtn").on("click", function () {
    var addTime = $("#add_income_time").val();
    var billingType = $("#billing_type").val();
    var moneyNum = $("#money_num").val();
    var moneyDesc = $("#money_desc").val();
    if (addTime == ""){
        layer.msg('请选择记录时间！', {offset: 't', anim: 6});return;
    }
    if (billingType == ""){
        layer.msg('记录类型不能为空！', {offset: 't', anim: 6});return;
    }
    if (moneyNum == ""){
        layer.msg('金额不能为空！', {offset: 't', anim: 6});return;
    }
    if (moneyDesc == ""){
        layer.msg('记录描述不能为空！', {offset: 't', anim: 6});return;
    }
    layer.load();
    $.ajax({
        url: "/billing/add",
        type: "post",
        data:{
            "add_time": addTime,
            "type": 1,
            "billing_type": billingType,
            "money_num": moneyNum,
            "money_desc": moneyDesc
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

$("#editIncomeRecordBtn").on("click", function () {
    var addTime = $("#add_income_time_edit").val();
    var billingType = $("#billing_type_edit").val();
    var moneyNum = $("#money_num_edit").val();
    var moneyDesc = $("#money_desc_edit").val();
    if (addTime == ""){
        layer.msg('请选择记录时间！', {offset: 't', anim: 6});return;
    }
    if (billingType == ""){
        layer.msg('记录类型不能为空！', {offset: 't', anim: 6});return;
    }
    if (moneyNum == ""){
        layer.msg('金额不能为空！', {offset: 't', anim: 6});return;
    }
    if (moneyDesc == ""){
        layer.msg('记录描述不能为空！', {offset: 't', anim: 6});return;
    }
    layer.load();
    $.ajax({
        url: "/billing/edit",
        type: "post",
        data:{
            "id": $("#id_edit").val(),
            "add_time": addTime,
            "billing_type": billingType,
            "type": 1,
            "money_num": moneyNum,
            "money_desc": moneyDesc
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