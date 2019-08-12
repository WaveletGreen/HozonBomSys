/**
 * Created with IntelliJ IDEA.
 * User: xlf
 * Date: 2018/12/28
 * Time: 15:51
 */

var tableUpdateHelper = tableUpdateHelper || (function () {
    var options = {
        // 需要更新的数据
        updateData: null,
        //需要更新的table的id
        tableId: null,
        // 默认更新之后不勾选
        uncheck: true,
    };

    /**
     *
     * @param updateData 需要更新的数据
     * @param tableId 需要更新的table的id
     * @returns {boolean} 默认更新之后不勾选
     */
    function updateTableRow(opts) {
        options = $.extend({}, options, opts);
        if (null == opts) {
            error("无入参数，请设置入参");
            return false;
        }
        log(opts.updateData);
        let tableId = opts.tableId;
        let myData = JSON.parse(opts.updateData);
        let rows = $('#' + tableId).bootstrapTable('getSelections');
        let index = rows[0].myIndex;
        log(index)
        log(rows)
        $('#' + tableId).bootstrapTable('updateRow', {index: index, row: myData});
        log(opts.uncheck)
        if (opts.uncheck) {
            $('#' + tableId).bootstrapTable('uncheckAll');

        }
        else {
            $('#' + tableId).bootstrapTable('check', index);
        }
        return true;
    }

    /**
     *
     * @param updateData 需要更新的数据
     * @param tableId 需要更新的table的id
     * @param uncheck 是否还勾选
     * @param index 索引位置
     * @returns {boolean} 默认更新之后不勾选
     */
    function updateTableRowWithIndex(opts) {
        options = $.extend({}, options, opts);
        if (null == opts) {
            error("无入参数，请设置入参");
            return false;
        }
        log(opts.updateData);
        let tableId = opts.tableId;
        let myData = opts.updateData;
        myData.ck = opts.uncheck;
        $('#' + tableId).bootstrapTable('updateRow', {row: myData});
        return true;
    }

    return {
        updateTableRow: updateTableRow,
        updateTableRowWithIndex: updateTableRowWithIndex,
    };
})();