$(document).ready((function () {
    var projectPuid = $("#project", window.top.document).val();
    var eplUrl = "epl/record?projectId="+projectPuid;
    initTable(eplUrl);
   /* $("#eplTable").bootstrapTable('hideColumn', 'puid');
    $("#eplTable").bootstrapTable('hideColumn', 'rank');
    $("#eplTable").bootstrapTable('hideColumn', 'groupNum');*/
}))


function doQuery(){
    //$('#eplTable').bootstrapTable('refresh');    //刷新表格
    var projectPuid = $("#project", window.top.document).val();
    var eplUrl = "epl/record?projectId="+projectPuid;
    var level = $("#level").val();
    eplUrl+="&level="+level;
    var pBomOfWhichDept = $("#pBomOfWhichDept").val();
    eplUrl+="&pBomOfWhichDept="+pBomOfWhichDept;
    var lineId = $("#lineId").val();
    eplUrl += "&lineId="+lineId;
    initTable(eplUrl);
    $('#eplTable').bootstrapTable('destroy');
    console.log("有搜索框的参数是："+eplUrl)
}

function initTable(eplUrl){
    var projectPuid = $("#project", window.top.document).val();
    var $table = $("#eplTable");
    var column = [];
    //var eplUrl = "epl/record?projectId="+projectPuid;
    var eplTitleUrl = "epl/title?projectId="+projectPuid;
    $.ajax({
        url:eplTitleUrl,
        type: "GET",
        success: function (result) {
            console.log(result)
            var column = [];
            column.push({field: 'puid', title: 'Puid'});
            var data = result.data;
            var nameZh =data[0];
            var nameEn = data[1];
            console.log(data);
            var myData=JSON.stringify({
                "level": $("#level").val(),
                "lineId": $("#lineId").val(),
                "pBomOfWhichDept": $("#pBomOfWhichDept").val(),
            });
            var keys = [];
            var values;
            for (var key in nameEn) {
                if (nameEn.hasOwnProperty(key)) {
                    var json = {
                        field: nameEn[key],
                        title: nameZh[key],
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
                //data:myData,
                height: $(window.parent.document).find("#wrapper").height() - 180,
                width: $(window).width(),
                formId :"queryEplManage",
                undefinedText: "",//当数据为 undefined 时显示的字符
                pagination:true,
                pageNumber:1,                       //初始化加载第一页，默认第一页
                pageSize: 20,                       //每页的记录行数（*）
                pageList: [20, 50,100,200],        //可供选择的每页的行数（*）
                //queryParams:queryParam,
                //uniqueId: "puid",                     //每一行的唯一标识，一般为主键列
                showExport: true,
                exportDataType: 'all',
                //responseHandler: responseHandler,
                columns: column,
                sortable: true,                     //是否启用排序
                sortOrder: "asc",                   //排序方式
                striped: true, //是否显示行间隔色
                search: false, //是否显示表格搜索，此搜索是客户端搜索，不会进服务端
                showColumns: true, //是否显示所有的列
                /*fixedColumns: true,
                fixedNumber:1,*/
                showToggle: false,                   //是否显示详细视图和列表视图的切换按钮
                showRefresh: true,                  //是否显示刷新按钮
                //minimumCountColumns:4
            });
            $table.bootstrapTable('hideColumn', 'puid');
            $table.bootstrapTable('hideColumn', 'rank');
            $table.bootstrapTable('hideColumn', 'groupNum');
        }
    })

}