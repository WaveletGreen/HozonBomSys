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