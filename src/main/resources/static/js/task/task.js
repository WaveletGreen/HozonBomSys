/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */
var taskData = null;
var vwoForm = null;
var ewoForm = null;
var mwoForm = null;
var changeForm = null;

$(function () {
    loadTasks();
});

var projectId = $("#project", window.top.document).val();

function loadTasks() {
    log("加载个人任务");
    $.ajax({
        url: "./task/loadTasks",
        type: "POST",
        success: function (data) {
            var _data = data.data;
            taskData = null;
            let count = 0;
            if (_data) {
                $("#myTasks li").remove();
                if ((null != _data || _data != undefined) && _data.length != 0) {
                    $("#myTasks").append(
                        "<li>" +
                        "<a href='javascript:void(0)' class='project' onclick='loadTab(" + JSON.stringify(null).replace(/\'/g, "\"") + ")'>取消选择任务</a>" +
                        "</li>" +
                        "<li class='divider'></li>"
                    );
                }
                for (var i in _data) {
                    $("#myTasks").append(
                        "<li>" +
                        "<a href='javascript:void(0)' class='project' onclick='loadTab(" + JSON.stringify(_data[i]).replace(/\'/g, "\"") + ")'>" + _data[i].targetName + "</a>" +
                        "</li>" +
                        "<li class='divider'></li>"
                    );
                    count++;
                }
                $("#myCurrentTask").html("");
                if (count == 0) {
                    $("#myCurrentTask").append("<span style='color: gray'>任务数:无</span>");
                }
                else {
                    $("#myCurrentTask").append("<span style='color: red'>任务数:" + count + "</span>");
                }
            }
        },
        error: function (err) {
            log(err.status);
        }
    })
}

function loadTab(data) {
    taskData = data;
    if (null == taskData) {
        $.ajax({
            url: "./task/loadTasks",
            type: "POST",
            success: function (data) {
                var _data = data.data;
                taskData = null;
                let count = 0;
                if (_data) {
                    for (var i in _data) {
                        count++;
                    }
                    $("#myCurrentTask").html("");
                    if (count == 0) {
                        $("#myCurrentTask").append("<span style='color: gray'>任务数:无</span>");
                    }
                    else {
                        $("#myCurrentTask").append("<span style='color: red'>任务数:" + count + "</span>");
                    }
                }
            },
        });
        return;
    }
    var showObj = $(top.document.body).find(".nav-tabs li a[href='#" + (data.targetName + data.targetId) + "']");
    // 增加一个页面的时候判断当前的标签页是否已经打开过了，若打开过则不再重新生成新的tab标签页，而是直接显示打开过的标签页
    if ($(showObj).html() == undefined) {
        $("#tabContainer").data("tabs").addTab({
            id: data.targetName + data.targetId,
            text: data.targetName,
            closeable: true,
            url: data.url
        });
    }
    switch (data.formType) {
        case 1:
            vwoForm = {};
            vwoForm.id = data.id;
            vwoForm.url = data.url;
            break;
        case 2:
            ewoForm.id = data.id;
            ewoForm.url = data.url;
            break;
        case 3:
            mwoForm.id = data.id;
            mwoForm.url = data.url;
        case 10:
            changeForm.id = data.id;
            changeForm.url = data.url;
            break;
    }
    $("#myCurrentTask").text(data.targetName);
}

/**
 * 获取VWO表单的ID和URL
 * @returns {*}
 */
function getVwoForm() {
    return vwoForm;
}

/**
 * 获取VWO表单的ID和URL
 * @returns {*}
 */
function getEwoForm() {
    return ewoForm;
}

/**
 * 获取VWO表单的ID和URL
 * @returns {*}
 */
function getMwoForm() {
    return mwoForm;
}

/**
 * 获取表更表单对象
 * @returns {*}
 */
function getChangeForm() {
    return changeForm;
}

/**
 * 获取任务对象
 * @returns {{}}
 */
function getTaskData() {
    return taskData;
}

/**
 * 置空任务对象
 */
function clearTaskData() {
    taskData = null;
}
function f() {
    //var showObj = $(top.document.body).find(".nav-tabs li a[href='#" + (data.targetName + data.targetId) + "']");
    // 增加一个页面的时候判断当前的标签页是否已经打开过了，若打开过则不再重新生成新的tab标签页，而是直接显示打开过的标签页
    //if ($(showObj).html() == undefined) {
    $("#tabContainer").data("tabs").addTab({
        //id: data.targetName + data.targetId,
        id: "待办事项" + "122",
        text: "待办事项",//data.targetName
        closeable: true,
        url: "untreated",//data.url
    });
    //}
}