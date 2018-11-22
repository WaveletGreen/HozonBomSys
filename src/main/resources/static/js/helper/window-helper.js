/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */
/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description:
 * @Date: Created in  2018/11/22 11:04
 * @Modified By:
 */
/**
 * 防止弹窗出来造成的页面卡死
 */
function undead() {
    $('body').removeClass("modal-open");
    $('body').removeAttr("style");
}

/**
 * 获取当前激活的窗口
 * @returns {Window}
 */
function getActiveTab() {
    return $(window.parent.document).contents().find(".tab-pane.fade.active.in iframe")[0].contentWindow;
}

function getActiveDiv() {
    return $(window.parent.document).contents().find(".tab-pane.fade.active.in");
}

/**
 * 因弹窗造成的body卡死情况，将当前激活的iframe下的body样式modal-open去除
 */
function activeTabBodyReset() {
    $('.modal-dialog', window.top.document).parent('div').remove();
    $('body', window.top.document).find('.modal-backdrop').remove();
    getActiveTab().undead();
}
/**
 *即重置body的样式，又刷新页面
 */
function activeTabBodyRefresh() {
    activeTabBodyReset();
    getActiveTab().doQuery();
}