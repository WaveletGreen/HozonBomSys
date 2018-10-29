/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

let columnOfModelColor = [
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
        url: "getModelColorTable?vwoId=" + vwoId,
        type: "POST",
        success: function (result) {
            if (result) {
                initTable(result);
            }
        },
        error: function (e) {
            console.error("服务器错误:" + e.status);
        }
    })
}

/**
 * 初始化配色方案变更table
 * @param data
 */

function initTable(data) {
    let titles = data.titleSet;
    let after = data.changAfter;
    let before = data.changBefor;
    let tableData = [];

    let lengthOfAfter = getLengthOfJson(after);
    let lengthOfBefore = getLengthOfJson(before);
    let sorter = -1;
    //存储定义的table表头
    for (let i in titles) {
        columnOfModelColor.push({
            field: "s" + i,
            title:
                titles[i],
            align:
                'center',
            valign:
                'middle'
        })
    }
    let ajson = {};
    let bjson = {};
    if (lengthOfAfter != lengthOfBefore) {
        sorter = lengthOfAfter > lengthOfBefore ? lengthOfAfter : lengthOfBefore;
    } else {
        sorter = before;
    }

    ajson.codeOfColorModel = after[0].cmcrDetailCgCreator;
    ajson.descOfColorModel = after[0].cmcrDetailCgId;
    ajson.modelShell = after[0].cmcrDetailCgVwoId;

    bjson.codeOfColorModel = before[0].cmcrDetailCgCreator;
    bjson.descOfColorModel = before[0].cmcrDetailCgId;
    bjson.modelShell = before[0].cmcrDetailCgVwoId;

    for (let i = 0; i < sorter; i++) {
        if (after.hasOwnProperty(i)) {
            ajson['s' + i] = after[i].cmcrDetailSrcColorUid;
        }
        else {
            ajson['s' + i] = "-";
        }
        if (before.hasOwnProperty(i)) {
            bjson['s' + i] = before[i].cmcrDetailSrcColorUid;
        }
        else {
            bjson['s' + i] = "-";
        }
    }

    tableData.push(before);
    tableData.push(after);

    let $table = $("#" + commonTableName);
    $table.bootstrapTable('destroy');
    $table.bootstrapTable({
        // url: "getModelColorTable?vwoId=" + vwoId,
        method: "get",
        data: tableData,
        cache: false,
        height: commonHeight,// $(window.parent.document).find("#wrapper").height() - document.body.offsetHeight - 100,
        width: $(window).width(),
        showToggle: false,                   //是否显示详细视图和列表视图的切换按钮
        showColumns: false,                  //是否显示所有的列
        showRefresh: false,                  //是否显示刷新按钮
        pagination: false,                   //是否显示分页（*）
        clickToSelect: false,                // 单击某一行的时候选中某一条记录
        exportTypes: ['csv', 'txt', 'xml'],
        formId: "null",                       //需要定义formId，不定义的话会造成jQuery异常
        /**列信息，需要预先定义好*/
        columns: columnOfModelColor,
        onLoadSuccess: function () {
            //加载成功时执行,渲染双行的
            renderingTableRowColor(commonTableName);
        },
    });
}
