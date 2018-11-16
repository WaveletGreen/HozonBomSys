
$(document).ready((function () {
    var projectPuid = $("#project", window.top.document).val();
    var eBomUrl = "data";
    console.log(eBomUrl)
    initTable(eBomUrl);
}))


function initTable(eBomUrl) {
    var projectPuid = $("#project", window.top.document).val();
    if (!checkIsSelectProject(projectPuid)) {
        return;
    }
    //var eBomUrl ="ebom/getEBom/list?projectId=" + projectPuid
    var $table = $("#ebom_table");
    var column = [];
    $.ajax({
        url: "title",
        type: "GET",
        success: function (result) {
            var data = result.data;
            for (var key in data) {
                if (data.hasOwnProperty(key)) {
                    var json = {
                        field: key,
                        title: data[key],
                        // align: 'center',
                        valign: 'middle',
                    };
                    column.push(json);
                }
            }
            $table.bootstrapTable({
                url: eBomUrl,
                method: 'GET',
                dataType: 'json',
                //cache: false,
                // striped: true,                              //是否显示行间隔色
                height: $(window.parent.document).find("#wrapper").height() - 150,
                width: $(window).width(),
                formId: 'ebomChangeData',
                undefinedText: "",//当数据为 undefined 时显示的字符
                pagination: true,
                //showExport: true,
                //exportDataType: 'all',
                columns: column,
                toolbar: "#toolbar",
                //sortName: 'id',
                // sortable: true,                     //是否启用排序
                sortOrder: "asc",                   //排序方式
                clickToSelect: true,// 单击某一行的时候选中某一条记录
                //search: true, //是否显示表格搜索，此搜索是客户端搜索，不会进服务端
                showColumns: true, //是否显示所有的列
                // resizable:true,
                // fixedColumns: true,
                // fixedNumber:3,
                showToggle: false,                   //是否显示详细视图和列表视图的切换按钮
                showRefresh: true,                  //是否显示刷新按钮
                //minimumCountColumns:4,
                toolbars: [
                ],
            });

        }
    });
}