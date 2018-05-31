var firstLoad = true;
$(document).ready(
    $("#queryPbomMaintenance").click(function () {
        var $table = $("#pbomMaintenanceTable");
        var column = [];
        $.ajax({
            url: "pbom/maintain/title",
            type: "GET",
            success: function (result) {
                var column = [];
                column.push({field: 'pBomPuid', title: 'puid'});
                column.push({field: 'ck', checkbox: true, Width: 50});
                column.push({field: '',
                    title: '序号',
                    formatter: function (value, row, index) {
                        return index+1;},
                    align:
                        'center',
                    valign:
                        'middle'
                });
                var data = result.data;
                console.log(data);
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
                    url: "pbom/getMaintain/detail",
                    method: 'get',
                    height: $(window.parent.document).find("#wrapper").height() - 252,
                    width: $(window).width(),
                    showToggle: true,                   //是否显示详细视图和列表视图的切换按钮
                    showRefresh: true,                  //是否显示刷新按钮
                    // pageSize: 10,
                    pagination: false,                   //是否显示分页（*）
                    clickToSelect: true,                // 单击某一行的时候选中某一条记录
                    formId: "queryPbomMain",
                    /**列信息，需要预先定义好*/
                    columns: column,
                    sortable: true,                     //是否启用排序
                    sortOrder: "asc",                   //排序方式
                    toolbars: [
                        /*{
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
                                    url: "pbom/insert/list/maintain?pBomPuid=" + rows[0].pBomPuid,
                                    //url:"pbom/insert/list/maintain",
                                    gridId: "gridId",
                                    width: 500,
                                    height: 600
                                })
                            }
                        },*/
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
                                    url: "pbom/update/list/maintain?pBomPuid=" + rows[0].pBomPuid,
                                    gridId: "gridId",
                                    width: 500,
                                    height: 600
                                });
                            }
                        },
                        /*{
                            text: '删除',
                            iconCls: 'glyphicon glyphicon-remove',
                            handler: function () {
                                var rows = $table.bootstrapTable('getSelections');
                                if (rows.length == 0) {
                                    window.Ewin.alert({message: '请选择一条需要删除的数据!'});
                                    return false;
                                }
                                //测试数据
                                window.Ewin.confirm({
                                    title: '提示',
                                    message: '是否要删除您所选择的记录？',
                                    width: 500
                                }).on(function (e) {
                                    if (e) {
                                        $.ajax({
                                            type: "POST",
                                            //ajax需要添加打包名
                                            url: "./pbom/delete/maintain",
                                            data: JSON.stringify(rows),
                                            contentType: "application/json",
                                            success: function (result) {
                                                if (result) {
                                                    window.Ewin.alert({message: "删除时数据成功"});
                                                    //刷新，会重新申请数据库数据
                                                }
                                                else {
                                                    window.Ewin.alert({message: "操作删除失败:" + result.msg});
                                                }
                                                $table.bootstrapTable("refresh");
                                            },
                                            error: function (info) {
                                                window.Ewin.alert({message: "操作删除:" + info.status});
                                            }
                                        })
                                    }
                                });
                            }
                        }*/
                    ]
                });
                // $table.bootstrapTable('hideColumn', 'pBomPuid');
            }
        });
    }),


    $("#queryBtn").click(function () {
        var myData = JSON.stringify({
            "level": $("#level").val(),
            "pBomOfWhichDept": $("#pBomOfWhichDept").val(),
            "lineId": $("#lineId").val(),
        });
        $.ajax({
            contentType: "application/json",
            type:"GET",
            url:"pbom/searchMaintain/detail",
            data:myData,
            success:
            function (data) {
                var result = data.data;
                console.log(result);
            },
            error:function (info) {
                alert(info);
            }
        })
    })
);