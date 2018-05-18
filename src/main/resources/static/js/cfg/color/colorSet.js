var firstLoad = true;
$(document).ready(
    $("#queryColorSet").click(function () {
        var $table = $("#dataTable");
        var last = 0;
        $.ajax({
            type: 'GET',
            url: '/colorSet/queryAll',
            success: function (thisData) {
                var orgName = thisData[0];
                var localName = thisData[1];
                var myColumns = [];

                var ck = {
                    field: 'ck',
                    checkbox: true,
                };

                myColumns.push(ck);
                for (var i = 0; i < orgName.length - last; i++) {
                    var temp = {
                        field: orgName[i],
                        title: localName[i]
                    };
                    myColumns.push(temp);
                }
                var data = thisData.slice(2, thisData.length);
                addToTable($table, data, myColumns);
            },
            error: function (info) {
                alert(info.status);
            }
        });
    }),
    $("#refreshColorSet").click(function () {
        alert("手动刷新");
        $('#dataTable').bootstrapTable('refresh', {url: '/colorSet/queryAll'});
    })
);

function addToTable($table, result, myColumns) {
    if (firstLoad) {
        $table.bootstrapTable({
            striped: true,
            data: result,
            columns: myColumns,
            striped: true,
            showToggle: true,                    //是否显示详细视图和列表视图的切换按钮
            // showColumns: true,                  //是否显示所有的列不要加，否则你会看到id
            showRefresh: true,                  //是否显示刷新按钮
            search: true,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
            // pagination: isPagination,                   //是否显示分页（*）
            sortable: true,                     //是否启用排序
            sortOrder: "asc",                   //排序方式
            clickToSelect: true,// 单击某一行的时候选中某一条记录
            cache: false,
            toolbars: [
                {
                    text: '添加',
                    iconCls: 'glyphicon glyphicon-plus',
                    handler: function () {
                        window.Ewin.dialog({
                            title: "添加",
                            url: "group/addUserPage",
                            gridId: "gridId",
                            width: 600,
                            height: 745
                        })
                    }
                },
                {
                    text: '修改',
                    iconCls: 'glyphicon glyphicon-pencil',
                    handler: function () {
                        var rows = $table.bootstrapTable('getSelections');
                        if (rows.length == 0 || rows.length > 1) {
                            window.Ewin.alert({message: '请选择一条需要修改的数据!'});
                            return false;
                        }
                        window.Ewin.dialog({
                            title: "修改",
                            url: "colorSet/update?puid=" + rows[0].hide,
                            gridId: "gridId",
                            width: 300,
                            height: 400
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
                        var ddd = JSON.parse(rows);
                        window.Ewin.confirm({title: '提示', message: '是否要删除您所选择的记录？', width: 500}).on(function (e) {
                            alert(e);
                            if (e) {
                                $.post("user/removeBath", {json: JSON.stringify(rows)}, function (e) {
                                    if (e.result) {
                                        window.Ewin.alert({message: e.msg});
                                        doQuery();
                                    }
                                });
                            }
                        });
                    }
                }
            ],
        });
    } else {
        //重新载入数据，清空之前的，用load，用refresh不成功
        $table.bootstrapTable("load", result);
    }
    $table.bootstrapTable('hideColumn', 'hide');
    firstLoad = false;
}