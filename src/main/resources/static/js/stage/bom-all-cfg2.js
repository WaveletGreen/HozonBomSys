//车型数量
var modelSize;
var peculiarity;
//特性数组
var array;
//2Y层数量
var cfgSize;
//版本头
var versionHead;
function doRefresh(projectUid) {
    loadData(projectUid);
}

function loadData(projectUid) {
    if (!checkIsSelectProject(projectUid)) {
        return;
    }
    $.ajax({
        url: 'bomAllCfg/loadCfg0BomLineOfModel',
        type: 'GET',
        data: {
            'bdf': projectUid
        },
        success: function (myData) {
            if (myData == null) {
                alert("查无数据，请联系项目经理或管理员");
                return;
            }
            var _data = JSON.stringify(myData);
            var _ddd = JSON.parse(_data);
            var data = _ddd.data;
            var model = _ddd.model;
            var startIndex = 12;
            var rowCount = 11;
            modelSize = _ddd.modelSize;
            cfgSize = _ddd.cfgSize;
            array = _ddd.array;
            var main = _ddd.main;

            var versionArr = main.version.split(".");
            versionHead = parseInt(versionArr[0]);
            var $table = $("#cfg0Table");
            //清空
            $table.html("");
            $table.removeClass("table").removeClass("table-striped");
            // $table.val("");
            var temp = "";
            for (var i = 0; i < rowCount; i++) {
                if (i == 0) {
                    temp += "<tr id='tr" + i + "'><td id='row" + i + "' colspan='11' style='border: #fff'>阶段：" + main.stage + "</td></tr>";
                }
                else if (i == 1) {
                    temp += "<tr id='tr" + i + "'><td id='row" + i + "' colspan='11' style='border: #fff'>版本：" + main.version + "</td></tr>";
                }
                else if (i == 2) {
                    temp += "<tr id='tr" + i + "'><td id='row" + i + "' colspan='11' style='border: #fff'>生效日期：" + main.effectiveDate + "</td></tr>";
                }
                else {
                    temp += "<tr id='tr" + i + "'><td id='row" + i + "' colspan='11' style='border: #fff'></td></tr>";
                }
            }

            $table.append(temp);


            $("#tr0").append("<th id='th1'></th>");
            $("#tr1").append("<th id='th0'><div style='width: 200px'  >品牌</div><div id='modelSize' style='display: none'>" + modelSize + "</div><div id='cfgSize' style='display: none'>" + cfgSize + "</div></th>");
            $("#tr2").append("<th id='th1'><div style='width: 200px'  >平台</div></th>");
            $("#tr3").append("<th id='th2'><div style='width: 200px'  >车型</div></th>");
            $("#tr4").append("<th id='th3'><div style='width: 200px'  >版型</div></th>");
            $("#tr5").append("<th id='th4'><div style='width: 200px'  >车身形式</div></th>");
            $("#tr6").append("<th id='th5'><div style='width: 200px'  >公告</div></th>");
            $("#tr7").append("<th id='th6'><div style='width: 200px'  >配置描述</div></th>");
            $("#tr8").append("<th id='th7'><div style='width: 200px'  >配置管理</div></th>");
            $("#tr9").append("<th id='th8'><div style='width: 200px'  >试制号</div></th>");
            $("#tr10").append("<th id='th9'><div style='width: 200px'  >商品号</div></th>");


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
                "<th style='text-align: center'><div style='width: 110px' >选择</div></th>" +
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
                "<th ><div style='width: 150px' >是否颜色件</div></th>" +
                "<th ><div style='width: 150px' >标配/选配</div></th>" +
                "</tr>"
            ;
            $table.append(t);
            //动态拼接后台数据
            for (var i = 0; i < data.length; i++) {
                var dataOfModel = data[i];
                var j = i + startIndex;
                var cfgId = "in_in_" + i;
                var delta = "<tr id='tr" + j + "'>" +
                    "<td style='text-align: center'><input class='btn btn-default' type='button' value='编辑' style='width: 50px' onclick='editorOrSave(this)'><input class='btn btn-default' type='button' value='取消' style='width: 50px; display: none' onclick='cancelEditor(this)'><div id='" + cfgId + "' style='display: none'>" + dataOfModel.bomLinePuid + "</div></td>"
                    // "<td style='text-align: center'><input class='btn btn-default' type='button' value='编辑' style='width: 50px' onclick='editorOrSave(this)'><input class='pCfg0ObjectId' type='text' value='"+dataOfModel.pCfg0ObjectId+"' hidden='hidden' disabled='disabled'><div id='"+cfgId+"' style='display: none'>"+dataOfModel.bomLinePuid+"</div></td>"
                    +
                    "<td><div style='width: 50px' >" + (i + 1) + "</div></td>";
                var aaa = 0;
                for (var index in dataOfModel) {
                    if (index == "bomLinePuid") {
                        continue;
                    }
                    if (aaa == 6) {
                        delta = delta + "<td class='edit'><input type='text' value='" + dataOfModel[index] + "' style='display: none'><div style='width: 150px'>" + dataOfModel[index] + "</div></td>";
                    } else if (aaa == 7) {
                        var cfgSelect = "<select style='display: none;width: 83px'>";
                        cfgSelect += "<option></option>";
                        for (var j = 0; j < array.length; j++) {
                            var cfg = array[j];
                            if (cfg.pCfg0ObjectId == dataOfModel[index]) {
                                cfgSelect += "<option value='" + cfg.puid + "|" + cfg.pCfg0Desc + "' selected='selected'>" + cfg.pCfg0ObjectId + "</option>";
                            } else {
                                cfgSelect += "<option value='" + cfg.puid + "|" + cfg.pCfg0Desc + "'>" + cfg.pCfg0ObjectId + "</option>";
                            }
                        }
                        cfgSelect += "</select>";
                        // delta = delta + "<td class='edit'><input type='text' value='"+dataOfModel[index]+"' style='display: none'><div style='width: 150px'>" + dataOfModel[index] + "</div></td>";
                        delta = delta + "<td class='edit'>" + cfgSelect + "<div style='width: 150px'>" + dataOfModel[index] + "</div></td>";
                    }else if(aaa==8){
                        var colorPartId = "colorPart"+i;
                            delta+="<td class='edit'><div id='"+colorPartId+"'>"+dataOfModel[index]+"</div><select style='display: none'><option value='1'>Y</option><option value='0'>N</option></select></td>"
                    }else if(aaa==9){
                        var messageId = "msg"+i;
                        var msgVal = "";
                        if(dataOfModel[index]=='0'){
                            msgVal = "选配";
                        }else if(dataOfModel[index]=='1'){
                            msgVal = "标配";
                        }
                        delta+="<td class='edit'><div id='"+messageId+"'>"+msgVal+"</div><select style='display: none'><option value='0'>选配</option><option value='1'>标配</option></select></td>";
                    }
                    // else if(aaa>9){
                    // delta = delta + "<td class='edit'><select style='display: none'><option>-</option><option>●</option><option>○</option></select><div style='width: 150px'>" + dataOfModel[index] + "</div></td>";
                    else {
                        delta = delta + "<td class='edit'><div style='width: 150px'>" + dataOfModel[index] + "</div></td>";
                    }
                    aaa++;
                }
                delta = delta + "</tr>";
                $table.append(delta);
            }
            for (var i = 0; i < model.length; i++) {
                var modeli = model[i];
                var v0 = modeli.key;
                var v1 = modeli.hide;

                $("#tr0").append("<td ><input class='btn btn-default' type='button' value='编辑' onclick='editPoint(this)'/><input class='btn btn-default' type='button' value='取消' style='display: none' onclick='cancelEditorPoint(this)'/><input class='btn btn-default' type='button' value='删除' style='background-color: red;display:none' onclick='deleteModel(this)'/><div style='display: none' id='in_" + i + "'>" + modeli.modelPuid + "</div></td>");
                //品牌
                $("#tr1").append("<td ><div style='width: 200px'  >" + modeli.brand + "</div></td>");
                //平台
                $("#tr2").append("<td ><div style='width: 200px'  >" + modeli.platform + "</div></td>");
                //车型
                $("#tr3").append("<td ><div style='width: 200px'  >" + modeli.vehicle + "</div></td>");
                $("#tr4").append("<td ><div style='width: 200px'  ><a href='javascript:void(0);' onclick='Botton(\"" + v1 + "\")'>" + v0 + "</a></div></td>");
                $("#tr5").append("<td ><div style='width: 200px'  >" + modeli.pModelShape + "</div></td>");
                $("#tr6").append("<td ><div style='width: 200px'  >" + modeli.pModelAnnouncement + "</div></td>");
                $("#tr7").append("<td ><div style='width: 200px'  >" + modeli.pModelCfgDesc + "</div></td>");
                $("#tr8").append("<td ><div style='width: 200px'  >" + modeli.pModelCfgMng + "</div></td>");
                $("#tr9").append("<td ><div style='width: 200px'  >" + modeli.pModelTrailNum + "</div></td>");
                $("#tr10").append("<td ><div style='width: 200px'  >" + modeli.pModelGoodsNum + "</div></td>");
                $("#tr11").append("<td ><div style='width: 200px'  >" + "" + "</div></td>");
                var point = modeli.point;
                for (var j = startIndex; j < (point.length + startIndex); j++) {
                    var trNumber = "tr" + j;
                    var pointId = "in_" + i + "in_in_" + (j - startIndex);
                    var cfgObjectIdVar = $("#" + trNumber).find("select").find("option:selected").text();
                    var cfgObjectId = point[j - startIndex].point;
                    //总成零件号和总成零件名称
                    var bomLineId = $("#" + trNumber).find("td:eq(4)").find("div").text();
                    var bomLineName = $("#" + trNumber).find("td:eq(5)").find("div").text();
                    //大版本大于等于2时打点图只有两个选项
                    if(versionHead>=2){
                        if (cfgObjectId == ""){
                            $("#" + trNumber).append("<td class='edit'><select style='display: none' title='总成零件号:    " + bomLineId + "&#10总成零件名称:    " + bomLineName + "'><option title='总成零件号:    " + bomLineId + "&#10总成零件名称:    " + bomLineName + "'>-</option><option title='总成零件号:    " + bomLineId + "&#10总成零件名称:    " + bomLineName + "'>●</option></select><div id='" + pointId + "' style='width: 150px'>" + cfgObjectId + "</div></td>");
                        }else if(cfgObjectId == "-") {
                            // $("#"+trNumber).append("<td class='edit'><select style='display: none'><option selected='selected'>-</option><option>●</option><option>○</option></select><div id='"+pointId+"' style='width: 150px'>" + point[j-10].point + "</div></td>");
                            $("#" + trNumber).append("<td class='edit'><select style='display: none' title='总成零件号:    " + bomLineId + "&#10总成零件名称:    " + bomLineName + "'><option selected='selected' title='总成零件号:    " + bomLineId + "&#10总成零件名称:    " + bomLineName + "'>-</option><option title='总成零件号:    " + bomLineId + "&#10总成零件名称:    " + bomLineName + "'>●</option></select><div id='" + pointId + "' style='width: 150px'>" + cfgObjectId + "</div></td>");
                        } else if (cfgObjectId == "●") {
                            $("#" + trNumber).append("<td class='edit'><select style='display: none' title='总成零件号:    " + bomLineId + "&#10总成零件名称:    " + bomLineName + "'><option title='总成零件号:    " + bomLineId + "&#10总成零件名称:    " + bomLineName + "'>-</option><option selected='selected' title='总成零件号:    " + bomLineId + "&#10总成零件名称:    " + bomLineName + "'>●</option></select><div id='" + pointId + "' style='width: 150px'>" + cfgObjectId + "</div></td>");
                        }
                    }else{
                        //大版本为1时打点图3个选项
                        if (cfgObjectId == ""){
                            $("#" + trNumber).append("<td class='edit'><select style='display: none' title='总成零件号:    " + bomLineId + "&#10总成零件名称:    " + bomLineName + "'><option title='总成零件号:    " + bomLineId + "&#10总成零件名称:    " + bomLineName + "'>-</option><option title='总成零件号:    " + bomLineId + "&#10总成零件名称:    " + bomLineName + "'>●</option><option title='总成零件号:    " + bomLineId + "&#10总成零件名称:    " + bomLineName + "'>○</option></select><div id='" + pointId + "' style='width: 150px'>" + cfgObjectId + "</div></td>");
                        }else if(cfgObjectId == "-") {
                            // $("#"+trNumber).append("<td class='edit'><select style='display: none'><option selected='selected'>-</option><option>●</option><option>○</option></select><div id='"+pointId+"' style='width: 150px'>" + point[j-10].point + "</div></td>");
                            $("#" + trNumber).append("<td class='edit'><select style='display: none' title='总成零件号:    " + bomLineId + "&#10总成零件名称:    " + bomLineName + "'><option selected='selected' title='总成零件号:    " + bomLineId + "&#10总成零件名称:    " + bomLineName + "'>-</option><option title='总成零件号:    " + bomLineId + "&#10总成零件名称:    " + bomLineName + "'>●</option><option title='总成零件号:    " + bomLineId + "&#10总成零件名称:    " + bomLineName + "'>○</option></select><div id='" + pointId + "' style='width: 150px'>" + cfgObjectId + "</div></td>");
                        } else if (cfgObjectId == "●") {
                            $("#" + trNumber).append("<td class='edit'><select style='display: none' title='总成零件号:    " + bomLineId + "&#10总成零件名称:    " + bomLineName + "'><option title='总成零件号:    " + bomLineId + "&#10总成零件名称:    " + bomLineName + "'>-</option><option selected='selected' title='总成零件号:    " + bomLineId + "&#10总成零件名称:    " + bomLineName + "'>●</option><option title='总成零件号:    " + bomLineId + "&#10总成零件名称:    " + bomLineName + "'>○</option></select><div id='" + pointId + "' style='width: 150px'>" + cfgObjectId + "</div></td>");
                        } else if (cfgObjectId == "○") {
                            $("#" + trNumber).append("<td class='edit'><select style='display: none' title='总成零件号:    " + bomLineId + "&#10总成零件名称:    " + bomLineName + "'><option title='总成零件号:    " + bomLineId + "&#10总成零件名称:    " + bomLineName + "'>-</option><option title='总成零件号:    " + bomLineId + "&#10总成零件名称:    " + bomLineName + "'>●</option><option selected='selected' title='总成零件号:    " + bomLineId + "&#10总成零件名称:    " + bomLineName + "'>○</option></select><div id='" + pointId + "' style='width: 150px'>" + cfgObjectId + "</div></td>");
                        }
                    }
                }
                // for(var j=9;j<(data.length+9);j++){
                //     var trNumber = "tr"+j;
                //     var pointId = "in_"+i+"in_in_"+(j-9);
                //     $("#"+trNumber).append("<td class='edit'><select style='display: none'><option>-</option><option>●</option><option>○</option></select><div id='"+pointId+"' style='width: 150px'>-</div></td>");
                // }
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
    loadData(getProjectUid()),
    $("#myButton").click(function () {
        loadData(getProjectUid());
    }),
    $("#addVehicle").click(function () {

        var flag = false;
        for(var i=0;i<cfgSize;i++){
            var msgDivId = 'msg'+i;
            var msgVal = $("#"+msgDivId).text();
            if(msgVal==""||msgVal==null){
                alert("请确认备注是否全部填写");
                flag = true;
                break;
            }
        }
        if(!flag){
            projectPuid = $("#project", window.top.document).val(),
                window.Ewin.dialog({
                    // 这个puid就是车型模型的puid，直接修改了车型模型的基本信息（在bom系统维护的字段）
                    title: "添加车型模型",
                    url: "materiel/addVehicleModelPage2?projectPuid=" + projectPuid,
                    gridId: "gridId",
                    width: 350,
                    height: 450
                });
        }
    }),
    // $("#reflect2Y").click(function () {
    //     projectPuid = $("#project", window.top.document).val(),
    //         window.Ewin.alert({
    //             // 这个puid就是车型模型的puid，直接修改了车型模型的基本信息（在bom系统维护的字段）
    //             title: "将配置映射到2Y层",
    //             url: "loadBom/reflectTo2YPage?projectPuid=" + projectPuid,
    //             gridId: "gridId",
    //             width: 400,
    //             height: 450
    //         });
    // }),
    $("#setStage").click(function () {
        // $.ajax({
        //     url: "bomAllCfg/setStagePage?projectUid=" + getProjectUid(),
        //     type: "GET",
        //     success: function (result) {
        //         window.Ewin.alert({message: result});
        //     },
        //     error: function (info) {
        //         window.Ewin.alert({message: "操作失败:" + info.status});
        //     }
        // })
        var flag = false;
        for(var i=0;i<cfgSize;i++){
            var msgDivId = 'msg'+i;
            var msgVal = $("#"+msgDivId).text();
            if(msgVal==""||msgVal==null){
                alert("请确认备注是否全部填写");
                flag = true;
                break;
            }
            var cfgDivId = "in_in_"+i;
            for(var j=0;j<modelSize;j++){
                var modelDivId = "in_"+j;
                var pointDivId = modelDivId+cfgDivId;
                var point = $("#"+pointDivId).text();
                if(point=="○"||point==""){
                    alert("升版时打点图不能存在○或空，请重新填写");
                    flag = true;
                    break;
                }
            }
            if(flag){
                break;
            }
        }
        if(!flag){
            window.Ewin.dialog({
                // 这个puid就是车型模型的puid，直接修改了车型模型的基本信息（在bom系统维护的字段）
                title: "手动设置阶段与版本",
                url: "bomAllCfg/setStagePage?projectUid=" + getProjectUid(),
                gridId: "gridId",
                width: 450,
                height: 450
            });
        }
    }),
    $("#release").click(function () {
        window.Ewin.confirm({title: '提示', message: '您确定需要升小版本吗？', width: 500}).on(function (e) {
            if (e) {
                $.ajax({
                    type: "POST",
                    //ajax需要添加打包名
                    url: "./bomAllCfg/promote?projectUid=" + getProjectUid(),
                    contentType: "application/json",
                    success: function (result) {
                        if (result.status) {
                            $("#row1").text("版本：" + result.version);
                            $("#row2").text("生效日期：" + result.releaseDate);
                            layer.msg(result.msg, {icon: 1, time: 2000})
                        }
                        else {
                            window.Ewin.alert({message: "操作升级小版本失败:" + result.msg});
                        }
                        // loadData();
                    },
                    error: function (info) {
                        window.Ewin.alert({message: "操作失败:" + info.status});
                    }
                })
            }
        });
    }),
);


$('.edit').on('click', function () {
    if (!($(this).find('div').hasClass('btn'))) {
        $(this).find('div').is(':visible') && ($(this).find('input').show().prev().hide(), $(this).parent().find('.btn').html('保存'));
    }
})


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
    // var jsonStr = '{';
    // var trList = $("#cfg0Table").children("tr");
    //
    // for(var i=9;i<trList.length;i++){
    //     var tdArr = trList.eq(i).find("td");
    //     var pCfg0ObjectId = tdArr.eq(0).find('.pCfg0ObjectId').val();
    //     var pBomlineId = tdArr.eq(4).find('input').val();
    //     jsonStr = jsonStr+'\"'+pCfg0ObjectId+'\":\"'+pBomlineId+'\"';
    //     if(trList.length-i!=1){
    //         jsonStr+=',';
    //     }
    // }
    // jsonStr+='}';
    // var data = JSON.parse(jsonStr);
    // $.ajax({
    //     type: "POST",
    //     //ajax需要添加打包名
    //     url: "bomAllCfg/saveBom",
    //     data:data,
    //     // data: JSON.stringify(puidOfModelFeatures),
    //     success: function (result) {
    //         window.Ewin.alert({message: result, width: 800});
    //         $table.bootstrapTable("refresh");
    //     },
    //     error: function (info) {
    //         window.Ewin.alert({message: "操作删除:" + info.status});
    //     }
    // })
    // var potinJsonStr = "{"
    // for(var i=0;i<modelSize;i++){
    //     var modelDivId = "in_"+i;
    //     var modelId = $("#"+modelDivId).text();
    //     var modelJsonStr = '\"'+modelId+'\":[';
    //
    //     for(var j=0;j<cfgSize;j++){
    //         var cfgDivId = "in_in_"+j;
    //         var cfgId = $("#"+cfgDivId).text();
    //         var ponitId = modelDivId+cfgDivId;
    //         var ponitVal = $("#"+ponitId).text();
    //         var cfgPonitJson = '{\"'+cfgId+'\":\"'+ponitVal+'\"}';
    //         modelJsonStr+=cfgPonitJson;
    //         if(cfgSize-j>1){
    //             modelJsonStr+=",";
    //         }
    //     }
    //     modelJsonStr+="]";
    //     if(modelSize-i>1){
    //         modelJsonStr+=",";
    //     }
    //     potinJsonStr+=modelJsonStr;
    // }
    // potinJsonStr+="}";
    // var data = JSON.parse(potinJsonStr);


    var object = {};
    for (var i = 0; i < modelSize; i++) {
        var params = {};
        var modelDivId = "in_" + i;
        for (var j = 0; j < cfgSize; j++) {
            var cfgDivId = "in_in_" + j;
            var cfgId = $("#" + cfgDivId).text();
            var ponitId = modelDivId + cfgDivId;
            var ponitVal = $("#" + ponitId).text();

            // var obj = {};
            // obj[cfgId] = ponitVal;
            params[cfgId] = ponitVal;
        }

        var modelId = $("#" + modelDivId).text();
        object[modelId] = params;
    }
    var data = JSON.stringify(object);
    $.ajax({
        type: "POST",
        //ajax需要添加打包名
        url: "bomAllCfg/savePoint",
        data: data,
        contentType: 'application/json',
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

//编辑保存单行
function editorOrSave(but) {
    if ($(but).val() == '编辑') {

        projectPuid = $("#project", window.top.document).val();
        var bomLineId = $(but).parent().find('div').text();
        $.ajax({
            type: "GET",
            url: "bomAllCfg/query2YCfg?projectPuid=" + projectPuid+"&bomLineId=" + bomLineId,
            success: function (result) {
                var cfgs = result.cfgs;
                var selfCfg = result.selfCfg;
                $(but).val('保存');
                $(but).parent().find('input:eq(1)').show();
                $(but).parent().siblings().each(function (index, item) {
                    if (index == 8) {
                        var select = $(item).find('select');
                        $(select).empty();
                        $(select).append("<option></option>");
                        let [..._array] = array;
                        console.log(_array);
                        for (let j = 0; j < cfgs.length; j++) {
                            for (let i = 0; i < _array.length; i++) {
                                if (cfgs[j].cfgCfg0Uid == _array[i].puid) {
                                    _array.splice(i,1);
                                    break;
                                }
                            }

                        }
                        for (let i = 0; i < _array.length; i++) {
                            $(select).append("<option value='" + _array[i].puid + "|" + _array[i].pCfg0Desc + "'>" + _array[i].pCfg0ObjectId + "</option>");
                        }
                        for(let cfgIdIndex=0;cfgIdIndex<array.length;cfgIdIndex++){
                            if(array[cfgIdIndex].puid==selfCfg.cfgCfg0Uid){
                                $(select).append("<option value='" +array[cfgIdIndex].puid + "|" +array[cfgIdIndex].pCfg0Desc + "' selected='selected'>" + array[cfgIdIndex].pCfg0ObjectId + "</option>");
                            }
                        }
                        // for (var i = 0; i < cfgs.length; i++) {
                        //     $("select option[value='" + cfgs[i].cfgCfg0Uid + "']").remove();
                        //     // alert(cfgs[i].cfgCfg0Uid);
                        // }
                        // var selectText = $(select).find("option:selected").text();
                        // if(selectText==null){
                        //     $('#'+selectText+ 'option:first').prop("selected", 'selected');
                        // }

                        $(item).find('select').show();
                        $(item).find('div').hide();
                    }else if(index==9){
                        var div = $(item).find('div');
                        var select = $(item).find('select');
                        var divText = $(div).text();
                        // alert(divText);
                        // $(select).find("option[text='N']").attr("selected",true);
                        if(divText=="Y"){
                            $(select).val("1");
                        }else{
                            $(select).val("0");
                        }
                        $(div).hide();
                        $(select).show();
                    }else if(index==10){
                        $(item).find('select').show();
                        $(item).find('div').hide();
                    }
                })

            },
            error: function (info) {
                window.Ewin.alert({message: "系统错误:" + info.status});
            }
        });
        // $(but).parent().siblings().each(function (index, item) {
        //     if(index==8){
        //         // var divText = $(item).find('div').text();
        //         // $("select option:contains('"+divText+"')").attr("selected","selected");
        //         // $(item).find('select').val(divText);
        //         var select = $(item).find('select');
        //         var selectText = $(select).find("option:selected").text();
        //         if(selectText==null){
        //             $('#'+selectText+ 'option:first').prop("selected", 'selected');
        //         }
        //         $(item).find('select').show();
        //         $(item).find('div').hide();
        //     }
        // })
    } else {
        $(but).val('编辑');
        $(but).parent().find('input:eq(1)').hide();
        //2Y层id
        var bomLinePuid;
        //特性Id
        var cfgPuid;
        //备注值
        var msgVal;
        //是否为颜色件
        var colorPart;
        var cfgIndex;
        //第一个select
        var select;
        var desc = "";
        $(but).parent().siblings().each(function (index, item) {
            //通过序号找到存放2Y层id值的DIV的id，然后获取2Y层ID值
            if (index == 0) {
                select = $(item).find('select');
                var divVal = $(item).find('div').text();
                var num = parseInt(divVal);
                var bomLinePuidDivId = "in_in_" + (num - 1);
                bomLinePuid = $("#" + bomLinePuidDivId).text();
            } else if (index == 8) {
                //获取特性Id
                // cfgCode = $(item).find('select').text();
                let str = $(item).find('select').val();
                /**避免出现前端异常问题*/
                if (str == null || str == "") {
                    cfgPuid = "";
                    desc = "";
                }
                else {
                    /**截取字符串*/
                    cfgPuid = str.substring(0, str.indexOf("|"));//$(item).find('select').val();
                    console.log(cfgPuid);
                    desc = str.substring(str.indexOf("|") + 1, str.length);
                    // cfgIndex = $(item).find('select').get(0).selectedIndex;
                }
            }else if(index==9){
                //获取是否为颜色件的值
                var div = $(item).find('div');
                var select = $(item).find('select');
                colorPart = $(select).val();
                $(div).text($(select).find("option:selected").text());
                // $(select).hide();
                // $(div).show();
            }else if (index == 10) {
                //获取备注值
                msgVal = $(item).find('select').val();
                return false;
            }
        });
        //保存2Y层对应的各数据
        $.ajax({
            type: "POST",
            url: "bomAllCfg/saveOneRow?bomLinePuid=" + bomLinePuid + "&cfgPuid=" + cfgPuid+"&colorPart="+colorPart+"&msgVal=" + msgVal,
            contentType: 'application/json',
            success: function (result) {
                if (result.flag) {
                    //如果备注不为空则为所有车型对应该2Y层的打点图设置默认值
                    if(msgVal!=""&&msgVal!=null){
                        var object = {};
                        var params = {}
                        for(var modIndex=0;modIndex<modelSize;modIndex++){
                            var modelDivId = "in_"+modIndex;
                            var modeId = $("#"+modelDivId).text();
                            // var cfgDivId = $(but).parent().find("div").attr("id");
                            // var cfgId = $("#"+modelDivId+cfgDivId).text();
                            //备注为选配时
                            if(msgVal==0){
                                //大版本小于2时
                                if(versionHead<2){
                                    params[modeId] = "○";
                                }else{
                                    params[modeId] = "";
                                }
                            }else if(msgVal==1){
                                //备注为标配时
                                params[modeId] = "●";
                            }
                        }
                        object[bomLinePuid] = params;
                        var json = JSON.stringify(object);
                        $.ajax({
                           type:"POST",
                           url:"bomAllCfg/saveBomLinePiont",
                           data:json, 
                           dataType: 'json',
                           contentType: 'application/json',
                           success:function (respons) {
                               if(respons.updateFlag){
                                   $(but).parent().siblings().each(function (index, item) {
                                       if (index == 7) {
                                           if (cfgPuid == "" || cfgPuid == null) {
                                               $(item).find('div').text('');
                                               $(item).find('div').show();
                                           } else {
                                               $(item).find('div').text(desc/*array[cfgIndex - 1].pCfg0Desc*/);
                                               $(item).find('div').show();
                                           }
                                           // $(item).find('select').hide();
                                       } else if (index == 8) {
                                           var selectText = $(item).find('select').find("option:selected").text();
                                           $(item).find('div').text(selectText);
                                           $(item).find('div').show();
                                           $(item).find('select').hide();
                                       }else if(index==9){
                                           var select = $(item).find('select').hide();
                                           var div = $(item).find('div').show();
                                       }else if(index==10){
                                           var select = $(item).find('select');
                                           var div = $(item).find('div');
                                           $(select).hide();
                                           $(div).text($(select).find("option:selected").text());
                                           $(div).show();
                                           msgVal = $(select).val();
                                       }else if(index>10){
                                           if(msgVal==1){
                                               // $(item).find('select').find("option[text='●']").attr("selected",true);
                                               $(item).find('div').text("●");
                                           }else if(msgVal==0){
                                               if(versionHead<2){
                                                   $(item).find('div').text("○");
                                               }else{
                                                   $(item).find('div').text("");
                                               }
                                           }
                                       }
                                   })
                               }else {
                                   window.Ewin.alert({message: "修改失败:" + info.status});
                               }
                           },
                            error: function (info) {
                                window.Ewin.alert({message: "修改失败:" + info.status});
                            }
                            
                        });
                    }


                } else {
                    alert("该特性以被其他2Y层关联");
                    $(but).parent().siblings().each(function (index, item) {
                        if (index == 7) {
                            // $(item).find('div').text("");
                            $(item).find('div').show();
                        } else if (index == 8) {
                            // $(item).find('div').text("");
                            $(item).find('div').show();
                            $(item).find('select').hide();
                        }else if(index==9){
                            $(item).find('select').hide();
                            $(item).find('div').show();
                        }else if(index==10){
                            $(item).find('select').hide();
                            $(item).find('div').show();
                        }
                    })
                }
            },
            error: function (info) {
                window.Ewin.alert({message: "操作删除:" + info.status});
            }
        });
    }
}

//取消编辑
function cancelEditor(but) {
    $(but).hide();
    $(but).parent().find("input:eq(0)").val('编辑');
    $(but).parent().siblings().each(function (index, item) {
        if (index == 7) {
            // $(item).find('div').text("");
            $(item).find('div').show();
        } else if (index == 8) {
            // $(item).find('div').text("");
            $(item).find('div').show();
            $(item).find('select').hide();
        }else if(index==9){
            $(item).find('select').hide();
            $(item).find('div').show();
        }else if(index==10){
            $(item).find('select').hide();
            $(item).find('div').show();
        }
    })
}


//编辑保存打点图
function editPoint(but) {
    // var modelPuid = $(but).parent().find("div").text();
    var modelDivId = $(but).parent().find("div").attr("id");
    if ($(but).val() == '编辑') {
        $(but).val('保存');
        //显示取消和删除两个按钮
        $(but).parent().find("input:eq(1)").show();
        $(but).parent().find("input:eq(2)").show();
        //查找当前车辆模型所有2Y层
        for (var i = 0; i < cfgSize; i++) {
            //打点图id
            var pointId = modelDivId + "in_in_" + i;
            //备注id
            var msgId = "msg"+i;
            //备注值
            var msgVal = $("#"+msgId).text();
            //当备注不为标配和空时才可以编辑打点图
            if(msgVal!='标配'&&msgVal!=""){
                $("#" + pointId).parent().find("select").show();
                $("#" + pointId).parent().find("div").hide();
            }
        }
    } else {
        $(but).val('编辑');
        // var object = {};
        // object['modelPuid'] = modelPuid;
        // var params = [];
        // for(var i=0;i<cfgSize;i++){
        //     var cfgDivId = "in_in_"+i;
        //     var cfgId = $("#"+cfgDivId).text();
        //     var pointId = lineId + cfgDivId;
        //     var point = $("#" + pointId).parent().find("select").val();
        //     var obj = {};
        //     obj[cfgId] = point;
        //     params.push(obj);
        // }
        // object['data'] = params;
        //将改车辆模型对应的所有2Y层的打点图、2Y层Id，车型ID封装成json格式的数据
        var object = {};
        var params = {};
        for (var j = 0; j < cfgSize; j++) {
            var cfgDivId = "in_in_" + j;
            // var cfgId = $("#" + cfgDivId).parent().parent().find("select:first").val();
            var bomLineId = $("#" + cfgDivId).text();
            var ponitId = modelDivId + cfgDivId;
            var ponitVal = $("#" + ponitId).parent().find("select").val();
            params[bomLineId] = ponitVal;
        }

        var modelId = $(but).parent().find("div").text();
        object[modelId] = params;

        var json = JSON.stringify(object);
        //传给后台保存打点图
        $.ajax({
            type: "POST",
            url: "bomAllCfg/savePoint",
            data: json,
            dataType: 'json',
            contentType: 'application/json',
            success: function (result) {
                if (result.updateFlag) {
                    for (var i = 0; i < cfgSize; i++) {
                        var pointId = modelDivId + "in_in_" + i;
                        var selectVal = $("#" + pointId).parent().find("select").find("option:selected").val();
                        $("#" + pointId).parent().find("div").text(selectVal);
                        $("#" + pointId).parent().find("div").show();
                        $("#" + pointId).parent().find("select").hide();
                    }
                    $(but).parent().find("input:eq(1)").hide();
                    $(but).parent().find("input:eq(2)").hide();
                } else {
                    window.Ewin.alert({message: "修改失败:" + info.status});
                }
            },
            error: function (info) {
                window.Ewin.alert({message: "操作删除:" + info.status});
            }
        });
    }
}

//删除车辆模型
function deleteModel(obj) {
    var isDelete = confirm("是否确认删除");
    if (isDelete) {
        var modelId = $(obj).parent().find("div").text();
        $.ajax({
            type: "GET",
            url: "bomAllCfg/deleteModel?modelId=" + modelId,
            success: function (result) {
                if (result.flag) {
                    layer.msg("删除成功", {icon: 1, time: 2000})
                    loadData(getProjectUid());
                } else {
                    window.Ewin.alert({message: "执行删除操作失败"});
                }
            },
            error: function (info) {
                window.Ewin.alert({message: "删除失败:" + info.status});
            }
        });
    }
}

/**
 * 设置阶段和版本
 * @param result
 */
function setStage(result) {
    $("#row0").text("阶段：" + result.stage);
    $("#row1").text("版本：" + result.version);
}

function cancelEditorPoint(but) {
    var modelDivId = $(but).parent().find("div").attr("id");
    $(but).hide();
    $(but).parent().find("input:eq(0)").val('编辑');
    $(but).parent().find("input:eq(2)").hide();
    for (var i = 0; i < cfgSize; i++) {
        var pointId = modelDivId + "in_in_" + i;
        $("#" + pointId).parent().find("div").show();
        $("#" + pointId).parent().find("select").hide();
        var divText = $("#" + pointId).parent().find("div").text();
        $("#" + pointId).parent().find("select").val(divText);
    }
}