/**
 * Created with IntelliJ IDEA.
 * User: xlf
 * Date: 2018/8/28
 * Time: 16:58
 */

/**
 * 添加
 */
var add = function(){
    var rows = $('#dictionaryLibraryTable').bootstrapTable('getSelections');
    //只能选一条
    var url = "dict/getAdd";
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
                    url: "dict/getAdd",
                    gridId: "gridId",
                    width: 500,
                    height: 500
                })
            }
        }
    })
}
/**
 * 快速添加
 * @returns {boolean}
 */
var quickAdd = function(){
    var rows = $('#dictionaryLibraryTable').bootstrapTable('getSelections');
    //只能选一条
    if (rows.length != 1) {
        window.Ewin.alert({message: '请选择一条数据再进行快速添加!'});
        return false;
    }
    var url = "dict/getQuickAdd";
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
                    url: "dict/getQuickAdd?puid=" + rows[0].puid,
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
 * @returns {boolean}
 */
var update = function(){
    var rows = $('#dictionaryLibraryTable').bootstrapTable('getSelections');
    //只能选一条
    if (rows.length != 1) {
        window.Ewin.alert({message: '请选择一条需要修改的数据!'});
        return false;
    }
    var url = "dict/getUpdate";
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
                    url: "dict/getUpdate?puid=" + rows[0].puid,
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
 * @returns {boolean}
 */
var doDelete = function(){
    var rows = $('#dictionaryLibraryTable').bootstrapTable('getSelections');
    if (rows.length == 0) {
        window.Ewin.alert({message: '请选择一条需要删除的数据!'});
        return false;
    }
    var url = "dict/delete";
    $.ajax({
        url: "privilege/write?url=" + url,
        type: "GET",
        success: function (result) {
            if (!result.success) {
                window.Ewin.alert({message: result.errMsg});
                return false;
            }
            else {
                window.Ewin.confirm({title: '提示', message: '是否要删除您选择的记录', width: 500}).on(function (e) {
                    if (e) {
                        $.ajax({
                            type: "POST",
                            //ajax需要添加打包名
                            url: "dict/delete?puid=" + rows[0].puid,
                            // data: JSON.stringify(rows),
                            contentType: "application/json",
                            success: function (result) {
                                /*if (result.status) {
                                    window.Ewin.alert({message: result.errMsg});
                                    //刷新，会重新申请数据库数据
                                }
                                else {
                                    window.Ewin.alert({message: ":" + result.errMsg});
                                }*/
                                if (result.success) {
                                    layer.msg('删除成功', {icon: 1, time: 2000})
                                }
                                else if (!result.success) {
                                    window.Ewin.alert({message: result.errMsg});
                                }
                                $('#dictionaryLibraryTable').bootstrapTable("refresh");
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
const toolbar =[
    {
        text: '添加',
        iconCls: 'glyphicon glyphicon-plus',
        handler: add
    },
    {
        text: '快速添加',
        iconCls: 'glyphicon glyphicon-plus',
        handler: quickAdd,
    },
    {
        text: '修改',
        iconCls: 'glyphicon glyphicon-pencil',
        handler: update,
    },
    {
        text: '删除',
        iconCls: 'glyphicon glyphicon-remove',
        handler: doDelete,
    },
]
/**
 * 页面初始化后自动生成table
 */
$(document).ready((function () {
    var url = "dict/list";
    initTable(url);
}))
/**
 * 自动查询调用
 */
function doQuery() {
    var url = "dict/list";
    initTable(url);
    $('#dictionaryLibraryTable').bootstrapTable('destroy');
}

/**
 * 创建表头
 * @param result
 * @returns {Array}
 */
function createColumn(result) {
    let column = [];
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
    return column;
}
/**
 * table初始化
 * @param url
 */
function initTable(url) {
    $.ajax({
        url: "dict/title",
        type: "GET",
        success: function (result) {
            let column = createColumn(result);
            $('#dictionaryLibraryTable').bootstrapTable({
                url: url,
                method: 'GET',
                dataType: 'json',
                cache: false,
                // striped: true,                                //是否显示行间隔色
                sidePagination: "server",                    //分页方式：client客户端分页，server服务端分页（*）
                height: $(window.parent.document).find("#wrapper").height() - 150,
                width: $(window).width(),
                formId: "queryDictionaryLibrary",
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
                columns:column,
                showExport: phoneOrPc(),              //是否显示导出按钮(此方法是自己写的目的是判断终端是电脑还是手机,电脑则返回true,手机返回falsee,手机不显示按钮)
                exportDataType: "selected",              //basic', 'all', 'selected'.
                exportTypes: ['xlsx'],	    //导出类型
                //exportButton: $('#btn_export'),     //为按钮btn_export  绑定导出事件  自定义导出按钮(可以不用)
                exportOptions: {
                    //ignoreColumn: [0,0],            //忽略某一列的索引
                    fileName: '配置字典导出',              //文件名称设置
                    worksheetName: 'Sheet1',          //表格工作区名称
                    tableName: '配置字典表',
                    excelstyles: ['background-color', 'color', 'font-size', 'font-weight'],
                    //onMsoNumberFormat: DoOnMsoNumberFormat
                },
                toolbars: toolbar,
            })
        }
    })
}
function toPage() {
    var pageNum = $("#pageNum").val();
    if (pageNum) {
        $('#dictionaryLibraryTable').bootstrapTable('selectPage', parseInt(pageNum));
    }
}
$(document).keydown(function(event) {
    if (event.keyCode == 13) {
        $('form').each(function() {
            event.preventDefault();
        });
    }
});