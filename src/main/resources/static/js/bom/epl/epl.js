$(document).ready((function () {
    initTable();
}))


function doQuery(params){
    $('#eplTable').bootstrapTable('refresh');    //刷新表格
}

function initTable(){
    var $table = $("#eplTable");
    var column = [];
    var eplUrl = "epl/record";
    var eplTitleUrl = "epl/title";
    $.ajax({
        url:eplTitleUrl,
        type: "GET",
        success: function (result) {
            var column = [];
            column.push({field: 'puid', title: 'Puid'});
            column.push({
                field: 'No',
                title: '序号',
                // formatter: function (pageNum, pageSize) {
                //     var i = (pageNum-1)*pageSize+1;
                //     return i++;
                // },
                align:
                    'center',
                valign:
                    'middle'
            });
            var data = result.data;
            console.log(data);
            var keys = [];
            var values;
            for (var key in data) {
                if (data.hasOwnProperty(key)) {
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
            $('#eplTable').bootstrapTable({
                method:'GET',
                dataType:'json',
                cache: false,
                striped: true,                              //是否显示行间隔色
                sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
                url:eplUrl,
                height: $(window.parent.document).find("#wrapper").height(),
                width: $(window).width(),
                pagination:true,
                pageNumber:1,                       //初始化加载第一页，默认第一页
                pageSize: 20,                       //每页的记录行数（*）
                pageList: [20, 50,100,200],        //可供选择的每页的行数（*）
                uniqueId: "puid",                     //每一行的唯一标识，一般为主键列
                showExport: true,
                exportDataType: 'all',
                responseHandler: responseHandler,
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
            $table.bootstrapTable('hideColumn', 'puid');
        }
    })

}

function responseHandler(res) {
    if (res) {
        return {
            "rows" : res.result,
            "total" : res.totalCount
        };
    } else {
        return {
            "rows" : [],
            "total" : 0
        };
    }
}
