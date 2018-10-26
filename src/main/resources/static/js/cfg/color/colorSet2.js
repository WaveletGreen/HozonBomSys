/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */
/**工具条设置*/
var toolbar = [
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
        text: '修改',
        iconCls: 'glyphicon glyphicon-pencil',
        handler: function () {
            var rows = $table.bootstrapTable('getSelections');
            //只能选一条
            if (rows.length != 1) {
                window.Ewin.alert({message: '请选择一条需要修改的数据!'});
                return false;
            }
            window.Ewin.dialog({
                title: "修改",
                url: "colorSet/update?puid=" + rows[0].puid,
                gridId: "gridId",
                width: 400,
                height: 500
            });
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
];
/**
 * 表头设置
 */
var column = [
    {
        field: 'ck',
        checkbox: true
    },
    {
        field: 'pColorOfSet',
        title: '色系',
        sortable: true,
        sortOrder: 'asc',
    },
    {
        field: 'pColorName',
        title: '颜色名称',
        align: 'center',
        valign: 'middle',
        sortable: true,
        sortOrder: 'asc',
    },
    {
        field: 'pColorCode',
        title: '颜色代码',
        align: 'center',
        valign: 'middle',
        sortable: true,
        sortOrder: 'asc',
    },
    {
        field: 'pColorPlate',
        title: '色板编号',
        align: 'center',
        valign: 'middle',
        sortable: true,
        sortOrder: 'asc',
    },
    {
        field: 'pColorIsMultiply',
        title: '是否拼色',
        align: 'center',
        valign: 'middle',
        sortable: true,
        sortOrder: 'asc',
    },
    {
        field: 'pColorEffectedDate',
        title: '生效时间',
        align: 'center',
        valign: 'middle',
        sortable: true,
        sortOrder: 'asc',
        //——修改——获取日期列的值进行转换
        formatter: function (value, row, index) {
            return changeDateFormat(value)
        }
    },
    {
        field: 'pColorAbolishDate',
        title: '废止时间',
        align: 'center',
        valign: 'middle',
        sortable: true,
        sortOrder: 'asc',
        //——修改——获取日期列的值进行转换
        formatter: function (value, row, index) {
            return changeDateFormat(value)
        }
    },
    {
        field: 'pColorStatus',
        title: '状态',
        align: 'center',
        valign: 'middle',
        formatter: function (value, row, index) {
            if (value == 1 || "1" == value) {
                return "<span style='color: #00B83F'>已生效</span>";
            }
            if (value == 0 || "0" == value) {
                return "<span style='color: #a97f89'>草稿状态</span>";
            }
            if (-1 == value || "-1" == value) {
                return "<span style='color: #9492a9'>已废止</span>";
            }
            else {
                return "<span style='color: #a90009'>未知状态</span>";
            }
        }
    },
    {
        field: 'csPaintMaterielCodes',
        title: '油漆物料号集合',
        align: 'center',
        valign: 'middle',
        sortable: false
    },
    {
        field: 'pColorComment',
        title: '备注',
        align: 'center',
        valign: 'middle',
        sortable: true,
        sortOrder: 'asc',
    }
];
var $table=null;
$(document).ready(
    (function () {
        loadData();
    }),
    //手动刷新按钮
    $("#refreshColorSet").click(function () {
        loadData();
    })
);

function loadData() {
    $table= $("#dataTable");
    $table.bootstrapTable('destroy');
    $("#refreshColorSet").removeAttr("disabled");
    $table.bootstrapTable({
        url: "colorSet/queryAll2",
        height: $(window.parent.document).find("#wrapper").height() - 150,//$(window.parent.document).find("#wrapper").height() - document.body.offsetHeight-100,
        width: $(window).width(),
        showToggle: true,                   //是否显示详细视图和列表视图的切换按钮
        showRefresh: true,                  //是否显示刷新按钮
        pagination: true,                   //是否显示分页（*）
        pageNumber: 1,                       //初始化加载第一页，默认第一页
        pageSize: 10,                       //每页的记录行数（*）
        pageList: [10, 30, 50, 100, 500, 1000],//可供选择的每页的行数（*）
        clickToSelect: true,                // 单击某一行的时候选中某一条记录
        smartDisplay: false,
        search: true,
        sortable: true,
        sortName: 'pColorCode',
        sortOrder: 'asc',
        // sidePagination: "client",           //客户端/客户端分页
        formId: "queryColor",
        toolbars: toolbar,
        /**列信息，需要预先定义好*/
        columns: column,
        //>>>>>>>>>>>>>>导出excel表格设置
        showExport: phoneOrPc(),              //是否显示导出按钮(此方法是自己写的目的是判断终端是电脑还是手机,电脑则返回true,手机返回falsee,手机不显示按钮)
        exportDataType: "selected",              //basic', 'all', 'selected'.
        exportTypes: ['xlsx'],	    //导出类型
        //exportButton: $('#btn_export'),     //为按钮btn_export  绑定导出事件  自定义导出按钮(可以不用)
        exportOptions: {
            //ignoreColumn: [0,0],            //忽略某一列的索引
            fileName: '颜色库数据导出',              //文件名称设置
            worksheetName: 'Sheet1',          //表格工作区名称
            tableName: '颜色库数据表',
            excelstyles: ['background-color', 'color', 'font-size', 'font-weight'],
            //onMsoNumberFormat: DoOnMsoNumberFormat
        }
        //导出excel表格设置<<<<<<<<<<<<<<<<
    });
    //设置跳转的tableID，放在table初始化语句之后
    setTargetTableId("dataTable");
    $table.bootstrapTable('hideColumn', 'puid');
}

/**
 * 修改——转换日期格式(时间戳转换为datetime格式),将日期毫秒转成格式化格式化字符串形式
 * @param cellval
 * @returns {string}
 */
function changeDateFormat(cellval) {
    if (cellval != null) {
        var date = new Date(parseInt(cellval));
        var month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
        var currentDate = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
        return date.getFullYear() + "-" + month + "-" + currentDate /*+ " " + hour + ":" + mins*/;
    }
}