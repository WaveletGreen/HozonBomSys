var firstLoad = true;
$(document).ready(
    // $("#queryColorSet").click(function () {
    (function () {
        loadData();
    }),
    //手动刷新按钮
    $("#refreshColorSet").click(function () {
        loadData();
    })
);

function loadData() {
    var $table = $("#dataTable");
    $table.bootstrapTable('destroy');
    $("#refreshColorSet").removeAttr("disabled");
    $table.bootstrapTable({
        url: "colorSet/queryAll2",
        height: $(window.parent.document).find("#wrapper").height() - document.body.offsetHeight - 200,
        width: $(window).width(),
        showToggle: true,                   //是否显示详细视图和列表视图的切换按钮
        showRefresh: true,                  //是否显示刷新按钮
        pageSize: 10,
        pagination: true,                   //是否显示分页（*）
        clickToSelect: true,                // 单击某一行的时候选中某一条记录
        sortable: true,
        sortName: 'pColorCode',
        sortOrder: 'asc',
        sidePagination: "server",           //客户端分页
        formId: "hide",
        toolbars: [
            {
                text: '添加',
                iconCls: 'glyphicon glyphicon-plus',
                handler: function () {
                    window.Ewin.dialog({
                        title: "添加",
                        url: "colorSet/addPage",
                        gridId: "gridId",
                        width: 350,
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
                        url: "colorSet/update?puid=" + rows[0].puid,
                        gridId: "gridId",
                        width: 350,
                        height: 450
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
                    //测试数据
                    // var obj = {
                    //     "puid": rows[0].puid,
                    //     "pColorOfSet": rows[0].pColorOfSet,
                    //     "pColorName": rows[0].pColorName,
                    //     "pColorCode": rows[0].pColorCode,
                    //     "pColorComment": rows[0].pColorComment,
                    // };
                    // var array = [];
                    // array.push(obj);
                    // array.push(obj);
                    // var data = JSON.stringify(array);
                    //data = data.replace(new RegExp('\\"', "gm"), '"');
                    //测试数据
                    window.Ewin.confirm({title: '提示', message: '是否要删除您所选择的记录？', width: 500}).on(function (e) {
                        if (e) {
                            $.ajax({
                                type: "POST",
                                //ajax需要添加打包名
                                url: "./colorSet/delete",
                                data: JSON.stringify(rows),
                                contentType: "application/json",
                                success: function (result) {
                                    if (result.status) {
                                        window.Ewin.alert({message: result.msg});
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
            }
        ],
        /**列信息，需要预先定义好*/
        columns: [
            {
                field: 'ck',
                checkbox: true
            },
            {
                field: 'pColorOfSet',
                title: '色系',
                sortable: true,
                sortOrder: 'asc',
            },
            {
                field: 'pColorName',
                title: '颜色名称',
                align: 'center',
                valign: 'middle',
                sortable: true,
                sortOrder: 'asc',
            },
            {
                field: 'pColorCode',
                title: '颜色代码',
                align: 'center',
                valign: 'middle',
                sortable: true,
                sortOrder: 'asc',
            },
            {
                field: 'pColorPlate',
                title: '色板编号',
                align: 'center',
                valign: 'middle',
                sortable: true,
                sortOrder: 'asc',
            },
            {
                field: 'pColorIsMultiply',
                title: '是否多色',
                align: 'center',
                valign: 'middle',
                sortable: true,
                sortOrder: 'asc',
            },
            {
                field: 'pColorEffectedDate',
                title: '生效时间',
                align: 'center',
                valign: 'middle',
                sortable: true,
                sortOrder: 'asc',
                //——修改——获取日期列的值进行转换
                formatter: function (value, row, index) {
                    return changeDateFormat(value)
                }
            },
            {
                field: 'pColorAbolishDate',
                title: '废止时间',
                align: 'center',
                valign: 'middle',
                sortable: true,
                sortOrder: 'asc',
                //——修改——获取日期列的值进行转换
                formatter: function (value, row, index) {
                    return changeDateFormat(value)
                }
            },
            {
                field: 'pColorComment',
                title: '备注',
                align: 'center',
                valign: 'middle',
                sortable: true,
                sortOrder: 'asc',
            },
            {
                field: 'puid',
                title: 'puid',
                hide: false
            }
        ]
        // sortable: true,                     //是否启用排序
        // sortOrder: "asc",                   //排序方式
    });
    $table.bootstrapTable('hideColumn', 'puid');

    //修改——转换日期格式(时间戳转换为datetime格式)
    function changeDateFormat(cellval) {
        if (cellval != null) {
            var date = new Date(parseInt(cellval));
            var month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
            var currentDate = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
            var hour = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
            var mins = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
            var sec = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
            return date.getFullYear() + "-" + month + "-" + currentDate + " " + hour + ":" + mins;
        }
    }
}