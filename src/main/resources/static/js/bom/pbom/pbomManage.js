$(document).ready((function () {
    var projectPuid = $("#project", window.top.document).val();
    var pBomUrl ="pbom/getBomManage?projectId=" + projectPuid;
    initTable(pBomUrl);
}))

function doQuery() {
    //$('#pbomManageTable').bootstrapTable('refresh');    //刷新表格
    var projectPuid = $("#project", window.top.document).val();
    var pBomUrl ="pbom/getBomManage?projectId=" + projectPuid;
    var level = $("#level").val();
    pBomUrl+="&level="+level;
    var pBomOfWhichDept = $("#pBomOfWhichDept").val();
    pBomUrl+="&pBomOfWhichDept="+pBomOfWhichDept;
    var lineId = $("#lineId").val();
    pBomUrl += "&lineId="+lineId;
    initTable(pBomUrl);
    $('#pbomManageTable').bootstrapTable('destroy');
}

function initTable(pBomUrl) {
    var $table = $("#pbomManageTable");
    var column = [];
    var projectPuid = $("#project", window.top.document).val();
    $.ajax({
        url: "pbom/manage/title?project=" + projectPuid,
        type: "GET",
        success: function (result) {
            var column = [];
            column.push({field: 'eBomPuid', title: '主键'});
            column.push({field: 'ck', checkbox: true, Width: 50});
            // column.push({
            //     field: '',
            //     title: '序号',
            //     formatter: function (value, row, index) {
            //         return index + 1;
            //     },
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
                url:pBomUrl,
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
                //minimumCountColumns:4,
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
                                url: "pbom/addPbomManage?projectId=" + projectPuid + "&eBomPuid=" + rows[0].eBomPuid,
                                gridId: "gridId",
                                width: 500,
                                height: 500
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
                                url: "pbom/updatePbomManage?projectId=" + projectPuid + "&eBomPuid=" + rows[0].eBomPuid,
                                gridId: "gridId",
                                width: 500,
                                height: 500
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
                                window.Ewin.alert({message: '请至少选择一条需要删除的数据!'});
                                return false;
                            }
                            var _table = '<p>是否要删除您所选择的记录？</p>' +
                                '<div style="max-height: 400px;overflow:scroll;"><table class="table table-striped tableNormalStyle" >';
                            for (var index in rows) {
                                _table += '<tr><td>' + rows[index].lineId + '</td></tr>';
                            }
                            _table += '</table></div>';
                            window.Ewin.confirm({title: '提示', message:_table, width: 500}).on(function (e) {
                                if (e) {
                                    $.ajax({
                                        type: "POST",
                                        //ajax需要添加打包名
                                        url: "pbom/delete",
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
                                            if (result.success){
                                                layer.msg('删除成功', {icon: 1, time: 2000})
                                            } else if (!result.success){
                                                window.Ewin.alert({message: result.errMsg});
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
                    }
                ],
            });
            $table.bootstrapTable('hideColumn', 'eBomPuid');
            $table.bootstrapTable('hideColumn','level');
            $table.bootstrapTable('hideColumn','groupNum');
            $table.bootstrapTable('hideColumn','pBomLinePartEnName');
        }
    });
}