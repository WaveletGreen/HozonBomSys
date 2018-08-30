var modelSize;
var peculiarity;
function loadData() {
    var project = $("#project", window.top.document);
    var data = project.val();
    if (0 == data.length) {
        $("#myModal").modal('show');
        return;
    }
    $.ajax({
        url: 'bomAllCfg/loadCfg0BomLineOfModel',
        type: 'GET',
        data: {
            'bdf': data
        },
        success: function (myData) {
            if (myData == null) {
                alert("查无数据，请联系项目经理或管理员");
                return;
            }
            var _data = JSON.stringify(myData);
            var _ddd = JSON.parse(_data);
            var data = _ddd.data;
            // var model = _ddd.model;
            // modelSize = _ddd.modelSize;
            // peculiarity = _ddd.peculiarity;
            var $table = $("#cfg0Table");
            //清空
            $table.html("");
            $table.removeClass("table").removeClass("table-striped");
            // $table.val("");
            var temp = "";
            for (var i = 0; i < 8; i++) {
                if (i == 0) {
                    temp += "<tr id='tr" + i + "'><td id='row" + i + "' colspan='10' style='border: #fff'>阶段</td></tr>";
                }
                else if (i == 1) {
                    temp += "<tr id='tr" + i + "'><td id='row" + i + "' colspan='10' style='border: #fff'>版本</td></tr>";
                }
                else if (i == 2) {
                    temp += "<tr id='tr" + i + "'><td id='row" + i + "' colspan='10' style='border: #fff'>生效日期</td></tr>";
                }
                else {
                    temp += "<tr id='tr" + i + "'><td id='row" + i + "' colspan='10' style='border: #fff'></td></tr>";
                }
            }

            $table.append(temp);

            $("#tr0").append("<th id='th0'><div style='width: 200px'  >品牌</div></th>");
            $("#tr1").append("<th id='th1'><div style='width: 200px'  >平台</div></th>");
            $("#tr2").append("<th id='th2'><div style='width: 200px'  >车型</div></th>");
            $("#tr3").append("<th id='th3'><div style='width: 200px'  >版型</div></th>");
            $("#tr4").append("<th id='th4'><div style='width: 200px'  >车身形式</div></th>");
            $("#tr5").append("<th id='th5'><div style='width: 200px'  >公告</div></th>");
            $("#tr6").append("<th id='th6'><div style='width: 200px'  >配置描述</div></th>");
            $("#tr7").append("<th id='th7'><div style='width: 200px'  >配置管理</div></th>");

            // for (var i = 0; i < model.length; i++) {
            //     var modeli = model[i];
            //     var v0 = modeli.key;
            //     var v1 = modeli.hide;
            //     //品牌
            //     $("#tr0").append("<td ><div style='width: 200px'  >" + modeli.brand + "</div></td>");
            //     //平台
            //     $("#tr1").append("<td ><div style='width: 200px'  >" + modeli.platform + "</div></td>");
            //     //车型
            //     $("#tr2").append("<td ><div style='width: 200px'  >" + modeli.vehicle + "</div></td>");
            //     $("#tr3").append("<td ><div style='width: 200px'  ><a href='javascript:void(0);' onclick='Botton(\"" + v1 + "\")'>" + v0 + "</a></div></td>");
            //     $("#tr4").append("<td ><div style='width: 200px'  >" + modeli.pModelShape + "</div></td>");
            //     $("#tr5").append("<td ><div style='width: 200px'  >" + modeli.pModelAnnouncement + "</div></td>");
            //     $("#tr6").append("<td ><div style='width: 200px'  >" + modeli.pModelCfgDesc + "</div></td>");
            //     $("#tr7").append("<td ><div style='width: 200px'  >" + modeli.pModelCfgMng + "</div></td>");
            // }

            // var c = "<tr>" +
            //     // "<td width='100px'  align='center'><input type='checkbox'></td>"+
            //     "<th width='100px'>品牌</th>" +
            //     "<th width='100px'>平台</th>" +
            //     "<th width='100px'>车型</th>" +
            //     "<th width='100px'>版型</th>" +
            //     "<th width='100px'>车身形式</th>" +
            //     "<th width='100px'>公告</th>" +
            //     "<th width='100px'>配置描述</th>" +
            //     "<th width='100px'>配置管理</th>" +
            //     "</tr>";
            // for (var i = 0; i < model.length; i++) {
            //     var dModel = model[i];
            //     var v0 = dModel.key
            //     var v1 = dModel.hide
            //
            //     c = c + "<tr>" +
            //         // "<td width='100px'  align='center'><input type='checkbox'></td>"+
            //         "<input id='hidden" + i + "' type='hidden' value='" + v1 + "'>" +
            //         //品牌
            //         "<td width='100px'>" + dModel.brand + "</td>" +
            //         //平台
            //         "<td width='100px'>" + dModel.platform + "</td>" +
            //         //车型
            //         "<td width='100px'>" + dModel.vehicle + "</td>" +
            //         "<td width='120px'><a href='javascript:void(0);' onclick='Botton(\"" + v1 + "\")'>" + v0 + "</a></td>" +
            //         "<td width='100px'>" + dModel.pModelShape + "</td>" +
            //         "<td width='100px'>" + dModel.pModelAnnouncement + "</td>" +
            //         "<td width='100px'>" + dModel.pModelCfgDesc + "</td>" +
            //         "<td width='100px'>" + dModel.pModelCfgMng + "</td>" +
            //         "</tr>"
            // }
            // c = c + "</table>";
            // $rable.empty();
            // $rable.html(c);
            // $("#cfg1Table").removeClass("hidden");
            //
            // var r, re;
            // var s = $("#cfg1Table").prop("outerHTML");
            // /*cfg1Table.outerHTML;*/
            // re = /<table(.[^>]*)>/i;
            // r = s.match(re)[0].replace(" id=", " oldid=");
            // var tablehtml = r;
            // for (var i = 0; i < cfg1Table.rows[0].cells.length; i++) {
            //     tablehtml += "<tr>"
            //     for (var k = 0; k < cfg1Table.rows.length; k++) {
            //         tablehtml += cfg1Table.rows[k].cells[i].outerHTML
            //     }
            //     tablehtml += "</wtr>"
            // }
            // tablehtml += "</table>";
            // newtable.innerHTML = "";
            // newtable.innerHTML = tablehtml;
            // //下面这一句是让JS执行时隐藏原来的表格内容，达到新表格在原来的表格位置刷出来的效果。
            // // document.getElementById("cfg1Table").style.display = "none";
            //
            // $("#cfg1Table").addClass("hidden");
            // //清空原来的table
            // $("#cfg1Table tbody").html("");
            // // $("#cfg1Table tr").html("");

            //动态添加
            var t =
                "<tr>" +
                "<th align='center'><div style='width: 50px' >选择</div></th>" +
                "<th ><div style='width: 50px' >序号</div></th>" +
                "<th ><div style='width: 150px' >操作类型</div></th>" +
                "<th ><div style='width: 150px' >系统</div></th>" +
                "<th ><div style='width: 150px' >总成零件号</div></th>" +
                "<th ><div style='width: 150px' >总成零件名称</div></th>" +
                "<th ><div style='width: 150px' >总成英文名称</div></th>" +
                "<th ><div style='width: 150px' >责任工程师</div></th>" +
                //描述的宽度应该大一点，保持可用
                "<th ><div style='width: 150px' >配置描述</div></th>" +
                "<th ><div style='width: 150px' >配置代码</div></th>" +
                "<th ><div style='width: 150px' >备注</div></th>" +
                "</tr>"
            ;
            $table.append(t);
            //动态拼接后台数据
            for (var i = 0; i < data.length; i++) {
                var dataOfModel = data[i];
                var delta = "<tr>" +
                    "<td style='text-align: center'><input type='button' value='编辑' style='width: 50px' onclick='editorOrSave(this)'><input class='pCfg0ObjectId' type='text' value='"+dataOfModel.pCfg0ObjectId+"' hidden='hidden' disabled='disabled'></td>"
                    +
                    "<td><div style='width: 50px' >" + (i + 1) + "</div></td>";
                var aaa = 0;
                for (var index in dataOfModel) {
                    if(aaa<7){
                        delta = delta + "<td class='edit'><input type='text' value='"+dataOfModel[index]+"' style='display: none'><div style='width: 150px'>" + dataOfModel[index] + "</div></td>";
                    }else if(aaa>9){
                        delta = delta + "<td class='edit'><select style='display: none'><option>-</option><option>●</option><option>○</option></select><div style='width: 150px'>" + dataOfModel[index] + "</div></td>";
                    }else{
                        delta = delta + "<td class='edit'><div style='width: 150px'>" + dataOfModel[index] + "</div></td>";
                    }
                    aaa++;
                }
                delta = delta + "</tr>";
                $table.append(delta);
            }
            $table.addClass("table").addClass("table-striped");
        },
        error: function (info) {
            alert(info);
        }
    });
}


var projectPuid;
$(document).ready(
    (loadData()),
    $("#myButton").click(function () {
        loadData();
    }),
    $("#addVehicle").click(function () {
        projectPuid = $("#project", window.top.document).val(),
            window.Ewin.dialog({
                // 这个puid就是车型模型的puid，直接修改了车型模型的基本信息（在bom系统维护的字段）
                title: "添加车型模型",
                url: "materiel/addVehicleModelPage?projectPuid=" + projectPuid,
                gridId: "gridId",
                width: 350,
                height: 450
            });
    }),
    $("#reflect2Y").click(function () {
        projectPuid = $("#project", window.top.document).val(),
            window.Ewin.dialog({
                // 这个puid就是车型模型的puid，直接修改了车型模型的基本信息（在bom系统维护的字段）
                title: "将配置映射到2Y层",
                url: "loadBom/reflectTo2YPage?projectPuid=" + projectPuid,
                gridId: "gridId",
                width: 400,
                height: 450
            });
    })
);


$('.edit').on('click', function () {
    if (!($(this).find('div').hasClass('btn'))) {
        $(this).find('div').is(':visible') && ($(this).find('input').show().prev().hide(), $(this).parent().find('.btn').html('保存'));
    }
})
$('.btn').on('click', function () {
    // $(this).html() == '编辑' ? $(this).html('保存') : $(this).html('编辑');
    if ($(this).html() == '编辑') {
        $(this).html('保存');
        $(this).parent().siblings().each(function (index, item) {
            $(item).find('div').hide().next().show();
        })
    } else {
        $(this).html('编辑');
        $(this).parent().siblings().each(function (index, item) {
            $(item).find('div').html($(item).find('input').val()).show().next().hide();
        })
    }
})

function editorOrSave(but) {
    if($(but).val() == '编辑'){
        $(but).val('保存');
        $(but).parent().siblings().each(function (index, item) {
            if(index>1&&index<7){
                var divText = $(item).find('div').text();
                $(item).find('input').val(divText);
                $(item).find('input').show();
                $(item).find('div').hide();
            }else if(index>9){
                var divText = $(item).find('div').text();
                $(item).find('select').val(divText);
                $(item).find('select').show();
                $(item).find('div').hide();
            }
        })
    }else {
        $(but).val('编辑');
        $(but).parent().siblings().each(function (index, item) {
            if(index>1&&index<7){
                var inputVal = $(item).find('input').val();
                $(item).find('div').text(inputVal);
                $(item).find('div').show();
                $(item).find('input').hide();
            }else if(index>9){
                var selectVal = $(item).find('select').val();
                $(item).find('div').text(selectVal);
                $(item).find('div').show();
                $(item).find('select').hide();
            }
        })
    }
}

function Botton(id) {
    window.Ewin.dialog({
        url: "model/modModel?pModelPuid=" + id,
        title: "修改",
        width: 500,
        height: 400,
        gridId: "addPageOfModel"
    })
};

$(function () {
    $("div input:button").click(function () {
        str = $(this).val() == "编辑" ? "确定" : "编辑";
        $(this).val(str);   // 按钮被点击后，在“编辑”和“确定”之间切换
        $(this).parent().parent().siblings("td").each(function () {  // 获取当前行的其他单元格
            obj_text = $(this).find("div input:text");    // 判断单元格下是否有文本框
            if (!obj_text.length)   // 如果没有文本框，则添加文本框使之可以编辑
                $(this).html("<div style='width: 150px'><input type='text' value='" + $(this).text() + "'></div>");
            else   // 如果已经存在文本框，则将其显示为文本框修改的值
                $(this).html("<div style='width: 150px'>" + obj_text.val() + "</div>");
        });
    });
});
var i = 0;
$(function () {
    $("#addTo").click(function () {
        $("#tr_1").append("<div><td>追加值" + (++i) + "</td></div>");
    });
})


function save() {
    var jsonStr = '{';
    var trList = $("#cfg0Table").children("tr");

    for(var i=9;i<trList.length;i++){
        var tdArr = trList.eq(i).find("td");
        var pCfg0ObjectId = tdArr.eq(0).find('.pCfg0ObjectId').val();
        var pBomlineId = tdArr.eq(4).find('input').val();
        jsonStr = jsonStr+'\"'+pCfg0ObjectId+'\":\"'+pBomlineId+'\"';
        if(trList.length-i!=1){
            jsonStr+=',';
        }
    }
    jsonStr+='}';
    var data = JSON.parse(jsonStr);
    $.ajax({
        type: "POST",
        //ajax需要添加打包名
        url: "bomAllCfg/saveBom",
        data:data,
        // data: JSON.stringify(puidOfModelFeatures),
        success: function (result) {
            window.Ewin.alert({message: result, width: 800});
            $table.bootstrapTable("refresh");
        },
        error: function (info) {
            window.Ewin.alert({message: "操作删除:" + info.status});
        }
    })
}