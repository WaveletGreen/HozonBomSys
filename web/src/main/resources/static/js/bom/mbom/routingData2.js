var type;
$(document).ready((function () {
    var projectId = $("#project", window.top.document).val();
    var url = "work/process/record/page2?projectId=" + projectId;
    initTable(url);
}))

function doRefresh(projectId) {
    $('#routingDataTable').bootstrapTable('destroy');
    var url = "work/process/record/page2?projectId=" + projectId;
    initTable(url);
}

function doQuery() {
    var projectId = $("#project", window.top.document).val();
    var url = "work/process/record/page2?projectId=" + projectId;
    type = $("#type").val();
    if (type == "请选择工艺路线") {
        url += "&type=" + "";
    }
    else {
        url += "&type=" + type;
    }
    initTable(url);
    $('#routingDataTable').bootstrapTable('destroy');
}


function initTable(url) {
    var projectId = $("#project", window.top.document).val();
    if (!checkIsSelectProject(projectId)) {
        return;
    }
    var $table = $("#routingDataTable");
    var column = [];
    $.ajax({
        url: "work/process/title",
        type: "GET",
        success: function (result) {
            var column = [];
            column.push({field: 'ck', checkbox: true, Width: 50});
            var data = result.data;
            var keys = [];
            var values;
            for (var key in data) {
                if (data.hasOwnProperty(key)) {
                    var json = {
                        field: key,
                        title: data[key],
                        // align:
                        //     'center',
                        valign:
                            'middle'
                    };
                    column.push(json);
                }
            }
            ;
            column.push({
                field: 'status',
                title: '变更状态',
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
                cache: false,
                striped: true,                              //是否显示行间隔色
                sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
                height: $(window.parent.document).find("#wrapper").height() - 150,
                width: $(window).width(),
                formId: "queryRoutingData",
                undefinedText: "",//当数据为 undefined 时显示的字符
                pagination: true,
                pageNumber: 1,                       //初始化加载第一页，默认第一页
                pageSize: 20,                       //每页的记录行数（*）
                pageList: ['ALL', 10, 20, 50, 100, 200, 500, 1000],        //可供选择的每页的行数（*）                uniqueId: "puid",                     //每一行的唯一标识，一般为主键列
                // showExport: true,
                columns: column,
                sortable: true,                     //是否启用排序
                sortOrder: "asc",                   //排序方式
                clickToSelect: true,// 单击某一行的时候选中某一条记录
                // striped: true, //是否显示行间隔色
                showColumns: true, //是否显示所有的列
                showToggle: false,                   //是否显示详细视图和列表视图的切换按钮
                showRefresh: true,                  //是否显示刷新按钮
                showExport: phoneOrPc(),              //是否显示导出按钮(此方法是自己写的目的是判断终端是电脑还是手机,电脑则返回true,手机返回falsee,手机不显示按钮)
                exportDataType: "selected",              //basic', 'all', 'selected'.
                exportTypes: ['xlsx'],	    //导出类型
                //exportButton: $('#btn_export'),     //为按钮btn_export  绑定导出事件  自定义导出按钮(可以不用)
                exportOptions: {
                    //ignoreColumn: [0,0],            //忽略某一列的索引
                    fileName: '工艺路线导出',              //文件名称设置
                    worksheetName: 'Sheet1',          //表格工作区名称
                    tableName: '工艺路线表',
                    excelstyles: ['background-color', 'color', 'font-size', 'font-weight'],
                    //onMsoNumberFormat: DoOnMsoNumberFormat
                },
                toolbars: [
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
                            var url = "work/process/updateWorkProcess2";
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
                                            url: "work/process/updateWorkProcess2?projectId=" + projectId + "&materielId=" + rows[0].materielId + "&procedureDesc=" + rows[0].pProcedureDesc,
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
                        text: '修改四大工艺',
                        iconCls: 'glyphicon glyphicon-pencil',
                        handler: function () {
                            var rows = $table.bootstrapTable('getSelections');
                            if (type != "1" && type != "2") {
                                window.Ewin.alert({message: '请先选择工艺路线'})
                                return false;
                            }
                            else if (rows[0].status == 5 || rows[0].status == 6) {
                                window.Ewin.alert({message: '对不起,审核中的数据不能修改!'});
                                return false;
                            }
                            for (var i = 0; i < rows.length; i++) {
                                for (var j = i + 1; j < rows.length; j++) {
                                    if (rows[i].materielId == rows[j].materielId) {
                                        window.Ewin.alert({message: '请不要选择重复物料!'});
                                        return false;
                                    }
                                }
                            }
                            var puids = "";
                            var processDescList = "";
                            for (var i = 0; i < rows.length; i++) {
                                puids += rows[i].materielId + ",";
                                processDescList += rows[i].pProcedureDesc + ",";
                            }
                            if (rows.length < 1) {
                                window.Ewin.alert({message: '请至少选择一条需要修改的数据!'});
                                return false;
                            }
                            var url = "work/process/four2"
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
                                            title: "修改四大工艺",
                                            url: "work/process/four2?projectId=" + projectId + "&puids=" + puids + "&type=" + type + "&processDescs=" + processDescList,
                                            gridId: "gridId",
                                            contentType: "application/json",
                                            width: 350,
                                            height: 300,
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
                            var materielIds = [];
                            var procedureDesc = [];
                            if (rows.length == 0) {
                                window.Ewin.alert({message: '请选择一条需要删除的数据!'});
                                return false;
                            }
                            else if (rows[0].status == 5 || rows[0].status == 6) {
                                window.Ewin.alert({message: '对不起,审核中的数据不能删除!'});
                                return false;
                            }
                            var url = "work/process/delete2";
                            $.ajax({
                                url: "privilege/write?url=" + url,
                                type: "GET",
                                success: function (result) {
                                    if (!result.success) {
                                        window.Ewin.alert({message: result.errMsg});
                                        return false;
                                    }
                                    else {
                                        var _table = '<p>是否要删除您所选择的记录？</p>' +
                                            '<div style="max-height: 400px;overflow:scroll;"><table class="table table-striped tableNormalStyle" >';
                                        for (var index in rows) {
                                            var pMaterielDesc = (rows[index].pMaterielDesc == undefined ? "" : rows[index].pMaterielDesc);
                                            var pMaterielDesc = (rows[index].pMaterielDesc == null ? "" : rows[index].pMaterielDesc);
                                            _table += '<tr><td>' + pMaterielDesc + '</td></tr>';
                                            materielIds.push(rows[index].materielId);
                                            procedureDesc.push(rows[index].pProcedureDesc);
                                        }
                                        var data = {};
                                        data["materielIds"] = materielIds;
                                        data["procedureDesc"] = procedureDesc;
                                        _table += '</table></div>';
                                        window.Ewin.confirm({
                                            title: '提示',
                                            message: _table,
                                            width: 500
                                        }).on(function (e) {
                                            if (e) {
                                                $.ajax({
                                                    type: "POST",
                                                    //ajax需要添加打包名
                                                    url: "work/process/delete2",
                                                    data: JSON.stringify(data),
                                                    contentType: "application/json",
                                                    success: function (result) {
                                                        if (result.success) {
                                                            layer.msg('删除成功', {icon: 1, time: 2000})
                                                        }
                                                        else if (!result.success) {
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
                        text: '撤销',
                        iconCls: 'glyphicon glyphicon-share-alt',
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
                                window.Ewin.alert({message: '请选择需要撤销的数据!'});
                                return false;
                            }
                            else {
                                for (var i = 0; i < rows.length; i++) {
                                    if (rows[i].status != 4 && rows[i].status != 2) {
                                        window.Ewin.alert({message: '撤销功能只能选择状态为草稿状态或删除状态的数据!'});
                                        return false;
                                    }
                                }
                            }
                            var url = "work/process/cancel"
                            $.ajax({
                                url: "privilege/write?url=" + url,
                                type: "GET",
                                success: function (result) {
                                    if (!result.success) {
                                        window.Ewin.alert({message: result.errMsg});
                                        return false;
                                    }
                                    else {
                                        window.Ewin.confirm({title: '提示', message: '确定要撤销数据吗?', width: 500}).on(function (e) {
                                            if (e) {
                                                $.ajax({
                                                    type: "POST",
                                                    //ajax需要添加打包名
                                                    url: "work/process/cancel",
                                                    data: myData,
                                                    contentType: "application/json",
                                                    success: function (result) {
                                                        if (result.success) {
                                                            layer.msg('撤销成功', {icon: 1, time: 2000})
                                                        } else if (!result.success) {
                                                            window.Ewin.alert({message: result.errMsg});
                                                        }
                                                        $table.bootstrapTable("refresh");
                                                    },
                                                    error: function (info) {
                                                        window.Ewin.alert({message: ":" + info.status});
                                                    }
                                                })
                                            }
                                        });
                                    }
                                }
                            })
                        }
                    },
                    // {
                    //     text: '发送MBOM到SAP',
                    //     iconCls: 'glyphicon glyphicon-remove',
                    //     handler: function () {
                    //         var rows = $table.bootstrapTable('getSelections');
                    //         var projectId = $("#project", window.top.document).val();
                    //         if (rows.length == 0) {
                    //             window.Ewin.alert({message: '请选择一条需要发送的数据!'});
                    //             return false;
                    //         }
                    //         else if (rows[0].status == 5 || rows[0].status == 6) {
                    //             window.Ewin.alert({message: '对不起,审核中的数据不能发送!'});
                    //             return false;
                    //         }
                    //         var _table = '<p>是否要发送您所选择的记录？</p>' +
                    //             '<div style="max-height: 400px;overflow:scroll;"><table class="table table-striped tableNormalStyle" >';
                    //         for (var index in rows) {
                    //             _table += '<tr><td>' + rows[index].pMaterielDesc + '</td></tr>';
                    //         }
                    //         _table += '</table></div>';
                    //         window.Ewin.confirm({title: '提示', message: _table, width: 500}).on(function (e) {
                    //             if (e) {
                    //                 var materielIds = new Array();
                    //                 for (var i = 0; i < rows.length; i++) {
                    //                     materielIds[i] = rows[i].materielId;
                    //                 }
                    //                 $.ajax({
                    //                     type: "POST",
                    //                     //ajax需要添加打包名
                    //                     url: "work/process/submit?projectId=" + projectId + "&materielIds=" + materielIds,
                    //                     success: function (result) {
                    //                         if (result.success) {
                    //                             layer.msg('提交成功', {icon: 1, time: 2000})
                    //                         }
                    //                         else if (!result.success) {
                    //                             window.Ewin.alert({message: result.errMsg});
                    //                         }
                    //                         $table.bootstrapTable("refresh");
                    //                     },
                    //                     error: function (info) {
                    //                         window.Ewin.alert({message: "操作提交:" + info.status});
                    //                     }
                    //                 })
                    //             }
                    //         });
                    //     }
                    // },
                    // {
                    //     text: '删除MBOM到SAP',
                    //     iconCls: 'glyphicon glyphicon-remove',
                    //     handler: function () {
                    //         var rows = $table.bootstrapTable('getSelections');
                    //         var projectId = $("#project", window.top.document).val();
                    //         if (rows.length == 0) {
                    //             window.Ewin.alert({message: '请选择一条需要发送的数据!'});
                    //             return false;
                    //         }
                    //         else if (rows[0].status == 5 || rows[0].status == 6) {
                    //             window.Ewin.alert({message: '对不起,审核中的数据不能发送!'});
                    //             return false;
                    //         }
                    //         var _table = '<p>是否要发送您所选择的记录？</p>' +
                    //             '<div style="max-height: 400px;overflow:scroll;"><table class="table table-striped tableNormalStyle" >';
                    //         for (var index in rows) {
                    //             _table += '<tr><td>' + rows[index].pMaterielDesc + '</td></tr>';
                    //         }
                    //         _table += '</table></div>';
                    //         window.Ewin.confirm({title: '提示', message: _table, width: 500}).on(function (e) {
                    //             if (e) {
                    //                 var materielIds = new Array();
                    //                 for (var i = 0; i < rows.length; i++) {
                    //                     materielIds[i] = rows[i].materielId;
                    //                 }
                    //                 $.ajax({
                    //                     type: "POST",
                    //                     //ajax需要添加打包名
                    //                     url: "work/process/delete1?projectId=" + projectId + "&materielIds=" + materielIds,
                    //                     success: function (result) {
                    //                         if (result.success) {
                    //                             layer.msg('提交成功', {icon: 1, time: 2000})
                    //                         }
                    //                         else if (!result.success) {
                    //                             window.Ewin.alert({message: result.errMsg});
                    //                         }
                    //                         $table.bootstrapTable("refresh");
                    //                     },
                    //                     error: function (info) {
                    //                         window.Ewin.alert({message: "操作提交:" + info.status});
                    //                     }
                    //                 })
                    //             }
                    //         });
                    //     }
                    // },
                    // {
                    //     text: '修改MBOM到SAP',
                    //     iconCls: 'glyphicon glyphicon-remove',
                    //     handler: function () {
                    //         var rows = $table.bootstrapTable('getSelections');
                    //         var projectId = $("#project", window.top.document).val();
                    //         if (rows.length == 0) {
                    //             window.Ewin.alert({message: '请选择一条需要发送的数据!'});
                    //             return false;
                    //         }
                    //         else if (rows[0].status == 5 || rows[0].status == 6) {
                    //             window.Ewin.alert({message: '对不起,审核中的数据不能发送!'});
                    //             return false;
                    //         }
                    //         var _table = '<p>是否要发送您所选择的记录？</p>' +
                    //             '<div style="max-height: 400px;overflow:scroll;"><table class="table table-striped tableNormalStyle" >';
                    //         for (var index in rows) {
                    //             _table += '<tr><td>' + rows[index].pMaterielDesc + '</td></tr>';
                    //         }
                    //         _table += '</table></div>';
                    //         window.Ewin.confirm({title: '提示', message: _table, width: 500}).on(function (e) {
                    //             if (e) {
                    //                 var materielIds = new Array();
                    //                 for (var i = 0; i < rows.length; i++) {
                    //                     materielIds[i] = rows[i].materielId;
                    //                 }
                    //                 $.ajax({
                    //                     type: "POST",
                    //                     //ajax需要添加打包名
                    //                     url: "work/process/updata?projectId=" + projectId + "&materielIds=" + materielIds,
                    //                     success: function (result) {
                    //                         if (result.success) {
                    //                             layer.msg('提交成功', {icon: 1, time: 2000})
                    //                         }
                    //                         else if (!result.success) {
                    //                             window.Ewin.alert({message: result.errMsg});
                    //                         }
                    //                         $table.bootstrapTable("refresh");
                    //                     },
                    //                     error: function (info) {
                    //                         window.Ewin.alert({message: "操作提交:" + info.status});
                    //                     }
                    //                 })
                    //             }
                    //         });
                    //     }
                    // },
                    {
                        text: '关联变更单',
                        iconCls: 'glyphicon glyphicon-log-out',
                        handler: function () {
                            var rows = $table.bootstrapTable('getSelections');
                            var puids = "";
                            for (var i = 0; i < rows.length; i++) {
                                puids += rows[i].puid + ",";
                            }
                            if (rows.length == 0) {
                                window.Ewin.alert({message: '请选择需要变更的数据!'});
                                return false;
                            }
                            else {
                                for (var i = 0; i < rows.length; i++) {
                                    if (rows[i].status != 4 && rows[i].status != 2) {
                                        window.Ewin.alert({message: '只能选择状态为草稿状态或删除状态的数据发起流程!'});
                                        return false;
                                    }
                                }
                            }
                            var url = "work/process/order/choose";
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
                                            url: "work/process/order/choose?projectId=" + projectId + "&puids=" + puids,
                                            gridId: "gridId",
                                            width: 450,
                                            height: 450
                                        });
                                    }
                                }
                            })
                        }
                    },
                ],
            });
        }
    })
}

function toPage() {
    var pageNum = $("#pageNum").val();
    if (pageNum) {
        $('#routingDataTable').bootstrapTable('selectPage', parseInt(pageNum));
    }
}

$(document).keydown(function (event) {
    if (event.keyCode == 13) {
        $('form').each(function () {
            event.preventDefault();
        });
    }
});