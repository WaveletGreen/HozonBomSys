/**
 * Created with IntelliJ IDEA.
 * User: xlf
 * Date: 2018/11/14
 * Time: 9:26
 */
var columnOfFeature1 = [
    {
        field: 'headDesc',
        title: '变更类型',
    },
    {
        field: 'featureName',
        title: '特性名称',
    },
    {
        field: 'featureDesc',
        title: '特性描述',
        align: 'center',
        valign: 'middle',
    },
    {
        field: 'h9featureenname',
        title: '特性英文名称',
        align: 'center',
        valign: 'middle'
    },
    {
        field: 'featureValueName',
        title: '特性值/配置代码',
        align: 'center',
        valign: 'middle',
    },
    {
        field: 'featureValueDesc',
        title: '特性值/配置(描述)',
        align: 'center',
        valign: 'middle',
    },
    {
        field: 'cfgEffectedDate',
        title: '生效时间',
        align: 'center',
        valign: 'middle',
        //——修改——获取日期列的值进行转换
        formatter: function (value, row, index) {
            return dateToStringFormat(value)
        }
    },
    {
        field: 'cfgAbolishDate',
        title: '废止时间',
        align: 'center',
        valign: 'middle',
        //——修改——获取日期列的值进行转换
        formatter: function (value, row, index) {
            return dateToStringFormat(value)
        }
    }];


function skip(type) {
    if(type=='feature'){
        // $.ajax({
        //    type : "POST",
        //    url : "./vwoProcess/changeDetails",
        //    success : function (result) {
        //
        //    },
        //    error : function (result) {
        //
        //    }
        // });
        // window.
        var $table = $("#routingDaTable");
        $table.bootstrapTable('destroy');
        $table.bootstrapTable({
            // url: "getFeatureTable?vwoId=" + vwoId,
            url : "../vwo/getFeatureTable?vwoId="+6,
            method: "POST",
            height: 400,// $(window.parent.document).find("#wrapper").height() - document.body.offsetHeight - 100,
            width: $(window).width(),
            showToggle: false,                   //是否显示详细视图和列表视图的切换按钮
            showColumns: false,                  //是否显示所有的列
            showRefresh: false,                  //是否显示刷新按钮
            pagination: false,                   //是否显示分页（*）
            clickToSelect: false,                // 单击某一行的时候选中某一条记录
            formId: "null",                      //需要定义formId，不定义的话会造成jQuery异常，随便定义一个没有的id即可
            /**列信息，需要预先定义好*/
            columns: columnOfFeature1,
            onLoadSuccess: function () {
                //加载成功时执行,渲染双行的
                changeTableRendering("routingDaTable");
            },
        });
    }
}


// function OnToTeXing() {
//     window.Ewin.dialog({
//         title: "添加",
//         url:"../change/texing",
//         gridId: "gridId",
//         width: 950,
//         height: 600
//     })
// }