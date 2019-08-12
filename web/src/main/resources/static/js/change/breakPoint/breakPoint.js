/**
 * Created with IntelliJ IDEA.
 * User: xlf
 * Date: 2018/12/7
 * Time: 10:18
 */
$(document).ready(
    // $("#queryColorSet").click(function () {
    (function () {
        //手动刷新按钮
        $("#refresh").click(function () {
            loadData();
        });
        loadData();
    })
);

function loadData() {
    var $table = $("#codpwoTable");
    $table.bootstrapTable('destroy');
    $table.bootstrapTable({
        url: "codpwo/codpwoFromList",
        method: "GET",
        height: $(window.parent.document).find("#wrapper").height() - 150,//$(window.parent.document).find("#wrapper").height() - document.body.offsetHeight-100,
        width: $(window).width(),
        showToggle: false,                   //是否显示详细视图和列表视图的切换按钮
        showRefresh: true,                  //是否显示刷新按钮
        pagination: true,                   //是否显示分页（*）
        pageNumber: 1,                       //初始化加载第一页，默认第一页
        pageSize: 10,                       //每页的记录行数（*）
        pageList: [10, 30, 50, 100, 500, 1000],//可供选择的每页的行数（*）
        clickToSelect: true,                // 单击某一行的时候选中某一条记录
        smartDisplay: false,
        //search:true,                      //输入框“查询”
        sortable: true,
        sortName: 'pid',//一个对应字段
        sortOrder: 'asc',
        // sidePagination: "client",           //客户端/客户端分页
        formId: "queryBreakpointForm",//query+对应表名

        /**列信息，需要预先定义好*/
        columns: [
            {
                field: '',
                title: '序号',
                align: 'center',
                width: 50,
                formatter: function (value, row, index) {
                    var options = $table.bootstrapTable('getOptions');
                    return options.pageSize * (options.pageNumber - 1) + index + 1;
                }
            },
            // {
            //     field: 'ck',
            //     checkbox: true
            // },
            // {field: 'pid', title: '主键', sortable: true, sortOrder: 'asc',},
            // {field: 'pPackno', title: '数据包号', align: 'center', valign: 'middle', sortable: true, sortOrder: 'asc',},
            // {field: 'pItem', title: '行号', align: 'center', valign: 'middle', sortable: true, sortOrder: 'asc',},
            {field: 'tcEcn', title: 'TC-ECN', align: 'center', valign: 'middle', sortable: true, sortOrder: 'asc',},
            {field: 'ecn', title: 'SAP-ECN', align: 'center', valign: 'middle', sortable: true, sortOrder: 'asc',},
            {field: 'breakDate', title: '断点日期', align: 'center', valign: 'middle', sortable: true, sortOrder: 'asc',},
            {field: 'preEcn', title: '断点前ECN', align: 'center', valign: 'middle', sortable: true, sortOrder: 'asc',},
            {field: 'preEcnFinishDate', title: '前ECN结束日期', align: 'center', valign: 'middle', sortable: true, sortOrder: 'asc',},
            {field: 'startVin', title: '断点起始vin', align: 'center', valign: 'middle', sortable: true, sortOrder: 'asc',},
            {field: 'executeUserNo', title: '断点执行工号', align: 'center', valign: 'middle', sortable: true, sortOrder: 'asc',},
            {field: 'breakHandleDate', title: '断点处理日期', align: 'center', valign: 'middle', sortable: true, sortOrder: 'asc',},
            {field: 'breakHandlerTime', title: '断点处理时间', align: 'center', valign: 'middle', sortable: true, sortOrder: 'asc',},
            {
                field: 'feedbackTime',
                title: '断点回传到BOM系统时间',
                align: 'center',
                valign: 'middle',
                sortable: true,
                sortOrder: 'asc',
                //——修改——获取日期列的值进行转换
                formatter: function (value, row, index) {
                    return changeDateFormat(value)
                }
            }

        ]
    });
    //修改——转换日期格式(时间戳转换为datetime格式)
    function changeDateFormat(cellval) {
        if (cellval != null) {
            var date = new Date(parseInt(cellval));
            var month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
            var currentDate = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
            return date.getFullYear() + "-" + month + "-" + currentDate /*+ " " + hour + ":" + mins*/;
        }
    }
}