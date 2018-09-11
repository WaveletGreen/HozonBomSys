/**
 * Created with IntelliJ IDEA.
 * User: xlf
 * Date: 2018/9/4
 * Time: 16:02
 */
$(document).ready((function () {
    var url = "vpps/list";
    initTable(url);
}))

function doQuery() {
    // $('#dictionaryLibraryTable').bootstrapTable('refresh');    //刷新表格
    var url = "vpps/list";
    // var vppsLevel = $("#vppsLevel").val();
    // url += "?vppsLevel=" + vppsLevel;
    // var vsgCode = $("#vsgCode").val();
    // url += "&vsgCode=" + vsgCode;
    // var vppsCode = $("#vppsCode").val();
    // url += "&vppsCode=" + vppsCode;
    // var upc = $("#upc").val();
    // url += "&upc=" + upc;
    // var fna = $("#fna").val();
    // url += "&fna=" + fna;
    // var standardPartCode = $("#standardPartCode").val();
    // url += "&standardPartCode=" + standardPartCode;
    initTable(url);
    $('#VPPSLibraryTable').bootstrapTable('destroy');
}

function initTable(url) {
    var column = [];
    $.ajax({
        url: "vpps/title",
        type: "GET",
        success: function (result) {
            var column = [];
            column.push({field: 'ck', checkbox: true, Width: 50});
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
            ;
            $('#VPPSLibraryTable').bootstrapTable({
                url: url,
                method: 'GET',
                dataType: 'json',
                cache: false,
                striped: true,                                //是否显示行间隔色
                sidePagination: "server",                    //分页方式：client客户端分页，server服务端分页（*）
                height: $(window.parent.document).find("#wrapper").height() - 100,
                width: $(window).width(),
                formId: "queryVPPSLibrary",
                undefinedText: "",                           //当数据为 undefined 时显示的字符
                pagination: true,
                pageNumber: 1,                                //初始化加载第一页，默认第一页
                pageSize: 20,                                //每页的记录行数（*）
                pageList: ['ALL', 20, 50, 100, 200, 500, 1000],   //可供选择的每页的行数（*）
                uniqueId: "puid",                           //每一行的唯一标识，一般为主键列
                showExport: true,
                sortable: true,                             //是否启用排序
                sortOrder: "asc",                           //排序方式
                clickToSelect: true,                       // 单击某一行的时候选中某一条记录
                striped: true,                              //是否显示行间隔色
                showColumns: true,                         //是否显示所有的列
                showToggle: false,                        //是否显示详细视图和列表视图的切换按钮
                showRefresh: true,                        //是否显示刷新按钮
                columns:column,
                toolbars: [
                    {
                        text: '添加',
                        iconCls: 'glyphicon glyphicon-plus',
                        handler: function () {
                            var rows = $('#VPPSLibraryTable').bootstrapTable('getSelections');
                            //只能选一条
                            window.Ewin.dialog({
                                title: "添加",
                                url: "vpps/getAdd",
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
                            var rows = $('#VPPSLibraryTable').bootstrapTable('getSelections');
                            //只能选一条
                            if (rows.length != 1) {
                                window.Ewin.alert({message: '请选择一条需要修改的数据!'});
                                return false;
                            }
                            window.Ewin.dialog({
                                title: "修改",
                                url: "vpps/getUpdate?puid=" + rows[0].puid,
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
                            var rows = $('#VPPSLibraryTable').bootstrapTable('getSelections');
                            if (rows.length == 0) {
                                window.Ewin.alert({message: '请选择一条需要删除的数据!'});
                                return false;
                            }
                            // var _table = '<p>是否要删除您所选择的记录？</p>' +
                            //     '<div style="max-height: 400px;overflow:scroll;"><table class="table table-striped tableNormalStyle" >';
                            // for (var index in rows) {
                            //     _table += '<tr><td>' + rows[index].pMaterielDesc + '</td></tr>';
                            // }
                            // _table += '</table></div>';
                            // window.Ewin.confirm({title: '提示', message: _table, width: 500}).on(function (e) {
                            window.Ewin.confirm({title: '提示', message: '是否要删除您选择的记录', width: 500}).on(function (e) {
                                if (e) {
                                    $.ajax({
                                        type: "POST",
                                        //ajax需要添加打包名
                                        url: "vpps/delete?puid=" + rows[0].puid,
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
                                            if (result.success) {
                                                layer.msg('删除成功', {icon: 1, time: 2000})
                                            }
                                            else if (!result.success) {
                                                window.Ewin.alert({message: result.errMsg});
                                            }
                                            $('#VPPSLibraryTable').bootstrapTable("refresh");
                                        },
                                        error: function (info) {
                                            window.Ewin.alert({message: "操作删除:" + info.status});
                                        }
                                    })
                                }
                            });
                        }
                    },
                ],
            })
        }
    })
}