$(document).ready((function () {
    var projectId = $("#project", window.top.document).val();
    var url = "materiel/getMateriel?projectId=" + projectId;
    initTable(url);
}))

function doQuery() {
    //$('#materialDataTable').bootstrapTable('refresh');    //刷新表格
    var projectId = $("#project", window.top.document).val();
    var url = "materiel/getMateriel?projectId=" + projectId;
    var pMaterielCode = $("#pMaterielCode").val();
    url += "&pMaterielCode=" + pMaterielCode;
    var pMaterielType = $("#pMaterielType").val();
    url += "&pMaterielType=" + pMaterielType;
    var pMaterielDataType = $("#pMaterielDataType").val();
    if (pMaterielDataType == "车型项目物料总表") {
        url += "&pMaterielDataType=" + "";
    }
    else {
        url += "&pMaterielDataType=" + pMaterielDataType;
    }
    initTable(url);
    $('#materialDataTable').bootstrapTable('destroy');
}

function initTable(url) {
    var projectId = $("#project", window.top.document).val();
    var currentProjectHead = $("#currentProjectHead", window.top.document).val();
    var $table = $("#materialDataTable");
    var column = [];
    $.ajax({
        url: "materiel/title",
        type: "GET",
        success: function (result) {
            var column = [];
            column.push({field: 'ck', checkbox: true, Width: 50});
            /*column.push({field: '',
                title: '序号',
                formatter: function (value, row, index) {
                    return index+1;},
                align:
                    'center',
                valign:
                    'middle'
            });*/
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
                    url: url,
                    method: 'GET',
                    dataType: 'json',
                    cache: false,
                    striped: true,                              //是否显示行间隔色
                    sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
                    height: $(window.parent.document).find("#wrapper").height() - 180,
                    width: $(window).width(),
                    formId: "queryMaterialData",
                    undefinedText: "",//当数据为 undefined 时显示的字符
                    pagination: true,
                    pageNumber:1,                       //初始化加载第一页，默认第一页
                    pageSize: 20,                       //每页的记录行数（*）
                    pageList: [20, 50,100,200,500,1000,'ALL'],     //可供选择的每页的行数（*）
                    uniqueId: "puid",                     //每一行的唯一标识，一般为主键列
                    showExport: true,
                    //exportDataType: 'all',
                    columns: column,
                    sortable: true,                     //是否启用排序
                    sortOrder: "asc",                   //排序方式
                    clickToSelect: true,               // 单击某一行的时候选中某一条记录
                    striped: true,                      //是否显示行间隔色
                    //search: true,                      //是否显示表格搜索，此搜索是客户端搜索，不会进服务端
                    showColumns: true,                  //是否显示所有的列
                    /*fixedColumns: true,
                    fixedNumber:1,*/
                    showToggle: false,                   //是否显示详细视图和列表视图的切换按钮
                    showRefresh: true,                  //是否显示刷新按钮
                    //minimumCountColumns: 4,
                    toolbars: [
                        {
                            text: '添加',
                            iconCls: 'glyphicon glyphicon-plus',
                            handler: function () {
                                var rows = $table.bootstrapTable('getSelections');
                                //只能选一条
                                if (rows.length != 1) {
                                    window.Ewin.alert({message: '请选择一条需要添加的数据!'});
                                    return false;
                                }
                                window.Ewin.dialog({
                                    title: "添加",
                                    url: "materiel/addMateriel?projectId=" + projectId + "&puid=" + rows[0].puid,
                                    gridId: "gridId",
                                    width: 500,
                                    height: 650
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
                                window.Ewin.dialog({
                                    title: "修改",
                                    url: "materiel/updateMBom?projectId=" + projectId + "&puid=" + rows[0].puid,
                                    gridId: "gridId",
                                    width: 500,
                                    height: 650
                                });
                            }
                        },
                        {
                            text: '删除',
                            iconCls: 'glyphicon glyphicon-remove',
                            handler: function () {
                                var rows = $table.bootstrapTable('getSelections');
                                if (rows.length == 0) {
                                    window.Ewin.alert({message: '请选择一条需要删除的数据!'});
                                    return false;
                                }
                                window.Ewin.confirm({title: '提示', message: '是否要删除您所选择的记录？', width: 500}).on(function (e) {
                                    if (e) {
                                        $.ajax({
                                            type: "POST",
                                            //ajax需要添加打包名
                                            url: "materiel/delete?puid=" + rows[0].puid,
                                            //data: JSON.stringify(rows),
                                            contentType: "application/json",
                                            success: function (result) {
                                                /*if (result.status) {
                                                    window.Ewin.alert({message: result.errMsg});
                                                    //刷新，会重新申请数据库数据
                                                }
                                                else {
                                                    window.Ewin.alert({message: ":" + result.errMsg});
                                                }*/
                                                window.Ewin.alert({message: result.errMsg});
                                                $table.bootstrapTable("refresh");
                                            },
                                            error: function (info) {
                                                window.Ewin.alert({message: "操作失败:" + info.status});
                                            }
                                        })
                                    }
                                });
                            }
                        },
                        {
                            text: '发送SAP',
                            iconCls: 'glyphicon glyphicon-send',
                            handler: function () {
                                var rows = $table.bootstrapTable('getSelections');
                                var confirm = undefined;
                                var localUrl = "";
                                var datas = null;
                                if (rows.length == 0) {
                                    window.Ewin.confirm({
                                        title: '提示',
                                        message: '<p ><strong style="color: red">注意：正在进行耗时操作</strong></p>' +
                                        '您未选择任何一条数据，是否将当前项目' + currentProjectHead + '下的所有物料数据发送到ERP系统?',
                                        width: 600
                                    }).on(function (e) {
                                        //发送全部
                                        if (e) {
                                            localUrl = "synMateriel/synAllByProjectPuid?projectId=" + projectId;
                                            datas = null;
                                            $.ajax({
                                                type: "POST",
                                                //ajax需要添加打包名
                                                url: localUrl,
                                                data: datas,
                                                contentType: "application/json",
                                                success: function (result) {
                                                    // if (result.status) {
                                                    //     window.Ewin.alert({message: result.msg});
                                                    //     //刷新，会重新申请数据库数据
                                                    // }
                                                    // else {
                                                    //     window.Ewin.alert({message: ":" + result.errMsg});
                                                    // }
                                                    window.Ewin.alert({message: result, width: 800});
                                                    $table.bootstrapTable("refresh");
                                                },
                                                error: function (info) {
                                                    window.Ewin.alert({message: "操作删除:" + info.status});
                                                }
                                            })
                                        }
                                    });
                                }
                                else {
                                    window.Ewin.confirm({
                                        title: '提示',
                                        message: '是否要重新发送您所选择的记录？',
                                        width: 600
                                    }).on(function (e) {
                                        if (e) {
                                            localUrl = "synMateriel/updateOrAddByUids";
                                            datas = JSON.stringify(rows);
                                            $.ajax({
                                                type: "POST",
                                                //ajax需要添加打包名
                                                url: localUrl,
                                                data: datas,
                                                contentType: "application/json",
                                                success: function (result) {
                                                    // if (result.status) {
                                                    //     window.Ewin.alert({message: result.msg});
                                                    //     //刷新，会重新申请数据库数据
                                                    // }
                                                    // else {
                                                    //     window.Ewin.alert({message: ":" + result.errMsg});
                                                    // }
                                                    window.Ewin.alert({message: result, width: 800});
                                                    $table.bootstrapTable("refresh");
                                                },
                                                error: function (info) {
                                                    window.Ewin.alert({message: "操作失败:" + info.status});
                                                }
                                            })
                                        }
                                    });
                                }
                            }
                        }
                    ]
                }
            );
            $table.bootstrapTable('hideColumn', 'puid');
        }
    })
}