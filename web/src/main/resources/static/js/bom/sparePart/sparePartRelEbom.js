/**53个属性+勾选框列和序号列*/
var eBomTitleSet = 56;
/**项目ID*/
var projectId;
var $table;
/**
 * 访问的url
 */
var eBomUrl;

let selectedRows;


/**
 * 创建table表头
 * @param result
 */
function createColumn(result) {
    let column = [];
    column.push({field: 'ck', checkbox: true});
    var data = result;
    for (var key in data) {
        log(key);
        log(data[key]);
        if (data.hasOwnProperty(key)) {
            if ('pLouaFlag' === key) {
                var json = {
                    field: key,
                    title: data[key],
                    // align: 'center',
                    valign: 'middle',

                    formatter: function (value, row, index) {
                        if (value == "LOA") {
                            return [
                                '<a href="javascript:void(0)" onclick="queryLoa(\'' + row.puid + '\')">' + value + '</a>'
                            ].join("");
                        } else if (value == "LOU") {
                            return [
                                '<a href="javascript:void(0)" onclick="queryLou(\'' + row.puid + '\')">' + value + '</a>'
                            ].join("");
                        }
                        else {
                            return [
                                value
                            ].join("");
                        }
                    }
                };
                column.push(json);
            }
            else {
                var json = {
                    field: key,
                    title: data[key],
                    // align: 'center',
                    valign: 'middle',

                    formatter: function (value, row, index) {
                        if (value == "LOA") {
                            return [
                                '<a href="javascript:void(0)" onclick="queryLoa(' + row.puid + ')">' + value + '</a>'
                            ].join("");
                        } else if (value == "LOU") {
                            return [
                                '<a href="javascript:void(0)" onclick="queryLou(' + row.puid + ')">' + value + '</a>'
                            ].join("");
                        }
                        else {
                            return [
                                value
                            ].join("");
                        }
                    }
                };
                column.push(json);
            }
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
    })
    log(column)
    return column;
}

/**
 * 引用
 */
const quote = function () {
    selectedRows = $table.bootstrapTable('getSelections');
    log(selectedRows)
    if (selectedRows.length <= 0) {
        return false;
    }
    let data = {};
    data.projectId = projectId;
    data.ids = [];
    for (let i in selectedRows) {
        data.ids[i] = selectedRows[i].puid
    }
    $.ajax({
        url: "quoteEbomLines",
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
                layer.msg('引用成功', {icon: 1, time: 2000});
            }
        }
    })
};
/**
 * 新增
 */
const newlyAdd = function () {
    selectedRows = $table.bootstrapTable('getSelections');
    if (selectedRows.length !== 1) {
        window.Ewin.alert({message: '<span style="color: red">请只选择一条备件数据添加子层!</span>'});
        return;
    }
    log(selectedRows)
};
/**
 * 合成
 */
const synthesize = function () {
    selectedRows = $table.bootstrapTable('getSelections');
    log(selectedRows)
};


/**
 * 工具条
 * @type {*[]}
 */
const toolbar = [
    {
        text: '引用',
        iconCls: 'glyphicon glyphicon-plus',
        handler: quote
    },
    {
        text: '新增',
        iconCls: 'glyphicon glyphicon-plus',
        handler: quote
    },
    {
        text: '合成',
        iconCls: 'glyphicon glyphicon-plus',
        handler: quote
    }
]

function toPage() {
    var pageNum = $("#pageNum").val();
    if (pageNum) {
        $('#ebomManageTable').bootstrapTable('selectPage', parseInt(pageNum));
    }
}

/**
 * table初始化
 * @param eBomUrl
 */
function initTable(eBomUrl) {
    if (!checkIsSelectProject(projectId)) {
        return;
    }
    $table.bootstrapTable('destroy');
    $.ajax({
        url: "ebom/title?projectId=" + projectId,
        type: "GET",
        success: function (result) {
            let column = createColumn(result);
            $table.bootstrapTable({
                url: eBomUrl,
                method: 'GET',
                dataType: 'json',
                sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
                height: $(window.parent.document).find("#wrapper").height() - 150,
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
                toolbars: toolbar,
            });
        }
    });
}

/**\
 * 页面刷新
 * @param projectId
 */
function doRefresh(projectPuid) {
    $('#ebomManageTable').bootstrapTable('destroy');
    projectId = getProjectUid();
    var eBomUrl = "ebom/getEBom/list?projectId=" + projectId;
    initTable(eBomUrl);
}

/**
 * 自动查询调用
 */
function doQuery() {
    projectId = getProjectUid();
    eBomUrl = "ebom/getEBom/list?projectId=" + projectId;
    var pBomLinePartClass = $("#pBomLinePartClass").val();
    if (pBomLinePartClass == "请选择零件分类") {
        eBomUrl += "&pBomLinePartClass=" + "";
    } else {
        eBomUrl += "&pBomLinePartClass=" + pBomLinePartClass;
    }
    var pBomLinePartResource = $("#pBomLinePartResource").val();
    if (pBomLinePartResource == "请选择零件来源") {
        eBomUrl += "&pBomLinePartResource=" + "";
    }
    else {
        eBomUrl += "&pBomLinePartResource=" + pBomLinePartResource;
    }
    initTable(eBomUrl);
    // $('#ebomManageTable').bootstrapTable('destroy');
}

/**
 * 页面初始化后自动生成table
 */
$(document).ready((function () {
    projectId = getProjectUid();
    $table = $("#ebomManageTable");
    eBomUrl = "ebom/getEBom/list?projectId=" + projectId;
    initTable(eBomUrl);
}))