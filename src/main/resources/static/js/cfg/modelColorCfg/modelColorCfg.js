/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

var projectPuid = '';
/**动态表头*/
var dynamicTitle = [];
///工具条设置
var toolbar = [
    {
        text: '添加',
        iconCls: 'glyphicon glyphicon-plus',
        handler: function () {
            window.Ewin.dialog({
                title: "添加",
                url: "modelColor/addPage?projectPuid=" + projectPuid,
                gridId: "gridId",
                width: 700,
                height: 600
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
            for (let i in rows) {
                if (10 == rows[i].cmcrStatus || "10" == rows[i].cmcrStatus) {
                    window.Ewin.alert({message: rows[i].codeOfColorModel + '已在VWO流程中，不允许修改'});
                    return false;
                }
            }
            window.Ewin.dialog({
                title: "修改",
                url: "modelColor/modifyPage?puid=" + rows[0].puid,
                gridId: "gridId",
                width: 700,
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
            for (let i in rows) {
                if (10 == rows[i].cmcrStatus || "10" == rows[i].cmcrStatus) {
                    window.Ewin.alert({message: rows[i].codeOfColorModel + '已在VWO流程中，不允许删除'});
                    return false;
                }
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
                        url: "./modelColor/delete",
                        data: JSON.stringify(rows),
                        contentType: "application/json",
                        success: function (result) {
                            if (result) {
                                layer.msg("删除时数据成功", {icon: 1, time: 2000})
                                // window.Ewin.alert({message: "删除时数据成功"});
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
    },
    {
        text: '发起VWO流程',
        iconCls: 'glyphicon glyphicon-remove',
        handler: function () {
            var rows = $table.bootstrapTable('getSelections');
            if (rows.length == 0) {
                window.Ewin.alert({message: '请选择一条需要发起VWO流程的数据!'});
                return false;
            }
            for (let i in rows) {
                if (10 == rows[i].cmcrStatus || "10" == rows[i].cmcrStatus) {
                    window.Ewin.alert({message: rows[i].codeOfColorModel + '已在VWO流程中，不允许再次发起VWO流程'});
                    return false;
                }
                i
            }

            var data = {};
            data.rows = rows;
            data.titles = dynamicTitle;
            data.projectPuid = projectPuid;
            console.log(JSON.stringify(data));
            console.log(data);
            //测试数据
            window.Ewin.confirm({
                title: '提示',
                message: '是否要发起VWO流程？',
                width: 500
            }).on(function (e) {
                if (e) {
                    var puids = "";
                    for(let i in rows){
                        puids += rows[i].puid;
                        if(i<rows.length-1){
                            puids+=",";
                        }
                    }
                    var titles = "";
                    for(let i in dynamicTitle){
                        titles += dynamicTitle[i];
                        if(i<dynamicTitle.length-1){
                            titles+=",";
                        }
                    }
                    window.Ewin.dialog({
                        // 这个puid就是车型模型的puid，直接修改了车型模型的基本信息（在bom系统维护的字段）
                        title: "选择变更表单",
                        url: "./modelColor/setChangeFromPage?projectUid="+getProjectUid()+"&puids="+puids+"&titles="+titles,
                        gridId: "gridId",
                        width: 450,
                        height: 450
                    });
                    // $.ajax({
                    //     type: "POST",
                    //     //ajax需要添加打包名
                    //     url: "./modelColor/getVWO",
                    //     data: /*data*/JSON.stringify(data),
                    //     contentType: "application/json",
                    //     success: function (result) {
                    //         if (result.status) {
                    //             layer.msg("发起VWO流程成功", {icon: 1, time: 2000})
                    //             // window.Ewin.alert({message: "删除时数据成功"});
                    //             //刷新，会重新申请数据库数据
                    //         }
                    //         else {
                    //             window.Ewin.alert({message: "发起VWO流程失败:" + result.msg});
                    //         }
                    //         $table.bootstrapTable("refresh");
                    //     },
                    //     error: function (info) {
                    //         window.Ewin.alert({message: "发起VWO流程:" + info.status});
                    //     }
                    // })
                }
            });
        }
    }
];

function modeVehicle(puid) {
    projectPuid = $("#project", window.top.document).val();
    window.Ewin.dialog({
        title: "添加",
        url: "modelColor/setLvl2ColorPage?modelUid=" + puid + "&projectUid=" + projectPuid,
        gridId: "gridId",
        width: 880,
        height: 600
    })
}

$(document).ready(
    loadData(getProjectUid()),
    $("#refresh").removeAttr("disabled"),
//手动刷新按钮
    $("#refresh").click(function () {
        loadData(getProjectUid());
    }),
    $("#query").click(function () {
            loadData(getProjectUid());
            $("#refresh").removeAttr("disabled");
        }
    )
);

function loadData(_projectPuid) {
    if (!checkIsSelectProject(_projectPuid)) {
        return;
    }
    projectPuid = _projectPuid;
    $table = $("#modelColorCfgTable");
    $table.bootstrapTable('destroy');
    $("#refresh").removeAttr("disabled");
    $.ajax({
        url: "modelColor/getColumn?projectPuid=" + projectPuid,
        type: "GET",
        success: function (result) {
            if (result.status != 99) {
                if (result.status == 1) {
                    window.Ewin.alert({message: result.msg});
                }
                else if (result.status == 0) {
                    let msg = "<div style='max-height: 400px;overflow: scroll'><table>";
                    let entity = result.msg;
                    for (let i in entity) {
                        if (entity.hasOwnProperty(i)) {
                            msg += "<tr><td><div><span style='color: #ac2925'>" + entity[i] + "</span>对于特性来说颜色定义存在歧义，请到全配置BOM一级清单中重新定义颜色" + "</div></td></tr>";
                        }
                    }
                    msg += "</table></div>";
                    window.Ewin.alert({message: msg});
                }
                return;
            }
            var data = result.data;
            var column = [];
            column.push({field: 'ck', checkbox: true, Width: 50});
            column.push({field: 'codeOfColorModel', title: '车型颜色代码', align: 'center', valign: 'middle'});
            column.push({
                field: 'descOfColorModel',
                title: '&emsp;&emsp;&emsp;&emsp;描述&emsp;&emsp;&emsp;&emsp;',
                align: 'center',
                valign: 'middle',
                formatter: function (value, row, index) {
                    return [
                        '<div style="width: 200px">' + value + '</div>'
                    ].join("");
                }
            });
            column.push({
                field: 'modelShell',
                title: '油漆车身总成',
                align: 'center',
                valign: 'middle',
                // formatter: function (value, row, index) {
                //     var rowValues = JSON.parse(JSON.stringify(row));
                //     if ("Y" === rowValues.modeColorIsMultiply) {
                //         return [
                //             '<a href="javascript:void(0)" onclick="modeVehicle(\'' + row.puid + '\')">' + value + '</a>'
                //         ].join("");
                //     }
                //     else {
                //         return [
                //             value
                //         ].join("");
                //     }
                // }
            });
            for (var i = 0; i < data.length; i++) {
                var josn = {
                    field: "s" + i,
                    title:
                        data[i],
                    align:
                        'center',
                    valign:
                        'middle'
                };
                column.push(josn);
                dynamicTitle.push(data[i]);
            }
            var status = {
                field: "cmcrStatus",
                title: "状态",
                align:
                    'center',
                valign:
                    'middle',
                formatter: function (value, row, index) {
                    if (value == 999 || "999" == value) {
                        return "<span style='color: #00B83F'>已生效</span>";
                    }
                    else if (value == 0 || "0" == value) {
                        return "<span style='color: #a97f89'>草稿状态</span>";
                    }
                    else if (-1 == value || "-1" == value) {
                        return "<span style='color: #9492a9'>已废止</span>";
                    }
                    else if (10 == value || "10" == value) {
                        return "<span style='color: #e69800'>VWO审核中<br>("+row.cmcrVwoNum+")</span>";
                    }
                    else {
                        return "<span style='color: #a90009'>未知状态</span>";
                    }
                }
            };
            column.push(status);
            $table.bootstrapTable({
                url: "modelColor/loadAll?projectPuid=" + projectPuid,
                method: 'get',
                height: $(window.parent.document).find("#wrapper").height() - 150,//$(window.parent.document).find("#wrapper").height() - document.body.offsetHeight - 45,
                width: $(window).width(),
                showToggle: true,                   //是否显示详细视图和列表视图的切换按钮
                showRefresh: true,                  //是否显示刷新按钮
                pageSize: 10,
                pagination: true,                   //是否显示分页（*）
                clickToSelect: true,                // 单击某一行的时候选中某一条记录
                formId: "hide",
                /**列信息，需要预先定义好*/
                columns: column,
                // sortable: true,                     //是否启用排序
                // sortOrder: "asc",                   //排序方式
                toolbars: toolbar,
                //>>>>>>>>>>>>>>导出excel表格设置
                showExport: phoneOrPc(),              //是否显示导出按钮(此方法是自己写的目的是判断终端是电脑还是手机,电脑则返回true,手机返回falsee,手机不显示按钮)
                exportDataType: "selected",              //basic', 'all', 'selected'.
                exportTypes: ['xlsx'],	    //导出类型
                //exportButton: $('#btn_export'),     //为按钮btn_export  绑定导出事件  自定义导出按钮(可以不用)
                exportOptions: {
                    //ignoreColumn: [0,0],            //忽略某一列的索引
                    fileName: '配色方案数据导出',              //文件名称设置
                    worksheetName: 'Sheet1',          //表格工作区名称
                    tableName: '配色方案表',
                    excelstyles: ['background-color', 'color', 'font-size', 'font-weight'],
                    //onMsoNumberFormat: DoOnMsoNumberFormat
                }
                //导出excel表格设置<<<<<<<<<<<<<<<<
            });
        }
    })
}