$(document).ready(
    $("#doSubmit1").click(
        function () {
            var x = $("#text1").val();
            if (x.length == 0) {
                $("#myModal").modal('show');
                return;
            }
            $.ajax({
                type: 'GET',
                url: '/hozon/loadBom/loadByID',
                data: {
                    "bdf": x
                },
                success: function (result) {
                    var toJson = JSON.stringify(result);
                    var thisResult = JSON.parse(toJson);
                    var localName = thisResult[1];
                    var orgName = thisResult[0];
                    var rel = "<table class='table table-striped tableNormalStyle' style='width:" + 200 * localName.length + "px'>" + "<tr>";
                    // rel = rel + "<th class='numToCenter'>序号</th>" + "<th>顺序号</th>";//可以更加优化
                    for (var i = 0; i < localName.length; i++) {
                        rel = rel + "<th>" + localName[i] + "</th>";
                    }
                    rel = rel + "</tr>";
                    for (var i = 2; i < thisResult.length; i++) {
                        rel = rel + "<tr>";
                        // rel = rel + "<td>" + thisResult[i].index + "</td>";
                        // rel = rel + "<td>" + thisResult[i].on + "</td>";
                        for (var j = 0; j < orgName.length; j++) {
                            rel = rel + "<td>" + eval("result[" + i + "]." + eval("orgName[" + j + "]")) + "</td>";
                        }
                        rel = rel + "</tr>";
                    }
                    rel = rel + "</table>";
                    $("#content").html(rel);
                },
                error: function (result) {
                    alert("网络发生错误，请稍后重试");
                }
            });
        })
);