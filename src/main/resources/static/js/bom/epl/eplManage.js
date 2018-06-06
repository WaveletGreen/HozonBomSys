// $(document).ready(
//     alert("花QQQQQ"),
window.onload = function () {
    $.ajax({
        url: "epl/record",
        type: "GET",
        success: function (data) {
            var data = data.data;
            console.log(data);
            var num = 1;
            var re = "<tr>" +  // style='position:fixed; background-color:#9FB6CD '
                "<th>序号</th>" + "<th>状态</th>" + "<th>层级</th>" + "<th>专业</th>" + "<th>级别</th>" + "<th>分组号</th>" +
                "<th>零件号</th>" + "<th>名称</th>" + "<th>英文名称</th>" + "<th>单位</th>" + "<th>分时租赁低配</th>" + "<th>分时租赁高配</th>" +
                "<th>图号</th>" + "<th>安装图号</th>" + "<th>图幅</th>" + "<th>料厚</th>" + "<th>材料1</th>" + "<th>材料2</th>" +
                "<th>材料3</th>" + "<th>密度</th>" + "<th>材料标准</th>" + "<th>表面处理</th>" + "<th>纹理编号/色彩编号</th>" + "<th>制造工艺</th>" +
                "<th>对称</th>" + "<th>重要度</th>" + "<th>是否法规件</th>" + "<th>法规件型号</th>" + "<th>黑白灰匣子件</th>" + "<th>开发类型</th>" +
                "<th>数据版本</th>" + "<th>目标重量</th>" + "<th>预估重量</th>" + "<th>实际重量</th>" + "<th>紧固件</th>" + "<th>紧固件规格</th>" +
                "<th>紧固件性能等级</th>" + "<th>扭矩</th>" + "<th>专业部门</th>" + "<th>责任工程师</th>" + "<th>供应商</th>" + "<th>供应商代码</th>" +
                "<th>采购工程师</th>" + "<th>备注</th>" + "<th>零件分类</th>" + "<th>零部件来源</th>" + "<th>自制/采购</th>" + "<th>焊接/装配</th>" +
                "<th>采购单元</th>" + "<th>车间1</th>" + "<th>车间2</th>" + "<th>生产线</th>" + "<th>工位</th>" + "<th>模具类别</th>" +
                "<th>外委件</th>" + "<th>颜色件</th>" + "<th>备件</th>" + "<th>备件编号</th>" + "<th>工艺路线</th>" + "<th>人工工时</th>" +
                "<th>节拍</th>" + "<th>焊点</th>" + "<th>机物料</th>" + "<th>标准件</th>" + "<th>工具</th>" + "<th>废品</th>" + "<th>供货状态</th>" +
                "<th>变更</th>"+"<th>变更号</th>"
                "</tr>";
            for (var i = 0; i < data.length; i++) {
                re = re + "<tr align='center'>" +
                    "<td>"+num+++"</td>"+
                    "<td>"+data[i].pState+"</td>"+
                    "<td>"+data[i].level+"</td>"+
                    "<td>"+data[i].pBomOfWhichDept+"</td>"+
                    "<td>"+data[i].rank+"</td>"+
                    "<td>"+data[i].groupNum+"</td>"+
                    "<td>"+data[i].lineId+"</td>"+
                    "<td>"+data[i].nameZh+"</td>"+
                    "<td>"+data[i].nameEn+"</td>"+
                    "<td>"+data[i].pUnit+"</td>"+
                    "<td>"+data[i].pRentLow+"</td>"+
                    "<td>"+data[i].pRentHigh+"</td>"+
                    "<td>"+data[i].pPictureNo+"</td>"+
                    "<td>"+data[i].pInstallPictureNo+"</td>"+
                    "<td>"+data[i].pMap+"</td>"+
                    "<td>"+data[i].pMaterialHigh+"</td>"+
                    "<td>"+data[i].pMaterial1+"</td>"+
                    "<td>"+data[i].pMaterial2+"</td>"+
                    "<td>"+data[i].pMaterial3+"</td>"+
                    "<td>"+data[i].pDensity+"</td>"+
                    "<td>"+data[i].pMaterialStandard+"</td>"+
                    "<td>"+data[i].pSurfaceManage+"</td>"+
                    "<td>"+data[i].pTextureNo+"</td>"+
                    "<td>"+data[i].pMadeArt+"</td>"+
                    "<td>"+data[i].pSymmetric+"</td>"+
                    "<td>"+data[i].pImportance+"</td>"+
                    "<td>"+data[i].pIsRulePart+"</td>"+
                    "<td>"+data[i].pRulePartNo+"</td>"+
                    "<td>"+data[i].pCasketPart+"</td>"+
                    "<td>"+data[i].pDevelopType+"</td>"+
                    "<td>"+data[i].pDataVersion+"</td>"+
                    "<td>"+data[i].pTargetHeight+"</td>"+
                    "<td>"+data[i].pEstimateHeight+"</td>"+
                    "<td>"+data[i].pActualHeight+"</td>"+
                    "<td>"+data[i].pFixture+"</td>"+
                    "<td>"+data[i].pFixtureSpec+"</td>"+
                    "<td>"+data[i].pFixtureLevel+"</td>"+
                    "<td>"+data[i].pTorque+"</td>"+
                    "<td>"+data[i].pMajorDept+"</td>"+
                    "<td>"+data[i].pDutyEngineer+"</td>"+
                    "<td>"+data[i].pSupplier+"</td>"+
                    "<td>"+data[i].pSupplierNo+"</td>"+
                    "<td>"+data[i].pBuyEngineer+"</td>"+
                    "<td>"+data[i].pRemark+"</td>"+
                    "<td>"+data[i].pItemClassification+"</td>"+
                    "<td>"+data[i].pItemResource+"</td>"+
                    "<td>"+data[i].resource+"</td>"+
                    "<td>"+data[i].type+"</td>"+
                    "<td>"+data[i].buyUnit+"</td>"+
                    "<td>"+data[i].workShop1+"</td>"+
                    "<td>"+data[i].workShop2+"</td>"+
                    "<td>"+data[i].productLine+"</td>"+
                    "<td>"+data[i].station+"</td>"+
                    "<td>"+data[i].mouldType+"</td>"+
                    "<td>"+data[i].outerPart+"</td>"+
                    "<td>"+data[i].colorPart+"</td>"+
                    "<td>"+data[i].sparePart+"</td>"+
                    "<td>"+data[i].sparePartNum+"</td>"+
                    "<td>"+data[i].processRoute+"</td>"+
                    "<td>"+data[i].laborHour+"</td>"+
                    "<td>"+data[i].rhythm+"</td>"+
                    "<td>"+data[i].solderJoint+"</td>"+
                    "<td>"+data[i].machineMaterial+"</td>"+
                    "<td>"+data[i].standardPart+"</td>"+
                    "<td>"+data[i].tools+"</td>"+
                    "<td>"+data[i].wasterProduct+"</td>"+
                    "<td>"+data[i].pSupplyState+"</td>"+
                    "<td>"+data[i].change+"</td>"+
                    "<td>"+data[i].changeNum+"</td>"+
                    "</tr>"

            }
            var table = $("#eplTable");
            table.html(re);
        }
    })
// )
//
//     var tTD; //用来存储当前更改宽度的Table Cell,避免快速移动鼠标的问题
//     var table = document.getElementById("eplTable");
//     for (j = 0; j < table.rows[0].cells.length; j++) {
//         table.rows[0].cells[j].onmousedown = function () {
// //记录单元格
//             tTD = this;
//             if (event.offsetX > tTD.offsetWidth - 10) {
//                 tTD.mouseDown = true;
//                 tTD.oldX = event.x;
//                 tTD.oldWidth = tTD.offsetWidth;
//             }
// //记录Table宽度
// //table = tTD; while (table.tagName != ‘TABLE') table = table.parentElement;
// //tTD.tableWidth = table.offsetWidth;
//         };
//         table.rows[0].cells[j].onmouseup = function () {
// //结束宽度调整
//             if (tTD == undefined) tTD = this;
//             tTD.mouseDown = false;
//             tTD.style.cursor = 'default';
//         };
//         table.rows[0].cells[j].onmousemove = function () {
// //更改鼠标样式
//             if (event.offsetX > this.offsetWidth - 10)
//                 this.style.cursor = 'col-resize';
//             else
//                 this.style.cursor = 'default';
// //取出暂存的Table Cell
//             if (tTD == undefined) tTD = this;
// //调整宽度
//             if (tTD.mouseDown != null && tTD.mouseDown == true) {
//                 tTD.style.cursor = 'default';
//                 if (tTD.oldWidth + (event.x - tTD.oldX)>0)
//                     tTD.width = tTD.oldWidth + (event.x - tTD.oldX);
// //调整列宽
//                 tTD.style.width = tTD.width;
//                 tTD.style.cursor = 'col-resize';
// //调整该列中的每个Cell
//                 table = tTD; while (table.tagName != 'TABLE') table = table.parentElement;
//                 for (j = 0; j < table.rows.length; j++) {
//                     table.rows[j].cells[tTD.cellIndex].width = tTD.width;
//                 }
// //调整整个表
// //table.width = tTD.tableWidth + (tTD.offsetWidth – tTD.oldWidth);
// //table.style.width = table.width;
//             }
//         };
//     }
}

