// 已废除
var type ;
$(document).ready((function (){
    // $("#ms").multiselect();
    var projectId = $("#project", window.top.document).val();
    var url = "work/process/record/page?projectId="+projectId;
    initTable(url);
}))
function doRefresh(projectId){
    $('#routingDataTable').bootstrapTable('destroy');
    var url = "work/process/record/page?projectId="+projectId;
    initTable(url);
}
function doQuery() {
    //$('#routingDataTable').bootstrapTable('refresh');    //刷新表格
    var projectId = $("#project", window.top.document).val();
    var url = "work/process/record/page?projectId="+projectId;
    // var pMaterielCode = $("#pMaterielCode").val();
    // url+="&pMaterielCode="+pMaterielCode;
    // var pMaterielDesc = $("#pMaterielDesc").val();
    // url+="&pMaterielDesc="+pMaterielDesc;
    type = $("#type").val();
    if (type=="请选择工艺路线") {
        url += "&type="+"";
    }
    else {
        url += "&type="+type;
    }
    initTable(url);
    $('#routingDataTable').bootstrapTable('destroy');
}


function initTable(url) {
    var projectId = $("#project", window.top.document).val();
    if (!checkIsSelectProject(projectId)) {
        return;
    }
    var  $table =  $("#routingDataTable");
    var  column = [];
    $.ajax({
        url:"work/process/title",
        type:"GET",
        success:function(result){
            var column = [];
            // column.push({field: 'materielId', title: 'puid'});
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
                        // align:
                        //     'center',
                        valign:
                            'middle'
                    };
                    column.push(json);
                }
            };
            $table.bootstrapTable({
                url: url,
                method: 'GET',
                dataType: 'json',
                cache: false,
                striped: true,                              //是否显示行间隔色
                sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
                height: $(window.parent.document).find("#wrapper").height() - 150,
                width: $(window).width(),
                formId :"queryRoutingData",
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
                //minimumCountColumns: 4,
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
                                url: "work/process/addWorkProcess?projectId="+projectId+"&materielId="+rows[0].materielId,
                                gridId: "gridId",
                                width: 500,
                                height: 500
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
                                url: "work/process/updateWorkProcess?projectId="+projectId+"&materielId="+rows[0].materielId,
                                gridId: "gridId",
                                width: 500,
                                height: 500
                            });
                        }
                    },
                    {
                        text: '选择工艺',
                        iconCls: 'glyphicon glyphicon-pencil',
                        handler: function () {
                            var rows = $table.bootstrapTable('getSelections');
                            if (type!="1"&&type!="2"){
                                window.Ewin.alert({message:'请先选择工艺路线'})
                                return false;
                            }
                            var puids = "";
                            for (var i = 0; i < rows.length; i++) {
                                puids += rows[i].materielId + ",";
                            };
                            var myData = JSON.stringify({
                                "projectId": $("#project", window.top.document).val(),
                                "puids": puids,
                            });
                            if (rows.length < 1) {
                                window.Ewin.alert({message: '请至少选择一条需要修改的数据!'});
                                return false;
                            }
                            window.Ewin.dialog({
                                title: "修改四大工艺",
                                // url: "work/process/updateWorkProcess?projectId="+projectId+"&materielId="+rows[0].materielId,
                                url:"work/process/four?projectId="+projectId+"&puids="+puids,
                                gridId: "gridId",
                                // data:myData,
                                width: 350,
                                height: 300,
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
                            var _table = '<p>是否要删除您所选择的记录？</p>' +
                                '<div style="max-height: 400px;overflow:scroll;"><table class="table table-striped tableNormalStyle" >';
                            for (var index in rows) {
                                _table += '<tr><td>' + rows[index].pMaterielDesc + '</td></tr>';
                            }
                            _table += '</table></div>';
                            window.Ewin.confirm({title: '提示', message: _table, width: 500}).on(function (e) {
                                if (e) {
                                    $.ajax({
                                        type: "POST",
                                        //ajax需要添加打包名
                                        url: "work/process/delete?materielId="+rows[0].materielId,
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
                                            if (result.success){
                                                layer.msg('删除成功', {icon: 1, time: 2000})
                                            }
                                            else if (!result.success) {
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
                    },
                    {
                        text: '发送MBOM到SAP',
                        iconCls: 'glyphicon glyphicon-remove',
                        handler: function () {
                            var rows = $table.bootstrapTable('getSelections');
                            var projectId = $("#project", window.top.document).val();
                            if (rows.length == 0) {
                                window.Ewin.alert({message: '请选择一条需要发送的数据!'});
                                return false;
                            }
                            var _table = '<p>是否要发送您所选择的记录？</p>' +
                                '<div style="max-height: 400px;overflow:scroll;"><table class="table table-striped tableNormalStyle" >';
                            for (var index in rows) {
                                _table += '<tr><td>' + rows[index].pMaterielDesc + '</td></tr>';
                            }
                            _table += '</table></div>';
                            window.Ewin.confirm({title: '提示', message: _table, width: 500}).on(function (e) {
                                if (e) {
                                    var materielIds = new Array();
                                    for(var i=0;i<rows.length;i++){
                                        materielIds[i] = rows[i].materielId;
                                    }
                                    $.ajax({
                                        type: "POST",
                                        //ajax需要添加打包名
                                        url: "work/process/submit?projectId="+projectId+"&materielIds="+materielIds,
                                        success: function (result) {
                                            if (result.success){
                                                layer.msg('提交成功', {icon: 1, time: 2000})
                                            }
                                            else if (!result.success) {
                                                window.Ewin.alert({message: result.errMsg});
                                            }
                                            $table.bootstrapTable("refresh");
                                        },
                                        error: function (info) {
                                            window.Ewin.alert({message: "操作提交:" + info.status});
                                        }
                                    })
                                }
                            });
                        }
                    },
                    {
                        text: '删除MBOM到SAP',
                        iconCls: 'glyphicon glyphicon-remove',
                        handler: function () {
                            var rows = $table.bootstrapTable('getSelections');
                            var projectId = $("#project", window.top.document).val();
                            if (rows.length == 0) {
                                window.Ewin.alert({message: '请选择一条需要发送的数据!'});
                                return false;
                            }
                            var _table = '<p>是否要发送您所选择的记录？</p>' +
                                '<div style="max-height: 400px;overflow:scroll;"><table class="table table-striped tableNormalStyle" >';
                            for (var index in rows) {
                                _table += '<tr><td>' + rows[index].pMaterielDesc + '</td></tr>';
                            }
                            _table += '</table></div>';
                            window.Ewin.confirm({title: '提示', message: _table, width: 500}).on(function (e) {
                                if (e) {
                                    var materielIds = new Array();
                                    for(var i=0;i<rows.length;i++){
                                        materielIds[i] = rows[i].materielId;
                                    }
                                    $.ajax({
                                        type: "POST",
                                        //ajax需要添加打包名
                                        url: "work/process/delete1?projectId="+projectId+"&materielIds="+materielIds,
                                        success: function (result) {
                                            if (result.success){
                                                layer.msg('提交成功', {icon: 1, time: 2000})
                                            }
                                            else if (!result.success) {
                                                window.Ewin.alert({message: result.errMsg});
                                            }
                                            $table.bootstrapTable("refresh");
                                        },
                                        error: function (info) {
                                            window.Ewin.alert({message: "操作提交:" + info.status});
                                        }
                                    })
                                }
                            });
                        }
                    },
                    {
                        text: '修改MBOM到SAP',
                        iconCls: 'glyphicon glyphicon-remove',
                        handler: function () {
                            var rows = $table.bootstrapTable('getSelections');
                            var projectId = $("#project", window.top.document).val();
                            if (rows.length == 0) {
                                window.Ewin.alert({message: '请选择一条需要发送的数据!'});
                                return false;
                            }
                            var _table = '<p>是否要发送您所选择的记录？</p>' +
                                '<div style="max-height: 400px;overflow:scroll;"><table class="table table-striped tableNormalStyle" >';
                            for (var index in rows) {
                                _table += '<tr><td>' + rows[index].pMaterielDesc + '</td></tr>';
                            }
                            _table += '</table></div>';
                            window.Ewin.confirm({title: '提示', message: _table, width: 500}).on(function (e) {
                                if (e) {
                                    var materielIds = new Array();
                                    for(var i=0;i<rows.length;i++){
                                        materielIds[i] = rows[i].materielId;
                                    }
                                    $.ajax({
                                        type: "POST",
                                        //ajax需要添加打包名
                                        url: "work/process/updata?projectId="+projectId+"&materielIds="+materielIds,
                                        success: function (result) {
                                            if (result.success){
                                                layer.msg('提交成功', {icon: 1, time: 2000})
                                            }
                                            else if (!result.success) {
                                                window.Ewin.alert({message: result.errMsg});
                                            }
                                            $table.bootstrapTable("refresh");
                                        },
                                        error: function (info) {
                                            window.Ewin.alert({message: "操作提交:" + info.status});
                                        }
                                    })
                                }
                            });
                        }
                    },
                    /*{
                        text: '选取数据',
                        iconCls: 'glyphicon glyphicon-save',
                        handler: function () {
                                    window.Ewin.dialog({
                                        title: "选取数据",
                                        url: "work/process/getMBom",
                                        gridId: "gridId2",
                                        width: 700,
                                        height:650
                                    })
                                }
                    }*/
                    // ,
                    // {
                    //     text: '选取数据',
                    //     iconCls: 'glyphicon glyphicon-save',
                    //     handler: function () {
                    //         window.Ewin.dialog({
                    //             title: "选取数据",
                    //             url: "work/process/getMBom",
                    //             gridId: "gridId",
                    //             width: 500,
                    //             height:650
                    //         })
                    //     }
                    // },
                ],
            });
            // $table.bootstrapTable('hideColumn', 'materielId');
        }
    })
}
$(document).keydown(function(event) {
    if (event.keyCode == 13) {
        $('form').each(function() {
            event.preventDefault();
        });
    }
});