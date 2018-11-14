/**
 * Created with IntelliJ IDEA.
 * User: xlf
 * Date: 2018/11/13
 * Time: 11:15
 */

$(document).ready((function () {
    var projectId = $("#project", window.top.document).val();
    var url = "ewo/base/infoList?projectId="+projectId;
    initTable(url);
}))

//刷新
// function doRefresh(projectUid) {
//     initTable(projectUid);
// }
function doQuery() {
    // initTable(getProjectUid());
    var projectId = $("#project", window.top.document).val();
    var url = "ewo/base/infoList?projectId="+projectId;
    initTable(url);
    $('#myApplicationTable').bootstrapTable('destroy');
}

function formatDate() {
    let startdate = stringToDateFormat($('#startdate').data("time"));
    let enddate = stringToDateFormat($('#enddate').data("time"));
    $('#startdate').val(finishTime);
    $('#enddate').val(vwoEndEffectiveTime);
}

function initTable(url) {
    // if (!checkIsSelectProject(projectUid)) {
    //     return;
    // }
    // var projectId = $("#project", window.top.document).val();
    var $table = $("#myApplicationTable");
    var column = [];
    // $.ajax({
    //     url: "ebom/title?projectId=" + projectPuid,
    //     type: "GET",
    //     success: function (result) {
    //         var column = [];
    column.push({field: 'ck', checkbox: true, width: 50});
    column.push({
        field: 'ewoNo',
        title: '变更单号',
        align: 'center',
        valign: 'middle',
        formatter: function (value, row, index) {
            var id = row.id
            return [
                // '<a href="ewo/base/info?id='+id +'">' + value + '</a>'
                '<a href="javascript:void(0)" onclick="queryLou(' + id + ')">' + value + '</a>'
            ].join("");
        }
    });
    column.push({field: 'formCreateTime', title: '发起时间', align: 'center', valign: 'middle'});
    column.push({field: 'dept', title: '部门', align: 'center', valign: 'middle'});
    column.push({field: 'changeType', title: '变更类型', align: 'center', valign: 'middle'});
    column.push({field: 'reasonCode', title: '原因类型', align: 'center', valign: 'middle'});
    column.push({field: 'title', title: '标题', align: 'center', valign: 'middle'});
    column.push({field: 'originator', title: '流程发起人', align: 'center', valign: 'middle'});
    column.push({field: 'originator', title: '项目', align: 'center', valign: 'middle'})
    $table.bootstrapTable({
        url: url,
        method: 'get',
        height: $(window.parent.document).find("#wrapper").height() - 90,
        width: $(window).width(),
        showToggle: false,                   //是否显示详细视图和列表视图的切换按钮
        showRefresh: true,                  //是否显示刷新按钮
        pagination: true,                   //是否显示分页（*）
        pageSize: 20,
        pageNumber: 1,
        pageList: ['ALL', 20, 50, 100, 200, 500, 1000],        //可供选择的每页的行数（*）
        sidePagination: "server",          //分页方式：client客户端分页，server服务端分页（*）
        clickToSelect: true,                // 单击某一行的时候选中某一条记录
        showExport: false,
        formId: "formId",
        columns: column,                     //列信息，需要预先定义好
        sortable: false,                     //是否启用排序
        sortOrder: "asc",                   //排序方式
        striped: true,                      //是否显示行间隔色
        search: false,                      //是否显示表格搜索，此搜索是客户端搜索，不会进服务端
        showColumns: false,                 //是否显示所有的列
    });

}

function queryLou(id) {
    window.location.href = "myApplication/ToMyApplicationForm?id=" + id;
}