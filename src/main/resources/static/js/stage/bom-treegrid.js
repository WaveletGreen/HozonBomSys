var firstLoad = true;
var isExpand = false;
$(document).ready(
    // $("#doSubmit2").click(
    (
        function () {
            var $table = $("#dataTable");
            var project = $("#project", window.top.document);
            var projectPuid = project.val();
            if (projectPuid.length <= 0) {
                $("#myModal").modal('show');
                return;
            }
            $.ajax({
                type: "POST",
                url: "./loadBom/loadColumns",
                data: {
                    "projectPuid": projectPuid
                },
                success: function (result) {
                    var toJson = JSON.stringify(result);
                    var thisResult = JSON.parse(toJson);
                    var orgName = thisResult[0];
                    var localName = thisResult[1];
                    var myColumns = [];
                    // var check = {
                    //     field: 'ck',
                    //     checkbox: false
                    // }
                    // myColumns.push(check);
                    for (var i = 0; i < localName.length; i++) {
                        var temp = {
                            field: orgName[i],
                            title: localName[i]
                        };
                        myColumns.push(temp);
                    }
                    $table.bootstrapTable('destroy');
                    // var data = thisResult.slice(2, thisResult.length);
                    addToTable($table, projectPuid, myColumns);
                },
                error: function (result) {
                    var xx = JSON.stringify(result);
                    var yy = JSON.parse(xx);
                    alert("错误" + yy);
                }
            });
        }
    ),
    //展开
    $("#treeExecutor").click(function () {
        var $table = $("#dataTable");
        if (isExpand) {
            toTree($table, "collapsed");
            $("#treeExecutor").text("展开树形结构");
        }
        else {
            toTree($table, "expanded");
            $("#treeExecutor").text("折叠树形结构");
        }
        isExpand = !isExpand;
    })
//     $('#switch1').bootstrapSwitch({    //初始化按钮
//             onText: "ON",
//             offText: "OFF",
//             onColor: "success",
//             offColor: "info",
//             size: "small",
//             onSwitchChange: function (event, state) {
//                 if (state == true) {
//                     $(this).val("on");
//                 } else {
//                     $(this).val("off");
//                 }
//             }
//         }
//     )
)

function addToTable($table, projectPuid, myColumns) {
    $table.bootstrapTable({
        striped: true,
        url: "loadBom/loadByID?projectPuid=" + projectPuid,
        method: "POST",
        columns: myColumns,
        idField: 'id',
        parentIdField: 'pid',
        treeShowField: 'object_string',
        height: $(window.parent.document).find("#wrapper").height() - document.body.offsetHeight - 45,
        width: $(window).width(),
        striped: true,
        showToggle: true,                    //是否显示详细视图和列表视图的切换按钮
        showColumns: true,                  //是否显示所有的列
        showRefresh: true,                  //是否显示刷新按钮
        search: true,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
        // pagination: isPagination,                   //是否显示分页（*）
        // sortable: true,                     //是否启用排序
        // sortOrder: "asc",                   //排序方式
        clickToSelect: true  //点击表格项即可选择
    });
    toTree($table, "expanded");
    firstLoad = false;

}

function toTree($table, isExpand) {
    $table.treegrid({
        initialState: isExpand,//收缩collapsed展开expanded
        treeColumn: 0,//指明第几列数据改为树形is-expander-expandedis-expander-collapsedglyphicon-triangle-bottom glyphicon glyphicon-triangle-right
        expanderExpandedClass: 'glyphicon glyphicon-minus',//有关于折叠图标的问题，由于字体集文件无法跨域调用，因此需要将fonts文件夹中的文件放到与bootstrap.xxx.css统计目录下，并修改@font-face的字体集路径
        expanderCollapsedClass: 'glyphicon glyphicon-plus',
        onChange: function () {
            $table.bootstrapTable('resetWidth');
        }
    });
}