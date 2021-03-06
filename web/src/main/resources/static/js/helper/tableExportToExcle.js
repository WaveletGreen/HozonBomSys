/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can't post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

var idTmr;

function getExplorer() {
    var explorer = window.navigator.userAgent;
    //ie
    //"Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; .NET4.0C; .NET4.0E; .NET CLR 2.0.50727; .NET CLR 3.0.30729; .NET CLR 3.5.30729; rv:11.0) like Gecko"
    //Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.140 Safari/537.36 Edge/17.17134
    //"Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; .NET4.0C; .NET4.0E; .NET CLR 2.0.50727; .NET CLR 3.0.30729; .NET CLR 3.5.30729; rv:11.0) like Gecko"
    //"Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; .NET4.0C; .NET4.0E; .NET CLR 2.0.50727; .NET CLR 3.0.30729; .NET CLR 3.5.30729; rv:11.0) like Gecko"
 /*   if (explorer.indexOf("Trident") >= 0) {
        return 'ie';
    }
    //firefox
    else*/
    if (explorer.indexOf("Firefox") >= 0) {
        return 'Firefox';
    }
    //Chrome
    else if (explorer.indexOf("Chrome") >= 0) {
        return 'Chrome';
    }
    //Operad
    else if (explorer.indexOf("Opera") >= 0) {
        return 'Opera';
    }
    //Safari
    else if (explorer.indexOf("Safari") >= 0) {
        return 'Safari';
    }

    else if (explorer.indexOf('Trident') > 0 && explorer.indexOf('compatible') < 0) {
        return 'ie';
    }
}

/**
 * 将页面上的表格拷贝到excle中，并完成下载操作
 * @param tableid
 * @param name 下载的文件名称
 */
function table2excel(tableid, name) {
    //不支持IE浏览器下在，也不支持EDGE下载
    if (getExplorer() == 'ie') {
        var curTbl = document.getElementById(tableid);
        var oXL = new ActiveXObject("Excel.Application");

        //创建AX对象excel
        var oWB = oXL.Workbooks.Add();
        //获取workbook对象
        var xlsheet = oWB.Worksheets(1);
        //激活当前sheet
        var sel = document.body.createTextRange();
        sel.moveToElementText(curTbl);
        //把表格中的内容移到TextRange中
        sel.select();
        //全选TextRange中内容
        sel.execCommand("Copy");
        //复制TextRange中内容
        xlsheet.Paste();
        //粘贴到活动的EXCEL中
        oXL.Visible = true;
        //设置excel可见属性

        try {
            var fname = oXL.Application.GetSaveAsFilename("Excel.xls", "Excel Spreadsheets (*.xls), *.xls");
        } catch (e) {
            print("Nested catch caught " + e);
        } finally {
            oWB.SaveAs(fname);
            oWB.Close(savechanges = false);
            //xls.visible = false;
            oXL.Quit();
            oXL = null;
            //结束excel进程，退出完成
            //window.setInterval("Cleanup();",1);
            idTmr = window.setInterval("Cleanup();", 1);

        }
    }
    else {
        tableToExcel(tableid, name)
    }
}

function Cleanup() {
    window.clearInterval(idTmr);
    CollectGarbage();
}

var tableToExcel = (function () {
    var uri = 'data:application/vnd.ms-excel;base64,',
        //格式化导出表格的样式
        template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel"' +
            'xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet>'
            + '<x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets>'
            + '</x:ExcelWorkbook></xml><![endif]-->' +
            ' <style type="text/css">' +
            '.excelTable  {' +
            'border-collapse:collapse;' +
            ' border:thin solid #999; ' +
            '}' +
            '   .excelTable  th {' +
            '   border: thin solid #999;' +
            '  padding:20px;' +
            '  text-align: center;' +
            '  border-top: thin solid #999;' +
            ' background-color: #E6E6E6;' +
            ' }' +
            ' .excelTable  td{' +
            ' border:thin solid #999;' +
            '  padding:2px 5px;' +
            '  text-align: center;' +
            ' }</style>' +
            '</head><body ><table class="excelTable">{table}</table></body></html>',
        base64 = function (s) {
            return window.btoa(unescape(encodeURIComponent(s)))
        },
        format = function (s, c) {
            return s.replace(/{(\w+)}/g,
                function (m, p) {
                    return c[p];
                })
        }
    return function (table, name) {
        if (name.length == 0) {
            name = '导出Excel信息';
        }
        if (!table.nodeType) table = document.getElementById(table)
        var ctx = {worksheet: name || 'Worksheet', table: table.innerHTML}
        //window.location.href = uri + base64(format(template, ctx))
        var downloadLink = document.createElement("A");
        downloadLink.href = uri + base64(format(template, ctx));
        downloadLink.download = name + '_' + formatTime(new Date(new Date().getTime()), 'yyyy_mm_dd hh:ii:ss') + '.xls';
        downloadLink.target = '_blank';
        document.body.appendChild(downloadLink);
        downloadLink.click();
        document.body.removeChild(downloadLink);
    }
})()

/**
 * 扩展String对象,添加查找字符串出现的次数
 * @param (String) str 要测试的字符串
 */
String.prototype.findCount = function (str) {
    return this.split(str).length - 1;
}

/**
 * 复制字符串
 * @param (String) str 要复制的字符串
 * @param (String) num 要复制的次数
 * @return (Number) 复制后的字符串
 */
function copy(str, num) {
    var tmp = '';
    for (var i = 0; i < num; i++) {
        tmp += str;
    }
    return tmp;
}

/**
 * 格式化时间字符串，支持Date对象
 * @param (Number) time 要格式化的时间串，或者是一个Date对象
 * @param (String) format 格式，如：yyyymmddhhiiss yyyy-mm-dd hh:ii:ss
 * @return (String) 格式化后的时间串
 */
function formatTime(time /* Number */, format /* String */) {
    var
        y = format.findCount('y'),
        m = format.findCount('m'),
        d = format.findCount('d'),
        h = format.findCount('h'),
        i = format.findCount('i'),
        s = format.findCount('s');

    time = time || '';
    format = format || '';
    format = format.toLowerCase();
    if (time == '') {
        return time;
    }
    if (time.constructor == Date) {
        var tmp = '' + time.getFullYear() +
            ('00' + (time.getMonth() + 1)).slice(-2) +
            ('00' + time.getDate()).slice(-2) +
            ('00' + time.getHours()).slice(-2) +
            ('00' + time.getMinutes()).slice(-2) +
            ('00' + time.getSeconds()).slice(-2);
        time = tmp;
    }
    /*
    if(time.length <format.length){
        alert('要格式化的时间串与转换格式不一致！');
        return false;
    }
    */

    if (y > 0) {
        format = format.replace(copy('y', y), time.substring(0, 4).slice(-y));
    }

    if (m > 0) {
        format = format.replace(copy('m', m), ('00' + time.substring(4, 2)).slice(-m));
    }

    if (d > 0) {
        format = format.replace(copy('d', d), ('00' + time.substring(6, 2)).slice(-d));
    }

    if (h > 0) {
        format = format.replace(copy('h', h), ('00' + time.substring(8, 2)).slice(-h));
    }

    if (i > 0) {
        format = format.replace(copy('i', i), ('00' + time.substring(10, 2)).slice(-i));
    }

    if (s > 0) {
        format = format.replace(copy('s', s), ('00' + time.substring(12, 2)).slice(-s));
    }

    return format;
}