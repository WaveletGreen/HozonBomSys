function loadData() {
    var project = $("#project", window.top.document);
    var data = project.val();
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
            if (data == null) {
                alert("查无数据，请联系项目经理或管理员");
                return;
            }
            var _data = JSON.stringify(data);
            var _ddd = JSON.parse(_data);
            var dddx = _ddd.data;
            var dddModel = _ddd.model;
            var b = 1;
            var c = "<table>" +
                "<tr>" +
                // "<td width='100px'  align='center'><input type='checkbox'></td>"+
                "<th width='100px'>品牌</th>" +
                "<th width='100px'>平台</th>" +
                "<th width='100px'>车型</th>" +
                "<th width='100px'>版型</th>" +
                "<th width='100px'>车身形式</th>" +
                "<th width='100px'>公告</th>" +
                "<th width='100px'>配置描述</th>" +
                "<th width='100px'>配置管理</th>" +
                "</tr>"
            for (var i = 0; i < dddModel.length; i++) {
                var dModel = dddModel[i];
                var v0 = dModel.key
                var v1 = dModel.hide

                c = c + "<tr>" +
                    // "<td width='100px'  align='center'><input type='checkbox'></td>"+
                    "<input id='hidden" + i + "' type='hidden' value='" + v1 + "'>" +
                    //品牌
                    "<td width='100px'>" + dModel.brand + "</td>" +
                    //平台
                    "<td width='100px'>" + dModel.platform + "</td>" +
                    //车型
                    "<td width='100px'>" + dModel.vehicle + "</td>" +
                    "<td width='120px'><a href='javascript:void(0);' onclick='Botton(\"" + v1 + "\")'>" + v0 + "</a></td>" +
                    "<td width='100px'>" + dModel.pModelShape + "</td>" +
                    "<td width='100px'>" + dModel.pModelAnnouncement + "</td>" +
                    "<td width='100px'>" + dModel.pModelCfgDesc + "</td>" +
                    "<td width='100px'>" + dModel.pModelCfgMng + "</td>" +
                    "</tr>"
            }
            var c = c + "</table>"
            var $rable = $("#cfg1Table");
            // $rable.empty();
            $rable.html(c);
            $("#cfg1Table").removeClass("hidden");

            var r, re;
            var s = $("#cfg1Table").prop("outerHTML");
            /*cfg1Table.outerHTML;*/
            re = /<table(.[^>]*)>/i;
            r = s.match(re)[0].replace(" id=", " oldid=");
            var tablehtml = r;
            for (var i = 0; i < cfg1Table.rows[0].cells.length; i++) {
                tablehtml += "<tr>"
                for (var k = 0; k < cfg1Table.rows.length; k++) {
                    tablehtml += cfg1Table.rows[k].cells[i].outerHTML
                }
                tablehtml += "</wtr>"
            }
            tablehtml += "</table>";
            newtable.innerHTML = "";
            newtable.innerHTML = tablehtml;
            //下面这一句是让JS执行时隐藏原来的表格内容，达到新表格在原来的表格位置刷出来的效果。
            // document.getElementById("cfg1Table").style.display = "none";

            $("#cfg1Table").addClass("hidden");
            //清空原来的table
            $("#cfg1Table tbody").html("");
            // $("#cfg1Table tr").html("");

            //动态添加
            var d = "<table>" +
                "<tr>" +
                "<th width='50px' align='center'><input type='checkbox'></th>" +
                "<th width='50px'>序号</th>" +
                "<th width='100px'>操作类型</th>" +
                "<th width='100px'>系统</th>" +
                "<th width='200px'>总成零件号</th>" +
                "<th width='180px'>总成零件名称</th>" +
                "<th width='200px'>总成英文名称</th>" +
                //描述的宽度应该大一点，保持可用
                "<th width='200px'>配置描述</th>" +
                "<th width='100px'>配置代码</th>" +
                "<th width='100px'>备注</th>";
            if (dddModel.length > 0)
                for (var i = 0; i < dddModel.length; i++) {
                    //为保持与车型对齐，设置的宽度与车型的单元格宽度一致
                    d = d + "<th width='120px'> </th>";
                }
            d = d + "</tr>";
            //动态拼接后台数据
            for (var i = 0; i < dddx.length; i++) {
                var dataOfModel = dddx[i];
                d = d + "<tr>" +
                    "<td><input type='checkbox'></td>"
                    +
                    "<td>" + b++ + "</td>";
                for (var index in dataOfModel) {
                    d = d + "<td>" + dataOfModel[index] + "</td>";
                }
                d = d + "</tr>";
            }
            var d = d + "</table>";
            var $rable = $("#cfg0Table");
            //清空内容，再填充
            $rable.html("");
            $rable.html(d);
        },
        error: function (info) {
            alert(info);
        }
    });
}

$(document).ready(
    (loadData()),
    $("#myButton").click(function () {
        loadData();
    })
)

function Botton(id) {
    window.Ewin.dialog({
        url: "model/modModel?pModelPuid=" + id,
        title: "修改",
        width: 500,
        height: 400,
        gridId: "addPageOfModel"
    })
}
