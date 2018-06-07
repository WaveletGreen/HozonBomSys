$(document).ready((function () {
        var $table = $("#eplTable");
        var column = [];
        $.ajax({
            url: "epl/title",
            type: "GET",
            success: function (result) {
                var column = [];
                column.push({field: 'puid', title: 'Puid'});
                // column.push({field: 'ck', checkbox: true, Width: 50});
                column.push({
                    field: '',
                    title: '序号',
                    formatter: function (value, row, index) {
                        return index + 1;
                    },
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
                ;
                $table.bootstrapTable({
                    url: "epl/record",
                    method: 'get',
                    height: $(window.parent.document).find("#wrapper").height(),
                    width: $(window).width(),
                    showToggle: true,                   //是否显示详细视图和列表视图的切换按钮
                    showRefresh: true,                  //是否显示刷新按钮
                    pagination: false,                   //是否显示分页（*）
                    clickToSelect: true,                // 单击某一行的时候选中某一条记录
                    formId: "formId",
                    /**列信息，需要预先定义好*/
                    columns: column,
                    sortable: true,                     //是否启用排序
                    sortOrder: "asc",                   //排序方式
                    striped: true, //是否显示行间隔色
                    search: true, //是否显示表格搜索，此搜索是客户端搜索，不会进服务端
                    showColumns: true, //是否显示所有的列
                    fixedColumns: true,
                    fixedNumber: 1 ,//固定列数
                    minimumCountColumns: 4,//设置最小列数
                });
                $table.bootstrapTable('hideColumn', 'puid');
            }
        })
    })
)
