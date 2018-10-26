/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

let column = [
    {
        field: 'codeOfColorModel',
        title: '车型颜色代码',
        align: 'center',
        valign: 'middle'
    },
    {
        field: 'descOfColorModel',
        title: '&emsp;&emsp;&emsp;&emsp;描述&emsp;&emsp;&emsp;&emsp;',
        align: 'center',
        valign: 'middle'
    },
    {
        field: 'modelShell',
        title: '油漆车身总成',
        align: 'center',
        valign: 'middle'
    }
];

function loadModelColor(vwoId) {
    $.ajax({
        url: "",
        type: "GET",
        success: function (result) {
            if (result.status) {
                initTable(result.data);
            }
        }
    })
}

/**
 * 初始化配色方案变更table
 * @param data
 */

function initTable(data) {
    //存储定义的table表头
    for (let i in data) {
        column.push({
            field: "s" + i,
            title:
                data[i],
            align:
                'center',
            valign:
                'middle'
        })
    }
    var $table = $("#" + commonTableName);
    $table.bootstrapTable('destroy');
    $table.bootstrapTable({
        url: "getModelColorTable?vwoId=" + vwoId,
        method: "POST",
        height: commonHeight,// $(window.parent.document).find("#wrapper").height() - document.body.offsetHeight - 100,
        width: $(window).width(),
        showToggle: false,                   //是否显示详细视图和列表视图的切换按钮
        showColumns: false,                  //是否显示所有的列
        showRefresh: false,                  //是否显示刷新按钮
        pagination: false,                   //是否显示分页（*）
        clickToSelect: false,                // 单击某一行的时候选中某一条记录
        formId: "null",                       //需要定义formId，不定义的话会造成jQuery异常
        /**列信息，需要预先定义好*/
        columns: column,
        onLoadSuccess: function () {
            //加载成功时执行,渲染双行的
            renderingTableRowColor(commonTableName);
        },
    });
}
