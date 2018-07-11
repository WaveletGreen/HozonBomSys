// <![CDATA[
$(document).ready(function () {
    var allPuids="";
    var arr =[];
    var count=0;
    $(function () {
        $('#pbomManageForm').bootstrapValidator({
            message: 'This value is not valid',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                lineId: {
                    message: '零件号验证失败',
                    validators: {
                        notEmpty: {
                            message: '零件号不能为空'
                        },
                    }
                },
            }
        });
        // 绑定dialog的确定按钮的监听事件
        $("#btnOk", window.top.document).click(function () {
            // var bootstrapValidator = $("#pbomManageForm", window.top.document).data('bootstrapValidator');
            // bootstrapValidator.validate();
            if (true) {
                var url = $("#pbomManageForm", window.top.document).attr('action');
                var myData = JSON.stringify({
                    "puids": $("#puids", window.top.document).val(),
                    "lineId": $("#lineId", window.top.document).val(),
                    "pBomOfWhichDept": $("#pBomOfWhichDept", window.top.document).val(),
                    "pBomLinePartClass": $("#pBomLinePartClass", window.top.document).val(),
                    "pBomLinePartResource": $("#pBomLinePartResource", window.top.document).val(),
                    "resource": $("#resource", window.top.document).val(),
                    "type": $("#type", window.top.document).val(),
                    "buyUnit": $("#buyUnit", window.top.document).val(),
                    "workShop1": $("#workShop1", window.top.document).val(),
                    "workShop2": $("#workShop2", window.top.document).val(),
                    "productLine": $("#productLine", window.top.document).val(),
                    "mouldType": $("#mouldType", window.top.document).val(),
                    "outerPart":$("#outerPart", window.top.document).val(),
                    "colorPart": $("#colorPart", window.top.document).val(),
                    "station": $("#station", window.top.document).val(),
                    "projectId": $("#project", window.top.document).val()
                });
                console.log(myData);
                $.ajax({
                    contentType:
                        "application/json",
                    type:
                        'POST',
                    url: url,
                    data: myData,
                    success:
                        function (result) {
                            $('.modal-dialog', window.top.document).parent('div').remove()
                            $('body', window.top.document).find('.modal-backdrop').remove();
                            // jquery 调用刷新当前操作的table页面的refresh方法
                            // $(window.parent.document).contents().find(".tab-pane.fade.active.in iframe")[0].contentWindow.doQuery();
                            window.Ewin.alert({message: result.errMsg});
                            var setting = {
                                view: {
                                    selectedMulti: false,
                                    dblClickExpand:
                                        false,
                                    showLine:
                                        true,
                                },
                                check: {
                                    enable: true
                                },
                                data: {
                                    simpleData: {
                                        enable: true,
                                        idKey:
                                            "puid",
                                        pIdKey:
                                            "parentUid",
                                        rootPId:
                                            "#"
                                    },
                                    key: {
                                        checked:
                                            "CHECKED",
                                        name:
                                            "lineId",
                                    }
                                }
                                ,
                                //回调函数
                                callback: {
                                    /*beforeClick:function(treeId, treeNode) {
                                        return !treeNode.isParent;//当是父节点 返回false 不让选取
                                    },*/
                                    onCheck: function (e, treeId, treeNode) {
                                        var treeObj = $.fn.zTree.getZTreeObj("Ztree1");
                                        var nodes = treeObj.getCheckedNodes(true);
                                        console.log(nodes.length)
                                        for(var i=0;i<nodes.length;i++){
                                            arr.push(nodes[i].puid);
                                            allPuids+=nodes[i].puid+","
                                            count++;
                                        }
                                        $("#demo3").html( "<p>"+allPuids+"</p>");
                                        if(count!=nodes.length){
                                            arr.splice(0,arr.length);
                                            $("#demo3").html( "<p>"+"</p>");
                                            count=0;
                                            allPuids="";
                                            for(var i=0;i<nodes.length;i++){
                                                arr.push(nodes[i].puid);
                                                allPuids+=nodes[i].puid+","
                                                count++;
                                            }
                                            $("#demo3").html( "<p>"+allPuids+"</p>");
                                        }
                                        console.log(arr);
                                    },
                                    onClick: function (event, treeId, treeNode) {
                                        localSelectedNode = treeNode;
                                        $.ajax({
                                            url: "pbom/detail?puid=" + localSelectedNode.puid + "&projectId=" + $("#project", window.top.document).val(),
                                            type: "GET",
                                            undefinedText: "",//当数据为 undefined 时显示的字符
                                            success: function (data) {
                                                var result = JSON.stringify(data);
                                                var ddd = JSON.parse(result);
                                                var date = ddd.data;
                                                var va = date[0];
                                                var rel = "<table>"
                                                rel += "<tr>" +
                                                    "<th>序号</th>" +
                                                    "<td></td>" +
                                                    "</tr><tr>"+
                                                    "<th>层级</th>" +
                                                    "<td>" + va.level + "</td>" +
                                                    "</tr><tr>" +
                                                    "<th>专业</th>" +
                                                    "<td>" + va.pBomOfWhichDept + "</td>" +
                                                    "</tr><tr>" +
                                                    "<th>级别</th>" +
                                                    "<td>" + va.rank + "</td>" +
                                                    "</tr><tr>" +
                                                    "<th>分组号</th>" +
                                                    "<td>" + va.groupNum + "</td>" +
                                                    "</tr><tr>" +
                                                    "<th>零件号</th>" +
                                                    "<td>" + va.lineId + "</td>" +
                                                    "</tr><tr>" +
                                                    "<th>名称</th>" +
                                                    "<td>" + va.pBomLinePartName + "</td>" +
                                                    "</tr><tr>" +
                                                    "<th>英文名称</th>" +
                                                    "<td>" + va.pBomLinePartEnName + "</td>" +
                                                    "</tr><tr>" +
                                                    "<th>零件分类</th>" +
                                                    "<td>" + va.pBomLinePartClass + "</td>" +
                                                    "</tr><tr>" +
                                                    "<th>零部件来源</th>" +
                                                    "<td>" + va.pBomLinePartResource + "</td>" +
                                                    "</tr><tr>" +
                                                    "<th>自制/采购</th>" +
                                                    "<td>" + va.resource + "</td>" +
                                                    "</tr><tr>" +
                                                    "<th>焊接/装配</th>" +
                                                    "<td>" + va.type + "</td>" +
                                                    "</tr><tr>" +
                                                    "<th>采购单元</th>" +
                                                    "<td>" + va.buyUnit + "</td>" +
                                                    "</tr><tr>" +
                                                    "<th>车间1</th>" +
                                                    "<td>" + va.workShop1 + "</td>" +
                                                    "</tr><tr>" +
                                                    "<th>车间2</th>" +
                                                    "<td>" + va.workShop2 + "</td>" +
                                                    "</tr><tr>" +
                                                    "<th>生产线</th>" +
                                                    "<td>" + va.productLine + "</td>" +
                                                    "</tr><tr>" +
                                                    "<th>模具类别</th>" +
                                                    "<td>" + va.mouldType + "</td>" +
                                                    "</tr><tr>" +
                                                    "<th>外委件</th>" +
                                                    "<td>" + va.outerPart + "</td>" +
                                                    "</tr><tr>" +
                                                    "<th>颜色件</th>" +
                                                    "<td>" + va.colorPart + "</td>" +
                                                    "</tr><tr>" +
                                                    "<th>工位</th>" +
                                                    "<td>" + va.station + "</td>" +
                                                    "</tr>"
                                                rel += "</table>"
                                                $("#detailTable").html(rel);
                                            },
                                            error: function (err) {
                                                alert(err.status);
                                            }
                                        })
                                        localSelectedNode = treeNode;
                                    },
                                }
                            };


                            console.log(document.getElementById("demo3").innerText);
                            var zNodes = result.data;
                            if(result.success){
                                $("#demo1").html( "<p class=\"text-primary\">"+"合成结果：</p>");
                                $("#demo2").html("");
                                $(document).ready(function () {
                                    $.fn.zTree.init($("#Ztree1"), setting, zNodes);
                                });
                            }

                        },
                    error: function (status) {
                        window.Ewin.alert({message: status.status + ':生成工艺合件失败!'});
                    }
                });
            }
        });
    });
})
// ]]>