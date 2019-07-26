//初始化页面
function loadTable(result) {
    var $table = $("#routingDataTable");
    var mainResult = result.mainResult;
    var modelResult = result.modelResult;
    var withCfgResult = result.withCfgResult;
    var pointResult = result.pointResult;

    mainId = mainResult.mainId;

    // if(mainResult==null){
    //
    //     var tr = "<tr><td style='background-color: #00B83F'>不存在全配置BOM变更数据</td></tr>";
    //     $table.append(tr)
    //     return false;
    // }




    var rowCount = 10;

    $table.html("");
    $table.removeClass("table").removeClass("table-striped");

    var temp = "";
    for (var i = 0; i < rowCount; i++) {
        if (i == 1) {
            temp +=
                "<tr id='tr" + i + "'>" +
                "<td id='row" + i + "' colspan='9' style='border: #fff' align='left'>阶段：" + mainResult.stage + "</td>" +
                "</tr>";
        }
        else if (i == 2) {
            temp +=
                "<tr id='tr" + i + "'>" +
                "<td id='row" + i + "' colspan='9' style='border: #fff' align='left'>版本：" + mainResult.version + "</td>" +
                "</tr>";
        }
        else if (i == 3) {
            temp +=
                "<tr id='tr" + i + "'>" +
                "<td id='row" + i + "' colspan='9' style='border: #fff' align='left'>生效日期：" + mainResult.effectiveDate + "</td>" +
                "</tr>";
        }
        else {
            temp +=
                "<tr id='tr" + i + "'>" +
                "<td id='row" + i + "' colspan='9' style='border: #fff' align='left'></td>" +
                "</tr>";
        }
    }
    $table.append(temp);
    $("#tr0").append(
        "<th id='th0'>" +
        "<div style='width: 200px'  >品牌</div>" +
        "</th>");
    $("#tr1").append("<th id='th1'><div style='width: 200px'  >平台</div></th>");
    $("#tr2").append("<th id='th2'><div style='width: 200px'  >车型</div></th>");
    $("#tr3").append("<th id='th3'><div style='width: 200px'  >版型</div></th>");
    $("#tr4").append("<th id='th4'><div style='width: 200px'  >车身形式</div></th>");
    $("#tr5").append("<th id='th5'><div style='width: 200px'  >公告</div></th>");
    $("#tr6").append("<th id='th6'><div style='width: 200px'  >配置描述</div></th>");
    $("#tr7").append("<th id='th7'><div style='width: 200px'  >配置管理</div></th>");
    $("#tr8").append("<th id='th8'><div style='width: 200px'  >试制号</div></th>");
    $("#tr9").append("<th id='th9'><div style='width: 200px'  >商品号</div></th>");

    var t =
        "<tr>" +
        "<th ><div style='width: 150px;text-align: center'>操作类型</div></th>" +
        "<th ><div style='width: 150px;text-align: center'>系统</div></th>" +
        "<th ><div style='width: 150px;text-align: center'>总成零件号</div></th>" +
        "<th ><div style='width: 150px;text-align: center'>总成零件名称</div></th>" +
        "<th ><div style='width: 150px;text-align: center'>总成英文名称</div></th>" +
        "<th ><div style='width: 150px;text-align: center'>责任工程师</div></th>" +
        //描述的宽度应该大一点，保持可用
        "<th ><div style='width: 150px;text-align: center'>配置描述</div></th>" +
        "<th ><div style='width: 150px;text-align: center'>配置代码</div></th>" +
        "<th ><div style='width: 150px;text-align: center'>是否颜色件</div></th>" +
        "<th ><div style='width: 150px;text-align: center'>备注</div></th>" +
        "</tr>";
    $table.append(t);

    /*************画2Y层***************/
    for(var i=0;i<withCfgResult.pBomLineId.length;i++){
        var delta =  "<tr id='bomLine"+i+"'></tr>";
        $table.append(delta);
    }
    for(var i=0;i<withCfgResult.pBomLineId.length;i++){
        var tr = $("#bomLine"+i);
        var tdStr = "<td style='text-align: center;background-color: #00B83F' class='edit'>"+withCfgResult.operationType[i]+"</td>";
        tdStr += "<td style='text-align: center;background-color: #00B83F' class='edit'>"+withCfgResult.pBomOfWhichDept[i]+"</td>";
        tdStr += "<td style='text-align: center;background-color: #00B83F' class='edit'>"+withCfgResult.pBomLineId[i]+"</td>";
        tdStr += "<td style='text-align: center;background-color: #00B83F'class='edit'>"+withCfgResult.pBomLineName[i]+"</td>";
        tdStr += "<td style='text-align: center;background-color: #00B83F' class='edit'>"+withCfgResult.pH9featureenname[i]+"</td>";
        tdStr += "<td style='text-align: center;background-color: #00B83F' class='edit'>"+withCfgResult.owningUser[i]+"</td>";
        tdStr += "<td style='text-align: center;background-color: #00B83F'class='edit'>"+withCfgResult.pCfg0Desc[i]+"</td>";
        tdStr += "<td style='text-align: center;background-color: #00B83F'class='edit'>"+withCfgResult.pCfg0ObjectId[i]+"</td>";
        tdStr += "<td style='text-align: center;background-color: #00B83F'class='edit'>"+withCfgResult.isColorPart[i]+"</td>";
        tdStr += "<td style='text-align: center;background-color: #00B83F'class='edit'>"+withCfgResult.comment[i]+"</td>";
        tr.append(tdStr);
    }
    /******画车型模型*******/
    modelAppend(0,modelResult.brand);
    modelAppend(1,modelResult.platform);
    modelAppend(2,modelResult.vehicle);
    modelAppend(3,modelResult.modelVersion);
    modelAppend(4,modelResult.modelShape);
    modelAppend(5,modelResult.modelAnnouncement);
    modelAppend(6,modelResult.modelCfgDesc);
    modelAppend(7,modelResult.modelCfgMng);
    modelAppend(8,modelResult.modelTrailNum);
    modelAppend(9,modelResult.modelGoodsNum);

    /************画打点图****************/

    for(var i=0;i<withCfgResult.pBomLineId.length;i++){
        var tr = $("#bomLine"+i);
        var tdStr = "";
        for(var j=0;j<modelResult.brand.length;j++){
            var pointId = "c"+i+"m"+j;
            var point = pointResult[pointId];
            tdStr += "<td style='text-align: center;background:#0c8fe2'class='edit'>"+point+"</td>"
        }
        tr.append(tdStr);
    }

}

function modelAppend(index,data) {
    var tdStr = "";
    for(var i=0;i<data.length;i++){
        tdStr += "<td style='text-align: center;background:#b7bc31' class='edit'>" + data[i] + "</td>";
    }
    $("#tr"+index).append(tdStr);
}


function deleteBom(){
    var url = "vwo/deleteChangeBomAll";
    $.ajax({
        url: "../../../privilege/write?url=" + url,
        type: "GET",
        success: function (result) {
            if (!result.success) {
                window.Ewin.alert({message: result.errMsg});
                return false;
            }
            else {
                $.ajax({
                    type: "POST",
                    url: "/hozon/vwo/deleteChangeBomAll?mainId="+mainId+"&orderId="+orderChangeId,
                    contentType: "application/json",
                    success: function (result) {
                        if (result.status) {
                            layer.msg('删除成功', {icon: 1, time: 2000})
                        } else if (!result.status) {
                            window.Ewin.alert({message: result.msg});
                        }
                        $table.bootstrapTable("refresh");
                    },
                    error: function (info) {
                        window.Ewin.alert({message: "操作失败:" + info.status});
                    }
                })
            }
        }
    })
}