/**
 * Created with IntelliJ IDEA.
 * User: xlf
 * Date: 2018/11/8
 * Time: 15:17
 */
//@Modified by Fancyears·Maylos·Malvis in 2018/11/22 11:05
// 对于直接从任务跳转到列表页面的，需要在页面加载的时候判断是否要直接加载到详情页面中
$(document).ready((function () {
    let taskData = window.parent.getTaskData();
    if (taskData == null || taskData == undefined) {
        var url = "untreated/infoList";
        initTable(url);
        initListener();
        // doQuery();
    }
    else {
        loadDetailTask();
    }
}));

function doQuery() {
    $('#untreatedTable').bootstrapTable('destroy');
    var url = "untreated/infoList";
    initTable(url);
}

function formatDate() {
    let firstOriginTime = stringToDateFormat($('#firstOriginTime').data("time"));
    let lastOriginTime = stringToDateFormat($('#lastOriginTime').data("time"));
    $('#firstOriginTime').val(firstOriginTime);
    $('#lastOriginTime').val(lastOriginTime);
}

function initTable(url) {
    var $table = $("#untreatedTable");
    var column = [];
    column.push({field: 'ck', checkbox: true});
    column.push({
        field: '',
        title: '序号',
        align: 'center',
        width: 50,
        formatter: function (value, row, index) {
            //return index+1;
            // var temp = $('#changeFormTable').bootstrapTable("getIndex");//返回（pageSize * (pageNumber-1) + 1）
            // return temp + index;
            var options = $table.bootstrapTable('getOptions');
            return options.pageSize * (options.pageNumber - 1) + index + 1;

        }
    }),
        column.push({
            field: 'changeNo',
            title: '变更单号',
            align: 'center',
            valign: 'middle',
            formatter: function (value, row, index) {
                var id = row.id
                return [
                    '<a href="javascript:void(0)" onclick="queryLou(' + id + ')">' + value + '</a>'
                ].join("");
            }
        });
    column.push({field: 'originTime', title: '发起时间', align: 'center', valign: 'middle'});
    column.push({field: 'deptName', title: '部门', align: 'center', valign: 'middle'});
    column.push({field: 'changeType', title: '变更类型', align: 'center', valign: 'middle'});
    column.push({field: 'originator', title: '流程发起人', align: 'center', valign: 'middle'});
    column.push({field: 'projectName', title: '项目', align: 'center', valign: 'middle'});
    column.push({field: 'source', title: '来源', align: 'center', valign: 'middle'});
    $table.bootstrapTable({
        url: url,
        method: 'get',
        height: $(window.parent.document).find("#wrapper").height() - 90,
        width: $(window).width(),
        showToggle: false,                   //是否显示详细视图和列表视图的切换按钮
        showRefresh: true,                  //是否显示刷新按钮
        pagination: true,                   //是否显示分页（*）
        pageSize: 20,
        pageNumber: 1,
        pageList: [/*'ALL',*/ 20, 50, 100, 200, 500, 1000],        //可供选择的每页的行数（*）
        sidePagination: "server",          //分页方式：client客户端分页，server服务端分页（*）
        clickToSelect: true,                // 单击某一行的时候选中某一条记录
        showExport: false,
        formId: "queryUntreated",
        columns: column,                     //列信 息，需要预先定义好
        sortable: false,                     //是否启用排序
        sortOrder: "asc",                   //排序方式
        striped: true,                      //是否显示行间隔色
        search: false,                      //是否显示表格搜索，此搜索是客户端搜索，不会进服务端
        showColumns: false,                 //是否显示所有的列
        toolbars: [
            {
                text: '修改',
                iconCls: 'glyphicon glyphicon-pencil',
                handler: function () {
                    var rows = $table.bootstrapTable('getSelections');
                    //只能选一条
                    if (rows.length != 1) {
                        window.Ewin.alert({message: '请选择一条需要修改的变更表单!'});
                        return false;
                    }
                    else if (rows[0].isFromTc == 0) {
                        window.Ewin.alert({message: '只能选择来源是TC端的表单进行修改!'});
                        return false;
                    }
                    window.Ewin.dialog({
                        title: "修改",
                        url: "" ,
                        gridId: "gridId",
                        width: 500,
                        height: 500
                    });
                }
            },
        ],
    });
}

function queryLou(id) {
    window.location.href = "untreated/ToUntreatedForm?id=" + id;
}

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: 初始化监听
 * @Date: Created in  2018/11/22 11:06
 * @Modified By:
 */
function initListener() {
    $("#saveBtn").click(function () {
        saveAgreement();
    })
}

//@Author: Fancyears·Maylos·Malvis  in 2018/11/22 11:07 保存审核意见，参数必须要有agreement
//agreement用于直接驱动流程的中断还是发布
function saveAgreement() {
    let data = {};
    var _d = $("#bomLeaderOpinion").serializeArray();
    for (var p in _d) {
        data[_d[p].name] = _d[p].value;
    }
    data.id = $("#id").val();
    log(data);
    window.Ewin.confirm({
        title: '审核结果',
        message: "<span style='color: red'>" + (data.agreement == '1' ? "同意" : "不同意") + "</span>",
        width: 500
    }).on(function (e) {
        if (e) {
            $.ajax({
                contentType:
                    "application/json",
                type:
                    'POST',
                url: "../process/doCheck",
                data: JSON.stringify(data),
                success:
                    function (result) {
                        activeTabBodyRefresh();
                        if (result.status) {
                            layer.msg(result.msg, {icon: 1, time: 2000});
                            window.setTimeout(reload, 2000);
                        }
                        else {
                            window.Ewin.alert({message: result.msg});
                        }
                    },
                error: function (status) {
                    window.Ewin.alert({message: "提交失败:" + status.status});
                }
            })
        }
    })
}

//@Author: Fancyears·Maylos·Malvis  in 2018/11/22 13:06  重新加载任务，按钮禁用
function reload() {
    window.top.loadTasks();
    $('#saveBtn').attr('disabled',"true");
}