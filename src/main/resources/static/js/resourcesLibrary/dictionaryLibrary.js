/**
 * Created with IntelliJ IDEA.
 * User: xlf
 * Date: 2018/8/28
 * Time: 16:58
 */
var indexCoach = {};
$(document).ready((function () {
    var projectId = $("#project", window.top.document).val();
    var url = "dict/list";
    initTable(url);
}))

function doQuery() {
    var url = "dict/list";
    initTable(url);
    $('#dictionaryLibraryTable').bootstrapTable('destroy');
}

function initTable(url) {
    var column = [];
    $.ajax({
        url: "dict/title",
        type: "GET",
        success: function (result) {
            var column = [];
            column.push({
                field: 'ck', checkbox: true, Width: 50,
                formatter: function (value, row, index) {
                    row.myIndex=index;
                }
            });
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
            $('#dictionaryLibraryTable').bootstrapTable({
                url: url,
                method: 'GET',
                dataType: 'json',
                cache: false,
                striped: true,                                //是否显示行间隔色
                sidePagination: "server",                    //分页方式：client客户端分页，server服务端分页（*）
                height: $(window.parent.document).find("#wrapper").height() - 150,
                width: $(window).width(),
                formId: "queryDictionaryLibrary",
                undefinedText: "",                           //当数据为 undefined 时显示的字符
                pagination: true,
                pageNumber: 1,                                //初始化加载第一页，默认第一页
                pageSize: 20,                                //每页的记录行数（*）
                pageList: ['ALL', 10, 20, 50, 100, 200, 500, 1000],        //可供选择的每页的行数（*）                uniqueId: "puid",                           //每一行的唯一标识，一般为主键列
                showExport: true,
                sortable: true,                             //是否启用排序
                sortOrder: "asc",                           //排序方式
                clickToSelect: true,                       // 单击某一行的时候选中某一条记录
                striped: true,                              //是否显示行间隔色
                showColumns: true,                         //是否显示所有的列
                showToggle: false,                        //是否显示详细视图和列表视图的切换按钮
                showRefresh: true,                        //是否显示刷新按钮
                columns: column,
                toolbars: [
                    {
                        text: '添加',
                        iconCls: 'glyphicon glyphicon-plus',
                        handler: function () {
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
                    },
                    {
                        text: '修改',
                        iconCls: 'glyphicon glyphicon-pencil',
                        handler: function () {
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
                    },
                    {
                        text: '删除',
                        iconCls: 'glyphicon glyphicon-remove',
                        handler: function () {
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
                                        window.Ewin.confirm({
                                            title: '提示',
                                            message: '是否要删除您选择的记录',
                                            width: 500
                                        }).on(function (e) {
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
                    },
                ],
            })
        }
    });
    $('#dictionaryLibraryTable').on("click-row.bs.table", function (e, row, $element) {
        var index = $element.data('index');

    });
}

function toPage() {
    var pageNum = $("#pageNum").val();
    if (pageNum) {
        $('#dictionaryLibraryTable').bootstrapTable('selectPage', parseInt(pageNum));
    }
}

$(document).keydown(function (event) {
    if (event.keyCode == 13) {
        $('form').each(function () {
            event.preventDefault();
        });
    }
});

function updateTableRow(opts){
    return tableUpdateHelper.updateTableRow(opts);
}


