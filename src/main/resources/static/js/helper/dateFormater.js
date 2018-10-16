/**
 * 格式化日期，根据毫秒数进行格式化，时间转为字符串
 * @param cellval
 * @returns {string}
 */
function changeDateFormat(cellval) {
    if (cellval != null) {
        var date = new Date(parseInt(cellval));
        var month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
        var currentDate = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
        // var hour = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
        // var mins = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
        // var sec = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
        let result = date.getFullYear() + "-" + month + "-" + currentDate /*+ " " + hour + ":" + mins + ":" + sec*/;
        if ("NaN-NaN-NaN" === result) {
            return "";
        }
        else {
            return result;
        }
    }
};

/**
 * 格式化日期，根据字符串进行格式化，字符串转为时间
 * @param strDate
 * @returns {string}
 */
function changeDateFormat2(strDate) {
    if (strDate != null) {
        var date = new Date(Date.parse(strDate));
        if (date == null) {
            return "";
        }
        var month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
        var currentDate = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
        // var hour = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
        // var mins = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
        // var sec = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
        let result = date.getFullYear() + "-" + month + "-" + currentDate /*+ " " + hour + ":" + mins + ":" + sec*/;
        if ("NaN-NaN-NaN" === result) {
            return "";
        }
        else {
            return result;
        }
    }
}