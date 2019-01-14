$(document).ready((function () {
    var projectPuid = $("#project", window.top.document).val();
    var pBomUrl = "pbom/getBomManage?projectId=" + projectPuid;
    initTable(pBomUrl);
}))

function doRefresh(projectId) {
    $('#pbomManageTable').bootstrapTable('destroy');
    var pBomUrl = "pbom/getBomManage?projectId=" + projectId;
    initTable(pBomUrl);
}

function doQuery() {
    var projectPuid = $("#project", window.top.document).val();
    var pBomUrl = "pbom/getBomManage?projectId=" + projectPuid;
    var pBomLinePartClass = $("#pBomLinePartClass").val();
    if (pBomLinePartClass == "请选择零件分类") {
        pBomUrl += "&pBomLinePartClass=" + "";
    } else {
        pBomUrl += "&pBomLinePartClass=" + pBomLinePartClass;
    }
    var pBomLinePartResource = $("#pBomLinePartResource").val();
    if (pBomLinePartResource == "请选择零件来源") {
        pBomUrl += "&pBomLinePartResource=" + "";
    }
    else {
        pBomUrl += "&pBomLinePartResource=" + pBomLinePartResource;
    }
    var pIsNewPart = $("#pIsNewPart").val();
    if (pIsNewPart == "请选择工艺合件") {
        pBomUrl += "&pIsNewPart=" + "";
    }
    else {
        pBomUrl += "&pIsNewPart=" + pIsNewPart;
    }
    initTable(pBomUrl);
    $('#pbomManageTable').bootstrapTable('destroy');
}

function initTable(pBomUrl) {
    var $table = $("#pbomManageTable");
    var column = [];
    var projectPuid = $("#project", window.top.document).val();
    if (!checkIsSelectProject(projectPuid)) {
        return;
    }
    $.ajax({
        url: "pbom/manage/title?projectId=" + projectPuid,
        type: "GET",
        success: function (result) {
            var column = [];
            column.push({field: 'ck', checkbox: true, Width: 50});
            var data = result.data;
            var keys = [];
            var values;
            for (var key in data) {
                if (data.hasOwnProperty(key)) {
                    if ('pLouaFlag' === key) {
                        var json = {
                            field: key,
                            title: data[key],
                            align: 'center',
                            valign: 'middle',
                            formatter: function (value, row, index) {
                                if (value == "LOA") {
                                    return [
                                        '<a href="javascript:void(0)" onclick="queryLoa(\'' + row.eBomPuid + '\')">' + value + '</a>'
                                    ].join("");
                                }
                                else if (value == "LOU") {
                                    return [
                                        '<a href="javascript:void(0)" onclick="queryLou(\'' + row.eBomPuid + '\')">' + value + '</a>'
                                    ].join("");
                                }
                                else {
                                    return [
                                        value
                                    ].join("");
                                }
                            }
                        };
                        column.push(json);
                    }
                    else {
                        var json = {
                            field: key,
                            title: data[key],
                            // align: 'center',
                            valign: 'middle',
                            formatter: function (value, row, index) {
                                if (value == "LOA") {
                                    return [
                                        '<a href="javascript:void(0)" onclick="queryLoa(' + row.eBomPuid + ')">' + value + '</a>'
                                    ].join("");
                                }
                                else if (value == "LOU") {
                                    return [
                                        '<a href="javascript:void(0)" onclick="queryLou(\'' + row.eBomPuid + '\')">' + value + '</a>'
                                    ].join("");
                                }
                                else {
                                    return [
                                        value
                                    ].join("");
                                }
                            }
                        };
                        column.push(json);
                    }
                }
            }
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
                url: pBomUrl,
                method: 'GET',
                dataType: 'json',
                cache: false,
                striped: true,                              //是否显示行间隔色
                sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
                height: $(window.parent.document).find("#wrapper").height() - 150,
                width: $(window).width(),
                formId: "queryPbomManage",
                undefinedText: "",//当数据为 undefined 时显示的字符
                pagination: true,
                pageNumber: 1,                       //初始化加载第一页，默认第一页
                pageSize: 20,                       //每页的记录行数（*）
                pageList: ['ALL', 10, 20, 50, 100, 200, 500, 1000],        //可供选择的每页的行数（*）                uniqueId: "puid",                     //每一行的唯一标识，一般为主键列
                columns: column,
                sortable: true,                     //是否启用排序
                sortOrder: "asc",                   //排序方式
                clickToSelect: true,// 单击某一行的时候选中某一条记录
                showColumns: true, //是否显示所有的列
                showToggle: false,                   //是否显示详细视图和列表视图的切换按钮
                showRefresh: true,                  //是否显示刷新按钮
                toolbars: [
                    {
                        text: '同步修改',
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
                            var url = "pbom/updatePbomManage";
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
                                            title: "同步修改",
                                            url: "pbom/updatePbomManage?projectId=" + projectPuid + "&eBomPuid=" + rows[0].eBomPuid+"&puid="+rows[0].puid+"&updateType="+2,
                                            gridId: "gridId",
                                            width: 500,
                                            height: 500
                                        });
                                    }
                                }
                            })
                        }
                    },
                    // {
                    //     text: '当前修改',
                    //     iconCls: 'glyphicon glyphicon-pencil',
                    //     handler: function () {
                    //         var rows = $table.bootstrapTable('getSelections');
                    //         //只能选一条
                    //         if (rows.length != 1) {
                    //             window.Ewin.alert({message: '请选择一条需要修改的数据!'});
                    //             return false;
                    //         }
                    //         else if (rows[0].status == 5 || rows[0].status == 6) {
                    //             window.Ewin.alert({message: '对不起,审核中的数据不能修改!'});
                    //             return false;
                    //         }
                    //         var url = "pbom/updatePbomManage";
                    //         $.ajax({
                    //             url: "privilege/write?url=" + url,
                    //             type: "GET",
                    //             success: function (result) {
                    //                 if (!result.success) {
                    //                     window.Ewin.alert({message: result.errMsg});
                    //                     return false;
                    //                 }
                    //                 else {
                    //                     window.Ewin.dialog({
                    //                         title: "当前修改",
                    //                         url: "pbom/updatePbomManage?projectId=" + projectPuid + "&eBomPuid=" + rows[0].eBomPuid+"&puid="+rows[0].puid+"&updateType="+1,
                    //                         gridId: "gridId",
                    //                         width: 500,
                    //                         height: 500
                    //                     });
                    //                 }
                    //             }
                    //         })
                    //     }
                    // },
                    {
                        text: '删除',
                        iconCls: 'glyphicon glyphicon-remove',
                        handler: function () {
                            var rows = $table.bootstrapTable('getSelections');
                            var puids = "";
                            for (var i = 0; i < rows.length; i++) {
                                puids += rows[i].eBomPuid + ",";
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
                            var url = "pbom/delete";
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
                                            _table += '<tr><td>' + rows[index].lineId + '</td></tr>';
                                        }
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
                                                    url: "pbom/delete",
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
                            var url = "pbom/cancel";
                            $.ajax({
                                url: "privilege/write?url=" + url,
                                type: "GET",
                                success: function (result) {
                                    if (!result.success) {
                                        window.Ewin.alert({message: result.errMsg});
                                        return false;
                                    }
                                    else {
                                        window.Ewin.confirm({
                                            title: '提示',
                                            message: '确定要撤销数据吗?',
                                            width: 500
                                        }).on(function (e) {
                                            if (e) {
                                                $.ajax({
                                                    type: "POST",
                                                    //ajax需要添加打包名
                                                    url: "pbom/cancel",
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
                    {
                        text: '设置为LOU/取消',
                        iconCls: 'glyphicon glyphicon-cog',
                        handler: function () {
                            let rows = $table.bootstrapTable('getSelections');
                            if (rows.length == 0) {
                                window.Ewin.alert({message: '请选择一条需要设置为LOU的数据!'});
                                return false;
                            }
                            if (rows.length != 1) {
                                window.Ewin.alert({message: '请选择一条需要设置为LOU的数据!'});
                                return false;
                            }
                            else if (rows[0].status == 5 || rows[0].status == 6) {
                                window.Ewin.alert({message: '对不起,审核中的数据不能设置为LOU!'});
                                return false;
                            }
                            let puid = rows[0].eBomPuid;
                            let myData = JSON.stringify({
                                "projectId": $("#project", window.top.document).val(),
                                "puid": puid,
                            });

                            var url = "loa/setLou/pBom";
                            $.ajax({
                                url: "privilege/write?url=" + url,
                                type: "GET",
                                success: function (result) {
                                    if (!result.success) {
                                        window.Ewin.alert({message: result.errMsg});
                                        return false;
                                    }
                                    else {
                                        $.ajax({
                                            type: "POST",
                                            //ajax需要添加打包名
                                            url: "loa/setLou/pBom",
                                            data: myData,
                                            contentType: "application/json",
                                            success: function (result) {
                                                if (result.success) {
                                                    layer.msg('设置成功', {icon: 1, time: 2000})
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
                                }
                            })
                        }
                    },
                    {
                        text: '添加工艺辅料',
                        iconCls: 'glyphicon glyphicon-plus',
                        handler: function () {
                            var rows = $table.bootstrapTable('getSelections');
                            //只能选一条
                            if (rows.length != 1) {
                                window.Ewin.alert({message: '请选择一条需要添加工艺辅料的数据!'});
                                return false;
                            }
                            else if (rows[0].status == 5 || rows[0].status == 6) {
                                window.Ewin.alert({message: '对不起,审核中的数据不能添加工艺辅料!'});
                                return false;
                            }
                            var url = "pbom/update/accessories";
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
                                            url: "pbom/update/accessories?projectId=" + projectPuid + "&eBomPuid=" + rows[0].eBomPuid,
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
                        text: '显示子层',
                        iconCls: 'glyphicon glyphicon-eye-open',
                        handler: function () {
                            var rows = $table.bootstrapTable('getSelections');
                            var lineIds = "";
                            for (var i = 0; i < rows.length; i++) {
                                lineIds += rows[i].eBomPuid + ",";
                            }
                            if (rows.length <= 0) {
                                window.Ewin.alert({message: '请至少选择一条需要显示层级的数据!'});
                                return false;
                            }
                            if (this.innerText == '显示子层') {
                                $table.bootstrapTable('destroy');
                                initTable1(pBomUrl, lineIds);
                            }
                            else {
                                $table.bootstrapTable("refresh");
                            }
                            if (this.innerText == '显示子层') {
                                this.innerText = '取消显示子层'
                            }
                            else {
                                this.innerText = '显示子层'
                            }
                        }
                    },
                    {
                        text: '导出Excel',
                        iconCls: 'glyphicon glyphicon-export',
                        handler: function () {
                            //var headers = data;//表头
                            var rows = $table.bootstrapTable('getSelections');//选中行数据
                            if (rows.length == 0) {
                                window.Ewin.alert({message: '请选择一条需要导出的数据!'});
                                return false;
                            } else {
                                for (var index in rows) {
                                    if (rows[index].status == 5 || rows[index].status == 6) {
                                        window.Ewin.alert({message: '勾选的数据有审核中状态，审核中的数据不给导出修改!'});
                                        return false;
                                    }
                                }
                            }
                            window.Ewin.confirm({title: '提示', message: '是否要导出选中行？', width: 500}).on(function (e) {
                                if (e) {
                                    $.ajax({
                                        type: "POST",
                                        //ajax需要添加打包名
                                        url: "./pbom/excelExport",//??????
                                        data: JSON.stringify(rows),
                                        contentType: "application/json",
                                        success: function (result) {
                                            if (result.status) {
                                                layer.msg(result.msg, {icon: 1, time: 2000})

                                                //下载EBOM导入模板
                                                window.location.href = result.path;//V1.1.0.log
                                            }
                                            else {
                                                window.Ewin.alert({message: "操作导出失败:" + result.msg});
                                            }
                                            $table.bootstrapTable("refresh");
                                        },
                                        error: function (info) {
                                            window.Ewin.alert({message: "操作导出:" + info.status});
                                        }
                                    })
                                }
                            });

                        }
                    },
                    {
                        text: '导入Excel',
                        iconCls: 'glyphicon glyphicon-share',
                        handler: function () {
                            var url = "pbom/importExcel";
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
                                            title: "导入",
                                            url: "pbom/importExcel",
                                            width: 600,
                                            height: 500
                                        })
                                    }
                                }
                            })
                        }
                    },
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
                            var url = "pbom/order/choose";
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
                                            url: "pbom/order/choose?projectId=" + projectPuid + "&puids=" + puids,
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
            $table.bootstrapTable('hideColumn', 'level');
            $table.bootstrapTable('hideColumn', 'groupNum');
            $table.bootstrapTable('hideColumn', 'pBomLinePartEnName');
        }
    });
}

function initTable1(pBomUrl, lineIds) {
    var $table = $("#pbomManageTable");
    var column = [];
    var projectPuid = $("#project", window.top.document).val();
    if (!checkIsSelectProject(projectPuid)) {
        return;
    }
    $.ajax({
        url: "pbom/manage/title?project=" + projectPuid,
        type: "GET",
        success: function (result) {
            var column = [];
            column.push({field: 'ck', checkbox: true, Width: 50});
            var data = result.data;
            var keys = [];
            var values;
            for (var key in data) {
                if (data.hasOwnProperty(key)) {
                    if ('pLouaFlag' === key) {
                        var json = {
                            field: key,
                            title: data[key],
                            align: 'center',
                            valign: 'middle',
                            formatter: function (value, row, index) {
                                if (value == "LOA") {
                                    return [
                                        '<a href="javascript:void(0)" onclick="queryLoa(\'' + row.eBomPuid + '\')">' + value + '</a>'
                                    ].join("");
                                }
                                else if (value == "LOU") {
                                    return [
                                        '<a href="javascript:void(0)" onclick="queryLou(\'' + row.eBomPuid + '\')">' + value + '</a>'
                                    ].join("");
                                }
                                else {
                                    return [
                                        value
                                    ].join("");
                                }
                            }
                        };
                        column.push(json);
                    }
                    else {
                        var json = {
                            field: key,
                            title: data[key],
                            // align: 'center',
                            valign: 'middle',
                            formatter: function (value, row, index) {
                                if (value == "LOA") {
                                    return [
                                        '<a href="javascript:void(0)" onclick="queryLoa(' + row.eBomPuid + ')">' + value + '</a>'
                                    ].join("");
                                }
                                else if (value == "LOU") {
                                    return [
                                        '<a href="javascript:void(0)" onclick="queryLou(\'' + row.eBomPuid + '\')">' + value + '</a>'
                                    ].join("");
                                }
                                else {
                                    return [
                                        value
                                    ].join("");
                                }
                            }
                        };
                        column.push(json);
                    }
                }
            }
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
                url: pBomUrl + "&showBomStructure=1" + "&eBomPuids=" + lineIds,
                method: 'GET',
                dataType: 'json',
                cache: false,
                striped: true,                              //是否显示行间隔色
                sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
                height: $(window.parent.document).find("#wrapper").height() - 150,
                width: $(window).width(),
                formId: "queryPbomManage",
                undefinedText: "",//当数据为 undefined 时显示的字符
                pagination: true,
                pageNumber: 1,                       //初始化加载第一页，默认第一页
                pageSize: 20,                       //每页的记录行数（*）
                pageList: ['ALL', 10, 20, 50, 100, 200, 500, 1000],        //可供选择的每页的行数（*）                uniqueId: "puid",                     //每一行的唯一标识，一般为主键列
                showExport: true,
                columns: column,
                sortable: true,                     //是否启用排序
                sortOrder: "asc",                   //排序方式
                clickToSelect: true,// 单击某一行的时候选中某一条记录
                striped: true, //是否显示行间隔色
                showColumns: true, //是否显示所有的列
                showToggle: false,                   //是否显示详细视图和列表视图的切换按钮
                showRefresh: true,                  //是否显示刷新按钮
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
                            var url = "pbom/updatePbomManage";
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
                                            url: "pbom/updatePbomManage?projectId=" + projectPuid + "&eBomPuid=" + rows[0].eBomPuid,
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
                                puids += rows[i].eBomPuid + ",";
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
                            var url = "pbom/delete";
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
                                            _table += '<tr><td>' + rows[index].lineId + '</td></tr>';
                                        }
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
                                                    url: "pbom/delete",
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
                            var url = "pbom/cancel";
                            $.ajax({
                                url: "privilege/write?url=" + url,
                                type: "GET",
                                success: function (result) {
                                    if (!result.success) {
                                        window.Ewin.alert({message: result.errMsg});
                                        return false;
                                    }
                                    else {
                                        window.Ewin.confirm({
                                            title: '提示',
                                            message: '确定要撤销数据吗?',
                                            width: 500
                                        }).on(function (e) {
                                            if (e) {
                                                $.ajax({
                                                    type: "POST",
                                                    //ajax需要添加打包名
                                                    url: "pbom/cancel",
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
                    {
                        text: '设置为LOU/取消',
                        iconCls: 'glyphicon glyphicon-cog',
                        handler: function () {
                            var rows = $table.bootstrapTable('getSelections');
                            if (rows.length == 0) {
                                window.Ewin.alert({message: '请选择一条需要设置为LOU的数据!'});
                                return false;
                            }
                            if (rows.length != 1) {
                                window.Ewin.alert({message: '请选择一条需要设置为LOU的数据!'});
                                return false;
                            }
                            else if (rows[0].status == 5 || rows[0].status == 6) {
                                window.Ewin.alert({message: '对不起,审核中的数据不能设置为LOU!'});
                                return false;
                            }
                            let puid = rows[0].eBomPuid;
                            let myData = JSON.stringify({
                                "projectId": $("#project", window.top.document).val(),
                                "puid": puid,
                            });
                            var url = "loa/setLou/pBom";
                            $.ajax({
                                url: "privilege/write?url=" + url,
                                type: "GET",
                                success: function (result) {
                                    if (!result.success) {
                                        window.Ewin.alert({message: result.errMsg});
                                        return false;
                                    }
                                    else {
                                        $.ajax({
                                            type: "POST",
                                            //ajax需要添加打包名
                                            url: "loa/setLou/pBom",
                                            data: myData,
                                            contentType: "application/json",
                                            success: function (result) {
                                                if (result.success) {
                                                    layer.msg('设置成功', {icon: 1, time: 2000})
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
                                }
                            })
                        }
                    },
                    {
                        text: '添加工艺辅料',
                        iconCls: 'glyphicon glyphicon-plus',
                        handler: function () {
                            var rows = $table.bootstrapTable('getSelections');
                            //只能选一条
                            if (rows.length != 1) {
                                window.Ewin.alert({message: '请选择一条需要添加工艺辅料的数据!'});
                                return false;
                            }
                            else if (rows[0].status == 5 || rows[0].status == 6) {
                                window.Ewin.alert({message: '对不起,审核中的数据不能添加工艺辅料!'});
                                return false;
                            }
                            var url = "pbom/update/accessories";
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
                                            url: "pbom/update/accessories?projectId=" + projectPuid + "&eBomPuid=" + rows[0].eBomPuid,
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
                        text: '取消显示子层',
                        iconCls: 'glyphicon glyphicon-eye-open',
                        handler: function () {
                            if (this.innerText == '取消显示子层') {
                                $table.bootstrapTable('destroy');
                                initTable(pBomUrl);
                            }
                            else {
                                $table.bootstrapTable("refresh");
                            }
                            if (this.innerText == '显示子层') {
                                this.innerText = '取消显示子层'
                            }
                            else {
                                this.innerText = '显示子层'
                            }
                        }
                    },
                    {
                        text: '导出Excel',
                        iconCls: 'glyphicon glyphicon-export',
                        handler: function () {
                            var rows = $table.bootstrapTable('getSelections');//选中行数据
                            if (rows.length == 0) {
                                window.Ewin.alert({message: '请选择一条需要导出的数据!'});
                                return false;
                            } else {
                                for (var index in rows) {
                                    if (rows[index].status == 5 || rows[index].status == 6) {
                                        window.Ewin.alert({message: '勾选的数据有审核中状态，审核中的数据不给导出修改!'});
                                        return false;
                                    }
                                }
                            }
                            window.Ewin.confirm({title: '提示', message: '是否要导出选中行？', width: 500}).on(function (e) {
                                if (e) {
                                    $.ajax({
                                        type: "POST",
                                        //ajax需要添加打包名
                                        url: "./pbom/excelExport",//??????
                                        data: JSON.stringify(rows),
                                        contentType: "application/json",
                                        success: function (result) {
                                            if (result.status) {
                                                layer.msg(result.msg, {icon: 1, time: 2000})

                                                //下载EBOM导入模板
                                                window.location.href = result.path;//V1.1.0.log
                                            }
                                            else {
                                                window.Ewin.alert({message: "操作导出失败:" + result.msg});
                                            }
                                            $table.bootstrapTable("refresh");
                                        },
                                        error: function (info) {
                                            window.Ewin.alert({message: "操作导出:" + info.status});
                                        }
                                    })
                                }
                            });

                        }
                    },
                    {
                        text: '导入Excel',
                        iconCls: 'glyphicon glyphicon-share',
                        handler: function () {
                            var url = "pbom/importExcel";
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
                                            title: "导入",
                                            url: "pbom/importExcel",
                                            width: 600,
                                            height: 500
                                        })
                                    }
                                }
                            })
                        }
                    },
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
                            var url = "pbom/order/choose";
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
                                            url: "pbom/order/choose?projectId=" + projectPuid + "&puids=" + puids,
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
            $table.bootstrapTable('hideColumn', 'level');
            $table.bootstrapTable('hideColumn', 'groupNum');
            $table.bootstrapTable('hideColumn', 'pBomLinePartEnName');
        }
    });
}

function lookLevel(result, pBomUrl) {

}

function queryLoa(row) {
    var myData = JSON.stringify({
        "projectId": $("#project", window.top.document).val(),
        "puid": row
    });
    $.ajax({
        type: "POST",
        //ajax需要添加打包名
        url: "loa/pbom",
        data: myData,
        contentType: "application/json",
        success: function (result) {
            var child = result.data.child;
            var parent = result.data.parent;
            var parentLevel = (parent.parentLevel == undefined ? "" : parent.parentLevel);
            var parentLineId = (parent.parentLineId == undefined ? "" : parent.parentLineId);
            var parentName = (parent.parentName == undefined ? "" : parent.parentName);
            var _table = '<div style="max-height: 400px;overflow:scroll;"><table class="table table-striped tableNormalStyle" >';
            _table += '<tr><td>父层级</td><td>父零件号</td><td>父名称</td></tr>'
            // for (var i=0;i<parent.length; i++) {
            _table += '<tr><td>' + parentLevel + '</td><td>' + parentLineId + '</td><td>' + parentName + '</td></tr>';
            // }
            _table += '</table></div>' + '<div style="max-height: 400px;overflow:scroll;"><table class="table table-striped tableNormalStyle" >';
            _table += '<tr><td>子层级</td><td>子零件号</td><td>子名称</td></tr>'
            for (var i = 0; i < child.length; i++) {
                _table += '<tr><td>' + child[i].childLevel + '</td><td>' + child[i].childLineId + '</td><td>' + child[i].childName + '</td></tr>';
            }
            _table += '</table></div>';
            window.Ewin.confirm({title: '提示', message: _table, width: 500});
        }
    })
}

function queryLou(row) {
    var projectId = $("#project", window.top.document).val();
    $.ajax({
        type: "GET",
        //ajax需要添加打包名
        url: "loa/getLou/pBom?projectId=" + projectId + "&puid=" + row,
        undefinedText: "",
        success: function (result) {
            var data = result.data;
            var parent = data.parent;
            var config = data.config;
            var child = data.child;
            var parentLevel = (parent.parentLevel == undefined ? "" : parent.parentLevel);
            var parentLineId = (parent.parentLineId == undefined ? "" : parent.parentLineId);
            var parentName = (parent.parentName == undefined ? "" : parent.parentName);
            var pCfg0name = (config.pCfg0name == undefined ? "" : config.pCfg0name);
            var cfg0Desc = (config.cfg0Desc == undefined ? "" : config.cfg0Desc);
            var pCfg0familyname = (config.pCfg0familyname == undefined ? "" : config.pCfg0familyname);
            var cfg0FamilyDesc = (config.cfg0FamilyDesc == undefined ? "" : config.cfg0FamilyDesc);
            var _table = '<div style="max-height: 400px;overflow:scroll;"><table class="table table-striped tableNormalStyle" >';
            _table += '<tr><td>父层级</td><td>父零件号</td><td>父名称</td></tr>'
            // for (var i=0;i<parent.length; i++) {
            _table += '<tr><td>' + parentLevel + '</td><td>' + parentLineId + '</td><td>' + parentName + '</td></tr>';
            _table += '</table></div>' + '<div style="max-height: 400px;overflow:scroll;"><table class="table table-striped tableNormalStyle" >';
            _table += '<tr><td>配置名</td><td>特性值描述</td><td>族名</td><td>特性描述</td></tr>'
            _table += '<tr><td>' + pCfg0name + '</td><td>' + cfg0Desc + '</td><td>' + pCfg0familyname + '</td><td>' + cfg0FamilyDesc + '</td></tr>';
            _table += '</table></div>' + '<div style="max-height: 400px;overflow:scroll;"><table class="table table-striped tableNormalStyle" >';
            _table += '<tr><td>子层级</td><td>子零件号</td><td>子名称</td></tr>'
            for (var i = 0; i < child.length; i++) {
                _table += '<tr><td>' + child[i].childLevel + '</td><td>' + child[i].childLineId + '</td><td>' + child[i].childName + '</td></tr>';
            }
            _table += '</table></div>';
            window.Ewin.confirm({title: '提示', message: _table, width: 500});
        }
    })
}

function toPage() {
    var pageNum = $("#pageNum").val();
    if (pageNum) {
        $('#pbomManageTable').bootstrapTable('selectPage', parseInt(pageNum));
    }
}

$(document).keydown(function (event) {
    if (event.keyCode == 13) {
        $('form').each(function () {
            event.preventDefault();
        });
    }
});