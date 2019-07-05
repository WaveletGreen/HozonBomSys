/**
 * Created with IntelliJ IDEA.
 * User: xlf
 * Date: 2018/12/7
 * Time: 10:05
 */

/**
 * Created with IntelliJ IDEA.
 * User: xlf
 * Date: 2018/12/6
 * Time: 10:21
 */

$(document).ready((function () {
    var projectPuid = $("#project", window.top.document).val();
    var url = ""
    initTable(url);
}))

function doRefresh(projectPuid) {
    $('#paymentTable').bootstrapTable('destroy');
    var url = ""
    initTable(url);
}

function doQuery() {
    var projectPuid = $("#project", window.top.document).val();
    var url = ""
    initTable(url);
    $('#paymentTable').bootstrapTable('destroy');
}


function initTable(url) {
    var projectPuid = $("#project", window.top.document).val();
    if (!checkIsSelectProject(projectPuid)) {
        return;
    }
    var $table = $("#paymentTable");
    $table.bootstrapTable({
        url: url,
        method: 'GET',
        dataType: 'json',
        sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
        height: $(window.parent.document).find("#wrapper").height() - 150,
        width: $(window).width(),
        formId: "queryPayment",
        undefinedText: "",//当数据为 undefined 时显示的字符
        pagination: true,
        pageNumber: 1,                       //初始化加载第一页，默认第一页
        pageSize: 20,                       //每页的记录行数（*）
        pageList: ['ALL', 10, 20, 50, 100, 200, 500, 1000],        //可供选择的每页的行数（*）
        sortOrder: "asc",                   //排序方式
        clickToSelect: true,// 单击某一行的时候选中某一条记录
        showColumns: true, //是否显示所有的列
        showToggle: false,                   //是否显示详细视图和列表视图的切换按钮
        showRefresh: true,                  //是否显示刷新按钮
        columns:[
            [
                {
                    field: 'ck',
                    colspan: 1, rowspan: 2,
                    checkbox: true
                },
                {
                    field: '',
                    title: '序号',
                    align: 'center',
                    valign: 'middle',
                    width: 50,
                    colspan: 1,
                    rowspan: 2,
                    formatter: function (value, row, index) {
                        var options = $table.bootstrapTable('getOptions');
                        return options.pageSize * (options.pageNumber - 1) + index + 1;

                    }
                },
                {field: 'status', title: '项目类别', align: 'center', valign: 'middle',colspan: 1, rowspan: 2,},
                {field: 'status', title: '项目名称', align: 'center', valign: 'middle',colspan: 1, rowspan: 2,},
                {field: 'status', title: '项目阶段', align: 'center', valign: 'middle',colspan: 1, rowspan: 2,},
                {field: 'status', title: '一级科目', align: 'center', valign: 'middle',colspan: 1, rowspan: 2,},
                {field: 'status', title: '二级科目', align: 'center', valign: 'middle',colspan: 1, rowspan: 2,},
                {field: 'status', title: '三级科目', align: 'center', valign: 'middle',colspan: 1, rowspan: 2,},
                {field: 'status', title: '需求部门', align: 'center', valign: 'middle',colspan: 1, rowspan: 2,},
                {field: 'status', title: '项目费用描述', align: 'center', valign: 'middle',colspan: 1, rowspan: 2,},
                {field: 'status', title: 'WBS号', align: 'center', valign: 'middle',colspan: 1, rowspan: 2,},
                {field: 'status', title: '预算金额', align: 'center', valign: 'middle',colspan: 1, rowspan: 1,},
                {field: 'status', title: '2019年度(万元)', align: 'center', valign: 'middle',colspan: 16, rowspan: 1,},
                {field: 'status', title: '历史年度累计', align: 'center', valign: 'middle',colspan: 1, rowspan: 1,},
                {field: 'status', title: '本年度', align: 'center', valign: 'middle',colspan: 1, rowspan: 1,},
                {field: 'status', title: 'N+1年度', align: 'center', valign: 'middle',colspan: 1, rowspan: 1,},
                {field: 'status', title: 'N+2年度', align: 'center', valign: 'middle',colspan: 1, rowspan: 1,},
                {field: 'status', title: 'N+3年度', align: 'center', valign: 'middle',colspan: 1, rowspan: 1,},
                {field: 'status', title: '询议标（PR)', align: 'center', valign: 'middle',colspan: 3, rowspan: 1,},
                {field: 'status', title: '预算总额', align: 'center', valign: 'middle',colspan: 1, rowspan: 2,},
                {field: 'status', title: '合同信息', align: 'center', valign: 'middle',colspan: 7, rowspan: 1,},
                {field: 'status', title: '付款条款', align: 'center', valign: 'middle',colspan: 1, rowspan: 2,},
                {field: 'status', title: '已付', align: 'center', valign: 'middle',colspan: 1, rowspan: 2,},
                {field: 'status', title: '未付', align: 'center', valign: 'middle',colspan: 1, rowspan: 2,},
                {field: 'status', title: '应付未付', align: 'center', valign: 'middle',colspan: 1, rowspan: 2,},
                {field: 'status', title: '第一次付款', align: 'center', valign: 'middle',colspan: 12, rowspan: 1,},
                {field: 'status', title: '第二次付款', align: 'center', valign: 'middle',colspan: 12, rowspan: 1,},
                {field: 'status', title: '第三次付款', align: 'center', valign: 'middle',colspan: 12, rowspan: 1,},
                {field: 'status', title: '第四次付款', align: 'center', valign: 'middle',colspan: 12, rowspan: 1,},
                {field: 'status', title: '第五次付款', align: 'center', valign: 'middle',colspan: 12, rowspan: 1,},
                {field: 'status', title: '第六次付款', align: 'center', valign: 'middle',colspan: 12, rowspan: 1,},
                {field: 'status', title: '合同付款状态', align: 'center', valign: 'middle',colspan: 1, rowspan: 2,},
                {field: 'status', title: '备注', align: 'center', valign: 'middle',colspan: 1, rowspan: 2,},
            ],
            [
                {field: 'status', title: '(含税)', align: 'center', valign: 'middle',},
                {field: 'status', title: '1月', align: 'center', valign: 'middle',},
                {field: 'status', title: '2月', align: 'center', valign: 'middle',},
                {field: 'status', title: '3月', align: 'center', valign: 'middle',},
                {field: 'status', title: 'Q1(季度)', align: 'center', valign: 'middle',},
                {field: 'status', title: '4月', align: 'center', valign: 'middle',},
                {field: 'status', title: '5月', align: 'center', valign: 'middle',},
                {field: 'status', title: '6月', align: 'center', valign: 'middle',},
                {field: 'status', title: 'Q2(季度)', align: 'center', valign: 'middle',},
                {field: 'status', title: '7月', align: 'center', valign: 'middle',},
                {field: 'status', title: '8月', align: 'center', valign: 'middle',},
                {field: 'status', title: '9月', align: 'center', valign: 'middle',},
                {field: 'status', title: 'Q3(季度)', align: 'center', valign: 'middle',},
                {field: 'status', title: '10月', align: 'center', valign: 'middle',},
                {field: 'status', title: '11月', align: 'center', valign: 'middle',},
                {field: 'status', title: '12月', align: 'center', valign: 'middle',},
                {field: 'status', title: 'Q4(季度)', align: 'center', valign: 'middle',},
                {field: 'status', title: '(万元)', align: 'center', valign: 'middle',},
                {field: 'status', title: '(万元)', align: 'center', valign: 'middle',},
                {field: 'status', title: '(万元)', align: 'center', valign: 'middle',},
                {field: 'status', title: '(万元)', align: 'center', valign: 'middle',},
                {field: 'status', title: '(万元)', align: 'center', valign: 'middle',},
                {field: 'status', title: '计划时间', align: 'center', valign: 'middle',},
                {field: 'status', title: '签订时间', align: 'center', valign: 'middle',},
                {field: 'status', title: '编号', align: 'center', valign: 'middle',},
                {field: 'status', title: '供应商', align: 'center', valign: 'middle',},
                {field: 'status', title: '合同编号', align: 'center', valign: 'middle',},
                {field: 'status', title: '签订时间', align: 'center', valign: 'middle',},
                {field: 'status', title: '合同金额(含税)', align: 'center', valign: 'middle',},
                {field: 'status', title: '差异数', align: 'center', valign: 'middle',},
                {field: 'status', title: '税点%', align: 'center', valign: 'middle',},
                {field: 'status', title: '合同发起人', align: 'center', valign: 'middle',},

                {field: 'status', title: '付款比例', align: 'center', valign: 'middle',},
                {field: 'status', title: '验收完工判定(是/否)', align: 'center', valign: 'middle',},
                {field: 'status', title: '计划付款金额', align: 'center', valign: 'middle',},
                {field: 'status', title: '计划付款时间', align: 'center', valign: 'middle',},
                {field: 'status', title: '发票号(必填)', align: 'center', valign: 'middle',},
                {field: 'status', title: '发票提交时间(必填)', align: 'center', valign: 'middle',},
                {field: 'status', title: 'OA付款申请流水号', align: 'center', valign: 'middle',},
                {field: 'status', title: '申请支付金额(发票金额/必填项)', align: 'center', valign: 'middle',},
                {field: 'status', title: '财务预支付时间', align: 'center', valign: 'middle',},
                {field: 'status', title: '实际支付时间', align: 'center', valign: 'middle',},
                {field: 'status', title: '实际支付金额', align: 'center', valign: 'middle',},
                {field: 'status', title: '支付金额差异', align: 'center', valign: 'middle',},

                {field: 'status', title: '付款比例', align: 'center', valign: 'middle',},
                {field: 'status', title: '验收完工判定(是/否)', align: 'center', valign: 'middle',},
                {field: 'status', title: '计划付款金额', align: 'center', valign: 'middle',},
                {field: 'status', title: '计划付款时间', align: 'center', valign: 'middle',},
                {field: 'status', title: '发票号(必填)', align: 'center', valign: 'middle',},
                {field: 'status', title: '发票提交时间(必填)', align: 'center', valign: 'middle',},
                {field: 'status', title: 'OA付款申请流水号', align: 'center', valign: 'middle',},
                {field: 'status', title: '申请支付金额(发票金额/必填项)', align: 'center', valign: 'middle',},
                {field: 'status', title: '财务预支付时间', align: 'center', valign: 'middle',},
                {field: 'status', title: '实际支付时间', align: 'center', valign: 'middle',},
                {field: 'status', title: '实际支付金额', align: 'center', valign: 'middle',},
                {field: 'status', title: '支付金额差异', align: 'center', valign: 'middle',},

                {field: 'status', title: '付款比例', align: 'center', valign: 'middle',},
                {field: 'status', title: '验收完工判定(是/否)', align: 'center', valign: 'middle',},
                {field: 'status', title: '计划付款金额', align: 'center', valign: 'middle',},
                {field: 'status', title: '计划付款时间', align: 'center', valign: 'middle',},
                {field: 'status', title: '发票号(必填)', align: 'center', valign: 'middle',},
                {field: 'status', title: '发票提交时间(必填)', align: 'center', valign: 'middle',},
                {field: 'status', title: 'OA付款申请流水号', align: 'center', valign: 'middle',},
                {field: 'status', title: '申请支付金额(发票金额/必填项)', align: 'center', valign: 'middle',},
                {field: 'status', title: '财务预支付时间', align: 'center', valign: 'middle',},
                {field: 'status', title: '实际支付时间', align: 'center', valign: 'middle',},
                {field: 'status', title: '实际支付金额', align: 'center', valign: 'middle',},
                {field: 'status', title: '支付金额差异', align: 'center', valign: 'middle',},

                {field: 'status', title: '付款比例', align: 'center', valign: 'middle',},
                {field: 'status', title: '验收完工判定(是/否)', align: 'center', valign: 'middle',},
                {field: 'status', title: '计划付款金额', align: 'center', valign: 'middle',},
                {field: 'status', title: '计划付款时间', align: 'center', valign: 'middle',},
                {field: 'status', title: '发票号(必填)', align: 'center', valign: 'middle',},
                {field: 'status', title: '发票提交时间(必填)', align: 'center', valign: 'middle',},
                {field: 'status', title: 'OA付款申请流水号', align: 'center', valign: 'middle',},
                {field: 'status', title: '申请支付金额(发票金额/必填项)', align: 'center', valign: 'middle',},
                {field: 'status', title: '财务预支付时间', align: 'center', valign: 'middle',},
                {field: 'status', title: '实际支付时间', align: 'center', valign: 'middle',},
                {field: 'status', title: '实际支付金额', align: 'center', valign: 'middle',},
                {field: 'status', title: '支付金额差异', align: 'center', valign: 'middle',},

                {field: 'status', title: '付款比例', align: 'center', valign: 'middle',},
                {field: 'status', title: '验收完工判定(是/否)', align: 'center', valign: 'middle',},
                {field: 'status', title: '计划付款金额', align: 'center', valign: 'middle',},
                {field: 'status', title: '计划付款时间', align: 'center', valign: 'middle',},
                {field: 'status', title: '发票号(必填)', align: 'center', valign: 'middle',},
                {field: 'status', title: '发票提交时间(必填)', align: 'center', valign: 'middle',},
                {field: 'status', title: 'OA付款申请流水号', align: 'center', valign: 'middle',},
                {field: 'status', title: '申请支付金额(发票金额/必填项)', align: 'center', valign: 'middle',},
                {field: 'status', title: '财务预支付时间', align: 'center', valign: 'middle',},
                {field: 'status', title: '实际支付时间', align: 'center', valign: 'middle',},
                {field: 'status', title: '实际支付金额', align: 'center', valign: 'middle',},
                {field: 'status', title: '支付金额差异', align: 'center', valign: 'middle',},

                {field: 'status', title: '付款比例', align: 'center', valign: 'middle',},
                {field: 'status', title: '验收完工判定(是/否)', align: 'center', valign: 'middle',},
                {field: 'status', title: '计划付款金额', align: 'center', valign: 'middle',},
                {field: 'status', title: '计划付款时间', align: 'center', valign: 'middle',},
                {field: 'status', title: '发票号(必填)', align: 'center', valign: 'middle',},
                {field: 'status', title: '发票提交时间(必填)', align: 'center', valign: 'middle',},
                {field: 'status', title: 'OA付款申请流水号', align: 'center', valign: 'middle',},
                {field: 'status', title: '申请支付金额(发票金额/必填项)', align: 'center', valign: 'middle',},
                {field: 'status', title: '财务预支付时间', align: 'center', valign: 'middle',},
                {field: 'status', title: '实际支付时间', align: 'center', valign: 'middle',},
                {field: 'status', title: '实际支付金额', align: 'center', valign: 'middle',},
                {field: 'status', title: '支付金额差异', align: 'center', valign: 'middle',},


            ],
        ],
        toolbars: [
            {
                text: '添加',
                iconCls: 'glyphicon glyphicon-plus',
                handler: function () {
                    var url = "chosenSupplier/addPage?projectPuid="+projectPuid;
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
                                    title: "添加",
                                    url: "chosenSupplier/addPage?projectPuid="+projectPuid,
                                    gridId: "gridId",
                                    width: 500,
                                    height: 500
                                })
                            }
                        }
                    })
                }
            },
            {
                text: '修改',
                iconCls: 'glyphicon glyphicon-pencil',
                handler: function () {
                    var rows = $table.bootstrapTable('getSelections');
                    //只能选一条
                    if (rows.length != 1) {
                        window.Ewin.alert({message: '请选择一条需要修改的数据!'});
                        return false;
                    }
                    else if (rows[0].status == 5 || rows[0].status == 6) {
                        window.Ewin.alert({message: '对不起,审核中的数据不能修改!'});
                        return false;
                    }
                    var url = "";
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
                                    title: "修改",
                                    url: "",
                                    gridId: "gridId",
                                    width: 500,
                                    height: 500
                                });
                            }
                        }
                    })
                }
            },
            {
                text: '删除',
                iconCls: 'glyphicon glyphicon-remove',
                handler: function () {
                    var rows = $table.bootstrapTable('getSelections');
                    var puids = "";
                    for (var i = 0; i < rows.length; i++) {
                        puids += rows[i].puid + ",";
                    }
                    var myData = JSON.stringify({
                        "projectId": $("#project", window.top.document).val(),
                        "puids": puids,
                    });
                    if (rows.length == 0) {
                        window.Ewin.alert({message: '请至少选择一条需要删除的数据!'});
                        return false;
                    }
                    else if (rows[0].status == 5 || rows[0].status == 6) {
                        window.Ewin.alert({message: '对不起,审核中的数据不能删除!'});
                        return false;
                    }
                    var url = "";
                    $.ajax({
                        url: "privilege/write?url=" + url,
                        type: "GET",
                        success: function (result) {
                            if (!result.success) {
                                window.Ewin.alert({message: result.errMsg});
                                return false;
                            }
                            else {
                                // var _table = '<p>是否要删除您所选择的记录？</p>' +
                                //     '<div style="max-height: 400px;overflow:scroll;"><table class="table table-striped tableNormalStyle" >';
                                // for (var index in rows) {
                                //     _table += '<tr><td>' + rows[index].lineId + '</td></tr>';
                                // }
                                // _table += '</table></div>';
                                window.Ewin.confirm({
                                    title: '提示',
                                    message: '确定要删除数据吗?',
                                    width: 500
                                }).on(function (e) {
                                    if (e) {
                                        $.ajax({
                                            type: "POST",
                                            //ajax需要添加打包名
                                            url: "",
                                            data: myData,
                                            contentType: "application/json",
                                            success: function (result) {
                                                if (result.success) {
                                                    layer.msg('删除成功', {icon: 1, time: 2000})
                                                } else if (!result.success) {
                                                    window.Ewin.alert({message: result.errMsg});
                                                }
                                                $table.bootstrapTable("refresh");
                                            },
                                            error: function (info) {
                                                window.Ewin.alert({message: "操作删除:" + info.status});
                                            }
                                        })
                                    }
                                });
                            }
                        }
                    })
                }
            },
        ]
    });
}