/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can't post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */
$(document).ready(
    (function () {
        var URL = document.URL.split("/");
        var contextPath = "/" + URL[3];
        // 页面加载完成以后开启websocket的连接
        var options = new Array();
        options.sockurl = contextPath + '/ricky-websocket';
        options.stompClienturl = '/ricky/topic/greetings';
        options.login =authName;
        options.success = function (greeting) {
            var r = eval("(" + JSON.parse(greeting.body).content + ")")
            alert(r);
            // $("#greetings").append("<tr><td>" + JSON.parse(greeting.body).content + "</td></tr>");
        }
        $.fn.socketConnect(options);
        $("#tabContainer").tabs({
            data: [{
                id: '99999999',
                text: '首页',
                url: "project",
                closeable: false
            }],
            showIndex: 0,
            loadAll: false
        }),
            $("#tabContainer").tabs({
                data: [{
                    id: '99999998',
                    text: '首页',
                    url: "project",
                    closeable: true
                }],
                showIndex: 1,
                loadAll: false
            })
        //
        $.fn.bootstrapTree({url: contextPath + "/user/mainTree", treeId: 'menu_tree', tabId: "tabContainer"});
        $.fn.dictUtil("/dict/loadDict");
    })
);
$(document).ready(
    ($.ajax({
            url: "./project/loadAll",
            type: "GET",
            success: function (data) {
                var auth = data.auth;
                var _data = data.data;
                var ok = true;
                if (_data) {
                    $("#projectList").append(
                        "<li>" +
                        "<a href='javascript:void(0)' class='project' onclick='doChangeProject(\"" + "\")'>请选择项目</a>" +
                        "</li>" +
                        "<li class='divider'></li>"
                    );
                    for (var i in _data) {
                        // if (i == 0 && ok) {
                        //     $("#projectList", window.top.document).val(_data[i].puid);
                        //     $("#currentProject").text("当前工作项目:" + _data[i].pProjectName);
                        //     $("#currentProjectHead", window.top.document).text(_data[i].pProjectName);
                        //     ok = false;
                        //     if (!auth) {
                        //         break;
                        //     }
                        // }
                        // if (auth) {x
                        //     $("#fastOption").append("<option value='" + _data[i].puid + "'>" + _data[i].pProjectName + "</option>");
                        // }

                        $("#projectList").append(
                            "<li>" +
                            "<a href='javascript:void(0)' class='project' onclick='doChangeProject(\"" + _data[i].puid + "\",\"" + _data[i].pProjectName + "\")'>" + _data[i].pProjectName + " </a>" +
                            "</li>" +
                            "<li class='divider'></li>"
                        );
                    }
                    console.log("加载项目成功");
                }
                if (auth) {
                    $("#fastOption").removeAttr("hidden");
                    $("#fastOption").addClass("form-control");
                    // $("#fastOption").bind("change", selectEvent);
                    $("#labHidden").removeAttr("hidden");
                }
            },
            error: function (err) {
                alert(err.status);
            }
        })
    ));

function doChangeProject(projectUid, projectName) {
    $("#project").val(projectUid);
    $("#currentProjectHead").text(projectName);
    myRefresh(projectUid);
}

function myRefresh(projectUid) {
    if ($.isFunction($(window.parent.document).contents().find(".tab-pane.fade.active.in iframe")[0].contentWindow.doRefresh)) {
        $(window.parent.document).contents().find(".tab-pane.fade.active.in iframe")[0].contentWindow.doRefresh(projectUid);
    }
}

function updatePassword() {
    window.Ewin.dialog({
        title: '修改密码',
        url: 'user/getUser',
        gridId: "gridId",
        width: 500,
        height: 500
    })
}