/**
 * Created with IntelliJ IDEA.
 * User: xlf
 * Date: 2018/11/13
 * Time: 11:15
 */

$(document).ready((function () {
    var url = "myApplication/infoList";
    initTable(url);
}))

function doQuery() {
    $('#myApplicationTable').bootstrapTable('destroy');
    var url = "myApplication/infoList";
    initTable(url);
}

function formatDate() {
    let firstOriginTime = stringToDateFormat($('#firstOriginTime').data("time"));
    let lastOriginTime = stringToDateFormat($('#lastOriginTime').data("time"));
    $('#firstOriginTime').val(firstOriginTime);
    $('#lastOriginTime').val(lastOriginTime);
}

function initTable(url) {
    var $table = $("#myApplicationTable");
    var column = [];
    column.push({
        field: '',
        title: '序号',
        align: 'center',
        width: 50,
        formatter: function (value, row, index) {
            //return index+1;
            // var temp = $('#changeFormTable').bootstrapTable("getIndex");//返回（pageSize * (pageNumber-1) + 1）
            // return temp + index;
            var options = $table.bootstrapTable('getOptions');
            return options.pageSize * (options.pageNumber - 1) + index + 1;

        }
    }),
        column.push({
            field: 'changeNo',
            title: '变更单号',
            align: 'center',
            valign: 'middle',
            formatter: function (value, row, index) {
                var id = row.id;
                var auditId = row.auditId;
                // return [
                //     '<a href="javascript:void(0)" onclick="queryLou(' + id + ')">' + value + '</a>'
                // ].join("");
                return [
                    '<a href="javascript:void(0)" onclick="queryLou(\'' + id+ '\',+\'' + auditId+'\')">' + value + '</a>'
                ].join("");
            }
        });
    column.push({field: 'originTime', title: '发起时间', align: 'center', valign: 'middle'});
    column.push({field: 'auditTime', title: '审批时间', align: 'center', valign: 'middle'});
    column.push({field: 'deptName', title: '部门', align: 'center', valign: 'middle'});
    column.push({field: 'changeType', title: '变更类型', align: 'center', valign: 'middle'});
    column.push({field: 'state', title: '变更单状态', align: 'center', valign: 'middle'});
    column.push({field: 'originator', title: '流程发起人', align: 'center', valign: 'middle'});
    column.push({field: 'createName', title: '表单创建者', align: 'center', valign: 'middle'});
    column.push({field: 'projectName', title: '项目', align: 'center', valign: 'middle'});
    column.push({field: 'source', title: '来源', align: 'center', valign: 'middle'});
    $table.bootstrapTable({
        url: url,
        method: 'get',
        height: $(window.parent.document).find("#wrapper").height() - 170,
        width: $(window).width(),
        showToggle: false,                   //是否显示详细视图和列表视图的切换按钮
        showRefresh: true,                  //是否显示刷新按钮
        pagination: true,                   //是否显示分页（*）
        pageSize: 20,
        pageNumber: 1,
        pageList: [/*'ALL',*/ 20, 50, 100, 200, 500, 1000],        //可供选择的每页的行数（*）
        sidePagination: "server",          //分页方式：client客户端分页，server服务端分页（*）
        clickToSelect: true,                // 单击某一行的时候选中某一条记录
        showExport: false,
        formId: "queryMyApplication",
        columns: column,                     //列信息，需要预先定义好
        sortable: false,                     //是否启用排序
        sortOrder: "asc",                   //排序方式
        striped: true,                      //是否显示行间隔色
        search: false,                      //是否显示表格搜索，此搜索是客户端搜索，不会进服务端
        showColumns: false,                 //是否显示所有的列
    });

}

function queryLou(id,auditId) {
    //window.location.href = "myApplication/ToMyApplicationForm?id=" + id;
    window.location.href = "myApplication/ToMyApplicationForm?id=" + id+"&auditId="+auditId;
}