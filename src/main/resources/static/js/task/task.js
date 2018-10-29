/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

$(function () {
    loadTasks();
});

function loadTasks() {
    $.ajax({
        url: "./project/loadTasks",
        type: "POST",
        success: function (data) {
            var _data = data.data;
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
    console.log(data);
    $("#tabContainer").data("tabs").addTab({
        id: data.id,
        text: data.text,
        closeable: true,
        url: data.url
    });
    $("#currentTask").data("taskData", data);
    console.log($("#currentTask").data("taskData"));
}