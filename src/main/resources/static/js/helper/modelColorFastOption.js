var temp = null;

function xsearch2() {
    let el = $('#feature').val();
    if ($("#" + el)) {
        document.getElementById($('#feature').val()).scrollIntoView(true);
        if (temp != null || temp != undefined || temp != "") {
            if (temp != el) {
                $('#' + el + ' label').css("color", "red");
                $('#' + temp + ' label').css("color", "black");
                temp = el;
            }
        }
        else {
            temp = el;
            $('#' + el + ' label').css("color", "red");
        }
    }
}