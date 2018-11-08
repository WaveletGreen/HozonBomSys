/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

/**
 * 简单的获取JSON对象的长度
 * @param obj
 * @returns {number}
 */
function getLength(obj) {
    let result = 0;
    if (null != obj && undefined != obj)
        for (let i in obj) {
            if (parentNode.hasOwnProperty(i))
                result++;
        }
    return result;
}

/**
 * 获取当前JSON的长度
 * @param obj
 * @returns {number}
 */
function getLengthOfJson(obj){
    let result = 0;
    if (null != obj && undefined != obj)
        for (let i in obj) {
            if (obj.hasOwnProperty(i))
                result++;
        }
    return result;
}
/**
 * 获取二维JSON对象的长度
 * @param obj
 * @returns {number}
 */
function getLength2(obj) {
    let result = 0;
    if (null != obj && undefined != obj) {
        for (let i in obj) {
            if (obj.hasOwnProperty(i) && obj[i] != null) {
                for (let j in obj[i]) {
                    if (obj[i].hasOwnProperty(j)) {
                        result++;
                    }
                }
            }
        }
    }
    return result;
}