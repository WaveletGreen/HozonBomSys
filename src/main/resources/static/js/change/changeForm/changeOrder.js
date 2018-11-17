/**
 * Created with IntelliJ IDEA.
 * User: xlf
 * Date: 2018/11/14
 * Time: 9:26
 */
$(document).ready(function () {
    var id = $("#id").val();
    var projectId = $("#project", window.top.document).val();
    var table = "<tr>";
    table +="<th><a href='../change/texing?projectId="+projectId+"&id="+id+"'>特性变更</a></th>";
    table +="<th><a href=''>全BOM配置</a></th>";
    table +="<th><a href=''>配色方案</a></th>";
    table +="<th><a href=''>配置物料</a></th>";
    table +="<th><a href='../change/data/ebom/page?projectId="+projectId+"&orderId="+id+"'>EBOM管理</a></th>";
    table +="<th><a href=''>PBOM管理</a></th>";
    table +="<th><a href=''>超级MBOM</a></th>";
    table +="<th><a href=''>白车身生产</a></th>";
    table +="<th><a href=''>白车身财务</a></th>";
    table +="<th><a href=''>物料数据</a></th>";
    table +="<th><a href=''>工艺路线</a></th>";
    table +="</tr>"
    $("#changeOrderTable").html(table) ;
})