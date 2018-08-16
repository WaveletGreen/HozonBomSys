function changeDateFormat(cellval) {
    if (cellval != null) {
        var date = new Date(parseInt(cellval));
        var month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
        var currentDate = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
        // var hour = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
        // var mins = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
        // var sec = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
        return date.getFullYear() + "-" + month + "-" + currentDate /*+ " " + hour + ":" + mins + ":" + sec*/;
    }
};

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
        return date.getFullYear() + "-" + month + "-" + currentDate /*+ " " + hour + ":" + mins + ":" + sec*/;
    }
}