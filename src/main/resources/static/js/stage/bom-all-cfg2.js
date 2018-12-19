//车型数量
var modelSize;
var peculiarity;
//特性数组
var array;
//2Y层数量
var cfgSize;
//版本头
var versionHead;
//总成零件号数组
var partIdArr = [];
//配置代码数组
var cfgValArr = [];
//版型数组
var modelArr = [];
//配置管理
var cfgmagArr = [];


var mainStatus;

function doRefresh(projectUid) {
    loadData(projectUid);
}

function loadData(projectUid) {
    if (!checkIsSelectProject(projectUid)) {
        return;
    }


    // $("#getExcel").attr("href","/hozon/bomAllCfg/getExcel?projectUid="+projectUid);
    $("#getExcel").attr("href","/hozon/bomAllCfg/getExcel?projectUid=1c128c60-84a2-4076-9b1c-f7093e56e4df");
    $.ajax({
        url: 'bomAllCfg/loadCfg0BomLineOfModel',
        type: 'GET',
        data: {
            'bdf': projectUid
        },
        success: function (myData) {
            //总成零件号数组
            partIdArr = [];
            //配置代码数组
            cfgValArr = [];
            //版型数组
            modelArr = [];
            //配置管理
            cfgmagArr = [];
            if (myData == null) {
                window.Ewin.alert({message: "查无数据，请联系项目经理或管理员"});
                return;
            }
            if (!myData.hasOwnProperty("data") || !myData.hasOwnProperty("model") || !myData.hasOwnProperty("array") || !myData.hasOwnProperty("main")) {
                window.Ewin.alert({message: "请按F5刷新页面重试"})
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
            mainStatus = main.status;
            var versionArr = main.version.split(".");
            versionHead = parseInt(versionArr[0]);
            var $table = $("#cfg0Table");
            //清空
            $table.html("");
            $table.removeClass("table").removeClass("table-striped");
            // $table.val("");
            var temp = "";
            for (var i = 0; i < rowCount; i++) {
                if (i == 1) {
                    temp +=
                        "<tr id='tr" + i + "'>" +
                        "<td></td>" +
                        "<td id='row" + i + "' colspan='10' style='border: #fff' align='left'>阶段：" + main.stage + "</td>" +
                        "</tr>";
                }
                else if (i == 2) {
                    temp +=
                        "<tr id='tr" + i + "'>" +
                        "<td></td>" +
                        "<td id='row" + i + "' colspan='10' style='border: #fff' align='left'>版本：" + main.version + "</td>" +
                        "</tr>";
                }
                else if (i == 3) {
                    temp +=
                        "<tr id='tr" + i + "'>" +
                        "<td></td>" +
                        "<td id='row" + i + "' colspan='10' style='border: #fff' align='left'>生效日期：" + main.effectiveDate + "</td>" +
                        "</tr>";
                }
                else if (i == 4) {
                    temp +=
                        "<tr id='tr" + i + "'>" +
                        "<td></td>" +
                        "<td id='row" + i + "' colspan='10' style='border: #fff' align='left'>状态：" + statusIntToStr(mainStatus) + "</td>" +
                        "</tr>";
                }
                else {
                    temp +=
                        "<tr id='tr" + i + "'>" +
                        "<td></td>" +
                        "<td id='row" + i + "' colspan='10' style='border: #fff' align='left'></td>" +
                        "</tr>";
                }
            }

            $table.append(temp);


            $("#tr0").append("<th id='th1'></th>");
            $("#tr1").append(
                "<th id='th0'>" +
                "<div style='width: 200px'  >品牌</div>" +
                "<div id='modelSize' style='display: none'>" + modelSize + "</div>" +
                "<div id='cfgSize' style='display: none'>" + cfgSize + "</div>" +
                "</th>");
            $("#tr2").append("<th id='th1'><div style='width: 200px'  >平台</div></th>");
            $("#tr3").append("<th id='th2'><div style='width: 200px'  >车型</div></th>");
            $("#tr4").append("<th id='th3'><div style='width: 200px'  >版型</div></th>");
            $("#tr5").append("<th id='th4'><div style='width: 200px'  >车身形式</div></th>");
            $("#tr6").append("<th id='th5'><div style='width: 200px'  >公告</div></th>");
            $("#tr7").append("<th id='th6'><div style='width: 200px'  >配置描述</div></th>");
            $("#tr8").append("<th id='th7'><div style='width: 200px'  >配置管理</div></th>");
            $("#tr9").append("<th id='th8'><div style='width: 200px'  >试制号</div></th>");
            $("#tr10").append("<th id='th9'><div style='width: 200px'  >商品号</div></th>");

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
                "<th ><div style='width: 150px' >备注</div></th>" +
                "</tr>"
            ;
            $table.append(t);
            //动态拼接后台数据
            for (var i = 0; i < data.length; i++) {
                var dataOfModel = data[i];
                var j = i + startIndex;
                var cfgId = "in_in_" + i;
                var delta = "<tr id='tr" + j + "' >" +
                    "<td style='text-align: center' id='" + cfgId + "' data-uid-coach=" + dataOfModel.bomLinePuid + ">" +
                    "<input class='btn btn-default' type='button' value='编辑' style='width: 50px' onclick='editorOrSave(this)'>" +
                    "<input class='btn btn-default' type='button' value='取消' style='width: 50px; display: none' onclick='cancelEditor(this)'>" +
                    // "<div id='" + cfgId + "' style='display: none'>" + dataOfModel.bomLinePuid + "</div>" +
                    "</td>"
                    // "<td style='text-align: center'><input class='btn btn-default' type='button' value='编辑' style='width: 50px' onclick='editorOrSave(this)'><input class='pCfg0ObjectId' type='text' value='"+dataOfModel.pCfg0ObjectId+"' hidden='hidden' disabled='disabled'><div id='"+cfgId+"' style='display: none'>"+dataOfModel.bomLinePuid+"</div></td>"
                    +
                    "<td>" +
                    "<div style='width: 50px' >" + (i + 1) + "</div>" +
                    "</td>";
                var aaa = 0;
                for (var index in dataOfModel) {
                    if (index == "bomLinePuid") {
                        continue;
                    }
                    // if (aaa == 6) {
                    //     delta = delta + "<td class='edit'><input type='text' value='" + dataOfModel[index] + "' style='display: none'><div style='width: 150px'>" + dataOfModel[index] + "</div></td>";
                    // } else
                    if (aaa == 2) {
                        partIdArr.push(dataOfModel[index]);
                        // delta = delta + "<td class='edit' id='td_part" + dataOfModel[index] + "'><div style='width: 150px;overflow: hidden' title='" + dataOfModel[index] + "'>" + dataOfModel[index] + "</div></td>";
                        delta = delta + "<td class='edit'><div style='width: 150px;overflow: hidden' title='" + dataOfModel[index] + "' class='td_partClass'>" + dataOfModel[index] + "</div></td>";
                    } else if (aaa == 7) {
                        //往特性数组中添加数据
                        cfgValArr.push(dataOfModel[index]);
                        var cfgSelect = "<select style='display: none;width: 83px'>";
                        cfgSelect += "<option></option>";
                        for (var j = 0; j < array.length; j++) {
                            var cfg = array[j];
                            if (cfg.pCfg0ObjectId == dataOfModel[index]) {
                                cfgSelect += "<option value='" + cfg.puid + "|" + cfg.pCfg0Desc + "' selected='selected' style='color: #c1ab58'>" + cfg.pCfg0ObjectId + "</span></option>";
                            } else {
                                cfgSelect += "<option value='" + cfg.puid + "|" + cfg.pCfg0Desc + "' style='color: #3fc3ee'>" + cfg.pCfg0ObjectId + "</span></option>";
                            }
                        }
                        cfgSelect += "</select>";
                        // delta = delta + "<td class='edit'><input type='text' value='"+dataOfModel[index]+"' style='display: none'><div style='width: 150px'>" + dataOfModel[index] + "</div></td>";
                        // delta = delta + "<td class='edit' id='td_cfg" + dataOfModel[index] + "'>" + cfgSelect + "<div style='width: 150px'>" + dataOfModel[index] + "</div></td>";
                        delta = delta + "<td class='edit'>" + cfgSelect + "<div style='width: 150px' class='td_cfgClass'>" + dataOfModel[index] + "</div></td>";
                    } else if (aaa == 8) {
                        var colorPartId = "colorPart" + i;
                        let isColor = null;
                        if (dataOfModel[index] == 'Y') {
                            isColor = "<span style='color: #ee4c40'>" + dataOfModel[index] + "</span>";
                        }
                        else {
                            isColor = "<span style='color: #bbd0ee'>" + dataOfModel[index] + "</span>";
                        }
                        delta += "<td class='edit'><div id='" + colorPartId + "'>" + isColor + "</div><select style='display: none'><option value='1' style='color: #ee4c40'>Y</option><option value='0' style='color: #bbd0ee'>N</option></select></td>"
                    } else if (aaa == 9) {
                        var messageId = "msg" + i;
                        var msgVal = "";
                        if (dataOfModel[index] == '0') {
                            msgVal = "<span style='color: #8449d6'>选配</span>";
                        } else if (dataOfModel[index] == '1') {
                            msgVal = "<span style='color: #43da4e'>标配</span>";
                        }
                        delta += "<td class='edit'><div id='" + messageId + "'>" + msgVal + "</div><select style='display: none'><option value='0' style='color:#8449d6 '>选配</option><option value='1' style='color: #43da4e'>标配</option></select></td>";

                    }
                    // else if(aaa>9){
                    // delta = delta + "<td class='edit'><select style='display: none'><option>-</option><option>●</option><option>○</option></select><div style='width: 150px'>" + dataOfModel[index] + "</div></td>";
                    else {
                        delta = delta + "<td class='edit'><div style='width: 150px;overflow: hidden' title='" + dataOfModel[index] + "'>" + dataOfModel[index] + "</div></td>";
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

                $("#tr0").append("<td id='in_" + i + "' data-model-uid='" + modeli.modelPuid + "'>" +
                    "<input class='btn btn-default' type='button' value='编辑' onclick='editPoint(this)'/>" +
                    "<input class='btn btn-default' type='button' value='取消' style='display: none' onclick='cancelEditorPoint(this)'/>" +
                    "<input class='btn btn-default' type='button' value='删除' style='background-color: red;display:none' onclick='deleteModel(this)'/>" +
                    // "<div style='display: none' >" + modeli.modelPuid + "</div>" +
                    "</td>");
                //品牌
                $("#tr1").append("<td ><div style='width: 200px'  >" + modeli.brand + "</div></td>");
                //平台
                $("#tr2").append("<td ><div style='width: 200px'  >" + modeli.platform + "</div></td>");
                //车型
                $("#tr3").append("<td ><div style='width: 200px'  >" + modeli.vehicle + "</div></td>");
                //往版型数组添加数据
                modelArr.push(v0);
                // $("#tr4").append("<td id='td_model" + v0 + "'><div style='width: 200px'  ><a href='javascript:void(0);' onclick='Botton(\"" + v1 + "\")'>" + v0 + "</a></div></td>");
                $("#tr4").append("<td ><div style='width: 200px'  class='td_modelClass'><a href='javascript:void(0);' onclick='Botton(\"" + v1 + "\")'>" + v0 + "</a></div></td>");
                $("#tr5").append("<td ><div style='width: 200px'  >" + modeli.pModelShape + "</div></td>");
                $("#tr6").append("<td ><div style='width: 200px'  >" + modeli.pModelAnnouncement + "</div></td>");
                $("#tr7").append("<td ><div style='width: 200px'  >" + modeli.pModelCfgDesc + "</div></td>");
                //往配置管理数组添加数据
                cfgmagArr.push(modeli.pModelCfgMng);
                // $("#tr8").append("<td id='td_cfgmag" + modeli.pModelCfgMng + "'><div style='width: 200px'  >" + modeli.pModelCfgMng + "</div></td>");
                $("#tr8").append("<td ><div style='width: 200px' class='td_cfgmagClass' >" + modeli.pModelCfgMng + "</div></td>");
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
                    if (versionHead >= 2) {
                        if (cfgObjectId == "") {
                            $("#" + trNumber).append(
                                "<td class='edit'>" +
                                "<select style='display: none' title='总成零件号:    " + bomLineId + "&#10总成零件名称:    " + bomLineName + "'>" +
                                "<option title='总成零件号:    " + bomLineId + "&#10总成零件名称:    " + bomLineName + "'>-</option>" +
                                "<option title='总成零件号:    " + bomLineId + "&#10总成零件名称:    " + bomLineName + "'>●</option>" +
                                "</select>" +
                                "<div id='" + pointId + "' style='width: 150px'>" + cfgObjectId + "</div>" +
                                "</td>"
                            );
                        } else if (cfgObjectId == "-") {
                            // $("#"+trNumber).append("<td class='edit'><select style='display: none'><option selected='selected'>-</option><option>●</option><option>○</option></select><div id='"+pointId+"' style='width: 150px'>" + point[j-10].point + "</div></td>");
                            $("#" + trNumber).append(
                                "<td class='edit'>" +
                                "<select style='display: none' title='总成零件号:    " + bomLineId + "&#10总成零件名称:    " + bomLineName + "'>" +
                                "<option selected='selected' title='总成零件号:    " + bomLineId + "&#10总成零件名称:    " + bomLineName + "'>-</option>" +
                                "<option title='总成零件号:    " + bomLineId + "&#10总成零件名称:    " + bomLineName + "'>●</option>" +
                                "</select><div id='" + pointId + "' style='width: 150px'>" + cfgObjectId + "</div>" +
                                "</td>"
                            );
                        } else if (cfgObjectId == "●") {
                            $("#" + trNumber).append(
                                "<td class='edit'>" +
                                "<select style='display: none' title='总成零件号:    " + bomLineId + "&#10总成零件名称:    " + bomLineName + "'>" +
                                "<option title='总成零件号:    " + bomLineId + "&#10总成零件名称:    " + bomLineName + "'>-</option>" +
                                "<option selected='selected' title='总成零件号:    " + bomLineId + "&#10总成零件名称:    " + bomLineName + "'>●</option>" +
                                "</select>" +
                                "<div id='" + pointId + "' style='width: 150px'>" + cfgObjectId + "</div>" +
                                "</td>"
                            );
                        }
                    } else {
                        //大版本为1时打点图3个选项
                        if (cfgObjectId == "") {
                            $("#" + trNumber).append("<td class='edit'>" +
                                "<select style='display: none' title='总成零件号:    " + bomLineId + "&#10总成零件名称:    " + bomLineName + "'>" +
                                "<option title='总成零件号:    " + bomLineId + "&#10总成零件名称:    " + bomLineName + "'>-</option>" +
                                "<option title='总成零件号:    " + bomLineId + "&#10总成零件名称:    " + bomLineName + "'>●</option>" +
                                "<option title='总成零件号:    " + bomLineId + "&#10总成零件名称:    " + bomLineName + "'>○</option>" +
                                "</select>" +
                                "<div id='" + pointId + "' style='width: 150px;color: #209ec9'>" + cfgObjectId + "</div>" +
                                "</td>");
                        } else if (cfgObjectId == "-") {
                            // $("#"+trNumber).append("<td class='edit'><select style='display: none'><option selected='selected'>-</option><option>●</option><option>○</option></select><div id='"+pointId+"' style='width: 150px'>" + point[j-10].point + "</div></td>");
                            $("#" + trNumber).append(
                                "<td class='edit'>" +
                                "<select style='display: none' title='总成零件号:    " + bomLineId + "&#10总成零件名称:    " + bomLineName + "'>" +
                                "<option selected='selected' title='总成零件号:    " + bomLineId + "&#10总成零件名称:    " + bomLineName + "'>-</option>" +
                                "<option title='总成零件号:    " + bomLineId + "&#10总成零件名称:    " + bomLineName + "'>●</option>" +
                                "<option title='总成零件号:    " + bomLineId + "&#10总成零件名称:    " + bomLineName + "'>○</option>" +
                                "</select>" +
                                "<div id='" + pointId + "' style='width: 150px;'>" + cfgObjectId + "</div>" +
                                "</td>");
                        } else if (cfgObjectId == "●") {
                            $("#" + trNumber).append(
                                "<td class='edit'>" +
                                "<select style='display: none' title='总成零件号:    " + bomLineId + "&#10总成零件名称:    " + bomLineName + "'>" +
                                "<option title='总成零件号:    " + bomLineId + "&#10总成零件名称:    " + bomLineName + "'>-</option>" +
                                "<option selected='selected' title='总成零件号:    " + bomLineId + "&#10总成零件名称:    " + bomLineName + "'>●</option>" +
                                "<option title='总成零件号:    " + bomLineId + "&#10总成零件名称:    " + bomLineName + "'>○</option>" +
                                "</select>" +
                                "<div id='" + pointId + "' style='width: 150px;color:#209ec9 '>" + cfgObjectId + "</div>" +
                                "</td>");
                        } else if (cfgObjectId == "○") {
                            $("#" + trNumber).append(
                                "<td class='edit'>" +
                                "<select style='display: none' title='总成零件号:    " + bomLineId + "&#10总成零件名称:    " + bomLineName + "'>" +
                                "<option title='总成零件号:    " + bomLineId + "&#10总成零件名称:    " + bomLineName + "'>-</option>" +
                                "<option title='总成零件号:    " + bomLineId + "&#10总成零件名称:    " + bomLineName + "'>●</option>" +
                                "<option selected='selected' title='总成零件号:    " + bomLineId + "&#10总成零件名称:    " + bomLineName + "'>○</option>" +
                                "</select>" +
                                "<div id='" + pointId + "' style='width: 150px;color:#ffae3f'>" + cfgObjectId + "</div>" +
                                "</td>"
                            );
                        }
                    }
                }
            }
            $table.addClass("table").addClass("table-striped");

            var td_part_parent = $("#td_part").parent();
            $("#td_part").remove();
            td_part_parent.append('<input type="text" class="form-control" name="platforms" id="td_part" placeholder="请输入总成零件号" onfocus="focusQuery(this)"/>');

            var td_cfg_parent = $("#td_cfg").parent();
            $("#td_cfg").remove();
            td_cfg_parent.append('<input type="text" class="form-control" name="modelnumber" id="td_cfg" placeholder="请输入配置代码" onfocus="focusQuery(this)"/>');

            var td_model_parent = $("#td_model").parent();
            $("#td_model").remove();
            td_model_parent.append('<input type="text" class="form-control" name="modelyear" id="td_model" placeholder="请输入版型" onfocus="focusQuery(this)"/>');

            var td_cfgmag_parent = $("#td_cfgmag").parent();
            $("#td_cfgmag").remove();
            td_cfgmag_parent.append('<input type="text" class="form-control" name="modelyear" id="td_cfgmag" placeholder="请输入配置管理" onfocus="focusQuery(this)"/>');

            $("#td_part").typeahead({
                source: partIdArr
            });
            $("#td_cfg").typeahead({
                source: cfgValArr
            });
            $("#td_model").typeahead({
                source: modelArr
            });
            $("#td_cfgmag").typeahead({
                source: cfgmagArr
            });
        },
        error: function (info) {
            if (info.status == 200) {
                window.Ewin.alert({message: "您似乎已操作超时，请尝试重新登录!"});
            }
            else if (info.status == 0 || 500 == info.status) {
                window.Ewin.alert({message: "服务器故障，请联系系统管理员"});
            }
            else {
                window.Ewin.alert({message: "未知错误，请稍后重试"});
            }
        }
    });
}


var projectPuid = $("#project", window.top.document).val();
$(document).ready(
    function () {
        loadData(getProjectUid()),
            $("#myButton").click(function () {
                loadData(getProjectUid());
            }),
            $("#addVehicle").click(function () {
                var flag = false;
                for (var i = 0; i < cfgSize; i++) {
                    var msgDivId = 'msg' + i;
                    var msgVal = $("#" + msgDivId).text();
                    if (msgVal == "" || msgVal == null) {
                        window.Ewin.alert({message: "请确认备注是否全部填写！"});
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    projectPuid = $("#project", window.top.document).val();
                    var url = "bomAllCfg/addVehicleModelPage2";
                    $.ajax({
                        url: "privilege/write?url=" + url,
                        type: "GET",
                        success: function (result) {
                            if (!result.success) {
                                window.Ewin.alert({message: result.errMsg});
                                return false;
                            }
                            else {
                                window.Ewin.dialog({
                                    // 这个puid就是车型模型的puid，直接修改了车型模型的基本信息（在bom系统维护的字段）
                                    title: "添加车型模型",
                                    url: "bomAllCfg/addVehicleModelPage2?projectPuid=" + projectPuid,
                                    gridId: "gridId",
                                    width: 350,
                                    height: 450
                                });
                            }
                        }
                    })
                }
            }),
            $("#setVersion").click(function () {
                var flag = false;
                for (var i = 0; i < cfgSize; i++) {
                    var msgDivId = 'msg' + i;
                    var msgVal = $("#" + msgDivId).text();
                    if (msgVal == "" || msgVal == null) {
                        window.Ewin.alert({message: "请确认备注是否全部填写！"});
                        flag = true;
                        break;
                    }
                    var cfgDivId = "in_in_" + i;
                    for (var j = 0; j < modelSize; j++) {
                        var modelDivId = "in_" + j;
                        var pointDivId = modelDivId + cfgDivId;
                        var point = $("#" + pointDivId).text();
                        if (point == "○" || point == "") {
                            window.Ewin.alert({message: '升版时打点图不能存在"○"或空，请重新填写！'});
                            flag = true;
                            break;
                        }
                    }
                    if (flag) {
                        break;
                    }
                }
                if (!flag) {
                    var url = "bomAllCfg/setStageOrVersionPage";
                    $.ajax({
                        url: "privilege/write?url=" + url,
                        type: "GET",
                        success: function (result) {
                            if (!result.success) {
                                window.Ewin.alert({message: result.errMsg});
                                return false;
                            }
                            else {
                                window.Ewin.dialog({
                                    // 这个puid就是车型模型的puid，直接修改了车型模型的基本信息（在bom系统维护的字段）
                                    title: "手动设置版本",
                                    url: "bomAllCfg/setStageOrVersionPage?projectUid=" + getProjectUid() + "&setName=version",
                                    gridId: "gridId",
                                    width: 450,
                                    height: 450
                                });
                            }
                        }
                    })
                }
            }),
            $("#setStage").click(function () {
                var url = "bomAllCfg/setStageOrVersionPage";
                $.ajax({
                    url: "privilege/write?url=" + url,
                    type: "GET",
                    success: function (result) {
                        if (!result.success) {
                            window.Ewin.alert({message: result.errMsg});
                            return false;
                        }
                        else {
                            window.Ewin.dialog({
                                // 这个puid就是车型模型的puid，直接修改了车型模型的基本信息（在bom系统维护的字段）
                                title: "手动设置阶段",
                                url: "bomAllCfg/setStageOrVersionPage?projectUid=" + getProjectUid() + "&setName=stage",
                                gridId: "gridId",
                                width: 450,
                                height: 450
                            });
                        }
                    }
                })
                undead();
            }),
            $("#release").click(function () {
                var url = "bomAllCfg/promote";
                $.ajax({
                    url: "privilege/write?url=" + url,
                    type: "GET",
                    success: function (result) {
                        if (!result.success) {
                            window.Ewin.alert({message: result.errMsg});
                            return false;
                        }
                        else {
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
                        }
                    }
                })
            }),
            $("#getVwo").click(function () {
                for (var i = 0; i < cfgSize; i++) {
                    var msgDivId = 'msg' + i;
                    var msgVal = $("#" + msgDivId).text();
                    if (msgVal == "" || msgVal == null) {
                        window.Ewin.alert({message: "请确认备注是否全部填写！"});
                        flag = true;
                        break;
                    }
                }
                var stage = $("#row1").html();
                if (stage == "阶段：") {
                    window.Ewin.alert({message: "请设置完阶段后再关联变更单号"});
                    return false;
                }

                if (mainStatus != 0 && mainStatus != 5) {
                    window.Ewin.alert({message: "非编辑状态不能关联变更单号"});
                    return false;
                }
                var url = "bomAllCfg/setChangeFromPage";
                $.ajax({
                    url: "privilege/write?url=" + url,
                    type: "GET",
                    success: function (result) {
                        if (!result.success) {
                            window.Ewin.alert({message: result.errMsg});
                            return false;
                        }
                        else {
                            window.Ewin.dialog({
                                // 这个puid就是车型模型的puid，直接修改了车型模型的基本信息（在bom系统维护的字段）
                                title: "选择变更表单",
                                url: "bomAllCfg/setChangeFromPage?projectUid=" + getProjectUid(),
                                gridId: "gridId",
                                width: 450,
                                height: 450
                            });
                        }
                    }
                })
            }),
            $("#goBackData").click(function () {
                if (mainStatus != 0 && mainStatus != 5) {
                    window.Ewin.alert({message: "非编辑状态不能撤销"});
                } else {
                    var url = "bomAllCfg/goBackData";
                    $.ajax({
                        url: "privilege/write?url=" + url,
                        type: "GET",
                        success: function (result) {
                            if (!result.success) {
                                window.Ewin.alert({message: result.errMsg});
                                return false;
                            }
                            else {
                                window.Ewin.confirm({title: '提示', message: '是否要撤销您所选择的记录？', width: 500}).on(function (e) {
                                    if (e) {
                                        $.ajax({
                                            type: "POST",
                                            url: "bomAllCfg/goBackData?projectUid=" + getProjectUid(),
                                            success: function (result) {
                                                layer.msg(result.msg, {icon: 1, time: 2000});
                                                window.location.reload();
                                            },
                                            error: function (result) {
                                                window.Ewin.alert({message: "撤销失败:" + result.msg});
                                            }
                                        });
                                    }
                                })
                            }
                        }
                    })
                }
            })
        // ,
        // $("#export").click(function(){
        //     $("#cfg0Table").tableExport({
        //         headings: true,                    // (Boolean), display table headings (th elements) in the first row
        //         formats: ["xls", "csv", "txt"],    // (String[]), filetypes for the export
        //         fileName: "id",                    // (id, String), filename for the downloaded file
        //         bootstrap: false,                   // (Boolean), style buttons using bootstrap
        //         position: "bottom"                 // (top, bottom), position of the caption element relative to table
        //     });
        // })
        // ,
        // $("#export").click(function(){
        //     $("#cfg0Table").table2excel({
        //         exclude: ".noExl",
        //         name: "全配置BOM一级清单表",
        //         filename: "xx",
        //         fileext: ".xlsx",
        //         exclude_img: false,
        //         exclude_links: false,
        //         exclude_inputs: false
        //     });
        // })
    }
)


$('.edit').on('click', function () {
    if (!($(this).find('div').hasClass('btn'))) {
        $(this).find('div').is(':visible') && ($(this).find('input').show().prev().hide(), $(this).parent().find('.btn').html('保存'));
    }
})


function Botton(id) {
    var url = "model/modModel";
    $.ajax({
        url: "privilege/write?url=" + url,
        type: "GET",
        success: function (result) {
            if (!result.success) {
                window.Ewin.alert({message: result.errMsg});
                return false;
            }
            else {
                window.Ewin.dialog({
                    url: "model/modModel?pModelPuid=" + id,
                    title: "修改",
                    width: 500,
                    height: 400,
                    gridId: "addPageOfModel"
                })
            }
        }
    })
}

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
})
var i = 0;
$(function () {
    $("#addTo").click(function () {
        $("#tr_1").append("<div><td>追加值" + (++i) + "</td></div>");
    });
})


function save() {
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
    var url = "bomAllCfg/savePoint";
    $.ajax({
        url: "privilege/write?url=" + url,
        type: "GET",
        success: function (result) {
            if (!result.success) {
                window.Ewin.alert({message: result.errMsg});
                return false;
            }
            else {
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
        }
    })
}

//编辑保存单行
function editorOrSave(but) {
    if ($(but).val() == '编辑') {

        projectPuid = $("#project", window.top.document).val();
        // var bomLineId = $(but).parent().find('div').text();
        //是一个jQuery对象
        var parent = $(but).parent();
        //jQuery对象jQuery获取方法data()方法
        var bomLineId = parent.data("uid-coach");
        var url = "bomAllCfg/query2YCfg";
        $.ajax({
            url: "privilege/write?url=" + url,
            type: "GET",
            success: function (result) {
                if (!result.success) {
                    window.Ewin.alert({message: result.errMsg});
                    return false;
                }
                else {
                    $.ajax({
                        type: "GET",
                        url: "bomAllCfg/query2YCfg?projectPuid=" + projectPuid + "&bomLineId=" + bomLineId,
                        success: function (result) {
                            var cfgs = result.cfgs;
                            var selfCfg = result.selfCfg;
                            $(but).val('保存');
                            $(but).parent().find('input:eq(1)').show();
                            $(but).parent().siblings().each(function (index, item) {
                                if (index == 8) {
                                    var divText = $(item).find('div').text();
                                    //从特性数组中删除对应特性
                                    for (var i = 0; i < cfgValArr.length; i++) {
                                        if (divText == cfgValArr[i]) {
                                            cfgValArr.splice(i, 1);
                                            break;
                                        }
                                    }
                                    $("#td_cfg").typeahead({
                                        source: cfgValArr
                                    });
                                    var select = $(item).find('select');
                                    $(select).empty();
                                    $(select).append("<option></option>");
                                    let _array = JSON.parse(JSON.stringify(array));
                                    // console.log(_array);
                                    for (let j = 0; j < cfgs.length; j++) {
                                        for (let i = 0; i < _array.length; i++) {
                                            if (cfgs[j].cfgCfg0Uid == _array[i].puid) {
                                                _array.splice(i, 1);
                                                break;
                                            }
                                        }

                                    }
                                    for (let i = 0; i < _array.length; i++) {
                                        $(select).append("<option value='" + _array[i].puid + "|" + _array[i].pCfg0Desc + "'><span style='color: #0c5460'>" + _array[i].pCfg0ObjectId + "</span></option>");
                                    }
                                    if (undefined != selfCfg) {
                                        for (let cfgIdIndex = 0; cfgIdIndex < array.length; cfgIdIndex++) {
                                            if (array[cfgIdIndex].puid == selfCfg.cfgCfg0Uid) {
                                                $(select).append("<option value='" + array[cfgIdIndex].puid + "|" + array[cfgIdIndex].pCfg0Desc + "' selected='selected'><span style='color: #b96104'>" + array[cfgIdIndex].pCfg0ObjectId + "</span></option>");
                                            }
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
                                } else if (index == 9) {
                                    var div = $(item).find('div');
                                    var select = $(item).find('select');
                                    var divText = $(div).text();
                                    // alert(divText);
                                    // $(select).find("option[text='N']").attr("selected",true);
                                    if (divText == "Y") {
                                        $(select).val("1");
                                    } else {
                                        $(select).val("0");
                                    }
                                    $(div).hide();
                                    $(select).show();
                                } else if (index == 10) {
                                    var select = $(item).find('select');
                                    var div = $(item).find('div');
                                    if ($(div).text() == "选配") {
                                        $(select).val("0");
                                    } else {
                                        $(select).val("1");
                                    }
                                    $(select).show();
                                    $(div).hide();
                                }
                            })

                        },
                        error: function (info) {
                            window.Ewin.alert({message: "系统错误:" + info.status});
                        }
                    });
                }
            }
        })
    } else {
        $(but).val('编辑');
        $(but).parent().find('input:eq(1)').hide();
        //2Y层id
        var parent = $(but).parent();
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
                bomLinePuid = parent.data("uid-coach");
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
                    // console.log(cfgPuid);
                    desc = str.substring(str.indexOf("|") + 1, str.length);
                    // cfgIndex = $(item).find('select').get(0).selectedIndex;
                }
            } else if (index == 9) {
                //获取是否为颜色件的值
                var div = $(item).find('div');
                var select = $(item).find('select');
                colorPart = $(select).val();
                $(div).text($(select).find("option:selected").text());
                // $(select).hide();
                // $(div).show();
            } else if (index == 10) {
                //获取备注值
                msgVal = $(item).find('select').val();
                return false;
            }
        });
        //保存2Y层对应的各数据
        $.ajax({
            type: "POST",
            url: "bomAllCfg/saveOneRow?bomLinePuid=" + bomLinePuid + "&cfgPuid=" + cfgPuid + "&colorPart=" + colorPart + "&msgVal=" + msgVal + "&projectPuid=" + projectPuid,
            contentType: 'application/json',
            success: function (result) {
                if (result.flag) {
                    //如果备注不为空则为所有车型对应该2Y层的打点图设置默认值
                    if (msgVal != "" && msgVal != null) {
                        var object = {};
                        var params = {}
                        for (var modIndex = 0; modIndex < modelSize; modIndex++) {
                            var modelDivId = "in_" + modIndex;
                            var modeId = $("#" + modelDivId).data("model-uid");
                            // var cfgDivId = $(but).parent().find("div").attr("id");
                            // var cfgId = $("#"+modelDivId+cfgDivId).text();
                            //备注为选配时
                            if (msgVal == 0) {
                                //大版本小于2时
                                $(but).parent().siblings().each(function (index, item) {
                                    if(index>10){
                                        var pointOld = $(item).find('div').text();
                                        if(pointOld==""){
                                            if (versionHead < 2) {
                                                params[modeId] = "○";
                                            } else {
                                                params[modeId] = "";
                                            }
                                        }
                                    }
                                });
                            } else if (msgVal == 1) {
                                //备注为标配时
                                params[modeId] = "●";
                            }
                        }
                        object[bomLinePuid] = params;
                        var json = JSON.stringify(object);
                        $.ajax({
                            type: "POST",
                            url: "bomAllCfg/saveBomLinePiont",
                            data: json,
                            dataType: 'json',
                            contentType: 'application/json',
                            success: function (respons) {
                                if (respons.updateFlag) {
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
                                            //为特性数组添加数据
                                            cfgValArr.push(selectText);
                                            $("#td_cfg").typeahead({
                                                source: cfgValArr
                                            });
                                            $(item).find('div').text(selectText);
                                            $(item).find('div').show();
                                            $(item).find('select').hide();
                                        } else if (index == 9) {
                                            var select = $(item).find('select').hide();
                                            var div = $(item).find('div').show();
                                        } else if (index == 10) {
                                            var select = $(item).find('select');
                                            var div = $(item).find('div');
                                            $(select).hide();
                                            $(div).text($(select).find("option:selected").text());
                                            if ($(div).text() == "选配") {
                                                $(div).css('color', '#8449d6');
                                            } else {
                                                $(div).css('color', '#43da4e');
                                            }
                                            $(div).show();
                                            msgVal = $(select).val();
                                        } else if (index > 10) {
                                            if (msgVal == 1) {
                                                // $(item).find('select').find("option[text='●']").attr("selected",true);
                                                $(item).find('div').text("●");
                                            } else if (msgVal == 0) {
                                                if($(item).find('div').text()=="") {
                                                    if (versionHead < 2) {
                                                        $(item).find('div').text("○");
                                                    } else {
                                                        $(item).find('div').text("");
                                                    }
                                                }
                                            }
                                        }
                                    })
                                } else {
                                    window.Ewin.alert({message: "修改失败:" + info.status});
                                }
                            },
                            error: function (info) {
                                window.Ewin.alert({message: "修改失败:" + info.status});
                            }

                        });
                    }


                } else {
                    window.Ewin.alert({message: "该特性以被其他2Y层关联！"});
                    $(but).parent().siblings().each(function (index, item) {
                        if (index == 7) {
                            // $(item).find('div').text("");
                            $(item).find('div').show();
                        } else if (index == 8) {
                            // $(item).find('div').text("");
                            $(item).find('div').show();
                            $(item).find('select').hide();
                        } else if (index == 9) {
                            $(item).find('select').hide();
                            $(item).find('div').show();
                        } else if (index == 10) {
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
            var selectText = $(item).find("div").text();
            cfgValArr.push(selectText);
            $("#td_cfg").typeahead({
                source: cfgValArr
            });
            // $(item).find('div').text("");
            $(item).find('div').show();
            $(item).find('select').hide();
        } else if (index == 9) {
            $(item).find('select').hide();
            $(item).find('div').show();
        } else if (index == 10) {
            $(item).find('select').hide();
            $(item).find('div').show();
        }
    })
}


//编辑保存打点图
function editPoint(but) {
    // var modelPuid = $(but).parent().find("div").text();
    var modelDivId = $(but).parent().attr("id");
    if ($(but).val() == '编辑') {
        var url = "bomAllCfg/savePoint";
        $.ajax({
            url: "privilege/write?url=" + url,
            type: "GET",
            success: function (result) {
                if (!result.success) {
                    window.Ewin.alert({message: result.errMsg});
                    return false;
                }
                else {
                    $(but).val('保存');
                    //显示取消和删除两个按钮
                    $(but).parent().find("input:eq(1)").show();
                    $(but).parent().find("input:eq(2)").show();
                    //查找当前车辆模型所有2Y层
                    for (var i = 0; i < cfgSize; i++) {
                        //打点图id
                        var pointId = modelDivId + "in_in_" + i;
                        //备注id
                        var msgId = "msg" + i;
                        //备注值
                        var msgVal = $("#" + msgId).text();
                        //当备注不为标配和空时才可以编辑打点图
                        if (msgVal != '标配' && msgVal != "") {
                            $("#" + pointId).parent().find("select").show();
                            $("#" + pointId).parent().find("div").hide();
                        }
                    }
                }
            }
        })
    } else {
        $(but).val('编辑');
        $(but).parent().find("input:eq(1)").hide();
        $(but).parent().find("input:eq(2)").hide();
        //将改车辆模型对应的所有2Y层的打点图、2Y层Id，车型ID封装成json格式的数据
        var object = {};
        var params = {};
        for (var j = 0; j < cfgSize; j++) {
            var cfgDivId = "in_in_" + j;
            // var cfgId = $("#" + cfgDivId).parent().parent().find("select:first").val();
            var bomLineId = $("#" + cfgDivId).data("uid-coach");
            var ponitId = modelDivId + cfgDivId;
            var ponitVal = $("#" + ponitId).parent().find("select").val();
            params[bomLineId] = ponitVal;
        }

        var modelId = $(but).parent().data("model-uid");
        object[modelId] = params;

        var json = JSON.stringify(object);
        //传给后台保存打点图
        var url = "bomAllCfg/savePoint";
        $.ajax({
            url: "privilege/write?url=" + url,
            type: "GET",
            success: function (result) {
                if (!result.success) {
                    window.Ewin.alert({message: result.errMsg});
                    return false;
                }
                else {
                    $.ajax({
                        type: "POST",
                        url: "bomAllCfg/savePoint?projectPuid=" + projectPuid,
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
                            } else {
                                window.Ewin.alert({message: "修改失败:" + info.status});
                            }
                        },
                        error: function (info) {
                            if (200 == info.status) {
                                window.Ewin.alert({message: "您似乎操作超时，请重新登录"});
                            }
                            else if (info.status == 0 || 500 == info.status) {
                                window.Ewin.alert({message: "保存失败，服务器故障，请联系系统管理员查看日志"});
                            }
                            else {
                                window.Ewin.alert({message: "未知错误，请稍后重试"});
                            }
                        }
                    });
                }
            }
        })
    }
}

//删除车辆模型
function deleteModel(obj) {
    // var isDelete = confirm("是否确认删除");
    var url = "bomAllCfg/deleteModel";
    $.ajax({
        url: "privilege/write?url=" + url,
        type: "GET",
        success: function (result) {
            if (!result.success) {
                window.Ewin.alert({message: result.errMsg});
                return false;
            }
            else {
                window.Ewin.confirm({title: '提示', message: '是否确认删除？', width: 500}).on(function (e) {
                    if (e) {
                        var modelId = $(obj).parent().data("model-uid");
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
                });
            }
        }
    })
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
    var modelDivId = $(but).parent().attr("id");
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

function statusIntToStr(mStatus) {
    if (mStatus == 0) {
        return "编辑";
    } else if (mStatus == 1) {
        return "已生效";
    } else if (mStatus == 5) {
        return "更新";
    } else if (mStatus == 10) {
        return "变更流程中"
    }
}
