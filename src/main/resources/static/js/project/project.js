var zTree;
var demoIframe;
var selectNode;
var coach = [];
var localSelectedNode;
var localProjectDetail;

function selectEvent() {
    var selectedOption = $("#fastOption").find("option:selected");
    $("#project", window.top.document).val(selectedOption.val());
    $("#currentProject").text("当前工作项目:" + selectedOption.text());
    $("#currentProjectHead", window.top.document).text(selectedOption.text());
}

// 异步加载项目数据
$(document).ready(
    ($.ajax({
            url: "./project/loadAll",
            type: "GET",
            success: function (data) {
                var auth = data.auth;
                var _data = data.data;
                var ok = true;
                if (_data) {
                    for (var i in _data) {
                        if (i == 0 && ok) {
                            $("#project", window.top.document).val(_data[i].puid);
                            $("#currentProject").text("当前工作项目:" + _data[i].pProjectName);
                            $("#currentProjectHead", window.top.document).text(_data[i].pProjectName);
                            ok = false;
                            if (!auth) {
                                break;
                            }
                        }
                        if (auth) {
                            $("#fastOption").append("<option value='" + _data[i].puid + "'>" + _data[i].pProjectName + "</option>");
                        }
                    }
                    console.log("加载项目成功");
                }
                if (auth) {
                    $("#fastOption").removeAttr("hidden");
                    $("#fastOption").addClass("form-control");
                    $("#fastOption").bind("change", selectEvent);
                    $("#labHidden").removeAttr("hidden");
                }
            },
            error: function (err) {
                alert(err.status);
            }
        })
    ))

/*鼠标移入操作*/
function addHoverDom(treeId, treeNode) {
    //根据权限设置能否有某种操作
    if (true) {
        //根据层级添加按钮
        var sObj = $("#" + treeNode.tId + "_span");
        if (treeNode.editNameFlag || $("#editBtn_" + treeNode.tId).length > 0) {
            return;
        }
        var addStr = "<span class='button edit' id='editBtn_" + treeNode.tId + "' title='修改' onfocus='this.blur();'></span>";
        //处理第一层和第二层
        // //第二层可以添加项目
        if (0 == treeNode.level) {
            addStr += "<span class='button add' id='addBtn_" + treeNode.tId + "' title='添加平台'></span>";
            addStr += "<span class='button remove' id='removeBtn_" + treeNode.tId + "' title='删除品牌'></span>";
            addStr += "<span class='button add' id='addParentBtn_" + treeNode.tId + "' title='添加品牌'></span>";
        }
        else if (1 == treeNode.level) {
            addStr += "<span class='button add' id='addBtn_" + treeNode.tId + "' title='添加车型'></span>";
            addStr += "<span class='button remove' id='removeBtn_" + treeNode.tId + "' title='删除平台'></span>";
        }
        // //第一层可以添加品牌和平台

        else if (2 == treeNode.level) {
            addStr += "<span class='button add' id='addBtn_" + treeNode.tId + "' title='添加项目'></span>";
            addStr += "<span class='button remove' id='removeBtn_" + treeNode.tId + "' title='删除车型'></span>";
        }
        else if (3 == treeNode.level) {
            addStr += "<span class='button remove' id='removeBtn_" + treeNode.tId + "' title='删除项目'></span>";
        }

        sObj.after(addStr);
        //添加按钮
        var btn = $("#addBtn_" + treeNode.tId);
        if (btn) {
            btn.bind("click", function () {
                zTree = $.fn.zTree.getZTreeObj("menu_tree");
                selectNode = treeNode;
                if (0 == treeNode.level) {
                    //添加平台
                    window.Ewin.dialog({
                        title: "添加",
                        url: "project/addPage?id=" + treeNode.puid + "&page=platform",
                        width: 400,
                        height: 650
                    })
                }
                else if (1 == treeNode.level) {
                    //添加项目
                    window.Ewin.dialog({
                        title: "添加",
                        url: "project/addPage?id=" + treeNode.puid + "&page=vehicle",
                        width: 400,
                        height: 650
                    })
                }
                else if (2 == treeNode.level) {
                    window.Ewin.dialog({
                        title: "添加",
                        url: "project/addPage?id=" + treeNode.puid + "&page=project",
                        width: 400,
                        height: 650
                    })
                }
                return false;
            });
        }
        //删除按钮
        var remove_btn = $("#removeBtn_" + treeNode.tId);
        if (remove_btn) {
            remove_btn.bind("click", function () {
                var type = "";
                var typeName = "";
                var name = treeNode.name;
                var downer = "";
                zTree = $.fn.zTree.getZTreeObj("menu_tree");
                switch (treeNode.level) {
                    case 0:
                        type = "brand";
                        downer = "平台";
                        typeName = "品牌";
                        break;
                    case 1:
                        type = "platform";
                        downer = "项目";
                        typeName = "平台";
                        break;
                    case 2:
                        type = "vehicle";
                        downer = "项目";
                        typeName = "车型";
                        break;
                    case 3:
                        type = "project";
                        downer = "";
                        typeName = "项目";
                        break;
                }
                if (treeNode.isParent) {
                    window.Ewin.alert({message: "请先删除下级" + downer});
                } else {
                    window.Ewin.confirm({
                        title: '提示',
                        message: '是否要删除您当前选中的' + typeName + ":" + name + "?",
                        width: 500
                    }).on(function (e) {
                        if (e) {
                            $.post("./project/delete", {puid: treeNode.puid, type: type}, function (e) {
                                if (e) {
                                    zTree.removeNode(treeNode);
                                    window.Ewin.alert({message: "成功删除"});
                                } else {
                                    window.Ewin.alert({message: "删除失败" + typeName + ":" + name});
                                }
                            })
                        }
                    });
                }
                return false;
            });
        }
        //编辑按钮
        var edit_btn = $("#editBtn_" + treeNode.tId);
        if (edit_btn) {
            edit_btn.bind("click", function () {
                zTree = $.fn.zTree.getZTreeObj("menu_tree");
                selectNode = treeNode;
                if (0 == treeNode.level) {
                    window.Ewin.dialog({
                        title: "修改品牌信息",
                        url: "project/modifyPage?id=" + treeNode.puid + "&page=brand",
                        width: 400,
                        height: 650
                    });
                }
                else if (1 == treeNode.level) {
                    window.Ewin.dialog({
                        title: "修改平台信息",
                        url: "project/modifyPage?id=" + treeNode.puid + "&page=platform",
                        width: 400,
                        height: 650
                    });
                }
                else if (2 == treeNode.level) {
                    window.Ewin.dialog({
                        title: "修改项目信息",
                        url: "project/modifyPage?id=" + treeNode.puid + "&page=vehicle",
                        width: 400,
                        height: 650
                    });
                }
                else if (3 == treeNode.level) {
                    window.Ewin.dialog({
                        title: "修改项目信息",
                        url: "project/modifyPage?id=" + treeNode.puid + "&page=project",
                        width: 400,
                        height: 650
                    });
                }
                var puid = selectNode.puid;
                coach[puid] = null;
                return false;
            });
        }
        //给父级添加
        var add_parent_btn = $("#addParentBtn_" + treeNode.tId);
        //添加品牌
        if (add_parent_btn) {
            add_parent_btn.bind("click", function () {
                zTree = $.fn.zTree.getZTreeObj("menu_tree");
                selectNode = null;
                window.Ewin.dialog({title: "添加", url: "project/addPage?id=&page=brand", width: 400, height: 650})
                return false;
            })
        }
    }
};

function removeHoverDom(treeId, treeNode) {
    //根据权限设置能否有某种操作
    if (true) {
        $("#addBtn_" + treeNode.tId).unbind().remove();
        $("#removeBtn_" + treeNode.tId).unbind().remove();
        $("#editBtn_" + treeNode.tId).unbind().remove();
        $("#addParentBtn_" + treeNode.tId).unbind().remove();
    }
};

var setting = {
    check: {
        enable: false
    },
    view: {
        addHoverDom: addHoverDom,
        removeHoverDom: removeHoverDom,
        dblClickExpand: false,
        showLine: true,
        selectedMulti: false
    },
    data: {
        simpleData: {
            enable: true,
            idKey: "puid",
            pIdKey: "pPuid",
            rootPId: "#"
        }
    },
    callback: {
        beforeClick: function (treeId, treeNode) {
            var zTree = $.fn.zTree.getZTreeObj('menu_tree');
            if (treeNode.isParent) {
                zTree.expandNode(treeNode);
                return false;
            } else {
                return true;
            }
        },
        onClick: function (event, treeId, treeNode) {
            if (treeNode.level != 3) {
                console.log("项目节点。不再展开");
                return false;
            }
            console.log("正在展开节点");
            //品牌的父id只有#
            if (null == coach[treeNode.puid]) {
                //beforeClick排除了有子层的节点，
                //绑定本地选择的子节点
                localSelectedNode = treeNode;
                $.ajax({
                    url: "./project/getProjectDetailById?puid=" + localSelectedNode.puid,
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
        }
    }
};

$(function () {
    $.post("./project/loadProjectTree", function (info) {
        var t = $("#menu_tree");
        t = $.fn.zTree.init(t, setting, info.data);
    })
});

$(document).ready(
    $("#setCurrentProject").click(function () {
        if (null == localSelectedNode)
            return;
        var project = $("#project", window.top.document);
        var projValue = localSelectedNode.puid;
        project.val(projValue);
        $("#currentProject").text("当前工作项目:" + localProjectDetail.project.pProjectName);
        $("#currentProjectHead", window.top.document).text(localProjectDetail.project.pProjectName);
        alert("当前选择的工作项目是:" + localProjectDetail.project.pProjectName);
    })
)
;

function changeView(data) {
    //品牌
    $("#pBrandCode").text(data.brand.pBrandCode);
    $("#pBrandName").text(data.brand.pBrandName);
    //平台
    $("#pPlatformCode").text(data.platform.pPlatformCode);
    $("#pPlatformName").text(data.platform.pPlatformName);
    //车型
    $("#projVehicleCode").text(data.vehicle.pVehicleName);
    $("#projVehicleName").text(data.vehicle.pVehicleCode);
    $("#projVehicleShapeCode").text(data.vehicle.pVehicleShapeCode);
    $("#projVehicleTranName").text(data.vehicle.pVehicleTranName);
    $("#projVehicleAnnualCode").text(data.vehicle.pVehicleAnnualCode);
    $("#projVehicleAnnual").text(data.vehicle.pVehicleAnnual);
    //项目
    $("#pProjectCode").text(data.project.pProjectCode);
    $("#pProjectName").text(data.project.pProjectName);
}