/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */
var toolbar = [
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
    //         window.Ewin.dialog({
    //             title: "修改",
    //             url: "cfg0/relModifyPage?uid=" + rows[0].puid + "&page=" + "modifyPage",
    //             gridId: "gridId",
    //             width: 400,
    //             height: 500
    //         });
    //     }
    // },
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
    //                     url: "./cfg0/sendRelToERP",
    //                     data: JSON.stringify(rows),
    //                     contentType: "application/json",
    //                     success: function (result) {
    //                         // if (result.status) {
    //                         //     layer.msg(result.msg, {icon: 1, time: 2000})
    //                         window.Ewin.alert({message: result, width: 800});
    //                         //     //刷新，会重新申请数据库数据
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
    // }
];
var column = [
    // {
    //     field: 'ck',
    //     checkbox: true
    // },
    {
        field: 'index',
        title: '序号',
    },
    {
        field: 'relevance',
        title: '相关性',
        align: 'center',
        valign: 'middle',
        sortable: true,
        sortOrder: 'asc',
    },
    {
        field: 'relevanceDesc',
        title: '相关性描述',
        align: 'center',
        valign: 'middle',
        sortable: true,
        sortOrder: 'asc',
    },
    {
        field: 'relevanceCode',
        title: '相关性代码',
        align: 'center',
        valign: 'middle',
        sortable: true,
        sortOrder: 'asc',
    },
    {
        field: 'status',
        title: '状态',
        align: 'center',
        valign: 'middle',
        sortable: true,
        sortOrder: 'asc',
        formatter: function (value, row, index) {
            if (value == 1 || "1" == value) {
                return "<span style='color: #00B83F'>已生效</span>";
            }
            else if (value == 10 || "10" == value) {
                return "<span style='color: #e2ab2f'>审核中</span>";
            }
            else if (value == 0 || "0" == value) {
                return "<span style='color: #ff7cf4'>草稿状态</span>";
            }
            else if (2 == value || "2" == value) {
                return "<span style='color: #a90009'>删除状态</span>";
            }
            else if (-1 == value || "-1" == value) {
                return "<span style='color: #9492a9'>已废止</span>";
            }
            else {
                return "<span style='color: #a90009'>未知状态</span>";
            }
        }
    },{
        field: 'effectedDate',
        title: '生效时间',
        align: 'center',
        valign: 'middle',
        formatter: function (value, row, index) {
            return dateToStringFormat(value)
        }
    }
    // {
    //     field: 'puid',
    //     title: 'puid',
    //     hide: false
    // },
    // {
    //     field: '_table',
    //     title: '_table',
    //     hide: false
    // }
];
var $table = null;
let projectPuid = null;

function loadData(_projectPuid) {
    if (!checkIsSelectProject(_projectPuid)) {
        return;
    }
    projectPuid = _projectPuid;
    $table = $("#dataTable");
    if ($table == null) {
        return;
    }
    /**设置项目*/
    $("#projectUid").val(projectPuid);
    $table.bootstrapTable('destroy');
    $("#refresh").removeAttr("disabled");
    $table.bootstrapTable({
        url: "relevance/queryRelevance",
        method: "GET",
        height: $(window.parent.document).find("#wrapper").height() - 150,//$(window.parent.document).find("#wrapper").height() - document.body.offsetHeight - 45,
        width: $(window).width(),
        showToggle: true,                   //是否显示详细视图和列表视图的切换按钮
        // showColumns: true,                  //是否显示所有的列
        showRefresh: true,                  //是否显示刷新按钮
        pagination: true,                   //是否显示分页（*）
        pageNumber: 1,                       //初始化加载第一页，默认第一页
        pageSize: 20,                       //每页的记录行数（*）
        pageList: ['ALL', 20, 30, 50, 100, 500, 1000],//可供选择的每页的行数（*）
        smartDisplay: false,
        clickToSelect: true,                // 单击某一行的时候选中某一条记录
        formId: "queryRelevance",
        toolbars: toolbar,
        /**列信息，需要预先定义好*/
        columns: column,
        sortable: true,                     //是否启用排序
        sortOrder: "asc",                   //排序方式
        sortName: 'relevance',
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
    //设置跳转的tableID
    setTargetTableId("dataTable");
}

$(document).ready(
    // $("#query").click(function () {
    loadData(getProjectUid()),
    //手动刷新按钮
    $("#refresh").click(function () {
        loadData(getProjectUid());
        // $('#dataTable').bootstrapTable('refresh');
    }),
    //手动刷新按钮
    $("#query").click(function () {
        loadData(getProjectUid());
    }),
    $("#generateRelevance").click(function () {
        generateRelevance();
    }),
    $("#getChange").click(function () {
        getChange();
    }),
    $("#goBackData").click(function () {
        goBackData();
    })
);

function generateRelevance() {
    var rows = $table.bootstrapTable('getData');
    for(let i in rows){
        if(rows[i].status==10){
            window.Ewin.alert({message: "相关性数据已关联变更单号" +
                "，不能生成相关性"});
            return false;
        }
    }
    var msg = "您确定生成相关性吗！";
    var projectPuid = $("#project", window.top.document).val();
    var url = "relevance/addRelevance";
    $.ajax({
        url: "privilege/write?url=" + url,
        type: "GET",
        success: function (result) {
            if (!result.success) {
                window.Ewin.alert({message: result.errMsg});
                return false;
            }
            else {
                window.Ewin.confirm({title: '提示', message: msg, width: 500}).on(function (e) {
                    if (e) {
                        $.ajax({
                            type: "GET",
                            //ajax需要添加打包名
                            url: "./relevance/addRelevance?projectPuid=" + projectPuid,
                            contentType: "application/json",
                            success: function (result) {
                                if (result) {
                                    loadData(getProjectUid());
                                }
                            },
                            error: function (info) {
                                window.Ewin.alert({message: "操作发送失败:" + info.status});
                            }
                        })
                    }
                })
            }
        }
    })
}

function getChange() {
    var rows = $table.bootstrapTable('getData');
    var changeFlag = true;
    for(let i in rows){
        if(rows[i].status==10){
            window.Ewin.alert({message: "相关性数据已关联变更单号，不能再次关联变更单号"});
            return false;
        }
        if(rows[i].status==0||rows[i].status==2){
            changeFlag = false;
        }
    }
    if(changeFlag){
        window.Ewin.alert({message: "相关性数据不存在草稿状态或删除状态的数据，不能关联变更单号"});
        return false;
    }
    var msg = "您确定关联变更单号吗！";
    var projectPuid = $("#project", window.top.document).val();
    var url = "relevance/getChangePage";
    $.ajax({
        url: "privilege/write?url=" + url,
        type: "GET",
        success: function (result) {
            if (!result.success) {
                window.Ewin.alert({message: result.errMsg});
                return false;
            }
            else {
                window.Ewin.confirm({title: '提示', message: msg, width: 500}).on(function (e) {
                    if (e) {

                        window.Ewin.dialog({
                            title: "选择变更表单",
                            url: "./relevance/getChangePage?projectUid=" + getProjectUid(),
                            gridId: "gridId",
                            width: 450,
                            height: 450
                        });

                        // $.ajax({
                        //     type: "GET",
                        //     //ajax需要添加打包名
                        //     url: "./relevance/getChangePage?projectUid="+getProjectUid(),
                        //     contentType: "application/json",
                        //     success: function (result) {
                        //         if (result) {
                        //             layer.msg("发起流程成功", {icon: 1, time: 2000});
                        //             loadData(getProjectUid());
                        //         }
                        //     },
                        //     error: function (info) {
                        //         window.Ewin.alert({message: "操作发送失败:" + info.status});
                        //     }
                        // })
                    }
                })
            }
        }
    })
}

function goBackData() {
    var rows = $table.bootstrapTable('getData');
    var backFlag = true;
    for(let i in rows){
        if(rows[i].status==10){
            window.Ewin.alert({message: "相关性数据已关联变更单号，不能撤销"});
            return false;
        }
        if(rows[i].status==0){
            backFlag = false;
        }
    }
    if(backFlag){
        window.Ewin.alert({message: "相关性数据不存在草稿状态数据，不能撤销"});
        return false;
    }
    var msg = "您确定撤销吗！";
    var projectPuid = $("#project", window.top.document).val();
    var url = "relevance/goBackData";
    $.ajax({
        url: "privilege/write?url=" + url,
        type: "GET",
        success: function (result) {
            if (!result.success) {
                window.Ewin.alert({message: result.errMsg});
                return false;
            }
            else {
                window.Ewin.confirm({title: '提示', message: msg, width: 500}).on(function (e) {
                    if (e) {
                        $.ajax({
                            type: "GET",
                            url: "./relevance/goBackData?projectPuid=" + projectPuid,
                            contentType: "application/json",
                            success: function (result) {
                                if (result.status) {
                                    layer.msg("撤销数据成功", {icon: 1, time: 2000})
                                    window.location.reload();
                                } else {
                                    window.Ewin.alert({message: "操作撤销失败:" + result.msg});
                                }
                            },
                            error: function (info) {
                                window.Ewin.alert({message: "操作发送失败:" + info.status});
                            }
                        })
                    }
                })
            }
        }
    })
}