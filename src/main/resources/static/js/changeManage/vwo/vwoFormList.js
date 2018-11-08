/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

$(document).ready((function () {
    let taskData = window.parent.getTaskData();
    if (taskData == null || taskData == undefined) {
        doQuery();
    }
    else {
        loadDetailTask();
    }
}));

function doQuery() {
    loadData(getProjectUid());
}

//刷新
function doRefresh(projectUid) {
    loadData(projectUid);
    let vwoFormDetail=window.parent.getVwoForm();
    console.log(vwoFormDetail);
    let $a=$(window.parent.document).contents().find("#tabContainer .nav-tabs .active a");
    let $span=$(window.parent.document).contents().find("#tabContainer .nav-tabs .active span");
    $a.attr("href","#"+vwoFormDetail.id);
    $span.text(vwoFormDetail.url);
}

function loadData(projectUid) {
    // if (!checkIsSelectProject(projectUid)) {
    //     return;
    // }
    let url = $("#url").val();
    url += "?projectUid=" + projectUid;
    let vwoStatus = $("#vwoStatus").val();
    let vwoChangeType = $("#vwoChangeType").val();
    let vwoCostBearingDept = $("#vwoCostBearingDept").val();
    let vwoListedType = $("#vwoListedType").val();
    if (vwoStatus != null) {
        url += "&vwoStatus=" + vwoStatus;
    }
    if (vwoChangeType != null) {
        url += "&vwoChangeType=" + vwoChangeType;
    }
    if (vwoCostBearingDept != null) {
        url += "&vwoCostBearingDept=" + vwoCostBearingDept;
    }
    if (vwoListedType != null) {
        url += "&vwoListedType=" + vwoListedType;
    }
    var $table = $("#vwo_table");
    $table.bootstrapTable('destroy');
    $table.bootstrapTable({
        url: url,
        method: 'post',
        height: $(window.parent.document).find("#wrapper").height() - 220,
        width: $(window).width(),
        showToggle: false,                   //是否显示详细视图和列表视图的切换按钮
        showRefresh: true,                  //是否显示刷新按钮
        pagination: true,                   //是否显示分页（*）
        pageSize: 20,
        pageNumber: 1,
        pageList: [20, 50, 100, 200, 500, 1000],        //可供选择的每页的行数（*）
        sidePagination: "server",          //分页方式：client客户端分页，server服务端分页（*）
        clickToSelect: true,                // 单击某一行的时候选中某一条记录
        showExport: false,
        formId: "vwoFormList",
        striped: true,                      //是否显示行间隔色
        showColumns: false,                 //是否显示所有的列
        sortable: true,                     //是否启用排序
        smartDisplay: false,
        sortOrder: "desc",                   //排序方式
        sortName: 'id',
        //列信息，需要预先定义好
        columns: [
            {
                field: 'vwoNum',
                title: 'vwo号',
                sortable: true,
                sortOrder: 'asc',
                formatter: function (value, row, index) {
                    return [
                        '<a href="vwo/vwoFormList?id=' + row.id + '&vwoType=' + row.vwoType + '">' + value + '</a>'
                    ].join("");
                }
            },
            {
                field: 'vwoName',
                title: 'vwo名称',
                sortable: true,
                sortOrder: 'asc',
            },
            {
                field: 'approvalType',
                title: '审批类型',
                sortable: true,
                sortOrder: 'asc',
            },
            {
                field: 'demandType',
                title: 'vwo需求类型',
                sortable: true,
                sortOrder: 'asc',
            },
            {
                field: 'vwoStatus',
                title: 'vwo状态',
                sortable: true,
                sortOrder: 'asc',
                formatter: function (value, row, index) {
                    return row.vwoStatusName;
                }
            },
            {
                field: 'vwoExpectExecuteTime',
                title: '预计实施时间',
                sortable: true,
                sortOrder: 'asc',
                formatter: function (value, row, index) {
                    return dateToStringFormat(value)
                }
            },
            {
                field: 'vwoCreator',
                title: 'vwo提出者',
                sortable: true,
                sortOrder: 'asc',
            },
            {
                field: 'vwoCreateDate',
                title: '提出时间',
                sortable: true,
                sortOrder: 'asc',
                formatter: function (value, row, index) {
                    return dateToStringFormat(value)
                }
            },
        ],
    });
}

function basic() {
    window.Ewin.dialog({
        title: '信息',
        url: 'vwo/vwoFromList',
        gridId: "gridId",
        width: 800,
        height: 500
    })
}