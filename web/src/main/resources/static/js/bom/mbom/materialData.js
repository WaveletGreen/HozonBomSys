var puids='';
var $table;
var projectId;
/******************定义参数*************************/
/**
 * 关联变更单
 * @returns {boolean}
 */
const attachChangeForm = function () {
    var rows = $table.bootstrapTable('getSelections');
    for (var i = 0; i < rows.length; i++) {
        puids += rows[i].puid + ",";
    }
    if (rows.length > 1000) {
        window.Ewin.alert({message: '最多只能选择1000条数据关联变更单号'});
        return false;
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
    var url = "materiel/order/choose";
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
                    title: "选择变更表单",
                    url: "materiel/order/choose?projectId=" + projectId,
                    gridId: "gridId",
                    width: 450,
                    height: 450
                });
            }
        }
    })
};
/**
 * 修改
 * @returns {boolean}
 */
var modify = function () {
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
    var url = "materiel/updateMBom";
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
                    url: "materiel/updateMBom?projectId=" + projectId + "&puid=" + rows[0].puid,
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
    puids = "";
    for (var i = 0; i < rows.length; i++) {
        puids += rows[i].puid + ",";
    }
    var myData = JSON.stringify({
        "puids": puids,
    });

    if (rows.length == 0) {
        window.Ewin.alert({message: '请选择一条需要删除的数据!'});
        return false;
    } else if (rows[0].status == 5 || rows[0].status == 6) {
        window.Ewin.alert({message: '对不起,审核中的数据不能删除!'});
        return false;
    }
    var url = "materiel/delete";
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
                    _table += '<tr><td>' + rows[index].pMaterielCode + '</td></tr>';
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
                            url: "materiel/delete",
                            data: myData,
                            contentType: "application/json",
                            success: function (result) {
                                if (result.success) {
                                    layer.msg('删除成功', {icon: 1, time: 2000})
                                } else if (!result.success) {
                                    window.Ewin.alert({message: result.errMsg});
                                }
                                $table.bootstrapTable("refresh");
                            },
                            error: function (info) {
                                window.Ewin.alert({message: "操作失败:" + info.status});
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
const revoke = function () {
    var rows = $table.bootstrapTable('getSelections');
    puids = "";
    for (var i = 0; i < rows.length; i++) {
        puids += rows[i].puid + ",";
    }
    var myData = JSON.stringify({
        "projectId": projectId,
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
    var url = "materiel/cancel";
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
                            url: "materiel/cancel",
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
}
/**
 * 工具条
 * @type {*[]}
 */
const toolbar = [
    {
        text: '修改',
        iconCls: 'glyphicon glyphicon-pencil',
        handler: modify
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
        text: '关联变更单',
        iconCls: 'glyphicon glyphicon-log-out',
        handler: attachChangeForm
    },
]

/**
 * 页面初始化后自动生成table
 */
$(document).ready((function () {
    projectId= $("#project", window.top.document).val();
    var url = "materiel/getMateriel?projectId=" + projectId;
    initTable(url);
}))

/**\
 * 页面刷新
 * @param projectId
 */
function doRefresh(_projectId) {
    $('#materialDataTable').bootstrapTable('destroy');
    projectId=_projectId;
    var url = "materiel/getMateriel?projectId=" + projectId;
    initTable(url);
}

/**
 * 自动查询调用
 */
function doQuery() {
    projectId = $("#project", window.top.document).val();
    var url = "materiel/getMateriel?projectId=" + projectId;
    var pMaterielDataType = $("#pMaterielDataType").val();
    if (pMaterielDataType == "车型项目物料总表") {
        url += "&pMaterielDataType=" + "";
    }
    else {
        url += "&pMaterielDataType=" + pMaterielDataType;
    }
    initTable(url);
    $('#materialDataTable').bootstrapTable('destroy');
}

/**
 * 创建table表头
 * @param result
 * @returns {Array}
 */
function createColumn(result) {
    let column = [];
    column.push({field: 'ck', checkbox: true, Width: 50});
    var data = result.data;
    var keys = [];
    var values;
    for (var key in data) {
        if (data.hasOwnProperty(key)) {
            var json = {
                field: key,
                title: data[key],
                // align:
                //     'center',
                valign:
                    'middle'
            };
            column.push(json);
        }
    }
    column.push({
        field: 'status',
        title: '状态',
        align: 'center',
        valign: 'middle',
        formatter: function (value, row, index) {
            if (value == 1 || "1" == value) {
                return "<span style='color: #00B83F'>已生效</span>";
            }
            if (value == 2 || "2" == value) {
                return "<span style='color: #ff7cf4'>草稿状态</span>";
            }
            if (3 == value || "3" == value) {
                return "<span style='color: #9492a9'>废除状态</span>";
            }
            if (4 == value || "4" == value) {
                return "<span style='color: #a90009'>删除状态</span>";
            }
            if (value == 5 || value == "5") {
                return "<span style='color: #e2ab2f'>审核中</span>"
            }
            if (value == 6 || value == "6") {
                return "<span style='color: #e2ab2f'>审核中</span>"
            }
        }
    });
    return column;
}

/**
 * table初始化
 * @param url
 */
function initTable(url) {
    // projectId = $("#project", window.top.document).val();
    if (!checkIsSelectProject(projectId)) {
        return;
    }
    var currentProjectHead = $("#currentProjectHead", window.top.document).val();
    $table = $("#materialDataTable");
    var column = [];
    $.ajax({
        url: "materiel/title",
        type: "GET",
        success: function (result) {
            let column = createColumn(result);
            $table.bootstrapTable({
                    url: url,
                    method: 'GET',
                    dataType: 'json',
                    cache: false,
                    striped: true,                              //是否显示行间隔色
                    sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
                    height: $(window.parent.document).find("#wrapper").height() - 150,
                    width: $(window).width(),
                    formId: "queryMaterialData",
                    undefinedText: "",//当数据为 undefined 时显示的字符
                    pagination: true,
                    pageNumber: 1,                       //初始化加载第一页，默认第一页
                    pageSize: 20,                       //每页的记录行数（*）
                    pageList: ['ALL', 10, 20, 50, 100, 200, 500, 1000],        //可供选择的每页的行数（*）                    uniqueId: "puid",                     //每一行的唯一标识，一般为主键列
                    columns: column,
                    sortable: true,                     //是否启用排序
                    sortOrder: "asc",                   //排序方式
                    clickToSelect: true,               // 单击某一行的时候选中某一条记录
                    // striped: true,                      //是否显示行间隔色
                    showColumns: true,                  //是否显示所有的列
                    showToggle: false,                   //是否显示详细视图和列表视图的切换按钮
                    showRefresh: true,                  //是否显示刷新按钮
                    showExport: phoneOrPc(),              //是否显示导出按钮(此方法是自己写的目的是判断终端是电脑还是手机,电脑则返回true,手机返回falsee,手机不显示按钮)
                    exportDataType: "selected",              //basic', 'all', 'selected'.
                    exportTypes: ['xlsx'],	    //导出类型
                    //exportButton: $('#btn_export'),     //为按钮btn_export  绑定导出事件  自定义导出按钮(可以不用)
                    exportOptions: {
                        //ignoreColumn: [0,0],            //忽略某一列的索引
                        fileName: '物料数据导出',              //文件名称设置
                        worksheetName: 'Sheet1',          //表格工作区名称
                        tableName: '物料数据表',
                        excelstyles: ['background-color', 'color', 'font-size', 'font-weight'],
                        //onMsoNumberFormat: DoOnMsoNumberFormat
                    },
                    toolbars: toolbar
                }
            );
        }
    })
}

function toPage() {
    var pageNum = $("#pageNum").val();
    if (pageNum) {
        $('#materialDataTable').bootstrapTable('selectPage', parseInt(pageNum));
    }
}

$(document).keydown(function (event) {
    if (event.keyCode == 13) {
        $('form').each(function () {
            event.preventDefault();
        });
    }
});

/**
 * 获取到当前选择的rows的puid
 * @returns {*}
 */
function getPuids() {
    return puids
}