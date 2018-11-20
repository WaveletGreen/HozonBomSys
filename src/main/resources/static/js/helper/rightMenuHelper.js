/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can't post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */
var context = context || (function () {
    var options = {
        fadeSpeed: 100,
        filter: function ($obj) {
        },
        above: 'auto',
        preventDoubleContext: true,
        compress: false
    };

    function initialize(opts) {
        options = $.extend({}, options, opts);
        $(document).on('click', 'html', function () {
            $('.dropdown-context').fadeOut(options.fadeSpeed, function () {
                $('.dropdown-context').css({display: ''}).find('.drop-left').removeClass('drop-left');
            });
        });
        if (options.preventDoubleContext) {
            $(document).on('contextmenu', '.dropdown-context', function (e) {
                e.preventDefault();
            });
        }
        $(document).on('mouseenter', '.dropdown-submenu', function () {
            var $sub = $(this).find('.dropdown-context-sub:first'),
                subWidth = $sub.width(),
                subLeft = $sub.offset().left,
                collision = (subWidth + subLeft) > window.innerWidth;
            if (collision) {
                $sub.addClass('drop-left');
            }
        });

    }

    function updateOptions(opts) {
        options = $.extend({}, options, opts);
    }

    function buildMenu(data, id, subMenu) {
        var subClass = (subMenu) ? ' dropdown-context-sub' : '',
            compressed = options.compress ? ' compressed-context' : '',
            $menu = $('<ul class="dropdown-menu dropdown-context' + subClass + compressed + '" id="dropdown-' + id + '"></ul>');
        var i = 0, linkTarget = '';
        for (i; i < data.length; i++) {
            if (typeof data[i].divider !== 'undefined') {
                $menu.append('<li class="divider"></li>');
            } else if (typeof data[i].header !== 'undefined') {
                $menu.append('<li class="nav-header">' + data[i].header + '</li>');
            } else {
                if (typeof data[i].href == 'undefined') {
                    data[i].href = '#';
                }
                if (typeof data[i].target !== 'undefined') {
                    linkTarget = ' target="' + data[i].target + '"';
                }
                if (typeof data[i].subMenu !== 'undefined') {
                    // var sd = "<a onclick='closeAll()' href='javascript:void(0)'></a>"
                    $sub = ('<li class="dropdown-submenu"><a tabindex="-1" href="' + data[i].href + '" onclick="' + data[i].func + '">' + data[i].text + '</a></li>');
                } else {
                    $sub = $('<li><a tabindex="-1" href="' + data[i].href + '"' + linkTarget + ' onclick="' + data[i].func + '">' + data[i].text + '</a></li>');
                }
                if (typeof data[i].action !== 'undefined') {
                    var actiond = new Date(),
                        actionID = 'event-' + actiond.getTime() * Math.floor(Math.random() * 100000),
                        eventAction = data[i].action;
                    $sub.find('a').attr('id', actionID);
                    $('#' + actionID).addClass('context-event');
                    $(document).on('click', '#' + actionID, eventAction);
                }
                $menu.append($sub);
                if (typeof data[i].subMenu != 'undefined') {
                    var subMenuData = buildMenu(data[i].subMenu, id, true);
                    $menu.find('li:last').append(subMenuData);
                }
            }
            if (typeof options.filter == 'function') {
                options.filter($menu.find('li:last'));
            }
        }
        return $menu;
    }

    function addContext(selector, data) {
        var d = new Date(),
            id = d.getTime(),
            $menu = buildMenu(data, id);
        $('body').append($menu);
        $(document).on('contextmenu', selector, function (e) {
            e.preventDefault();
            e.stopPropagation();
            $('.dropdown-context:not(.dropdown-context-sub)').hide();
            $dd = $('#dropdown-' + id);
            if (typeof options.above == 'boolean' && options.above) {
                $dd.addClass('dropdown-context-up').css({
                    top: e.pageY - 20 - $('#dropdown-' + id).height(),
                    left: e.pageX - 13
                }).fadeIn(options.fadeSpeed);
            } else if (typeof options.above == 'string' && options.above == 'auto') {
                $dd.removeClass('dropdown-context-up');
                var autoH = $dd.height() + 12;
                if ((e.pageY + autoH) > $('html').height()) {
                    $dd.addClass('dropdown-context-up').css({
                        top: e.pageY - 20 - autoH,
                        left: e.pageX - 13
                    }).fadeIn(options.fadeSpeed);
                } else {
                    $dd.css({
                        top: e.pageY + 10,
                        left: e.pageX - 13
                    }).fadeIn(options.fadeSpeed);
                }
            }
        });
    }

    function destroyContext(selector) {
        $(document).off('contextmenu', selector).off('click', '.context-event');
    }

    return {
        init: initialize,
        settings: updateOptions,
        attach: addContext,
        destroy: destroyContext
    };
})();
/**
 * @trimmer Visec·Dana
 * @time 2014-7-23
 */
$(document).ready(function () {
    /**
     * 右键菜单信息
     * text 文本内容
     * href 链接地址ַ
     */
    var b = $("body");
    if (b.attr("id")) {
        return;
    }
    b.append('<div style="text-align:center;margin:50px 0; font:normal 14px/24px \'MicroSoft YaHei\';"></div>');
    context.settings({compress: true});   //字体属性大小
    context.attach('html', [
        // {header: '&emsp;快捷操作'},
        {divider: true},
        {text: '关闭其他标签页', href: 'javascript:void(0)', func: "closeOthers()"},
        {divider: true},
        {text: '关闭左边所有标签页', href: 'javascript:void(0)', func: "closeLeft()"},
        // {divider: true},
        // {text: '关闭右边所有标签页', href: 'javascript:void(0)', func: "closeRigth()"},
        {divider: true},      //控制实线
        {text: '关闭所有标签页', href: 'javascript:void(0)', func: "closeAll()"},
    ]);
    context.init({preventDoubleContext: false});  //单击左键关闭右键菜单
});

function closeCurrent() {
    closeTab(4);
}

function closeOthers() {
    closeTab(3);
}

/**
 * 关闭左边标签页
 */
function closeLeft() {
    closeTab(1);
}

/**
 * 关闭右边所有标签页
 */
function closeRigth() {
    closeTab(2);
}

/**
 * 关闭所有标签页
 */
function closeAll() {
    closeTab(100);
}

function getCurrentDivId() {
    return $(window.parent.document).contents().find(".tab-pane.fade.active.in")[0].id;
}

function closeTab(direction) {
    var $alla = $(window.parent.document).contents().find("#tabContainer .nav-tabs li a");
    var currentDiv = getCurrentDivId();
    var current = false;
    var hash = "";
    var xhash = "";
    var target = null;
    if ($alla != null && $alla != undefined && $alla.length > 0) {
        for (let i = 0; i < $alla.length; i++) {
            if ($alla.hasOwnProperty(i)) {
                target = $alla[i];
                hash = target.hash;
                xhash = hash.substring(1, hash.length);
                if (100 == direction) {
                    doClose(target);
                }
                //左边
                else if (1 == direction) {
                    if (currentDiv == xhash) {
                        break;
                    }
                    else {
                        doClose(target);
                    }
                }
                //右边
                else if (2 == direction) {
                    if (currentDiv == xhash) {
                        current = true;
                        continue;
                    }
                    if (current) {
                        doClose()
                    }
                }
                else if (3 == direction) {
                    if (currentDiv == xhash) {
                        continue;
                    }
                    else {
                        doClose(target);
                    }
                }
                else if (4 == direction) {
                    if (currentDiv == xhash) {
                        doClose(target);
                        break;
                    }
                    else {
                        continue;
                    }
                }
            }
        }
    }
}

function doClose(obj) {
    var _i = $(obj).find("i");
    if (_i) {
        $(_i).trigger("click");
    }
}
