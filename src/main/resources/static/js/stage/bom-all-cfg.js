$(document).ready(
    $("#myButton").click(function () {
        var data = $("#text1").val();
        if (data.length == 0) {
            $("#myModal").modal('show');
            return;
        }

        $.ajax({
            url: 'loadBom/loadCfg0BomLineOfModel',
            type: 'GET',
            data: {
                'bdf': data
            },
            success: function (data) {
                var _data = JSON.stringify(data);
                var _ddd = JSON.parse(_data);
                var dddx = _ddd.data;
                var dddModel = _ddd.model;
                var b = 1;
                var c = "<table>" +
                    "<tr>" +
                    // "<td width='100px'  align='center'><input type='checkbox'></td>"+
                    "<th width='100px'>销售区域</th>" +
                    "<th width='100px'>品牌</th>" +
                    "<th width='100px'>车型年份</th>" +
                    "<th width='100px'>车型物料号</th>" +
                    "<th width='100px'>配置级别</th>" +
                    "<th width='100px'>驾驶位置</th>" +
                    "<th width='100px'>乘员数</th>" +
                    "<th width='100px'>车身形式</th>" +
                    "</tr>"
                for (var i = 0; i < dddModel.length; i++) {
                    var dModel = dddModel[i];
                    var v0 = dModel.key
                    var v1 = dModel.hide

                    c = c + "<tr>" +
                        // "<td width='100px'  align='center'><input type='checkbox'></td>"+
                        "<input id='hidden" + i + "' type='hidden' value='" + v1 + "'>" +
                        "<td width='100px'></td>" +
                        "<td width='100px'></td>" +
                        "<td width='100px'></td>" +
                        "<td width='100px'></td>" +
                        "<td width='100px'><a href='javascript:void(0);' onclick='Botton(" + i + ")'>" + v0 + "</a></td>" +
                        "<td width='100px'></td>" +
                        "<td width='100px'></td>" +
                        "<td width='100px'></td>" +
                        "</tr>"
                }
                var c = c + "</table>"
                var $rable = $("#cfg1Table");
                $rable.html(c);
                var r, re;
                var s = cfg1Table.outerHTML;
                re = /<table(.[^>]*)>/i;
                r = s.match(re)[0].replace(" id=", " oldid=");
                var tablehtml = r
                for (var i = 0; i < cfg1Table.rows[0].cells.length; i++) {
                    tablehtml += "<tr>"
                    for (var k = 0; k < cfg1Table.rows.length; k++) {
                        tablehtml += cfg1Table.rows[k].cells[i].outerHTML
                    }
                    tablehtml += "</tr>"
                }
                tablehtml += "</table>"
                newtable.innerHTML = tablehtml;
                //下面这一句是让JS执行时隐藏原来的表格内容，达到新表格在原来的表格位置刷出来的效果。
                document.getElementById("cfg1Table").style.display = "none";
                var d = "<table>" +
                    "<tr>" +
                    "<th align='center'><input type='checkbox'></th>" +
                    "<th>序号</th>" +
                    "<th>系统</th>" +
                    "<th>总成零件号</th>" +
                    "<th>总成零件名称</th>" +
                    "<th>总成英文名称</th>" +
                    "<th>配置描述</th>" +
                    "<th>配置代码</th>" +
                    "<th width='100px'>备注</th>" +
                    "<th width='100px'></th>" +
                    "<th width='100px'></th>" +
                    "<th width='100px'></th>" +
                    "</tr>";
                for (var i = 0; i < dddx.length; i++) {
                    var dataOfModel = dddx[i];
                    var _v0 = dataOfModel.系统;
                    var _v1 = dataOfModel.总成零件号;
                    var _v2 = dataOfModel.总成零件名称;
                    var _v3 = dataOfModel.总成英文名称;
                    var _v4 = "配置(描述)";
                    var _v5 = dataOfModel.配置代码;
                    var _v6 = dataOfModel.备注;
                    var _v7 = dataOfModel.低续航豪华型;
                    var _v8 = dataOfModel.高续航舒适型;
                    var _v9 = dataOfModel.高续航豪华型;
                    d = d + "<tr>" +
                        "<td><input type='checkbox'></td>" +
                        "<td>" + b++ + "</td>" +
                        "<td>" + _v0 + "</td>" +
                        "<td>" + _v1 + "</td>" +
                        "<td>" + _v2 + "</td>" +
                        "<td>" + _v3 + "</td>" +
                        "<td>" + _v4 + "</td>" +
                        "<td>" + _v5 + "</td>" +
                        "<td width='100px'>" + _v6 + "</td>" +
                        "<td width='100px'>" + _v7 + "</td>" +
                        "<td width='100px'>" + _v8 + "</td>" +
                        "<td width='100px'>" + _v9 + "</td>" +
                        "</tr>";
                }
                var d = d + "</table>"
                var $rable = $("#cfg0Table");
                $rable.html(d);


            },

            error: function (info) {
                alert(info);
            }

        });
    })
)

function addToTable($table, result, myColumns) {
    if (firstLoad) {
        $table.cfg0Table({
            // striped: true,
            data: result,
            columns: myColumns,
            idField: 'id',
            parentIdField: 'pid',
            treeShowField: 'object_string',
            // striped: true,
            // showToggle: true,                    //是否显示详细视图和列表视图的切换按钮
            // showColumns: true,                  //是否显示所有的列
            // showRefresh: true,                  //是否显示刷新按钮
            // search: true,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
            // pagination: isPagination,                   //是否显示分页（*）
            // sortable: true,                     //是否启用排序
            // sortOrder: "asc",                   //排序方式
            // clickToSelect: true  //点击表格项即可选择
        });
    }
    else {
        //重新载入数据，清空之前的，用load，用refresh不成功
        $table.cfg0Table("load", result);
    }
    toTree($table, "collapsed");
    firstLoad = false;


}


function Botton(index) {

    var id = $("#hidden" + index).val();
    // alert(id);
    var pModelPuid = "046adedc-09b2-43ca-a49c-a99d47c9fa3e";
    window.Ewin.dialog({
        url: "model/modModel?pModelPuid=" + id,
        title: "修改",
        width: 500,
        height: 400,
        gridId: "addPageofModel"
    })
    // $.ajax({
    // 	url:'./colorSet/addPageOfModel',
    // 	type:'GET',
    // 	success:function(data){
    // 		alert(data);
    // 	}
    // })
//	window.location.href='update.html?backurl='+window.location.href;  
}

