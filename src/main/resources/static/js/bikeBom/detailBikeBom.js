/**
 * Created with IntelliJ IDEA.
 * User: xlf
 * Date: 2018/9/12
 * Time: 15:53
 */

$(document).ready((function () {
    var $table = $("#detailBikeBomTable");
    var projectId = $("#project", window.top.document).val();
    var singleVehiclesId = $("#singleVehiclesId").val();
    var url = "bom/record?projectId=" + projectId+"&singleVehiclesId="+singleVehiclesId;
    initTable(url);
}))
function doRefresh(projectId){
    $('#detailBikeBomTable').bootstrapTable('destroy');
    var singleVehiclesId = $("#singleVehiclesId").val();
    var url = "bom/record?projectId=" + projectId+"&singleVehiclesId="+singleVehiclesId;
    initTable(url);
}
function doQuery() {
    // $('#detailBikeBomTable').bootstrapTable('refresh');
    var projectId = $("#project", window.top.document).val();
    var singleVehiclesId = $("#singleVehiclesId").val();
    var url = "bom/record?projectId=" + projectId+"&singleVehiclesId="+singleVehiclesId;
    // var level = $("#level").val();
    // mBomUrl += "&level=" + level;
    // var pBomOfWhichDept = $("#pBomOfWhichDept").val();
    // mBomUrl += "&pBomOfWhichDept=" + pBomOfWhichDept;
    // var lineId = $("#lineId").val();
    // mBomUrl += "&lineId=" + lineId;
    var pBomLinePartClass = $("#pBomLinePartClass").val();
    if (pBomLinePartClass =="请选择零件分类") {
        url += "&pBomLinePartClass="+ "";
    }else {
        url += "&pBomLinePartClass=" + pBomLinePartClass;
    }
    var pBomLinePartResource = $("#pBomLinePartResource").val();
    if (pBomLinePartResource == "请选择零件来源") {
        url += "&pBomLinePartResource="+ "";
    }
    else {
        url += "&pBomLinePartResource=" + pBomLinePartResource;
    }
    initTable(url);
    $('#detailBikeBomTable').bootstrapTable('destroy');
}

function initTable(url) {
    var projectId = $("#project", window.top.document).val();
    var $table = $("#detailBikeBomTable");
    var column = [];
    $.ajax({
        url: "bom/title",
        type: "GET",
        success: function (result) {
            var column = [];
            // column.push({field: 'id', title: '主键'});
            // column.push({field: 'ck', checkbox: true, width: 50});
            // column.push({
            //     field: 'ewoNo',
            //     title: '内饰颜色代码',
            //     align: 'center',
            //     valign: 'middle',
            //     formatter: function (value, row, index) {
            //         var id = row.id
            //         return [
            //             // '<a href="ewo/base/info?id='+id +'">' + value + '</a>'
            //             '<a href="javascript:void(0)" onclick="queryLou(' + id + ')">' + value + '</a>'
            //         ].join("");
            //     }
            // });
            // column.push({field: 'formCreateTime', title: '内饰颜色名称', align: 'center', valign: 'middle'});
            // column.push({field: 'dept', title: '颜色代码', align: 'center', valign: 'middle'});
            // column.push({field: 'changeType', title: '颜色名称', align: 'center', valign: 'middle'});
            // column.push({field: 'reasonCode', title: '电池型号', align: 'center', valign: 'middle'});
            // column.push({field: 'title', title: '电机型号', align: 'center', valign: 'middle'});
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
            ;
            $table.bootstrapTable({
                // ajax: function (request) {
                //     $.ajax({
                //         url: "ewo/base/infoList?projectId=" + projectId,
                //         success: function (result) {
                //             // var data = JSON.stringify(result);
                //             // var msg = JSON.parse(data);
                //             // console.log(msg);
                //             request.success({
                //                 row: result
                //             });
                //             $table.bootstrapTable('load', result);
                //         },
                //         error: function () {
                //             window.Ewin.alert("操作错误")
                //         }
                //     })
                // },
                url: url,
                method: 'get',
                height: $(window.parent.document).find("#wrapper").height() - 90,
                width: $(window).width(),
                showToggle: false,                   //是否显示详细视图和列表视图的切换按钮
                showRefresh: true,                  //是否显示刷新按钮
                pagination: true,                   //是否显示分页（*）
                pageSize: 20,
                pageNumber: 1,
                pageList: ['ALL', 20, 50, 100, 200, 500, 1000],        //可供选择的每页的行数（*）
                sidePagination: "server",          //分页方式：client客户端分页，server服务端分页（*）
                clickToSelect: true,                // 单击某一行的时候选中某一条记录
                showExport: false,
                formId: "queryDetailBikeBom ",
                columns: column,                     //列信息，需要预先定义好
                sortable: false,                     //是否启用排序
                sortOrder: "asc",                   //排序方式
                striped: true,                      //是否显示行间隔色
                search: false,                      //是否显示表格搜索，此搜索是客户端搜索，不会进服务端
                showColumns: false,                 //是否显示所有的列
                toolbars: [
                //     {
                //         text: '添加',
                //         iconCls: 'glyphicon glyphicon-plus',
                //         handler: function () {
                //             window.Ewin.dialog({
                //                 title: "添加",
                //                 url: "accessories/addAccessories",
                //                 gridId: "gridId",
                //                 width: 500,
                //                 height: 500
                //             })
                //         }
                //     },
                //     {
                //         text: '修改',
                //         iconCls: 'glyphicon glyphicon-pencil',
                //         handler: function () {
                //             var rows = $table.bootstrapTable('getSelections');
                //             //只能选一条
                //             if (rows.length != 1) {
                //                 window.Ewin.alert({message: '请选择一条需要修改的数据!'});
                //                 return false;
                //             }
                //             window.Ewin.dialog({
                //                 title: "修改",
                //                 url: "accessories/updateAccessories?puid=" + rows[0].puid,
                //                 gridId: "gridId",
                //                 width: 500,
                //                 height: 500
                //             });
                //         }
                //     },
                //     {
                //         text: '删除',
                //         iconCls: 'glyphicon glyphicon-remove',
                //         handler: function () {
                //             var rows = $table.bootstrapTable('getSelections');
                //             var puids = "";
                //             for (var i = 0 ; i<rows.length;i++){
                //                 puids += rows[i].puid+",";
                //             };
                //             var myData = JSON.stringify({
                //                 "puids":puids,
                //             });
                //             if (rows.length == 0) {
                //                 window.Ewin.alert({message: '请至少选择一条需要删除的数据!'});
                //                 return false;
                //             }
                //             var _table = '<p>是否要删除您所选择的记录？</p>' +
                //                 '<div style="max-height: 400px;overflow:scroll;"><table class="table table-striped tableNormalStyle" >';
                //             for (var index in rows) {
                //                 _table += '<tr><td>' + rows[index].pLineId + '</td></tr>';
                //             }
                //             _table += '</table></div>';
                //             window.Ewin.confirm({title: '提示', message: _table, width: 500}).on(function (e) {
                //                 if (e) {
                //                     $.ajax({
                //                         type: "POST",
                //                         //ajax需要添加打包名
                //                         url: "accessories/delete",
                //                         data: myData,
                //                         contentType: "application/json",
                //                         success: function (result) {
                //                             /*if (result.status) {
                //                                 window.Ewin.alert({message: result.errMsg});
                //                                 //刷新，会重新申请数据库数据
                //                             }
                //                             else {
                //                                 window.Ewin.alert({message: ":" + result.errMsg});
                //                             }*/if (result.success) {
                //                                 layer.msg('删除成功', {icon: 1, time: 2000})
                //                             }
                //                             else if(!result.success){
                //                                 window.Ewin.alert({message: result.errMsg})
                //                             }
                //                             //window.Ewin.alert({message: result.errMsg});
                //                             $table.bootstrapTable("refresh");
                //                         },
                //                         error: function (info) {
                //                             window.Ewin.alert({message: "操作删除:" + info.status});
                //                         }
                //                     })
                //                 }
                //             });
                //         }
                //     },
                    {
                        text: '数据同步',
                        iconCls: 'glyphicon glyphicon-repeat',
                        handler: function () {
                            var url = "";
                            window.Ewin.confirm({
                                title: '提示',
                                message: '确定要同步数据到单车清单吗?',
                                width: 500
                            }).on(function (e) {
                                if (e) {
                                    var _table ="<p><strong style='font-size: 20px'>数据正在同步中,请耐心等待...</strong></p>"
                                    _table+="<p><strong style='color: red'>警告:请勿进行其他的操作!</strong></p>"
                                    _table+="<div style='margin-top: 50px;text-align: center;z-index: 100;'><img src='/hozon/img/img.gif'/></div>"
                                    window.Ewin.confirm({
                                        title: '提示',
                                        message: _table,
                                        width: 500
                                    })
                                    url = "bom/refresh?projectId="+$("#project", window.top.document).val();
                                    $.ajax({
                                        type: "POST",
                                        //ajax需要添加打包名
                                        url: url,
                                        // data: myData,
                                        contentType: "application/json",
                                        success: function (result) {
                                            $('.modal-dialog', window.top.document).parent('div').remove()
                                            $('body', window.top.document).find('.modal-backdrop').remove();
                                            if (result.success) {
                                                layer.msg('同步成功', {icon: 1, time: 2000})
                                            } else if (!result.success) {
                                                window.Ewin.alert({message: result.errMsg});
                                            }
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
                        text: '导出Excel',
                        iconCls: 'glyphicon glyphicon-export',
                        handler: function () {
                            //var headers = data;//表头
                            var rows = $table.bootstrapTable('getSelections');//选中行数据
                            if (rows.length == 0) {
                                window.Ewin.alert({message: '请选择一条需要导出的数据!'});
                                return false;
                            }else{
                                for (var index in rows) {
                                    if (rows[index].status == 5 || rows[index].status == 6) {
                                        window.Ewin.alert({message: '勾选的数据有审核中状态，审核中的数据不给导出修改!'});
                                        return false;
                                    }
                                }
                            }
                            window.Ewin.confirm({title: '提示', message: '是否要导出选中行？', width: 500}).on(function (e) {
                                if (e) {
                                    $.ajax({
                                        type: "POST",
                                        //ajax需要添加打包名
                                        url: "./singleVehicles/excelExport2",//??????
                                        data: JSON.stringify(rows),
                                        contentType: "application/json",
                                        success: function (result) {
                                            console.log(result);
                                            if (result.status) {
                                                layer.msg(result.msg, {icon: 1, time: 2000})

                                                //下载EBOM导入模板
                                                window.location.href = result.path;//V1.1.0.log
                                            }
                                            else {
                                                window.Ewin.alert({message: "操作导出失败:" + result.msg});
                                            }
                                            $table.bootstrapTable("refresh");
                                        },
                                        error: function (info) {
                                            window.Ewin.alert({message: "操作导出:" + info.status});
                                        }
                                    })
                                }
                            });
                        }
                    }
                ],
            });
            $table.bootstrapTable('hideColumn', 'id');

        }
    })
}
