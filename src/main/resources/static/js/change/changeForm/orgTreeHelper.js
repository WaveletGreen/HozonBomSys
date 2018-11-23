/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·Malvis @connor. Any question/bug you can't post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */
var orgTree = (function () {
    return {
        init: initZTree,
    }
    var options = {
        targetTable: "",
        id: "",
        dept: "",
        selectedUserId: "",
        user: ""
    }

    var setting = null;

    function initZTree(opts) {
        options = $.extend({}, options, opts);
        initSetting(opts);
        $.post("./vwo/getUserAndGroup", function (info) {
            var t = $("#" + opts.id);
            t = $.fn.zTree.init(t, setting, info.data);
        })
    }

    function initSetting(opts) {
        // var zTree = $.fn.zTree.getZTreeObj(opts.id);
        setting = {
            check: {
                enable: false
            },
            view: {
                dblClickExpand: false,
                showLine: true,
                selectedMulti: false
            },
            data: {
                simpleData: {
                    enable: true,
                    idKey: "uid",
                    pIdKey: "pUid",
                    rootPId: "0"
                }
            },
            callback: {
                beforeClick: function (treeId, treeNode) {
                    if (treeNode.isParent) {
                        // zTree.expandNode(treeNode);
                        $.fn.zTree.getZTreeObj(opts.id).expandNode(treeNode);
                        return false;
                    } else {
                        return true;
                    }
                },
                onClick: function (event, treeId, treeNode) {
                    console.log(treeId);
                    console.log($("#selectOption").val());
                    let userId = treeNode.uid;
                    $.ajax({
                        type: "GET",
                        url: "./vwo/getUserDetail?uid=" + userId,
                        success: function (result) {
                            if (result.status) {
                                $("#" + opts.dept).val(result.groupName);
                                $("#" + opts.selectedUserId).val(result.personId);
                                $("#" + opts.user).val(result.personName);
                            }
                        },
                        error: function (e) {
                            console.error("连接服务器异常:" + e.status);
                        }
                    });
                }
            }
        };
    }
})();
