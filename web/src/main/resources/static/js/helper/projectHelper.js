/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

/**
 * 获取项目UID
 * @returns {jQuery}
 */
function getProjectUid() {
    return $("#project", window.top.document).val();
}

/**
 * 获取到当前工作项目的代码
 * @returns {jQuery}
 */
function getProjectCode(){
    const projectCode=$("#currentProjectHead", window.top.document).text();
    return  projectCode;
}

function checkIsSelectProject(data) {
    if (0 == data.length) {
        swal({
            title: '错误',
            width: 500,
            html: $('<span>')
                .addClass('some-class')
                .css("font-size", "24px")
                .css("color", "#ff4f6a")
                .text('请正确选择一个项目进行操作'),
            animation: false,
            customClass: 'animated tada'
        });
        // window.Ewin.alert({message: "请选择一个项目后再进行操作"});
        // $("#myModal").modal('show');
        return false;
    }
    else {
        return true;
    }
}
