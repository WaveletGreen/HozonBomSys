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
$(function() {
    $('#lineId1').keyup(function () {
        var val = $(this).val();
        var localSelectedNode;
        var localProjectDetail;
        var coach = [];
        var projectId=$("#project", window.top.document).val();
        console.log(projectId);
        if (val != "") {
            $.ajax({
                type: "GET",
                url: "pbom/processComposeTree?lineId=" +val+"&projectId="+projectId,
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
                                        url: "pbom/detail?lineId=" + localSelectedNode.lineId +  "&projectId="+projectId,
                                        type: "GET",
                                        success: function (data) {
                                            console.log(data);
                                            var result = data.externalObject;
                                            var lineId = localSelectedNode.lineId;
                                            var titleName = result[0];
                                            var titleEName = result[1];
                                            var value = result[2];
                                            var titleNameAll = "";
                                            var ck = "";
                                            for (var i = 0; i < titleName.length; i++) {
                                                var checkbox = "<td><input type='checkbox'id='checkbox'></td>"
                                                var titleName1 ="<td>"+titleName[i]+"</td>";
                                                titleNameAll += titleName1;
                                                ck += checkbox;
                                            }
                                            var rel = "<table>"+ "<tr>"+ck+"</tr>"+"<tr>"+titleNameAll+"</tr>"+ "<tr>";

                                            for (var i = 0; i <titleEName.length ; i++) {
                                                rel+= "<td>"+eval(("value."+(titleEName[i])))+"</td>";
                                            }
                                            $("#detailTable").html(rel);
                                            var r, re;
                                            var s = detailTable.outerHTML;
                                            re = /<table(.[^>]*)>/i;
                                            r = s.match(re)[0].replace(" id=", " oldid=");
                                            var tablehtml = r
                                            for (var i = 0; i < detailTable.rows[0].cells.length; i++) {
                                                tablehtml += "<tr>"
                                                for (var k = 0; k < detailTable.rows.length; k++) {
                                                    tablehtml += detailTable.rows[k].cells[i].outerHTML
                                                }
                                                tablehtml += "</wtr>"
                                            }
                                            tablehtml += "</table>"
                                            newtable.innerHTML = tablehtml;
//下面这一句是让JS执行时隐藏原来的表格内容，达到新表格在原来的表格位置刷出来的效果。
                                            document.getElementById("detailTable").style.display = "none";
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
                                localSelectedNode=treeNode;
                            }

                        }
                    };

                    var zNodes =data;

                    $(document).ready(function(){
                        $.fn.zTree.init($("#Ztree1"), setting, zNodes);
                    });

                },
                error: function (info) {
                    alert(info);
                }
            });
        }
    });
});


/**
 *  第二个树型结构的demo
 */
$(function() {
    $('#lineId3').keyup(function () {
        var val = $(this).val();
        var localSelectedNode;
        var localProjectDetail;
        var coach = [];
        var projectId=$("#project", window.top.document).val();
        console.log(projectId);
        if (val != "") {
            $.ajax({
                type: "GET",
                url: "pbom/processComposeTree?lineId=" +val+"&projectId="+projectId,
                undefinedText: "",//当数据为 undefined 时显示的字符
                success: function (data) {
                    console.log(data);
                    var setting = {
                        view: {
                            /*addHoverDom: addHoverDom,
                            removeHoverDom: removeHoverDom,*/
                            selectedMulti: false,
                            dblClickExpand: false,
                            showLine: true,
                        },
                        check: {
                            enable: true,
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
                    };
                    var zNodes =data;

                    $(document).ready(function(){
                        $.fn.zTree.init($("#Ztree3"), setting, zNodes);
                    });
                    function onCheck(e,treeId,treeNode) {
                        var treeObj = $.fn.zTree.getZTreeObj("treeDemo"),
                            nodes = treeObj.getCheckedNodes(true),
                            v = "";
                        for (var i = 0; i < nodes.length; i++) {
                            v += nodes[i].name + ",";
                            alert(nodes[i].pIdKey); //获取选中节点的值
                        }
                    }
                },
                error: function (info) {
                    alert(info);
                }
            });
        }
    });
});