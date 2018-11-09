
$(document).ready((function () {
    doQuery();
}))

//刷新
function doRefresh(projectUid) {
    initTable(projectUid);
}
function doQuery() {
    initTable(getProjectUid());
}
function formatDate() {
    let startdate = stringToDateFormat($('#startdate').data("time"));
    let enddate = stringToDateFormat($('#enddate').data("time"));
    $('#startdate').val(finishTime);
    $('#enddate').val(vwoEndEffectiveTime);
}
function initTable(projectUid) {
    if (!checkIsSelectProject(projectUid)) {
        return;
    }
    var projectId = $("#project", window.top.document).val();
    var $table = $("#handledTable");
    var column = [];
    // $.ajax({
    //     url: "ebom/title?projectId=" + projectPuid,
    //     type: "GET",
    //     success: function (result) {
    //         var column = [];
    column.push({field: 'ck', checkbox: true, width: 50});
    column.push({
        field: 'changeNum',
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
    column.push({field: 'launchTime', title: '发起时间', align: 'center', valign: 'middle'});
    column.push({field: 'launcherDep', title: '部门', align: 'center', valign: 'middle'});
    column.push({field: 'changeType', title: '变更类型', align: 'center', valign: 'middle'});
    //column.push({field: 'reasonCode', title: '原因类型', align: 'center', valign: 'middle'});
    //column.push({field: 'title', title: '标题', align: 'center', valign: 'middle'});
    column.push({field: 'launcher', title: '流程发起人', align: 'center', valign: 'middle'});
    // var data = result.data;
    // var keys = [];
    // var values;
    // for (var key in data) {
    //     if (data.hasOwnProperty(key)) {
    //         var json = {
    //             field: key,
    //             title: data[key],
    //             align:
    //                 'center',
    //             valign:
    //                 'middle'
    //         };
    //         column.push(json);
    //     }
    // };
    $table.bootstrapTable({
        // ajax: function (request) {
        //     $.ajax({
        //         url: "ewo/base/infoList?projectId=" + projectId,
        //         success: function (result) {
        //             // var data = JSON.stringify(result);
        //             // var msg = JSON.parse(data);
        //             // console.log(msg);
        //             request.success({
        //                 row: result
        //             });
        //             $table.bootstrapTable('load', result);
        //         },
        //         error: function () {
        //             window.Ewin.alert("操作错误")
        //         }
        //     })
        // },
        url: "worklist/base/infoList?projectId=" + projectUid +"&flag=2",
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

    //     }
    // })
}
function queryLou(id) {
    window.location.href="worklist/base/info?id="+id;
}
// function basic() {
//     window.Ewin.dialog({
//         title: '信息',
//         url: 'vwo/vwoFromList',
//         gridId: "gridId",
//         width: 800,
//         height: 500
//     })
// }