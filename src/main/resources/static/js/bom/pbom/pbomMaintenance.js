var firstLoad = true;
window.onload=function(){
    var projectPuid = $("#project", window.top.document).val();
        var $table = $("#pbomMaintenanceTable");
        var column = [];
        $.ajax({
            url: "mbom/manage/title?projectId="+projectPuid,
            type: "GET",
            success: function (result) {
                var column = [];
                column.push({field: 'pBomPuid', title: 'puid'});
                // column.push({field: 'ck', checkbox: true, Width: 50});
                // column.push({field: '',
                //     title: '序号',
                //     formatter: function (value, row, index) {
                //         return index+1;},
                //     align:
                //         'center',
                //     valign:
                //         'middle'
                // });
                var data = result.data;
                console.log(data);
                var keys = [];
                var values;
                for (var key in data) {
                    if (data.hasOwnProperty(key)) {
                        // keys.push(key);
                        var json = {
                           field: key,
                            title: data[key],
                            align:
                                'center',
                            valign:
                                'middle'
                        };
                        column.push(json);
                    }
                }
                $table.bootstrapTable({
                    url: "mbom/record?projectId="+projectPuid,
                    method:'GET',
                    dataType:'json',
                    cache: false,
                    striped: true,                              //是否显示行间隔色
                    sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
                    height: $(window.parent.document).find("#wrapper").height(),
                    width: $(window).width(),
                    pagination:true,
                    pageNumber:1,                       //初始化加载第一页，默认第一页
                    pageSize: 20,                       //每页的记录行数（*）
                    pageList: [20, 50,100,200],        //可供选择的每页的行数（*）
                    uniqueId: "puid",                     //每一行的唯一标识，一般为主键列
                    showExport: true,
                    exportDataType: 'all',
                    columns: column,
                    sortable: true,                     //是否启用排序
                    sortOrder: "asc",                   //排序方式
                    striped: true, //是否显示行间隔色
                    search: true, //是否显示表格搜索，此搜索是客户端搜索，不会进服务端
                    showColumns: true, //是否显示所有的列
                    fixedColumns: true,
                    fixedNumber:1,
                    showToggle: true,                   //是否显示详细视图和列表视图的切换按钮
                    showRefresh: true,                  //是否显示刷新按钮
                    minimumCountColumns:4
                });
                 $table.bootstrapTable('hideColumn', 'pBomPuid');
            }
        });


    /*$("#queryBtn").click(function () {
        var myData = JSON.stringify({
            "level": $("#level").val(),
            "pBomOfWhichDept": $("#pBomOfWhichDept").val(),
            "lineId": $("#lineId").val(),
        });
        $.ajax({
            contentType: "application/json",
            type:"GET",
            url:"pbom/searchMaintain/detail",
            data:myData,
            success:
            function (data) {
                var result = data.data;
                console.log(result);
            },
            error:function (info) {
                alert(info);
            }
        })
    })*/
}