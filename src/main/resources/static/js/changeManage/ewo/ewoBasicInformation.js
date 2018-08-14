$(document).ready((function () {
    var myData = JSON.stringify({
        "id": $("#id").val(),
        "ewoNo": $("#ewoNo").val(),
        "dept": $("#dept").val(),
        "changeType": $("#changeType").val(),
        "reasonCode": $("#reasonCode").val(),
        "formCreateTime": $("#formCreateTime").val(),
        "flowStatus": $("#flowStatus").val(),
        "title": $("#title").val(),
        "originator": $("#originator").val(),
        "tel": $("#tel").val(),
        "finishTime": $("#finishTime").val(),
        "costAssumeDept": $("#costAssumeDept").val(),
        "publicType": $("#publicType").val(),
        "relationEwoNo": $("#relationEwoNo").val(),
        "reasonDesc": $("#reasonDesc").val(),
        "platform": $("#platform").val(),
        "vehicleCode": $("#vehicleCode").val(),
        "projectCode": $("#projectCode").val(),
        "effectTime": $("#effectTime").val(),
        "projectStage": $("#projectStage").val(),
        "relationEngineer": $("#relationEngineer").val(),
        "relationEngineerDept": $("#relationEngineerDept").val(),
        "relationItemId": $("#relationItemId").val(),
        "relationItemName": $("#relationItemName").val(),
        "changeDesc": $("#changeDesc").val(),
    })




















    $('#finishTime').datetimepicker({
        format: 'yyyy-mm-dd  hh:ii:ss',//日期的格式
        weekStart: 1,//一周开始， 0（星期日）到6（星期六）
        startDate: "1900-01-01",//选择器的开始日期
        autoclose: true,//日期选择完成后是否关闭选择框
        bootcssVer: 3,//显示向左向右的箭头
        language: 'zh_CN',//语言
        minView: 2,//表示日期选择的最小范围，默认是hour
        todayBtn: true
    })
    $('#effectTime').datetimepicker({
        format: 'yyyy-mm-dd  hh:ii:ss',//日期的格式
        weekStart: 1,//一周开始， 0（星期日）到6（星期六）
        startDate: "1900-01-01",//选择器的开始日期
        autoclose: true,//日期选择完成后是否关闭选择框
        bootcssVer: 3,//显示向左向右的箭头
        language: 'zh_CN',//语言
        minView: 2,//表示日期选择的最小范围，默认是hour
        todayBtn: true
    })
}))

