/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

// import getProjectUid from './js/helper/projectHelper.js'

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
function loadData(projectPuid) {
    if (!checkIsSelectProject(projectPuid)) {
        return;
    }
    var $table = $("#dataTable");
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
        toolbars: [
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
            // {
            //     text: '修改',
            //     iconCls: 'glyphicon glyphicon-pencil',
            //     handler: function () {
            //         var rows = $table.bootstrapTable('getSelections');
            //         //只能选一条
            //         if (rows.length != 1) {
            //             window.Ewin.alert({message: '请选择一条需要修改的数据!'});
            //             return false;
            //         }
            //         if (1 == rows[0].cfgIsInProcess || "1" == rows[0].cfgIsInProcess) {
            //             window.Ewin.alert({message: rows[0].pCfg0ObjectId + '已在VWO流程中，不允许修改'});
            //             return false;
            //         }
            //         window.Ewin.dialog({
            //             title: "修改",
            //             url: "cfg0/modifyPage?projectPuid=" + rows[0].puid,
            //             gridId: "gridId",
            //             width: 400,
            //             height: 500
            //         });
            //     }
            // },
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
                                url: "./cfg0/deleteByPuid",
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
            //     text: '发送到ERP',
            //     iconCls: 'glyphicon glyphicon-send',
            //     handler: function () {
            //         var rows = $table.bootstrapTable('getSelections');
            //         if (rows.length == 0) {
            //             window.Ewin.alert({message: '请至少选择一条需要发送的数据!'});
            //             return false;
            //         }
            //         window.Ewin.confirm({title: '提示', message: '是否要发送您所选择的记录？', width: 500}).on(function (e) {
            //             if (e) {
            //                 $.ajax({
            //                     type: "POST",
            //                     //ajax需要添加打包名
            //                     url: "./cfg0/sendToERP",
            //                     data: JSON.stringify(rows),
            //                     contentType: "application/json",
            //                     success: function (result) {
            //                         // if (result.status) {
            //                         // layer.msg(result.msg, {icon: 1, time: 2000});
            //                         window.Ewin.alert({message: result, width: 800});
            //                         //刷新，会重新申请数据库数据
            //                         // }
            //                         // else {
            //                         //     window.Ewin.alert({message: "操作发送失败:" + result.msg});
            //                         // }
            //                         $table.bootstrapTable("refresh");
            //                     },
            //                     error: function (info) {
            //                         window.Ewin.alert({message: "操作发送失败:" + info.status});
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
                            $.ajax({
                                type: "POST",
                                //ajax需要添加打包名
                                url: "./vwoProcess/featureGetIntoVWO?projectUid=" + projectPuid,
                                data: JSON.stringify(rows),
                                contentType: "application/json",
                                success: function (result) {
                                    // if (result.status) {
                                    if (result.status) {
                                        layer.msg(result.msg, {icon: 1, time: 2000});
                                    }
                                    // window.Ewin.alert({message: result, width: 800});
                                    //刷新，会重新申请数据库数据
                                    // }
                                    else {
                                        window.Ewin.alert({message: result.msg});
                                    }
                                    $table.bootstrapTable("refresh");
                                },
                                error: function (info) {
                                    window.Ewin.alert({message: "操作发送失败:" + info.status});
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
            // {
            //     field: 'length',
            //     title: '字符长度',
            //     align: 'center',
            //     valign: 'middle',
            // },
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
                    return changeDateFormat(value)
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
                    return changeDateFormat(value)
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
                    if (value == 0 || "0" == value) {
                        if (1 == row.cfgIsInProcess || "1" == row.cfgIsInProcess) {
                            return "<span style='color: #e69800'>VWO审核中</span>";
                        }
                        else {
                            return "<span style='color: #a97f89'>草稿状态</span>";
                        }
                    }
                    if (-1 == value || "-1" == value) {
                        return "<span style='color: #9492a9'>已废止</span>";
                    }
                    else {
                        return "<span style='color: #a90009'>未知状态</span>";
                    }
                }
            }
            // ,
            // {
            //     field: 'puid',
            //     title: 'puid',
            //     hide: false
            // }
        ],
        sortable: true,                     //是否启用排序
        sortOrder: "asc",                   //排序方式
        sortName: 'pCfg0ObjectId'
    });

    // function queryParam(pageReqeust) {
    //     var pageReqeust = {
    //         page: this.pageNumber,// 起始页面
    //         limit: this.pageSize // 页面大小
    //     }
    //     // if(formId!=undefined||formId!=''){
    //     //     $.each($("#"+formId).find("input"),function(index,info){
    //     //         param[info.name] = info.value;
    //     //     })
    //     // }
    //     if (sortName != undefined) {
    //         pageReqeust.sort = sortName;
    //     }
    //     if (sortOrder != null) {
    //         pageReqeust.order = sortOrder;
    //     }
    //     if ($("#feature").val() != null) {
    //         pageReqeust.feature = $("#feature").val();
    //     }
    //     if ($("#featureValue").val() != null) {
    //         pageReqeust.featureValue = $("#featureValue").val();
    //     }
    //
    //     if (getProjectUid() != null) {
    //         pageReqeust.projectUid = getProjectUid();
    //     }
    //     console.log("-------------------------");
    //     console.log(pageReqeust);
    //     console.log("--------------------------");
    //     return pageReqeust;
    //
    // };
    // function queryParams(params) {
    //     return {
    //         limit: params.pageSize,
    //         page: params.pageNumber,
    //         sort: params.sortName,
    //         order: params.sortOrder,
    //         feature: $("#feature").val(),
    //         featureValue: $("#featureValue").val(),
    //         projectUid: $("#projectUid").val()
    //     };
    // }

    // $table.bootstrapTable('hideColumn', 'puid');
}