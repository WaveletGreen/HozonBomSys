/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

var $tableId = null;
var reg = /^\+?[1-9][0-9]*$/;  //正整数
/**
 * 跳转页面
 * @param tableId 目标table的ID
 */
function setTargetTableId(tableId) {
    $tableId = tableId;
}

function toPage() {
    var pageNum = $("#pageNum").val();
    if (pageNum && isInteger(pageNum)) {
        $('#' + $tableId).bootstrapTable('selectPage', parseInt(pageNum));
    }
}


/**
 * 判断是否为整数
 * @param obj
 * @returns {boolean}
 */
function isInteger(obj) {

    return reg.test(obj);
}