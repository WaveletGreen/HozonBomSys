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
    window.parent.clearTaskData();
}