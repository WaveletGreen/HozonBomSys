/*
var setting = {
    //设置
    check: {
        enable: false,
        //不会有任何自动关联勾选的操作
        chkboxType: {"Y": "", "N": ""}
    },
    // view: {
    //     addHoverDom: addHoverDom,
    //     removeHoverDom:
    //     removeHoverDom,
    //     dblClickExpand:
    //         false,
    //     showLine:
    //         true,
    //     selectedMulti:
    //         false
    // },
    data: {
        simpleData: {
            enable: true,
            idKey:
                "puid",
            pIdKey:
                "pPuid",
            rootPId:
                "#"
        }
    }
    ,
    //回调函数
    callback: {
        //展开父节点
        beforeClick: function (treeId, treeNode) {
            var zTree = $.fn.zTree.getZTreeObj('menu_tree1');
            //有子层节点的都不是项目节点，返回false则不会触发onClick事件
            if (treeNode.isParent) {
                zTree.expandNode(treeNode);
                return false;
            } else {
                return true;
            }
        }
        ,
       /!* onClick: function (event, treeId, treeNode) {
            if (treeNode.level != 2) {
                return;
            }
            // var zTree = $.fn.zTree.getZTreeObj('menu_tree');
            //品牌的父id只有#
            if (null == coach[treeNode.puid]) {
                //beforeClick排除了有子层的节点，
                //绑定本地选择的子节点
                localSelectedNode = treeNode;
                $.ajax({
                    url: "project/loadProjectTree" + localSelectedNode.puid,
                    type: "GET",
                    success: function (data) {
                        var puid = localSelectedNode.puid;
                        localProjectDetail = data;
                        coach[puid] = localProjectDetail;
                        // eval("coach." + puid + "=" + data);
                        changeView(localProjectDetail);
                        $("#detailTable").css("visibility", "visible");
                    },
                    error: function (err) {
                        alert(err.status);
                    }
                })
            }
            else {
                localProjectDetail = coach[treeNode.puid];
                changeView(localProjectDetail);
            }
            localSelectedNode = treeNode;
        }*!/
    }
};


function loadReady() {
    var bodyH = demoIframe.contents().find("body").get(0).scrollHeight,
        htmlH = demoIframe.contents().find("html").get(0).scrollHeight,
        maxH = Math.max(bodyH, htmlH), minH = Math.min(bodyH, htmlH),
        h = demoIframe.height() >= maxH ? minH : maxH;
    if (h < 530) {
        h = 530;
    }
    demoIframe.height(h);
}

$(function () {
        $.w("project/loadProjectTree", function (info) {
            var t = $("#menu_tree1");
            t = $.fn.zTree.init(t, setting, info.data);
        })
    }
);
*/


/**
 *  第一个树型结构的demo
 */
$(function () {
    $('#lineId1').keyup(function () {
        var val = $(this).val();
        var localSelectedNode;
        var localProjectDetail;
        var coach = [];
        var projectId = $("#project", window.top.document).val();
        console.log(projectId);
        if (val != "") {
            $.ajax({
                type: "GET",
                url: "pbom/processComposeTree?lineId=" + val + "&projectId=" + projectId,
                undefinedText: "",//当数据为 undefined 时显示的字符
                success: function (data) {
                    console.log(data);
                    var setting = {
                        view: {
                            /*addHoverDom: addHoverDom,
                            removeHoverDom: removeHoverDom,*/
                            selectedMulti: false,
                            dblClickExpand:
                                false,
                            showLine:
                                true,
                        },
                        check: {
                            enable: false
                        },
                        data: {
                            simpleData: {
                                enable: true,
                                idKey:
                                    "puid",
                                pIdKey:
                                    "parentUid",
                                rootPId:
                                    "#"
                            },
                            key: {
                                checked:
                                    "CHECKED",
                                name:
                                    "lineId",
                                // name:"groupNum"
                            }
                        }
                        ,
                        //回调函数
                        callback: {
                            //展开父节点
                            beforeClick: function (treeId, treeNode) {
                                var zTree = $.fn.zTree.getZTreeObj('Ztree1');
                                //有子层节点的都不是项目节点，返回false则不会触发onClick事件
                                if (treeNode.isParent) {
                                    zTree.expandNode(treeNode);
                                    return false;
                                } else {
                                    return true;
                                }
                            }
                            ,
                            onClick: function (event, treeId, treeNode) {
                                if (treeNode.level != 2) {
                                    return;
                                }
                                // var zTree = $.fn.zTree.getZTreeObj('menu_tree');
                                //品牌的父id只有#
                                if (null == coach[treeNode.puid]) {
                                    //beforeClick排除了有子层的节点，
                                    //绑定本地选择的子节点
                                    localSelectedNode = treeNode;
                                    $.ajax({
                                        url: "pbom/detail?lineId=" + localSelectedNode.lineId + "&projectId=" + projectId,
                                        type: "GET",
                                        success: function (data) {
                                            console.log(data);
                                            var result = data.externalObject;
                                            var lineId = localSelectedNode.lineId;
                                            var titleName = result[0];
                                            var titleEName = result[1];
                                            var value = result[2];
                                            var rel = "<table class='table table-striped tableNormalStyle'>"
                                            for (var i = 0; i < titleEName.length; i++) {
                                                var a = eval(("value." + (titleEName[i])))
                                                rel += "<tr>" +
                                                    "<td><input type='checkbox' name='checkbox'value='" + titleEName[i] + ":" + a + "'></td>" +
                                                    "<th>" + titleName[i] + "</th>" +
                                                    "<td>" + a + "</td>" +
                                                    "</tr>";
                                            }
                                            rel += "</table>"
                                            $("#detailTable").html(rel);
                                        },
                                        error: function (err) {
                                            alert(err.status);
                                        }
                                    })
                                }
                                else {
                                    localProjectDetail = coach[treeNode.lineId];
                                    changeView(localProjectDetail);
                                }
                                localSelectedNode = treeNode;
                            }

                        }
                    };

                    var zNodes = data;

                    $(document).ready(function () {
                        $.fn.zTree.init($("#Ztree1"), setting, zNodes);
                    });

                },
                /*error: function (info) {
                    alert(info);
                }*/
            });
        }
    });
});


/**
 *  第二个树型结构的demo
 */

var localSelectedNode;
$(function () {
    $('#lineId3').keyup(function () {
        var val = $(this).val();

        var localProjectDetail;
        var coach = [];
        var projectId = $("#project", window.top.document).val();
        console.log(projectId);
        if (val != "") {
            $.ajax({
                type: "GET",
                url: "pbom/processComposeTree?lineId=" + val + "&projectId=" + projectId,
                undefinedText: "",//当数据为 undefined 时显示的字符
                success: function (data) {
                    console.log(data);
                    var setting = {
                        view: {
                            /*addHoverDom: addHoverDom,
                            removeHoverDom: removeHoverDom,*/
                            selectedMulti: false,
                            dblClickExpand: true,
                            showLine: true,
                        },
                        check: {
                            enable: false,
                        },
                        data: {
                            simpleData: {
                                enable: true,
                                idKey: "puid",
                                pIdKey: "parentUid",
                                rootPId: "#"
                            },
                            key: {
                                checked: "CHECKED",
                                name: "lineId",
                                // name:"groupNum"
                            }
                        },
                        callback: {
                            beforeClick: function (treeId, treeNode) {
                                var zTree = $.fn.zTree.getZTreeObj('Ztree3');
                                if (treeNode.isParent) {
                                    zTree.expandNode(treeNode);
                                    return true;
                                } else {
                                    return true;
                                }
                            },
                            onClick: function (event, treeId, treeNode) {

                                //品牌的父id只有#
                                if (null == coach[treeNode.idKey]) {
                                    //beforeClick排除了有子层的节点，
                                    //绑定本地选择的子节点
                                    localSelectedNode = treeNode;
                                    // alert(
                                    //     localSelectedNode.puid
                                    // );
                                }
                                else {
                                    localProjectDetail = coach[treeNode.puid];
                                    changeView(localProjectDetail);
                                }
                                localSelectedNode = treeNode;
                                // alert(localSelectedNode.puid)
                            }
                        }

                    };
                    var zNodes = data;

                    $(document).ready(function () {
                        $.fn.zTree.init($("#Ztree3"), setting, zNodes);
                    });
                },
                /*error: function (info) {
                    alert(info);
                }*/
            });
        }
    });
});


/**
 * 合成操作指定合成位置
 */
function doSubmit() {
    var obj = document.getElementsByName("checkbox");
    localSelectedNode.puid;
    localSelectedNode.parentId;
    var check_val = [];
    var data = {};
    var compound;
    var puid;
    var eBomContent;
    var _eBomContent = [];
    for (var k in obj) {
        if (obj[k].checked)
            check_val.push(obj[k].value);
    }
    for (var d in check_val) {
        var temp = check_val[d].split(":");
        data[temp[0]] = temp[1];
    }
    // eBomContent = JSON.stringify(data);//勾选需要合成的零件json字符串
    // eBomContent=JSON.parse(eBomContent);
    _eBomContent.push(data);
    //console.log("ck是："+ck);
    puid = localSelectedNode.puid;
    compound = $("#compoundLineId").val();//获取合成后的零件号

    var projectId = "projectPuid="
    projectId += $("#project", window.top.document).val();
    var url = "pbom/add/processCompose" + "?lineId=" + compound + "&puid=" + puid + "&" + projectId;
    console.log(url);
    $.ajax({
        url: url,
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(data),
        // dataType: "json",
        success: function (result) {
            window.Ewin.alert({message: result.msg});
        },
        error: function (status) {
            window.Ewin.alert({message: status.status + ':合成零件失败!'});
        }
    })


}
