var firstLoad = true;
$(document).ready(
    $("#query").click(function () {
        var $table = $("#dataTable");
        $table.bootstrapTable('destroy');
        $("#refresh").removeAttr("disabled");
        var projectPuid=$("#project", window.top.document).val();
        $table.bootstrapTable({
            url: "cfg0/loadFeature?projectPuid="+   projectPuid,
            method:"GET",
            height: $(window.parent.document).find("#wrapper").height() - 252,
            width: $(window).width(),
            showToggle: true,                   //是否显示详细视图和列表视图的切换按钮
            showColumns: true,                  //是否显示所有的列
            showRefresh: true,                  //是否显示刷新按钮
            pageSize: 10,
            pagination: true,                   //是否显示分页（*）
            clickToSelect: true,                // 单击某一行的时候选中某一条记录
            formId: "hide",
            toolbars: [
                {
                    text: '添加',
                    iconCls: 'glyphicon glyphicon-plus',
                    handler: function () {
                        window.Ewin.dialog({
                            title: "添加",
                            url: "colorSet/addPage",
                            gridId: "gridId",
                            width: 350,
                            height: 500
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
                            url: "colorSet/update?puid=" + rows[0].puid,
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
                        window.Ewin.confirm({title: '提示', message: '是否要删除您所选择的记录？', width: 500}).on(function (e) {
                            if (e) {
                                $.ajax({
                                    type: "POST",
                                    //ajax需要添加打包名
                                    url: "./colorSet/delete",
                                    data: JSON.stringify(rows),
                                    contentType: "application/json",
                                    success: function (result) {
                                        if (result.status) {
                                            window.Ewin.alert({message: result.msg});
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
            ],
            /**列信息，需要预先定义好*/
            columns: [
                {
                    field: 'ck',
                    checkbox: true
                },
                {
                    field: 'pCfg0FamilyName',
                    title: '特性名称',
                },
                {
                    field: 'pCfg0FamilyDesc',
                    title: '特性描述',
                    align: 'center',
                    valign: 'middle',
                },
                {
                    field: 'pH9featureenname',
                    title: '特性英文名称',
                    align: 'center',
                    valign: 'middle',
                },
                {
                    field: 'length',
                    title: '字符长度',
                    align: 'center',
                    valign: 'middle',
                },
                {
                    field: 'pCfg0ObjectId',
                    title: '特性值/配置代码',
                    align: 'center',
                    valign: 'middle',
                },
                {
                    field: 'pCfg0Desc',
                    title: '特性值/配置(描述)',
                    align: 'center',
                    valign: 'middle',
                },
                {
                    field: 'puid',
                    title: 'puid',
                    hide: false
                }
            ]
            // sortable: true,                     //是否启用排序
            // sortOrder: "asc",                   //排序方式
        });
        $table.bootstrapTable('hideColumn', 'puid');
    }),
    //手动刷新按钮
    $("#refresh").click(function () {
        $('#dataTable').bootstrapTable('refresh');
    })
);