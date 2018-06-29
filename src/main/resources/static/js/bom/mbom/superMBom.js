var count = 1;
$(document).ready((function () {
    var projectPuid = $("#project", window.top.document).val();
    var url = "mbom/super/record?projectId="+projectPuid;
    initTable(url);
}))
function doQuery() {
    //$('#superMBomTable').bootstrapTable('refresh');    //刷新表格
    var projectPuid = $("#project", window.top.document).val();
    var url = "mbom/super/record?projectId="+projectPuid;
    var level = $("#level").val();
    url+="&level="+level;
    var pBomOfWhichDept = $("#pBomOfWhichDept").val();
    url+="&pBomOfWhichDept="+pBomOfWhichDept;
    var lineId = $("#lineId").val();
    url += "&lineId="+lineId;
    var cfg0ModelRecordId = $("#objectName").val();
    if (cfg0ModelRecordId=="请选择车型名称") {
        url += "&cfg0ModelRecordId="+"";
    }
    else {
        url += "&cfg0ModelRecordId="+cfg0ModelRecordId;
    }

    initTable(url);
    $('#superMBomTable').bootstrapTable('destroy');
    console.log("有搜索框的参数是："+url)
    count++;
}

function initTable(url) {
    console.log(url)
    var projectPuid = $("#project", window.top.document).val();
    //var url = "mbom/super/record?projectId="+projectPuid;
    var $table = $("#superMBomTable");
    var column = [];
    $.ajax({
        url: "mbom/superMbomTitle?projectId="+projectPuid,
        type: "GET",
        success: function (result) {
            var column = [];
            // column.push({field: 'Puid', title: 'puid'});
            // column.push({field: 'ck', checkbox: true, Width: 50});
            /*column.push({field: '',
                title: '序号',
                formatter: function (value, row, index) {
                    return index+1;},
                align:
                    'center',
                valign:
                    'middle'
            });*/

            var _model = result.model;

            if(count<2){
                $("#objectName").append("<option>请选择车型名称</option>");
                for(var i = 0 ; i < _model.length; i++) {
                    $("#objectName").append("<option value='" + _model[i].cfg0ModelRecordId + "'>" + _model[i].objectName + "</option>");
                }
            }
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

                url: url,
                method: 'GET',
                dataType: 'json',
                cache: false,
                striped: true,                              //是否显示行间隔色
                //sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
                height: $(window.parent.document).find("#wrapper").height() - 180,
                width: $(window).width(),
                formId :"querySuperMBom",
                undefinedText: "",//当数据为 undefined 时显示的字符
                pagination: true,
                //pageNumber:1,                       //初始化加载第一页，默认第一页
                pageSize: 20,                       //每页的记录行数（*）
                //pageList: [20, 50,100,200],        //可供选择的每页的行数（*）
                uniqueId: "puid",                     //每一行的唯一标识，一般为主键列
                showExport: true,
                // responseHandler:responseHandler,
                //exportDataType: 'all',
                columns: column,
                sortable: true,                     //是否启用排序
                sortOrder: "asc",                   //排序方式
                //clickToSelect: true,// 单击某一行的时候选中某一条记录
                striped: true, //是否显示行间隔色
                //search: true, //是否显示表格搜索，此搜索是客户端搜索，不会进服务端
                showColumns: true, //是否显示所有的列
                /*fixedColumns: true,
                fixedNumber:1,*/
                showToggle: false,                   //是否显示详细视图和列表视图的切换按钮
                showRefresh: true,                  //是否显示刷新按钮
                //minimumCountColumns: 4,


            });
            function responseHandler(result){
                var model = result.model;
                var data = result.result;
                console.log(data);
                console.log(model)
                //如果没有错误则返回数据，渲染表格
                return {
                    model:result.model
                }
            }
            // $table.bootstrapTable('hideColumn', 'Puid');
        }

    })
}
