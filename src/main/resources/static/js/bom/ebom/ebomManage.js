var firstLoad = true;
// $(document).ready(
//     $("#ebomManage").click(function () {
    window.onload = function(){
        var $table = $("#ebomManageTable");
        var column = [];
        $.ajax({
            url: "ebom/ebomTitle",
            type: "GET",
            success: function (result) {
                var column = [];
                column.push({field: 'eBomPuid', title: 'puid'});
                // column.push({field: 'ck', checkbox: true, Width: 50});
                column.push({field: '',
                    title: '序号',
                    formatter: function (value, row, index) {
                        return index+1;},
                    align:
                        'center',
                    valign:
                        'middle'
                });
                var data = result.data;
                console.log(data);
                var keys = [];
                var values;
                for (var key in data) {
                    if (data.hasOwnProperty(key)) {
                        var json = {
                            field: key,
                            title: data[key],
                            align:
                                'center',
                            valign:
                                'middle'
                        };
                        column.push(json);
                    }
                }
                $table.bootstrapTable({
                    url: "",
                    method: 'get',
                    height: $(window.parent.document).find("#wrapper").height() - 252,
                    width: $(window).width(),
                    showToggle: true,                   //是否显示详细视图和列表视图的切换按钮
                    showRefresh: true,                  //是否显示刷新按钮
                    // pageSize: 10,
                    pagination: false,                   //是否显示分页（*）
                    clickToSelect: true,                // 单击某一行的时候选中某一条记录
                    formId: "queryEbomManage",
                    /**列信息，需要预先定义好*/
                    columns: column,
                    sortable: true,                     //是否启用排序
                    sortOrder: "asc",                   //排序方式
                    // toolbars: [
                    //     {
                    //         text: '添加',
                    //         iconCls: 'glyphicon glyphicon-plus',
                    //         handler: function () {
                    //
                    //             window.Ewin.dialog({
                    //                 title: "添加",
                    //                 url: "ebom/addPage",
                    //                 gridId: "gridId",
                    //                 width: 500,
                    //                 height: 600
                    //             })
                    //         }
                    //     },
                    //     {
                    //         text: '修改',
                    //         iconCls: 'glyphicon glyphicon-pencil',
                    //         handler: function () {
                    //             var rows = $table.bootstrapTable('getSelections');
                    //             //只能选一条
                    //             if (rows.length != 1) {
                    //                 window.Ewin.alert({message: '请选择一条需要修改的数据!'});
                    //                 return false;
                    //             }
                    //             window.Ewin.dialog({
                    //                 title: "修改",
                    //                 url: "ebom/updateManageProcess?puid=" + rows[0].puid,
                    //                 gridId: "gridId",
                    //                 width: 650,
                    //                 height: 800
                    //             });
                    //         }
                    //     },
                    //     {
                    //         text: '删除',
                    //         iconCls: 'glyphicon glyphicon-remove',
                    //         handler: function () {
                    //             var rows = $table.bootstrapTable('getSelections');
                    //             if (rows.length == 0) {
                    //                 window.Ewin.alert({message: '请选择一条需要删除的数据!'});
                    //                 return false;
                    //             }
                    //             //测试数据
                    //             window.Ewin.confirm({
                    //                 title: '提示',
                    //                 message: '是否要删除您所选择的记录？',
                    //                 width: 500
                    //             }).on(function (e) {
                    //                 if (e) {
                    //                     $.ajax({
                    //                         type: "POST",
                    //                         //ajax需要添加打包名
                    //                         url: "./pbom/delete",
                    //                         data: JSON.stringify(rows),
                    //                         contentType: "application/json",
                    //                         success: function (result) {
                    //                             if (result) {
                    //                                 window.Ewin.alert({message: "删除时数据成功"});
                    //                                 //刷新，会重新申请数据库数据
                    //                             }
                    //                             else {
                    //                                 window.Ewin.alert({message: "操作删除失败:" + result.msg});
                    //                             }
                    //                             $table.bootstrapTable("refresh");
                    //                         },
                    //                         error: function (info) {
                    //                             window.Ewin.alert({message: "操作删除:" + info.status});
                    //                         }
                    //                     })
                    //                 }
                    //             });
                    //         }
                    //     }
                    // ]
                });
               $table.bootstrapTable('hideColumn', 'eBomPuid');
    /*column.push({field: 'eBomPuid', title: 'puid'});
    column.push({field: 'ck', checkbox: true, Width: 50});
    column.push({field: '',
        title: '序号',
        formatter: function (value, row, index) {
            return index+1;},
        align:
            'center',
        valign:
            'middle'
    });
    column.push({
        field: 'level',
        title: '层级',
        align: 'center',
        valign: 'middle',
    });
    column.push({
        field: '专业',
        title: '专业',
        align: 'center',
        valign: 'middle',
    });
    column.push({
        field: '级别',
        title: '级别',
        align: 'center',
        valign: 'middle',
    });
    column.push({
        field: '分组号',
        title: '分组号',
        align: 'center',
        valign: 'middle',
    });
    column.push({
        field: '',
        title: '零件号',
        align: 'center',
        valign: 'middle',
    });
    column.push({
        field: '',
        title: '名称',
        align: 'center',
        valign: 'middle',
    });
    column.push({
        field: '',
        title: '英文名称',
        align: 'center',
        valign: 'middle',
    });
    column.push({
        field: '',
        title: '单位',
        align: 'center',
        valign: 'middle',
    });
    column.push({
        field: '',
        title: '分时租赁低配',
        align: 'center',
        valign: 'middle',
    });
    column.push({
        field: '',
        title: '分时租赁高配',
        align: 'center',
        valign: 'middle',
    });
    column.push({
        field: '',
        title: '图号',
        align: 'center',
        valign: 'middle',
    });
    column.push({
        field: '',
        title: '安装图号',
        align: 'center',
        valign: 'middle',
    });
    column.push({
        field: '',
        title: '图幅',
        align: 'center',
        valign: 'middle',
    });
    column.push({
        field: '',
        title: '规格',
        align: 'center',
        valign: 'middle',
    });
    column.push({
        field: '',
        title: '料厚',
        align: 'center',
        valign: 'middle',
    });
    column.push({
        field: '',
        title: '材料1',
        align: 'center',
        valign: 'middle',
    });
    column.push({
        field: '',
        title: '材料2',
        align: 'center',
        valign: 'middle',
    });
    column.push({
        field: '',
        title: '材料3',
        align: 'center',
        valign: 'middle',
    });
    column.push({
        field: '',
        title: '密度 ',
        align: 'center',
        valign: 'middle',
    });
    column.push({
        field: '',
        title: '材料标准',
        align: 'center',
        valign: 'middle',
    });
    column.push({
        field: '',
        title: '表面处理',
        align: 'center',
        valign: 'middle',
    });
    column.push({
        field: '',
        title: '纹理编号/色彩编号',
        align: 'center',
        valign: 'middle',
    });
    column.push({
        field: '',
        title: '制造工艺',
        align: 'center',
        valign: 'middle',
    });
    column.push({
        field: '',
        title: '对称',
        align: 'center',
        valign: 'middle',
    });
    column.push({
        field: '',
        title: '重要度',
        align: 'center',
        valign: 'middle',
    });
    column.push({
        field: '',
        title: '是否法规件',
        align: 'center',
        valign: 'middle',
    });
    column.push({
        field: '',
        title: '法规件型号',
        align: 'center',
        valign: 'middle',
    });
    column.push({
        field: '',
        title: '黑白灰匣子件',
        align: 'center',
        valign: 'middle',
    });
    column.push({
        field: '',
        title: '开发类型',
        align: 'center',
        valign: 'middle',
    });
    column.push({
        field: '',
        title: '数据版本',
        align: 'center',
        valign: 'middle',
    });
    column.push({
        field: '',
        title: '目标重量',
        align: 'center',
        valign: 'middle',
    });
    column.push({
        field: '',
        title: '预估重量',
        align: 'center',
        valign: 'middle',
    });
    column.push({
        field: '',
        title: '实际重量',
        align: 'center',
        valign: 'middle',
    });
    column.push({
        field: '',
        title: '紧固件规格',
        align: 'center',
        valign: 'middle',
    });
    column.push({
        field: '',
        title: '紧固件性能等级',
        align: 'center',
        valign: 'middle',
    });
    column.push({
        field: '',
        title: '扭矩',
        align: 'center',
        valign: 'middle',
    });
    column.push({
        field: '',
        title: '专业部门',
        align: 'center',
        valign: 'middle',
    });
    column.push({
        field: '',
        title: '责任工程师',
        align: 'center',
        valign: 'middle',
    });
    column.push({
        field: '',
        title: '供应商',
        align: 'center',
        valign: 'middle',
    });
    column.push({
        field: '',
        title: '供应商代码',
        align: 'center',
        valign: 'middle',
    });
    column.push({
        field: '',
        title: '采购工程师',
        align: 'center',
        valign: 'middle',
    });
    column.push({
        field: '',
        title: '备注',
        align: 'center',
        valign: 'middle',
    });
    column.push({
        field: '',
        title: '零件分类',
        align: 'center',
        valign: 'middle',
    });
    column.push({
        field: '',
        title: '零部件来源',
        align: 'center',
        valign: 'middle',
    });*/
            }
        });
/*    }),
);*/
    }
