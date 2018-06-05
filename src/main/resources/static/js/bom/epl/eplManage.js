// $(document).ready(
//     alert("花QQQQQ"),
window.onload = function () {
    $.ajax({
        url: "",
        type: "GET",
        success: function (data) {
            var num = 1;
            var re = "<tr>" +  // style='position:fixed; background-color:#9FB6CD '
                "<th style='position:fixed; background-color:#9FB6CD;width: 50px '>序号</th>" + "<th>状态</th>" + "<th>层级</th>" + "<th>专业</th>" + "<th>级别</th>" + "<th>分组号</th>" +
                "<th>零件号</th>" + "<th>名称</th>" + "<th>英文名称</th>" + "<th>单位</th>" + "<th>分时租赁低配</th>" + "<th>分时租赁高配</th>" +
                "<th>图号</th>" + "<th>安装图号</th>" + "<th>图幅</th>" + "<th>料厚</th>" + "<th>材料1</th>" + "<th>材料2</th>" +
                "<th>材料3</th>" + "<th>密度</th>" + "<th>材料标准</th>" + "<th>表面处理</th>" + "<th>纹理编号/色彩编号</th>" + "<th>制造工艺</th>" +
                "<th>对称</th>" + "<th>重要度</th>" + "<th>是否法规件</th>" + "<th>法规件型号</th>" + "<th>黑白灰匣子件</th>" + "<th>开发类型</th>" +
                "<th>数据版本</th>" + "<th>目标重量</th>" + "<th>预估重量</th>" + "<th>实际重量</th>" + "<th>紧固件</th>" + "<th>紧固件规格</th>" +
                "<th>紧固件性能等级</th>" + "<th>扭矩</th>" + "<th>专业部门</th>" + "<th>责任工程师</th>" + "<th>供应商</th>" + "<th>供应商代码</th>" +
                "<th>采购工程师</th>" + "<th>备注</th>" + "<th>零件分类</th>" + "<th>零部件来源</th>" + "<th>自制/采购</th>" + "<th>焊接/装配</th>" +
                "<th>采购单元</th>" + "<th>车间1</th>" + "<th>车间2</th>" + "<th>生产线</th>" + "<th>工位</th>" + "<th>模具类别</th>" +
                "<th>外委件</th>" + "<th>颜色件</th>" + "<th>备件</th>" + "<th>备件编号</th>" + "<th>工艺路线</th>" + "<th>人工工时</th>" +
                "<th>节拍</th>" + "<th>焊点</th>" + "<th>机物料</th>" + "<th>标准件</th>" + "<th>工具</th>" + "<th>废品</th>" + "<th>是否供货件</th>" +
                "</tr>";
            for (var i = 0; i < data.length; i++) {
                re = re + "<tr>" +
                    "<td style='position:fixed; background-color:#9FB6CD;width: 50px '>"+num+++"</td>"+
                    "<td>"+data[i].a+"</td>"+
                    "<td>"+data[i].a+"</td>"+
                    "<td>"+data[i].a+"</td>"+
                    "<td>"+data[i].a+"</td>"+
                    "<td>"+data[i].a+"</td>"+
                    "<td>"+data[i].a+"</td>"+
                    "<td>"+data[i].a+"</td>"+
                    "<td>"+data[i].a+"</td>"+
                    "<td>"+data[i].a+"</td>"+
                    "<td>"+data[i].a+"</td>"+
                    "<td>"+data[i].a+"</td>"+
                    "<td>"+data[i].a+"</td>"+
                    "<td>"+data[i].a+"</td>"+
                    "<td>"+data[i].a+"</td>"+
                    "<td>"+data[i].a+"</td>"+
                    "<td>"+data[i].a+"</td>"+
                    "<td>"+data[i].a+"</td>"+
                    "<td>"+data[i].a+"</td>"+
                    "<td>"+data[i].a+"</td>"+
                    "<td>"+data[i].a+"</td>"+
                    "<td>"+data[i].a+"</td>"+
                    "<td>"+data[i].a+"</td>"+
                    "<td>"+data[i].a+"</td>"+
                    "<td>"+data[i].a+"</td>"+
                    "<td>"+data[i].a+"</td>"+
                    "<td>"+data[i].a+"</td>"+
                    "<td>"+data[i].a+"</td>"+
                    "<td>"+data[i].a+"</td>"+
                    "<td>"+data[i].a+"</td>"+
                    "<td>"+data[i].a+"</td>"+
                    "<td>"+data[i].a+"</td>"+
                    "<td>"+data[i].a+"</td>"+
                    "<td>"+data[i].a+"</td>"+
                    "<td>"+data[i].a+"</td>"+
                    "<td>"+data[i].a+"</td>"+
                    "<td>"+data[i].a+"</td>"+
                    "<td>"+data[i].a+"</td>"+
                    "<td>"+data[i].a+"</td>"+
                    "<td>"+data[i].a+"</td>"+
                    "<td>"+data[i].a+"</td>"+
                    "<td>"+data[i].a+"</td>"+
                    "<td>"+data[i].a+"</td>"+
                    "<td>"+data[i].a+"</td>"+
                    "<td>"+data[i].a+"</td>"+
                    "<td>"+data[i].a+"</td>"+
                    "<td>"+data[i].a+"</td>"+
                    "<td>"+data[i].a+"</td>"+
                    "<td>"+data[i].a+"</td>"+
                    "<td>"+data[i].a+"</td>"+
                    "<td>"+data[i].a+"</td>"+
                    "<td>"+data[i].a+"</td>"+
                    "<td>"+data[i].a+"</td>"+
                    "<td>"+data[i].a+"</td>"+
                    "<td>"+data[i].a+"</td>"+
                    "<td>"+data[i].a+"</td>"+
                    "<td>"+data[i].a+"</td>"+
                    "<td>"+data[i].a+"</td>"+
                    "<td>"+data[i].a+"</td>"+
                    "<td>"+data[i].a+"</td>"+
                    "<td>"+data[i].a+"</td>"+
                    "<td>"+data[i].a+"</td>"+
                    "<td>"+data[i].a+"</td>"+
                    "<td>"+data[i].a+"</td>"+
                    "<td>"+data[i].a+"</td>"+
                    "<td>"+data[i].a+"</td>"+
                    "<td>"+data[i].a+"</td>"+
                    "</tr>"

            }
            var table = $("#eplTable");
            table.html(re);
        }
    })
// )
}

