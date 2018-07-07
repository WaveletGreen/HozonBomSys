/*$(function () {
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
                            /!*addHoverDom: addHoverDom,
                            removeHoverDom: removeHoverDom,*!/
                            selectedMulti: false,
                            dblClickExpand:
                                false,
                            showLine:
                                true,
                        },
                        check: {
                            //chkboxType:{"Y":"ps","N":"ps"},//勾选checkbox对于父子节点的关联关系
                            enable: true,
                            //autoCheckTrigger: true,
                            //nocheckInherit: true
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
                                                    //"<td><input type='checkbox' name='checkbox'value='" + titleEName[i] + ":" + a + "'></td>" +
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
                /!*error: function (info) {
                    alert(info);
                }*!/
            });
        }
    });
});*/
$(document).ready(function () {
    $('#queryBtn1').keyup(function () {
        var val = $("#lineId1").val();
        var localSelectedNode;
        var localProjectDetail;
        var coach = [];
        var projectId = $("#project", window.top.document).val();
        if (val ==""){
            alert("请输入您要查询的零件号")
        } else {
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
    })
})


function doSubmit() {

}
