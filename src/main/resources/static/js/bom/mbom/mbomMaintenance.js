$(document).ready((function () {

}))

setTimeout(function() {
    // IE
    if(document.all) {
        document.getElementById("mb").click();
    }
    // 其它浏览器
    else {
        var e = document.createEvent("MouseEvents");
        e.initEvent("click", true, true);
        document.getElementById("mb").dispatchEvent(e);
    }
}, 100);

function doRefresh(projectId) {
    $('#mbomMaintenanceTable').bootstrapTable('destroy');
    var mBomUrl = "mbom/record?projectId=" + projectId;
    initTable1(mBomUrl);
    $('#whiteBodyProductionTable').bootstrapTable('destroy');
    var productionUrl = "mbom/record?projectId=" + projectId+"&type="+1;
    initTable2(productionUrl);
    $('#whiteBodyFinancialTable').bootstrapTable('destroy');
    var financialUrl = "mbom/record?projectId=" + projectId+"&type="+6;
    initTable3(financialUrl);
}
function doQuery() {
    //$('#eplTable').bootstrapTable('refresh');    //刷新表格
    var projectPuid = $("#project", window.top.document).val();

    var mBomUrl = "mbom/record?projectId=" + projectPuid;
    // var level = $("#level").val();
    // mBomUrl += "&level=" + level;
    // var pBomOfWhichDept = $("#pBomOfWhichDept").val();
    // mBomUrl += "&pBomOfWhichDept=" + pBomOfWhichDept;
    // var lineId = $("#lineId").val();
    // mBomUrl += "&lineId=" + lineId;
    var pBomLinePartClass = $("#pBomLinePartClass").val();
    if (pBomLinePartClass == "请选择零件分类") {
        mBomUrl += "&pBomLinePartClass=" + "";
    } else {
        mBomUrl += "&pBomLinePartClass=" + pBomLinePartClass;
    }
    var pBomLinePartResource = $("#pBomLinePartResource").val();
    if (pBomLinePartResource == "请选择零件来源") {
        mBomUrl += "&pBomLinePartResource=" + "";
    }
    else {
        mBomUrl += "&pBomLinePartResource=" + pBomLinePartResource;
    }
    initTable1(mBomUrl);
    $('#mbomMaintenanceTable').bootstrapTable('destroy');
}
function initTable1(mBomUrl) {
    var projectPuid = $("#project", window.top.document).val();
    if (!checkIsSelectProject(projectPuid)) {
        return;
    }
    var $mBomTable = $("#mbomMaintenanceTable");
    // $mBomTable.bootstrapTable('destroy');
    var currentProjectHead = $("#currentProjectHead", window.top.document).val();
    var column = [];
    selections = [];

    $.ajax({
        url: "mbom/manage/title?projectId=" + projectPuid,
        type: "GET",
        success: function (result) {
            var column = [];
            // column.push({field: 'eBomPuid', title: 'puid'});
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
                    if ('pLouaFlag' === key) {
                        var json = {
                            field: key,
                            title: data[key],
                            // align: 'center',
                            valign: 'middle',
                            formatter: function (value, row, index) {
                                if (value == "LOA") {
                                    return [
                                        '<a href="javascript:void(0)" onclick="queryLoa(\'' + row.eBomPuid + '\')">' + value + '</a>'
                                    ].join("");
                                }
                                else if (value == "LOU") {
                                    return [
                                        '<a href="javascript:void(0)" onclick="queryLou(\'' + row.eBomPuid + '\')">' + value + '</a>'
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
                                if (value == "LOA") {
                                    return [
                                        '<a href="javascript:void(0)" onclick="queryLoa(' + row.eBomPuid + ')">' + value + '</a>'
                                    ].join("");
                                }
                                else if (value == "LOU") {
                                    return [
                                        '<a href="javascript:void(0)" onclick="queryLou(\'' + row.eBomPuid + '\')">' + value + '</a>'
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
                    if (value == 5 || value == "5") {
                        return "<span style='color: #e2ab2f'>审核中</span>"
                    }
                    if (value == 6 || value == "6") {
                        return "<span style='color: #e2ab2f'>审核中</span>"
                    }
                }
            })
            $mBomTable.bootstrapTable({
                url: mBomUrl,
                method: 'GET',
                dataType: 'json',
                cache: false,
                striped: true,                              //是否显示行间隔色
                sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
                height: $(window.parent.document).find("#wrapper").height() - 170,
                width: $(window).width(),
                formId: "queryMbomMain",
                undefinedText: "",//当数据为 undefined 时显示的字符
                pagination: true,
                pageNumber: 1,                       //初始化加载第一页，默认第一页
                pageSize: 20,                       //每页的记录行数（*）
                pageList: ['ALL', 20, 50, 100, 200, 500, 1000],        //可供选择的每页的行数（*）
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
                //responseHandler:responseHandler, //在渲染页面数据之前执行的方法，此配置很重要!!!!!!!
                toolbars: [
                    /*{
                        text: '添加',
                        iconCls: 'glyphicon glyphicon-plus',
                        handler: function () {
                            var rows = $mBomTable.bootstrapTable('getSelections');
                            //只能选一条
                            if (rows.length != 1) {
                                window.Ewin.alert({message: '请选择一条需要添加的数据!'});
                                return false;
                            }
                            window.Ewin.dialog({
                                title: "添加",
                                url: "mbom/addMBom?projectId=" + projectPuid + "&eBomPuid=" + rows[0].eBomPuid,
                                gridId: "gridId",
                                width: 500,
                                height: 650
                            })
                        }
                    },*/
                    {
                        text: '修改',
                        iconCls: 'glyphicon glyphicon-pencil',
                        handler: function () {
                            var rows = $mBomTable.bootstrapTable('getSelections');
                            //只能选一条
                            if (rows.length != 1) {
                                window.Ewin.alert({message: '请选择一条需要修改的数据!'});
                                return false;
                            }
                            window.Ewin.dialog({
                                title: "修改",
                                url: "mbom/updateMBom?projectId=" + projectPuid + "&eBomPuid=" + rows[0].eBomPuid,
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
                            var rows = $mBomTable.bootstrapTable('getSelections');
                            var puids = "";
                            for (var i = 0; i < rows.length; i++) {
                                puids += rows[i].eBomPuid + ",";
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
                            var _table = '<p>是否要删除您所选择的记录？</p>' +
                                '<div style="max-height: 400px;overflow:scroll;"><table class="table table-striped tableNormalStyle" >';
                            for (var index in rows) {
                                _table += '<tr><td>' + rows[index].lineId + '</td></tr>';
                            }
                            _table += '</table></div>';
                            window.Ewin.confirm({
                                title: '提示',
                                message: _table,
                                width: 500
                            }).on(function (e) {
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
                                            if (result.success) {
                                                layer.msg('删除成功', {icon: 1, time: 2000})
                                            } else if (!result.success) {
                                                window.Ewin.alert({message: result.errMsg});
                                            }
                                            $mBomTable.bootstrapTable("refresh");
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
                        text: '数据同步',
                        iconCls: 'glyphicon glyphicon-repeat',
                        handler: function () {
                            var url = "";
                            // var myData = JSON.parse({
                            //     "projectId": $("#project", window.top.document).val(),
                            // });

                            window.Ewin.confirm({
                                title: '提示',
                                message: '确定要同步数据到MBOM吗?',
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
                                    url = "mbom/refresh?projectId="+$("#project", window.top.document).val();
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
                                            $mBomTable.bootstrapTable("refresh");
                                        },
                                        error: function (info) {
                                            window.Ewin.alert({message: "操作失败:" + info.status});
                                        }
                                    })
                                }
                            });
                        }
                    },
                    // {
                    //     text: '设置为LOU/取消',
                    //     iconCls: 'glyphicon glyphicon-cog',
                    //     handler: function () {
                    //         var rows = $mBomTable.bootstrapTable('getSelections');
                    //         var lineIds = "";
                    //         for (var i = 0; i < rows.length; i++) {
                    //             lineIds += rows[i].lineId + ",";
                    //         }
                    //         ;
                    //         var myData = JSON.stringify({
                    //             "projectId": $("#project", window.top.document).val(),
                    //             "lineIds": lineIds,
                    //         });
                    //         if (rows.length == 0) {
                    //             window.Ewin.alert({message: '请选择至少一条需要设置为LOU的数据!'});
                    //             return false;
                    //         }
                    //         else if (rows[0].status == 5 || rows[0].status == 6){
                    //             window.Ewin.alert({message: '对不起,审核中的数据不能设置为LOU!'});
                    //             return false;
                    //         }
                    //         // var _table = '<p>是否要删除您所选择的记录？</p>' +
                    //         //     '<div style="max-height: 400px;overflow:scroll;"><table class="table table-striped tableNormalStyle" >';
                    //         // for (var index in rows) {
                    //         //     _table += '<tr><td>' + rows[index].lineId + '</td></tr>';
                    //         // }
                    //         // _table += '</table></div>';
                    //         // window.Ewin.confirm({title: '提示', message: _table, width: 500}).on(function (e) {
                    //         //     if (e) {
                    //         $.ajax({
                    //             type: "POST",
                    //             //ajax需要添加打包名
                    //             url: "loa/setLou/mBom",
                    //             data: myData,
                    //             contentType: "application/json",
                    //             success: function (result) {
                    //                 // if (result.status) {
                    //                 //     window.Ewin.alert({message: result.errMsg});
                    //                 //     //刷新，会重新申请数据库数据
                    //                 // }
                    //                 // else {
                    //                 //     window.Ewin.alert({messabge: + result.errMsg});
                    //                 // }
                    //                 if (result.success) {
                    //                     layer.msg('设置成功', {icon: 1, time: 2000})
                    //                 } else if (!result.success) {
                    //                     window.Ewin.alert({message: result.errMsg});
                    //                 }
                    //                 $mBomTable.bootstrapTable("refresh");
                    //             },
                    //             error: function (info) {
                    //                 window.Ewin.alert({message: ":" + info.status});
                    //             }
                    //         })
                    //         //     }
                    //         // });
                    //     }
                    // },
                    // {
                    //     text: '发送到半成品工艺',
                    //     iconCls: 'glyphicon glyphicon-send',
                    //     handler: function () {
                    //         var rows = $mBomTable.bootstrapTable('getSelections');
                    //         if (rows.length == 0) {
                    //             window.Ewin.alert({message: '请选择需要发送的数据!'});
                    //             return false;
                    //         }
                    //         window.Ewin.confirm({
                    //             title: '提示',
                    //             message: '是否要发送您所选择的记录？',
                    //             width: 500
                    //         }).on(function (e) {
                    //             if (e) {
                    //                 $.ajax({
                    //                     type: "POST",
                    //                     //ajax需要添加打包名
                    //                     url: "work/process/apply/oneKey?projectId=" + projectPuid + "&type=2",
                    //                     data: JSON.stringify(rows),
                    //                     contentType: "application/json",
                    //                     success: function (result) {
                    //                         /*if (result.status) {
                    //                             window.Ewin.alert({message: result.errMsg});
                    //                             //刷新，会重新申请数据库数据
                    //                         }
                    //                         else {
                    //                             window.Ewin.alert({message: ":" + result.errMsg});
                    //                         }*/
                    //                         window.Ewin.alert({message: result.errMsg});
                    //                         $mBomTable.bootstrapTable("refresh");
                    //                     },
                    //                     error: function (info) {
                    //                         window.Ewin.alert({message: "操作失败:" + info.status});
                    //                     }
                    //                 })
                    //             }
                    //         });
                    //     }
                    // },
                    // {
                    //     text: '发送到总成路线',
                    //     iconCls: 'glyphicon glyphicon-send',
                    //     handler: function () {
                    //         var rows = $mBomTable.bootstrapTable('getSelections');
                    //         if (rows.length == 0) {
                    //             window.Ewin.alert({message: '请选择需要发送的数据!'});
                    //             return false;
                    //         }
                    //         window.Ewin.confirm({
                    //             title: '提示',
                    //             message: '是否要发送您所选择的记录？',
                    //             width: 500
                    //         }).on(function (e) {
                    //             if (e) {
                    //                 $.ajax({
                    //                     type: "POST",
                    //                     //ajax需要添加打包名
                    //                     url: "work/process/apply/oneKey?projectId=" + projectPuid + "&type=3",
                    //                     data: JSON.stringify(rows),
                    //                     contentType: "application/json",
                    //                     success: function (result) {
                    //                         /*if (result.status) {
                    //                             window.Ewin.alert({message: result.errMsg});
                    //                             //刷新，会重新申请数据库数据
                    //                         }
                    //                         else {
                    //                             window.Ewin.alert({message: ":" + result.errMsg});
                    //                         }*/
                    //                         window.Ewin.alert({message: result.errMsg});
                    //                         $mBomTable.bootstrapTable("refresh");
                    //                     },
                    //                     error: function (info) {
                    //                         window.Ewin.alert({message: "操作失败:" + info.status});
                    //                     }
                    //                 })
                    //             }
                    //         });
                    //     }
                    // },
                    {
                        text: '发送MBOM到SAP',
                        iconCls: 'glyphicon glyphicon-send',
                        handler: function () {
                            var rows = $mBomTable.bootstrapTable('getSelections');
                            var confirm = undefined;
                            var localUrl = "";
                            var datas = null;
                            if (rows.length == 0) {
                                window.Ewin.confirm({
                                    title: '提示',
                                    message: '<p ><strong style="color: red">注意：正在进行耗时操作</strong></p>' +
                                    '您未选择任何一条数据，是否将当前项目' + currentProjectHead + '下的所有物料数据发送到ERP系统?'
                                    ,
                                    width: 1000
                                }).on(function (e) {
                                    //发送全部
                                    if (e) {
                                        localUrl = "synBom/synAllBomByProjectPuid?projectUid=" + projectPuid;
                                        datas = null;
                                        $.ajax({
                                            type: "POST",
                                            //ajax需要添加打包名
                                            url: localUrl,
                                            data: datas,
                                            contentType: "application/json",
                                            success: function (result) {
                                                window.Ewin.alert({message: result, width: 800});
                                                $mBomTable.bootstrapTable("refresh");
                                            },
                                            error: function (info) {
                                                window.Ewin.alert({message: "操作失败:" + info.status});
                                            }
                                        })
                                    }
                                });
                            }
                            else {
                                window.Ewin.confirm({
                                    title: '提示',
                                    message: '是否要重新发送您所选择的记录？',
                                    width: 800
                                }).on(function (e) {
                                    if (e) {
                                        localUrl = "synBom/updateByUids?projectUid=" + projectPuid;
                                        datas = JSON.stringify(rows);
                                        $.ajax({
                                            type: "POST",
                                            //ajax需要添加打包名
                                            url: localUrl,
                                            data: datas,
                                            contentType: "application/json",
                                            success: function (result) {
                                                // if (result.status) {
                                                //     window.Ewin.alert({message: result.msg});
                                                //     //刷新，会重新申请数据库数据
                                                // }
                                                // else {
                                                //     window.Ewin.alert({message: ":" + result.errMsg});
                                                // }
                                                window.Ewin.alert({message: result, width: 1000});
                                                $mBomTable.bootstrapTable("refresh");
                                            },
                                            error: function (info) {
                                                window.Ewin.alert({message: "操作失败:" + info.status});
                                            }
                                        })
                                    }
                                });
                            }
                        }
                    },
                    {
                        text: '发送单条MBOM到SAP',
                        iconCls: 'glyphicon glyphicon-send',
                        handler: function () {
                            var rows = $mBomTable.bootstrapTable('getSelections');
                            var confirm = undefined;
                            var localUrl = "";
                            var datas = null;
                            if (rows.length != 1) {
                                // window.Ewin.confirm({
                                //     title: '提示',
                                //     message: '<p ><strong style="color: red">注意：正在进行耗时操作</strong></p>' +
                                //     '您未选择任何一条数据，是否将当前项目' + currentProjectHead + '下的所有物料数据发送到ERP系统?'
                                //     ,
                                //     width: 1000
                                // }).on(function (e) {
                                //     //发送全部
                                //     if (e) {
                                //         localUrl = "synBom/addByUids?projectUid=" + projectPuid;
                                //         datas = null;
                                //         $.ajax({
                                //             type: "POST",
                                //             //ajax需要添加打包名
                                //             url: localUrl,
                                //             data: datas,
                                //             contentType: "application/json",
                                //             success: function (result) {
                                //                 window.Ewin.alert({message: result, width: 800});
                                //                 $mBomTable.bootstrapTable("refresh");
                                //             },
                                //             error: function (info) {
                                //                 window.Ewin.alert({message: "操作失败:" + info.status});
                                //             }
                                //         })
                                //     }
                                // });
                            }
                            else {
                                window.Ewin.confirm({
                                    title: '提示',
                                    message: '是否要发送您所选择的记录？',
                                    width: 800
                                }).on(function (e) {
                                    if (e) {
                                        localUrl = "synBom/addByUids?projectUid=" + projectPuid;
                                        datas = JSON.stringify(rows);
                                        $.ajax({
                                            type: "POST",
                                            //ajax需要添加打包名
                                            url: localUrl,
                                            data: datas,
                                            contentType: "application/json",
                                            success: function (result) {
                                                // if (result.status) {
                                                //     window.Ewin.alert({message: result.msg});
                                                //     //刷新，会重新申请数据库数据
                                                // }
                                                // else {
                                                //     window.Ewin.alert({message: ":" + result.errMsg});
                                                // }
                                                window.Ewin.alert({message: result, width: 1000});
                                                $mBomTable.bootstrapTable("refresh");
                                            },
                                            error: function (info) {
                                                window.Ewin.alert({message: "操作失败:" + info.status});
                                            }
                                        })
                                    }
                                });
                            }
                        }
                    }

                ],
            });
            // $mBomTable.bootstrapTable('hideColumn', 'eBomPuid');
        }
    });
}
function doQuery1() {
    //$('#eplTable').bootstrapTable('refresh');    //刷新表格
    var projectId = $("#project", window.top.document).val();
    var productionUrl = "mbom/record?projectId=" + projectId+"&type="+1;
    // var level = $("#level").val();
    // productionUrl += "&level=" + level;
    // var pBomOfWhichDept = $("#pBomOfWhichDept").val();
    // productionUrl += "&pBomOfWhichDept=" + pBomOfWhichDept;
    // var lineId = $("#lineId").val();
    // productionUrl += "&lineId=" + lineId;
    var pBomLinePartClass = $("#pBomLinePartClass1").val();
    if (pBomLinePartClass =="请选择零件分类") {
        productionUrl += "&pBomLinePartClass="+ "";
    }else {
        productionUrl += "&pBomLinePartClass=" + pBomLinePartClass;
    }
    var pBomLinePartResource = $("#pBomLinePartResource1").val();
    if (pBomLinePartResource == "请选择零件来源") {
        productionUrl += "&pBomLinePartResource="+ "";
    }
    else {
        productionUrl += "&pBomLinePartResource=" + pBomLinePartResource;
    }
    initTable2(productionUrl);
    $('#whiteBodyProductionTable').bootstrapTable('destroy');
}
function initTable2(productionUrl) {
    var projectId = $("#project", window.top.document).val();
    var $productionTable = $("#whiteBodyProductionTable");
    // $productionTable.bootstrapTable('destroy');

    var currentProjectHead = $("#currentProjectHead", window.top.document).val();
    var column = [];
    selections = [];

    $.ajax({
        url: "mbom/manage/title?projectId=" + projectId,
        type: "GET",
        success: function (result) {
            var column = [];
            column.push({field: 'ck', checkbox: true, Width: 50});
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
                                        '<a href="javascript:void(0)" onclick="queryLoa(\'' + row.eBomPuid + '\')">' + value + '</a>'
                                    ].join("");
                                }
                                else if (value == "LOU") {
                                    return [
                                        '<a href="javascript:void(0)" onclick="queryLou(\'' + row.eBomPuid + '\')">' + value + '</a>'
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
                                if (value == "LOA") {
                                    return [
                                        '<a href="javascript:void(0)" onclick="queryLoa(' + row.eBomPuid + ')">' + value + '</a>'
                                    ].join("");
                                }
                                else if (value == "LOU") {
                                    return [
                                        '<a href="javascript:void(0)" onclick="queryLou(\'' + row.eBomPuid + '\')">' + value + '</a>'
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
            $productionTable.bootstrapTable({
                url: productionUrl,
                method: 'GET',
                dataType: 'json',
                cache: false,
                striped: true,                              //是否显示行间隔色
                sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
                height: $(window.parent.document).find("#wrapper").height() - 170,
                width: $(window).width(),
                formId: "queryWhiteBodyProduction",
                undefinedText: "",//当数据为 undefined 时显示的字符
                pagination: true,
                pageNumber: 1,                       //初始化加载第一页，默认第一页
                pageSize: 20,                       //每页的记录行数（*）
                pageList: ['ALL', 20, 50, 100, 200, 500, 1000],        //可供选择的每页的行数（*）
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
                //responseHandler:responseHandler, //在渲染页面数据之前执行的方法，此配置很重要!!!!!!!
                toolbars: [
                    {
                        text: '修改',
                        iconCls: 'glyphicon glyphicon-pencil',
                        handler: function () {
                            var rows = $productionTable.bootstrapTable('getSelections');
                            //只能选一条
                            if (rows.length != 1) {
                                window.Ewin.alert({message: '请选择一条需要修改的数据!'});
                                return false;
                            }
                            window.Ewin.dialog({
                                title: "修改",
                                url: "mbom/updateProduction?projectId=" + projectId + "&eBomPuid=" + rows[0].eBomPuid+"&type="+1,
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
                            var rows = $productionTable.bootstrapTable('getSelections');
                            var puids = "";
                            for (var i = 0; i < rows.length; i++) {
                                puids += rows[i].eBomPuid + ",";
                            }
                            ;
                            var myData = JSON.stringify({
                                "projectId": $("#project", window.top.document).val(),
                                "puids": puids,
                                "type":1,
                            });
                            if (rows.length == 0) {
                                window.Ewin.alert({message: '请选择一条需要删除的数据!'});
                                return false;
                            }
                            var _table = '<p>是否要删除您所选择的记录？</p>' +
                                '<div style="max-height: 400px;overflow:scroll;"><table class="table table-striped tableNormalStyle" >';
                            for (var index in rows) {
                                _table += '<tr><td>' + rows[index].lineId + '</td></tr>';
                            }
                            _table += '</table></div>';
                            window.Ewin.confirm({
                                title: '提示',
                                message: _table,
                                width: 500
                            }).on(function (e) {
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
                                            if (result.success) {
                                                layer.msg('删除成功', {icon: 1, time: 2000})
                                            } else if (!result.success) {
                                                window.Ewin.alert({message: result.errMsg});
                                            }
                                            $productionTable.bootstrapTable("refresh");
                                        },
                                        error: function (info) {
                                            window.Ewin.alert({message: "操作失败:" + info.status});
                                        }
                                    })
                                }
                            });
                        }
                    },

                ],
            });
            // $productionTable.bootstrapTable('hideColumn', 'eBomPuid');
        }
    });
}
function doQuery2() {
    //$('#eplTable').bootstrapTable('refresh');    //刷新表格
    var projectId = $("#project", window.top.document).val();
    var financialUrl = "mbom/record?projectId=" + projectId+"&type="+6;
    // var level = $("#level").val();
    // financialUrl += "&level=" + level;
    // var pBomOfWhichDept = $("#pBomOfWhichDept").val();
    // financialUrl += "&pBomOfWhichDept=" + pBomOfWhichDept;
    // var lineId = $("#lineId").val();
    // financialUrl += "&lineId=" + lineId;
    var pBomLinePartClass = $("#pBomLinePartClass6").val();
    if (pBomLinePartClass =="请选择零件分类") {
        financialUrl += "&pBomLinePartClass="+ "";
    }else {
        financialUrl += "&pBomLinePartClass=" + pBomLinePartClass;
    }
    var pBomLinePartResource = $("#pBomLinePartResource6").val();
    if (pBomLinePartResource == "请选择零件来源") {
        financialUrl += "&pBomLinePartResource="+ "";
    }
    else {
        financialUrl += "&pBomLinePartResource=" + pBomLinePartResource;
    }
    initTable3(financialUrl);
    $('#whiteBodyFinancialTable').bootstrapTable('destroy');
}
function initTable3(financialUrl) {
    var projectId = $("#project", window.top.document).val();
    var $financialTable = $("#whiteBodyFinancialTable");
    var currentProjectHead = $("#currentProjectHead", window.top.document).val();
    var column = [];
    selections = [];
    // $financialTable.bootstrapTable('destroy');

    $.ajax({
        url: "mbom/manage/title?projectId=" + projectId,
        type: "GET",
        success: function (result) {
            var column = [];
            column.push({field: 'ck', checkbox: true, Width: 50});
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
                                        '<a href="javascript:void(0)" onclick="queryLoa(\'' + row.eBomPuid + '\')">' + value + '</a>'
                                    ].join("");
                                }
                                else if (value == "LOU") {
                                    return [
                                        '<a href="javascript:void(0)" onclick="queryLou(\'' + row.eBomPuid + '\')">' + value + '</a>'
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
                                // console.log(row)
                                if (value == "LOA") {
                                    return [
                                        '<a href="javascript:void(0)" onclick="queryLoa(' + row.eBomPuid + ')">' + value + '</a>'
                                    ].join("");
                                }
                                else if (value == "LOU") {
                                    return [
                                        '<a href="javascript:void(0)" onclick="queryLou(\'' + row.eBomPuid + '\')">' + value + '</a>'
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
            $financialTable.bootstrapTable({
                url: financialUrl,
                method: 'GET',
                dataType: 'json',
                cache: false,
                striped: true,                              //是否显示行间隔色
                sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
                height: $(window.parent.document).find("#wrapper").height() - 170,
                width: $(window).width(),
                formId: "queryMbomMain",
                undefinedText: "",//当数据为 undefined 时显示的字符
                pagination: true,
                pageNumber: 1,                       //初始化加载第一页，默认第一页
                pageSize: 20,                       //每页的记录行数（*）
                pageList: ['ALL', 20, 50, 100, 200, 500, 1000],        //可供选择的每页的行数（*）
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
                //responseHandler:responseHandler, //在渲染页面数据之前执行的方法，此配置很重要!!!!!!!
                toolbars: [
                    {
                        text: '修改',
                        iconCls: 'glyphicon glyphicon-pencil',
                        handler: function () {
                            var rows = $financialTable.bootstrapTable('getSelections');
                            //只能选一条
                            if (rows.length != 1) {
                                window.Ewin.alert({message: '请选择一条需要修改的数据!'});
                                return false;
                            }
                            window.Ewin.dialog({
                                title: "修改",
                                url: "mbom/updateFinancial?projectId=" + projectId + "&eBomPuid=" + rows[0].eBomPuid+"&type="+6,
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
                            var rows = $financialTable.bootstrapTable('getSelections');
                            var puids = "";
                            for (var i = 0; i < rows.length; i++) {
                                puids += rows[i].eBomPuid + ",";
                            }
                            ;
                            var myData = JSON.stringify({
                                "projectId": $("#project", window.top.document).val(),
                                "puids": puids,
                                "type":6,
                            });
                            if (rows.length == 0) {
                                window.Ewin.alert({message: '请选择一条需要删除的数据!'});
                                return false;
                            }
                            var _table = '<p>是否要删除您所选择的记录？</p>' +
                                '<div style="max-height: 400px;overflow:scroll;"><table class="table table-striped tableNormalStyle" >';
                            for (var index in rows) {
                                _table += '<tr><td>' + rows[index].lineId + '</td></tr>';
                            }
                            _table += '</table></div>';
                            window.Ewin.confirm({
                                title: '提示',
                                message: _table,
                                width: 500
                            }).on(function (e) {
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
                                            if (result.success) {
                                                layer.msg('删除成功', {icon: 1, time: 2000})
                                            } else if (!result.success) {
                                                window.Ewin.alert({message: result.errMsg});
                                            }
                                            $financialTable.bootstrapTable("refresh");
                                        },
                                        error: function (info) {
                                            window.Ewin.alert({message: "操作失败:" + info.status});
                                        }
                                    })
                                }
                            });
                        }
                    },
                ],
            });
            // $financialTable.bootstrapTable('hideColumn', 'eBomPuid');
        }
    });
}
function doMBom() {
    $(document).ready((function () {
    var projectId = $("#project", window.top.document).val();
    var mBomUrl = "mbom/record?projectId=" + projectId;
    initTable1(mBomUrl);
    }))
}
function doProduction() {
    $(document).ready((function () {
        var projectId = $("#project", window.top.document).val();
        var productionUrl = "mbom/record?projectId=" + projectId+"&type="+1;
        initTable2(productionUrl);
    }))
}

function doFinancial() {
    $(document).ready((function () {
        var projectId = $("#project", window.top.document).val();
        var financialUrl = "mbom/record?projectId=" + projectId+"&type="+6;
        initTable3(financialUrl);
    }))
}

function queryLoa(row) {
    var myData = JSON.stringify({
        "projectId": $("#project", window.top.document).val(),
        "puid": row
    });
    $.ajax({
        type: "POST",
        //ajax需要添加打包名
        url: "loa/mbom",
        data: myData,
        contentType: "application/json",
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

function queryLou(row) {
    // var myData = JSON.stringify({
    //     "projectId": $("#project", window.top.document).val(),
    //     "puid": row
    // });
    var projectId = $("#project", window.top.document).val();
    $.ajax({
        type: "GET",
        //ajax需要添加打包名
        url: "loa/getLou/mBom?projectId=" + projectId + "&puid=" + row,
        // data: myData,
        // contentType: "application/json",
        undefinedText: "",
        success: function (result) {
            var data = result.data;
            var parent = data.parent;
            var config = data.config;
            var child = data.child;
            var parentLevel = (parent.parentLevel == undefined ? "" : parent.parentLevel);
            var parentLineId = (parent.parentLineId == undefined ? "" : parent.parentLineId);
            var parentName = (parent.parentName == undefined ? "" : parent.parentName);
            var pCfg0name = (config.pCfg0name == undefined ? "" : config.pCfg0name);
            var cfg0Desc = (config.cfg0Desc == undefined ? "" : config.cfg0Desc);
            var pCfg0familyname = (config.pCfg0familyname == undefined ? "" : config.pCfg0familyname);
            var cfg0FamilyDesc = (config.cfg0FamilyDesc == undefined ? "" : config.cfg0FamilyDesc);
            var _table = '<div style="max-height: 400px;overflow:scroll;"><table class="table table-striped tableNormalStyle" >';
            _table += '<tr><td>父层级</td><td>父零件号</td><td>父名称</td></tr>'
            // for (var i=0;i<parent.length; i++) {
            _table += '<tr><td>' + parentLevel + '</td><td>' + parentLineId + '</td><td>' + parentName + '</td></tr>';
            _table += '</table></div>' + '<div style="max-height: 400px;overflow:scroll;"><table class="table table-striped tableNormalStyle" >';
            _table += '<tr><td>配置名</td><td>特性值描述</td><td>族名</td><td>特性描述</td></tr>'
            _table += '<tr><td>' + pCfg0name + '</td><td>' + cfg0Desc + '</td><td>' + pCfg0familyname + '</td><td>' + cfg0FamilyDesc + '</td></tr>';
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

$(document).keydown(function (event) {
    if (event.keyCode == 13) {
        $('form').each(function () {
            event.preventDefault();
        });
    }
});