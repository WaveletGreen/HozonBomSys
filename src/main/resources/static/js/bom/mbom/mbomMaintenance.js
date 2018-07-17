$(document).ready((function () {
    var projectPuid = $("#project", window.top.document).val();
    var mBomUrl = "mbom/record?projectId=" + projectPuid;
    initTable(mBomUrl);
}))


function doQuery(){
    //$('#eplTable').bootstrapTable('refresh');    //刷新表格
    var projectPuid = $("#project", window.top.document).val();
    var mBomUrl = "mbom/record?projectId=" + projectPuid;
    var level = $("#level").val();
    mBomUrl+="&level="+level;
    var pBomOfWhichDept = $("#pBomOfWhichDept").val();
    mBomUrl+="&pBomOfWhichDept="+pBomOfWhichDept;
    var lineId = $("#lineId").val();
    mBomUrl += "&lineId="+lineId;
    initTable(mBomUrl);
    $('#mbomMaintenanceTable').bootstrapTable('destroy');
}
function initTable(mBomUrl){
    var projectPuid = $("#project", window.top.document).val();
    var $table = $("#mbomMaintenanceTable");
    var column = [];
    $.ajax({
        url: "mbom/manage/title?projectId=" + projectPuid,
        type: "GET",
        success: function (result) {
            var column = [];
            column.push({field: 'eBomPuid', title: 'puid'});
            column.push({field: 'ck', checkbox: true, Width: 50});
            // column.push({field: '',
            //     title: '序号',
            //     formatter: function (value, row, index) {
            //         return index+1;},
            //     align:
            //         'center',
            //     valign:
            //         'middle'
            // });
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
                url: mBomUrl,
                method: 'GET',
                dataType: 'json',
                cache: false,
                striped: true,                              //是否显示行间隔色
                sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
                height: $(window.parent.document).find("#wrapper").height() - 180,
                width: $(window).width(),
                formId :"queryMbomMain",
                undefinedText: "",//当数据为 undefined 时显示的字符
                pagination: true,
                pageNumber:1,                       //初始化加载第一页，默认第一页
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
                                url: "mbom/addMBom?projectId="+projectPuid+"&eBomPuid="+rows[0].eBomPuid,
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
                                url: "mbom/updateMBom?projectId="+projectPuid+"&eBomPuid="+rows[0].eBomPuid,
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
                            var puids = "";
                            for (var i = 0 ; i<rows.length;i++){
                                puids += rows[i].eBomPuid+",";
                            };
                            var myData = JSON.stringify({
                                "projectId": $("#project", window.top.document).val(),
                                "puids":puids,
                            });
                            if (rows.length == 0) {
                                window.Ewin.alert({message: '请选择一条需要删除的数据!'});
                                return false;
                            }
                            window.Ewin.confirm({title: '提示', message: '是否要删除您所选择的记录？', width: 500}).on(function (e) {
                                if (e) {
                                    $.ajax({
                                        type: "POST",
                                        //ajax需要添加打包名
                                        url: "mbom/delete",
                                        data: myData,
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
                                            window.Ewin.alert({message: "操作删除:" + info.status});
                                        }
                                    })
                                }
                            });
                        }
                    },
                    {
                        text: '发送到工艺路线',
                        iconCls: 'glyphicon glyphicon-send',
                        handler: function () {
                            var rows = $table.bootstrapTable('getSelections');
                            if (rows.length == 0) {
                                window.Ewin.alert({message: '请选择需要发送的数据!'});
                                return false;
                            }
                            window.Ewin.confirm({title: '提示', message: '是否要发送您所选择的记录？', width: 500}).on(function (e) {
                                if (e) {
                                    $.ajax({
                                        type: "POST",
                                        //ajax需要添加打包名
                                        url: "/delete?eBomPuid="+rows[0].eBomPuid,
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
                                            window.Ewin.alert({message: "操作删除:" + info.status});
                                        }
                                    })
                                }
                            });
                        }
                    }
                ],
            });
            $table.bootstrapTable('hideColumn', 'eBomPuid');
        }
    });
}