var firstLoad = true;
window.onload = function(){
        var projectPuid = $("#project", window.top.document).val();
        var $table = $("#ebomManageTable");
        var column = [];
        $.ajax({
            url: "ebom/ebomTitle?projectId="+projectPuid,
            type: "GET",
            success: function (result) {
                var column = [];
                // column.push({field: 'eBomPuid', title: 'puid'});
                column.push({field: 'ck', checkbox: true});
                var data = result.data;
                var nameZh =data[0];
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
                }
                $table.bootstrapTable({
                    url: "ebom/getEBom/list?projectId="+projectPuid,
                    method: 'get',
                    dataType:'json',
                    //cache: false,
                    //striped: true,                              //是否显示行间隔色
                    //sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
                    height: $(window.parent.document).find("#wrapper").height() - 90,
                    width: $(window).width(),
                     pagination:true,
                    // pageNumber:1,                       //初始化加载第一页，默认第一页
                     pageSize: 20,                       //每页的记录行数（*）
                    // pageList: [20, 50,100,200],        //可供选择的每页的行数（*）
                    //uniqueId: "puid",                     //每一行的唯一标识，一般为主键列
                    //showExport: true,
                    //exportDataType: 'all',
                    columns: column,
                    formId:"queryEbomManage",
                    sortName : 'id',
                    // sortable: true,                     //是否启用排序
                    sortOrder: "desc",                   //排序方式
                    clickToSelect: true,// 单击某一行的时候选中某一条记录
                    search: true, //是否显示表格搜索，此搜索是客户端搜索，不会进服务端
                    showColumns: true, //是否显示所有的列
                    // fixedColumns: true,
                    // fixedNumber:2,
                    showToggle: false,                   //是否显示详细视图和列表视图的切换按钮
                    showRefresh: true,                  //是否显示刷新按钮
                    //minimumCountColumns:4,
                    toolbars: [
                        {
                            text: '添加',
                            iconCls: 'glyphicon glyphicon-plus',
                            handler: function () {
                                var rows = $table.bootstrapTable('getSelections');
                                if (rows.length > 1) {
                                    window.Ewin.alert({message: '只能选择一个父级!'});
                                    return false;
                                }
                                else if (rows.length==1) {
                                window.Ewin.dialog({
                                    title: "添加",
                                    url: "ebom/addEbom?projectId=" + projectPuid + "&puid=" + rows[0].puid,
                                    gridId: "gridId",
                                    width: 500,
                                    height: 650
                                })
                            }
                                else if (rows.length==0) {
                                    window.Ewin.dialog({
                                        title: "添加",
                                        url: "ebom/addEbom?projectId=" + projectPuid,
                                        gridId: "gridId",
                                        width: 500,
                                        height: 650
                                    })
                                }
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
                                    url: "ebom/updateEbom?projectId="+projectPuid +"&puid="+ rows[0].puid ,
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
                                            url: "ebom/delete/ebom?puid="+rows[0].puid+"&projectId="+projectPuid,
                                            //data: JSON.stringify(rows),
                                            //contentType: "application/json",
                                            success: function (result) {
                                                // if (result.status) {
                                                //     window.Ewin.alert({message: result.errMsg});
                                                //     //刷新，会重新申请数据库数据
                                                // }
                                                // else {
                                                //     window.Ewin.alert({messabge: + result.errMsg});
                                                // }
                                                window.Ewin.alert({message: result.errMsg});
                                                $table.bootstrapTable("refresh");
                                            },
                                            error: function (info) {
                                                console.log(info)
                                                window.Ewin.alert({message: ":" + info.status});
                                            }
                                        })
                                    }
                                });
                            }
                        }
                    ],
                });
                //$table.bootstrapTable('hideColumn','puid');
               $table.bootstrapTable('hideColumn', 'puid');
         }
     });
}
