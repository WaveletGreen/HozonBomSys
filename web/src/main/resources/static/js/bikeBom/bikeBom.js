/**
 * Created with IntelliJ IDEA.
 * User: xlf
 * Date: 2018/9/12
 * Time: 13:29
 */
//当前table对象
let $table;
//项目ID
let projectId;
/**
 *修改
 */
const modify = function () {
    var rows = $table.bootstrapTable('getSelections');
    // 只能选一条
    if (rows.length != 1) {
        window.Ewin.alert({message: '请选择一条需要修改的数据!'});
        return false;
    }
    var url = "singleVehicles/update/page";
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
                    url: "singleVehicles/update/page?projectId=" + projectId + "&id=" + rows[0].id,
                    gridId: "gridId",
                    width: 500,
                    height: 500
                });
            }
        }
    })
};
/**
 * 发送到sap
 * @returns {boolean}
 */
const sendToSAP = function () {
    var rows = $table.bootstrapTable('getSelections');
    // 只能选一条
    if (rows.length <= 0) {
        window.Ewin.alert({message: '请选择需要发送至SAP的数据!'});
        return false;
    }
    var url = "singleVehicles/sendSap";
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
                    url: "singleVehicles/sendSap",
                    type: "POST",
                    contentType: "application/json;charset=utf-8",
                    data: JSON.stringify(rows),
                    success: function (result) {
                        if (result.status) {
                            layer.msg(result.msg, {icon: 1, time: 2000})
                        } else {
                            window.Ewin.alert({message: result.msg});
                        }
                    },
                    error: function () {

                    }
                })
            }
        }
    })
};
/**
 * 删除SAP中存在的数据
 * @returns {boolean}
 */
var deleteFromSAP = function () {
    var rows = $table.bootstrapTable('getSelections');
    // 只能选一条
    if (rows.length <= 0) {
        window.Ewin.alert({message: '请选择需要从SAP删除的数据!'});
        return false;
    }
    var url = "singleVehicles/deleteSap";
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
                    url: "singleVehicles/deleteSap",
                    type: "POST",
                    contentType: "application/json;charset=utf-8",
                    data: JSON.stringify(rows),
                    success: function (result) {
                        if (result.status) {
                            layer.msg(result.msg, {icon: 1, time: 2000})
                        } else {
                            window.Ewin.alert({message: result.msg});
                        }
                    },
                    error: function () {

                    }
                })
            }
        }
    })
};
/**
 * 导出到excel
 * @returns {boolean}
 */
const exportToExcel = function () {
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
                url: "./singleVehicles/excelExport",//??????
                data: JSON.stringify(rows),
                contentType: "application/json",
                success: function (result) {
                    console.log(result);
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
};

/**
 * 工具条设置
 * @type {*[]}
 */
const toolbar = [
    {
        text: '修改',
        iconCls: 'glyphicon glyphicon-pencil',
        handler: modify
    },
    // {
    //     text: '发送至SAP',
    //     iconCls: 'glyphicon glyphicon-send',
    //     handler: sendToSAP
    // },
    // {
    //     text: '从SAP删除',
    //     iconCls: 'glyphicon glyphicon-send',
    //     handler: deleteFromSAP
    // },
    {
        text: '导出Excel',
        iconCls: 'glyphicon glyphicon-export',
        handler: exportToExcel
    }
];
//页面初始化
$(document).ready((function () {
    initTable();
}))

//刷新table
function doRefresh(projectId) {
    initTable();
}

//提供的全局刷新table功能，由window-hepler进行调用
function doQuery() {
    $('#bikeBomTable').bootstrapTable('refresh');
}

/**
 * 创建表头
 * @returns {Array}
 */
function createColumn() {
    let column = [];
    // column.push({field: 'id', title: '主键'});
    column.push({field: 'ck', checkbox: true, align: 'center', width: 50});
    column.push({
        field: 'svlMaterialCode',
        title: '物料编号',
        align: 'center',
        valign: 'middle',
        formatter: function (value, row, index) {
            return [
                '<a href="javascript:void(0)" onclick="queryLou(' + row.id + ')">' + value + '</a>'
            ].join("");
        }
    });
    column.push({field: 'svlMaterialBasicInfo', title: '基本信息', align: 'center', valign: 'middle'});
    column.push({
        field: 'checkStatus',
        title: '检查状态',
        align: 'center',
        valign: 'middle',
        formatter: function (value, row, index) {
            if(value===1){
                value='完整';
            }
            else if(value===10){
                value='重复';
            }
            else if(value===-1){
                value='不完整';
            }
            else {
                value='未知状态';
            }
            return [
                '<a href="javascript:void(0)" onclick="getCheckStatus(' + row.id + ',\''+row.svlMaterialCode+'\')">' + value + '</a>'
            ].join("");
        }
    });
    column.push({field: 'brandCode', title: '品牌代码', align: 'center', valign: 'middle'});
    column.push({field: 'brandName', title: '中文品牌', align: 'center', valign: 'middle'});
    column.push({field: 'platformCode', title: '平台代码', align: 'center', valign: 'middle'});
    column.push({field: 'platformName', title: '平台名称', align: 'center', valign: 'middle'});
    column.push({field: 'vehicleCode', title: '车型代码', align: 'center', valign: 'middle'});
    column.push({field: 'vehicleName', title: '车型名称', align: 'center', valign: 'middle'});
    column.push({field: 'svlInnerColorCode', title: '内饰颜色代码', align: 'center', valign: 'middle'});
    column.push({field: 'svlInnerColorName', title: '内饰颜色名称', align: 'center', valign: 'middle'});
    column.push({field: 'colorCode', title: '颜色代码', align: 'center', valign: 'middle'});
    column.push({field: 'colorName', title: '颜色名称', align: 'center', valign: 'middle'});
    column.push({field: 'svlBatteryCode', title: '电池型号', align: 'center', valign: 'middle'});
    column.push({field: 'svlMotorCode', title: '电机型号', align: 'center', valign: 'middle'});
    return column;
}

function initTable() {
    projectId = getProjectUid();
    if (!checkIsSelectProject(projectId)) {
        return;
    }
    const url = "singleVehicles/record?projectId=" + projectId
    $table = $("#bikeBomTable");
    $table.bootstrapTable('destroy');
    let column=createColumn();
    $table.bootstrapTable({
        url: url,
        method: 'get',
        height: $(window.parent.document).find("#wrapper").height() - 90,
        width: $(window).width(),
        showToggle: false,                   //是否显示详细视图和列表视图的切换按钮
        showRefresh: true,                  //是否显示刷新按钮
        pagination: false,                   //是否显示分页（*）
        clickToSelect: true,                // 单击某一行的时候选中某一条记录
        showExport: false,
        formId: "formId",
        columns: column,                     //列信息，需要预先定义好
        sortable: false,                     //是否启用排序
        sortOrder: "asc",                   //排序方式
        striped: true,                      //是否显示行间隔色
        search: false,                      //是否显示表格搜索，此搜索是客户端搜索，不会进服务端
        showColumns: true,                 //是否显示所有的列
        toolbars: toolbar
    });
}

/**
 * 状态检查
 * @param id
 */
function getCheckStatus(id,svlMaterialCode) {
    log("检查状态ID="+id);
    if(isAsForDialog()) {
        window.Ewin.dialog({
            title: svlMaterialCode+"检查状态",
            url: "singleVehicles/checkStatus?projectId=" + getProjectUid() + "&vehiclesId=" + id,
            width: 800,
            height: 500
        });
    }
    //不是作为弹窗，作为页面跳转
    else{
        window.location.href = "singleVehicles/checkStatus?projectId=" +getProjectUid()+"&vehiclesId="+id;
    }
}

function queryLou(row) {
    window.location.href = "singleVehicles/getDetail?id=" + row;
}
