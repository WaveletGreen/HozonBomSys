/**
 * Created with IntelliJ IDEA.
 * User: xulf
 * Date: 2019/8/27
 * Time: 10:02
 */
/**
 * 创建标题
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
            // if ('pLouaFlag' === key) {
            var json = {
                field: key,
                title: data[key],
                align: 'center',
                valign: 'middle',
                // formatter: function (value, row, index) {
                //     if (value == "LOA") {
                //         return [
                //             '<a href="javascript:void(0)" onclick="queryLoa(\'' + row.eBomPuid + '\')">' + value + '</a>'
                //         ].join("");
                //     }
                //     else if (value == "LOU") {
                //         return [
                //             '<a href="javascript:void(0)" onclick="queryLou(\'' + row.eBomPuid + '\')">' + value + '</a>'
                //         ].join("");
                //     }
                //     else {
                //         return [
                //             value
                //         ].join("");
                //     }
                // }
            };
            column.push(json);
            // }
            // else {
            //     var json = {
            //         field: key,
            //         title: data[key],
            //         // align: 'center',
            //         valign: 'middle',
            //         formatter: function (value, row, index) {
            //             if (value == "LOA") {
            //                 return [
            //                     '<a href="javascript:void(0)" onclick="queryLoa(' + row.eBomPuid + ')">' + value + '</a>'
            //                 ].join("");
            //             }
            //             else if (value == "LOU") {
            //                 return [
            //                     '<a href="javascript:void(0)" onclick="queryLou(\'' + row.eBomPuid + '\')">' + value + '</a>'
            //                 ].join("");
            //             }
            //             else {
            //                 return [
            //                     value
            //                 ].join("");
            //             }
            //         }
            //     };
            //     column.push(json);
            // }
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
    return column;
}

/**
 * 页面刷新
 * @param projectId
 */
function doRefresh(projectId) {
    $('#selectPbomTable').bootstrapTable('destroy');
    var pBomUrl = "getBomManage?projectId=" + projectId;
    var puids = $("#puids").val();
    initTable(pBomUrl, puids);
}

/**
 * 调用查询
 */
function doQuery() {
    var projectId = $("#project", window.top.document).val();
    var pBomUrl = "getBomManage?projectId=" + projectId;
    var pBomLinePartClass = $("#pBomLinePartClass").val();
    if (pBomLinePartClass == "请选择零件分类") {
        pBomUrl += "&pBomLinePartClass=" + "";
    } else {
        pBomUrl += "&pBomLinePartClass=" + pBomLinePartClass;
    }
    var pBomLinePartResource = $("#pBomLinePartResource").val();
    if (pBomLinePartResource == "请选择零件来源") {
        pBomUrl += "&pBomLinePartResource=" + "";
    }
    else {
        pBomUrl += "&pBomLinePartResource=" + pBomLinePartResource;
    }
    var pIsNewPart = $("#pIsNewPart").val();
    if (pIsNewPart == "请选择工艺合件") {
        pBomUrl += "&pIsNewPart=" + "";
    }
    else {
        pBomUrl += "&pIsNewPart=" + pIsNewPart;
    }
    var puids = $("#puids").val();
    initTable(pBomUrl, puids);
}

/**
 * 初始化Table
 * @param pBomUrl
 * @param puids
 */
function initTable(pBomUrl, puids) {
    var $table = $("#selectPbomTable");
    var projectId = $("#project", window.top.document).val();
    if (!checkIsSelectProject(projectId)) {
        return;
    }
    $.ajax({
        url: "manage/title?project=" + projectId,
        type: "GET",
        success: function (result) {
            let column = createColumn(result)
            $table.bootstrapTable({
                url: pBomUrl + "&showBomStructure=1" + "&eBomPuids=" + puids,
                method: 'GET',
                dataType: 'json',
                cache: false,
                striped: true,                              //是否显示行间隔色
                sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
                height: $(window.parent.document).find("#wrapper").height() - 100,
                width: $(window).width(),
                formId: "queryPbomManage",
                undefinedText: "",//当数据为 undefined 时显示的字符
                pagination: true,
                pageNumber: 1,                       //初始化加载第一页，默认第一页
                pageSize: 20,                       //每页的记录行数（*）
                pageList: ['ALL', 10, 20, 50, 100, 200, 500, 1000],        //可供选择的每页的行数（*）                uniqueId: "puid",                     //每一行的唯一标识，一般为主键列
                showExport: false,
                columns: column,
                sortable: true,                     //是否启用排序
                sortOrder: "asc",                   //排序方式
                clickToSelect: true,// 单击某一行的时候选中某一条记录
                striped: true, //是否显示行间隔色
                showColumns: true, //是否显示所有的列
                showToggle: false,                   //是否显示详细视图和列表视图的切换按钮
                showRefresh: true,                  //是否显示刷新按钮
                toolbars: [
                    {
                        text: '返回上一步',
                        iconCls: 'fa fa-mail-reply',
                        handler: function () {
                            window.history.back(-1);
                        }
                    },
                ]
            });
            // $table.bootstrapTable('hideColumn', 'level');
            $table.bootstrapTable('hideColumn', 'groupNum');
            $table.bootstrapTable('hideColumn', 'pBomLinePartEnName');
        }
    });
}
function toPage() {
    var pageNum = $("#pageNum").val();
    if (pageNum) {
        $('#selectPbomTable').bootstrapTable('selectPage', parseInt(pageNum));
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
 * 页面初始化后自动生成table
 */
$(document).ready((function () {
    var projectId = $("#project", window.top.document).val();
    var pBomUrl = "getBomManage?projectId=" + projectId;
    var puids = $("#puids").val();
    initTable(pBomUrl, puids);
}))