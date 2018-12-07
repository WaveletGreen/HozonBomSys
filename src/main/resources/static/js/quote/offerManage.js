/**
 * Created with IntelliJ IDEA.
 * User: xlf
 * Date: 2018/12/6
 * Time: 10:21
 */

$(document).ready((function () {
    var projectPuid = $("#project", window.top.document).val();
    var url = ""
    initTable(url);
}))

function doRefresh(projectPuid) {
    $('#offerManageTable').bootstrapTable('destroy');
    var url = ""
    initTable(url);
}

function doQuery() {
    var projectPuid = $("#project", window.top.document).val();
    var url = ""
    initTable(url);
    $('#offerManageTable').bootstrapTable('destroy');
}

function initTable(url) {
    var projectPuid = $("#project", window.top.document).val();
    if (!checkIsSelectProject(projectPuid)) {
        return;
    }
    var $table = $("#offerManageTable");
    var column = [];
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
        column.push({field: 'status', title: '零件号', align: 'center', valign: 'middle',})
    column.push({field: 'status', title: '零件名称', align: 'center', valign: 'middle',})
    column.push({field: 'status', title: '每车数量', align: 'center', valign: 'middle',})
    column.push({field: 'status', title: '中选供应商', align: 'center', valign: 'middle',})
    column.push({field: 'status', title: '零部件(不含税)', align: 'center', valign: 'middle',})
    column.push({field: 'status', title: '单车零部件价格(不含税)', align: 'center', valign: 'middle',})
    column.push({field: 'status', title: '模具费(不含税)', align: 'center', valign: 'middle',})
    column.push({field: 'status', title: '模具费(含税)', align: 'center', valign: 'middle',})
    column.push({field: 'status', title: '模具费付费情况', align: 'center', valign: 'middle',})
    column.push({field: 'status', title: '检具费(不含税)', align: 'center', valign: 'middle',})
    column.push({field: 'status', title: '开发费(不含税)', align: 'center', valign: 'middle',})
    column.push({field: 'status', title: 'A样件', align: 'center', valign: 'middle',})
    column.push({field: 'status', title: 'B样件', align: 'center', valign: 'middle',})
    column.push({field: 'status', title: 'C样件', align: 'center', valign: 'middle',})
    column.push({field: 'status', title: '供应商降本计划', align: 'center', valign: 'middle',})
    column.push({field: 'status', title: '备注', align: 'center', valign: 'middle',})
    column.push({field: 'status', title: '专业', align: 'center', valign: 'middle',})
    column.push({
        field: 'status',
        title: '状态',
        align: 'center',
        valign: 'middle',

        formatter: function (value, row, index) {
            if (value == 1 || "1" == value) {
                return "<span style='color: #00B83F'>已生效</span>";
            }
            if (value == 2 || "2" == value) {
                return "<span style='color: #ff7cf4'>草稿状态</span>";
            }
            if (3 == value || "3" == value) {
                return "<span style='color: #9492a9'>废除状态</span>";
            }
            if (4 == value || "4" == value) {
                return "<span style='color: #a90009'>删除状态</span>";
            }
            if (value == 5 || value == "5") {
                return "<span style='color: #e2ab2f'>审核中</span>"
            }
            if (value == 6 || value == "6") {
                return "<span style='color: #e2ab2f'>审核中</span>"
            }
        }
    })
    $table.bootstrapTable({
        url: url,
        method: 'GET',
        dataType: 'json',
        sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
        height: $(window.parent.document).find("#wrapper").height() - 150,
        width: $(window).width(),
        formId: "queryOfferManage",
        undefinedText: "",//当数据为 undefined 时显示的字符
        pagination: true,
        pageNumber: 1,                       //初始化加载第一页，默认第一页
        pageSize: 20,                       //每页的记录行数（*）
        pageList: ['ALL', 10, 20, 50, 100, 200, 500, 1000],        //可供选择的每页的行数（*）
        columns: column,
        sortOrder: "asc",                   //排序方式
        clickToSelect: true,// 单击某一行的时候选中某一条记录
        showColumns: true, //是否显示所有的列
        showToggle: false,                   //是否显示详细视图和列表视图的切换按钮
        showRefresh: true,                  //是否显示刷新按钮
        toolbars: [
            {
                text: '添加',
                iconCls: 'glyphicon glyphicon-plus',
                handler: function () {
                    var url = "";
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
                                    url: "",
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
                    //只能选一条
                    if (rows.length != 1) {
                        window.Ewin.alert({message: '请选择一条需要修改的数据!'});
                        return false;
                    }
                    else if (rows[0].status == 5 || rows[0].status == 6) {
                        window.Ewin.alert({message: '对不起,审核中的数据不能修改!'});
                        return false;
                    }
                    var url = "";
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
                                    url: "",
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
                    var puids = "";
                    for (var i = 0; i < rows.length; i++) {
                        puids += rows[i].puid + ",";
                    }
                    var myData = JSON.stringify({
                        "projectId": $("#project", window.top.document).val(),
                        "puids": puids,
                    });
                    if (rows.length == 0) {
                        window.Ewin.alert({message: '请至少选择一条需要删除的数据!'});
                        return false;
                    }
                    else if (rows[0].status == 5 || rows[0].status == 6) {
                        window.Ewin.alert({message: '对不起,审核中的数据不能删除!'});
                        return false;
                    }
                    var url = "";
                    $.ajax({
                        url: "privilege/write?url=" + url,
                        type: "GET",
                        success: function (result) {
                            if (!result.success) {
                                window.Ewin.alert({message: result.errMsg});
                                return false;
                            }
                            else {
                                // var _table = '<p>是否要删除您所选择的记录？</p>' +
                                //     '<div style="max-height: 400px;overflow:scroll;"><table class="table table-striped tableNormalStyle" >';
                                // for (var index in rows) {
                                //     _table += '<tr><td>' + rows[index].lineId + '</td></tr>';
                                // }
                                // _table += '</table></div>';
                                window.Ewin.confirm({
                                    title: '提示',
                                    message: '确定要删除数据吗?',
                                    width: 500
                                }).on(function (e) {
                                    if (e) {
                                        $.ajax({
                                            type: "POST",
                                            //ajax需要添加打包名
                                            url: "",
                                            data: myData,
                                            contentType: "application/json",
                                            success: function (result) {
                                                if (result.success) {
                                                    layer.msg('删除成功', {icon: 1, time: 2000})
                                                } else if (!result.success) {
                                                    window.Ewin.alert({message: result.errMsg});
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
                    var puids = "";
                    for (var i = 0; i < rows.length; i++) {
                        puids += rows[i].puid + ",";
                    }
                    if (rows.length == 0) {
                        window.Ewin.alert({message: '请选择一条需要变更的数据!'});
                        return false;
                    }
                    else {
                        for (var i = 0; i < rows.length; i++) {
                            if (rows[i].status != 4 && rows[i].status != 2) {
                                window.Ewin.alert({message: '请选择状态为草稿状态或删除状态的数据!'});
                                return false;
                            }
                        }
                    }
                    var url = "";
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
                                    title: "选择变更表单",
                                    url: "",
                                    gridId: "gridId",
                                    width: 450,
                                    height: 450
                                });
                            }
                        }
                    })
                }
            },
        ]
    });
}