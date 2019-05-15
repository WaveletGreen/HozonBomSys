/**
 * Created with IntelliJ IDEA.
 * User: xlf
 * Date: 2018/12/6
 * Time: 10:21
 */
var countFlag = true;
var column = [];
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
            var $table = $("#offerManageTable");
            var rows = $table.bootstrapTable('getSelections');
            var ids = "";
            for (var i = 0; i < rows.length; i++) {
                ids += rows[i].id + ",";
            }
            if (rows.length == 0) {
                window.Ewin.alert({message: '请至少选择一条需要删除的数据!'});
                return false;
            }
            $.ajax({
                type: "GET",
                //ajax需要添加打包名
                url: "chosenSupplier/delete?ids=" + ids,
                success: function (result) {
                    if (result.status) {
                        layer.msg('删除成功', {icon: 1, time: 2000})
                    } else if (!result.status) {
                        window.Ewin.alert({message: result.msg});
                    }
                    $table.bootstrapTable("refresh");
                },
                error: function (info) {
                    window.Ewin.alert({message: "操作删除:" + info.status});
                }
            })
        }
    },
    // {
    //     text : "统计",
    //     iconCls: 'glyphicon glyphicon-remove',
    //     handler: function () {
    //         var $table = $("#offerManageTable");
    //         var rows = $table.bootstrapTable('getSelections');
    //
    //         var parts = 0.0;
    //         var singleCarPrice = 0.0;
    //         var moldsCostNotRevenue = 0.0;
    //         var moldsCostHasRevenue = 0.0;
    //         var gaugeCost = 0.0;
    //         var exploitationCost = 0.0;
    //         for(i in rows ){
    //             if(rows[i].itemId=="统计"){
    //                 continue;
    //             }
    //             parts = addNum(parts,rows[i].parts);
    //             singleCarPrice = addNum(singleCarPrice,rows[i].singleCarPrice);
    //             moldsCostNotRevenue = addNum(moldsCostNotRevenue,rows[i].moldsCostNotRevenue);
    //             moldsCostHasRevenue = addNum(moldsCostHasRevenue,rows[i].moldsCostHasRevenue);
    //             gaugeCost = addNum(gaugeCost,rows[i].gaugeCost);
    //             exploitationCost = addNum(exploitationCost,rows[i].exploitationCost);
    //         }
    //         var data = {"itemId" : "统计", "itemName" : "", "eachCarQuantity" : "","chosenSupplier": "","parts" : parts,"singleCarPrice":singleCarPrice,"moldsCostNotRevenue":moldsCostNotRevenue,
    //             "moldsCostHasRevenue":moldsCostHasRevenue,"moldsCostDetails":"","gaugeCost":gaugeCost,"exploitationCost":exploitationCost,"aSamplePiece":"","bSamplePiece":"","cSamplePiece":"","csLowerCostingoPlan":"",
    //             "remark":"","specialty":""};
    //
    //         if(countFlag){
    //             // $("#offerManageTable").bootstrapTable("insertRow",{index:0,row:data});
    //             $("#offerManageTable").bootstrapTable("append",data);
    //             countFlag = false;
    //         }else {
    //             // $('#offerManageTable').bootstrapTable('updateRow', {index: 0, row: data});
    //             var count = $('#offerManageTable').bootstrapTable('getData').length;
    //             $('#offerManageTable').bootstrapTable('updateRow', {index: count-1, row: data});
    //         }
    //     }
    // },
    {
        text: '整车配置管理',
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
        }
    }
]
$(document).ready((function () {
    countFlag = true;
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
    var columnTitle1 = [];
    var columnTitle2 = [];
    columnTitle1.push({
        field: 'ck',
        colspan: 1, rowspan: 2,
        checkbox: true
    })
    columnTitle1.push({
        field: '',
        title: '序号',
        align: 'center',
        width: 50,
        colspan: 1, rowspan: 2,
        formatter: function (value, row, index) {
            var options = $table.bootstrapTable('getOptions');
            if(options.pageSize="ALL"){
                return index+1;
            }
            return options.pageSize * (options.pageNumber - 1) + index + 1;

        }
    })
    columnTitle1.push({field: 'itemId', title: '零件号', align: 'center', valign: 'middle',colspan: 1, rowspan: 2,})
    columnTitle1.push({field: 'itemName', title: '零件名称', align: 'center', valign: 'middle',colspan: 1, rowspan: 2,})
    columnTitle1.push({field: 'eachCarQuantity', title: '每车数量', align: 'center', valign: 'middle',colspan: 1, rowspan: 2,})
    columnTitle1.push({field: 'eachCarQuantity', title: '整车配置', align: 'center', valign: 'middle',colspan: 1, rowspan: 1,})
    columnTitle1.push({field: 'eachCarQuantity', title: '三级科目', align: 'center', valign: 'middle',colspan: 1, rowspan: 2,})
    columnTitle1.push({field: 'chosenSupplier', title: '中选供应商', align: 'center', valign: 'middle',colspan: 1, rowspan: 2,})
    columnTitle1.push({field: 'parts', title: '零部件单价目标(不含税)', align: 'center', valign: 'middle',colspan: 1, rowspan: 2,})
    columnTitle1.push({field: 'parts', title: '零部件单价(不含税)', align: 'center', valign: 'middle',colspan: 1, rowspan: 2,})
    columnTitle1.push({field: 'singleCarPrice', title: '单车零部件目标(不含税)', align: 'center', valign: 'middle',colspan: 1, rowspan: 2,})
    columnTitle1.push({field: 'singleCarPrice', title: '单车零部件价格(不含税)', align: 'center', valign: 'middle',colspan: 1, rowspan: 2,})
    columnTitle1.push({field: 'singleCarPrice', title: 'WBS号', align: 'center', valign: 'middle',colspan: 1, rowspan: 2,})
    columnTitle1.push({field: 'moldsCostNotRevenue', title: '模具费', align: 'center', valign: 'middle',colspan: 4, rowspan: 1,})
    columnTitle1.push({field: 'moldsCostHasRevenue', title: '检具费', align: 'center', valign: 'middle',colspan: 4, rowspan: 1,})
    columnTitle1.push({field: 'exploitationCost', title: '开发费', align: 'center', valign: 'middle',colspan: 4, rowspan: 1,})
    columnTitle1.push({field: 'aSamplePiece', title: '手工件目标（不含税）', align: 'center', valign: 'middle',colspan: 1, rowspan: 2,})
    columnTitle1.push({field: 'bSamplePiece', title: '手工件协议单价（不含税）', align: 'center', valign: 'middle',colspan: 1, rowspan: 2,})
    columnTitle1.push({field: 'cSamplePiece', title: 'CNC件目标（不含税）', align: 'center', valign: 'middle',colspan: 1, rowspan: 2,})
    columnTitle1.push({field: 'csLowerCostingoPlan', title: 'CNC件协议单价（不含税）', align: 'center', valign: 'middle',colspan: 1, rowspan: 2,})
    columnTitle1.push({field: 'remark', title: '3D打印件目标（不含税）', align: 'center', valign: 'middle',colspan: 1, rowspan: 2,})
    columnTitle1.push({field: 'remark1', title: '3D打印件协议单价（不含税）', align: 'center', valign: 'middle',colspan: 1, rowspan: 2,})
    columnTitle1.push({field: 'remark2', title: '软（硅胶）模件目标（不含税）', align: 'center', valign: 'middle',colspan: 1, rowspan: 2,})
    columnTitle1.push({field: 'remark3', title: '软（硅胶）模件协议单价（不含税）', align: 'center', valign: 'middle',colspan: 1, rowspan: 2,})
    columnTitle1.push({field: 'remark4', title: '钢膜件目标（不含税）', align: 'center', valign: 'middle',colspan: 1, rowspan: 2,})
    columnTitle1.push({field: 'remark5', title: '钢膜件协议单价（不含税）', align: 'center', valign: 'middle',colspan: 1, rowspan: 2,})
    columnTitle1.push({field: 'remark6', title: '半工装件目标（不含税）', align: 'center', valign: 'middle',colspan: 1, rowspan: 2,})
    columnTitle1.push({field: 'remark7', title: '半工装件协议单价（不含税）', align: 'center', valign: 'middle',colspan: 1, rowspan: 2,})






    // columnTitle1.push({field: 'itemId', title: '零件号', align: 'center', valign: 'middle',colspan: 1, rowspan: 2,})
    // columnTitle1.push({field: 'itemName', title: '零件名称', align: 'center', valign: 'middle',colspan: 1, rowspan: 2,})
    // columnTitle1.push({field: 'eachCarQuantity', title: '每车数量', align: 'center', valign: 'middle',colspan: 1, rowspan: 2,})
    // columnTitle1.push({field: 'chosenSupplier', title: '中选供应商', align: 'center', valign: 'middle',colspan: 1, rowspan: 2,})
    // columnTitle1.push({field: 'parts', title: '零部件(不含税)', align: 'center', valign: 'middle',colspan: 1, rowspan: 2,})
    // columnTitle1.push({field: 'singleCarPrice', title: '单车零部件价格(不含税)', align: 'center', valign: 'middle',colspan: 1, rowspan: 2,})
    // columnTitle1.push({field: 'moldsCostNotRevenue', title: '模具费(不含税)', align: 'center', valign: 'middle',colspan: 1, rowspan: 2,})
    // columnTitle1.push({field: 'moldsCostHasRevenue', title: '模具费(含税)', align: 'center', valign: 'middle',colspan: 1, rowspan: 2,})
    // columnTitle1.push({field: 'moldsCostDetails', title: '模具费付费情况', align: 'center', valign: 'middle',colspan: 1, rowspan: 2,})
    // columnTitle1.push({field: 'gaugeCost', title: '检具费(不含税)', align: 'center', valign: 'middle',colspan: 1, rowspan: 2,})
    // columnTitle1.push({field: 'exploitationCost', title: '开发费(不含税)', align: 'center', valign: 'middle',colspan: 1, rowspan: 2,})
    // columnTitle1.push({field: 'aSamplePiece', title: 'A样件', align: 'center', valign: 'middle',colspan: 1, rowspan: 2,})
    // columnTitle1.push({field: 'bSamplePiece', title: 'B样件', align: 'center', valign: 'middle',colspan: 1, rowspan: 2,})
    // columnTitle1.push({field: 'cSamplePiece', title: 'C样件', align: 'center', valign: 'middle',colspan: 1, rowspan: 2,})
    // columnTitle1.push({field: 'csLowerCostingoPlan', title: '供应商降本计划', align: 'center', valign: 'middle',colspan: 1, rowspan: 2,})
    // columnTitle1.push({field: 'remark', title: '备注', align: 'center', valign: 'middle',colspan: 1, rowspan: 2,})
    // // columnTitle1.push({field: 'specialty', title: '专业', align: 'center', valign: 'middle',colspan: 1, rowspan: 1,})
    // columnTitle1.push({field: 'specialty', title: '专业', align: 'center', valign: 'middle',colspan: 1, rowspan: 1,})

    column.push(columnTitle1);

    // columnTitle2.push({field: 'specialty', title: '专业', align: 'center', valign: 'middle',});



    columnTitle2.push({field: 'specialty', title: '**型', align: 'center', valign: 'middle',});
    columnTitle2.push({field: 'specialty', title: '合同号', align: 'center', valign: 'middle',});
    columnTitle2.push({field: 'specialty', title: '预算金额（含税）', align: 'center', valign: 'middle',});
    columnTitle2.push({field: 'specialty', title: '合同金额（含税）', align: 'center', valign: 'middle',});
    columnTitle2.push({field: 'specialty', title: '已付', align: 'center', valign: 'middle',});
    columnTitle2.push({field: 'specialty', title: '合同号', align: 'center', valign: 'middle',});
    columnTitle2.push({field: 'specialty', title: '预算金额（含税）', align: 'center', valign: 'middle',});
    columnTitle2.push({field: 'specialty', title: '合同金额（含税）', align: 'center', valign: 'middle',});
    columnTitle2.push({field: 'specialty', title: '已付', align: 'center', valign: 'middle',});
    columnTitle2.push({field: 'specialty', title: '合同号', align: 'center', valign: 'middle',});
    columnTitle2.push({field: 'specialty', title: '预算金额（含税）', align: 'center', valign: 'middle',});
    columnTitle2.push({field: 'specialty', title: '合同金额（含税）', align: 'center', valign: 'middle',});
    columnTitle2.push({field: 'specialty', title: '已付', align: 'center', valign: 'middle',});


    column.push(columnTitle2);
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


function addNum (num1, num2) {
    var sq1,sq2,m;
    try {
        sq1 = num1.toString().split(".")[1].length;
    }
    catch (e) {
        sq1 = 0;
    }
    try {
        sq2 = num2.toString().split(".")[1].length;
    }
    catch (e) {
        sq2 = 0;
    }
    m = Math.pow(10,Math.max(sq1, sq2));
    return (num1 * m + num2 * m) / m;
}