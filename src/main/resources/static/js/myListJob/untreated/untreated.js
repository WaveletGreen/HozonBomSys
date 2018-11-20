/**
 * Created with IntelliJ IDEA.
 * User: xlf
 * Date: 2018/11/8
 * Time: 15:17
 */

$(document).ready((function () {
    var url = "untreated/infoList";
    initTable(url);
    initListener();
}))

function doQuery() {
    var url = "untreated/infoList";
    initTable(url);
    $('#untreatedTable').bootstrapTable('destroy');
}

function formatDate() {
    let firstOriginTime = stringToDateFormat($('#firstOriginTime').data("time"));
    let lastOriginTime = stringToDateFormat($('#lastOriginTime').data("time"));
    $('#firstOriginTime').val(firstOriginTime);
    $('#lastOriginTime').val(lastOriginTime);
}

function initTable(url) {
    var $table = $("#untreatedTable");
    var column = [];
    column.push({field: 'ck', checkbox: true, width: 50});
    column.push({
        field: 'changeNo',
        title: '变更单号',
        align: 'center',
        valign: 'middle',
        formatter: function (value, row, index) {
            var id = row.id
            return [
                '<a href="javascript:void(0)" onclick="queryLou(' + id + ')">' + value + '</a>'
            ].join("");
        }
    });
    column.push({field: 'originTime', title: '发起时间', align: 'center', valign: 'middle'});
    column.push({field: 'deptName', title: '部门', align: 'center', valign: 'middle'});
    column.push({field: 'changeType', title: '变更类型', align: 'center', valign: 'middle'});
    column.push({field: 'originator', title: '流程发起人', align: 'center', valign: 'middle'});
    column.push({field: 'originator', title: '项目', align: 'center', valign: 'middle'});
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
        formId: "queryUntreated",
        columns: column,                     //列信 息，需要预先定义好
        sortable: false,                     //是否启用排序
        sortOrder: "asc",                   //排序方式
        striped: true,                      //是否显示行间隔色
        search: false,                      //是否显示表格搜索，此搜索是客户端搜索，不会进服务端
        showColumns: false,                 //是否显示所有的列

    });
}

function queryLou(id) {
    window.location.href = "untreated/ToUntreatedForm?id=" + id;
}

/**
 *添加监听
 */
function initListener() {
    $("#saveBtn").click(function () {
        saveAgreement();
    })
}

function saveAgreement() {
    let data = {};
    var _d = $("#bomLeaderOpinion").serializeArray();
    for (var p in _d) {
        data[_d[p].name] = _d[p].value;
    }
    data.id = $("#id").val();
    log(data);
    $.ajax({
        contentType:
            "application/json",
        type:
            'POST',
        url: "../process/doCheck",
        data: JSON.stringify(data),
        success:
            function (result) {
                activeTabBodyRefresh();
                if (result.status) {
                    layer.msg(result.msg, {icon: 1, time: 2000})
                }
                else {
                    window.Ewin.alert({message: result.msg});
                }
            },
        error: function (status) {
            window.Ewin.alert({message: "提交失败:" + status.status});
        }
    })
}