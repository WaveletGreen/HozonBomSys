var parentsPuids = [];
var childrenPuids = [];
$(document).ready(function () {
    var size = 0;
    var count = 0;
    var localSelectedNode;
    var localSelectedNode1;
    var coach = [];
    var puids = "";
    var allPuids = "";
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
                url: "pbom/processComposeTree?lineId=" + val + "&projectId=" + getProjectUid(),
                undefinedText: "",//当数据为 undefined 时显示的字符
                success: function (data) {
                    if (!data.success) {
                        window.Ewin.alert({message: data.errMsg});
                        return;
                    }
                    var zNodes = data.externalObject;
                    initZtree(zNodes);
                    $("#info_div span").text("第二步：选择需要合成的零件(至少2个,选父影响子，选子不影响父)");
                    $("#info_div").css("top", "20%");
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
            chkboxType: {"Y": "s", "N": "s"}
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
            /**
             * 不能选择最顶层
             */
            beforeCheck: function (treeId, treeNode) {
                if (treeNode.getParentNode() == null) {
                    window.Ewin.alert({message: "您不能选中顶层进行合成"});
                    return false;
                }
            },
            onCheck: function (e, treeId, treeNode) {
                size = 0;
                var treeObj = $.fn.zTree.getZTreeObj("Ztree1");
                var nodes = treeObj.getCheckedNodes(true);
                /**
                 * 只能父影响子，不能子影响父
                 */
                let localCode = sortOnlyByParent(treeNode, treeId);
                if (localCode == -1) {
                    window.Ewin.alert({message: "不能选择最顶层进行合成!"});
                    return false;
                }
                console.log("****************************************************************");
                console.log("-----------------打印对象xxx父层-----------");
                console.log(parent);
                console.log("-----------------打印对象xxx父层-----------");

                console.log("-----------------打印对象子层-----------");
                console.log(children);
                console.log("-----------------打印对象子层-----------");

                console.log("-----------------打印对象xxx父层节点-----------");
                console.log(parentNode);
                console.log("-----------------打印对象xxx父层节点-----------");

                console.log("-----------------打印对象xxx子层节点-----------");
                console.log(childrenNode);
                console.log("-----------------打印对象xxx子层节点-----------");
                console.log("#################################################################");

                //提示表格
                let selectedToCraftTable = $("#selectedToCraftTable");
                selectedToCraftTable.html("");
                let _tr = document.createElement("tr");
                let _th = document.createElement("th");
                _th.innerHTML = "已选择的父节点是:";
                _tr.appendChild(_th);
                selectedToCraftTable.append(_tr);

                let indexx = 1;
                let parentLength = 0;
                let childrenLength = 0;

                parentsPuids = [];
                childrenPuids = [];

                /*** 先获获取长度*/
                parentLength = getLength(parentNode);
                childrenLength = getLength2(childrenNode);
                //动态显示选择的节点
                let tr = document.createElement("tr");
                /**存放在3列中**/
                if (parentLength >= 3) {
                    for (let i in parentNode) {
                        if (parentNode.hasOwnProperty(i)) {
                            let td = document.createElement("td");
                            td.innerHTML = parentNode[i].lineId;
                            tr.appendChild(td);

                            parentsPuids.push(parentNode[i].puid);

                            if (indexx % 3 == 0) {
                                selectedToCraftTable.append(tr);
                                tr = document.createElement("tr");
                            }
                            else if (parentLength == indexx) {
                                selectedToCraftTable.append(tr);
                            }
                            size++;
                            indexx++;
                        }
                    }
                }
                else if (parentLength < 3) {
                    for (let i in parentNode) {
                        if (parentNode.hasOwnProperty(i)) {
                            let td = document.createElement("td");
                            td.innerHTML = parentNode[i].lineId;
                            tr.appendChild(td);
                            size++;
                            indexx++;
                        }
                    }
                    selectedToCraftTable.append(tr);
                }

                let _tr2 = document.createElement("tr");
                let _th2 = document.createElement("th");
                _th2.innerHTML = "已选择的子节点是:";
                _tr2.appendChild(_th2);
                selectedToCraftTable.append(_tr2);
                indexx = 1;
                tr = document.createElement("tr");
                if (childrenLength >= 3) {
                    for (let i in childrenNode) {
                        if (childrenNode.hasOwnProperty(i)) {
                            for (let j in childrenNode[i]) {
                                let td = document.createElement("td");
                                td.innerHTML = childrenNode[i][j].lineId;
                                tr.appendChild(td);

                                childrenPuids.push(childrenNode[i][j].puid);

                                if (indexx % 3 == 0) {
                                    selectedToCraftTable.append(tr);
                                    tr = document.createElement("tr");
                                }
                                else if (childrenLength == indexx) {
                                    selectedToCraftTable.append(tr);
                                }
                                size++;
                                indexx++;
                            }
                        }
                    }
                }
                else {
                    let tr = document.createElement("tr");
                    for (let i in childrenNode) {
                        if (childrenNode.hasOwnProperty(i)) {
                            for (let j in childrenNode[i]) {
                                let td = document.createElement("td");
                                td.innerHTML = childrenNode[i][j].lineId;
                                tr.appendChild(td);
                                indexx++;
                                size++;
                            }
                        }
                    }
                    selectedToCraftTable.append(tr);
                }
                //动态提示
                if (size >= 2) {
                    $("#info_div span").text("第三步：点击“生成工艺合件”");
                    $("#info_div").css("top", "8%");
                    $("#info_img_2").css("display", "inline");
                    $("#info_img_2").css("max-width", "36px");
                    $("#info_img_1").css("display", "none");
                    $("#synthetic").removeAttr("disabled");
                }
                else {
                    $("#info_div span").text("第二步：选择需要合成的零件(至少2个,选父影响子，选子不影响父)");
                    $("#info_div").css("top", "20%");
                    $("#info_img_1").css("display", "inline");
                    $("#info_img_1").css("max-width", "36px");
                    $("#info_img_2").css("display", "none");
                    $("#synthetic").attr("disabled", "disabled");
                }

                for (var i = 0; i < nodes.length; i++) {
                    if (!nodes[i].isParent) {
                        coach.push(nodes[nodes.length - 1].puid);
                        puids += nodes[nodes.length - 1].puid + ",";
                        break;
                    } else {
                        count++;//记录父节点个数
                    }
                }
                //1.一下选择父节点 带出所有子节点
                //2.逐一选择子节点
                //3.选择子节点 有取消部分已选择子节点
                //4.选择父节点 又放弃选择
                if (nodes.length == 0) {
                    coach.splice(0, coach.length);
                    puids = "";
                } else if (count + coach.length != nodes.length) {
                    coach.splice(0, coach.length);
                    puids = "";
                    for (var i = 0; i < nodes.length; i++) {
                        if (!nodes[i].isParent) {
                            coach.push(nodes[i].puid);
                            puids += nodes[i].puid + ",";
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
                    url: "pbom/detail?puid=" + localSelectedNode.puid + "&projectId=" + getProjectUid(),
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
                            "</tr><tr>" +
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
            }
            ,
        }
    };
    initZtree = function (zNodes) {
        var treeObj = $.fn.zTree.getZTreeObj("Ztree1");
        if (treeObj != null || treeObj != undefined) {
            treeObj.destroy("#Ztree1");
            // treeObj.destroy();
        }
        $.fn.zTree.init($("#Ztree1"), setting, zNodes);
    }
    $("#synthetic").click(function () {
        if (size < 2) {
            window.Ewin.alert({message: "请至少选择两个零件生成工艺合件"});
        }
        else {
            window.Ewin.dialog({
                title: "生成工艺合件",
                url: "pbom/updataProcessOfFitting2",
                gridId: "gridId",
                width: 500,
                height: 500
            })
            // let param = {childrenUids: childrenPuids, parentUids: parentsPuids};
            // var myData = JSON.stringify(param);
            // console.log(myData);
            // $.ajax({
            //     url: "pbom/add/processCompose2",
            //     data: myData,
            //     type: "POST",
            //     contentType: "application/json",
            //     success: function (result) {
            //         window.Ewin.alert({message: result.errMsg});
            //         if (result.success) {//成功
            //             /////////////////////////////////在这里清空结构//////////////////////////////////
            //             var zTreeObj = $.fn.zTree.getZTreeObj("Ztree1");
            //             zTreeObj.destroy();
            //             $("#demo4").html("<p>" + "" + "</p>")
            //             $("#demo3").html("<p>" + "" + "</p>");
            //             var zTree = $.fn.zTree.getZTreeObj("Ztree3");
            //             zTree.destroy();
            //             var val = $("#queryLineId2").val();
            //             $.ajax({
            //                 type: "GET",
            //                 url: "pbom/processComposeTree?lineId=" + val + "&projectId=" + getProjectUid(),
            //                 undefinedText: "",//当数据为 undefined 时显示的字符
            //                 success: function (data) {
            //                     if (!data.success) {
            //                         window.Ewin.alert({message: data.errMsg});
            //                         return;
            //                     }
            //                     var setting = {
            //                         view: {
            //                             selectedMulti: false,
            //                             dblClickExpand:
            //                                 false,
            //                             showLine:
            //                                 true,
            //                         },
            //                         check: {
            //                             enable: true,
            //                             chkStyle: "radio"
            //                         },
            //                         data: {
            //                             simpleData: {
            //                                 enable: true,
            //                                 idKey:
            //                                     "puid",
            //                                 pIdKey:
            //                                     "parentUid",
            //                                 rootPId:
            //                                     "#"
            //                             },
            //                             key: {
            //                                 checked:
            //                                     "CHECKED",
            //                                 name:
            //                                     "lineId",
            //                             }
            //                         }
            //                         ,
            //                         //回调函数
            //                         callback: {
            //                             /*beforeClick:function(treeId, treeNode) {
            //                                 return !treeNode.isParent;//当是父节点 返回false 不让选取
            //                             },*/
            //                             onCheck: function (e, treeId, treeNode) {
            //                                 var treeObj = $.fn.zTree.getZTreeObj("Ztree3");
            //                                 var nodes =
            //                                     // treeObj.getSelectedNodes();
            //                                     treeObj.getCheckedNodes(true);
            //                                 if (nodes.length > 1) {
            //                                     window.Ewin.alert({message: "只能指定一个工艺合件的位置"});
            //                                     for (var k = 0; k < nodes.length; k++) {
            //                                         treeObj.checkNode(nodes[k]);
            //                                     }
            //                                     return;
            //                                 }
            //                                 else if (nodes.length > 0 && nodes.length < 2) {
            //                                     coach1.splice(0, coach1.length);
            //                                     coach1.push(nodes[0].puid);
            //                                 } else {
            //                                     coach1.splice(0, coach1.length);
            //                                 }
            //                             },
            //                             onClick: function (event, treeId, treeNode) {
            //                                 // if (treeNode.level != 2) {
            //                                 //     alert("该节点下有子节点,请选取子节点")
            //                                 //     return;
            //                                 // }
            //                                 localSelectedNode1 = treeNode;
            //                                 $.ajax({
            //                                     url: "pbom/detail?puid=" + localSelectedNode1.puid + "&projectId=" + getProjectUid(),
            //                                     type: "GET",
            //                                     undefinedText: "",//当数据为 undefined 时显示的字符
            //                                     success: function (data) {
            //                                         var result = JSON.stringify(data);
            //                                         var ddd = JSON.parse(result);
            //                                         var date = ddd.data;
            //                                         var va = date[0];
            //                                         var rel = "<table>"
            //                                         rel += "<tr>" +
            //                                             "<th>序号</th>" +
            //                                             "<td></td>" +
            //                                             "</tr><tr>" +
            //                                             "<th>层级</th>" +
            //                                             "<td>" + va.level + "</td>" +
            //                                             "</tr><tr>" +
            //                                             "<th>专业</th>" +
            //                                             "<td>" + va.pBomOfWhichDept + "</td>" +
            //                                             "</tr><tr>" +
            //                                             "<th>级别</th>" +
            //                                             "<td>" + va.rank + "</td>" +
            //                                             "</tr><tr>" +
            //                                             "<th>分组号</th>" +
            //                                             "<td>" + va.groupNum + "</td>" +
            //                                             "</tr><tr>" +
            //                                             "<th>零件号</th>" +
            //                                             "<td>" + va.lineId + "</td>" +
            //                                             "</tr><tr>" +
            //                                             "<th>名称</th>" +
            //                                             "<td>" + va.pBomLinePartName + "</td>" +
            //                                             "</tr><tr>" +
            //                                             "<th>英文名称</th>" +
            //                                             "<td>" + va.pBomLinePartEnName + "</td>" +
            //                                             "</tr><tr>" +
            //                                             "<th>零件分类</th>" +
            //                                             "<td>" + va.pBomLinePartClass + "</td>" +
            //                                             "</tr><tr>" +
            //                                             "<th>零部件来源</th>" +
            //                                             "<td>" + va.pBomLinePartResource + "</td>" +
            //                                             "</tr><tr>" +
            //                                             "<th>自制/采购</th>" +
            //                                             "<td>" + va.resource + "</td>" +
            //                                             "</tr><tr>" +
            //                                             "<th>焊接/装配</th>" +
            //                                             "<td>" + va.type + "</td>" +
            //                                             "</tr><tr>" +
            //                                             "<th>采购单元</th>" +
            //                                             "<td>" + va.buyUnit + "</td>" +
            //                                             "</tr><tr>" +
            //                                             "<th>车间1</th>" +
            //                                             "<td>" + va.workShop1 + "</td>" +
            //                                             "</tr><tr>" +
            //                                             "<th>车间2</th>" +
            //                                             "<td>" + va.workShop2 + "</td>" +
            //                                             "</tr><tr>" +
            //                                             "<th>生产线</th>" +
            //                                             "<td>" + va.productLine + "</td>" +
            //                                             "</tr><tr>" +
            //                                             "<th>模具类别</th>" +
            //                                             "<td>" + va.mouldType + "</td>" +
            //                                             "</tr><tr>" +
            //                                             "<th>外委件</th>" +
            //                                             "<td>" + va.outerPart + "</td>" +
            //                                             "</tr><tr>" +
            //                                             "<th>颜色件</th>" +
            //                                             "<td>" + va.colorPart + "</td>" +
            //                                             "</tr><tr>" +
            //                                             "<th>工位</th>" +
            //                                             "<td>" + va.station + "</td>" +
            //                                             "</tr>"
            //                                         rel += "</table>"
            //                                         $("#detailTable").html(rel);
            //                                     },
            //                                     error: function (err) {
            //                                         window.Ewin.alert({message: err.status});
            //                                     }
            //                                 })
            //                                 localSelectedNode1 = treeNode;
            //                             },
            //                         }
            //                     };
            //                     var zNodes = data.externalObject;
            //
            //                     $(document).ready(function () {
            //                         $.fn.zTree.init($("#Ztree3"), setting, zNodes);
            //                     });
            //                 },
            //             });
            //         }
            //         if (!result.success) {//失败
            //
            //         }
            //
            //     },
            //     error: function (status) {
            //         window.Ewin.alert({message: status.status + ':生成工艺合件失败!'});
            //     }
            // })
        }
    })
    $("#synthetic1").click(function () {
        allPuids = document.getElementById("demo3").innerText;
        if (size < 2) {
            window.Ewin.alert({message: "请选择至少选择2个件进行合成"});
            return;
        }
        puids = allPuids;
        if (coach1.length != 0) {

            /*= JSON.stringify({
                            "eBomPuid": coach1[0],
                            "projectId": getProjectUid(),
                            "puids": puids
                        })*/
            $.ajax({
                url: "pbom/add/processCompose2",
                data: myData,
                type: "POST",
                contentType: "application/json",
                success: function (result) {
                    window.Ewin.alert({message: result.errMsg});
                    if (result.success) {//成功
                        /////////////////////////////////在这里清空结构//////////////////////////////////
                        var zTreeObj = $.fn.zTree.getZTreeObj("Ztree1");
                        zTreeObj.destroy();
                        $("#demo4").html("<p>" + "" + "</p>")
                        $("#demo3").html("<p>" + "" + "</p>");
                        var zTree = $.fn.zTree.getZTreeObj("Ztree3");
                        zTree.destroy();
                        var val = $("#queryLineId2").val();
                        $.ajax({
                            type: "GET",
                            url: "pbom/processComposeTree?lineId=" + val + "&projectId=" + getProjectUid(),
                            undefinedText: "",//当数据为 undefined 时显示的字符
                            success: function (data) {
                                if (!data.success) {
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
                                            if (nodes.length > 1) {
                                                window.Ewin.alert({message: "只能指定一个工艺合件的位置"});
                                                for (var k = 0; k < nodes.length; k++) {
                                                    treeObj.checkNode(nodes[k]);
                                                }
                                                return;
                                            }
                                            else if (nodes.length > 0 && nodes.length < 2) {
                                                coach1.splice(0, coach1.length);
                                                coach1.push(nodes[0].puid);
                                            } else {
                                                coach1.splice(0, coach1.length);
                                            }
                                        },
                                        onClick: function (event, treeId, treeNode) {
                                            // if (treeNode.level != 2) {
                                            //     alert("该节点下有子节点,请选取子节点")
                                            //     return;
                                            // }
                                            localSelectedNode1 = treeNode;
                                            $.ajax({
                                                url: "pbom/detail?puid=" + localSelectedNode1.puid + "&projectId=" + getProjectUid(),
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
                                                        "</tr><tr>" +
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
                                var zNodes = data.externalObject;

                                $(document).ready(function () {
                                    $.fn.zTree.init($("#Ztree3"), setting, zNodes);
                                });
                            },
                        });
                    }
                    if (!result.success) {//失败

                    }

                },
                error: function (status) {
                    window.Ewin.alert({message: status.status + ':生成工艺合件失败!'});
                }
            })
        } else {
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
                url: "pbom/processComposeTree?lineId=" + val + "&projectId=" + getProjectUid(),
                undefinedText: "",//当数据为 undefined 时显示的字符
                success: function (data) {
                    if (!data.success) {
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
                                if (nodes.length > 1) {
                                    window.Ewin.alert({message: "只能指定一个工艺合件的位置"});
                                    for (var k = 0; k < nodes.length; k++) {
                                        treeObj.checkNode(nodes[k]);
                                    }
                                    return;
                                }
                                else if (nodes.length > 0 && nodes.length < 2) {
                                    coach1.splice(0, coach1.length);
                                    coach1.push(nodes[0].puid);
                                } else {
                                    coach1.splice(0, coach1.length);
                                }
                            },
                            onClick: function (event, treeId, treeNode) {
                                // if (treeNode.level != 2) {
                                //     alert("该节点下有子节点,请选取子节点")
                                //     return;
                                // }
                                localSelectedNode1 = treeNode;
                                $.ajax({
                                    url: "pbom/detail?puid=" + localSelectedNode1.puid + "&projectId=" + getProjectUid(),
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
                                            "</tr><tr>" +
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
                    var zNodes = data.externalObject;

                    $(document).ready(function () {
                        $.fn.zTree.init($("#Ztree3"), setting, zNodes);
                    });
                },
            });
        }
    })

})
$(document).keydown(function (event) {
    if (event.keyCode == 13) {
        $('form').each(function () {
            event.preventDefault();
        });
    }
});

/**
 * 返回父层的UID，会有多个父层UID，因此需要用List接收
 * @returns {Array}
 */
function getParentUids() {
    console.log("---------------parentsPuids--------------------")
    console.log(parentsPuids);
    console.log("---------------parentsPuids--------------------")
    return parentsPuids;
}

/**
 * 返回子层UID，同样有多个子层UID，需要用List接收
 * @returns {Array}
 */
function getChildrenUids() {
    console.log("-------------------childrenPuids----------------")
    console.log(childrenPuids);
    console.log("-------------------childrenPuids----------------")
    return childrenPuids;
}


