$(document).ready(function () {
    var count =0;
    var localSelectedNode;
    var localSelectedNode1;
    var coach = [];
    var puids="";
    var allPuids="";
    var coach1 = [];
    var projectId = $("#project", window.top.document).val();
    if (!checkIsSelectProject(projectId)) {
        return;
    }
    $("#queryBtn1").click(function () {
        var val = $("#queryLineId").val();
        if (val == "") {
            window.Ewin.alert({message: "请输入您要查询的零件号"});
        }
        else {
            $.ajax({
                type: "GET",
                url: "pbom/processComposeTree?lineId=" + val + "&projectId=" + projectId,
                undefinedText: "",//当数据为 undefined 时显示的字符
                success: function (data) {
                    if(!data.success){
                        window.Ewin.alert({message: data.errMsg});
                        return;
                    }
                    var zNodes = data.obj;
                    initZtree(zNodes);
                },
            });
        }
    })
    var setting = {
        view: {
            selectedMulti: false,
            dblClickExpand:
                false,
            showLine:
                true,
        },
        check: {
            enable: true,
            chkStyle: "checkbox",
            chkboxType: { "Y": "s", "N": "s"}
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
            }
        }
        ,
        //回调函数
        callback: {
            /*beforeClick:function(treeId, treeNode) {
                return !treeNode.isParent;//当是父节点 返回false 不让选取
            },*/
            onCheck: function (e, treeId, treeNode) {
                var treeObj = $.fn.zTree.getZTreeObj("Ztree1");
                var nodes = treeObj.getCheckedNodes(true);
                for (var i = 0; i < nodes.length; i++) {
                    if (!nodes[i].isParent) {
                        coach.push(nodes[nodes.length - 1].puid);
                        puids +=nodes[nodes.length - 1].puid+",";
                        break;
                    }else{
                        count++;//记录父节点个数
                    }
                }
                //1.一下选择父节点 带出所有子节点
                //2.逐一选择子节点
                //3.选择子节点 有取消部分已选择子节点
                //4.选择父节点 又放弃选择
                if(nodes.length == 0){
                    coach.splice(0,coach.length);
                    puids = "";
                }else if(count+coach.length!=nodes.length){
                    coach.splice(0,coach.length);
                    puids ="";
                    for(var i = 0;i<nodes.length;i++){
                        if (!nodes[i].isParent) {
                            coach.push(nodes[i].puid);
                            puids+=nodes[i].puid+",";
                        }
                    }
                }
            },
            onClick: function (event, treeId, treeNode) {
                // if (treeNode.level != 1) {
                //     alert("该节点下有子节点,请选取子节点")
                //     return;
                // }
                localSelectedNode = treeNode;
                $.ajax({
                    url: "pbom/detail?puid=" + localSelectedNode.puid + "&projectId=" + projectId,
                    type: "GET",
                    undefinedText: "",//当数据为 undefined 时显示的字符
                    success: function (data) {
                        var result = JSON.stringify(data);
                        var ddd = JSON.parse(result);
                        var date = ddd.data;
                        var va = date[0];
                        var rel = "<table>"
                        rel += "<tr>" +
                            "<th>序号</th>" +
                            "<td></td>" +
                            "</tr><tr>"+
                            "<th>层级</th>" +
                            "<td>" + va.level + "</td>" +
                            "</tr><tr>" +
                            "<th>专业</th>" +
                            "<td>" + va.pBomOfWhichDept + "</td>" +
                            "</tr><tr>" +
                            "<th>级别</th>" +
                            "<td>" + va.rank + "</td>" +
                            "</tr><tr>" +
                            "<th>分组号</th>" +
                            "<td>" + va.groupNum + "</td>" +
                            "</tr><tr>" +
                            "<th>零件号</th>" +
                            "<td>" + va.lineId + "</td>" +
                            "</tr><tr>" +
                            "<th>名称</th>" +
                            "<td>" + va.pBomLinePartName + "</td>" +
                            "</tr><tr>" +
                            "<th>英文名称</th>" +
                            "<td>" + va.pBomLinePartEnName + "</td>" +
                            "</tr><tr>" +
                            "<th>零件分类</th>" +
                            "<td>" + va.pBomLinePartClass + "</td>" +
                            "</tr><tr>" +
                            "<th>零部件来源</th>" +
                            "<td>" + va.pBomLinePartResource + "</td>" +
                            "</tr><tr>" +
                            "<th>自制/采购</th>" +
                            "<td>" + va.resource + "</td>" +
                            "</tr><tr>" +
                            "<th>焊接/装配</th>" +
                            "<td>" + va.type + "</td>" +
                            "</tr><tr>" +
                            "<th>采购单元</th>" +
                            "<td>" + va.buyUnit + "</td>" +
                            "</tr><tr>" +
                            "<th>车间1</th>" +
                            "<td>" + va.workShop1 + "</td>" +
                            "</tr><tr>" +
                            "<th>车间2</th>" +
                            "<td>" + va.workShop2 + "</td>" +
                            "</tr><tr>" +
                            "<th>生产线</th>" +
                            "<td>" + va.productLine + "</td>" +
                            "</tr><tr>" +
                            "<th>模具类别</th>" +
                            "<td>" + va.mouldType + "</td>" +
                            "</tr><tr>" +
                            "<th>外委件</th>" +
                            "<td>" + va.outerPart + "</td>" +
                            "</tr><tr>" +
                            "<th>颜色件</th>" +
                            "<td>" + va.colorPart + "</td>" +
                            "</tr><tr>" +
                            "<th>工位</th>" +
                            "<td>" + va.station + "</td>" +
                            "</tr>"
                        rel += "</table>"
                        $("#detailTable").html(rel);
                    },
                    error: function (err) {
                        window.Ewin.alert({message: err.status});
                    }
                })
                localSelectedNode = treeNode;
            },
        }
    };
    initZtree=function (zNodes) {
        var treeObj = $.fn.zTree.getZTreeObj("Ztree1");
        if(treeObj!=null||treeObj!=undefined){
            treeObj.destroy("#Ztree1");
            // treeObj.destroy();
        }
        $.fn.zTree.init($("#Ztree1"), setting, zNodes);
    }
    $("#synthetic").click(function () {
        if (coach.length < 2) {
            window.Ewin.alert({message: "请至少选择两个零件生成工艺合件"});
        }
        else {
            window.Ewin.dialog({
                title: "生成工艺合件",
                url: "pbom/updataProcessOfFitting?puids="+puids,
                gridId: "gridId",
                width: 500,
                height: 500
            })
        }
    })
    $("#synthetic1").click(function () {
        allPuids = document.getElementById("demo3").innerText;
        if(allPuids == null || allPuids == ""){
            window.Ewin.alert({message: "请选择您要生成工艺合件的零件号"});
            return;
        }
        puids = allPuids;
        if (coach1.length!=0){
            var myData = JSON.stringify({
                "eBomPuid" :coach1[0],
                "projectId": $("#project", window.top.document).val(),
                "puids":puids
            })
            $.ajax({
                url:"pbom/add/processCompose",
                data:myData,
                type:"POST",
                contentType: "application/json",
                success: function (result) {
                    window.Ewin.alert({message: result.errMsg});
                    if(result.success){//成功
                        var zTreeObj = $.fn.zTree.getZTreeObj("Ztree1");
                        zTreeObj.destroy();
                        $("#demo4").html("<p>"+""+"</p>")
                        $("#demo3").html( "<p>"+""+"</p>");
                        var zTree = $.fn.zTree.getZTreeObj("Ztree3");
                        zTree.destroy();
                        var val = $("#queryLineId2").val();
                        $.ajax({
                            type: "GET",
                            url: "pbom/processComposeTree?lineId=" + val + "&projectId=" + projectId,
                            undefinedText: "",//当数据为 undefined 时显示的字符
                            success: function (data) {
                                if(!data.success){
                                    window.Ewin.alert({message: data.errMsg});
                                    return;
                                }
                                var setting = {
                                    view: {
                                        selectedMulti: false,
                                        dblClickExpand:
                                            false,
                                        showLine:
                                            true,
                                    },
                                    check: {
                                        enable: true,
                                        chkStyle: "radio"
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
                                        }
                                    }
                                    ,
                                    //回调函数
                                    callback: {
                                        /*beforeClick:function(treeId, treeNode) {
                                            return !treeNode.isParent;//当是父节点 返回false 不让选取
                                        },*/
                                        onCheck: function (e, treeId, treeNode) {
                                            var treeObj = $.fn.zTree.getZTreeObj("Ztree3");
                                            var nodes =
                                                // treeObj.getSelectedNodes();
                                                treeObj.getCheckedNodes(true);
                                            if (nodes.length>1){
                                                window.Ewin.alert({message: "只能指定一个工艺合件的位置"});
                                                for(var k=0;k<nodes.length;k++){
                                                    treeObj.checkNode(nodes[k]);
                                                }
                                                return;
                                            }
                                            else if (nodes.length>0&&nodes.length<2) {
                                                coach1.splice(0,coach1.length);
                                                coach1.push(nodes[0].puid);
                                            }else {
                                                coach1.splice(0,coach1.length);
                                            }
                                        },
                                        onClick: function (event, treeId, treeNode) {
                                            // if (treeNode.level != 2) {
                                            //     alert("该节点下有子节点,请选取子节点")
                                            //     return;
                                            // }
                                            localSelectedNode1 = treeNode;
                                            $.ajax({
                                                url: "pbom/detail?puid=" + localSelectedNode1.puid + "&projectId=" + projectId,
                                                type: "GET",
                                                undefinedText: "",//当数据为 undefined 时显示的字符
                                                success: function (data) {
                                                    var result = JSON.stringify(data);
                                                    var ddd = JSON.parse(result);
                                                    var date = ddd.data;
                                                    var va = date[0];
                                                    var rel = "<table>"
                                                    rel += "<tr>" +
                                                        "<th>序号</th>" +
                                                        "<td></td>" +
                                                        "</tr><tr>"+
                                                        "<th>层级</th>" +
                                                        "<td>" + va.level + "</td>" +
                                                        "</tr><tr>" +
                                                        "<th>专业</th>" +
                                                        "<td>" + va.pBomOfWhichDept + "</td>" +
                                                        "</tr><tr>" +
                                                        "<th>级别</th>" +
                                                        "<td>" + va.rank + "</td>" +
                                                        "</tr><tr>" +
                                                        "<th>分组号</th>" +
                                                        "<td>" + va.groupNum + "</td>" +
                                                        "</tr><tr>" +
                                                        "<th>零件号</th>" +
                                                        "<td>" + va.lineId + "</td>" +
                                                        "</tr><tr>" +
                                                        "<th>名称</th>" +
                                                        "<td>" + va.pBomLinePartName + "</td>" +
                                                        "</tr><tr>" +
                                                        "<th>英文名称</th>" +
                                                        "<td>" + va.pBomLinePartEnName + "</td>" +
                                                        "</tr><tr>" +
                                                        "<th>零件分类</th>" +
                                                        "<td>" + va.pBomLinePartClass + "</td>" +
                                                        "</tr><tr>" +
                                                        "<th>零部件来源</th>" +
                                                        "<td>" + va.pBomLinePartResource + "</td>" +
                                                        "</tr><tr>" +
                                                        "<th>自制/采购</th>" +
                                                        "<td>" + va.resource + "</td>" +
                                                        "</tr><tr>" +
                                                        "<th>焊接/装配</th>" +
                                                        "<td>" + va.type + "</td>" +
                                                        "</tr><tr>" +
                                                        "<th>采购单元</th>" +
                                                        "<td>" + va.buyUnit + "</td>" +
                                                        "</tr><tr>" +
                                                        "<th>车间1</th>" +
                                                        "<td>" + va.workShop1 + "</td>" +
                                                        "</tr><tr>" +
                                                        "<th>车间2</th>" +
                                                        "<td>" + va.workShop2 + "</td>" +
                                                        "</tr><tr>" +
                                                        "<th>生产线</th>" +
                                                        "<td>" + va.productLine + "</td>" +
                                                        "</tr><tr>" +
                                                        "<th>模具类别</th>" +
                                                        "<td>" + va.mouldType + "</td>" +
                                                        "</tr><tr>" +
                                                        "<th>外委件</th>" +
                                                        "<td>" + va.outerPart + "</td>" +
                                                        "</tr><tr>" +
                                                        "<th>颜色件</th>" +
                                                        "<td>" + va.colorPart + "</td>" +
                                                        "</tr><tr>" +
                                                        "<th>工位</th>" +
                                                        "<td>" + va.station + "</td>" +
                                                        "</tr>"
                                                    rel += "</table>"
                                                    $("#detailTable").html(rel);
                                                },
                                                error: function (err) {
                                                    window.Ewin.alert({message: err.status});
                                                }
                                            })
                                            localSelectedNode1 = treeNode;
                                        },
                                    }
                                };
                                var zNodes = data.obj;

                                $(document).ready(function () {
                                    $.fn.zTree.init($("#Ztree3"), setting, zNodes);
                                });
                            },
                        });
                    }
                    if(!result.success){//失败

                    }

                },
                error: function (status) {
                    window.Ewin.alert({message: status.status + ':生成工艺合件失败!'});
                }
            })
        }else {
            window.Ewin.alert({message: "请指定您要合成的位置"});
        }
    })
    function refreshParentNode() {
        var zTree = $.fn.zTree.getZTreeObj("Ztree3"),
            type = "refresh",
            silent = false,
            nodes = zTree.getSelectedNodes();
        /*根据 zTree 的唯一标识 tId 快速获取节点 JSON 数据对象*/
        var parentNode = zTree.getNodeByTId(nodes[0].parentTId);
        /*选中指定节点*/
        zTree.selectNode(parentNode);
        zTree.reAsyncChildNodes(parentNode, type, silent);
    }
    $("#queryBtn2").click(function () {
        var val = $("#queryLineId2").val();
        if (val == "") {
            window.Ewin.alert({message: "请输入您要查询的零件号"});
        }
        else {
            $.ajax({
                type: "GET",
                url: "pbom/processComposeTree?lineId=" + val + "&projectId=" + projectId,
                undefinedText: "",//当数据为 undefined 时显示的字符
                success: function (data) {
                    if(!data.success){
                        window.Ewin.alert({message: data.errMsg});
                        return;
                    }
                    var setting = {
                        view: {
                            selectedMulti: false,
                            dblClickExpand:
                                false,
                            showLine:
                                true,
                        },
                        check: {
                            enable: true,
                            chkStyle: "radio"
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
                            }
                        }
                        ,
                        //回调函数
                        callback: {
                            /*beforeClick:function(treeId, treeNode) {
                                return !treeNode.isParent;//当是父节点 返回false 不让选取
                            },*/
                            onCheck: function (e, treeId, treeNode) {
                                var treeObj = $.fn.zTree.getZTreeObj("Ztree3");
                                var nodes =
                                    // treeObj.getSelectedNodes();
                                treeObj.getCheckedNodes(true);
                                if (nodes.length>1){
                                    window.Ewin.alert({message: "只能指定一个工艺合件的位置"});
                                    for(var k=0;k<nodes.length;k++){
                                        treeObj.checkNode(nodes[k]);
                                    }
                                    return;
                                }
                                else if (nodes.length>0&&nodes.length<2) {
                                    coach1.splice(0,coach1.length);
                                    coach1.push(nodes[0].puid);
                                }else {
                                    coach1.splice(0,coach1.length);
                                }
                            },
                            onClick: function (event, treeId, treeNode) {
                                // if (treeNode.level != 2) {
                                //     alert("该节点下有子节点,请选取子节点")
                                //     return;
                                // }
                                localSelectedNode1 = treeNode;
                                $.ajax({
                                    url: "pbom/detail?puid=" + localSelectedNode1.puid + "&projectId=" + projectId,
                                    type: "GET",
                                    undefinedText: "",//当数据为 undefined 时显示的字符
                                    success: function (data) {
                                        var result = JSON.stringify(data);
                                        var ddd = JSON.parse(result);
                                        var date = ddd.data;
                                        var va = date[0];
                                        var rel = "<table>"
                                        rel += "<tr>" +
                                            "<th>序号</th>" +
                                            "<td></td>" +
                                            "</tr><tr>"+
                                            "<th>层级</th>" +
                                            "<td>" + va.level + "</td>" +
                                            "</tr><tr>" +
                                            "<th>专业</th>" +
                                            "<td>" + va.pBomOfWhichDept + "</td>" +
                                            "</tr><tr>" +
                                            "<th>级别</th>" +
                                            "<td>" + va.rank + "</td>" +
                                            "</tr><tr>" +
                                            "<th>分组号</th>" +
                                            "<td>" + va.groupNum + "</td>" +
                                            "</tr><tr>" +
                                            "<th>零件号</th>" +
                                            "<td>" + va.lineId + "</td>" +
                                            "</tr><tr>" +
                                            "<th>名称</th>" +
                                            "<td>" + va.pBomLinePartName + "</td>" +
                                            "</tr><tr>" +
                                            "<th>英文名称</th>" +
                                            "<td>" + va.pBomLinePartEnName + "</td>" +
                                            "</tr><tr>" +
                                            "<th>零件分类</th>" +
                                            "<td>" + va.pBomLinePartClass + "</td>" +
                                            "</tr><tr>" +
                                            "<th>零部件来源</th>" +
                                            "<td>" + va.pBomLinePartResource + "</td>" +
                                            "</tr><tr>" +
                                            "<th>自制/采购</th>" +
                                            "<td>" + va.resource + "</td>" +
                                            "</tr><tr>" +
                                            "<th>焊接/装配</th>" +
                                            "<td>" + va.type + "</td>" +
                                            "</tr><tr>" +
                                            "<th>采购单元</th>" +
                                            "<td>" + va.buyUnit + "</td>" +
                                            "</tr><tr>" +
                                            "<th>车间1</th>" +
                                            "<td>" + va.workShop1 + "</td>" +
                                            "</tr><tr>" +
                                            "<th>车间2</th>" +
                                            "<td>" + va.workShop2 + "</td>" +
                                            "</tr><tr>" +
                                            "<th>生产线</th>" +
                                            "<td>" + va.productLine + "</td>" +
                                            "</tr><tr>" +
                                            "<th>模具类别</th>" +
                                            "<td>" + va.mouldType + "</td>" +
                                            "</tr><tr>" +
                                            "<th>外委件</th>" +
                                            "<td>" + va.outerPart + "</td>" +
                                            "</tr><tr>" +
                                            "<th>颜色件</th>" +
                                            "<td>" + va.colorPart + "</td>" +
                                            "</tr><tr>" +
                                            "<th>工位</th>" +
                                            "<td>" + va.station + "</td>" +
                                            "</tr>"
                                        rel += "</table>"
                                        $("#detailTable").html(rel);
                                    },
                                    error: function (err) {
                                        window.Ewin.alert({message: err.status});
                                    }
                                })
                                localSelectedNode1 = treeNode;
                            },
                        }
                    };
                    var zNodes = data.obj;

                    $(document).ready(function () {
                        $.fn.zTree.init($("#Ztree3"), setting, zNodes);
                    });
                },
            });
        }
    })

})
$(document).keydown(function(event) {
    if (event.keyCode == 13) {
        $('form').each(function() {
            event.preventDefault();
        });
    }
});


