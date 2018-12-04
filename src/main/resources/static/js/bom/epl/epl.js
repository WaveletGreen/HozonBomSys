$(document).ready((function () {
    var projectPuid = $("#project", window.top.document).val();
    var eplUrl = "epl/record?projectId=" + projectPuid;
    initTable(eplUrl);
}))

function doRefresh(projectId) {
    $('#eplTable').bootstrapTable('destroy');
    var eplUrl = "epl/record?projectId=" + projectId;
    initTable(eplUrl);
}

function doQuery() {
    var projectPuid = $("#project", window.top.document).val();
    var eplUrl = "epl/record?projectId=" + projectPuid;
    var pFastener = $("#pFastener").val();
    if (pFastener == "请选择是否紧固件") {
        eplUrl += "&pFastener=" + "";
    }
    else {
        eplUrl += "&pFastener=" + pFastener;
    }
    initTable(eplUrl);
    $('#eplTable').bootstrapTable('destroy');
}

function initTable(eplUrl) {
    var projectPuid = $("#project", window.top.document).val();
    if (!checkIsSelectProject(projectPuid)) {
        return;
    }
    var $table = $("#eplTable");
    var column = [];
    var eplTitleUrl = "epl/epl/title";
    $.ajax({
        url: eplTitleUrl,
        type: "GET",
        success: function (result) {
            var column = [];
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
                            'middle',
                    };
                    column.push(json);
                }
            }
            $('#eplTable').bootstrapTable({
                method: 'GET',
                dataType: 'json',
                cache: false,
                striped: true,                              //是否显示行间隔色
                sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
                url: eplUrl,
                //data:myData,
                height: $(window.parent.document).find("#wrapper").height() - 150,
                width: $(window).width(),
                formId: "queryEplManage",
                undefinedText: "",//当数据为 undefined 时显示的字符
                pagination: true,
                pageNumber: 1,                       //初始化加载第一页，默认第一页
                pageSize: 20,                       //每页的记录行数（*）
                pageList: ['ALL', 10, 20, 50, 100, 200, 500, 1000],        //可供选择的每页的行数（*）                //queryParams:queryParam,
                //uniqueId: "puid",                     //每一行的唯一标识，一般为主键列
                showExport: true,
                exportDataType: 'all',
                columns: column,
                sortable: true,                     //是否启用排序
                sortOrder: "asc",                   //排序方式
                striped: true, //是否显示行间隔色
                search: false, //是否显示表格搜索，此搜索是客户端搜索，不会进服务端
                showColumns: true, //是否显示所有的列
                showToggle: false,                   //是否显示详细视图和列表视图的切换按钮
                showRefresh: true,                  //是否显示刷新按钮
            });
        }
    })

}

function toPage() {
    var pageNum = $("#pageNum").val();
    if (pageNum) {
        $('#eplTable').bootstrapTable('selectPage', parseInt(pageNum));
    }
}

$(document).keydown(function (event) {
    if (event.keyCode == 13) {
        $('form').each(function () {
            event.preventDefault();
        });
    }
});