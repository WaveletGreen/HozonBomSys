/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */
/**项目ID*/
let projectId;
let $table;
/**
 * 访问的url
 */
let url = 'sparePartsBom/selectPageByProjectId';
let selectedRows = null;


const debug = false;

/**
 * 自动查询调用
 */
function doQuery() {
    projectId = getProjectUid();
    initTable();
}

const smallWidth = 150;
const midWidth = 250;
const largeWidth = 350;

/**
 * 创建table表头
 * @param result
 */
function createColumn(vehicleUsageTitle) {
    let column = [];
    column.push({field: 'ck', checkbox: true});
    column.push({field: 'ads', title: 'A/D/S', align: 'left', valign: 'middle', width: smallWidth});
    column.push({field: 'hierarchy', title: '层级', align: 'left', valign: 'middle', width: smallWidth});
    column.push({field: 'major', title: '专业', align: 'left', valign: 'middle', width: smallWidth});
    column.push({field: 'level', title: '级别', align: 'left', valign: 'middle', width: smallWidth});
    column.push({field: 'productivePartCode', title: '生产零件号', align: 'left', valign: 'middle', width: largeWidth});
    column.push({field: 'productivePartName', title: '生产零件名称', align: 'left', valign: 'middle', width: largeWidth});
    column.push({field: 'sparePartCode', title: '备件零件号', align: 'left', valign: 'middle', width: largeWidth});
    column.push({field: 'sparePartName', title: '备件零件名称', align: 'left', valign: 'middle', width: largeWidth});
    column.push({
        field: 'isSparePart', title: '是否备件', align: 'left', valign: 'middle', width: smallWidth,
        formatter: function (value, row, index) {
            if (1 === value || "1" === value) {
                return "<span>是</span>";
            }
            else if (0 === value || "0" === value) {
                return "<span >否</span>";
            }
            else {
                return "<span ></span>";
            }
        }
    });
    column.push({field: 'unit', title: '单位', align: 'left', valign: 'middle', width: midWidth});
    column.push({field: 'drawingNum', title: '图号', align: 'left', valign: 'middle', width: midWidth});
    column.push({field: 'department', title: '专业部门', align: 'left', valign: 'middle', width: largeWidth});
    column.push({field: 'responsibleEngineer', title: '责任工程师', align: 'left', valign: 'middle', width: largeWidth});
    column.push({field: 'supplier', title: '供应商', align: 'left', valign: 'middle', width: largeWidth});
    column.push({field: 'code', title: '代码', align: 'left', valign: 'middle', width: smallWidth});
    column.push({field: 'purchasingEngineer', title: '采购工程师', align: 'left', valign: 'middle', width: largeWidth});
    column.push({field: 'remark', title: '备注', align: 'left', valign: 'middle', width: largeWidth});
    column.push({field: 'partClass', title: '零件分类', align: 'left', valign: 'middle', width: largeWidth});
    column.push({field: 'workshop1', title: '车间1', align: 'left', valign: 'middle', width: midWidth});
    column.push({field: 'workshop2', title: '车间2', align: 'left', valign: 'middle', width: midWidth});


    for (let key in vehicleUsageTitle) {
        let json = {
            field: key,
            title: vehicleUsageTitle[key],
            align: 'left',
            valign: 'middle',
        };
        column.push(json);
    }

    log(column)
    return column;
}

/**
 * 添加
 * @returns {boolean}
 */
const add = function () {
    window.Ewin.dialog({
        title: "添加单条备件零件",
        url: "sparePartsBom/getPage?type=add&projectId=" + projectId,
        width: 600,
        height: 400
    })
};
/**
 * 添加子层
 */
const addChild = function () {
    selectedRows = $table.bootstrapTable('getSelections');
    if (selectedRows.length !== 1) {
        window.Ewin.alert({message: '<span style="color: red">请只选择一条备件数据添加子层!</span>'});
        return;
    }
    window.Ewin.dialog({
        title: "添加单条备件零件",
        url: "sparePartsBom/addChildPage?type=1",
        width: 600,
        height: 400
    })
};
/**
 * 删除
 * @returns {boolean}
 */
const doDelete = function () {
    let rows = $table.bootstrapTable('getSelections');
    log(rows)
    if (rows.length <= 0)
        return false;
    window.Ewin.confirm({
        title: '提示',
        message: '是否要删除您所选择的记录？',
        width: 500
    }).on(function (e) {
        if (e) {
            let data = [];
            for (let i = 0; i < rows.length; i++) {
                data.push({id: rows[i].id, relEbomLineId: rows[i].relEbomLineId});
            }
            log(data);
            if (!debug)
                $.ajax({
                    url: "sparePartsBom/deleteList",
                    type: "POST",
                    contentType:
                        "application/json",
                    data: JSON.stringify(data),
                    success: function (result) {
                        if (!result.success) {
                            window.Ewin.alert({message: result.errMsg});
                            return false;
                        }
                        else {
                            layer.msg('删除成功', {icon: 1, time: 2000});
                            initTable();
                            // $table.bootstrapTable('remove',{field:"ck", values:true});
                        }
                    }
                })
        }
    })
};
/**
 * 修改
 * @returns {boolean}
 */
const update = function () {
    selectedRows = $table.bootstrapTable('getSelections');
    //只能选一条
    if (selectedRows.length !== 1) {
        window.Ewin.alert({message: '请选择一条需要修改的数据!'});
        return false;
    }
    window.Ewin.dialog({
        title: "修改单条备件零件",
        url: "sparePartsBom/getPage?type=update&projectId=" + projectId,
        width: 600,
        height: 400
    })

    // else if (rows[0].status == 5 || rows[0].status == 6) {
    //     window.Ewin.alert({message: '对不起,审核中的数据不能修改!'});
    //     return false;
    // }
    // var url = "ebom/updateEbom";
    // $.ajax({
    //     url: "privilege/write?url=" + url,
    //     type: "GET",
    //     success: function (result) {
    //         if (!result.success) {
    //             window.Ewin.alert({message: result.errMsg});
    //             return false;
    //         }
    //         else {
    //             window.Ewin.dialog({
    //                 title: "修改",
    //                 url: "ebom/updateEbom?projectId=" + projectId + "&puid=" + rows[0].puid + "&updateType=" + 2,
    //                 gridId: "gridId",
    //                 width: 500,
    //                 height: 500
    //             });
    //         }
    //     }
    // })
};
/**
 * 递归查询测试
 * @returns {boolean}
 */
const selectRecursionByTopLayerId = function () {
    selectedRows = $table.bootstrapTable('getSelections');
    //只能选一条
    if (selectedRows.length !== 1) {
        window.Ewin.alert({message: '请选择一条需要修改的数据!'});
        return false;
    }
    window.Ewin.dialog({
        title: "修改单条备件零件",
        url: "sparePartsBom/getPage?type=selectRecursionByTopLayerId&projectId=" + projectId,
        width: 600,
        height: 400
    })
};

/**
 * 撤销
 * @returns {boolean}
 */
const revoke = function () {
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
const setUpLou = function () {
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
const attachChangeForm = function () {
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
const doRank = function () {
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
 * 导入Excel
 */
const importExcel = function () {
    var url = "ebom/importExcel";
    if (true) {
        alert('开发中');
        return;
    }

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
const exportExcel = function () {
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

/**
 * EBOM中标记跳转
 */
const remarkInEbom = function () {
    window.location.href = "sparePartsBom/jumpToEbom";
}
/**克隆数据*/
const clonePartData = function () {
    selectedRows = $table.bootstrapTable('getSelections');
    //只能选一条
    if (selectedRows.length !== 1) {
        window.Ewin.alert({message: '请选择一条需要修改的数据!'});
        return false;
    }
    window.Ewin.dialog({
        title: "修改单条备件零件",
        url: "sparePartsBom/getPage?type=clonePartData&projectId=" + projectId,
        width: 600,
        height: 400
    })
};
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

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
        text: '新增',
        iconCls: 'glyphicon glyphicon-plus',
        handler: clonePartData
    },
    {
        text: '递归测试',
        iconCls: 'glyphicon glyphicon-plus',
        handler: selectRecursionByTopLayerId
    },
    // {
    //     text: '添加子件',
    //     iconCls: 'glyphicon glyphicon-plus',
    //     handler: addChild
    // },
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
        text: 'EBOM中标记',
        iconCls: 'glyphicon glyphicon-plus',
        handler: remarkInEbom
    },
    //先去掉下面的多余功能
    // {
    //     text: '撤销',
    //     iconCls: 'glyphicon glyphicon-share-alt',
    //     handler: revoke
    // },
    // {
    //     text: '设置为LOU/取消',
    //     iconCls: 'glyphicon glyphicon-cog',
    //     handler: setUpLou
    // },
    // {
    //     text: '关联变更单',
    //     iconCls: 'glyphicon glyphicon-log-out',
    //     handler: attachChangeForm
    // },
    // {
    //     text: '引用层级',
    //     iconCls: 'glyphicon glyphicon-copyright-mark',
    //     handler: doRank
    // },
    // {
    //     text: '导入Excel',
    //     iconCls: 'glyphicon glyphicon-share',
    //     handler: importExcel
    // },
    // {
    //     text: '导出Excel',
    //     iconCls: 'glyphicon glyphicon-export',
    //     handler: exportExcel
    // }
]

function toPage() {
    var pageNum = $("#pageNum").val();
    if (pageNum) {
        $('#ebomManageTable').bootstrapTable('selectPage', parseInt(pageNum));
    }
}

function loadDataWithHead(vehicleUsageTitle) {
    let column = createColumn(vehicleUsageTitle);
    $table.bootstrapTable({
        url: url,
        method: 'GET',
        dataType: 'json',
        sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
        height: $(window.parent.document).find("#wrapper").height() - 150,
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
        sortName: 't.ID',
        clickToSelect: true,// 单击某一行的时候选中某一条记录
        showColumns: true, //是否显示所有的列
        showToggle: false,                   //是否显示详细视图和列表视图的切换按钮
        showRefresh: true,                  //是否显示刷新按钮
        toolbars: toolbar,
        responseHandler: function (res) {  //后操作
            //如果后台返回的json格式不是{rows:[{...},{...}],total:100},可以在这块处理成这样的格式
            let rows={};
            for (let i in res.result){
                let vehicleNum=res.result[i].reserved2;
                if(vehicleNum==null||vehicleNum.indexOf(",")==-1){
                    continue;
                }
                let _data=vehicleNum.split(",");//根据逗号进行拆分字符串
                //所有的字段都已经在后台去除所有的**
                for (let field in _data){
                    if(_data[field].indexOf("#")!=-1){//需要判断是否存在#，有些是没有的
                        let value=_data[field].split("#");//拆解字符串
                        let f=value[0];
                        let v=value[1];
                        res.result[i][f]=v;//填充遗漏的数据，本身也是一个JSON对象，可以进行无脑填充
                    }
                }
            }
            rows.rows=res.result;
            rows.total=res.totalCount;
            return rows;
        },
    });
}

/**
 * table初始化
 */
function initTable() {
    if (!checkIsSelectProject(projectId)) {
        return;
    }
    $("#projectId").val(projectId);
    $table.bootstrapTable('destroy');
    $.ajax({
        url: "sparePartsBom/vehicleUsageTitle?projectId=" + projectId,
        type: "GET",
        success: function (vehicleUsage) {
            loadDataWithHead(vehicleUsage);
        }
    })
}

/**
 * 页面初始化后自动生成table
 */
$(document).ready((function () {
    projectId = getProjectUid();
    $table = $("#sparePartTable");
    initTable();
}));

function getSelectedRows() {
    return selectedRows;
}
