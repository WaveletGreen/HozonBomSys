/**
 * Created with IntelliJ IDEA.
 * User: xulf
 * Date: 2019/8/21
 * Time: 17:22
 */

/**53个属性+勾选框列和序号列*/
var eBomTitleSet = 56;
/**项目ID*/
var projectId;
var $table;
/**
 * 访问的url
 */
var eBomUrl;
/**\
 * 页面刷新
 * @param projectId
 */
function doRefresh(projectPuid) {
    $('#selectEbomTable').bootstrapTable('destroy');
    projectId = getProjectUid();
    var eBomUrl = "getEBom/list?projectId=" + projectId;
    var puid = $("#puid").val();
    initTable(eBomUrl,puid);
}
/**
 * 自动查询调用
 */
function doQuery() {
    projectId = getProjectUid();
    eBomUrl = "getEBom/list?projectId=" + projectId;
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
    var puid = $("#puid").val();
    initTable(eBomUrl,puid);
}
/**
 * 创建table表头
 * @param result
 */
function createColumn(result) {
    let column = [];
    column.push({field: 'ck', checkbox: true});
    var data = result.data;
    for (var key in data) {
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
    return column;
}

/**
 * table初始化
 * @param eBomUrl
 */
function initTable(eBomUrl,puid) {
    if (!checkIsSelectProject(projectId)) {
        return;
    }
    $table.bootstrapTable('destroy');
    $.ajax({
        url: "title?projectId=" + projectId,
        type: "GET",
        success: function (result) {
            let column = createColumn(result);
            $table.bootstrapTable({
                url: eBomUrl+"&puids="+puid+"&showBomStructure=1",
                method: 'GET',
                dataType: 'json',
                sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
                height: $(window.parent.document).find("#wrapper").height() - 150,
                width: $(window).width(),
                formId: "querySelectEbom",
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
            });
            $table.bootstrapTable('hideColumn', 'groupNum');
            $table.bootstrapTable('hideColumn', 'rank');
        }
    });
}

function toPage() {
    var pageNum = $("#pageNum").val();
    if (pageNum) {
        $('#selectEbomTable').bootstrapTable('selectPage', parseInt(pageNum));
    }
}

function queryLoa(row) {
    var myData = JSON.stringify({
        "projectId": projectId,
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
 * 页面初始化后自动生成table
 */
$(document).ready((function () {
    projectId = $("#project", window.top.document).val();
    $table = $("#selectEbomTable");
    eBomUrl = "getEBom/list?projectId=" + projectId;
    var puid = $("#puid").val();
    initTable(eBomUrl,puid);
}))