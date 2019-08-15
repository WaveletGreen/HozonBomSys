/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

/**53个属性+勾选框列和序号列*/
var eBomTitleSet = 56;
/**项目ID*/
var projectId;
var $table;
/**
 * 访问的url
 */
var url = 'sparePartsBom/selectPageByProjectId';

/**
 * 自动查询调用
 */
function doQuery() {
    projectId = getProjectUid();
    initTable();
}

/**
 * 创建table表头
 * @param result
 */
function createColumn() {
    let column = [];
    column.push({field: 'ck', checkbox: true});
    column.push({field: 'ads', title: 'A/D/S', align: 'left', valign: 'middle', width: 80});
    column.push({field: 'hierarchy', title: '层级', align: 'left', valign: 'middle', width: 80});
    column.push({field: 'major', title: '专业', align: 'left', valign: 'middle', width: 80});
    column.push({field: 'level', title: '级别', align: 'left', valign: 'middle', width: 80});
    column.push({field: 'productivePartCode', title: '生产零件号', align: 'left', valign: 'middle', width: 300});
    column.push({field: 'productivePartName', title: '生产零件名称', align: 'left', valign: 'middle', width: 300});
    column.push({field: 'sparePartCode', title: '备件零件号', align: 'left', valign: 'middle', width: 300});
    column.push({field: 'sparePartName', title: '备件零件名称', align: 'left', valign: 'middle', width: 300});
    column.push({field: 'isSparePart', title: '是否备件', align: 'left', valign: 'middle', width: 80});
    column.push({field: 'unit', title: '单位', align: 'left', valign: 'middle', width: 200});
    column.push({field: 'department', title: '专业部门', align: 'left', valign: 'middle', width: 300});
    column.push({field: 'responsibleEngineer', title: '责任工程师', align: 'left', valign: 'middle', width: 300});
    column.push({field: 'supplier', title: '供应商', align: 'left', valign: 'middle', width: 300});
    column.push({field: 'code', title: '代码', align: 'left', valign: 'middle', width: 100});
    column.push({field: 'purchasingEngineer', title: '采购工程师', align: 'left', valign: 'middle', width: 300});
    column.push({field: 'remark', title: '备注', align: 'left', valign: 'middle', width: 300});
    column.push({field: 'partClass', title: '零件分类', align: 'left', valign: 'middle', width: 300});
    column.push({field: 'workshop1', title: '车间1', align: 'left', valign: 'middle', width: 200});
    column.push({field: 'workshop2', title: '车间2', align: 'left', valign: 'middle', width: 200});
    return column;
}

function createColumnVue() {
    let column = [];
    column.push({width: 60, titleAlign: 'center', columnAlign: 'center', type: 'selection'}),
        column.push({
            field: 'ads',
            title: 'A/D/S',
            width: 100,
            titleAlign: 'center',
            columnAlign: 'center',
            isResize: true
        });
    column.push({
        field: 'hierarchy',
        title: '层级',
        width: 100,
        titleAlign: 'center',
        columnAlign: 'center',
        isResize: true
    });
    column.push({field: 'major', title: '专业', width: 100, titleAlign: 'center', columnAlign: 'center', isResize: true});
    column.push({field: 'level', title: '级别', width: 100, titleAlign: 'center', columnAlign: 'center', isResize: true});
    column.push({
        field: 'productivePartCode',
        title: '生产零件号',
        width: 200,
        titleAlign: 'center',
        columnAlign: 'center',
        isResize: true
    });
    column.push({
        field: 'productivePartName',
        title: '生产零件名称',
        width: 200,
        titleAlign: 'center',
        columnAlign: 'center',
        isResize: true
    });
    column.push({
        field: 'sparePartCode', title: '备件零件号',
        width: 200, titleAlign: 'center', columnAlign: 'center', isResize: true
    });
    column.push({
        field: 'sparePartName', title: '备件零件名称',
        width: 200, titleAlign: 'center', columnAlign: 'center', isResize: true
    });
    column.push({
        field: 'isSparePart', title: '是否备件',
        width: 80, titleAlign: 'center', columnAlign: 'center', isResize: true
    });
    column.push({
        field: 'unit', title: '单位',
        width: 100, titleAlign: 'center', columnAlign: 'center', isResize: true
    });
    column.push({
        field: 'department', title: '专业部门',
        width: 100, titleAlign: 'center', columnAlign: 'center', isResize: true
    });
    column.push({
        field: 'responsibleEngineer',
        title: '责任工程师',
        width: 200,
        titleAlign: 'center',
        columnAlign: 'center',
        isResize: true
    });
    column.push({
        field: 'supplier', title: '供应商',
        width: 200, titleAlign: 'center', columnAlign: 'center', isResize: true
    });
    column.push({
        field: 'code', title: '代码',
        width: 60, titleAlign: 'center', columnAlign: 'center', isResize: true
    });
    column.push({
        field: 'purchasingEngineer',
        title: '采购工程师',
        width: 200,
        titleAlign: 'center',
        columnAlign: 'center',
        isResize: true
    });
    column.push({
        field: 'remark', title: '备注',
        width: 200, titleAlign: 'center', columnAlign: 'center', isResize: true
    });
    column.push({
        field: 'partClass', title: '零件分类',
        width: 100, titleAlign: 'center', columnAlign: 'center', isResize: true
    });
    column.push({
        field: 'workshop1', title: '车间1',
        width: 60, titleAlign: 'center', columnAlign: 'center', isResize: true
    });
    column.push({
        field: 'workshop2', title: '车间2',
        width: 60, titleAlign: 'center', columnAlign: 'center', isResize: true
    });
    return column;
}

/**
 * 添加
 * @returns {boolean}
 */
var add = function () {
    var rows = $table.bootstrapTable('getSelections');
    if (rows.length > 1) {
        window.Ewin.alert({message: '只能选择一个父级!'});
        return false;
    }
    var url = "ebom/addEbom";
    $.ajax({
        url: "privilege/write?url=" + url,
        type: "GET",
        success: function (result) {
            if (!result.success) {
                window.Ewin.alert({message: result.errMsg});
                return false;
            }
            else {
                if (rows.length == 1) {
                    window.Ewin.dialog({
                        title: "添加",
                        url: "ebom/addEbom?projectId=" + projectId + "&puid=" + rows[0].puid,
                        gridId: "gridId",
                        width: 500,
                        height: 500
                    })
                }
                else if (rows.length == 0) {
                    window.Ewin.dialog({
                        title: "添加",
                        url: "ebom/addEbom?projectId=" + projectId,
                        gridId: "gridId",
                        width: 500,
                        height: 500
                    })
                }
            }
        }
    })
};
/**
 * 修改
 * @returns {boolean}
 */
var update = function () {
    var rows = $table.bootstrapTable('getSelections');
    //只能选一条
    if (rows.length != 1) {
        window.Ewin.alert({message: '请选择一条需要修改的数据!'});
        return false;
    }
    else if (rows[0].status == 5 || rows[0].status == 6) {
        window.Ewin.alert({message: '对不起,审核中的数据不能修改!'});
        return false;
    }
    var url = "ebom/updateEbom";
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
                    title: "修改",
                    url: "ebom/updateEbom?projectId=" + projectId + "&puid=" + rows[0].puid + "&updateType=" + 2,
                    gridId: "gridId",
                    width: 500,
                    height: 500
                });
            }
        }
    })
};
/**
 * 删除
 * @returns {boolean}
 */
var doDelete = function () {
    var rows = $table.bootstrapTable('getSelections');
    var puids = "";
    for (var i = 0; i < rows.length; i++) {
        puids += rows[i].puid + ",";
    }
    var myData = JSON.stringify({
        "projectId": $("#project", window.top.document).val(),
        "puids": puids,
    });
    if (rows.length == 0) {
        window.Ewin.alert({message: '请选择一条需要删除的数据!'});
        return false;
    }
    else if (rows[0].status == 5 || rows[0].status == 6) {
        window.Ewin.alert({message: '对不起,审核中的数据不能删除!'});
        return false;
    }
    var url = "ebom/delete/ebom";
    $.ajax({
        url: "privilege/write?url=" + url,
        type: "GET",
        success: function (result) {
            if (!result.success) {
                window.Ewin.alert({message: result.errMsg});
                return false;
            }
            else {
                var _table = '<p>是否要删除您所选择的记录？</p>' +
                    '<div style="max-height: 400px;overflow:scroll;"><table class="table table-striped tableNormalStyle" >';
                for (var index in rows) {
                    _table += '<tr><td>' + rows[index].lineId + '</td></tr>';
                }
                _table += '</table></div>';
                window.Ewin.confirm({
                    title: '提示',
                    message: _table,
                    width: 500
                }).on(function (e) {
                    if (e) {
                        $.ajax({
                            type: "POST",
                            //ajax需要添加打包名
                            url: "ebom/delete/ebom",
                            data: myData,
                            contentType: "application/json",
                            success: function (result) {
                                if (typeof (result) == 'string') {
                                    result = JSON.parse(result);
                                }
                                if (result.success) {
                                    layer.msg('删除成功', {icon: 1, time: 2000})
                                } else if (!result.success) {
                                    window.Ewin.alert({message: result.errMsg});
                                }
                                $table.bootstrapTable("refresh");
                            },
                            error: function (info) {
                                window.Ewin.alert({message: ":" + info.status});
                            }
                        })
                    }
                });
            }
        }
    })
};
/**
 * 撤销
 * @returns {boolean}
 */
var revoke = function () {
    var rows = $table.bootstrapTable('getSelections');
    var puids = "";
    for (var i = 0; i < rows.length; i++) {
        puids += rows[i].puid + ",";
    }
    var myData = JSON.stringify({
        "projectId": $("#project", window.top.document).val(),
        "puids": puids,
    });
    if (rows.length == 0) {
        window.Ewin.alert({message: '请选择需要撤销的数据!'});
        return false;
    }
    else {
        for (var i = 0; i < rows.length; i++) {
            if (rows[i].status != 4 && rows[i].status != 2) {
                window.Ewin.alert({message: '撤销功能只能选择状态为草稿状态或删除状态的数据!'});
                return false;
            }
        }
    }
    var url = "ebom/cancel";
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
                    message: '确定要撤销数据吗?',
                    width: 500
                }).on(function (e) {
                    if (e) {
                        $.ajax({
                            type: "POST",
                            //ajax需要添加打包名
                            url: "ebom/cancel",
                            data: myData,
                            contentType: "application/json",
                            success: function (result) {
                                if (result.success) {
                                    layer.msg('撤销成功', {icon: 1, time: 2000})
                                } else if (!result.success) {
                                    window.Ewin.alert({message: result.errMsg});
                                }
                                $table.bootstrapTable("refresh");
                            },
                            error: function (info) {
                                window.Ewin.alert({message: ":" + info.status});
                            }
                        })
                    }
                });
            }
        }
    })
};
/**
 * 设置LOU
 * @returns {boolean}
 */
var setUpLou = function () {
    var rows = $table.bootstrapTable('getSelections');
    if (rows.length != 1) {
        window.Ewin.alert({message: '请选择一条需要设置LOU的数据!'});
        return false;
    }
    if (rows[0].status == 5 || rows[0].status == 6) {
        window.Ewin.alert({message: '对不起,审核中的数据不能设置为LOU!'});
        return false;
    }
    var puid = rows[0].puid;
    var myData = JSON.stringify({
        "projectId": $("#project", window.top.document).val(),
        "puid": puid,
    });
    var url = "loa/setLou";
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
                    type: "POST",
                    //ajax需要添加打包名
                    url: "loa/setLou",
                    data: myData,
                    contentType: "application/json",
                    success: function (result) {
                        if (result.success) {
                            layer.msg('设置成功', {icon: 1, time: 2000})
                        } else if (!result.success) {
                            window.Ewin.alert({message: result.data.errMsg});
                        }
                        $table.bootstrapTable("refresh");
                    },
                    error: function (info) {
                        window.Ewin.alert({message: ":" + info.status});
                    }
                })
            }
        }
    })
};
/**
 * 关联变更单
 * @returns {boolean}
 */
var attachChangeForm = function () {
    var rows = $table.bootstrapTable('getSelections');
    var puidArray = "";
    for (var i = 0; i < rows.length; i++) {
        puidArray += rows[i].puid + ",";
    }
    if (rows.length == 0) {
        window.Ewin.alert({message: '请选择需要变更的数据!'});
        return false;
    }
    else {
        for (var i = 0; i < rows.length; i++) {
            if (rows[i].status != 4 && rows[i].status != 2) {
                window.Ewin.alert({message: '只能选择状态为草稿状态或删除状态的数据发起流程!'});
                return false;
            }
        }
    }
    var url = "ebom/order/choose";
    $.ajax({
        url: "privilege/write?url=" + url,
        type: "GET",
        success: function (result) {
            if (!result.success) {
                window.Ewin.alert({message: result.errMsg});
                return false;
            }
            else {
                var myData = JSON.stringify({
                    "puids": puidArray,
                    "projectId": projectId
                });
                $.ajax({
                    url: "ebom/find/choose",
                    type: "POST",
                    gridId: "gridId",
                    contentType: "application/json",
                    data: myData,
                    success: function () {
                        window.Ewin.dialog({
                            title: "选择变更表单",
                            gridId: "gridId",
                            url: url,
                            width: 450,
                            height: 450
                        });
                    }
                });

            }
        }
    })
};
/**
 * 调整层级
 * @returns {boolean}
 */
var doRank = function () {
    var rows = $table.bootstrapTable('getSelections');
    //只能选一条进行层级调整
    if (rows.length != 1) {
        window.Ewin.alert({message: '请选择一条需要引用层级的数据!'});
        return false;
    }
    else if (rows[0].status == 5 || rows[0].status == 6) {
        window.Ewin.alert({message: '对不起,审核中的数据不能引用层级!'});
        return false;
    }
    var url = "ebom/updateEbomLevel";
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
                    title: "层级引用",
                    url: "ebom/updateEbomLevel?projectId=" + projectId
                    + "&puid=" + rows[0].puid + "&id=" + rows[0].lineId,
                    width: 500,
                    height: 500
                });
            }
        }
    })
};
/**
 * 显示子层
 * @returns {boolean}
 */
var showSublayer = function () {
    var rows = $table.bootstrapTable('getSelections');
    var puids = "";
    if (rows.length <= 0) {
        window.Ewin.alert({message: '请至少选择一条需要显示层级的数据!'});
        return false;
    }
    for (var i = 0; i < rows.length; i++) {
        puids += rows[i].puid + ",";
    }
    initTable1(url, puids)
    // if (this.innerText == '显示子层') {
    //
    //
    // }
    // if (this.innerText == '显示子层') {
    //     this.innerText = '取消显示子层'
    // }
    // else {
    //     this.innerText = '显示子层'
    // }
};
/**
 * 导入Excel
 */
var importExcel = function () {
    var url = "ebom/importExcel";
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
                    title: "导入",
                    url: "ebom/importExcel",
                    width: 600,
                    height: 500
                })
            }
        }
    })
};
/**
 * 导出Excel
 * @returns {boolean}
 */
var exportExcel = function () {
    var rows = $table.bootstrapTable('getSelections');//选中行数据
    var length = -1;
    if (rows.length == 0) {
        window.Ewin.alert({message: '请选择一条需要导出的数据!'});
        return false;
    } else {
        for (var index in rows) {
            if (rows[index].status == 5 || rows[index].status == 6) {
                window.Ewin.alert({message: '勾选的数据有审核中状态，审核中的数据不给导出修改!'});
                return false;
            }
            rows[index].map = {};
        }
        length = getLengthOfJson(rows[0]);
        console.log(length)
    }
    //动态获取单车配置用量数据
    for (let k in rows) {
        let param = {};
        //-1是把状态列去掉
        for (let i = 0; i < length - eBomTitleSet - 1; i++) {
            if (undefined == rows[k][('title' + i)] || "" == rows[k][('title' + i)] || null == rows[k][('title' + i)]) {
                param[('title' + i)] = "";
            }
            else {
                param[('title' + i)] = rows[k][('title' + i)];
            }
        }
        rows[k].map = param;//单车配置用量写进对象的Map
    }
    window.Ewin.confirm({title: '提示', message: '是否要导出选中行？', width: 500}).on(function (e) {
        if (e) {
            $.ajax({
                type: "POST",
                //ajax需要添加打包名
                url: "./ebom/excelExport",
                data: (JSON.stringify(rows)),
                contentType: "application/json",
                success: function (result) {
                    if (result.status) {
                        layer.msg(result.msg, {icon: 1, time: 2000})
                        //下载EBOM导入模板
                        window.location.href = result.path;//V1.1.0.log
                    }
                    else {
                        window.Ewin.alert({message: "操作导出失败:" + result.msg});
                    }
                    $table.bootstrapTable("refresh");
                },
                error: function (info) {
                    window.Ewin.alert({message: "操作导出:" + info.status});
                }
            })
        }
    })
};
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
/**
 * 取消显示子层
 * 只在initTable1中使用
 */
const cancelShowSublayer = function () {
    initTable(url);
}
/**
 * 工具条
 * @type {*[]}
 */
const toolbar = [
    {
        text: '添加',
        iconCls: 'glyphicon glyphicon-plus',
        handler: add
    },
    {
        text: '修改',
        iconCls: 'glyphicon glyphicon-pencil',
        handler: update
    },
    {
        text: '删除',
        iconCls: 'glyphicon glyphicon-remove',
        handler: doDelete
    },
    {
        text: '撤销',
        iconCls: 'glyphicon glyphicon-share-alt',
        handler: revoke
    },
    {
        text: '设置为LOU/取消',
        iconCls: 'glyphicon glyphicon-cog',
        handler: setUpLou
    },
    {
        text: '关联变更单',
        iconCls: 'glyphicon glyphicon-log-out',
        handler: attachChangeForm
    },
    {
        text: '引用层级',
        iconCls: 'glyphicon glyphicon-copyright-mark',
        handler: doRank
    },
    {
        text: '显示子层',
        iconCls: 'glyphicon glyphicon-eye-open',
        handler: showSublayer
    },
    {
        text: '导入Excel',
        iconCls: 'glyphicon glyphicon-share',
        handler: importExcel
    },
    {
        text: '导出Excel',
        iconCls: 'glyphicon glyphicon-export',
        handler: exportExcel
    }
]

const toolbar2 = [
    {
        text: '添加',
        iconCls: 'glyphicon glyphicon-plus',
        handler: add
    },
    {
        text: '修改',
        iconCls: 'glyphicon glyphicon-pencil',
        handler: update
    },
    {
        text: '删除',
        iconCls: 'glyphicon glyphicon-remove',
        handler: doDelete
    },
    {
        text: '撤销',
        iconCls: 'glyphicon glyphicon-share-alt',
        handler: revoke
    },
    {
        text: '设置为LOU/取消',
        iconCls: 'glyphicon glyphicon-cog',
        handler: setUpLou
    },
    {
        text: '关联变更单',
        iconCls: 'glyphicon glyphicon-log-out',
        handler: attachChangeForm
    },
    {
        text: '引用层级',
        iconCls: 'glyphicon glyphicon-copyright-mark',
        handler: doRank
    },
    {
        text: '取消显示子层',
        iconCls: 'glyphicon glyphicon-eye-open',
        handler: cancelShowSublayer
    },
    {
        text: '导入Excel',
        iconCls: 'glyphicon glyphicon-share',
        handler: importExcel
    },
    {
        text: '导出Excel',
        iconCls: 'glyphicon glyphicon-export',
        handler: exportExcel
    }
]


function toPage() {
    var pageNum = $("#pageNum").val();
    if (pageNum) {
        $('#ebomManageTable').bootstrapTable('selectPage', parseInt(pageNum));
    }
}

function queryLoa(row) {
    var myData = JSON.stringify({
        "projectId": $("#project", window.top.document).val(),
        "puid": row
    });
    $.ajax({
        type: "POST",
        //ajax需要添加打包名
        url: "loa/ebom",
        data: myData,
        contentType: "application/json",
        undefinedText: "",
        success: function (result) {
            var child = result.data.child;
            var parent = result.data.parent;
            var parentLevel = (parent.parentLevel == undefined ? "" : parent.parentLevel);
            var parentLineId = (parent.parentLineId == undefined ? "" : parent.parentLineId);
            var parentName = (parent.parentName == undefined ? "" : parent.parentName);
            var _table = '<div style="max-height: 400px;overflow:scroll;"><table class="table table-striped tableNormalStyle" >';
            _table += '<tr><td>父层级</td><td>父零件号</td><td>父名称</td></tr>'
            _table += '<tr><td>' + parentLevel + '</td><td>' + parentLineId + '</td><td>' + parentName + '</td></tr>';
            _table += '</table></div>' + '<div style="max-height: 400px;overflow:scroll;"><table class="table table-striped tableNormalStyle" >';
            _table += '<tr><td>子层级</td><td>子零件号</td><td>子名称</td></tr>'
            for (var i = 0; i < child.length; i++) {
                _table += '<tr><td>' + child[i].childLevel + '</td><td>' + child[i].childLineId + '</td><td>' + child[i].childName + '</td></tr>';
            }
            _table += '</table></div>';
            window.Ewin.confirm({title: '提示', message: _table, width: 500});
        }
    })
}

function queryLou(row) {
    var projectId = $("#project", window.top.document).val();
    $.ajax({
        type: "GET",
        //ajax需要添加打包名
        url: "loa/getLou?projectId=" + projectId + "&puid=" + row,

        undefinedText: "",
        success: function (result) {
            var data = result.data;
            var parent = data.parent;
            var config = data.config;
            var child = data.child;
            var parentLevel = (parent.parentLevel == undefined ? "" : parent.parentLevel);
            var parentLineId = (parent.parentLineId == undefined ? "" : parent.parentLineId);
            var parentName = (parent.parentName == undefined ? "" : parent.parentName);
            var pCfg0name = (config.pCfg0name == undefined ? "" : config.pCfg0name);
            var cfg0Desc = (config.cfg0Desc == undefined ? "" : config.cfg0Desc);
            var pCfg0familyname = (config.pCfg0familyname == undefined ? "" : config.pCfg0familyname);
            var cfg0FamilyDesc = (config.cfg0FamilyDesc == undefined ? "" : config.cfg0FamilyDesc);
            var _table = '<div style="max-height: 400px;overflow:scroll;"><table class="table table-striped tableNormalStyle" >';
            _table += '<tr><td>父层级</td><td>父零件号</td><td>父名称</td></tr>'
            _table += '<tr><td>' + parentLevel + '</td><td>' + parentLineId + '</td><td>' + parentName + '</td></tr>';
            _table += '</table></div>' + '<div style="max-height: 400px;overflow:scroll;"><table class="table table-striped tableNormalStyle" >';
            _table += '<tr><td>配置名</td><td>特性值描述</td><td>族名</td><td>特性描述</td></tr>'
            _table += '<tr><td>' + pCfg0name + '</td><td>' + cfg0Desc + '</td><td>' + pCfg0familyname + '</td><td>' + cfg0FamilyDesc + '</td></tr>';
            _table += '</table></div>' + '<div style="max-height: 400px;overflow:scroll;"><table class="table table-striped tableNormalStyle" >';
            _table += '<tr><td>子层级</td><td>子零件号</td><td>子名称</td></tr>'
            for (var i = 0; i < child.length; i++) {
                _table += '<tr><td>' + child[i].childLevel + '</td><td>' + child[i].childLineId + '</td><td>' + child[i].childName + '</td></tr>';
            }
            _table += '</table></div>';
            window.Ewin.confirm({title: '提示', message: _table, width: 500});
        }
    })
}

$(document).keydown(function (event) {
    if (event.keyCode == 13) {
        $('form').each(function () {
            event.preventDefault();
        });
    }
});

/**
 * table初始化
 * @param url
 */
function initTable() {
    if (!checkIsSelectProject(projectId)) {
        return;
    }
    $("#projectId").val(projectId);
    $table.bootstrapTable('destroy');
    let column = createColumn();
    $table.bootstrapTable({
        url: url,
        method: 'GET',
        dataType: 'json',
        sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
        height: $(window.parent.document).find("#wrapper").height() - 500,
        width: $(window).width(),
        formId: "sparePartForm",
        undefinedText: "",//当数据为 undefined 时显示的字符
        pagination: true,
        pageNumber: 1,                       //初始化加载第一页，默认第一页
        pageSize: 20,                       //每页的记录行数（*）
        pageList: [/*'ALL',*/ 10, 20, 50, 100, 200, 500, 1000],        //可供选择的每页的行数（*）
        columns: column,
        toolbar: "#toolbar",
        sortOrder: "asc",                   //排序方式
        clickToSelect: true,// 单击某一行的时候选中某一条记录
        showColumns: true, //是否显示所有的列
        showToggle: false,                   //是否显示详细视图和列表视图的切换按钮
        showRefresh: true,                  //是否显示刷新按钮
        toolbars: toolbar,
    });
}

function initTable1(url, puids) {
    if (!checkIsSelectProject(projectId)) {
        return;
    }
    $table.bootstrapTable('destroy');
    // $table = $("#ebomManageTable");
    $.ajax({
        url: "ebom/title?projectId=" + projectId,
        type: "GET",
        success: function (result) {
            let column = createColumn(result);
            $table.bootstrapTable({
                url: url + "&puids=" + puids + "&showBomStructure=1",
                method: 'GET',
                dataType: 'json',
                sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
                height: $(window.parent.document).find("#wrapper").height() - 400,
                width: $(window).width(),
                formId: "queryEbomManage",
                undefinedText: "",//当数据为 undefined 时显示的字符
                pagination: true,
                pageNumber: 1,                       //初始化加载第一页，默认第一页
                pageSize: 20,                       //每页的记录行数（*）
                pageList: ['ALL', 10, 20, 50, 100, 200, 500, 1000],        //可供选择的每页的行数（*）
                columns: column,
                toolbar: "#toolbar",
                sortOrder: "asc",                   //排序方式
                clickToSelect: true,// 单击某一行的时候选中某一条记录
                showColumns: true, //是否显示所有的列
                showToggle: false,                   //是否显示详细视图和列表视图的切换按钮
                showRefresh: true,                  //是否显示刷新按钮
                toolbars: toolbar2,
            });
            $table.bootstrapTable('hideColumn', 'groupNum');
            $table.bootstrapTable('hideColumn', 'rank');
        }
    });
}

/**
 * 初始化VUE组件
 */
function initVueTable() {
    let vm = new Vue({
        el: '#sparePartTableDiv',
        data: function () {
            return {
                pageSize: 20,
                pageIndex: 1,
                total: 0,
                pageSizeOption:[10,50,100,200,300,1000],
                tableData: [
                    // {
                    //     "name": "ads",
                    //     "hierarchy":
                    //         "156*****1987",
                    //     "major": "钢琴、书法、唱歌",
                    //     "level": "上海市黄浦区金陵东路569号17楼",
                    //     "productivePartCode": "productivePartCode",
                    //     "productivePartName": "productivePartName",
                    //     "sparePartCode": "sparePartCode",
                    //     "sparePartName": "sparePartName",
                    //     "isSparePart": "isSparePart",
                    //     "unit": "unit",
                    //     "department": "department",
                    //     "responsibleEngineer": "responsibleEngineer",
                    //     "supplier": "supplier",
                    //     "code": "code",
                    //     "purchasingEngineer": "purchasingEngineer",
                    //     "remark": "remark",
                    //     "partClass": "partClass",
                    //     "workshop1": "workshop1",
                    //     "workshop2": "workshop2",
                    // },
                ],
                columns: createColumnVue()
                //     [
                //     {width: 60, titleAlign: 'center',columnAlign:'center',type: 'selection'},
                //     {field: 'name', title:'姓名', width: 100, titleAlign: 'center',columnAlign:'center',isResize:true},
                //     {field: 'tel', title: '手机号码', width: 260, titleAlign: 'center',columnAlign:'center',isResize:true},
                //     {field: 'hobby', title: '爱好', width: 330, titleAlign: 'center',columnAlign:'center',isResize:true},
                //     {field: 'address', title: '地址', width:330,titleAlign: 'center',columnAlign:'left',isResize:true}
                // ]
            }
        },
        methods: {
            //
            selectALL(selection) {
                console.log('select-aLL', selection);
            },

            selectChange(selection, rowData) {
                console.log('select-change', selection, rowData);
            },

            selectGroupChange(selection) {
                console.log('select-group-change', selection);
            },
            pageChange(pageIndex) {

                this.pageIndex = pageIndex;
                this.getTableData();
                console.log(pageIndex)
            },
            pageSizeChange(pageSize) {
                this.pageIndex = 1;
                this.pageSize = pageSize;
                this.getTableData();
            },
            getTableData() {
                console.log('getTableData');
            },
            loadServerData() {
                axios.get(url, {
                    params: {
                        projectId: projectId
                    }
                })
                    .then(function (response) {
                        console.log(response.data);
                        console.log(vm.$data.tableData)
                        console.log(vm.$data.total)
                        vm.$data.tableData = response.data.result;
                        vm.$data.total = response.data.totalCount;
                        console.log(vm.$data.tableData)
                        console.log(vm.$data.total)
                    })
                    .catch(function (error) {
                        console.log(error);
                    });
            }
        },
        mounted() {
            this.loadServerData();
        },
    })
}

/**
 * 页面初始化后自动生成table
 */
$(document).ready((function () {
    projectId = getProjectUid();
    $table = $("#sparePartTable");
    initTable();
    initVueTable();
}));