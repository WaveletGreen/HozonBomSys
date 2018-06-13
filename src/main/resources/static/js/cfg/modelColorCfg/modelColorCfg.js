var firstLoad = true;

function modeVehicle(puid) {
    // $.ajax({
    //
    // })
}

$(document).ready(
    // $("#queryModelColorCfg").click(function () {
    (function () {
        //必须输入一个配置的puid
        var projectPuid = $("#project", window.top.document).val();
        if (projectPuid.length <= 0) {
            $("#myModal").modal('show');
            return;
        }
        var $table = $("#modelColorCfgTable");
        $table.bootstrapTable('destroy');
        var column = [];
        $("#refresh").removeAttr("disabled");
        $.ajax({
            url: "modelColor/getColumn?projectPuid=" + projectPuid,
            type: "GET",
            success: function (result) {
                if (!result.status) {
                    alert("查无数据，请联系管理员");
                    return;
                }
                var data = result.data;
                var column = [];
                column.push({field: 'puid', title: 'puid'});
                column.push({field: 'modeColorIsMultiply', title: 'modeColorIsMultiply'});
                column.push({field: 'ck', checkbox: true, Width: 50});
                column.push({field: 'codeOfColorModel', title: '车型颜色代码', align: 'center', valign: 'middle'});
                column.push({field: 'descOfColorModel', title: '描述', align: 'center', valign: 'middle'});
                column.push({
                    field: 'modelShell',
                    title: '油漆车身总成',
                    align: 'center',
                    valign: 'middle',
                    formatter: function (value, row, index) {
                        var rowValues = JSON.parse(JSON.stringify(row));
                        if ("Y" === rowValues.modeColorIsMultiply) {
                            return [
                                '<a href="javascript:void(0)" onclick="modeVehicle(' + row.puid + ')">' + value + '</a>'
                            ].join("");
                            z
                        }
                        else {
                            return [
                                value
                            ].join("");
                        }
                    }
                });
                for (var i = 0; i < data.length; i++) {
                    var josn = {
                        field: "s" + i,
                        title:
                            data[i],
                        align:
                            'center',
                        valign:
                            'middle'
                    };
                    column.push(josn);
                }
                $table.bootstrapTable({
                    url: "modelColor/loadAll?projectPuid=" + projectPuid,
                    method: 'get',
                    height: $(window.parent.document).find("#wrapper").height() - document.body.offsetHeight - 45,
                    width: $(window).width(),
                    showToggle: true,                   //是否显示详细视图和列表视图的切换按钮
                    showRefresh: true,                  //是否显示刷新按钮
                    pageSize: 10,
                    pagination: true,                   //是否显示分页（*）
                    clickToSelect: true,                // 单击某一行的时候选中某一条记录
                    formId: "hide",
                    /**列信息，需要预先定义好*/
                    columns: column,
                    // sortable: true,                     //是否启用排序
                    // sortOrder: "asc",                   //排序方式
                    toolbars: [
                        {
                            text: '添加',
                            iconCls: 'glyphicon glyphicon-plus',
                            handler: function () {
                                window.Ewin.dialog({
                                    title: "添加",
                                    url: "modelColor/addPage?projectPuid=" + projectPuid,
                                    gridId: "gridId",
                                    width: 500,
                                    height: 600
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
                                window.Ewin.dialog({
                                    title: "修改",
                                    url: "modelColor/modifyPage?puid=" + rows[0].puid,
                                    gridId: "gridId",
                                    width: 350,
                                    height: 450
                                });
                            }
                        },
                        {
                            text: '删除',
                            iconCls: 'glyphicon glyphicon-remove',
                            handler: function () {
                                var rows = $table.bootstrapTable('getSelections');
                                if (rows.length == 0) {
                                    window.Ewin.alert({message: '请选择一条需要删除的数据!'});
                                    return false;
                                }
                                //测试数据
                                window.Ewin.confirm({
                                    title: '提示',
                                    message: '是否要删除您所选择的记录？',
                                    width: 500
                                }).on(function (e) {
                                    if (e) {
                                        $.ajax({
                                            type: "POST",
                                            //ajax需要添加打包名
                                            url: "./modelColor/delete",
                                            data: JSON.stringify(rows),
                                            contentType: "application/json",
                                            success: function (result) {
                                                if (result) {
                                                    window.Ewin.alert({message: "删除时数据成功"});
                                                    //刷新，会重新申请数据库数据
                                                }
                                                else {
                                                    window.Ewin.alert({message: "操作删除失败:" + result.msg});
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
                    ]
                });
                $table.bootstrapTable('hideColumn', 'puid');
                $table.bootstrapTable('hideColumn', 'modeColorIsMultiply');
            }
        });

    }),
    //手动刷新按钮
    $("#refresh").click(function () {
        $('#modelColorCfgTable').bootstrapTable('refresh');
    })
);