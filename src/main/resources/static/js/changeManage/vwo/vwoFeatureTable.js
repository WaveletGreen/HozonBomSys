/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

function loadFeature(vwoId) {
    var $table = $("#vwo_table");
    $table.bootstrapTable('destroy');
    $table.bootstrapTable({
        url: "getFeatureTable?vwoId=" + vwoId,
        method: "POST",
        height: 300,// $(window.parent.document).find("#wrapper").height() - document.body.offsetHeight - 100,
        width: $(window).width(),
        showToggle: false,                   //是否显示详细视图和列表视图的切换按钮
        showColumns: false,                  //是否显示所有的列
        showRefresh: false,                  //是否显示刷新按钮
        pagination: false,                   //是否显示分页（*）
        clickToSelect: false,                // 单击某一行的时候选中某一条记录
        formId: "queryFeature",
        /**列信息，需要预先定义好*/
        columns: [
            {
                field: 'headDesc',
                title: '变更类型',
            },
            {
                field: 'featureName',
                title: '特性名称',
            },
            {
                field: 'featureDesc',
                title: '特性描述',
                align: 'center',
                valign: 'middle',
            },
            {
                field: 'h9featureenname',
                title: '特性英文名称',
                align: 'center',
                valign: 'middle'
            },
            {
                field: 'featureValueName',
                title: '特性值/配置代码',
                align: 'center',
                valign: 'middle',
            },
            {
                field: 'featureValueDesc',
                title: '特性值/配置(描述)',
                align: 'center',
                valign: 'middle',
            },
            {
                field: 'cfgEffectedDate',
                title: '生效时间',
                align: 'center',
                valign: 'middle',
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
                //——修改——获取日期列的值进行转换
                formatter: function (value, row, index) {
                    return dateToStringFormat(value)
                }
            }
        ],
        onLoadSuccess: function () {  //加载成功时执行
            getTdValue();
        },
    });
}

//将已批准的数据改变背景颜色
function getTdValue() {
    var tableId = document.getElementById("vwo_table");
    for (var i = 1; i < tableId.rows.length; i+=2) {
        // if(tableId.rows[i].cells[0].innerHTML=="变更前" ){
        let cl=randomColor();
        tableId.rows[i].setAttribute("style", "background: " + cl + ";");
        tableId.rows[i+1].setAttribute("style", "background: " + cl + ";");
        // }
        // else{
        //     tableId.rows[i].setAttribute("style","background: "+randomColor+";");
        // }
    }
}

function randomColor() {
    return '#' + Math.floor(0x909090+Math.random() * 0x707070).toString(16);
}