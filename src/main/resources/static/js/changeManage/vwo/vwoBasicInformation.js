/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can't post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */
var vwoId = -1;
/**
 * 关联人员表的工具条设置
 */
var connectedTableToolbar = [
    {
        text: '添加',
        iconCls: 'glyphicon glyphicon-plus',
        handler: function () {
            window.Ewin.dialog({
                title: "添加",
                url: "getUserAndGroupPage?vwoId=" + vwoId,
                gridId: "gridId",
                width: 600,
                height: 500
            })
        }
    },
    {
        text: '删除',
        iconCls: 'glyphicon glyphicon-remove',
        handler: function () {
            var rows = $table.bootstrapTable('getSelections');
            if (rows.length == 0) {
                window.Ewin.alert({message: '请选择一条需要删除的数据!'});
                return false;
            }
            window.Ewin.confirm({title: '提示', message: '是否要删除您所选择的记录？', width: 500}).on(function (e) {
                if (e) {
                    $.ajax({
                        type: "POST",
                        //ajax需要添加打包名
                        url: "deleteVwoInfoChange",
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
            });
        }
    }
];
/**
 * 关联人员的表头设置
 */
var connectedTableColumn = [
    {
        field: 'ck',
        title: '通知关联变更人',
        checkbox: true
    },
    {
        field: 'personName',
        title: '关联工程师姓名',
        align: 'center',
        valign: 'middle',
        sortable: true,
        sortOrder: 'asc',
    },
    {
        field: 'personDeptName',
        title: '所属部门',
        align: 'center',
        valign: 'middle',
        sortable: true,
        sortOrder: 'asc',
    },
    {
        field: 'partId',
        title: '关联零件号',
        align: 'center',
        valign: 'middle',
        sortable: true,
        sortOrder: 'asc',
    },
    {
        field: 'partName',
        title: '关联零件名称',
        align: 'center',
        valign: 'middle',
        sortable: true,
        sortOrder: 'asc',
    }
];
/**
 * 分发与实施工具条设置
 * */
var vwoExeToolBar = [
    {
        text: '添加',
        iconCls: 'glyphicon glyphicon-plus',
        handler: function () {
            window.Ewin.dialog({
                title: "添加",
                url: "getVwoExecuteDialog?vwoId=" + vwoId,
                gridId: "gridId",
                width: 600,
                height: 500
            })
        }
    },
    {
        text: '删除',
        iconCls: 'glyphicon glyphicon-remove',
        handler: function () {
            var rows = tablex.bootstrapTable('getSelections');
            if (rows.length == 0) {
                window.Ewin.alert({message: '请选择一条需要删除的数据!'});
                return false;
            }
            window.Ewin.confirm({title: '提示', message: '是否要删除您所选择的记录？', width: 500}).on(function (e) {
                if (e) {
                    $.ajax({
                        type: "POST",
                        //ajax需要添加打包名
                        url: "deleteExecuteInfo",
                        data: JSON.stringify(rows),
                        contentType: "application/json",
                        success: function (result) {
                            if (result.status) {
                                layer.msg(result.msg, {icon: 1, time: 2000})
                            }
                            else {
                                window.Ewin.alert({message: "操作删除失败:" + result.msg});
                            }
                            tablex.bootstrapTable("refresh");
                        },
                        error: function (info) {
                            window.Ewin.alert({message: "操作删除:" + info.status});
                        }
                    })
                }
            });
        }
    }
];
/**
 * 分发与实施表头设置
 */
var vwoExeColumn = [
    {
        field: 'ck',
        title: '选择',
        checkbox: true
    },
    {
        field: 'exeDept',
        title: '部门',
        align: 'center',
        valign: 'middle',
        sortable: true,
        sortOrder: 'asc',
    },
    {
        field: 'exeUser',
        title: '人员',
        align: 'center',
        valign: 'middle',
        sortable: true,
        sortOrder: 'asc',
    },
    {
        field: 'exeRole',
        title: '角色',
        align: 'center',
        valign: 'middle',
        sortable: true,
        sortOrder: 'asc',
        formatter: function (value, row, index) {
            switch (value) {
                case "1":
                    return "Draft";
                case "2":
                    return "EDIT";
                case "3":
                    return "PROCESS";
                case "4":
                    return "IMPL";
                case "5":
                    return "INFO";
            }
        }
    },
    {
        field: 'exeMission',
        title: '任务',
        align: 'center',
        valign: 'middle',
        sortable: true,
        sortOrder: 'asc',
        formatter: function (value, row, index) {
            switch (value) {
                case "1":
                    return "Draft";
                case "2":
                    return "EDIT";
                case "3":
                    return "PROCESS";
                case "4":
                    return "IMPL";
                case "5":
                    return "INFO";
            }
        }
    },
    {
        field: 'exePlanFinishDate',
        title: '计划完成时间',
        align: 'center',
        valign: 'middle',
        sortable: true,
        sortOrder: 'asc',
        formatter: function (value, row, index) {
            return dateToStringFormat(value)
        }
    },
    {
        field: 'exeStatus',
        title: '状态',
        align: 'center',
        valign: 'middle',
        sortable: true,
        sortOrder: 'asc',
    },
    {
        field: 'exeProof',
        title: '证明',
        align: 'center',
        valign: 'middle',
        sortable: true,
        sortOrder: 'asc',
    }
];
/**变更描述表高度设置*/
var commonHeight = 400;
/**动态变化的变更描述表*/
var commonTableName = "vwo_table";

$(document).ready((function () {
    formatDate();
    vwoId = $("#vwo").val();
    let url = "getInformChangers";
    url += "?vwo=" + vwoId;
    loadConnectedData(url);
    taskStatusHold();
    loadChangeDesc();
}));

/**
 * 整理日期格式
 */
function formatDate() {
    let vwoCreateDate = stringToDateFormat($('#vwoCreateDate').val());
    $('#vwoCreateDate').val(vwoCreateDate);

    let vwoStartEffectiveTime = stringToDateFormat($('#vwoStartEffectiveTime').data("time"));
    let vwoEndEffectiveTime = stringToDateFormat($('#vwoEndEffectiveTime').data("time"));
    let vwoDemandFinishTime = stringToDateFormat($('#vwoDemandFinishTime').data("time"));
    let vwoExpectExecuteTime = stringToDateFormat($('#vwoExpectExecuteTime').data("time"));
    $('#vwoStartEffectiveTime').val(vwoStartEffectiveTime);
    $('#vwoEndEffectiveTime').val(vwoEndEffectiveTime);
    $('#vwoDemandFinishTime').val(vwoDemandFinishTime);
    $('#vwoExpectExecuteTime').val(vwoExpectExecuteTime);

    let opiBomMngOptionDate = stringToDateFormat($('#opiBomMngOptionDate').val());
    let opiPmtMngOptionDate = stringToDateFormat($('#opiPmtMngOptionDate').val());
    let opiProjMngOptionDate = stringToDateFormat($('#opiProjMngOptionDate').val());
    $('#opiBomMngOptionDate').val(opiBomMngOptionDate);
    $('#opiPmtMngOptionDate').val(opiPmtMngOptionDate);
    $('#opiProjMngOptionDate').val(opiProjMngOptionDate);

    // $('#vwoStartEffectiveTime').datetimepicker({
    //     format: 'yyyy-mm-dd',//日期的格式
    //     weekStart: 1,// 0（星期日）到6（星期六）
    //     startDate: "2008-01-01",//选择器的开始日期
    //     autoclose: true,//日期选择完成后是否关闭选择框
    //     bootcssVer: 3,//显示向左向右的箭头
    //     language: 'zh_CN',//语言
    //     minView: 2,//表示日期选择的最小范围，默认是hour
    //     todayBtn: true
    // });
    // $('#vwoEndEffectiveTime').datetimepicker({
    //     format: 'yyyy-mm-dd',//日期的格式
    //     weekStart: 1,// 0（星期日）到6（星期六）
    //     startDate: "2008-01-01",//选择器的开始日期
    //     autoclose: true,//日期选择完成后是否关闭选择框
    //     bootcssVer: 3,//显示向左向右的箭头
    //     language: 'zh_CN',//语言
    //     minView: 2,//表示日期选择的最小范围，默认是hour
    //     todayBtn: true
    // });
    // $('#vwoDemandFinishTime').datetimepicker({
    //     format: 'yyyy-mm-dd',//日期的格式
    //     weekStart: 1,// 0（星期日）到6（星期六）
    //     startDate: "2008-01-01",//选择器的开始日期
    //     autoclose: true,//日期选择完成后是否关闭选择框
    //     bootcssVer: 3,//显示向左向右的箭头
    //     language: 'zh_CN',//语言
    //     minView: 2,//表示日期选择的最小范围，默认是hour
    //     todayBtn: true
    // });
    // $('#vwoExpectExecuteTime').datetimepicker({
    //     format: 'yyyy-mm-dd',//日期的格式
    //     weekStart: 1,// 0（星期日）到6（星期六）
    //     startDate: "2008-01-01",//选择器的开始日期
    //     autoclose: true,//日期选择完成后是否关闭选择框
    //     bootcssVer: 3,//显示向左向右的箭头
    //     language: 'zh_CN',//语言
    //     minView: 2,//表示日期选择的最小范围，默认是hour
    //     todayBtn: true
    // });
}

/**
 * 加载关联数据

 */
function loadConnectedData(url) {
    var vwoStatus = vwoInfo.vwoStatus;
    if (vwoStatus != 10) {
        $("body input").attr('disabled', true);
        $("body select").attr('disabled', true);
        $("body textarea").attr('disabled', true);
        $("body a").each(function () {
            var aText = $(this).html();
            $(this).after("<span>" + aText + "</span>");
            $(this).remove();
        });
        clearToolbars();
        if (vwoStatus == 101) {
            var bomUser = $("#BomUserId").val();
            if (user == bomUser) {
                notDisabledById("bomLeaderOpinion");
                $(".head-btnSets").append('<button class="btn btn-success head-func-btn" style="margin-top: 100px;z-index:99999;"\n' +
                    '        id="bomLeadApprove" onclick="approve(\'saveBomLeaderOpinion\',\'bomLeaderOpinion\')">BOM经理审批\n' +
                    '</button>');
            }
        } else if (vwoStatus == 102) {
            var pmtUser = $("#PmtUserId").val();
            if (user == pmtUser) {
                notDisabledById("pmtLeaderOpinion");
                $(".head-btnSets").append('<button class="btn btn-success head-func-btn" style="margin-top: 100px;z-index:99999;"\n' +
                    '        id="pmtLeadApprove" onclick="approve(\'savePmtLeaderOpinion\',\'pmtLeaderOpinion\')">PMT经理审批\n' +
                    '</button>');
            }
        } else if (vwoStatus == 103) {
            var projUser = $("#ProjUserId").val();
            if (user == projUser) {
                notDisabledById("projLeaderOpinion");
                $(".head-btnSets").append('<button class="btn btn-success head-func-btn" style="margin-top: 100px;z-index:99999;"\n' +
                    '        id="projLeadApprove" onclick="approve(\'saveProjLeaderOpinion\',\'projLeaderOpinion\')">项目经理审批\n' +
                    '</button>');
            }
        }
    }
    else {
        disabledById('bomLeaderOpinion');
        disabledById('pmtLeaderOpinion');
        disabledById('projLeaderOpinion');
        $(".head-btnSets").append('<button class="btn btn-success head-func-btn" style="margin-top: 100px;z-index:99999;"\n' +
            '        id="vwoSaveBtn">保存\n' +
            '</button>');
        $(".head-btnSets").append('<button class="btn btn-success head-func-btn" style="margin-top: 150px;z-index:99999;"\n' +
            '        id="launch">发起\n' +
            '</button>');
    }
    var $table = $("#connectedTable");
    $table.bootstrapTable('destroy');
    $table.bootstrapTable({
        url: url,
        method: 'GET',
        height: 300,//$(window.parent.document).find("#wrapper").height() - document.body.offsetHeight-100,
        width: $(window).width(),
        showToggle: false,                   //是否显示详细视图和列表视图的切换按钮
        showRefresh: false,                  //是否显示刷新按钮
        pagination: false,                  //是否显示分页（*）
        clickToSelect: true,                // 单击某一行的时候选中某一条记录
        sortName: 'pColorCode',
        sortOrder: 'asc',
        formId: "queryConnectedData",
        toolbars: connectedTableToolbar,
        /**列信息，需要预先定义好*/
        columns: connectedTableColumn,
    });
}

// function registerBtn() {

$(document).ready(
    function () {
        loadVwoExecuteInfo();
        $("#release").click(
            function () {
                release();
            }
        );
        $("#launch").click(
            function () {
                launch();
            }
        );
        $("#interrupt").click(
            function () {
                interrupt();
            }
        );
        $("#vwoSaveBtn").click(
            function () {
                var vwoStatus = vwoInfo.vwoStatus;
                var param = {};
                if ("999" == vwoStatus) {
                    window.Ewin.alert({message: "该VWO已发布，不能保存"});
                    return false;
                } else if ("899" == vwoStatus) {
                    window.Ewin.alert({message: "该VWO已中断，不能保存"});
                    return false;
                }

                let data = {};
                let _d = $("#basicInfo").serializeArray();
                for (let p in _d) {
                    data[_d[p].name] = _d[p].value;
                }
                data.vwoComment = $("#vwoComment").val();
                data.vwoStatus = vwoStatus;
                param.info = data;


                let data2 = {};
                let _d2 = $("#influenceDept").serializeArray();
                for (let p in _d2) {
                    data2[_d2[p].name] = _d2[p].value;
                }
                param.dept = data;

                //保存高层意见

                let data3 = {};
                let _d3 = $("#leaderOpinion").serializeArray();
                for (let p in _d3) {
                    data3[_d3[p].name] = _d3[p].value;
                }
                ///回传VWO ID到后台
                data3.opiVwoId = $("#vwo").val();

                param.bom = data;
                param.pmt = data;
                param.proj = data;

                data3.vwoStatus = vwoStatus;
                $.ajax({
                    contentType:
                        "application/json",
                    type:
                        'POST',
                    data: JSON.stringify(data),
                    url: "saveBasic",
                    success: function (result) {
                        if (result.status) {
                            layer.msg("保存成功", {icon: 1, time: 2000})
                        }
                        else {
                            window.Ewin.alert({message: "保存失败"});
                        }
                    },
                    error: function (e) {
                        console.log("连接服务器失败:" + e.status);
                    }
                });
                $.ajax({
                    contentType:
                        "application/json",
                    type:
                        'POST',
                    data: JSON.stringify(data2),
                    url: "saveInfluenceDept",
                    success: function (result) {
                    },
                    error: function (e) {
                        console.log("连接服务器失败:" + e.status);
                    }
                });
            });
    }
);

/**
 * 选择影响高层弹窗
 * @param id
 */
function doSelectPerson(id) {
    window.Ewin.dialog({
        title: "添加",
        url: "doSelectPersonPage?vwo=" + vwoId + "&selectType=" + id,
        gridId: "gridId",
        width: 600,
        height: 500
    })
}


/**
 * 重新绘制发布与实施table
 */
function loadVwoExecuteInfo() {
    var tablex = $("#executeTable");
    tablex.bootstrapTable('destroy');
    //下面这一行出现了bug，前端无法识别
    tablex.bootstrapTable({
        url: "getExecuteInfo?vwo=" + vwoId,
        method: 'GET',
        height: 350,//$(window.parent.document).find("#wrapper").height() - document.body.offsetHeight-100,
        width: $(window).width(),
        showToggle: false,                   //是否显示详细视图和列表视图的切换按钮
        showRefresh: false,                  //是否显示刷新按钮
        pagination: false,                  //是否显示分页（*）
        clickToSelect: true,                // 单击某一行的时候选中某一条记录
        sortName: 'exeId',
        sortOrder: 'asc',
        formId: "queryConnectedData",
        toolbars: vwoExeToolBar,
        /**列信息，需要预先定义好*/
        columns: vwoExeColumn,
    });
}

/**
 * 刷新发布与实施table
 */
function doRefreshExecuteTable() {
    $("#executeTable").bootstrapTable("refresh");
    undead();
}

/**
 * 刷新关联人员table
 */
function doRefreshConnectedTable() {
    $("#connectedTable").bootstrapTable("refresh");
    undead();
}

/**
 * 防止页面卡斯
 */
function undead() {
    $('body').removeClass("modal-open");
    $('body').removeAttr("style");
}


/**
 * 发布
 */
function release() {
    var vwoStatus = vwoInfo.vwoStatus;
    if ("999" == vwoStatus) {
        window.Ewin.alert({message: "该VWO已发布，不能发布"});
        return false;
    } else if ("899" == vwoStatus) {
        window.Ewin.alert({message: "该VWO已中断，不能发布"});
        return false;
    }
    $.ajax({
        contentType:
            "application/json",
        type:
            'POST',
        data: JSON.stringify(getVwoInfo()),
        url: "release",
        success: function (result) {
            if (result.status) {
                layer.msg(result.msg, {icon: 1, time: 2000})
                window.location.reload();
            }
            else {
                window.Ewin.alert({message: "发布失败:" + result.msg});
            }
            console.log(result);
        },
        error: function (e) {
            console.log("连接服务器失败:" + e.status);
        }
    });
}

/**
 * 中断
 */
function interrupt() {
    var vwoStatus = vwoInfo.vwoStatus;
    if ("999" == vwoStatus) {
        window.Ewin.alert({message: "该VWO已发布，不能中断"});
        return false;
    } else if ("899" == vwoStatus) {
        window.Ewin.alert({message: "该VWO已中断，不能中断"});
        return false;
    }
    $.ajax({
        contentType:
            "application/json",
        type:
            'POST',
        data: JSON.stringify(getVwoInfo()),
        url: "interrupt",
        success: function (result) {
            if (result.status) {
                layer.msg(result.msg, {icon: 1, time: 2000})
                window.location.reload();
            }
            else {
                window.Ewin.alert({message: "中断失败:" + result.msg});
            }
            console.log(result);
        },
        error: function (e) {
            console.log("连接服务器失败:" + e.status);
        }
    });
}

/**
 * 发起
 */
function launch() {
    $.ajax({
        contentType:
            "application/json",
        type:
            'POST',
        data: JSON.stringify(getVwoInfo()),
        url: "launch",
        success: function (result) {
            if (result.status) {
                layer.msg(result.msg, {icon: 1, time: 2000});
                window.top.loadTasks();
                window.location.reload();
            }
            else {
                window.Ewin.alert({message: "发起失败:" + result.msg});
            }
            console.log(result);
        },
        error: function (e) {
            console.log("连接服务器失败:" + e.status);
        }
    });
}

function getVwoInfo() {
    let data = {};
    data.projectUid = getProjectUid();
    data.vwoId = $("#vwo").val();
    data.vwoType = $("#vwoType").val();
    data.formId = $(window.parent.document).contents().find(".tab-pane.fade.active.in")[0].id;
    return data;
}

/**
 * 根据form的ID。获取到表单中的数据
 * @param formId
 * @returns {{}}
 */
function getFormData(formId) {
    let data3 = {};
    let _d3 = $("#" + formId).serializeArray();
    for (let p in _d3) {
        data3[_d3[p].name] = _d3[p].value;
    }
    return data3;
}


function approve(url, formId) {
    let isx = $("#" + formId + " select").find("option:selected").text();
    window.Ewin.confirm({
        title: '提示',
        message: '评估意见:<span style="color: red">' + isx + '</span>',
        width: 500
    }).on(function (e) {
        if (e) {
            let data = {};
            let d = $("#" + formId).serializeArray();
            for (let p in d) {
                data[d[p].name] = d[p].value;
            }
            data.opiVwoId = $("#vwo").val();
            data.vwoType = $("#vwoType").val();
            data.projectUid = getProjectUid();
            $.ajax({
                type: "POST",
                url: url,
                data: data,
                success: function (result) {
                    if (result.status) {
                        layer.msg(result.msg, {icon: 1, time: 2000});
                        window.top.loadTasks();
                        setTimeout('window.location.reload()', 2000);
                    } else {
                        window.Ewin.alert({message: "评估失败:" + result.msg});
                    }
                },
                error: function (result) {

                }
            });
        }
    });
}

function clearToolbars() {
    connectedTableToolbar = [];
    vwoExeToolBar = [];
}

function disabledById(id) {
    $("#" + id + " input").attr('disabled', true);
    $("#" + id + " select").attr('disabled', true);
    $("#" + id + " textarea").attr('disabled', true);
}

function notDisabledById(id) {
    $("#" + id + " select").attr('disabled', false);
    $("#" + id + " textarea").attr('disabled', false);
}

/**
 * 获取到真实
 */
function saveTask() {
    $target_div = $(window.parent.document).contents().find(".tab-pane.fade.active.in")[0].id;
    $iframe_src = $(window.parent.document).contents().find(".tab-pane.fade.active.in iframe")[0].src;
    console.log($target_div);
    console.log($iframe_src);

}

var $iframe_src;
var $target_div;


function doSelectBomMag(id) {
    window.Ewin.dialog({
        title: "添加",
        url: "doSelectBomMag?vwo=" + vwoId + "&selectType=" + id,
        gridId: "gridId",
        width: 600,
        height: 500
    })
}


function loadChangeDesc() {
    let vwoType = $("#vwoType").val();
    switch (vwoType) {
        case "1":
            loadFeature(vwoId);
            break;
        case "2":
            loadModelColor(vwoId);
            break;
        case "3":
            break;
    }
}