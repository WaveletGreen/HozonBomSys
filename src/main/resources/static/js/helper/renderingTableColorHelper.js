/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */
/**
 * 渲染目标table的相邻两行的进行随机变色
 * @param tableId 目标table的ID
 */
function renderingTableRowColor(tableId) {
    var tableId = document.getElementById(tableId);
    for (var i = 1; i < tableId.rows.length; i += 2) {
        // if(tableId.rows[i].cells[0].innerHTML=="变更前" ){
        let cl = randomColor();
        tableId.rows[i].setAttribute("style", "background: " + cl + ";");
        tableId.rows[i + 1].setAttribute("style", "background: " + cl + ";");
    }
}
function changeTableRendering(tableId) {
    var tableId = document.getElementById(tableId);
    for (var i = 1; i < tableId.rows.length; i += 2) {
        // if(tableId.rows[i].cells[0].innerHTML=="变更前" ){
        // let cl = randomColor();
        tableId.rows[i].setAttribute("style", "background: #fcff4e;");
        tableId.rows[i + 1].setAttribute("style", "background: #75ff78");
    }
}

/**
 * 生成随机色，去除比较暗的部分，不过依旧存在比较暗的部分色域
 * @returns {string}
 */
function randomColor() {
    return '#' + Math.floor(0x909090 + Math.random() * 0x707070).toString(16);
}
