//已废除 本js文件已作废
// $(document).ready((function () {
//         var $table = $("#eplTable");
//         var column = [];
//         $.ajax({
//             url: "epl/title",
//             type: "GET",
//             success: function (result) {
//                 var column = [];
//                 column.push({field: 'puid', title: 'Puid'});
//                 // column.push({field: 'ck', checkbox: true, Width: 50});
//                 column.push({
//                     field: '',
//                     title: '序号',
//                     formatter: function (value, row, index) {
//                         return index + 1;
//                     },
//                     align:
//                         'center',
//                     valign:
//                         'middle'
//                 });
//                 var data = result.data;
//                 var keys = [];
//                 var values;
//                 for (var key in data) {
//                     if (data.hasOwnProperty(key)) {
//                         var json = {
//                             field: key,
//                             title: data[key],
//                             align:
//                                 'center',
//                             valign:
//                                 'middle'
//                         };
//                         column.push(json);
//                     }
//                 }
//                 ;
//                 $table.bootstrapTable({
//                     url: "epl/record",
//                     method: 'get',
//                     height: $(window.parent.document).find("#wrapper").height(),
//                     width: $(window).width(),
//                     showToggle: true,                   //是否显示详细视图和列表视图的切换按钮
//                     showRefresh: true,                  //是否显示刷新按钮
//                     pagination: true,                   //是否显示分页（*）
//                     pageSize:50,
//                     pageNumber:1,
//                     pageList : [ 10, 25, 50, 100,200,300,500, ], //可供选择的每页的行数（*）
//                     sidePagination : "server", //分页方式：client客户端分页，server服务端分页（*）
//                     queryParamsType : '',
//                     queryParams : $table.queryParams,//传递参数（*）
//                     clickToSelect: true,                // 单击某一行的时候选中某一条记录
//                     formId: "formId",
//                     /**列信息，需要预先定义好*/
//                     columns: column,
//                     sortable: true,                     //是否启用排序
//                     sortOrder: "asc",                   //排序方式
//                     striped: true, //是否显示行间隔色
//                     search: true, //是否显示表格搜索，此搜索是客户端搜索，不会进服务端
//                     showColumns: true, //是否显示所有的列
//                     fixedColumns: true,
//                     fixedNumber: 1 ,//固定列数
//                     minimumCountColumns: 4,//设置最小列数
//                 });
//                 $table.bootstrapTable('hideColumn', 'puid');
//
//                 $table .queryParams = function(params) {
//
//                     var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
//                         pageSize : params.pageSize, //页面大小
//                         pageNum : params.pageNumber,//页码
//                     };
//                     return temp;
//                 };
//                 return $table ;
//             }
//         })
//     })
// )


/*
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
                    url: "epl/record",
                    method: 'get',
                    height: $(window.parent.document).find("#wrapper").height(),
                    width: $(window).width(),
                    showToggle: true,                   //是否显示详细视图和列表视图的切换按钮
                    showRefresh: true,                  //是否显示刷新按钮
                    pagination: true,                   //是否显示分页（*）
                    pageSize:50,
                    pageNumber:1,
                    pageList : [ 10, 25, 50, 100,200,300,500, ], //可供选择的每页的行数（*）
                    paginationPreText: '‹',//指定分页条中上一页按钮的图标或文字,这里是<
                    paginationNextText: '›',//指定分页条中下一页按钮的图标或文字,这里是>
                    sidePagination : "server", //分页方式：client客户端分页，server服务端分页（*）
                    queryParamsType : '',
                    queryParams: function (params) {//自定义参数，这里的参数是传给后台的，我这是是分页用的
                        return {//这里的params是table提供的
                            pageNum: params.offset,//从数据库第几条记录开始
                            pageSize: params.limit//找多少条
                        };
                    },
                    clickToSelect: true,                // 单击某一行的时候选中某一条记录
                    formId: "formId",
                    /!**列信息，需要预先定义好*!/
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
}))*/


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
                field: 'No',
                title: '序号',
                // formatter: function (value, row, index) {
                //     return index + 1;
                // },
                align:
                    'center',
                valign:
                    'middle'
            });
            var data = result.data;
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
                url:"epl/record",
                method: 'get',
                height: $(window.parent.document).find("#wrapper").height() - 90,
                width: $(window).width(),
                undefinedText: "",//当数据为 undefined 时显示的字符
                showToggle: false,                   //是否显示详细视图和列表视图的切换按钮
                showRefresh: true,                  //是否显示刷新按钮
                pagination: true,                   //是否显示分页（*）
                pageSize:20,
                pageNumber:1,
                pageList: ['ALL',20,50,100,200,500,1000],        //可供选择的每页的行数（*）
                sidePagination : "server", //分页方式：client客户端分页，server服务端分页（*）
                clickToSelect: true,                // 单击某一行的时候选中某一条记录
                showExport: true,
                formId: "formId",
                /**列信息，需要预先定义好*/
                columns: column,
                sortable: true,                     //是否启用排序
                sortOrder: "asc",                   //排序方式
                striped: true, //是否显示行间隔色
                search: true, //是否显示表格搜索，此搜索是客户端搜索，不会进服务端
                showColumns: true, //是否显示所有的列
                /*fixedColumns: true,
                fixedNumber: 1 ,//固定列数*/
                minimumCountColumns: 4,//设置最小列数
            });
            // $table.bootstrapTable('hideColumn', 'puid');
            // $table.bootstrapTable('hideColumn', 'level');
            // $table.bootstrapTable('hideColumn', 'groupNum');
        }
    })
}))
