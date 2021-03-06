/**
 * Created with IntelliJ IDEA.
 * User: xlf
 * Date: 2018/9/27
 * Time: 16:25
 */

$(document).ready((function () {
    initTable();
}))
function  doQuery() {
    $('#accessoriesLibraryTable').bootstrapTable('refresh');
}

function initTable(){
    var $table = $("#accessoriesLibraryTable");
    var column = [];
    $.ajax({
        url: "acce/title",
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
                        align:
                            'center',
                        valign:
                            'middle'
                    };
                    column.push(json);
                }
            };
            $table.bootstrapTable({
                url:"acce/get/lib",
                method: 'get',
                height: $(window.parent.document).find("#wrapper").height() - 90,
                width: $(window).width(),
                showToggle: false,                   //是否显示详细视图和列表视图的切换按钮
                showRefresh: true,                  //是否显示刷新按钮
                pagination: true,                   //是否显示分页（*）
                pageSize:20,
                pageNumber:1,
                pageList: ['ALL', 10, 20, 50, 100, 200, 500, 1000],        //可供选择的每页的行数（*）                sidePagination : "server",          //分页方式：client客户端分页，server服务端分页（*）
                clickToSelect: true,                // 单击某一行的时候选中某一条记录
                formId: "formId",
                columns: column,                     //列信息，需要预先定义好
                sortable: true,                     //是否启用排序
                sortOrder: "asc",                   //排序方式
                striped: true,                      //是否显示行间隔色
                search: false,                      //是否显示表格搜索，此搜索是客户端搜索，不会进服务端
                showColumns: true,                 //是否显示所有的列
                //>>>>>>>>>>>>>>导出excel表格设置
                showExport: phoneOrPc(),              //是否显示导出按钮(此方法是自己写的目的是判断终端是电脑还是手机,电脑则返回true,手机返回falsee,手机不显示按钮)
                exportDataType:'selected',              //basic', 'all', 'selected'.
                exportTypes: ['xlsx'],	    //导出类型
                // exportButton: $('#btn_download'),     //为按钮btn_export  绑定导出事件  自定义导出按钮(可以不用)
                exportOptions: {
                    //ignoreColumn: [0,0],            //忽略某一列的索引
                    fileName: '工艺辅料库导出',              //文件名称设置
                    worksheetName: 'Sheet1',          //表格工作区名称
                    tableName: '工艺辅料表',
                    excelstyles: ['background-color', 'color', 'font-size', 'font-weight'],
                    //onMsoNumberFormat: DoOnMsoNumberFormat
                },
                //导出excel表格设置<<<<<<<<<<<<<<<<
                toolbars: [
                    {
                        text: '添加',
                        iconCls: 'glyphicon glyphicon-plus',
                        handler: function () {
                            var url = "acce/addAccessories";
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
                                            url: "acce/addAccessories",
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
                            var rows = $table.bootstrapTable('getSelections');
                            //只能选一条
                            if (rows.length != 1) {
                                window.Ewin.alert({message: '请选择一条需要修改的数据!'});
                                return false;
                            }
                            var url = "acce/updateAccessories";
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
                                            url: "acce/updateAccessories?puid=" + rows[0].puid,
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
                            for (var i = 0 ; i<rows.length;i++){
                                puids += rows[i].puid+",";
                            };
                            var myData = JSON.stringify({
                                "puids":puids,
                            });
                            if (rows.length == 0) {
                                window.Ewin.alert({message: '请至少选择一条需要删除的数据!'});
                                return false;
                            }
                            var _table = '<p>是否要删除您所选择的记录？</p>' +
                                '<div style="max-height: 400px;overflow:scroll;"><table class="table table-striped tableNormalStyle" >';
                            for (var index in rows) {
                                _table += '<tr><td>' + rows[index].pMaterielName + '</td></tr>';
                            }
                            _table += '</table></div>';
                            var url = "acce/delete";
                            $.ajax({
                                url: "privilege/write?url=" + url,
                                type: "GET",
                                success: function (result) {
                                    if (!result.success) {
                                        window.Ewin.alert({message: result.errMsg});
                                        return false;
                                    }
                                    else {
                                        window.Ewin.confirm({title: '提示', message: _table, width: 500}).on(function (e) {
                                            if (e) {
                                                $.ajax({
                                                    type: "POST",
                                                    url: "acce/delete",
                                                    data: myData,
                                                    contentType: "application/json",
                                                    success: function (result) {
                                                        if (result.success) {
                                                            layer.msg('删除成功', {icon: 1, time: 2000})
                                                        }
                                                        else if(!result.success){
                                                            window.Ewin.alert({message: result.errMsg})
                                                        }
                                                        $('#accessoriesLibraryTable').bootstrapTable('refresh');
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
                        text: '导入Excel',
                        iconCls: 'glyphicon glyphicon-share',
                        handler: function () {
                            var url = "acce/importExcel";
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
                                            url: "acce/importExcel",
                                            width: 600,
                                            height: 500
                                        })
                                    }
                                }
                            })
                        }
                    },
                    // {
                    //     text: '导出Excel',
                    //     iconCls: 'glyphicon glyphicon-share',
                    //     handler: function () {
                    //         var rows = $table.bootstrapTable('getSelections');
                    //         if (rows.length == 0) {
                    //             window.Ewin.alert({message: '请选择要导出的数据!'});
                    //             return false;
                    //         }
                    //         var button = document.getElementById('download');
                    //         button.onclick = $table.tableExport(
                    //             {
                    //                 type: 'excel',
                    //                 escape: false,
                    //                 fileName: '工艺辅料库导出',              //文件名称设置
                    //                 worksheetName: 'Sheet1',          //表格工作区名称
                    //                 tableName: '工艺辅料表',
                    //                 excelstyles: ['background-color', 'color', 'font-size', 'font-weight'],
                    //                 exportTypes:['xlsx'],
                    //                 jsonScope:'selected'
                    //             });
                    //     }
                    // }
                ],
            });
        }
    })
}
function toPage() {
    var pageNum = $("#pageNum").val();
    if (pageNum) {
        $('#accessoriesLibraryTable').bootstrapTable('selectPage', parseInt(pageNum));
    }
}

$(document).keydown(function(event) {
    if (event.keyCode == 13) {
        $('form').each(function() {
            event.preventDefault();
        });
    }
});
