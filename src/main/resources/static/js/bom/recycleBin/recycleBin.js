$(document).ready((function () {
    doQuery();
}))


function doQuery() {
    //$('#ebomManageTable').bootstrapTable('refresh');    //刷新表格
    var projectPuid = $("#project", window.top.document).val();
    var $table = $("#recycleBinTable");
    var type = $("#type").val();
    var bomUrl = "recycle/record/?projectId=" + projectPuid+"&type="+type;
    if (type == 1) {
        // function initTable() {
        $table.bootstrapTable('destroy');
        $.ajax({
            url: "ebom/title?projectId=" + projectPuid,
            type: "GET",
            success: function (result) {
                var column = [];
                column.push({field: 'puid', title: '主键'});
                column.push({field: 'ck', checkbox: true});
                /*var data = result.data;
                var nameZh = data[0];
                var nameEn = data[1];
                var keys = [];
                var values;
                for (var key in nameEn) {
                    if (nameEn.hasOwnProperty(key)) {
                        var json = {
                            field: nameEn[key],
                            title: nameZh[key],
                            align:
                                'center',
                            valign:
                                'middle'
                        };
                        column.push(json);
                    }
                }*/
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
                $table.bootstrapTable({
                    url: bomUrl,
                    method: 'GET',
                    dataType: 'json',
                    sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
                    height: $(window.parent.document).find("#wrapper").height() - 150,
                    width: $(window).width(),
                    formId: "queryBom",
                    undefinedText: "",//当数据为 undefined 时显示的字符
                    pagination: true,
                    pageNumber: 1,                       //初始化加载第一页，默认第一页
                    pageSize: 20,                       //每页的记录行数（*）
                    pageList: ['ALL',20,50,100,200,500,1000],        //可供选择的每页的行数（*）
                    columns: column,
                    sortOrder: "asc",                   //排序方式
                    clickToSelect: true,// 单击某一行的时候选中某一条记录
                    showColumns: true, //是否显示所有的列
                    showToggle: false,                   //是否显示详细视图和列表视图的切换按钮
                    showRefresh: true,                  //是否显示刷新按钮
                    // toolbars: [
                    //     {
                    //         text: '还原',
                    //         iconCls: 'glyphicon glyphicon-share',
                    //         handler: function () {
                    //             var rows = $table.bootstrapTable('getSelections');
                    //             var myData = JSON.stringify({
                    //                 "projectId": $("#project", window.top.document).val(),
                    //                 "puid": rows[0].puid,
                    //                 "type":type
                    //             });
                    //             if (rows.length !=1) {
                    //                 window.Ewin.alert({message: '请选择一条需要还原的数据!'});
                    //                 return false;
                    //             }
                    //             window.Ewin.confirm({
                    //                 title: '提示',
                    //                 message: '是否要还原您所选择的记录？',
                    //                 width: 500
                    //             }).on(function (e) {
                    //                 if (e) {
                    //                     $.ajax({
                    //                         type: "POST",
                    //                         //ajax需要添加打包名
                    //                         url: "recycle/recover",
                    //                         data: myData,
                    //                         contentType: "application/json",
                    //                         success: function (result) {
                    //                             window.Ewin.alert({message: result.errMsg});
                    //                             $table.bootstrapTable("refresh");
                    //                         },
                    //                         error: function (info) {
                    //                             window.Ewin.alert({message: ":" + info.status});
                    //                         }
                    //                     })
                    //                 }
                    //             });
                    //         }
                    //     }
                    // ],
                });
                //$table.bootstrapTable('hideColumn','puid');
                $table.bootstrapTable('hideColumn', 'puid');
                $table.bootstrapTable('hideColumn', 'groupNum');
                $table.bootstrapTable('hideColumn', 'rank');
            }
        });
    }
    // }
    else if (type == 2) {
        $table.bootstrapTable('destroy');
        // function initTable() {
        $.ajax({
            url: "pbom/manage/title?project=" + projectPuid,
            type: "GET",
            success: function (result) {
                var column = [];
                column.push({field: 'eBomPuid', title: '主键'});
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
                }
                $table.bootstrapTable({
                    url: bomUrl,
                    method: 'GET',
                    dataType: 'json',
                    cache: false,
                    striped: true,                              //是否显示行间隔色
                    sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
                    height: $(window.parent.document).find("#wrapper").height() - 180,
                    width: $(window).width(),
                    formId: "queryPbomManage",
                    undefinedText: "",//当数据为 undefined 时显示的字符
                    pagination: true,
                    pageNumber: 1,                       //初始化加载第一页，默认第一页
                    pageSize: 20,                       //每页的记录行数（*）
                    pageList: ['ALL',20,50,100,200,500,1000],        //可供选择的每页的行数（*）
                    uniqueId: "puid",                     //每一行的唯一标识，一般为主键列
                    showExport: true,
                    columns: column,
                    sortable: true,                     //是否启用排序
                    sortOrder: "asc",                   //排序方式
                    clickToSelect: true,// 单击某一行的时候选中某一条记录
                    striped: true, //是否显示行间隔色
                    showColumns: true, //是否显示所有的列
                    showToggle: false,                   //是否显示详细视图和列表视图的切换按钮
                    showRefresh: true,                  //是否显示刷新按钮
                    // toolbars: [
                    //     {
                    //         text: '还原',
                    //         iconCls: 'glyphicon glyphicon-share',
                    //         handler: function () {
                    //             var rows = $table.bootstrapTable('getSelections');
                    //             var myData = JSON.stringify({
                    //                 "projectId": $("#project", window.top.document).val(),
                    //                 "puid": rows[0].eBomPuid,
                    //                 "type":type
                    //             });
                    //             if (rows.length !=1) {
                    //                 window.Ewin.alert({message: '请选择一条需要还原的数据!'});
                    //                 return false;
                    //             }
                    //             window.Ewin.confirm({
                    //                 title: '提示',
                    //                 message: '是否要还原您所选择的记录？',
                    //                 width: 500
                    //             }).on(function (e) {
                    //                 if (e) {
                    //                     $.ajax({
                    //                         type: "POST",
                    //                         //ajax需要添加打包名
                    //                         url: "recycle/recover",
                    //                         data: myData,
                    //                         contentType: "application/json",
                    //                         success: function (result) {
                    //                             window.Ewin.alert({message: result.errMsg});
                    //                             $table.bootstrapTable("refresh");
                    //                         },
                    //                         error: function (info) {
                    //                             window.Ewin.alert({message: "操作失败:" + info.status});
                    //                         }
                    //                     })
                    //                 }
                    //             });
                    //         }
                    //     }
                    // ],
                });
                $table.bootstrapTable('hideColumn', 'eBomPuid');
                $table.bootstrapTable('hideColumn', 'level');
                $table.bootstrapTable('hideColumn', 'groupNum');
                $table.bootstrapTable('hideColumn', 'pBomLinePartEnName');
            }
        });
        // }

    }
    else if (type == 3) {
        $table.bootstrapTable('destroy');
        // function initTable() {
        $.ajax({
            url: "mbom/manage/title?projectId=" + projectPuid,
            type: "GET",
            success: function (result) {
                var column = [];
                column.push({field: 'eBomPuid', title: 'puid'});
                column.push({field: 'ck', checkbox: true, Width: 50});
                var data = result.data;
                var keys = [];
                var values;
                for (var key in data) {
                    if (data.hasOwnProperty(key)) {
                        // keys.push(key);
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
                }
                $table.bootstrapTable({
                    url: bomUrl,
                    method: 'GET',
                    dataType: 'json',
                    cache: false,
                    striped: true,                              //是否显示行间隔色
                    sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
                    height: $(window.parent.document).find("#wrapper").height() - 180,
                    width: $(window).width(),
                    formId: "queryMbomMain",
                    undefinedText: "",//当数据为 undefined 时显示的字符
                    pagination: true,
                    pageNumber: 1,                       //初始化加载第一页，默认第一页
                    pageSize: 20,                       //每页的记录行数（*）
                    pageList: ['ALL',20,50,100,200,500,1000],        //可供选择的每页的行数（*）
                    uniqueId: "puid",                     //每一行的唯一标识，一般为主键列
                    showExport: true,
                    //exportDataType: 'all',
                    columns: column,
                    sortable: true,                     //是否启用排序
                    sortOrder: "asc",                   //排序方式
                    clickToSelect: true,// 单击某一行的时候选中某一条记录
                    striped: true, //是否显示行间隔色
                    //search: true, //是否显示表格搜索，此搜索是客户端搜索，不会进服务端
                    showColumns: true, //是否显示所有的列
                    /*fixedColumns: true,
                    fixedNumber:1,*/
                    showToggle: false,                   //是否显示详细视图和列表视图的切换按钮
                    showRefresh: true,                  //是否显示刷新按钮
                    minimumCountColumns: 4,
                    // toolbars: [
                    //     {
                    //         text: '还原',
                    //         iconCls: 'glyphicon glyphicon-share',
                    //         handler: function () {
                    //             var rows = $table.bootstrapTable('getSelections');
                    //
                    //             var myData = JSON.stringify({
                    //                 "projectId": $("#project", window.top.document).val(),
                    //                 "puid": rows[0].eBomPuid,
                    //                 "type":type
                    //             });
                    //             if (rows.length !=1) {
                    //                 window.Ewin.alert({message: '请选择一条需要还原的数据!'});
                    //                 return false;
                    //             }
                    //             window.Ewin.confirm({
                    //                 title: '提示',
                    //                 message: '是否要还原您所选择的记录？',
                    //                 width: 500
                    //             }).on(function (e) {
                    //                 if (e) {
                    //                     $.ajax({
                    //                         type: "POST",
                    //                         //ajax需要添加打包名
                    //                         url: "recycle/recover",
                    //                         data: myData,
                    //                         contentType: "application/json",
                    //                         success: function (result) {
                    //                             /*if (result.status) {
                    //                                 window.Ewin.alert({message: result.errMsg});
                    //                                 //刷新，会重新申请数据库数据
                    //                             }
                    //                             else {
                    //                                 window.Ewin.alert({message: ":" + result.errMsg});
                    //                             }*/
                    //                             window.Ewin.alert({message: result.errMsg});
                    //                             $table.bootstrapTable("refresh");
                    //                         },
                    //                         error: function (info) {
                    //                             window.Ewin.alert({message: "操作失败:" + info.status});
                    //                         }
                    //                     })
                    //                 }
                    //             });
                    //         }
                    //     }
                    // ],
                });
                $table.bootstrapTable('hideColumn', 'eBomPuid');
            }
        });
        // }
    }
}

// function initTable() {
//     var projectPuid = $("#project", window.top.document).val();
//     var $table = $("#recycleBinTable");
//     var eBomUrl ="ebom/getEBom/list?projectId=" + projectPuid;
//     var column = [];
//     $.ajax({
//         url: "ebom/ebomTitle?projectId=" + projectPuid,
//         type: "GET",
//         success: function (result) {
//             var column = [];
//             // column.push({field: 'eBomPuid', title: 'puid'});
//             column.push({field: 'ck', checkbox: true});
//             var data = result.data;
//             var nameZh = data[0];
//             var nameEn = data[1];
//             var keys = [];
//             var values;
//             for (var key in nameEn) {
//                 if (nameEn.hasOwnProperty(key)) {
//                     var json = {
//                         field: nameEn[key],
//                         title: nameZh[key],
//                         align:
//                             'center',
//                         valign:
//                             'middle'
//                     };
//                     column.push(json);
//                 }
//             }
//             $table.bootstrapTable({
//                 url: eBomUrl,
//                 method: 'GET',
//                 dataType: 'json',
//                 sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
//                 height: $(window.parent.document).find("#wrapper").height() - 180,
//                 width: $(window).width(),
//                 formId: "queryBom",
//                 undefinedText: "",//当数据为 undefined 时显示的字符
//                 pagination: true,
//                 pageNumber: 1,                       //初始化加载第一页，默认第一页
//                 pageSize: 20,                       //每页的记录行数（*）
//                 pageList: [20, 50, 100, 200, 500, 1000, 'ALL'],        //可供选择的每页的行数（*）
//                 columns: column,
//                 sortOrder: "asc",                   //排序方式
//                 clickToSelect: true,// 单击某一行的时候选中某一条记录
//                 showColumns: true, //是否显示所有的列
//                 showToggle: false,                   //是否显示详细视图和列表视图的切换按钮
//                 showRefresh: true,                  //是否显示刷新按钮
//                 toolbars: [
//                     {
//                         text: '还原',
//                         iconCls: 'glyphicon glyphicon-remove',
//                         handler: function () {
//                             var rows = $table.bootstrapTable('getSelections');
//                             var myData = JSON.stringify({
//                                 "projectId": $("#project", window.top.document).val(),
//                                 "puid": rows[0].puid,
//                             });
//                             if (rows.length == 0 && rows.length > 1) {
//                                 window.Ewin.alert({message: '请选择一条需要还原的数据!'});
//                                 return false;
//                             }
//                             window.Ewin.confirm({title: '提示', message: '是否要还原您所选择的记录？', width: 500}).on(function (e) {
//                                 if (e) {
//                                     $.ajax({
//                                         type: "POST",
//                                         //ajax需要添加打包名
//                                         url: "ebom/delete/ebom",
//                                         data: myData,
//                                         contentType: "application/json",
//                                         success: function (result) {
//                                             window.Ewin.alert({message: result.errMsg});
//                                             $table.bootstrapTable("refresh");
//                                         },
//                                         error: function (info) {
//                                             window.Ewin.alert({message: ":" + info.status});
//                                         }
//                                     })
//                                 }
//                             });
//                         }
//                     }
//                 ],
//             });
//             $table.bootstrapTable('hideColumn', 'puid');
//             $table.bootstrapTable('hideColumn', 'groupNum');
//             $table.bootstrapTable('hideColumn', 'rank');
//         }
//     });
// }
$(document).keydown(function(event) {
    if (event.keyCode == 13) {
        $('form').each(function() {
            event.preventDefault();
        });
    }
});