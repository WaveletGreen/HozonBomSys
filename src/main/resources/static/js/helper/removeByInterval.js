var cout = false;
var timerId = null;

function removePaginationSwitch() {
    timerId = setInterval(redo, 2000);
}

function redo() {
    let paginationSwitch = $("*[name='paginationSwitch']");
    if (!cout && paginationSwitch != null) {
        paginationSwitch.remove();
        clearTimeout(timerId);
        cout = true;
    }
}