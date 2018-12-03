/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

/*项目UID*/
var projectUid = null;
/**目标table*/
var $table = null;
/**动态表头**/
var dynamicTitle = [];
/**工具条设置*/
var toolbar = [
    {
        text: '一键生成',
        iconCls: 'glyphicon glyphicon-plus',
        handler: saveCompose
    },
    {
        text: '添加衍生物料',
        iconCls: 'glyphicon glyphicon-plus',
        handler: addVehicleDialog
    },
    {
        text: '修改基本信息',
        iconCls: 'glyphicon glyphicon-pencil',
        handler: modifyBasicDataDialog
    },

    // {
    //     text: '修改超级物料',
    //     iconCls: 'glyphicon glyphicon-pencil',
    //     handler: function () {
    //         var rows = $table.bootstrapTable('getSelections');
    //         //只能选一条
    //         if (rows.length != 1) {
    //             window.Ewin.alert({message: '请选择一条需要修改的数据!'});
    //             return false;
    //         }
    //         window.Ewin.dialog({
    //             title: "修改超级物料",
    //             //直接修改了超级物料表的数据，要根据配置器的puid找，否则就不能根据所见即所改
    //             url: "materiel/modifyPage?puid=" + rows[0].cfg0MainPuid + "&puidOfModelFeature=" + rows[0].puidOfModelFeature + "&page=superMateriel",
    //             gridId: "gridId",
    //             width: 350,
    //             height: 450
    //         });
    //     }
    // },
    {
        text: '删除衍生物料',
        iconCls: 'glyphicon glyphicon-pencil',
        handler: deleteVehicleFake
    },
    {
        text: '发起流程',
        iconCls: 'glyphicon glyphicon-log-out',
        handler: launchChangeForm
    },
    {
        text: '撤销',
        iconCls: 'glyphicon glyphicon-share-alt',
        handler: goBackData
    }

    // ,
    // {
    //     text: '同步整车物料属性到ERP',
    //     iconCls: 'glyphicon glyphicon-pencil',
    //     handler: function () {
    //         var rows = $table.bootstrapTable('getSelections');
    //         //只能选一条
    //         if (rows.length != 1) {
    //             window.Ewin.alert({message: '请选择一条需要修改的数据!'});
    //             return false;
    //         }
    //         window.Ewin.dialog({
    //             title: "同步车型",
    //             //直接修改了超级物料表的数据，要根据配置器的puid找，否则就不能根据所见即所改
    //             url: "cfgMateriel/synMateriel?puid=" + rows[0].puid,
    //             width: 500,
    //             height: 400
    //         });
    //     }
    // }, {
    //     text: '删除同步车型',
    //     iconCls: 'glyphicon glyphicon-pencil',
    //     handler: function () {
    //         var rows = $table.bootstrapTable('getSelections');
    //         //只能选一条
    //         if (rows.length != 1) {
    //             window.Ewin.alert({message: '请选择一条需要修改的数据!'});
    //             return false;
    //         }
    //         window.Ewin.dialog({
    //             title: "同步车型",
    //             //直接修改了超级物料表的数据，要根据配置器的puid找，否则就不能根据所见即所改
    //             url: "cfgMateriel/synMaterielDelete?puid=" + rows[0].puid,
    //             width: 500,
    //             height: 400
    //         });
    //     }
    // },
    // {
    //     text: '添加可配置物料分配特性到ERP',
    //     iconCls: 'glyphicon glyphicon-pencil',
    //     handler: function () {
    //         var rows = $table.bootstrapTable('getSelections');
    //         //只能选一条
    //         // if (rows.length != 1) {
    //         //     window.Ewin.alert({message: '请选择一条需要添加的数据!'});
    //         //     return false;
    //         // }
    //         var puids = new Array();
    //         var cfg0MainPuids = new Array();
    //         var modeBasiceDetails = new Array();
    //         for (var i = 0; i < rows.length; i++) {
    //             puids[i] = rows[i].puid;
    //             cfg0MainPuids[i] = rows[i].cfg0MainPuid;
    //             modeBasiceDetails[i] = rows[i].modeBasiceDetail;
    //         }
    //         var projectPuid = $("#project", window.top.document).val();
    //         window.Ewin.dialog({
    //             title: "添加",
    //             //直接修改了超级物料表的数据，要根据配置器的puid找，否则就不能根据所见即所改
    //             url: "cfgMateriel/addConfigurableMaterial?puids=" + puids + "&cfg0MainPuids=" + cfg0MainPuids + "&modeBasiceDetails=" + modeBasiceDetails + "&projectPuid=" + projectPuid,
    //             width: 500,
    //             height: 400
    //         });
    //     }
    // },
    // {
    //     text: '更新可配置物料分配特性到ERP',
    //     iconCls: 'glyphicon glyphicon-pencil',
    //     handler: function () {
    //         var rows = $table.bootstrapTable('getSelections');
    //         //只能选一条
    //         // if (rows.length != 1) {
    //         //     window.Ewin.alert({message: '请选择一条需要添加的数据!'});
    //         //     return false;
    //         // }
    //         var puids = new Array();
    //         var cfg0MainPuids = new Array();
    //         var modeBasiceDetails = new Array();
    //         for (var i = 0; i < rows.length; i++) {
    //             puids[i] = rows[i].puid;
    //             cfg0MainPuids[i] = rows[i].cfg0MainPuid;
    //             modeBasiceDetails[i] = rows[i].modeBasiceDetail;
    //         }
    //         var projectPuid = $("#project", window.top.document).val();
    //         window.Ewin.dialog({
    //             title: "添加",
    //             //直接修改了超级物料表的数据，要根据配置器的puid找，否则就不能根据所见即所改
    //             url: "cfgMateriel/deleteConfigurableMaterial?puids=" + puids + "&cfg0MainPuids=" + cfg0MainPuids + "&modeBasiceDetails=" + modeBasiceDetails + "&projectPuid=" + projectPuid,
    //             width: 500,
    //             height: 400
    //         });
    //     }
    // },
    // {
    //     text: '添加衍生物料到SAP',
    //     iconCls: 'glyphicon glyphicon-pencil',
    //     handler: function () {
    //         var rows = $table.bootstrapTable('getSelections');
    //         if (rows.length == 0) {
    //             window.Ewin.alert({message: '请选择一条需要添加的数据!'});
    //             return false;
    //         }
    //         window.Ewin.confirm({title: '提示', message: '是否要添加您所选择的记录？', width: 500}).on(function (e) {
    //             if (e) {
    //                 var puidOfModelFeatures = new Array();
    //                 for (var i = 0; i < rows.length; i++) {
    //                     puidOfModelFeatures[i] = rows[i].puidOfModelFeature;
    //                 }
    //                 $.ajax({
    //                     type: "POST",
    //                     //ajax需要添加打包名
    //                     url: "materiel/addToSAP?puidOfModelFeatures=" + puidOfModelFeatures,
    //                     // data: JSON.stringify(puidOfModelFeatures),
    //                     contentType: "application/json",
    //                     success: function (result) {
    //                         window.Ewin.alert({message: result, width: 800});
    //                         // if (result.status) {
    //                         //     // layer.msg(result.msg, {icon: 1, time: 2000})
    //                         //     // window.Ewin.alert({message: result, width: 800});
    //                         //     //刷新，会重新申请数据库数据
    //                         // }
    //                         // else {
    //                         //     window.Ewin.alert({message: "操作添加失败:" + result.msg});
    //                         // }
    //                         $table.bootstrapTable("refresh");
    //                     },
    //                     error: function (info) {
    //                         window.Ewin.alert({message: "操作添加:" + info.status});
    //                     }
    //                 })
    //             }
    //         });
    //     }
    // },
    // {
    //     text: '删除SAP中衍生物料',
    //     iconCls: 'glyphicon glyphicon-pencil',
    //     handler: function () {
    //         var rows = $table.bootstrapTable('getSelections');
    //         if (rows.length == 0) {
    //             window.Ewin.alert({message: '请选择一条需要删除的数据!'});
    //             return false;
    //         }
    //         window.Ewin.confirm({title: '提示', message: '是否要删除您所选择的记录？', width: 500}).on(function (e) {
    //             if (e) {
    //                 var puidOfModelFeatures = new Array();
    //                 for (var i = 0; i < rows.length; i++) {
    //                     puidOfModelFeatures[i] = rows[i].puidOfModelFeature;
    //                 }
    //                 $.ajax({
    //                     type: "POST",
    //                     //ajax需要添加打包名
    //                     url: "materiel/deleteToSAP?puidOfModelFeatures=" + puidOfModelFeatures,
    //                     // data: JSON.stringify(puidOfModelFeatures),
    //                     contentType: "application/json",
    //                     success: function (result) {
    //                         // if (result.status) {
    //                         //     layer.msg(result.msg, {icon: 1, time: 2000})
    //                         //     // window.Ewin.alert({message: result, width: 800});
    //                         //     //刷新，会重新申请数据库数据
    //                         // }
    //                         // else {
    //                         //     window.Ewin.alert({message: "操作删除失败:" + result.msg});
    //                         // }
    //                         window.Ewin.alert({message: result, width: 800});
    //                         $table.bootstrapTable("refresh");
    //                     },
    //                     error: function (info) {
    //                         window.Ewin.alert({message: "操作删除:" + info.status});
    //                     }
    //                 })
    //             }
    //         });
    //     }
    // }
];
$(document).ready(
//优先加载数据
    loadData(getProjectUid()),
    //查询按钮
    $("#query").click(function () {
        loadData(getProjectUid());
    }),
    //手动刷新按钮
    $("#refresh").click(function () {
        loadData(getProjectUid());
    })
);

/**加载数据*/
function loadData(_projectPuid) {
    //必须输入一个配置的puid
    if (!checkIsSelectProject(_projectPuid)) {
        return;
    }
    projectUid = _projectPuid;
    $table = $("#materielFeature");
    $table.bootstrapTable('destroy');
    var column = [];
    $("#refresh").removeAttr("disabled");
    $.ajax({
        url: "materielV2/loadColumnByProjectPuid2?projectPuid=" + projectUid,
        type: "GET",
        success: gotIt
    });
}

/**
 * 添加衍生物料
 */
function addVehicleDialog() {
    var url = "materielV2/composePage";
    $.ajax({
        url: "privilege/write?url=" + url,
        type: "GET",
        success: function (result) {
            if (!result.success) {
                window.Ewin.alert({message: result.errMsg});
                return false;
            }
            else {
                window.Ewin.dialog({
                    // 这个puid就是车型模型的puid，直接修改了车型模型的基本信息（在bom系统维护的字段）
                    title: "添加衍生物料",
                    url: "materielV2/composePage?projectUid=" + projectUid,
                    gridId: "gridId",
                    width: 400,
                    height: 450
                });
            }
        }
    })
}

/**
 * 修改基本信息
 */
function modifyBasicDataDialog() {
    var rows = $table.bootstrapTable('getSelections');
    //只能选一条
    if (rows.length != 1) {
        window.Ewin.alert({message: '请选择一条需要修改的数据!'});
        return;
    }
    var status = rows[0].status;
    if (status != 0 && status != 1 && status != null) {
        window.Ewin.alert({message: rows[0].modeBasicDetail + "状态不能修改"});
        return false
    }
    var url = "materielV2/modifyPage";
    $.ajax({
        url: "privilege/write?url=" + url,
        type: "GET",
        success: function (result) {
            if (!result.success) {
                window.Ewin.alert({message: result.errMsg});
                return false;
            }
            else {
                window.Ewin.dialog({
                    // 这个puid就是车型模型的puid，直接修改了车型模型的基本信息（在bom系统维护的字段）
                    title: "修改基本信息",
                    url: "materielV2/modifyPage?projectUid=" + projectUid + "&puid=" + rows[0].puid + "&puidOfModelFeature=" + rows[0].puidOfModelFeature + "&page=model",
                    gridId: "gridId",
                    width: 350,
                    height: 450
                });
            }
        }
    })
}

/**
 * 撤销
 */
function goBackData() {
    var rows = $table.bootstrapTable('getSelections');
    //只能选一条
    if (rows.length <= 0) {
        window.Ewin.alert({message: '请至少选择一条需要撤销的数据!'});
        return;
    }

    for (let i in rows) {
        if (10 == rows[i].status || "10" == rows[i].status) {
            window.Ewin.alert({message: rows[i].modeBasicDetail + "已在变更流程中，不可撤销"});
            return false;
        } else if (1 == rows[i].status || "1" == rows[i].status) {
            window.Ewin.alert({message: rows[i].modeBasicDetail + "已生效，不可撤销"});
            return false;
        }
    }
    var url = "materielV2/goBackData";
    $.ajax({
        url: "privilege/write?url=" + url,
        type: "GET",
        success: function (result) {
            if (!result.success) {
                window.Ewin.alert({message: result.errMsg});
                return false;
            }
            else {
                window.Ewin.confirm({title: '提示', message: '是否要撤销您所选择的记录？', width: 500}).on(function (e) {
                    if (e) {
                        $.ajax({
                            type: "POST",
                            //ajax需要添加打包名
                            url: "./materielV2/goBackData",
                            data: JSON.stringify(rows),
                            contentType: "application/json",
                            success: function (result) {
                                if (result.status) {
                                    layer.msg(result.msg, {icon: 1, time: 2000});
                                    doQuery();
                                    //$(window.parent.document).contents().find(".tab-pane.fade.active.in iframe")[0].contentWindow.doQuery();
                                    // window.Ewin.alert({message: });
                                    //刷新，会重新申请数据库数据
                                }
                                else {
                                    window.Ewin.alert({message: "操作撤销失败:" + result.msg});
                                }
                                $table.bootstrapTable("refresh");
                            },
                            error: function (info) {
                                window.Ewin.alert({message: "操作撤销:" + info.status});
                            }
                        })
                    }
                })
            }
        }
    })
}

/**
 * 删除衍生物料
 */
function deleteVehicleFake() {
    var rows = $table.bootstrapTable('getSelections');
    //只能选一条
    if (rows.length <= 0) {
        window.Ewin.alert({message: '请至少选择一条需要删除的数据!'});
        return;
    }
    for (let i in rows) {
        if (2 == rows[i].status || "2" == rows[i].status) {
            window.Ewin.alert({message: rows[i].modeBasicDetail + "已删除，不可重复删除"});
            return false;
        } else if (10 == rows[i].status || "10" == rows[i].status) {
            window.Ewin.alert({message: rows[i].modeBasicDetail + "已在变更流程中，不可删除"});
            return false;
        }
    }
    var url = "materielV2/deleteVehicleFake";
    $.ajax({
        url: "privilege/write?url=" + url,
        type: "GET",
        success: function (result) {
            if (!result.success) {
                window.Ewin.alert({message: result.errMsg});
                return false;
            }
            else {
                window.Ewin.confirm({title: '提示', message: '是否要删除您所选择的记录？', width: 500}).on(function (e) {
                    if (e) {
                        $.ajax({
                            type: "POST",
                            //ajax需要添加打包名
                            url: "./materielV2/deleteVehicleFake",
                            data: JSON.stringify(rows),
                            contentType: "application/json",
                            success: function (result) {
                                if (result.status) {
                                    layer.msg(result.msg, {icon: 1, time: 2000});
                                    doQuery();
                                    //$(window.parent.document).contents().find(".tab-pane.fade.active.in iframe")[0].contentWindow.doQuery();
                                    // window.Ewin.alert({message: });
                                    //刷新，会重新申请数据库数据
                                }
                                else {
                                    window.Ewin.alert({message: "操作删除失败:" + result.msg});
                                }
                                $table.bootstrapTable("refresh");
                            },
                            error: function (info) {
                                window.Ewin.alert({message: "操作删除:" + info.status});
                            }
                        })
                    }
                })
            }
        }
    })
}

// /**
//  * 删除衍生物料
//  */
// function deleteVehicle() {
//     var rows = $table.bootstrapTable('getSelections');
//     //只能选一条
//     if (rows.length <= 0) {
//         window.Ewin.alert({message: '请至少选择一条需要删除的数据!'});
//         return;
//     }
//     window.Ewin.confirm({title: '提示', message: '是否要删除您所选择的记录？', width: 500}).on(function (e) {
//         if (e) {
//             $.ajax({
//                 type: "POST",
//                 //ajax需要添加打包名
//                 url: "./materielV2/deleteCompose",
//                 data: JSON.stringify(rows),
//                 contentType: "application/json",
//                 success: function (result) {
//                     if (result.status) {
//                         layer.msg(result.msg, {icon: 1, time: 2000});
//                         doQuery();
//                         //$(window.parent.document).contents().find(".tab-pane.fade.active.in iframe")[0].contentWindow.doQuery();
//                         // window.Ewin.alert({message: });
//                         //刷新，会重新申请数据库数据
//                     }
//                     else {
//                         window.Ewin.alert({message: "操作删除失败:" + result.msg});
//                     }
//                     $table.bootstrapTable("refresh");
//                 },
//                 error: function (info) {
//                     window.Ewin.alert({message: "操作删除:" + info.status});
//                 }
//             })
//         }
//     })
// }

/******发起变更表单****/
function launchChangeForm() {
    var rows = $table.bootstrapTable('getSelections');
    if (rows.length == 0) {
        window.Ewin.alert({message: '请选择一条需要发起变更的数据!'});
        return false;
    }
    for (let i in rows) {
        if (1 == rows[i].status || "1" == rows[i].status) {
            window.Ewin.alert({message: rows[i].modeBasicDetail + "已生效，不可发起流程"});
            return false;
        } else if (10 == rows[i].status || "10" == rows[i].status) {
            window.Ewin.alert({message: rows[i].modeBasicDetail + "已在流程中，不可发起流程"});
            return false;
        }
    }

    let msg = "<div style='max-height: 350px;overflow: -moz-scrollbars-vertical'>";
    for (let i in rows) {
        if (0 != rows[i].status && "0" != rows[i].status && null != rows[i].status && "null" != rows[i].status && 2 != rows[i].status && "2" != rows[i].status) {
            window.Ewin.alert({message: rows[i].modeBasicDetail + "该衍生物料不是草稿状态，不能发起流程"});
            return false;
        }
        msg += "<p>" + rows[i].pCfg0ObjectId + "-" + rows[i].pCfg0Desc + "</p>";
    }

    // var data = {};
    // data.rows = rows;
    // var puids = [];
    // for(var i=0;i<rows.length;i++){
    //     puids.push(rows[i].basicId);
    // }
    // data.puids = puids;
    // data.titles = dynamicTitle;
    // data.projectPuid = projectUid;
    var url = "materielV2/setChangeFromPage";
    $.ajax({
        url: "privilege/write?url=" + url,
        type: "GET",
        success: function (result) {
            if (!result.success) {
                window.Ewin.alert({message: result.errMsg});
                return false;
            }
            else {
                window.Ewin.confirm({
                    title: '提示',
                    message: '是否要发起流程？',
                    width: 500
                }).on(function (e) {
                    if (e) {
                        var puids = "";
                        for (let i in rows) {
                            puids += rows[i].basicId;
                            if (i < rows.length - 1) {
                                puids += ",";
                            }
                        }
                        var titles = "";
                        for (let i in dynamicTitle) {
                            titles += dynamicTitle[i];
                            if (i < dynamicTitle.length - 1) {
                                titles += ",";
                            }
                        }

                        window.Ewin.dialog({
                            // 这个puid就是车型模型的puid，直接修改了车型模型的基本信息（在bom系统维护的字段）
                            title: "选择变更表单",
                            url: "./materielV2/setChangeFromPage?projectUid=" + getProjectUid() + "&puids=" + puids + "&titles=" + titles,
                            gridId: "gridId",
                            width: 450,
                            height: 450
                        });

                        // $.ajax({
                        //     type: "POST",
                        //     //ajax需要添加打包名
                        //     url: "./materielV2/getVWO",
                        //     data: /*data*/JSON.stringify(data),
                        //     contentType: "application/json",
                        //     success: function (result) {
                        //         if (result.status) {
                        //             layer.msg("发起VWO流程成功", {icon: 1, time: 2000})
                        //             // window.Ewin.alert({message: "删除时数据成功"});
                        //             //刷新，会重新申请数据库数据
                        //         }
                        //         else {
                        //             window.Ewin.alert({message: "发起VWO流程失败:" + result.msg});
                        //         }
                        //         $table.bootstrapTable("refresh");
                        //     },
                        //     error: function (info) {
                        //         window.Ewin.alert({message: "发起VWO流程:" + info.status});
                        //     }
                        // })
                    }
                });
            }
        }
    })
}

/**成功从后台获取数据*/
function gotIt(result) {
    if (!result.status) {
        swal({
            width: 500,
            height: 400,
            html: $('<span>')
                .addClass('some-class')
                .css("font-size", "24px")
                .css("color", "#ff4f6a")
                .text('没有找到数据，请尝试创建特性值'),
            animation: false,
            customClass: 'animated tada'
        });
        return;
    }
    var data = result.data;
    var column = [];
    column.push({field: 'ck', checkbox: true, Width: 50});
    //该puid是车型模型的
    column.push({field: 'modeBasicDetail', title: '基本信息代码'});
    column.push({field: 'factory', title: '工厂'});
    column.push({field: 'modeBasicDetailDesc', title: '基本信息'});
    column.push({field: 'superMateriel', title: '超级物料'});
    for (var i = 0; i < data.length; i++) {
        var josn = {
            field: "s" + i,
            title:
                data[i],
            align:
                'center',
            valign:
                'middle'
        };
        column.push(josn);
        dynamicTitle.push(data[i]);
    }
    column.push(
        {
            field: 'cfgStatus',
            title: '状态',
            align: 'center',
            valign: 'middle',
            formatter: function (value, row, index) {
                var status = row.status;
                if (status == 1 || "1" == status) {
                    return "<span style='color: #00B83F'>已生效</span>";
                }
                else if (status == 10 || "10" == status) {
                    return "<span style='color: #e69800'>变更审核中(" + row.changeOrderNo + ")</span>";
                }
                else if (0 == status || "0" == status || "null" == status || null == status) {
                    return "<span style='color: #a97f89'>草稿状态</span>";
                }
                else if (2 == status || "2" == status) {
                    return "<span style='color: #0c8fe2'>删除状态</span>";
                }
                else {
                    return "<span style='color: #a90009'>未知状态</span>";
                }
            }
        });
    $table.bootstrapTable({
        url: "materielV2/loadComposes?projectPuid=" + projectUid,
        method: 'get',
        height: $(window.parent.document).find("#wrapper").height() - 150,//$(window.parent.document).find("#wrapper").height() - document.body.offsetHeight - 45,
        width: $(window).width(),
        showToggle: true,                   //是否显示详细视图和列表视图的切换按钮
        showRefresh: true,                  //是否显示刷新按钮
        pageSize: 10,
        pagination: false,                   //是否显示分页（*）
        clickToSelect: true,                // 单击某一行的时候选中某一条记录
        formId: "hide",
        /**列信息，需要预先定义好*/
        columns: column,
        // sortable: true,                     //是否启用排序
        // sortOrder: "asc",                   //排序方式
        toolbars: toolbar,
        //>>>>>>>>>>>>>>导出excel表格设置
        showExport: phoneOrPc(),              //是否显示导出按钮(此方法是自己写的目的是判断终端是电脑还是手机,电脑则返回true,手机返回falsee,手机不显示按钮)
        exportDataType: "selected",              //basic', 'all', 'selected'.
        exportTypes: ['xlsx'],	    //导出类型
        //exportButton: $('#btn_export'),     //为按钮btn_export  绑定导出事件  自定义导出按钮(可以不用)
        exportOptions: {
            //ignoreColumn: [0,0],            //忽略某一列的索引
            fileName: '配置物料特性数据导出',              //文件名称设置
            worksheetName: 'Sheet1',          //表格工作区名称
            tableName: '配置物料特性表',
            excelstyles: ['background-color', 'color', 'font-size', 'font-weight'],
            //onMsoNumberFormat: DoOnMsoNumberFormat
        }
        //导出excel表格设置<<<<<<<<<<<<<<<<
    });

    //设置跳转的tableID，放在table初始化语句之后
    setTargetTableId("materielFeature");
}

function saveCompose() {
    var url = "materielV2/saveCompose";
    $.ajax({
        url: "privilege/write?url=" + url,
        type: "GET",
        success: function (result) {
            if (!result.success) {
                window.Ewin.alert({message: result.errMsg});
                return false;
            }
            else {
                $.ajax({
                    type: "GET",
                    url: "materielV2/saveCompose?projectPuid=" + projectUid,
                    success: function (result) {
                        if (result.status != false) {
                            // window.Ewin.alert({message: '生成成功!'});
                            layer.msg("生成成功", {icon: 1, time: 2000});
                            doQuery();
                        } else {
                            window.Ewin.alert({message: result.msg});
                        }
                    },
                    error: function (result) {
                        window.Ewin.alert({message: result.msg});
                    }
                })
            }
        }
    })
}

function doQuery() {
    $('#materielFeature').bootstrapTable('refresh');    //刷新表格
}