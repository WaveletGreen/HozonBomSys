/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can't post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */
/**
 * 由于想偷懒，不想再从JS删除一些日志数据，写一个SETTING直接控制是否打印日志即可
 */
/**
 * 设置是否打印信息
 * @type {boolean}
 */
var DEBUG_SETTING = true;

/**
 * log级别
 * @param obj
 */
function log(obj) {
    if (DEBUG_SETTING)
        console.log(obj);
}

/**
 * war级别
 * @param obj
 */
function warn(obj) {
    if (DEBUG_SETTING)
        console.warn(obj);
}

/**
 * error级别
 * @param obj
 */
function error(obj) {
    if (DEBUG_SETTING)
        console.error(obj);
}
