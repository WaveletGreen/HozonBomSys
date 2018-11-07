/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can't post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

$(function () {
    //禁用鼠标右键菜单
    $(document).bind("contextmenu", function (e) {
        return false;
    });
    //绑定点击右键显示菜单
    $(document).bind('mousedown', ShowMouse);
    $('.MouserNemu').hover(function () {
            //菜单出来后移上去点左健不会隐藏当前菜单
            $(document).unbind('mousedown');
        },
        function () {
            //移出后点击其它区域则隐藏菜单
            $(document).bind('mousedown', ShowMouse);
        }
    )
});

function ShowMouse(e) {
    //(e.which); // 1 = 鼠标左键 left; 2 = 鼠标中键; 3 = 鼠标右键
    switch (e.which) {
        case 1:
            //鼠标左键隐藏菜单
            $('.MouserNemu').hide();
            break;
        case 2:
            break;
        case 3:
            //鼠标右键显示菜单
            $('.MouserNemu').show().css({'left': e.pageX, 'top': e.pageY});
            break;
    }
    return false;
}

