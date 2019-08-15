/**
 * Created with IntelliJ IDEA.
 * User: xlf
 * Date: 2019/8/14
 * Time: 15:58
 */
var $table;
var projectId;
/**
 * 自动查询调用
 */
// function doQuery() {
//     initTable();
// }
/**
 * 创建table表头
 */
function createColumn() {
    let column = [];
    var columnOne = [];
    var columnTwo = [];
    columnOne.push({field: 'ck', colspan: 1, rowspan: 2, align: 'center', valign: 'middle', checkbox: true})
    columnOne.push({field: 'q', title: '序号',  align: 'center', valign: 'middle', width: 50, colspan: 1, rowspan: 2,
        formatter: function (value, row, index) {
            var options = $('#legislativeLibraryTable').bootstrapTable('getOptions');
            if (options.pageSize = "ALL") {
                return index + 1;
            }
            return options.pageSize * (options.pageNumber - 1) + index + 1;
        }
    })
    columnOne.push({field: 'w', title: '公告号', align: 'center', valign: 'middle', colspan: 1, rowspan: 2,})
    columnOne.push({field: 'e', title: '车型', align: 'center', valign: 'middle', colspan: 1, rowspan: 2,})
    columnOne.push({field: 'r', title: 'VIN码前8位', align: 'center', valign: 'middle', colspan: 1, rowspan: 2,})
    columnOne.push({field: 't', title: '电池', align: 'center', valign: 'middle', colspan: 3, rowspan: 1,})
    columnTwo.push({field: 'y', title: '电池厂家', align: 'center', valign: 'middle', colspan: 1, rowspan: 1,})
    columnTwo.push({field: 'u', title: '电池型号', align: 'center', valign: 'middle', colspan: 1, rowspan: 1,})
    columnTwo.push({field: 'i', title: '生产厂家', align: 'center', valign: 'middle', colspan: 1, rowspan: 1,})
    columnOne.push({field: 'o', title: '电机', align: 'center', valign: 'middle', colspan: 2, rowspan: 1,})
    columnTwo.push({field: 'p', title: '电机厂家', align: 'center', valign: 'middle', colspan: 1, rowspan: 1,})
    columnTwo.push({field: 'a', title: '电机型号', align: 'center', valign: 'middle', colspan: 1, rowspan: 1,})
    columnOne.push({field: 's', title: '备注', align: 'center', valign: 'middle', colspan: 1, rowspan: 2,})
    column.push(columnOne);
    column.push(columnTwo);
    return column;
}

/**
 * 添加
 */
var add = function () {

}
/**
 * 快速添加
 */
var quickAdd = function () {

}
/**
 * 修改
 */
var update = function () {

}
/**
 * 删除
 */
var doDelete = function () {

}
/**
 * 工具条
 * @type {*[]}
 */
const toolbar = [
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
        url: "",
        method: 'GET',
        dataType: 'json',
        cache: false,
        striped: true,                                //是否显示行间隔色
        sidePagination: "server",                    //分页方式：client客户端分页，server服务端分页（*）
        height: $(window.parent.document).find("#wrapper").height() - 150,
        width: $(window).width(),
        formId: "queryLegislativeLibrary",
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
        // showExport: phoneOrPc(),              //是否显示导出按钮(此方法是自己写的目的是判断终端是电脑还是手机,电脑则返回true,手机返回falsee,手机不显示按钮)
        // exportDataType: "selected",              //basic', 'all', 'selected'.
        // exportTypes: ['xlsx'],	    //导出类型
        // //exportButton: $('#btn_export'),     //为按钮btn_export  绑定导出事件  自定义导出按钮(可以不用)
        // exportOptions: {
        //     //ignoreColumn: [0,0],            //忽略某一列的索引
        //     fileName: '法规件库导出',              //文件名称设置
        //     worksheetName: 'Sheet1',          //表格工作区名称
        //     tableName: '法规件库表',
        //     excelstyles: ['background-color', 'color', 'font-size', 'font-weight'],
        //     //onMsoNumberFormat: DoOnMsoNumberFormat
        // },
        toolbars: toolbar
    })
}

/**
 * 页面初始化后自动生成table
 */
$(document).ready((function () {
    projectId = getProjectUid();
    $table = $("#legislativeLibraryTable");
    log($table)
    initTable();
}))

function toPage() {
    var pageNum = $("#pageNum").val();
    if (pageNum) {
        $('#legislativeLibraryTable').bootstrapTable('selectPage', parseInt(pageNum));
    }
}

$(document).keydown(function (event) {
    if (event.keyCode == 13) {
        $('form').each(function () {
            event.preventDefault();
        });
    }
});