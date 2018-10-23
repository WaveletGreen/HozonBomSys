/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */
var taskData = null;
var vwoForm = null;
var ewoForm = null;
var mwoForm = null;

$(function () {
    loadTasks();
});


function loadTasks() {
    $.ajax({
        url: "./task/loadTasks",
        type: "POST",
        success: function (data) {
            var _data = data.data;
            taskData = null;
            if (_data) {
                $("#myTasks li").remove();
                $("#myTasks").append(
                    "<li>" +
                    "<a href='javascript:void(0)' class='project' onclick='loadTab(\"" + null + "\")'>请选择任务</a>" +
                    "</li>" +
                    "<li class='divider'></li>"
                );

                for (var i in _data) {
                    $("#myTasks").append(
                        "<li>" +
                        "<a href='javascript:void(0)' class='project' onclick='loadTab(" + JSON.stringify(_data[i]).replace(/\'/g, "\"") + ")'>" + _data[i].targetName + "</a>" +
                        "</li>" +
                        "<li class='divider'></li>"
                    );
                }
                console.log("加载项目成功");
            }
        },
        error: function (err) {
            console.log(err.status);
        }
    })
}

function loadTab(data) {
    taskData = data;
    if(null==taskData){
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