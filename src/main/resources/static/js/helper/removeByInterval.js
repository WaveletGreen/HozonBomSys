/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

var cout = false;
var timerId = null;

function removePaginationSwitch() {
    timerId = setInterval(redo, 2000);
}

function redo() {
    let paginationSwitch = $("*[name='paginationSwitch']");
    if (!cout && paginationSwitch != null) {
        paginationSwitch.remove();
        clearTimeout(timerId);
        cout = true;
    }
}