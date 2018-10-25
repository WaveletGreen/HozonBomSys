/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

var temp = null;

function xsearch2() {
    let el = $('#feature').val();
    if ($("#" + el)) {
        document.getElementById($('#feature').val()).scrollIntoView(true);
        if (temp != null || temp != undefined || temp != "") {
            if (temp != el) {
                $('#' + el + ' label').css("color", "red");
                $('#' + temp + ' label').css("color", "black");
                temp = el;
            }
        }
        else {
            temp = el;
            $('#' + el + ' label').css("color", "red");
        }
    }
}