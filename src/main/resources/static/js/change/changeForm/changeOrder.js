/**
 * Created with IntelliJ IDEA.
 * User: xlf
 * Date: 2018/11/14
 * Time: 9:26
 */
$(document).ready(function () {
    var id = $("#id").val();
    var isFromTc = $("#isFromTc").val();
    var projectId = $("#project", window.top.document).val();
    var FileForm = document.getElementById("FileForm");
    var TCForm = document.getElementById("TCForm");
    var BOMForm = document.getElementById("BOMForm");
    if (isFromTc == 0) {
        TCForm.style.display = "none";
        FileForm.style.display = "none";
        BOMForm.style.display = "block";
    } else if (isFromTc == 1) {
        TCForm.style.display = "block";
        FileForm.style.display = "block";
        BOMForm.style.display = "block";
    }
    $.ajax({
        url: "../change/data/order/hyper?orderId=" + id,
        type: "GET",
        success: function (result) {
            var data = result.data;
            var table = "<tr>";
            table += "<th><a id='featureId' href='../change/data/feature/page?projectId=" + projectId + "&orderId=" + id + "'>" + data[7].name + "</a></th>";
            table += "<th><a id='bomCfgId' href='../change/data/bomCfg/page?projectId=" + projectId + "&orderId=" + id + "'>" + data[10].name + "</a></th>";
            table += "<th><a id='modelColorCfgId' href='../change/data/modelColorCfg/page?projectId=" + projectId + "&orderId=" + id + "'>" + data[8].name + "</a></th>";
            table += "<th><a id='materielFeatureId' href='../change/data/materielFeature/page?projectId=" + projectId + "&orderId=" + id + "'>" + data[9].name + "</a></th>";
            table += "<th><a id='relevanceId' href='../change/data/relevance/page?projectId=" + projectId + "&orderId=" + id + "'>"+data[11].name+"</a></th>";
            table += "<th><a id='ebomId' href='../change/data/ebom/page?projectId=" + projectId + "&orderId=" + id + "'>" + data[0].name + "</a></th>";
            table += "<th><a id='pbomId' href='../change/data/pbom/page?projectId=" + projectId + "&orderId=" + id + "'>" + data[1].name + "</a></th>";
            table += "<th><a id='mbomId' href='../change/data/mbom/page?projectId=" + projectId + "&orderId=" + id + "'>" + data[2].name + "</a></th>";
            table += "<th><a id='productionId' href='../change/data/mbom/page?projectId=" + projectId + "&orderId=" + id + "&type=1" + "'>" + data[3].name + "</a></th>";
            table += "<th><a id='financialId' href='../change/data/mbom/page?projectId=" + projectId + "&orderId=" + id + "&type=6" + "'>" + data[4].name + "</a></th>";
            table += "<th><a id='materialId' href='../change/data/material/page?projectId=" + projectId + "&orderId=" + id + "'>" + data[5].name + "</a></th>";
            table += "<th><a id='routingId' href='../change/data/routing/page?projectId=" + projectId + "&orderId=" + id + "'>" + data[6].name + "</a></th>";
            table += "</tr>"
            $("#changeOrderTable").html(table);
            // var featureId = document.getElementById("featureId");
            // var bomCfgId = document.getElementById("bomCfgId");
            // var modelColorCfgId = document.getElementById("modelColorCfgId");
            // var materielFeatureId = document.getElementById("materielFeatureId");
            // var ebomId = document.getElementById("ebomId");
            // var pbomId = document.getElementById("pbomId");
            // var mbomId = document.getElementById("mbomId");
            // var productionId = document.getElementById("productionId");
            // var financialId = document.getElementById("financialId");
            // var materialId = document.getElementById("materialId");
            // var routingId = document.getElementById("routingId");
            if (data[7].status == 0) {
                // featureId.style.display = "none";
                $("#featureId").hide();
            }
            if (data[10].status == 0) {
                // bomCfgId.style.display = "none";
                $("#bomCfgId").hide();
            }
            if (data[8].status == 0) {
                // modelColorCfgId.style.display = "none";
                $("#modelColorCfgId").hide();
            }
            if (data[9].status == 0) {
                // materielFeatureId.style.display = "none";
                $("#materielFeatureId").hide();
            }
            if (data[11].status == 0) {
                // materielFeatureId.style.display = "none";
                $("#relevanceId").hide();
            }
            if (data[0].status == 0) {
                // ebomId.style.display = "none";
                $("#ebomId").hide();
            }
            if (data[1].status == 0) {
                // pbomId.style.display = "none";
                $("#pbomId").hide();
            }
            if (data[2].status == 0) {
                // mbomId.style.display = "none";
                $("#mbomId").hide();
            }
            if (data[3].status == 0) {
                // productionId.style.display = "none";
                $("#productionId").hide();
            }
            if (data[4].status == 0) {
                // financialId.style.display = "none";
                $("#financialId").hide();
            }
            if (data[5].status == 0) {
                // materialId.style.display = "none";
                $("#materialId").hide();
            }
            if (data[6].status == 0) {
                // routingId.style.display = "none";
                $("#routingId").hide();
            }
        }
    })
})

function doReturn() {
    window.location.href = "javascript:history.go(-1);"
}

$(document).ready(function () {
    var changeNo = $("#changeNo").val();
    // $.ajax({
    //     url : "../change/date/changeFile?changeOrderId="+changeNo,
    //     type : "GET",
    //     success : function (result) {
    //         var a = result.files;
    //         alert(JSON.stringify(a));
    //         // for(let i in files){
    //         //     alert(files[i].showName);
    //         // }
    //     }
    // });

    $.ajax({
        url: "../change/data/changeFile?changeNo=" + changeNo,
        type: "GET",
        success: function (result) {
            var files = result.files;
            var temp = "";
            for(let i in files) {
                temp += "<div class='col-lg-2 col-md-2'><input id='file"+i+"' name='filePath' type='radio' value='"+files[i].id+"'/><span style='text-align: center;color: red'>  "+files[i].showName+"</span></div>";
            }
            $("#fileFormDiv").html(temp);
        }
    });



    $.ajax({
        url: "../change/data/tc?formId=" + changeNo,
        type: "GET",
        success: function (result) {
            var data = result.data;
            var temp = "<table >" +
                "<tr><th>零件号</th><th>版本</th></tr>";
            for (var i = 0; i < data.length; i++) {
                temp = temp + "<tr><td>" + data[i].itemId + "</td><td>" + data[i].itemRevision + "</td></tr>"
            }
            temp = temp + "</table>"
            $("#changeTCTable").html(temp);
        }
    });


})



function download() {
    var form = $("#changeFileForm");
    form.submit();
}