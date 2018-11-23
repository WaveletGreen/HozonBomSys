/**
 * Created with IntelliJ IDEA.
 * User: xlf
 * Date: 2018/11/14
 * Time: 9:26
 */
$(document).ready(function () {
    var id = $("#id").val();
    var isFromTc = $("#isFromTc").val();
    var TCForm = document.getElementById("TCForm");
    var BOMForm = document.getElementById("BOMForm");
    if (isFromTc == 0) {
        TCForm.style.display = "none";
        BOMForm.style.display = "block";
    } else if (isFromTc == 1) {
        TCForm.style.display = "block";
        BOMForm.style.display = "none";
    }
    var projectId = $("#project", window.top.document).val();
    var table = "<tr>";
    table += "<th><a href='../change/data/feature/page?projectId=" + projectId + "&orderId=" + id + "'>特性变更</a></th>";
    table += "<th><a href='../change/data/bomCfg/page?projectId=" + projectId + "&orderId=" + id + "'>全BOM配置</a></th>";
    table += "<th><a href='../change/data/modelColorCfg/page?projectId=" + projectId + "&orderId=" + id + "'>配色方案</a></th>";
    table += "<th><a href='../change/data/materielFeature/page?projectId=" + projectId + "&orderId=" + id + "'>配置物料</a></th>";
    table += "<th><a href='../change/data/ebom/page?projectId=" + projectId + "&orderId=" + id + "'>EBOM管理</a></th>";
    table += "<th><a href='../change/data/pbom/page?projectId=" + projectId + "&orderId=" + id + "'>PBOM管理</a></th>";
    table += "<th><a href='../change/data/mbom/page?projectId=" + projectId + "&orderId=" + id + "'>超级MBOM</a></th>";
    table += "<th><a href='../change/data/mbom/page?projectId=" + projectId + "&orderId=" + id + "&type=1" + "'>白车身生产</a></th>";
    table += "<th><a href='../change/data/mbom/page?projectId=" + projectId + "&orderId=" + id + "&type=6" + "'>白车身财务</a></th>";
    table += "<th><a href='../change/data/material/page?projectId=" + projectId + "&orderId=" + id + "'>物料数据</a></th>";
    table += "<th><a href='../change/data/routing/page?projectId=" + projectId + "&orderId=" + id + "'>工艺路线</a></th>";
    table += "</tr>"
    $("#changeOrderTable").html(table);
})

function doReturn() {
    window.location.href = "javascript:history.go(-1);"
}

$(document).ready(function () {
    var changeNo = $("#changeNo").val();
    $.ajax({
        url: "../change/data/tc?formId=" + changeNo,
        type: "GET",
        success: function (result) {
            var data = result.data;
            var temp = "<table >" +
                "<tr><th>零件号</th><th>版本</th></tr>";
            for (var i = 0; i < data.length; i++) {
                temp = temp + "<tr><td>" + data[i].itemId + "</td><td>" + data[i].itemRevision + "</td></tr>"
            }
            temp = temp+"</table>"
            $("#changeTCTable").html(temp);
        }
    })
})
