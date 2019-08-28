/**
 * Created with IntelliJ IDEA.
 * User: xulf
 * Date: 2019/8/15
 * Time: 15:03
 */
var $table;
var projectId;
/**
 * 自动查询调用
 */
function doQuery() {
    initTable();
}
/**
 * 创建table表头
 */
function createColumn() {
    let column = [];
    column.push({field: 'ck', align: 'center', valign: 'middle', checkbox: true})
    // column.push({field: 'q', title: '序号',  align: 'center', valign: 'middle', width: 50,
    //     formatter: function (value, row, index) {
    //         var options = $('#legislativeItemLibraryTable').bootstrapTable('getOptions');
    //         if (options.pageSize = "ALL") {
    //             return index + 1;
    //         }
    //         return options.pageSize * (options.pageNumber - 1) + index + 1;
    //     }
    // })
    column.push({field: 'no', title: '序号', align: 'center', valign: 'middle',})
    column.push({field: 'noticeNo', title: '公告号', align: 'center', valign: 'middle',})
    column.push({field: 'legislativeName', title: '法规件名称', align: 'center', valign: 'middle',})
    column.push({field: 'legislativeNo', title: '法规件型号', align: 'center', valign: 'middle',})
    column.push({field: 'applicableModels', title: '适用车型', align: 'center', valign: 'middle',})
    column.push({field: 'partId', title: '零件号', align: 'center', valign: 'middle',})
    column.push({field: 'partName', title: '零件名称', align: 'center', valign: 'middle',})
    column.push({field: 'supplier', title: '供应商', align: 'center', valign: 'middle',})
    column.push({field: 'supplierNo', title: '供应商代码', align: 'center', valign: 'middle',})
    column.push({field: 'technologyDesc', title: '技术特性描述', align: 'center', valign: 'middle',})
    column.push({field: 'applyDepa', title: '申请部门', align: 'center', valign: 'middle',})
    column.push({field: 'isHaveTest', title: '是否需要强检报告', align: 'center', valign: 'middle',})
    column.push({field: 'isHaveCcc', title: '是否需要3C证书', align: 'center', valign: 'middle',})
    column.push({field: 'dutyEngineer', title: '责任工程师', align: 'center', valign: 'middle',})
    column.push({field: 'remarks', title: '备注', align: 'center', valign: 'middle',})
    return column;
}

/**
 * 添加
 */
var add = function () {
    var url = "legislativeItem/addLegislative";
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
                    url: "legislativeItem/addLegislative",
                    gridId: "gridId",
                    width: 500,
                    height: 500
                });
            }
        }
    })
}
/**
 * 快速添加
 */
var quickAdd = function () {
    var rows = $table.bootstrapTable('getSelections');
    //只能选一条
    if (rows.length != 1) {
        window.Ewin.alert({message: '请选择一条数据再进行快速添加!'});
        return false;
    }
    var url = "legislativeItem/getQuickAdd";
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
                    title: "快速添加",
                    url: "legislativeItem/getQuickAdd?puid=" + rows[0].puid,
                    gridId: "gridId",
                    width: 500,
                    height: 500
                });
            }
        }
    })
}
/**
 * 修改
 */
var update = function () {
    var rows = $table.bootstrapTable('getSelections');
    //只能选一条
    if (rows.length != 1) {
        window.Ewin.alert({message: '请选择一条需要修改的数据!'});
        return false;
    }
    var url = "legislativeItem/updateLegislative";
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
                    url: "legislativeItem/updateLegislative?&puid=" + rows[0].puid,
                    gridId: "gridId",
                    width: 500,
                    height: 500
                });
            }
        }
    })
}
/**
 * 删除
 */
var doDelete = function () {
    var rows = $table.bootstrapTable('getSelections');
    //只能选一条
    if (rows.length <= 0) {
        window.Ewin.alert({message: '请选择一条需要删除的数据!'});
        return false;
    }
    var puids = "";
    for (let i in rows) {
        if (rows[i].partId!=null && rows[i].partId!=""){
            window.Ewin.alert({message: '法规件已在EPL中引用,无法删除!'});
            return false;
        }
        puids += rows[i].puid;
        if (i < rows.length - 1) {
            puids += ",";
        }
    }
    var url = "legislativeItem/delete/Legislative?puids=" + puids;
    $.ajax({
        url: "privilege/write?url=" + url,
        type: "GET",
        success: function (result) {
            if (!result.success) {
                window.Ewin.alert({message: result.errMsg});
                return false;
            } else {
                $.ajax({
                    url: url,
                    type: "POST",
                    success: function (result) {
                        if (!result.success) {
                            window.Ewin.alert({message: result.errMsg});
                            return false;
                        } else {
                            layer.msg("删除成功", {icon: 1, time: 2000});
                            $table.bootstrapTable("refresh");
                            // window.location.reload();
                        }
                    }
                })
            }
        }
    })
}
/**
 * 工具条
 * @type {*[]}
 */
const toolbar = [
    // {
    //     text: '返回上一步',
    //     iconCls: 'fa fa-arrow-left',
    //     handler: function () {
    //         window.location.href="javascript:history.go(-1);"
    //     }
    // },
    {
        text: '添加',
        iconCls: 'glyphicon glyphicon-plus',
        handler: add
    },
    {
        text: '快速添加',
        iconCls: 'glyphicon glyphicon-plus',
        handler: quickAdd
    },
    {
        text: '修改',
        iconCls: 'glyphicon glyphicon-pencil',
        handler: update
    },
    {
        text: '删除',
        iconCls: 'glyphicon glyphicon-remove',
        handler: doDelete
    },
]
/**
 * table初始化
 * @param url
 */
function initTable() {
    $table.bootstrapTable('destroy');
    let column = createColumn();
    $table.bootstrapTable({
        url: "legislativeItem/list",
        method: 'GET',
        dataType: 'json',
        cache: false,
        striped: true,                                //是否显示行间隔色
        sidePagination: "server",                    //分页方式：client客户端分页，server服务端分页（*）
        height: $(window.parent.document).find("#wrapper").height() - 150,
        width: $(window).width(),
        formId: "queryLegislativeItemLibrary",
        undefinedText: "",                           //当数据为 undefined 时显示的字符
        pagination: true,
        pageNumber: 1,                                //初始化加载第一页，默认第一页
        pageSize: 20,                                //每页的记录行数（*）
        pageList: ['ALL', 10, 20, 50, 100, 200, 500, 1000],        //可供选择的每页的行数（*）                uniqueId: "puid",                           //每一行的唯一标识，一般为主键列
        // showExport: true,
        sortable: true,                             //是否启用排序
        sortOrder: "asc",                           //排序方式
        clickToSelect: true,                       // 单击某一行的时候选中某一条记录
        striped: true,                              //是否显示行间隔色
        showColumns: true,                         //是否显示所有的列
        showToggle: false,                        //是否显示详细视图和列表视图的切换按钮
        showRefresh: true,                        //是否显示刷新按钮
        columns: column,
        showExport: phoneOrPc(),              //是否显示导出按钮(此方法是自己写的目的是判断终端是电脑还是手机,电脑则返回true,手机返回falsee,手机不显示按钮)
        exportDataType: "selected",              //basic', 'all', 'selected'.
        exportTypes: ['xlsx'],	    //导出类型
        //exportButton: $('#btn_export'),     //为按钮btn_export  绑定导出事件  自定义导出按钮(可以不用)
        exportOptions: {
            //ignoreColumn: [0,0],            //忽略某一列的索引
            fileName: '法规件库导出',              //文件名称设置
            worksheetName: 'Sheet1',          //表格工作区名称
            tableName: '法规件库表',
            excelstyles: ['background-color', 'color', 'font-size', 'font-weight'],
            //onMsoNumberFormat: DoOnMsoNumberFormat
        },
        toolbars: toolbar
    })
}

/**
 * 页面初始化后自动生成table
 */
$(document).ready((function () {
    projectId = getProjectUid();
    $table = $("#legislativeItemLibraryTable");
    initTable();
}))

function toPage() {
    var pageNum = $("#pageNum").val();
    if (pageNum) {
        $('#legislativeItemLibraryTable').bootstrapTable('selectPage', parseInt(pageNum));
    }
}

$(document).keydown(function (event) {
    if (event.keyCode == 13) {
        $('form').each(function () {
            event.preventDefault();
        });
    }
});