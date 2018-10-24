/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

/**
 * 加载具体的任务
 * @param options
 */
var loadDetailTask = function () {
    let options = window.parent.getTaskData();
    if (options != null && options != undefined) {
        if (1 == options.formType) {
            vwoLoader(options);
        }
    }
};


function vwoLoader(options) {
    var $div = $('<div id="load-task-div"/>');
    var $a = $('<a id="load-task-a" href="vwo/vwoFormList?id=' + options.targetId + '&vwoType=' + options.targetType + '"/>');
    $a.text(" ");
    $div.append($a).appendTo('body');
    document.getElementById("load-task-a").click();
    $div.remove();

}

/**
 * 进制浏览器后退，否则会回到vwo 列表页面
 */
function taskStatusHold() {
    if (window.parent.getTaskData() != null) {
        if (window.history && window.history.pushState) {
            $(window).on('popstate', function () {
                /// 当点击浏览器的 后退和前进按钮 时才会被触发，
                window.history.pushState('forward', null, '');
                window.history.forward(1);
            });
        }
        //
        window.history.pushState('forward', null, '');  //在IE中必须得有这两行
        window.history.forward(1);
        window.parent.clearTaskData();
    }
}