/**
 * Created with IntelliJ IDEA.
 * User: xlf
 * Date: 2018/12/6
 * Time: 10:21
 */

var toolbar = [
    {
        text: '添加',
        iconCls: 'glyphicon glyphicon-plus',
        handler: function () {
            var projectPuid = $("#project", window.top.document).val();
            window.Ewin.dialog({
                title: "添加",
                url: "chosenSupplier/addPage?projectPuid="+projectPuid,
                gridId: "gridId",
                width: 500,
                height: 500
            })
            // var url = "";
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
            //                 title: "添加",
            //                 url: "",
            //                 gridId: "gridId",
            //                 width: 500,
            //                 height: 500
            //             })
            //         }
            //     }
            // })
        }
    },
    {
        text: '修改',
        iconCls: 'glyphicon glyphicon-pencil',
        handler: function () {
            var $table = $("#offerManageTable");
            var rows = $table.bootstrapTable('getSelections');
            //只能选一条
            if (rows.length != 1) {
                window.Ewin.alert({message: '请选择一条需要修改的数据!'});
                return false;
            }
            var url = "";
            var projectPuid = $("#project", window.top.document).val();
            window.Ewin.dialog({
                title: "修改",
                url: "chosenSupplier/updatePage?projectPuid="+projectPuid+"&id="+rows[0].id,
                gridId: "gridId",
                width: 500,
                height: 500
            });
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
            //                 title: "修改",
            //                 url: "",
            //                 gridId: "gridId",
            //                 width: 500,
            //                 height: 500
            //             });
            //         }
            //     }
            // })
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
    }
]
$(document).ready((function () {
    initTable();
    $("#queryBtn").click(function () {
        initTable();
    })
}))

function doQuery() {
    $('#offerManageTable').bootstrapTable('refresh');    //刷新表格
}

function doRefresh(projectPuid) {
    $('#offerManageTable').bootstrapTable('destroy');
    var url = ""
    initTable(url);
}


function initTable() {
    var column = [];
    column.push({
        field: 'ck',
        checkbox: true
    })
    column.push({
        field: '',
        title: '序号',
        align: 'center',
        width: 50,
        formatter: function (value, row, index) {
            var options = $table.bootstrapTable('getOptions');
            if(options.pageSize="ALL"){
                return index+1;
            }
            return options.pageSize * (options.pageNumber - 1) + index + 1;

        }
    })
    column.push({field: 'itemId', title: '零件号', align: 'center', valign: 'middle',})
    column.push({field: 'itemName', title: '零件名称', align: 'center', valign: 'middle',})
    column.push({field: 'eachCarQuantity', title: '每车数量', align: 'center', valign: 'middle',})
    column.push({field: 'chosenSupplier', title: '中选供应商', align: 'center', valign: 'middle',})
    column.push({field: 'parts', title: '零部件(不含税)', align: 'center', valign: 'middle',})
    column.push({field: 'singleCarPrice', title: '单车零部件价格(不含税)', align: 'center', valign: 'middle',})
    column.push({field: 'moldsCostNotRevenue', title: '模具费(不含税)', align: 'center', valign: 'middle',})
    column.push({field: 'moldsCostHasRevenue', title: '模具费(含税)', align: 'center', valign: 'middle',})
    column.push({field: 'moldsCostDetails', title: '模具费付费情况', align: 'center', valign: 'middle',})
    column.push({field: 'gaugeCost', title: '检具费(不含税)', align: 'center', valign: 'middle',})
    column.push({field: 'exploitationCost', title: '开发费(不含税)', align: 'center', valign: 'middle',})
    column.push({field: 'aSamplePiece', title: 'A样件', align: 'center', valign: 'middle',})
    column.push({field: 'bSamplePiece', title: 'B样件', align: 'center', valign: 'middle',})
    column.push({field: 'cSamplePiece', title: 'C样件', align: 'center', valign: 'middle',})
    column.push({field: 'csLowerCostingoPlan', title: '供应商降本计划', align: 'center', valign: 'middle',})
    column.push({field: 'remark', title: '备注', align: 'center', valign: 'middle',})
    column.push({field: 'specialty', title: '专业', align: 'center', valign: 'middle',})
    var projectPuid = $("#project", window.top.document).val();
    if (!checkIsSelectProject(projectPuid)) {
        return;
    }
    var $table = $("#offerManageTable");
    $table.bootstrapTable('destroy');
    $table.bootstrapTable({
        url: "chosenSupplier/loadChosenSupplier?projectPuid="+projectPuid,
        method: "GET",
        // queryParams: queryParams,
        height: $(window.parent.document).find("#wrapper").height() - 150,// $(window.parent.document).find("#wrapper").height() - document.body.offsetHeight - 100,
        width: $(window).width(),
        showToggle: true,                   //是否显示详细视图和列表视图的切换按钮
        showColumns: true,                  //是否显示所有的列
        showRefresh: true,                  //是否显示刷新按钮
        pagination: true,                   //是否显示分页（*）
        pageNumber: 1,                       //初始化加载第一页，默认第一页
        pageSize: 20,                       //每页的记录行数（*）
        pageList: ['ALL',20, 30, 50, 100, 500, 1000],//可供选择的每页的行数（*）
        smartDisplay: false,
        clickToSelect: true,                // 单击某一行的时候选中某一条记录
        formId: "queryOfferManage",
        toolbars: toolbar,
        /**列信息，需要预先定义好*/
        columns: column,
        sortable: true,                     //是否启用排序
        sortOrder: "asc",                   //排序方式
        sortName: 'ITEM_ID',
    });
}