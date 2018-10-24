/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

jQuery.download = function(url, method, filedir, filename){
    jQuery('<form action="'+url+'" method="'+(method||'post')+'">' +  // action请求路径及推送方法
        '<input type="text" name="filedir" value="'+filedir+'"/>' + // 文件路径
        '<input type="text" name="filename" value="'+filename+'"/>' + // 文件名称
        '</form>')
        .appendTo('body').submit().remove();
};
/*===================下载文件
 * options:{
 * url:'',  //下载地址
 * data:{name:value}, //要发送的数据
 * method:'post'
 * }
 */
var DownLoadFile = function (options) {
    var config = $.extend(true, { method: 'post' }, options);
    var $iframe = $('<iframe id="down-file-iframe" />');
    var $form = $('<form target="down-file-iframe" method="' + config.method + '" content-Type="application/json","/>');
    $form.attr('action', config.url);
    for (var key in config.data) {
        $form.append('<input type="hidden" name="' + key + '" value="' + config.data[key] + '" />');
    }
    $iframe.append($form);
    $(document.body).append($iframe);
    $form[0].submit();
    $iframe.remove();
}