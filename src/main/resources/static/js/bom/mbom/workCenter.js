$(document).ready((function (){
        var  $table =  $("#workCenterTable");
        var  column = [];
        $.ajax({
            url:"mbom/workCenterTitel",
            type:"GET",
            success:function(result){
                var column = [];
                column.push({field: 'Puid', title: 'puid'});
                // column.push({field: 'ck', checkbox: true, Width: 50});
                column.push({field: '',
                    title: '序号',
                    formatter: function (value, row, index) {
                        return index+1;},
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
                };
                $table.bootstrapTable({
                    url: "",
                    method: 'get',
                    height: $(window.parent.document).find("#wrapper").height(),
                    width: $(window).width(),
                    showToggle: true,                   //是否显示详细视图和列表视图的切换按钮
                    showRefresh: true,                  //是否显示刷新按钮
                    // pageSize: 10,
                    pagination: false,                   //是否显示分页（*）
                    clickToSelect: true,                // 单击某一行的时候选中某一条记录
                    formId: "formId",
                    /**列信息，需要预先定义好*/
                    columns: column,
                    sortable: true,                     //是否启用排序
                    sortOrder: "asc",                   //排序方式
                });
                $table.bootstrapTable('hideColumn', 'Puid');
            }
        })
    })
)