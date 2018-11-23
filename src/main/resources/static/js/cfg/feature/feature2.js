/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can't post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */
var $table=null;
var projectPuid=null;

var toolbar = [
    {
        text: '添加',
        iconCls: 'glyphicon glyphicon-plus',
        handler: function () {
            window.Ewin.dialog({
                title: "添加",
                url: "cfg0/addPage2?projectPuid=" + projectPuid,
                gridId: "gridId",
                width: 400,
                height: 500
            })
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
                if (1 == rows[i].cfgIsInProcess || "1" == rows[i].cfgIsInProcess) {
                    window.Ewin.alert({message: rows[i].pCfg0ObjectId + "已在VWO流程中，不允许删除"});
                    return false;
                }
            }
            window.Ewin.confirm({title: '提示', message: '是否要删除您所选择的记录？', width: 500}).on(function (e) {
                if (e) {
                    $.ajax({
                        type: "POST",
                        //ajax需要添加打包名
                        url: "./cfg0/deleteByPuidFake",
                        data: JSON.stringify(rows),
                        contentType: "application/json",
                        success: function (result) {
                            if (result.status) {
                                layer.msg(result.msg, {icon: 1, time: 2000})
                                // window.Ewin.alert({message: result, width: 800});
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
    // {
    //     text: '删除',
    //     iconCls: 'glyphicon glyphicon-remove',
    //     handler: function () {
    //         var rows = $table.bootstrapTable('getSelections');
    //         if (rows.length == 0) {
    //             window.Ewin.alert({message: '请选择一条需要删除的数据!'});
    //             return false;
    //         }
    //         for (let i in rows) {
    //             if (1 == rows[i].cfgIsInProcess || "1" == rows[i].cfgIsInProcess) {
    //                 window.Ewin.alert({message: rows[i].pCfg0ObjectId + "已在VWO流程中，不允许删除"});
    //                 return false;
    //             }
    //         }
    //         window.Ewin.confirm({title: '提示', message: '是否要删除您所选择的记录？', width: 500}).on(function (e) {
    //             if (e) {
    //                 $.ajax({
    //                     type: "POST",
    //                     //ajax需要添加打包名
    //                     url: "./cfg0/deleteByPuid",
    //                     data: JSON.stringify(rows),
    //                     contentType: "application/json",
    //                     success: function (result) {
    //                         if (result.status) {
    //                             layer.msg(result.msg, {icon: 1, time: 2000})
    //                             // window.Ewin.alert({message: result, width: 800});
    //                             //刷新，会重新申请数据库数据
    //                         }
    //                         else {
    //                             window.Ewin.alert({message: "操作删除失败:" + result.msg});
    //                         }
    //                         $table.bootstrapTable("refresh");
    //                     },
    //                     error: function (info) {
    //                         window.Ewin.alert({message: "操作删除:" + info.status});
    //                     }
    //                 })
    //             }
    //         });
    //     }
    // },
    {
        text: '发起VWO流程',
        iconCls: 'glyphicon glyphicon-send',
        handler: function () {
            var rows = $table.bootstrapTable('getSelections');
            if (rows.length == 0) {
                window.Ewin.alert({message: '请至少选择一条需要发送的数据!'});
                return false;
            }
            let msg = "<div style='max-height: 350px;overflow: -moz-scrollbars-vertical'>";
            for (let i in rows) {
                if (1 == rows[i].cfgIsInProcess || "1" == rows[i].cfgIsInProcess) {
                    window.Ewin.alert({message: rows[i].pCfg0ObjectId + "已在VWO流程中，不允许重复发起VWO流程"});
                    return false;
                }
                msg += "<p>" + rows[i].pCfg0ObjectId + "-" + rows[i].pCfg0Desc + "</p>";
            }
            msg += "</div>";
            window.Ewin.confirm({title: '请确认发起VWO流程的特性值', message: msg, width: 500}).on(function (e) {
                if (e) {
                    var puids = "";
                    for(let i in rows){
                        puids += rows[i].puid;
                        if(i<rows.length-1){
                            puids+=",";
                        }
                    }
                    window.Ewin.dialog({
                        // 这个puid就是车型模型的puid，直接修改了车型模型的基本信息（在bom系统维护的字段）
                        title: "选择变更表单",
                        url: "./vwoProcess/setChangeFromPage?projectUid="+getProjectUid()+"&puids="+puids,
                        gridId: "gridId",
                        width: 450,
                        height: 450
                    });
                    // $.ajax({
                    //     type: "POST",
                    //     //ajax需要添加打包名
                    //     url: "./vwoProcess/featureGetIntoVWO?projectUid=" + projectPuid,
                    //     data: JSON.stringify(rows),
                    //     contentType: "application/json",
                    //     success: function (result) {
                    //         // if (result.status) {
                    //         if (result.status) {
                    //             layer.msg(result.msg, {icon: 1, time: 2000});
                    //         }
                    //         // window.Ewin.alert({message: result, width: 800});
                    //         //刷新，会重新申请数据库数据
                    //         // }
                    //         else {
                    //             window.Ewin.alert({message: result.msg});
                    //         }
                    //         $table.bootstrapTable("refresh");
                    //     },
                    //     error: function (info) {
                    //         window.Ewin.alert({message: "操作发送失败:" + info.status});
                    //     }
                    // })
                }
            });
        }
    },
    {
        text: '撤销',
        iconCls: 'glyphicon glyphicon-share-alt',
        handler: function () {
            var rows = $table.bootstrapTable('getSelections');
            if (rows.length == 0) {
                window.Ewin.alert({message: '请选择一条需要撤销的数据!'});
                return false;
            }
            for (let i in rows) {
                if (1 == rows[i].cfgIsInProcess || "1" == rows[i].cfgIsInProcess) {
                    window.Ewin.alert({message: rows[i].pCfg0ObjectId + "已在VWO流程中，不允许撤销"});
                    return false;
                }
            }
            window.Ewin.confirm({title: '提示', message: '是否要撤销您所选择的记录？', width: 500}).on(function (e) {
                if (e) {
                    $.ajax({
                        type: "POST",
                        //ajax需要添加打包名
                        url: "./vwoProcess/goBackData?projectUid="+getProjectUid(),
                        data: JSON.stringify(rows),
                        contentType: "application/json",
                        success: function (result) {
                            if (result.status) {
                                layer.msg(result.msg, {icon: 1, time: 2000})
                                // window.Ewin.alert({message: result, width: 800});
                                //刷新，会重新申请数据库数据
                            }
                            else {
                                window.Ewin.alert({message: "操作撤销失败:" + result.msg});
                            }
                            $table.bootstrapTable("refresh");
                        },
                        error: function (info) {
                            window.Ewin.alert({message: "操作撤销:" + info.status});
                        }
                    })
                }
            });
        }
    }
];

var column = [
    {
        field: 'ck',
        checkbox: true
    },
    {
        field: 'pCfg0FamilyName',
        title: '特性名称',
        sortable: true,                     //是否启用排序
        sortOrder: "asc"
    },
    {
        field: 'pCfg0FamilyDesc',
        title: '特性描述',
        align: 'center',
        valign: 'middle',
        sortable: true,                     //是否启用排序
        sortOrder: "asc"
    },
    {
        field: 'pH9featureenname',
        title: '特性英文名称',
        align: 'center',
        valign: 'middle'
    },
    {
        field: 'pCfg0ObjectId',
        title: '特性值/配置代码',
        align: 'center',
        valign: 'middle',
        sortable: true,                     //是否启用排序
        sortOrder: "asc"
    },
    {
        field: 'pCfg0Desc',
        title: '特性值/配置(描述)',
        align: 'center',
        valign: 'middle',
        sortable: true,                     //是否启用排序
        sortOrder: "asc"
    },
    {
        field: 'cfgEffectedDate',
        title: '生效时间',
        align: 'center',
        valign: 'middle',
        sortable: true,
        sortOrder: 'asc',
        //——修改——获取日期列的值进行转换
        formatter: function (value, row, index) {
            return dateToStringFormat(value)
        }
    },
    {
        field: 'cfgAbolishDate',
        title: '废止时间',
        align: 'center',
        valign: 'middle',
        sortable: true,
        sortOrder: 'asc',
        //——修改——获取日期列的值进行转换
        formatter: function (value, row, index) {
            return dateToStringFormat(value)
        }
    },
    {
        field: 'cfgStatus',
        title: '状态',
        align: 'center',
        valign: 'middle',
        formatter: function (value, row, index) {
            if (value == 1 || "1" == value) {
                return "<span style='color: #00B83F'>已生效</span>";
            }
            else if (value == 0 || "0" == value) {
                if (1 == row.cfgIsInProcess || "1" == row.cfgIsInProcess) {
                    return "<span style='color: #e69800'>VWO审核中<br>("+row.vwoNum+")</span>";
                }
                else {
                    return "<span style='color: #a97f89'>草稿状态</span>";
                }
            }
            else if (2 == row.cfgStatus || "2" == row.cfgStatus) {
                return "<span style='color: #0c8fe2'>已删除</span>";
            }
            else if (-1 == value || "-1" == value) {
                return "<span style='color: #9492a9'>已废止</span>";
            }
            else {
                return "<span style='color: #a90009'>未知状态</span>";
            }
        }
    }
];
$(document).ready(
    // $("#query").click(function () {
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

/***
 * 加载数据，异步操作，方便调用
 */
function loadData(_projectPuid) {
    if (!checkIsSelectProject(_projectPuid)) {
        return;
    }
    projectPuid=_projectPuid;
    $table = $("#dataTable");
    if ($table == null)
        return;
    /**设置项目*/
    $("#projectUid").val(projectPuid);
    $table.bootstrapTable('destroy');
    $table.bootstrapTable({
        url: "cfg0/loadFeature?projectPuid=" + projectPuid,
        method: "GET",
        // queryParams: queryParams,
        height: $(window.parent.document).find("#wrapper").height() - 150,// $(window.parent.document).find("#wrapper").height() - document.body.offsetHeight - 100,
        width: $(window).width(),
        showToggle: true,                   //是否显示详细视图和列表视图的切换按钮
        showColumns: true,                  //是否显示所有的列
        showRefresh: true,                  //是否显示刷新按钮
        pagination: true,                   //是否显示分页（*）
        pageNumber: 1,                       //初始化加载第一页，默认第一页
        pageSize: 10,                       //每页的记录行数（*）
        pageList: [10, 30, 50, 100, 500, 1000],//可供选择的每页的行数（*）
        smartDisplay: false,
        clickToSelect: true,                // 单击某一行的时候选中某一条记录
        formId: "queryFeature",
        toolbars: toolbar,
        /**列信息，需要预先定义好*/
        columns: column,
        sortable: true,                     //是否启用排序
        sortOrder: "asc",                   //排序方式
        sortName: 'pCfg0ObjectId',
        //>>>>>>>>>>>>>>导出excel表格设置
        showExport: phoneOrPc(),              //是否显示导出按钮(此方法是自己写的目的是判断终端是电脑还是手机,电脑则返回true,手机返回falsee,手机不显示按钮)
        exportDataType: "selected",              //basic', 'all', 'selected'.
        exportTypes: ['xlsx'],	    //导出类型
        //exportButton: $('#btn_export'),     //为按钮btn_export  绑定导出事件  自定义导出按钮(可以不用)
        exportOptions: {
            //ignoreColumn: [0,0],            //忽略某一列的索引
            fileName: '特性数据导出',              //文件名称设置
            worksheetName: 'Sheet1',          //表格工作区名称
            tableName: '特性数据表',
            excelstyles: ['background-color', 'color', 'font-size', 'font-weight'],
            //onMsoNumberFormat: DoOnMsoNumberFormat
        }
        //导出excel表格设置<<<<<<<<<<<<<<<<
    });
}

