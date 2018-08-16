$(document).ready((function () {
    // initTable(),
    formatDate();
    let url = $("#url").val();
    url += "?vwo=" + $("#vwo").val();
    loadConnectedData(url);
}));

/**
 * 整理日期格式
 */
function formatDate() {
    let vwoStartEffectiveTime = changeDateFormat2($('#vwoStartEffectiveTime').val());
    let vwoEndEffectiveTime = changeDateFormat2($('#vwoEndEffectiveTime').val());
    let vwoCreateDate = changeDateFormat2($('#vwoCreateDate').val());
    let vwoDemandFinishTime = changeDateFormat2($('#vwoDemandFinishTime').val());

    $('#vwoStartEffectiveTime').val(vwoStartEffectiveTime);
    $('#vwoEndEffectiveTime').val(vwoEndEffectiveTime);
    $('#vwoCreateDate').val(vwoCreateDate);
    $('#vwoDemandFinishTime').val(vwoDemandFinishTime);

    $('#vwoStartEffectiveTime').datetimepicker({
        format: 'yyyy-mm-dd',//日期的格式
        weekStart: 1,// 0（星期日）到6（星期六）
        startDate: "2008-01-01",//选择器的开始日期
        autoclose: true,//日期选择完成后是否关闭选择框
        bootcssVer: 3,//显示向左向右的箭头
        language: 'zh_CN',//语言
        minView: 2,//表示日期选择的最小范围，默认是hour
        todayBtn: true
    });
    $('#vwoEndEffectiveTime').datetimepicker({
        format: 'yyyy-mm-dd',//日期的格式
        weekStart: 1,// 0（星期日）到6（星期六）
        startDate: "2008-01-01",//选择器的开始日期
        autoclose: true,//日期选择完成后是否关闭选择框
        bootcssVer: 3,//显示向左向右的箭头
        language: 'zh_CN',//语言
        minView: 2,//表示日期选择的最小范围，默认是hour
        todayBtn: true
    });
    $('#vwoDemandFinishTime').datetimepicker({
        format: 'yyyy-mm-dd',//日期的格式
        weekStart: 1,// 0（星期日）到6（星期六）
        startDate: "2008-01-01",//选择器的开始日期
        autoclose: true,//日期选择完成后是否关闭选择框
        bootcssVer: 3,//显示向左向右的箭头
        language: 'zh_CN',//语言
        minView: 2,//表示日期选择的最小范围，默认是hour
        todayBtn: true
    });
}

/**
 * 加载关联数据
 */
function loadConnectedData(url) {
    var $table = $("#connectedTable");
    $table.bootstrapTable('destroy');
    $table.bootstrapTable({
        url: url,
        method: 'GET',
        height: 300,//$(window.parent.document).find("#wrapper").height() - document.body.offsetHeight-100,
        width: $(window).width(),
        showToggle: false,                   //是否显示详细视图和列表视图的切换按钮
        showRefresh: true,                  //是否显示刷新按钮
        pagination: false,                  //是否显示分页（*）
        clickToSelect: true,                // 单击某一行的时候选中某一条记录
        sortName: 'pColorCode',
        sortOrder: 'asc',
        formId: "queryConnectedData",
        toolbars: [
            {
                text: '添加',
                iconCls: 'glyphicon glyphicon-plus',
                handler: function () {
                    window.Ewin.dialog({
                        title: "添加",
                        url: "colorSet/addPage",
                        gridId: "gridId",
                        width: 400,
                        height: 500
                    })
                }
            },
            {
                text: '删除',
                iconCls: 'glyphicon glyphicon-remove',
                handler: function () {
                    var rows = $table.bootstrapTable('getSelections');
                    if (rows.length == 0) {
                        window.Ewin.alert({message: '请选择一条需要删除的数据!'});
                        return false;
                    }
                    window.Ewin.confirm({title: '提示', message: '是否要删除您所选择的记录？', width: 500}).on(function (e) {
                        if (e) {
                            $.ajax({
                                type: "POST",
                                //ajax需要添加打包名
                                url: "./colorSet/delete",
                                data: JSON.stringify(rows),
                                contentType: "application/json",
                                success: function (result) {
                                    if (result.status) {
                                        layer.msg(result.msg, {icon: 1, time: 2000})
                                        // window.Ewin.alert({message: });
                                        //刷新，会重新申请数据库数据
                                    }
                                    else {
                                        window.Ewin.alert({message: "操作删除失败:" + result.msg});
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
        ],
        /**列信息，需要预先定义好*/
        columns: [
            {
                field: 'ck',
                title: '通知关联变更人',
                checkbox: true
            },
            {
                field: 'personName',
                title: '关联工程师姓名',
                align: 'center',
                valign: 'middle',
                sortable: true,
                sortOrder: 'asc',
            },
            {
                field: 'personDeptName',
                title: '所属部门',
                align: 'center',
                valign: 'middle',
                sortable: true,
                sortOrder: 'asc',
            },
            {
                field: 'partId',
                title: '关联零件号',
                align: 'center',
                valign: 'middle',
                sortable: true,
                sortOrder: 'asc',
            },
            {
                field: 'partName',
                title: '关联零件名称',
                align: 'center',
                valign: 'middle',
                sortable: true,
                sortOrder: 'asc',
            }
        ]
    });
}