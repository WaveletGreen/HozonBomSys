/**
 * Created with IntelliJ IDEA.
 * User: xlf
 * Date: 2018/11/9
 * Time: 9:17
 */

$(document).ready((function () {
    var projectId = $("#project", window.top.document).val();
    var url = "change/order/list?projectId=" + projectId;
    initTable(url);
}))

//刷新
function doRefresh(projectUid) {
    $('#changeFormTable').bootstrapTable('destroy');
    var url = "change/order/list?projectId=" + projectUid;
    initTable(url);
}

function doQuery() {
    $('#changeFormTable').bootstrapTable('destroy');
    var projectId = $("#project", window.top.document).val();
    var url = "change/order/list?projectId=" + projectId;
    var state = $("#state").val();
    if (state == "请选择变更单状态") {
        url += "&state=" + "";
    } else {
        url += "&state=" + state;
    }
    initTable(url);

}

function formatDate() {
    let startdate = stringToDateFormat($('#startdate').data("time"));
    let enddate = stringToDateFormat($('#enddate').data("time"));
    $('#startdate').val(startdate);
    $('#enddate').val(enddate);
}

function initTable(url) {
    var projectId = $("#project", window.top.document).val();
    if (!checkIsSelectProject(projectId)) {
        return;
    }
    var $table = $("#changeFormTable");
    var column = [];
    column.push({field: 'ck', checkbox: true, width: 50});
    column.push({
        field: '',
        title: '序号',
        align: 'center',
        width: 50,
        formatter: function (value, row, index) {
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
                var id = row.id
                return [
                    // '<a href="ewo/base/info?id='+id +'">' + value + '</a>'
                    '<a href="javascript:void(0)" onclick="queryLou(' + id + ')">' + value + '</a>'
                ].join("");
            }
        });
    column.push({field: 'createTime', title: '创建时间', align: 'center', valign: 'middle'});
    column.push({field: 'originTime', title: '流程发起时间', align: 'center', valign: 'middle'});
    column.push({field: 'deptName', title: '部门', align: 'center', valign: 'middle'});
    column.push({field: 'originator', title: '发起人', align: 'center', valign: 'middle'});
    column.push({field: 'createName', title: '表单创建者', align: 'center', valign: 'middle'});
    column.push({field: 'tel', title: '联系电话', align: 'center', valign: 'middle'});
    column.push({field: 'state', title: '变更单状态', align: 'center', valign: 'middle'});
    column.push({field: 'changeType', title: '变更类型', align: 'center', valign: 'middle'});
    column.push({field: 'relationChangeNo', title: '是否关联变更', align: 'center', valign: 'middle'});
    column.push({field: 'marketType', title: '上市类型', align: 'center', valign: 'middle'});
    column.push({field: 'projectStage', title: '项目所属阶段', align: 'center', valign: 'middle'});
    column.push({field: 'remark', title: '备注', align: 'center', valign: 'middle'});
    column.push({field: 'resource', title: '来源', align: 'center', valign: 'middle'});
    $table.bootstrapTable({
        url: url,
        method: 'get',
        height: $(window.parent.document).find("#wrapper").height() - 200,
        width: $(window).width(),
        showToggle: false,                   //是否显示详细视图和列表视图的切换按钮
        showRefresh: true,                  //是否显示刷新按钮
        pagination: true,                   //是否显示分页（*）
        pageSize: 20,
        pageNumber: 1,
        pageList: [/*'ALL', */20, 50, 100, 200, 500, 1000],        //可供选择的每页的行数（*）
        sidePagination: "server",          //分页方式：client客户端分页，server服务端分页（*）
        clickToSelect: true,                // 单击某一行的时候选中某一条记录
        showExport: false,
        formId: "queryChangeForm",
        columns: column,                     //列信息，需要预先定义好
        sortable: false,                     //是否启用排序
        sortOrder: "asc",                   //排序方式
        striped: true,                      //是否显示行间隔色
        search: false,                      //是否显示表格搜索，此搜索是客户端搜索，不会进服务端
        showColumns: false,                 //是否显示所有的列
        toolbars: [
            {
                text: '添加',
                iconCls: 'glyphicon glyphicon-plus',
                handler: function () {
                    var url = "change/addPage";
                    $.ajax({
                        url: "privilege/write?url=" + url,
                        type: "GET",
                        success: function (result) {
                            if (!result.success) {
                                window.Ewin.alert({message: result.errMsg});
                                return false;
                            }
                            else {
                                window.Ewin.dialog({
                                    title: "添加",
                                    url: "change/addPage",
                                    gridId: "gridId",
                                    width: 500,
                                    height: 500
                                })
                            }
                        }
                    })
                }
            },
            {
                text: '修改',
                iconCls: 'glyphicon glyphicon-pencil',
                handler: function () {
                    var rows = $table.bootstrapTable('getSelections');
                    // 只能选一条
                    if (rows.length != 1) {
                        window.Ewin.alert({message: '请选择一条需要修改的数据!'});
                        return false;
                    }
                    var url = "change/updatePage";
                    $.ajax({
                        url: "privilege/write?url=" + url,
                        type: "GET",
                        success: function (result) {
                            if (!result.success) {
                                window.Ewin.alert({message: result.errMsg});
                                return false;
                            }
                            else {
                                window.Ewin.dialog({
                                    title: "修改",
                                    url: "change/updatePage?id=" + rows[0].id,
                                    gridId: "gridId",
                                    width: 500,
                                    height: 500
                                });
                            }
                        }
                    })
                }
            },
            {
                text: '删除',
                iconCls: 'glyphicon glyphicon-remove',
                handler: function () {
                    var rows = $table.bootstrapTable('getSelections');
                    if (rows.length != 1) {
                        window.Ewin.alert({message: '请选择一条需要删除的数据!'});
                        return false;
                    }
                    var url = "change/delete";
                    $.ajax({
                        url: "privilege/write?url=" + url,
                        type: "GET",
                        success: function (result) {
                            if (!result.success) {
                                window.Ewin.alert({message: result.errMsg});
                                return false;
                            }
                            else {
                                window.Ewin.confirm({title: '提示', message: "确定要删除数据么", width: 500}).on(function (e) {
                                    if (e) {
                                        $.ajax({
                                            type: "DELETE",
                                            //ajax需要添加打包名
                                            url: "change/delete?id=" + rows[0].id,
                                            success: function (result) {
                                                if (result.success) {
                                                    layer.msg('删除成功', {icon: 1, time: 2000})
                                                }
                                                else if (!result.success) {
                                                    window.Ewin.alert({message: result.errMsg})
                                                }
                                                $table.bootstrapTable("refresh");
                                            },
                                            error: function (info) {
                                                window.Ewin.alert({message: "操作删除:" + info.status});
                                            }
                                        })
                                    }
                                });
                            }
                        }
                    })
                }
            },
            {
                text: '发起流程',
                iconCls: 'glyphicon glyphicon-log-out',
                handler: function () {
                    var rows = $table.bootstrapTable('getSelections');
                    if (rows.length == 0 || rows.length != 1) {
                        window.Ewin.alert({message: '请选择<span style="color: red">一条</span>变更表单发起流程!'});
                        return false;
                    }
                    for (let i in rows) {
                        if ("进行中" != rows[i].state) {
                            if ("流程中" == rows[i].state) {
                                window.Ewin.alert({message: '表单已在流程中，不允许再发起流程'});
                            } else {
                                window.Ewin.alert({message: '表单已审核完成'});
                            }
                            return;
                        }
                    }
                    // 判断表单是否有关联数据 无关联数据不允许发起流程
                    var relatedOrderUrl = "change/related/data?orderId="+rows[0].id;
                    var url = "process/getAuditorPage";
                    $.ajax({
                        url:  relatedOrderUrl,
                        type: "GET",
                        success: function (result) {
                            if (!result.success) {
                                window.Ewin.alert({message: result.errMsg});
                                return false;
                            }
                            else {
                                $.ajax({
                                    url: "privilege/write?url=" + url,
                                    type: "GET",
                                    success: function (result) {
                                        if (!result.success) {
                                            window.Ewin.alert({message: result.errMsg});
                                            return false;
                                        }
                                        else {
                                            window.Ewin.dialog({
                                                title: "选择审核人",
                                                url: "process/getAuditorPage?orderId="+rows[0].id,
                                                gridId: "getAuditorPage",
                                                width: 500,
                                                height: 500
                                            });
                                        }
                                    }
                                });
                            }
                        }
                    })

                    // var url = "process/getAuditorPage";
                    // $.ajax({
                    //     url: "privilege/write?url=" + url,
                    //     type: "GET",
                    //     success: function (result) {
                    //         if (!result.success) {
                    //             window.Ewin.alert({message: result.errMsg});
                    //             return false;
                    //         }
                    //         else {
                    //             window.Ewin.dialog({
                    //                 title: "选择审核人",
                    //                 url: "process/getAuditorPage?orderId="+rows[0].id,
                    //                 gridId: "getAuditorPage",
                    //                 width: 500,
                    //                 height: 500
                    //             });
                    //         }
                    //     }
                    // })
                }
            },
        ],
    });
}

//@Modified by Fancyears·Maylos·Malvis in 2018/11/22 13:30  获取选中行
function getRows() {
    return $("#changeFormTable").bootstrapTable('getSelections');
}

function queryLou(id) {
    window.location.href = "change/ToChangeOrder?id=" + id;
}