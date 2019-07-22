$(document).ready((function () {
    var projectPuid = $("#project", window.top.document).val();
    var eplUrl = "epl/record?projectId=" + projectPuid;
    initTable(eplUrl);
}))

function doRefresh(projectId) {
    $('#eplTable').bootstrapTable('destroy');
    var eplUrl = "epl/record?projectId=" + projectId;
    initTable(eplUrl);
}

function doQuery() {
    var projectPuid = $("#project", window.top.document).val();
    var eplUrl = "epl/record?projectId=" + projectPuid;
    // var status = $("#status").val();
    // if (status != "请选择数据状态") {
    //     eplUrl += "&status=" + status;
    // }
    var partResource = $("#partResource").val();
    if (partResource == "请选择零件来源") {
        eplUrl += "&partResource=" + "";
    }
    else {
        eplUrl += "&partResource=" + partResource;
    }
    initTable(eplUrl);
    $('#eplTable').bootstrapTable('destroy');
}

function initTable(eplUrl) {
    var projectPuid = $("#project", window.top.document).val();
    if (!checkIsSelectProject(projectPuid)) {
        return;
    }
    var $table = $("#eplTable");
    var column = [];
    var eplTitleUrl = "epl/title";
    $.ajax({
        url: eplTitleUrl,
        type: "GET",
        success: function (result) {
            var column = [];
            column.push({field: 'ck', checkbox: true});
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
                            'middle',
                    };
                    column.push(json);
                }
            }
            // column.push({
            //     field: 'status',
            //     title: '状态',
            //     align: 'center',
            //     valign: 'middle',
            //
            //     formatter: function (value, row, index) {
            //         if (value == 1 || "1" == value) {
            //             return "<span style='color: #00B83F'>已生效</span>";
            //         }
            //         if (value == 2 || "2" == value) {
            //             return "<span style='color: #ff7cf4'>草稿状态</span>";
            //         }
            //         if (3 == value || "3" == value) {
            //             return "<span style='color: #9492a9'>废除状态</span>";
            //         }
            //         if (4 == value || "4" == value) {
            //             return "<span style='color: #a90009'>删除状态</span>";
            //         }
            //         if (value == 5 || value == "5") {
            //             return "<span style='color: #e2ab2f'>审核中</span>"
            //         }
            //     }
            // })
            $('#eplTable').bootstrapTable({
                method: 'GET',
                dataType: 'json',
                cache: false,
                striped: true,                              //是否显示行间隔色
                sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
                url: eplUrl,
                //data:myData,
                height: $(window.parent.document).find("#wrapper").height() - 150,
                width: $(window).width(),
                formId: "queryEplManage",
                undefinedText: "",//当数据为 undefined 时显示的字符
                pagination: true,
                pageNumber: 1,                       //初始化加载第一页，默认第一页
                pageSize: 20,                       //每页的记录行数（*）
                pageList: ['ALL', 10, 20, 50, 100, 200, 500, 1000],        //可供选择的每页的行数（*）                //queryParams:queryParam,
                //uniqueId: "puid",                     //每一行的唯一标识，一般为主键列
                // showExport: true,
                // exportDataType: 'all',
                columns: column,
                sortable: true,                     //是否启用排序
                sortOrder: "asc",                   //排序方式
                search: false, //是否显示表格搜索，此搜索是客户端搜索，不会进服务端
                showColumns: true, //是否显示所有的列
                showToggle: false,                   //是否显示详细视图和列表视图的切换按钮
                showRefresh: true,                  //是否显示刷新按钮
                clickToSelect: true,// 单击某一行的时候选中某一条记录
                toolbars: [
                    {
                        text: '添加',
                        iconCls: 'glyphicon glyphicon-plus',
                        handler: function () {
                            var url = "epl/add/page";
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
                                            url: "epl/add/page",
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
                        text: '修改',
                        iconCls: 'glyphicon glyphicon-pencil',
                        handler: function () {
                            var rows = $table.bootstrapTable('getSelections');
                            //只能选一条
                            if (rows.length != 1) {
                                window.Ewin.alert({message: '请选择一条需要修改的数据!'});
                                return false;
                            }
                            else if (rows[0].status == 5) {
                                window.Ewin.alert({message: '对不起,审核中的数据不能修改!'});
                                return false;
                            }
                            var url = "epl/update/page";
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
                                            url: "epl/update/page?&id=" + rows[0].id,
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
                            //只能选一条
                            if (rows.length <=0) {
                                window.Ewin.alert({message: '请选择一条需要删除的数据!'});
                                return false;
                            }
                            else if (rows[0].status == 5) {
                                window.Ewin.alert({message: '对不起,审核中的数据不能删除!'});
                                return false;
                            }
                            var ids = "";
                            for(let i in rows){
                                ids += rows[i].id;
                                if(i<rows.length-1){
                                    ids+=",";
                                }
                            }
                            var url = "epl/delete?ids="+ids;
                            $.ajax({
                                url: "privilege/write?url=" + url,
                                type: "GET",
                                success: function (result) {
                                    if (!result.success) {
                                        window.Ewin.alert({message: result.errMsg});
                                        return false;
                                    }else {
                                        $.ajax({
                                            url : url,
                                            type : "DELETE",
                                            success : function (result) {
                                                if (!result.success) {
                                                    window.Ewin.alert({message: result.errMsg});
                                                    return false;
                                                }else {
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
                    },
                    // {
                    //     text: '撤销',
                    //     iconCls: 'glyphicon glyphicon-pencil',
                    //     handler: function () {
                    //         var rows = $table.bootstrapTable('getSelections');
                    //         //只能选一条
                    //         if (rows.length <=0) {
                    //             window.Ewin.alert({message: '请选择一条需要撤销的数据!'});
                    //             return false;
                    //         }
                    //         else if (rows[0].status == 5) {
                    //             window.Ewin.alert({message: '对不起,审核中的数据不能撤销!'});
                    //             return false;
                    //         }
                    //         var ids = "";
                    //         for(let i in rows){
                    //             ids += rows[i].id;
                    //             if(i<rows.length-1){
                    //                 ids+=",";
                    //             }
                    //         }
                    //         var url = "epl/delete?ids="+ids;
                    //         $.ajax({
                    //             url: "privilege/write?url=" + url,
                    //             type: "GET",
                    //             success: function (result) {
                    //                 if (!result.success) {
                    //                     window.Ewin.alert({message: result.errMsg});
                    //                     return false;
                    //                 }else {
                    //                     $.ajax({
                    //                         url : url,
                    //                         type : "DELETE",
                    //                         success : function (result) {
                    //                             if (!result.success) {
                    //                                 window.Ewin.alert({message: result.errMsg});
                    //                                 return false;
                    //                             }else {
                    //                                 layer.msg("撤销成功", {icon: 1, time: 2000})
                    //                                 window.location.reload();
                    //                             }
                    //                         }
                    //                     })
                    //                 }
                    //             }
                    //         })
                    //     }
                    // },
                    // {
                    //     text: '关联变更单号',
                    //     iconCls: 'glyphicon glyphicon-log-out',
                    //     handler: function () {
                    //         var rows = $table.bootstrapTable('getSelections');
                    //         var puids = "";
                    //         for (var i = 0; i < rows.length; i++) {
                    //             if(rows[i].status = 1){
                    //                 window.Ewin.alert({message: '已生效数据不能关联变更单!'});
                    //                 return false;
                    //             }else if(rows[i].status=10){
                    //                 window.Ewin.alert({message: '已关联变更单,不可重复关联!'});
                    //                 return false;
                    //             }
                    //             puids += rows[i].puid + ",";
                    //         }
                    //         if (rows.length == 0) {
                    //             window.Ewin.alert({message: '请选择一条需要变更的数据!'});
                    //             return false;
                    //         }
                    //         // else {
                    //         //     for (var i = 0; i < rows.length; i++) {
                    //         //         if (rows[i].status != 4 && rows[i].status != 2) {
                    //         //             window.Ewin.alert({message: '请选择状态为草稿状态或删除状态的数据!'});
                    //         //             return false;
                    //         //         }
                    //         //     }
                    //         // }
                    //         var url = "epl/order/choose";
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
                    //                         title: "选择变更表单",
                    //                         url: "epl/order/choose?projectId=" + projectPuid + "&puids=" + puids,
                    //                         gridId: "gridId",
                    //                         width: 450,
                    //                         height: 450
                    //                     });
                    //                 }
                    //             }
                    //         })
                    //     }
                    // },
                    {
                        text: 'Excel导入',
                        iconCls: 'glyphicon glyphicon-log-out',
                        handler: function () {
                            var url = "epl/excelImportPage";
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
                                            url: "epl/excelImportPage",
                                            gridId: "gridId",
                                            width: 450,
                                            height: 450
                                        });
                                    }
                                }
                            })
                        }
                    }
                ],
                //>>>>>>>>>>>>>>导出excel表格设置
                showExport: phoneOrPc(),              //是否显示导出按钮(此方法是自己写的目的是判断终端是电脑还是手机,电脑则返回true,手机返回falsee,手机不显示按钮)
                exportDataType: "selected",              //basic', 'all', 'selected'.
                exportTypes: ['xlsx'],	    //导出类型
                //exportButton: $('#btn_export'),     //为按钮btn_export  绑定导出事件  自定义导出按钮(可以不用)
                exportOptions: {
                    //ignoreColumn: [0,0],            //忽略某一列的索引
                    fileName: 'EPL数据导出',              //文件名称设置
                    worksheetName: 'Sheet1',          //表格工作区名称
                    tableName: 'EPL数据表',
                    excelstyles: ['background-color', 'color', 'font-size', 'font-weight'],
                    //onMsoNumberFormat: DoOnMsoNumberFormat
                }
                //导出excel表格设置<<<<<<<<<<<<<<<<
            });
        }
    })

}

function toPage() {
    var pageNum = $("#pageNum").val();
    if (pageNum) {
        $('#eplTable').bootstrapTable('selectPage', parseInt(pageNum));
    }
}

$(document).keydown(function (event) {
    if (event.keyCode == 13) {
        $('form').each(function () {
            event.preventDefault();
        });
    }
});