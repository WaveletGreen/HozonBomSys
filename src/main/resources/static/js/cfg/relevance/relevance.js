var firstLoad = true;
function loadData(){
    var $table = $("#dataTable");
    $table.bootstrapTable('destroy');
    $("#refresh").removeAttr("disabled");
    var projectPuid = $("#project", window.top.document).val();
    $table.bootstrapTable({
        url: "cfg0/loadRelevance?projectPuid=" + projectPuid,
        method: "GET",
        height: $(window.parent.document).find("#wrapper").height() - document.body.offsetHeight - 45,
        width: $(window).width(),
        showToggle: true,                   //是否显示详细视图和列表视图的切换按钮
        // showColumns: true,                  //是否显示所有的列
        showRefresh: true,                  //是否显示刷新按钮
        pageSize: 10,
        pagination: true,                   //是否显示分页（*）
        clickToSelect: true,                // 单击某一行的时候选中某一条记录
        formId: "puid",
        toolbars: [
            {
                text: '修改',
                iconCls: 'glyphicon glyphicon-pencil',
                handler: function () {
                    var rows = $table.bootstrapTable('getSelections');
                    //只能选一条
                    if (rows.length != 1) {
                        window.Ewin.alert({message: '请选择一条需要修改的数据!'});
                        return false;
                    }
                    window.Ewin.dialog({
                        title: "修改",
                        url: "cfg0/relModifyPage?uid=" + rows[0].puid + "&page=" + "modifyPage",
                        gridId: "gridId",
                        width: 400,
                        height: 500
                    });
                }
            }
        ],
        /**列信息，需要预先定义好*/
        columns: [
            {
                field: 'ck',
                checkbox: true
            },
            {
                field: 'index',
                title: '序号',
            },
            {
                field: 'relevance',
                title: '相关性',
                align: 'center',
                valign: 'middle',
            },
            {
                field: 'relevanceDesc',
                title: '相关性描述',
                align: 'center',
                valign: 'middle',
            },
            {
                field: 'relevanceCode',
                title: '相关性代码',
                align: 'center',
                valign: 'middle',
            },
            {
                field: 'puid',
                title: 'puid',
                hide: false
            },
            {
                field: '_table',
                title: '_table',
                hide: false
            }
        ],
        // sortable: true,                     //是否启用排序
        // sortOrder: "asc",                   //排序方式
    });
    $table.bootstrapTable('hideColumn', 'puid');
    $table.bootstrapTable('hideColumn', '_table');
}

$(document).ready(
    // $("#query").click(function () {
    (loadData()),
    //手动刷新按钮
    $("#refresh").click(function () {
        $('#dataTable').bootstrapTable('refresh');
    }),
    //手动刷新按钮
    $("#query").click(function () {
        loadData();
    })
);