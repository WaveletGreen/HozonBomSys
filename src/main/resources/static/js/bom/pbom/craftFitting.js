$(document).ready(function () {
    var localSelectedNode;
    var localDetail;
    var coach = [];
    var puids="";
    var projectId = $("#project", window.top.document).val();
    $("#queryBtn1").click(function () {
        var val = $("#queryLineId").val();
        if (val == "") {
            alert("请输入您要查询的零件号")
        }
        else {
            $.ajax({
                type: "GET",
                url: "pbom/processComposeTree?lineId=" + val + "&projectId=" + projectId,
                undefinedText: "",//当数据为 undefined 时显示的字符
                success: function (data) {
                    var setting = {
                        view: {
                            selectedMulti: false,
                            dblClickExpand:
                                false,
                            showLine:
                                true,
                        },
                        check: {
                            enable: true
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
                                        puids+=nodes[nodes.length - 1].puid+",";
                                        coach.push(nodes[nodes.length - 1].puid);
                                        break;
                                    }
                                }
                            },
                            onClick: function (event, treeId, treeNode) {
                                if (treeNode.level != 2) {
                                    alert("该节点下有子节点,请选取子节点")
                                    return;
                                }
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
                                            "<th>层级</th>" +
                                            "<td>" + va.level + "</td>" +
                                            "</tr>" +
                                            "<tr>" +
                                            "<th>专业</th>" +
                                            "<td>" + va.pBomOfWhichDept + "</td>" +
                                            "<th>级别</th>" +
                                            "<td>" + va.rank + "</td>" +
                                            "</tr>" +
                                            "<tr>" +
                                            "<th>分组号</th>" +
                                            "<td>" + va.groupNum + "</td>" +
                                            "<th>零件号</th>" +
                                            "<td>" + va.lineId + "</td>" +
                                            "</tr>" +
                                            "<tr>" +
                                            "<th>名称</th>" +
                                            "<td>" + va.pBomLinePartName + "</td>" +
                                            "<th>英文名称</th>" +
                                            "<td>" + va.pBomLinePartEnName + "</td>" +
                                            "</tr>" +
                                            "<tr>" +
                                            "<th>零件分类</th>" +
                                            "<td>" + va.pBomLinePartClass + "</td>" +
                                            "<th>零部件来源</th>" +
                                            "<td>" + va.pBomLinePartResource + "</td>" +
                                            "</tr>" +
                                            "<tr>" +
                                            "<th>自制/采购</th>" +
                                            "<td>" + va.resource + "</td>" +
                                            "<th>焊接/装配</th>" +
                                            "<td>" + va.type + "</td>" +
                                            "</tr>" +
                                            "<tr>" +
                                            "<th>采购单元</th>" +
                                            "<td>" + va.buyUnit + "</td>" +
                                            "<th>车间1</th>" +
                                            "<td>" + va.workShop1 + "</td>" +
                                            "</tr>" +
                                            "<tr>" +
                                            "<th>车间2</th>" +
                                            "<td>" + va.workShop2 + "</td>" +
                                            "<th>生产线</th>" +
                                            "<td>" + va.productLine + "</td>" +
                                            "</tr>" +
                                            "<tr>" +
                                            "<th>模具类别</th>" +
                                            "<td>" + va.mouldType + "</td>" +
                                            "<th>外委件</th>" +
                                            "<td>" + va.outerPart + "</td>" +
                                            "</tr>" +
                                            "<tr>" +
                                            "<th>颜色件</th>" +
                                            "<td>" + va.colorPart + "</td>" +
                                            "<th>工位</th>" +
                                            "<td>" + va.station + "</td>" +
                                            "</tr>"
                                        rel += "</table>"
                                        $("#detailTable").html(rel);
                                    },
                                    error: function (err) {
                                        alert(err.status);
                                    }
                                })
                                localSelectedNode = treeNode;
                            }

                        }
                    };

                    function zTreeBeforeCheck(treeId, treeNode) {
                        return !treeNode.isParent;//当是父节点 返回false 不让选取
                    }

                    var zNodes = data;

                    $(document).ready(function () {
                        $.fn.zTree.init($("#Ztree1"), setting, zNodes);
                    });
                },
            });
        }
    })
    $("#synthetic").click(function () {
        if (coach.length < 2) {
            alert("请选择需要生成工艺合件的零件")
        } else {
            window.Ewin.dialog({
                title: "生成工艺合件",
                url: "pbom/updataProcessOfFitting?puids="+puids,
                gridId: "gridId",
                width: 500,
                height: 650
            })
        }
    })
})


