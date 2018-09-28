/***
 * 选中的父层节点主键，记录了eBOM的主键
 * @type {Array}
 */
let parentsPuids = [];
/**选中父节点的零件号*/
let parentsParts = [];
/***
 * 选中的子层节点主键，记录了eBOM的主键
 * @type {Array}
 */
let childrenPuids = [];
/**选中子节点的零件号*/
let childrenParts = [];

/**
 * 目标节点
 */
let targetNode = [];
/**
 * 目标节点UID
 */
let targetPointPuids = [];
/**
 * 目标节点零件
 */
let targetPointParts = [];

let collectedData = {};

let projectUid = "";
/**
 * 选中的节点数量，父节点的数量将覆盖子节点的数量
 * @type {number}
 */
var size = 0;
var targetSize = 0;

var count = 0;
var localSelectedNode1;
/***
 * 选中的节点
 */
let localSelectedNode;
var coach = [];
var puids = "";
var allPuids = "";
var coach1 = [];

let debug = false;
/**
 * 初始化*/
$(document).ready(function () {
    var projectId = getProjectUid();
    /**项目检查点*/
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
    initZtree = function (zNodes) {
        var treeObj = $.fn.zTree.getZTreeObj("Ztree1");
        if (treeObj != null || treeObj != undefined) {
            treeObj.destroy("#Ztree1");
            // treeObj.destroy();
        }
        $.fn.zTree.init($("#Ztree1"), setting, zNodes);
    }
    /**模拟合成*/
    $("#synthetic").click(function () {
        simulateSynthesise();
    })
    /**指定挂在地点*/
    $("#synthetic1").click(function () {
        assignPoints();
    });
    // function refreshParentNode() {
    //     var zTree = $.fn.zTree.getZTreeObj("Ztree3"),
    //         type = "refresh",
    //         silent = false,
    //         nodes = zTree.getSelectedNodes();
    //     /*根据 zTree 的唯一标识 tId 快速获取节点 JSON 数据对象*/
    //     var parentNode = zTree.getNodeByTId(nodes[0].parentTId);
    //     /*选中指定节点*/
    //     zTree.selectNode(parentNode);
    //     zTree.reAsyncChildNodes(parentNode, type, silent);
    // }
    /***查询挂载树*/
    $("#queryBtn2").click(function () {
        queryAssignTree();
    })

});

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
    if (debug) {
        console.log("---------------parentsPuids--------------------")
        console.log(parentsPuids);
        console.log("---------------parentsPuids--------------------")
    }
    return parentsPuids;
}

/**
 * 返回子层UID，同样有多个子层UID，需要用List接收
 * @returns {Array}
 */
function getChildrenUids() {
    if (debug) {
        console.log("-------------------childrenPuids----------------")
        console.log(childrenPuids);
        console.log("-------------------childrenPuids----------------")
    }
    return childrenPuids;
}

// function generateNewTree(arrayList) {
//     var setting = {
//         view: {
//             selectedMulti: false,
//             dblClickExpand:
//                 false,
//             showLine:
//                 true,
//         },
//         check: {
//             enable: true,
//             chkStyle: "checkbox",
//             chkboxType: {"Y": "s", "N": "s"}
//         },
//         data: {
//             simpleData: {
//                 enable: true,
//                 idKey:
//                     "puid",
//                 pIdKey:
//                     "parentUid",
//                 rootPId:
//                     "#"
//             },
//             key: {
//                 checked:
//                     "CHECKED",
//                 name:
//                     "lineId",
//             }
//         }
//         ,
//         //回调函数
//         callback: {
//             /*beforeClick:function(treeId, treeNode) {
//                 return !treeNode.isParent;//当是父节点 返回false 不让选取
//             },*/
//             /**
//              * 不能选择最顶层
//              */
//             beforeCheck: function (treeId, treeNode) {
//                 if (treeNode.getParentNode() == null) {
//                     window.Ewin.alert({message: "您不能选中顶层进行合成"});
//                     return false;
//                 }
//             },
//             onCheck: function (e, treeId, treeNode) {
//                 size = 0;
//                 var treeObj = $.fn.zTree.getZTreeObj("Ztree1");
//                 var nodes = treeObj.getCheckedNodes(true);
//                 /**
//                  * 只能父影响子，不能子影响父
//                  */
//                 let localCode = sortOnlyByParent(treeNode, treeId);
//                 if (localCode == -1) {
//                     window.Ewin.alert({message: "不能选择最顶层进行合成!"});
//                     return false;
//                 }
//                 console.log("****************************************************************");
//                 console.log("-----------------打印对象xxx父层-----------");
//                 console.log(parent);
//                 console.log("-----------------打印对象xxx父层-----------");
//
//                 console.log("-----------------打印对象子层-----------");
//                 console.log(children);
//                 console.log("-----------------打印对象子层-----------");
//
//                 console.log("-----------------打印对象xxx父层节点-----------");
//                 console.log(parentNode);
//                 console.log("-----------------打印对象xxx父层节点-----------");
//
//                 console.log("-----------------打印对象xxx子层节点-----------");
//                 console.log(childrenNode);
//                 console.log("-----------------打印对象xxx子层节点-----------");
//                 console.log("#################################################################");
//
//                 //提示表格
//                 let selectedToCraftTable = $("#selectedToCraftTable");
//                 selectedToCraftTable.html("");
//                 let _tr = document.createElement("tr");
//                 let _th = document.createElement("th");
//                 _th.innerHTML = "已选择的父节点是:";
//                 _tr.appendChild(_th);
//                 selectedToCraftTable.append(_tr);
//
//                 let indexx = 1;
//                 let parentLength = 0;
//                 let childrenLength = 0;
//                 /**清空*/
//                 if (parentsPuids.length > 0) {
//                     parentsPuids.splice(0, parentsPuids.length);
//                 }
//                 if (childrenPuids.length > 0) {
//                     childrenPuids.splice(0, childrenPuids.length);
//                 }
//
//                 /*** 先获获取长度*/
//                 parentLength = getLength(parentNode);
//                 childrenLength = getLength2(childrenNode);
//                 //动态显示选择的节点
//                 let tr = document.createElement("tr");
//                 /**存放在3列中**/
//                 if (parentLength >= 3) {
//                     for (let i in parentNode) {
//                         if (parentNode.hasOwnProperty(i)) {
//                             let td = document.createElement("td");
//                             td.innerHTML = parentNode[i].lineId;
//                             tr.appendChild(td);
//
//                             parentsPuids.push(parentNode[i].puid);
//
//                             if (indexx % 3 == 0) {
//                                 selectedToCraftTable.append(tr);
//                                 tr = document.createElement("tr");
//                             }
//                             else if (parentLength == indexx) {
//                                 selectedToCraftTable.append(tr);
//                             }
//                             size++;
//                             indexx++;
//                         }
//                     }
//                 }
//                 else if (parentLength < 3) {
//                     for (let i in parentNode) {
//                         if (parentNode.hasOwnProperty(i)) {
//                             let td = document.createElement("td");
//                             td.innerHTML = parentNode[i].lineId;
//                             tr.appendChild(td);
//
//                             parentsPuids.push(parentNode[i].puid);
//
//                             size++;
//                             indexx++;
//                         }
//                     }
//                     selectedToCraftTable.append(tr);
//                 }
//
//                 let _tr2 = document.createElement("tr");
//                 let _th2 = document.createElement("th");
//                 _th2.innerHTML = "已选择的子节点是:";
//                 _tr2.appendChild(_th2);
//                 selectedToCraftTable.append(_tr2);
//                 indexx = 1;
//                 tr = document.createElement("tr");
//                 if (childrenLength >= 3) {
//                     for (let i in childrenNode) {
//                         if (childrenNode.hasOwnProperty(i)) {
//                             for (let j in childrenNode[i]) {
//                                 let td = document.createElement("td");
//                                 td.innerHTML = childrenNode[i][j].lineId;
//                                 tr.appendChild(td);
//
//                                 childrenPuids.push(childrenNode[i][j].puid);
//
//                                 if (indexx % 3 == 0) {
//                                     selectedToCraftTable.append(tr);
//                                     tr = document.createElement("tr");
//                                 }
//                                 else if (childrenLength == indexx) {
//                                     selectedToCraftTable.append(tr);
//                                 }
//                                 size++;
//                                 indexx++;
//                             }
//                         }
//                     }
//                 }
//                 else {
//                     let tr = document.createElement("tr");
//                     for (let i in childrenNode) {
//                         if (childrenNode.hasOwnProperty(i)) {
//                             for (let j in childrenNode[i]) {
//                                 let td = document.createElement("td");
//                                 td.innerHTML = childrenNode[i][j].lineId;
//                                 tr.appendChild(td);
//
//                                 childrenPuids.push(childrenNode[i][j].puid);
//
//                                 indexx++;
//                                 size++;
//                             }
//                         }
//                     }
//                     selectedToCraftTable.append(tr);
//                 }
//                 //动态提示
//                 if (size >= 2) {
//                     $("#info_div span").text("第三步：点击“生成工艺合件”");
//                     $("#info_div").css("top", "8%");
//                     $("#info_img_2").css("display", "inline");
//                     $("#info_img_2").css("max-width", "36px");
//                     $("#info_img_1").css("display", "none");
//                     $("#synthetic").removeAttr("disabled");
//                 }
//                 else {
//                     $("#info_div span").text("第二步：选择需要合成的零件(至少2个,选父影响子，选子不影响父)");
//                     $("#info_div").css("top", "20%");
//                     $("#info_img_1").css("display", "inline");
//                     $("#info_img_1").css("max-width", "36px");
//                     $("#info_img_2").css("display", "none");
//                     $("#synthetic").attr("disabled", "disabled");
//                 }
//
//                 for (var i = 0; i < nodes.length; i++) {
//                     if (!nodes[i].isParent) {
//                         coach.push(nodes[nodes.length - 1].puid);
//                         puids += nodes[nodes.length - 1].puid + ",";
//                         break;
//                     } else {
//                         count++;//记录父节点个数
//                     }
//                 }
//                 //1.一下选择父节点 带出所有子节点
//                 //2.逐一选择子节点
//                 //3.选择子节点 有取消部分已选择子节点
//                 //4.选择父节点 又放弃选择
//                 if (nodes.length == 0) {
//                     coach.splice(0, coach.length);
//                     puids = "";
//                 } else if (count + coach.length != nodes.length) {
//                     coach.splice(0, coach.length);
//                     puids = "";
//                     for (var i = 0; i < nodes.length; i++) {
//                         if (!nodes[i].isParent) {
//                             coach.push(nodes[i].puid);
//                             puids += nodes[i].puid + ",";
//                         }
//                     }
//                 }
//             },
//             onClick: function (event, treeId, treeNode) {
//                 // if (treeNode.level != 1) {
//                 //     alert("该节点下有子节点,请选取子节点")
//                 //     return;
//                 // }
//                 $.ajax({
//                     url: "pbom/detail?puid=" + treeNode.puid + "&projectId=" + getProjectUid(),
//                     type: "GET",
//                     undefinedText: "",//当数据为 undefined 时显示的字符
//                     success: function (data) {
//                         var result = JSON.stringify(data);
//                         var ddd = JSON.parse(result);
//                         var date = ddd.data;
//                         var va = date[0];
//                         var rel = doGetTableByNode(va);
//                         $("#detailTable").html(rel);
//                     },
//                     error: function (err) {
//                         window.Ewin.alert({message: err.status});
//                     }
//                 })
//             }
//             ,
//         }
//     };
//     initZtree = function (zNodes) {
//         var treeObj = $.fn.zTree.getZTreeObj("Ztree1");
//         if (treeObj != null || treeObj != undefined) {
//             treeObj.destroy("#Ztree1");
//             // treeObj.destroy();
//         }
//         $.fn.zTree.init($("#Ztree1"), setting, zNodes);
//     }
// }
/**
 * 载入的时结果Tree
 * @param data 后台传入的模拟Tree
 */
function doReloadTree(result) {
    console.log("再次初始化ZTree");
    $("#demo1").html("<p class=\"text-primary\">" + "合成结果：</p>");
    $("#demo2").css("visibility", "hidden");
    $.fn.zTree.init($("#Ztree1"), setting2, result.data);
    collectedData = result.collectedData;
    projectUid = result.projectUid;
    /**提示特效*/
    var treeObj = $.fn.zTree.getZTreeObj("Ztree1");
    var nodes = treeObj.getNodes();
    treeObj.checkNode(nodes[0], true, true);
    $("#queryBtn2").removeAttr("disabled");
    $("#info_div2").css("display", "block");
    $("#info_div").css("display", "none");
    $("#synthetic").attr("disabled", "disabled");

}

/**
 * 源Tree设置
 */
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
            if (debug) {
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
            }
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
            /**清空*/
            if (parentsPuids.length > 0) {
                parentsPuids.splice(0, parentsPuids.length);
                parentsParts.splice(0, parentsParts.length);
            }
            if (childrenPuids.length > 0) {
                childrenPuids.splice(0, childrenPuids.length);
                childrenParts.splice(0, childrenParts.length);
            }

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

                        parentsParts.push(parentNode[i].lineId);

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

                        parentsPuids.push(parentNode[i].puid);

                        parentsParts.push(parentNode[i].lineId);

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

                            childrenParts.push(childrenNode[i][j].lineId);

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

                            childrenPuids.push(childrenNode[i][j].puid);

                            childrenParts.push(childrenNode[i][j].tId);

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

            // for (var i = 0; i < nodes.length; i++) {
            //     if (!nodes[i].isParent) {
            //         coach.push(nodes[nodes.length - 1].puid);
            //         puids += nodes[nodes.length - 1].puid + ",";
            //         break;
            //     } else {
            //         count++;//记录父节点个数
            //     }
            // }
            // //1.一下选择父节点 带出所有子节点
            // //2.逐一选择子节点
            // //3.选择子节点 有取消部分已选择子节点
            // //4.选择父节点 又放弃选择
            // if (nodes.length == 0) {
            //     coach.splice(0, coach.length);
            //     puids = "";
            // } else if (count + coach.length != nodes.length) {
            //     coach.splice(0, coach.length);
            //     puids = "";
            //     for (var i = 0; i < nodes.length; i++) {
            //         if (!nodes[i].isParent) {
            //             coach.push(nodes[i].puid);
            //             puids += nodes[i].puid + ",";
            //         }
            //     }
            // }
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
                    var rel = doGetTableByNode(va);
                    //     = "<table>"
                    // rel += "<tr>" +
                    //     "<th>序号</th>" +
                    //     "<td></td>" +
                    //     "</tr><tr>" +
                    //     "<th>层级</th>" +
                    //     "<td>" + va.level + "</td>" +
                    //     "</tr><tr>" +
                    //     "<th>专业</th>" +
                    //     "<td>" + va.pBomOfWhichDept + "</td>" +
                    //     "</tr><tr>" +
                    //     "<th>级别</th>" +
                    //     "<td>" + va.rank + "</td>" +
                    //     "</tr><tr>" +
                    //     "<th>分组号</th>" +
                    //     "<td>" + va.groupNum + "</td>" +
                    //     "</tr><tr>" +
                    //     "<th>零件号</th>" +
                    //     "<td>" + va.lineId + "</td>" +
                    //     "</tr><tr>" +
                    //     "<th>名称</th>" +
                    //     "<td>" + va.pBomLinePartName + "</td>" +
                    //     "</tr><tr>" +
                    //     "<th>英文名称</th>" +
                    //     "<td>" + va.pBomLinePartEnName + "</td>" +
                    //     "</tr><tr>" +
                    //     "<th>零件分类</th>" +
                    //     "<td>" + va.pBomLinePartClass + "</td>" +
                    //     "</tr><tr>" +
                    //     "<th>零部件来源</th>" +
                    //     "<td>" + va.pBomLinePartResource + "</td>" +
                    //     "</tr><tr>" +
                    //     "<th>自制/采购</th>" +
                    //     "<td>" + va.resource + "</td>" +
                    //     "</tr><tr>" +
                    //     "<th>焊接/装配</th>" +
                    //     "<td>" + va.type + "</td>" +
                    //     "</tr><tr>" +
                    //     "<th>采购单元</th>" +
                    //     "<td>" + va.buyUnit + "</td>" +
                    //     "</tr><tr>" +
                    //     "<th>车间1</th>" +
                    //     "<td>" + va.workShop1 + "</td>" +
                    //     "</tr><tr>" +
                    //     "<th>车间2</th>" +
                    //     "<td>" + va.workShop2 + "</td>" +
                    //     "</tr><tr>" +
                    //     "<th>生产线</th>" +
                    //     "<td>" + va.productLine + "</td>" +
                    //     "</tr><tr>" +
                    //     "<th>模具类别</th>" +
                    //     "<td>" + va.mouldType + "</td>" +
                    //     "</tr><tr>" +
                    //     "<th>外委件</th>" +
                    //     "<td>" + va.outerPart + "</td>" +
                    //     "</tr><tr>" +
                    //     "<th>颜色件</th>" +
                    //     "<td>" + va.colorPart + "</td>" +
                    //     "</tr><tr>" +
                    //     "<th>工位</th>" +
                    //     "<td>" + va.station + "</td>" +
                    //     "</tr>"
                    // rel += "</table>"
                    $("#detailTable").html(rel);
                },
                error: function (err) {
                    window.Ewin.alert({message: err.status});
                }
            })
        }
        ,
    }
};
/**
 * 结果Tree设置
 * */
var setting2 = {
    view: {
        selectedMulti: false,
        dblClickExpand:
            false,
        showLine:
            true,
    },
    check: {
        enable: false,
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
        onCheck: function (e, treeId, treeNode) {
            var treeObj = $.fn.zTree.getZTreeObj("Ztree1");
            var nodes = treeObj.getCheckedNodes(true);
            for (var i = 0; i < nodes.length; i++) {
                allPuids += nodes[i].puid + ","
            }
            $("#demo3").html("<p>" + allPuids + "</p>");
            if (count != nodes.length) {
                $("#demo3").html("<p>" + "</p>");
                count = 0;
                allPuids = "";
                for (var i = 0; i < nodes.length; i++) {
                    allPuids += nodes[i].puid + ","
                    count++;
                }
                $("#demo3").html("<p>" + allPuids + "</p>");
            }
        },
        onClick: function (event, treeId, treeNode) {
            $.ajax({
                url: "pbom/detail?puid=" + treeNode.puid + "&projectId=" + $("#project", window.top.document).val(),
                type: "GET",
                undefinedText: "",//当数据为 undefined 时显示的字符
                success: function (data) {
                    var result = JSON.stringify(data);
                    var ddd = JSON.parse(result);
                    var date = ddd.data;
                    if (undefined == date) {
                        return;
                    }
                    var va = date[0];
                    var rel = doGetTableByNode(va);
                    // "<table>"
                    // rel += "<tr>" +
                    //     "<th>序号</th>" +
                    //     "<td></td>" +
                    //     "</tr><tr>" +
                    //     "<th>层级</th>" +
                    //     "<td>" + va.level + "</td>" +
                    //     "</tr><tr>" +
                    //     "<th>专业</th>" +
                    //     "<td>" + va.pBomOfWhichDept + "</td>" +
                    //     "</tr><tr>" +
                    //     "<th>级别</th>" +
                    //     "<td>" + va.rank + "</td>" +
                    //     "</tr><tr>" +
                    //     "<th>分组号</th>" +
                    //     "<td>" + va.groupNum + "</td>" +
                    //     "</tr><tr>" +
                    //     "<th>零件号</th>" +
                    //     "<td>" + va.lineId + "</td>" +
                    //     "</tr><tr>" +
                    //     "<th>名称</th>" +
                    //     "<td>" + va.pBomLinePartName + "</td>" +
                    //     "</tr><tr>" +
                    //     "<th>英文名称</th>" +
                    //     "<td>" + va.pBomLinePartEnName + "</td>" +
                    //     "</tr><tr>" +
                    //     "<th>零件分类</th>" +
                    //     "<td>" + va.pBomLinePartClass + "</td>" +
                    //     "</tr><tr>" +
                    //     "<th>零部件来源</th>" +
                    //     "<td>" + va.pBomLinePartResource + "</td>" +
                    //     "</tr><tr>" +
                    //     "<th>自制/采购</th>" +
                    //     "<td>" + va.resource + "</td>" +
                    //     "</tr><tr>" +
                    //     "<th>焊接/装配</th>" +
                    //     "<td>" + va.type + "</td>" +
                    //     "</tr><tr>" +
                    //     "<th>采购单元</th>" +
                    //     "<td>" + va.buyUnit + "</td>" +
                    //     "</tr><tr>" +
                    //     "<th>车间1</th>" +
                    //     "<td>" + va.workShop1 + "</td>" +
                    //     "</tr><tr>" +
                    //     "<th>车间2</th>" +
                    //     "<td>" + va.workShop2 + "</td>" +
                    //     "</tr><tr>" +
                    //     "<th>生产线</th>" +
                    //     "<td>" + va.productLine + "</td>" +
                    //     "</tr><tr>" +
                    //     "<th>模具类别</th>" +
                    //     "<td>" + va.mouldType + "</td>" +
                    //     "</tr><tr>" +
                    //     "<th>外委件</th>" +
                    //     "<td>" + va.outerPart + "</td>" +
                    //     "</tr><tr>" +
                    //     "<th>颜色件</th>" +
                    //     "<td>" + va.colorPart + "</td>" +
                    //     "</tr><tr>" +
                    //     "<th>工位</th>" +
                    //     "<td>" + va.station + "</td>" +
                    //     "</tr>"
                    // rel += "</table>"
                    $("#detailTable").html(rel);
                },
                error: function (err) {
                    alert(err.status);
                }
            })
            localSelectedNode = treeNode;
        },
    }
};
/**
 * 挂在源设置
 */
var setting3 = {
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
        chkboxType: {"Y": "", "N": ""}
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
            var nodes = treeObj.getCheckedNodes();
            targetSize = 0;
            let isContinue = false;
            if (treeNode.CHECKED) {
                //校验是否挂载自身
                isContinue = checkAssignToItself(treeNode, true);
            }
            else {
                isContinue = checkAssignToItself(treeNode, false);
            }
            //回显到table中
            tellMeWhatISelected();

            if (treeNode.lineId == collectedData.lineId) {
                window.Ewin.alert({message: "您不能将合成的产生的新件挂载到新生成的件上"});
                isContinue = false;
            }
            if (!isContinue) {
                treeObj.checkNode(treeNode, false, true);
                treeObj.updateNode(treeNode);
            }
            if (0 < targetSize) {
                $("#info_div3").css("display", "block");
                $("#synthetic1").removeAttr("disabled");
            }
            else {
                $("#info_div3").css("display", "none");
                $("#synthetic1").attr("disabled", "disabled");
            }
            //显示节点数据
            //带上的tree
            var tree1 = $.fn.zTree.getZTreeObj("Ztree1");
            if (treeNode.CHECKED) {
                if (isContinue) {

                    var node = tree1.getNodes(); //可以获取所有的父节点
                    treeObj.addNodes(treeNode, node);
                    let c = treeNode.children;
                    let cn = null;
                    for (let i in c) {
                        if (c[i].lineId == collectedData.lineId) {
                            cn = c[i];
                            break;
                        }
                    }
                    ///////这里应该是将新家的mpde//////
                    setUnCheckable(cn, treeObj);
                }
            }
            //移除节点数据
            else {
                ///如果需要取消选中根节点之后，不将根节点下的数据删除，重新刷新一下tree，或者对根节点进行单独判断
                if (isContinue) {
                    if (treeNode.isParent) {
                        let children = treeNode.children;
                        //返回一个根节点
                        var first = getRoot(tree1);
                        for (let i in children) {
                            if (children[i].lineId == first.lineId) {
                                treeObj.removeNode(children[i]);
                                break;
                            }
                        }
                    }
                    // treeObj.removeChildNodes(treeNode);
                }
            }
            return isContinue;
        },
        onClick: function (event, treeId, treeNode) {
            $.ajax({
                url: "pbom/detail?puid=" + treeNode.puid + "&projectId=" + $("#project", window.top.document).val(),
                type: "GET",
                undefinedText: "",//当数据为 undefined 时显示的字符
                success: function (data) {
                    var result = JSON.stringify(data);
                    var ddd = JSON.parse(result);
                    var date = ddd.data;
                    if (undefined == date) {
                        return;
                    }
                    var va = date[0];
                    var rel = doGetTableByNode(va);
                    // "<table>"
                    // rel += "<tr>" +
                    //     "<th>序号</th>" +
                    //     "<td></td>" +
                    //     "</tr><tr>" +
                    //     "<th>层级</th>" +
                    //     "<td>" + va.level + "</td>" +
                    //     "</tr><tr>" +
                    //     "<th>专业</th>" +
                    //     "<td>" + va.pBomOfWhichDept + "</td>" +
                    //     "</tr><tr>" +
                    //     "<th>级别</th>" +
                    //     "<td>" + va.rank + "</td>" +
                    //     "</tr><tr>" +
                    //     "<th>分组号</th>" +
                    //     "<td>" + va.groupNum + "</td>" +
                    //     "</tr><tr>" +
                    //     "<th>零件号</th>" +
                    //     "<td>" + va.lineId + "</td>" +
                    //     "</tr><tr>" +
                    //     "<th>名称</th>" +
                    //     "<td>" + va.pBomLinePartName + "</td>" +
                    //     "</tr><tr>" +
                    //     "<th>英文名称</th>" +
                    //     "<td>" + va.pBomLinePartEnName + "</td>" +
                    //     "</tr><tr>" +
                    //     "<th>零件分类</th>" +
                    //     "<td>" + va.pBomLinePartClass + "</td>" +
                    //     "</tr><tr>" +
                    //     "<th>零部件来源</th>" +
                    //     "<td>" + va.pBomLinePartResource + "</td>" +
                    //     "</tr><tr>" +
                    //     "<th>自制/采购</th>" +
                    //     "<td>" + va.resource + "</td>" +
                    //     "</tr><tr>" +
                    //     "<th>焊接/装配</th>" +
                    //     "<td>" + va.type + "</td>" +
                    //     "</tr><tr>" +
                    //     "<th>采购单元</th>" +
                    //     "<td>" + va.buyUnit + "</td>" +
                    //     "</tr><tr>" +
                    //     "<th>车间1</th>" +
                    //     "<td>" + va.workShop1 + "</td>" +
                    //     "</tr><tr>" +
                    //     "<th>车间2</th>" +
                    //     "<td>" + va.workShop2 + "</td>" +
                    //     "</tr><tr>" +
                    //     "<th>生产线</th>" +
                    //     "<td>" + va.productLine + "</td>" +
                    //     "</tr><tr>" +
                    //     "<th>模具类别</th>" +
                    //     "<td>" + va.mouldType + "</td>" +
                    //     "</tr><tr>" +
                    //     "<th>外委件</th>" +
                    //     "<td>" + va.outerPart + "</td>" +
                    //     "</tr><tr>" +
                    //     "<th>颜色件</th>" +
                    //     "<td>" + va.colorPart + "</td>" +
                    //     "</tr><tr>" +
                    //     "<th>工位</th>" +
                    //     "<td>" + va.station + "</td>" +
                    //     "</tr>"
                    // rel += "</table>"
                    $("#detailTable").html(rel);
                },
                error: function (err) {
                    alert(err.status);
                }
            })
        },
    }
};

/**
 * 根据节点信息获取详情
 * @param va
 * @returns {string}
 */
function doGetTableByNode(va) {
    let rel = "<table>" +
        "<tr>" +
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
        "<td>" + (va.pBomLinePartEnName == undefined ? "" : va.pBomLinePartEnName) + "</td>" +
        "</tr><tr>" +
        "<th>零件分类</th>" +
        "<td>" + (va.pBomLinePartClass == undefined ? "" : va.pBomLinePartClass) + "</td>" +
        "</tr><tr>" +
        "<th>零部件来源</th>" +
        "<td>" + (va.pBomLinePartResource == undefined ? "" : va.pBomLinePartResource) + "</td>" +
        "</tr><tr>" +
        "<th>自制/采购</th>" +
        "<td>" + (va.resource == undefined ? "" : va.resource) + "</td>" +
        "</tr><tr>" +
        "<th>焊接/装配</th>" +
        "<td>" + (va.type == undefined ? "" : va.type) + "</td>" +
        "</tr><tr>" +
        "<th>采购单元</th>" +
        "<td>" + (va.buyUnit == undefined ? "" : va.buyUnit) + "</td>" +
        "</tr><tr>" +
        "<th>车间1</th>" +
        "<td>" + (va.workShop1 == undefined ? "" : va.workShop1) + "</td>" +
        "</tr><tr>" +
        "<th>车间2</th>" +
        "<td>" + (va.workShop2 == undefined ? "" : va.workShop2) + "</td>" +
        "</tr><tr>" +
        "<th>生产线</th>" +
        "<td>" + (va.productLine == undefined ? "" : va.productLine) + "</td>" +
        "</tr><tr>" +
        "<th>模具类别</th>" +
        "<td>" + (va.mouldType == undefined ? "" : va.mouldType) + "</td>" +
        "</tr><tr>" +
        "<th>外委件</th>" +
        "<td>" + (va.outerPart == undefined ? "" : va.outer) +
        "</td>" +
        "</tr><tr>" +
        "<th>颜色件</th>" +
        "<td>" + (va.colorPart == undefined ? "" : va.colorPart) + "</td>" +
        "</tr><tr>" +
        "<th>工位</th>" +
        "<td>" + (va.station == undefined ? "" : va.station) + "</td>" +
        "</tr>" +
        "</table>";
    return rel;
}

/**
 * 模拟合成
 */
function simulateSynthesise() {
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
}

/**
 * 指定挂在点
 */
function assignPoints() {
    if (targetSize <= 0) {
        window.Ewin.alert({message: "请选择至少选择2个件进行合成"});
        return;
    }
    let param = {
        projectUid: projectUid,
        parentUids: parentsPuids,
        childrenUids: childrenPuids,
        collectedData: collectedData,
        targetPointPuids: targetPointPuids
    }
    if (debug) {
        console.log(param);
    }
    window.Ewin.confirm({title: '提示', message: '您确定生成工艺合件？该操作过程不可逆', width: 500}).on(function (e) {
        if (e) {
            $.ajax({
                url: "pbom/doGenerateProcessCompose",
                data: JSON.stringify(param),
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
                    window.Ewin.alert({message: status.status + ':生成工艺合件失败!服务器异常，请尝试重新登录'});
                }
            })
        }
    })
}

/**
 * 查询挂载树
 */
function queryAssignTree() {
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
                // var setting = {
                //     view: {
                //         selectedMulti: false,
                //         dblClickExpand:
                //             false,
                //         showLine:
                //             true,
                //     },
                //     check: {
                //         enable: true,
                //         chkStyle: "radio"
                //     },
                //     data: {
                //         simpleData: {
                //             enable: true,
                //             idKey:
                //                 "puid",
                //             pIdKey:
                //                 "parentUid",
                //             rootPId:
                //                 "#"
                //         },
                //         key: {
                //             checked:
                //                 "CHECKED",
                //             name:
                //                 "lineId",
                //         }
                //     }
                //     ,
                //     //回调函数
                //     callback: {
                //         /*beforeClick:function(treeId, treeNode) {
                //             return !treeNode.isParent;//当是父节点 返回false 不让选取
                //         },*/
                //         onCheck: function (e, treeId, treeNode) {
                //             var treeObj = $.fn.zTree.getZTreeObj("Ztree3");
                //             var nodes =
                //                 // treeObj.getSelectedNodes();
                //                 treeObj.getCheckedNodes(true);
                //             if (nodes.length > 1) {
                //                 window.Ewin.alert({message: "只能指定一个工艺合件的位置"});
                //                 for (var k = 0; k < nodes.length; k++) {
                //                     treeObj.checkNode(nodes[k]);
                //                 }
                //                 return;
                //             }
                //             else if (nodes.length > 0 && nodes.length < 2) {
                //                 coach1.splice(0, coach1.length);
                //                 coach1.push(nodes[0].puid);
                //             } else {
                //                 coach1.splice(0, coach1.length);
                //             }
                //         },
                //         onClick: function (event, treeId, treeNode) {
                //             // if (treeNode.level != 2) {
                //             //     alert("该节点下有子节点,请选取子节点")
                //             //     return;
                //             // }
                //             localSelectedNode1 = treeNode;
                //             $.ajax({
                //                 url: "pbom/detail?puid=" + localSelectedNode1.puid + "&projectId=" + getProjectUid(),
                //                 type: "GET",
                //                 undefinedText: "",//当数据为 undefined 时显示的字符
                //                 success: function (data) {
                //                     var result = JSON.stringify(data);
                //                     var ddd = JSON.parse(result);
                //                     var date = ddd.data;
                //                     var va = date[0];
                //                     var rel = "<table>"
                //                     rel += "<tr>" +
                //                         "<th>序号</th>" +
                //                         "<td></td>" +
                //                         "</tr><tr>" +
                //                         "<th>层级</th>" +
                //                         "<td>" + va.level + "</td>" +
                //                         "</tr><tr>" +
                //                         "<th>专业</th>" +
                //                         "<td>" + va.pBomOfWhichDept + "</td>" +
                //                         "</tr><tr>" +
                //                         "<th>级别</th>" +
                //                         "<td>" + va.rank + "</td>" +
                //                         "</tr><tr>" +
                //                         "<th>分组号</th>" +
                //                         "<td>" + va.groupNum + "</td>" +
                //                         "</tr><tr>" +
                //                         "<th>零件号</th>" +
                //                         "<td>" + va.lineId + "</td>" +
                //                         "</tr><tr>" +
                //                         "<th>名称</th>" +
                //                         "<td>" + va.pBomLinePartName + "</td>" +
                //                         "</tr><tr>" +
                //                         "<th>英文名称</th>" +
                //                         "<td>" + va.pBomLinePartEnName + "</td>" +
                //                         "</tr><tr>" +
                //                         "<th>零件分类</th>" +
                //                         "<td>" + va.pBomLinePartClass + "</td>" +
                //                         "</tr><tr>" +
                //                         "<th>零部件来源</th>" +
                //                         "<td>" + va.pBomLinePartResource + "</td>" +
                //                         "</tr><tr>" +
                //                         "<th>自制/采购</th>" +
                //                         "<td>" + va.resource + "</td>" +
                //                         "</tr><tr>" +
                //                         "<th>焊接/装配</th>" +
                //                         "<td>" + va.type + "</td>" +
                //                         "</tr><tr>" +
                //                         "<th>采购单元</th>" +
                //                         "<td>" + va.buyUnit + "</td>" +
                //                         "</tr><tr>" +
                //                         "<th>车间1</th>" +
                //                         "<td>" + va.workShop1 + "</td>" +
                //                         "</tr><tr>" +
                //                         "<th>车间2</th>" +
                //                         "<td>" + va.workShop2 + "</td>" +
                //                         "</tr><tr>" +
                //                         "<th>生产线</th>" +
                //                         "<td>" + va.productLine + "</td>" +
                //                         "</tr><tr>" +
                //                         "<th>模具类别</th>" +
                //                         "<td>" + va.mouldType + "</td>" +
                //                         "</tr><tr>" +
                //                         "<th>外委件</th>" +
                //                         "<td>" + va.outerPart + "</td>" +
                //                         "</tr><tr>" +
                //                         "<th>颜色件</th>" +
                //                         "<td>" + va.colorPart + "</td>" +
                //                         "</tr><tr>" +
                //                         "<th>工位</th>" +
                //                         "<td>" + va.station + "</td>" +
                //                         "</tr>"
                //                     rel += "</table>"
                //                     $("#detailTable").html(rel);
                //                 },
                //                 error: function (err) {
                //                     window.Ewin.alert({message: err.status});
                //                 }
                //             })
                //             localSelectedNode1 = treeNode;
                //         },
                //     }
                // };
                var zNodes = data.externalObject;
                $.fn.zTree.init($("#Ztree3"), setting3, zNodes);
                $("#info_div2 span").text("第六步：选择挂载位置节点");
                $("#info_div2").css("top", "20%");
            },
        });
    }
}

/***
 *检查是否挂载自身
 * @param treeNode
 * @returns {boolean}
 */
function checkAssignToItself(treeNode, checked) {
    if (checked) {
        if (treeNode.isParent) {
            if (parentsParts != null) {
                for (let i in parentsParts) {
                    if (parentsParts.hasOwnProperty(i)) {
                        if (parentsParts[i] == treeNode.lineId) {
                            window.Ewin.alert({message: "您不能将合成源" + treeNode.lineId + "挂载到自己身上"});
                            return false;
                        }
                    }
                }
                // loopMyDescendantsIsInCoach(parentsParts, treeNode);
            }
        }
        else {
            if (childrenParts != null) {
                for (let i in childrenParts) {
                    if (childrenParts.hasOwnProperty(i)) {
                        if (childrenParts[i] == treeNode.lineId) {
                            window.Ewin.alert({message: "您不能将合成源" + treeNode.lineId + "挂载到自己身上"});
                            return false;
                        }
                    }
                }

            }
        }
        for (let i in parentNode) {
            if (!loopMyAncestorsIsInCoach(parentNode[i], treeNode)) {
                window.Ewin.alert({message: "您不能将包含父节点的合成结果挂载在它的子节点" + treeNode.lineId + "上"});
                return false;
            }
        }

        targetNode.push(treeNode);
        targetPointPuids.push(treeNode.puid);
        targetPointParts.push(treeNode.lineId);
    }
    else {
        let index = -1;
        if (targetNode != null) {
            for (let i in targetNode) {
                if (targetNode.hasOwnProperty(i)) {
                    if (targetNode[i].lineId == treeNode.lineId && targetNode[i].puid == treeNode.puid) {
                        index = i;
                        break;
                    }
                }
            }
        }
        if (index != -1) {
            targetNode.splice(index, 1);
            targetPointPuids.splice(index, 1);
            targetPointParts.splice(index, 1);
        }
    }
    return true;
}

/**
 * 选择了什么内容将显示在下方的table中
 */
function tellMeWhatISelected() {
    let selectedTargetTable = $("#selectedTargetTable");
    selectedTargetTable.html("");
    let _tr = document.createElement("tr");
    let _th = document.createElement("th");
    _th.innerHTML = "已选择节点是:";
    _tr.appendChild(_th);
    selectedTargetTable.append(_tr);

    let indexx = 1;

    /*** 先获获取长度*/
    let targetLength = targetNode.length;
    //动态显示选择的节点
    let tr = document.createElement("tr");
    /**存放在3列中**/
    if (targetLength >= 3) {
        for (let i in targetNode) {
            if (targetNode.hasOwnProperty(i)) {
                let td = document.createElement("td");
                td.innerHTML = targetNode[i].lineId;
                tr.appendChild(td);

                // parentsPuids.push(targetNode[i].puid);
                //
                // parentsParts.push(targetNode[i].tId);

                if (indexx % 3 == 0) {
                    selectedTargetTable.append(tr);
                    tr = document.createElement("tr");
                }
                else if (targetLength == indexx) {
                    selectedTargetTable.append(tr);
                }
                targetSize++;
                indexx++;
            }
        }
    }
    else if (targetLength < 3) {
        for (let i in targetNode) {
            if (targetNode.hasOwnProperty(i)) {
                let td = document.createElement("td");
                td.innerHTML = targetNode[i].lineId;
                tr.appendChild(td);

                // parentsPuids.push(targetNode[i].puid);
                //
                // parentsPuids.push(targetNode[i].tId);

                targetSize++;
                indexx++;
            }
        }
        selectedTargetTable.append(tr);
    }
}

/**
 * 所有的子代是否
 * @param parentsParts
 * @param treeNode
 */
function loopMyDescendantsIsInCoach(parentsParts, treeNode) {

}

/**
 * 子节点也不能挂载合成结果，因为子不能挂载父层
 * @param parentsParts
 * @param treeNode
 */
function loopMyAncestorsIsInCoach(node, treeNode) {
    if (node != null) {
        if (node.isParent) {
            let children = node.children;
            for (let i in children) {
                if (children[i].lineId == treeNode.lineId) {
                    return false;
                }
                if (children[i].isParent) {
                    let isEqu = loopMyAncestorsIsInCoach(children[i], treeNode);
                    if (!isEqu) {
                        return isEqu;
                    }
                }
            }
        }
        else {
            if (node.lineId == treeNode.lineId) {
                return false;
            }
        }
    }
    return true;
}

/**
 * 设置所有子都不可选
 * @param treeNode 目标节点子一层
 * @param treeObj
 */
function setUnCheckable(treeNode, treeObj) {
    if (treeNode != null) {
        if (treeNode.isParent) {
            let children = treeNode.children;
            for (let i in children) {
                children[i].chkDisabled = true;
                treeObj.updateNode(children[i]);
                setUnCheckable(children[i], treeObj);
            }
        }
        treeObj.setting.view.fontCss["color"] = "#ff5458";
        treeNode.chkDisabled = true;
        treeObj.updateNode(treeNode);
    }
}