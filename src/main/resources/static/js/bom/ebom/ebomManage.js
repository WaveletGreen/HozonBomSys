$(document).ready((function () {
    var projectPuid = $("#project", window.top.document).val();
    var eBomUrl = "ebom/getEBom/list?projectId=" + projectPuid;
    initTable(eBomUrl);
}))


function doQuery() {
    //$('#ebomManageTable').bootstrapTable('refresh');    //刷新表格
    var projectPuid = $("#project", window.top.document).val();
    var eBomUrl = "ebom/getEBom/list?projectId=" + projectPuid;
    var level = $("#level").val();
    eBomUrl += "&level=" + level;
    var pBomOfWhichDept = $("#pBomOfWhichDept").val();
    eBomUrl += "&pBomOfWhichDept=" + pBomOfWhichDept;
    var lineId = $("#lineId").val();
    eBomUrl += "&lineId=" + lineId;
    initTable(eBomUrl);
    $('#ebomManageTable').bootstrapTable('destroy');
}

function initTable(eBomUrl) {
    var projectPuid = $("#project", window.top.document).val();
    //var eBomUrl ="ebom/getEBom/list?projectId=" + projectPuid
    var $table = $("#ebomManageTable");
    var column = [];
    $.ajax({
        url: "ebom/title?projectId=" + projectPuid,
        type: "GET",
        success: function (result) {
            var column = [];
            // column.push({field: 'eBomPuid', title: 'puid'});
            column.push({field: 'ck', checkbox: true});
            // column.push({field: 'puid', title: '主键'});

            /* var data = result.data;
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
                    if ('pLouaFlag' === key) {
                        var json = {
                            field: key,
                            title: data[key],
                            // align: 'center',
                            valign: 'middle',
                            formatter: function (value, row, index) {
                                if (value == "LOA") {
                                    return [
                                        '<a href="javascript:void(0)" onclick="queryLoa(\'' + row.puid + '\')">' + value + '</a>'
                                    ].join("");
                                }else if (value == "LOU"){
                                    return [
                                        '<a href="javascript:void(0)" onclick="queryLou(\'' + row.puid + '\')">' + value + '</a>'
                                    ].join("");
                                }
                                else {
                                    return [
                                        value
                                    ].join("");
                                }
                            }
                        };
                        column.push(json);
                    }
                    else {
                        var json = {
                            field: key,
                            title: data[key],
                            // align: 'center',
                            valign: 'middle',
                            formatter: function (value, row, index) {
                                if (value == "LOU/LOA") {
                                    return [
                                        '<a href="javascript:void(0)" onclick="queryLou(' + row.puid + ')">' + value + '</a>'
                                    ].join("");
                                }
                                else {
                                    return [
                                        value
                                    ].join("");
                                }
                            }
                        };
                        column.push(json);
                    }


                }
            }
            column.push({
                field: 'status',
                title: '状态',
                align: 'center',
                valign: 'middle',
                formatter: function (value, row, index) {
                    if (value == 1 || "1" == value) {
                        return "<span style='color: #00B83F'>已生效</span>";
                    }
                    if (value == 2 || "2" == value) {
                        return "<span style='color: #ff7cf4'>草稿状态</span>";
                    }
                    if (3 == value || "3" == value) {
                        return "<span style='color: #9492a9'>废除状态</span>";
                    }
                    if (4 == value || "4" == value) {
                        return "<span style='color: #a90009'>删除状态</span>";
                    }
                    if (value == 5 || value == "5"){
                        return "<span style='color: #e2ab2f'>审核中</span>"
                    }
                    if (value == 6 || value == "6"){
                        return "<span style='color: #e2ab2f'>审核中</span>"
                    }
                }
            })
            $table.bootstrapTable({
                url: eBomUrl,
                method: 'GET',
                dataType: 'json',
                //cache: false,
                //striped: true,                              //是否显示行间隔色
                sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
                height: $(window.parent.document).find("#wrapper").height() - 150,
                width: $(window).width(),
                formId: "queryEbomManage",
                undefinedText: "",//当数据为 undefined 时显示的字符
                pagination: true,
                pageNumber: 1,                       //初始化加载第一页，默认第一页
                pageSize: 20,                       //每页的记录行数（*）
                pageList: ['ALL', 20, 50, 100, 200, 500, 1000],        //可供选择的每页的行数（*）
                //uniqueId: "puid",                     //每一行的唯一标识，一般为主键列
                //showExport: true,
                //exportDataType: 'all',
                columns: column,
                toolbar: "#toolbar",
                //sortName: 'id',
                // sortable: true,                     //是否启用排序
                sortOrder: "asc",                   //排序方式
                clickToSelect: true,// 单击某一行的时候选中某一条记录
                //search: true, //是否显示表格搜索，此搜索是客户端搜索，不会进服务端
                showColumns: true, //是否显示所有的列
                // resizable:true,
                // fixedColumns: true,
                // fixedNumber:3,
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
                            else if (rows.length == 1) {
                                window.Ewin.dialog({
                                    title: "添加",
                                    url: "ebom/addEbom?projectId=" + projectPuid + "&puid=" + rows[0].puid,
                                    gridId: "gridId",
                                    width: 500,
                                    height: 500
                                })
                            }
                            else if (rows.length == 0) {
                                window.Ewin.dialog({
                                    title: "添加",
                                    url: "ebom/addEbom?projectId=" + projectPuid,
                                    gridId: "gridId",
                                    width: 500,
                                    height: 500
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
                            else if (rows[0].status == 5 || rows[0].status == 6){
                                window.Ewin.alert({message: '对不起,审核中的数据不能修改!'});
                                return false;
                            }
                            window.Ewin.dialog({
                                title: "修改",
                                url: "ebom/updateEbom?projectId=" + projectPuid + "&puid=" + rows[0].puid,
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
                            for (var i = 0; i < rows.length; i++) {
                                puids += rows[i].puid + ",";
                            }
                            ;
                            var myData = JSON.stringify({
                                "projectId": $("#project", window.top.document).val(),
                                "puids": puids,
                            });
                            if (rows.length == 0) {
                                window.Ewin.alert({message: '请选择一条需要删除的数据!'});
                                return false;
                            }
                            else if (rows[0].status == 5 || rows[0].status == 6){
                                window.Ewin.alert({message: '对不起,审核中的数据不能修改!'});
                                return false;
                            }
                            var _table = '<p>是否要删除您所选择的记录？</p>' +
                                '<div style="max-height: 400px;overflow:scroll;"><table class="table table-striped tableNormalStyle" >';
                            for (var index in rows) {
                                _table += '<tr><td>' + rows[index].lineId + '</td></tr>';
                            }
                            _table += '</table></div>';
                            window.Ewin.confirm({title: '提示', message: _table, width: 500}).on(function (e) {
                                if (e) {
                                    $.ajax({
                                        type: "POST",
                                        //ajax需要添加打包名
                                        url: "ebom/delete/ebom",
                                        data: myData,
                                        contentType: "application/json",
                                        success: function (result) {
                                            // if (result.status) {
                                            //     window.Ewin.alert({message: result.errMsg});
                                            //     //刷新，会重新申请数据库数据
                                            // }
                                            // else {
                                            //     window.Ewin.alert({messabge: + result.errMsg});
                                            // }
                                            if (result.success) {
                                                layer.msg('删除成功', {icon: 1, time: 2000})
                                            } else if (!result.success) {
                                                window.Ewin.alert({message: result.errMsg});
                                            }
                                            $table.bootstrapTable("refresh");
                                        },
                                        error: function (info) {
                                            window.Ewin.alert({message: ":" + info.status});
                                        }
                                    })
                                }
                            });
                        }
                    },
                    {
                        text: '设置为LOU',
                        iconCls: 'glyphicon glyphicon-cog',
                        handler: function () {
                            var rows = $table.bootstrapTable('getSelections');
                            var lineIds = "";
                            for (var i = 0; i < rows.length; i++) {
                                lineIds += rows[i].lineId + ",";
                            }
                            ;
                            var myData = JSON.stringify({
                                "projectId": $("#project", window.top.document).val(),
                                "lineIds": lineIds,
                            });
                            if (rows.length == 0) {
                                window.Ewin.alert({message: '请选择至少一条需要设置为LOU的数据!'});
                                return false;
                            }
                            else if (rows[0].status == 5 || rows[0].status == 6){
                                window.Ewin.alert({message: '对不起,审核中的数据不能设置为LOU!'});
                                return false;
                            }
                            // var _table = '<p>是否要删除您所选择的记录？</p>' +
                            //     '<div style="max-height: 400px;overflow:scroll;"><table class="table table-striped tableNormalStyle" >';
                            // for (var index in rows) {
                            //     _table += '<tr><td>' + rows[index].lineId + '</td></tr>';
                            // }
                            // _table += '</table></div>';
                            // window.Ewin.confirm({title: '提示', message: _table, width: 500}).on(function (e) {
                            //     if (e) {
                                    $.ajax({
                                        type: "POST",
                                        //ajax需要添加打包名
                                        url: "loa/setLou",
                                        data: myData,
                                        contentType: "application/json",
                                        success: function (result) {
                                            // if (result.status) {
                                            //     window.Ewin.alert({message: result.errMsg});
                                            //     //刷新，会重新申请数据库数据
                                            // }
                                            // else {
                                            //     window.Ewin.alert({messabge: + result.errMsg});
                                            // }
                                            if (result.success) {
                                                layer.msg('设置成功', {icon: 1, time: 2000})
                                            } else if (!result.success) {
                                                window.Ewin.alert({message: result.errMsg});
                                            }
                                            $table.bootstrapTable("refresh");
                                        },
                                        error: function (info) {
                                            window.Ewin.alert({message: ":" + info.status});
                                        }
                                    })
                            //     }
                            // });
                        }
                    },
                    {
                        text: '发起流程',
                        iconCls: 'glyphicon glyphicon-log-out',
                        handler: function () {
                            var rows = $table.bootstrapTable('getSelections');
                            var puids = "";
                            for (var i = 0; i < rows.length; i++) {
                                puids += rows[i].puid + ",";
                            }
                            ;
                            var myData = JSON.stringify({
                                "projectId": $("#project", window.top.document).val(),
                                "puids": puids,
                            });
                            if (rows.length == 0) {
                                window.Ewin.alert({message: '请选择一条需要变更的数据!'});
                                return false;
                            }else {
                                for (var i = 0; i<rows.length;i++){
                                    if (rows[i].status !=4 &&rows[i].status !=2){
                                        window.Ewin.alert({message: '请选择状态为草稿状态或删除状态的数据!'});
                                        return false;
                                    }
                                }
                            }
                            // else if (rows[0].status !== 2||4) {
                            //
                            //     window.Ewin.alert({message: '请选择状态为草稿状态或删除状态的数据!'});
                            //     return false;
                            //
                            // }
                            // var _table = '<p>是否要删除您所选择的记录？</p>' +
                            //     '<div style="max-height: 400px;overflow:scroll;"><table class="table table-striped tableNormalStyle" >';
                            // for (var index in rows) {
                            //     _table += '<tr><td>' + rows[index].lineId + '</td></tr>';
                            // }
                            // _table += '</table></div>';
                            // window.Ewin.confirm({title: '提示', message: _table, width: 500}).on(function (e) {
                            //     if (e) {
                            $.ajax({
                                type: "POST",
                                // ajax需要添加打包名
                                url: "ewo/initiating/process",
                                data: myData,
                                contentType: "application/json",
                                success: function (result) {
                                    // if (result.status) {
                                    //     window.Ewin.alert({message: result.errMsg});
                                    //     //刷新，会重新申请数据库数据
                                    // }
                                    // else {
                                    //     window.Ewin.alert({messabge: + result.errMsg});
                                    // }
                                    if (result.success) {
                                        layer.msg('发起流程成功', {icon: 1, time: 2000})
                                    } else if (!result.success) {
                                        window.Ewin.alert({message: result.errMsg});
                                    }
                                    $table.bootstrapTable("refresh");
                                },
                                error: function (info) {
                                    window.Ewin.alert({message: ":" + info.status});
                                }
                            })
                            //     }
                            // });
                        }
                    }
                ],
            });
            //$table.bootstrapTable('hideColumn','puid');
            // $table.bootstrapTable('hideColumn', 'puid');
            $table.bootstrapTable('hideColumn', 'groupNum');
            $table.bootstrapTable('hideColumn', 'rank');
        }
    });
}

function queryLoa(row) {
    var myData = JSON.stringify({
        "projectId": $("#project", window.top.document).val(),
        "puid": row
    });
    $.ajax({
        type: "POST",
        //ajax需要添加打包名
        url: "loa/ebom",
        data: myData,
        contentType: "application/json",
        undefinedText: "",
        success: function (result) {
            var child = result.data.child;
            var parent = result.data.parent;
            var parentLevel = (parent.parentLevel == undefined ? "" : parent.parentLevel);
            var parentLineId = (parent.parentLineId == undefined ? "" : parent.parentLineId);
            var parentName = (parent.parentName == undefined ? "" : parent.parentName);
            var _table = '<div style="max-height: 400px;overflow:scroll;"><table class="table table-striped tableNormalStyle" >';
            _table += '<tr><td>父层级</td><td>父零件号</td><td>父名称</td></tr>'
            // for (var i=0;i<parent.length; i++) {
            _table += '<tr><td>' + parentLevel + '</td><td>' + parentLineId + '</td><td>' + parentName + '</td></tr>';
            // }
            _table += '</table></div>' + '<div style="max-height: 400px;overflow:scroll;"><table class="table table-striped tableNormalStyle" >';
            _table += '<tr><td>子层级</td><td>子零件号</td><td>子名称</td></tr>'
            for (var i = 0; i < child.length; i++) {
                _table += '<tr><td>' + child[i].childLevel + '</td><td>' + child[i].childLineId + '</td><td>' + child[i].childName + '</td></tr>';
            }
            _table += '</table></div>';
            window.Ewin.confirm({title: '提示', message: _table, width: 500});
        }
    })
}