/**
 * Created with IntelliJ IDEA.
 * User: xlf
 * Date: 2018/10/22
 * Time: 15:05
 */
var projectId = $("#project", window.top.document).val();
var ewoNo = $("#ewoNo").val();
$(document).ready((function () {
    formatDate();
    var partDescUrl = "change/record?projectId=" + projectId + "&ewoNo=" + ewoNo;
    initTable(partDescUrl);
}));

function formatDate() {
    let formCreateTime = stringToDateFormat($('#formCreateTime').val());
    $('#formCreateTime').val(formCreateTime);

    let finishTime = stringToDateFormat($('#finishTime').data("time"));
    let effectTime = stringToDateFormat($('#effectTime').data("time"));
    $('#finishTime').val(finishTime);
    $('#effectTime').val(effectTime);
}

// 零件信息
function initTable(partDescUrl) {
    var projectId = $("#project", window.top.document).val();
    var partDescTable = $("#partDescTable");
    var column = [];
    $.ajax({
        url: "title",
        type: "GET",
        success: function (result) {
            var column = [];
            // column.push({field: 'eBomPuid', title: 'puid'});
            column.push({field: 'ck', checkbox: true});
            // column.push({field: 'puid', title: '主键'});
            column.push({field: 'lineId', title: '零件号'});
            column.push({
                field: 'changeFlag',
                title: '变更前/变更后',
                align: 'center',
                valign: 'middle',
                formatter: function (value, row, index) {
                    if (value == 0 || "0" == value) {
                        return "<span>变更前</span>";
                    }
                    if (value == 1 || "1" == value) {
                        return "<span>变更后</span>";
                    }
                }
            });
            column.push({
                field: 'status',
                title: '状态',
                align: 'center',
                valign: 'middle',
                formatter: function (value, row, index) {
                    if (value == 1 || "1" == value) {
                        return "<span style='color: #00B83F'>已生效</span>";
                    }
                    if (value == 2 || "2" == value) {
                        return "<span style='color: #ff7cf4'>草稿状态</span>";
                    }
                    if (3 == value || "3" == value) {
                        return "<span style='color: #9492a9'>废除状态</span>";
                    }
                    if (4 == value || "4" == value) {
                        return "<span style='color: #a90009'>删除状态</span>";
                    }
                    if (value == 5 || value == "5") {
                        return "<span style='color: #e2ab2f'>审核中</span>"
                    }
                    if (value == 6 || value == "6") {
                        return "<span style='color: #e2ab2f'>审核中</span>"
                    }
                }
            })
            var data = result.data;
            var keys = [];
            var values;
            for (var key in data) {
                if (data.hasOwnProperty(key)) {
                    var json = {
                        field: key,
                        title: data[key],
                        // align:
                        //     'center',
                        valign:
                            'middle',
                    };
                    column.push(json);
                }
            }
            partDescTable.bootstrapTable({
                url: partDescUrl,
                method: 'GET',
                dataType: 'json',
                sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
                height: $(window.parent.document).find("#wrapper").height() - 150,
                width: $(window).width(),
                undefinedText: "",//当数据为 undefined 时显示的字符
                pagination: false,
                columns: column,
                sortOrder: "asc",                   //排序方式
                clickToSelect: true,// 单击某一行的时候选中某一条记录
                showColumns: false, //是否显示所有的列
                showToggle: false,                   //是否显示详细视图和列表视图的切换按钮
                showRefresh: true,                  //是否显示刷新按钮
                toolbars: [
                    {
                        text: '添加',
                        iconCls: 'glyphicon glyphicon-plus',
                        handler: function () {
                            var rows = $table.bootstrapTable('getSelections');
                            if (rows.length > 1) {
                                window.Ewin.alert({message: '只能选择一个父级!'});
                                return false;
                            }
                            window.Ewin.dialog({
                                title: "添加",
                                url: "ebom/addEbom?projectId=" + projectPuid + "&puid=" + rows[0].puid,
                                gridId: "gridId",
                                width: 500,
                                height: 500
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
                            ;
                            var myData = JSON.stringify({
                                "projectId": projectId,
                                "puids": puids,
                            });
                            if (rows.length == 0) {
                                window.Ewin.alert({message: '请选择一条需要删除的数据!'});
                                return false;
                            }
                            var _table = '<p>是否要删除您所选择的记录？</p>' +
                                '<div style="max-height: 400px;overflow:scroll;"><table class="table table-striped tableNormalStyle" >';
                            for (var index in rows) {
                                _table += '<tr><td>' + rows[index].lineId + '</td></tr>';
                            }
                            _table += '</table></div>';
                            window.Ewin.confirm({title: '提示', message: _table, width: 500}).on(function (e) {
                                if (e) {
                                    $.ajax({
                                        type: "POST",
                                        //ajax需要添加打包名
                                        url: "ebom/delete/ebom",
                                        data: myData,
                                        contentType: "application/json",
                                        success: function (result) {
                                            // if (result.status) {
                                            //     window.Ewin.alert({message: result.errMsg});
                                            //     //刷新，会重新申请数据库数据
                                            // }
                                            // else {
                                            //     window.Ewin.alert({messabge: + result.errMsg});
                                            // }
                                            if (result.success) {
                                                layer.msg('删除成功', {icon: 1, time: 2000})
                                            } else if (!result.success) {
                                                window.Ewin.alert({message: result.errMsg});
                                            }
                                            $table.bootstrapTable("refresh");
                                        },
                                        error: function (info) {
                                            window.Ewin.alert({message: ":" + info.status});
                                        }
                                    })
                                }
                            });
                        }
                    },
                ],
            });
        }
    });

}

// 影响分析
$(document).ready((function () {
    $.ajax({
        url: "impact/reference?projectId=" + projectId + "&ewoNo=" + ewoNo,
        type: "GET",
        success: function (result) {
            if (result == null) {
                window.Ewin.alert("查无数据,请联系管理员")
            }
            var impactAnalysisTable1 = $("#impactAnalysisTable1");
            var impactAnalysisTable2 = $("#impactAnalysisTable2");
            var data = JSON.stringify(result.data);
            var _data = JSON.parse(data);
            var temp = "<table>";
            var tempB = "<table>"
            for (var i = 0; i < _data.length; i++) {
                if (_data[i].type == "A") {

                    temp += "<tr><td><input " + (_data[i].checked == 1 ? "checked=true" : "") + " type='checkbox' id='ck'></td>" +
                        "<td>" + _data[i].content + "</td></tr>"
                    temp += "</table>"
                    impactAnalysisTable1.html(temp);
                } else {
                    tempB += "<tr><td><input " + (_data[i].checked == 1 ? "checked=true" : "") + " type='checkbox' id='ck'></td>" +
                        "<td>" + _data[i].content + "</td></tr>"
                    tempB += "</table>"
                    impactAnalysisTable2.html(tempB);
                }


            }


        }
    })
}))

// 相关部门
$(document).ready((function () {
    $.ajax({
        url: "all",
        type: "GET",
        success: function (result) {
            var affectDept = $("#affectDept");
            var data = JSON.stringify(result.data);
            var _data = JSON.parse(data);
            var temp = "<table>";
            for (var i = 0; i < _data.length; i++) {
                // if(_data[i].type == "A"){
                temp += "<tr><td style='width: 50px'><input id='" + _data[i].groupCode + "'value='" + _data[i].groupId + "' type='checkbox'></td>" +
                    "<td>" + _data[i].name + "</td></tr>"
                temp += "</table>"
                affectDept.html(temp);
                // }else {
                //     tempB +="<tr><td><input "+(_data[i].checked==1?"checked=true":"")+" type='checkbox' id='ck'></td>" +
                //         "<td>"+_data[i].content+"</td></tr>"
                // tempB += "</table>"
                // impactAnalysisTable2.html(tempB);
                // }


            }


        }
    })
    $.ajax({
        url: "allDept",
        type: "GET",
        success: function (result) {
            var data = result.data;
            var temp = "";
            for (var i = 0; i < data.length; i++) {
                $('#demo').append("<div class='influence-leader'>" +
                "<a href='javascript:void(0)' onclick='doSelectPerson("+ data[i].id+")'>" + data[i].deptName + ":</a>" +
                // <span id="sp_1" th:text="${influenceUser.getVwoProSectionChiefName()}"></span>
                "</div>")
            }
        }
    })
    // $.ajax({
    //     url: "user",
    //     type: "GET",
    //     success: function (result) {
    //         var _table = $("#deptName")
    //         // var data = JSON.stringify(result.data);
    //         // var _data = JSON.parse(data);
    //         // var arr = [];
    //         // for(var i =0 ;i < _data.length;i++){
    //         //     arr[i] = _data[i].username+_data[i].id;
    //         //     console.log(arr[i].id)
    //         // }
    //         // console.log(arr);
    //         // var temp = "<table>"
    //         // for (var i = 0; i < _data.length;i++){
    //         //     temp += "<tr><td>"+_data[i].username+"</td></tr>"
    //         // }
    //         // temp += "</table>"
    //         // _table.html(temp)
    //         $.ajax({
    //             url: "allDept",
    //             type: "GET",
    //             success: function (data) {
    //                 // var _table = $("#deptName");
    //                 var _data = JSON.stringify(data.data);
    //                 var allDept = JSON.parse(_data);
    //                 // var temp = "<table>";
    //                 // for (var i=0;i<allDept.length;i++) {
    //                 // if(_data[i].type == "A"){
    //
    //                 // temp += "<tr><td id='" + allDept[i].id + "'>" + allDept[i].deptName + "</td>" +
    //                 //     "<td><div id='queryName'><div class='wrapper'>" +
    //                 //     "<div class='search-form'></div>" +
    //                 //     "<div class='message'></div>" +
    //                 //     "</div></div></td></tr>"
    //                 // temp += "</table>"
    //                 for (var item in allDept) {
    //                     $('#demo').append($('<div class="wrapper">\
    //                       <laber>' + allDept[item].deptName + '</laber>\
    //                       <div class="search-form"></div>\
    //                       <div class="message"></div>\
    //                     </div>'))
    //                 }
    //                 $('.search-form').autocomplete({
    //                     hints: result,
    //                     width: 300,
    //                     height: 30,
    //                     // onSubmit: function(text){
    //                     //     $('#message').html('结果: <b>' + text + '</b>');
    //                     // }
    //                 });
    //                 // _table.html(temp);
    //             }
    //         })
    //
    //     }
    // })
}))


function doSelectPerson(id) {
    window.Ewin.dialog({
        title: "添加",
        url: "toPageInsertUser",
        gridId: "gridId",
        width: 600,
        height: 500
    })
}