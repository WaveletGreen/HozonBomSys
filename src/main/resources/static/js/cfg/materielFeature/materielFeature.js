/*项目UID*/
var projectUid = null;
/**目标table*/
var $table = null;
/**工具条设置*/
var toolbarSetting = [
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
        handler: deleteVehicle
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
function loadData(projectPuid) {
    //必须输入一个配置的puid
    if (!checkIsSelectProject(projectPuid)) {
        return;
    }
    projectUid = projectPuid;
    $table = $("#materielFeature");
    $table.bootstrapTable('destroy');
    var column = [];
    $("#refresh").removeAttr("disabled");
    $.ajax({
        url: "materiel/loadColumnByProjectPuid2?projectPuid=" + projectUid,
        type: "GET",
        success: gotIt
    });
}

/**
 * 添加衍生物料
 */
function addVehicleDialog() {
    window.Ewin.dialog({
        // 这个puid就是车型模型的puid，直接修改了车型模型的基本信息（在bom系统维护的字段）
        title: "修改基本信息",
        url: "materielV2/composePage?projectUid=" + projectUid,
        gridId: "gridId",
        width: 400,
        height: 450
    });
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
    window.Ewin.dialog({
        // 这个puid就是车型模型的puid，直接修改了车型模型的基本信息（在bom系统维护的字段）
        title: "修改基本信息",
        url: "materiel/modifyPage?projectUid=" + projectUid + "&puid=" + rows[0].puid + "&puidOfModelFeature=" + rows[0].puidOfModelFeature + "&page=model",
        gridId: "gridId",
        width: 350,
        height: 450
    });
}

/**
 * 删除衍生物料
 */
function deleteVehicle() {
    var rows = $table.bootstrapTable('getSelections');
    //只能选一条
    if (rows.length <= 0) {
        window.Ewin.alert({message: '请至少选择一条需要删除的数据!'});
        return;
    }
    window.Ewin.confirm({title: '提示', message: '是否要删除您所选择的记录？', width: 500}).on(function (e) {
        if (e) {
            $.ajax({
                type: "POST",
                //ajax需要添加打包名
                url: "./materielV2/deleteCompose",
                data: JSON.stringify(rows),
                contentType: "application/json",
                success: function (result) {
                    if (result.status) {
                        layer.msg(result.msg, {icon: 1, time: 2000})
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
    }
    $table.bootstrapTable({
        url: "materielV2/loadComposes?projectPuid=" + projectUid,
        method: 'get',
        height: $(window.parent.document).find("#wrapper").height() - 150,//$(window.parent.document).find("#wrapper").height() - document.body.offsetHeight - 45,
        width: $(window).width(),
        showToggle: true,                   //是否显示详细视图和列表视图的切换按钮
        showRefresh: true,                  //是否显示刷新按钮
        pageSize: 10,
        pagination: true,                   //是否显示分页（*）
        clickToSelect: true,                // 单击某一行的时候选中某一条记录
        formId: "hide",
        /**列信息，需要预先定义好*/
        columns: column,
        // sortable: true,                     //是否启用排序
        // sortOrder: "asc",                   //排序方式
        toolbars: toolbarSetting,
    });
}

function saveCompose() {
    $.ajax({
        type : "get",
        url : "materielV2/saveCompose?projectPuid="+projectPuid,
        success : function (result) {
            window.Ewin.alert({message: '生成成功!'});
        },
        error : function (result) {
            window.Ewin.alert({message: '生成失败!'});
        }
    })
}