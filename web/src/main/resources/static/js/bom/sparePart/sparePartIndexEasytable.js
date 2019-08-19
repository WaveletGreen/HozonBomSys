/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */
/**
 * 创建表头
 * @returns {Array}
 */
function createColumnVue() {
    let column = [];
    column.push({width: 60, titleAlign: 'center', columnAlign: 'center', type: 'selection'}),
        column.push({
            field: 'ads',
            title: 'A/D/S',
            width: 100,
            titleAlign: 'center',
            columnAlign: 'center',
            isResize: true
        });
    column.push({
        field: 'hierarchy',
        title: '层级',
        width: 100,
        titleAlign: 'center',
        columnAlign: 'center',
        isResize: true
    });
    column.push({field: 'major', title: '专业', width: 100, titleAlign: 'center', columnAlign: 'center', isResize: true});
    column.push({field: 'level', title: '级别', width: 100, titleAlign: 'center', columnAlign: 'center', isResize: true});
    column.push({
        field: 'productivePartCode',
        title: '生产零件号',
        width: 200,
        titleAlign: 'center',
        columnAlign: 'center',
        isResize: true
    });
    column.push({
        field: 'productivePartName',
        title: '生产零件名称',
        width: 200,
        titleAlign: 'center',
        columnAlign: 'center',
        isResize: true
    });
    column.push({
        field: 'sparePartCode', title: '备件零件号',
        width: 200, titleAlign: 'center', columnAlign: 'center', isResize: true
    });
    column.push({
        field: 'sparePartName', title: '备件零件名称',
        width: 200, titleAlign: 'center', columnAlign: 'center', isResize: true
    });
    column.push({
        field: 'isSparePart', title: '是否备件',
        width: 80, titleAlign: 'center', columnAlign: 'center', isResize: true
    });
    column.push({
        field: 'unit', title: '单位',
        width: 100, titleAlign: 'center', columnAlign: 'center', isResize: true
    });
    column.push({
        field: 'department', title: '专业部门',
        width: 100, titleAlign: 'center', columnAlign: 'center', isResize: true
    });
    column.push({
        field: 'responsibleEngineer',
        title: '责任工程师',
        width: 200,
        titleAlign: 'center',
        columnAlign: 'center',
        isResize: true
    });
    column.push({
        field: 'supplier', title: '供应商',
        width: 200, titleAlign: 'center', columnAlign: 'center', isResize: true
    });
    column.push({
        field: 'code', title: '代码',
        width: 60, titleAlign: 'center', columnAlign: 'center', isResize: true
    });
    column.push({
        field: 'purchasingEngineer',
        title: '采购工程师',
        width: 200,
        titleAlign: 'center',
        columnAlign: 'center',
        isResize: true
    });
    column.push({
        field: 'remark', title: '备注',
        width: 200, titleAlign: 'center', columnAlign: 'center', isResize: true
    });
    column.push({
        field: 'partClass', title: '零件分类',
        width: 100, titleAlign: 'center', columnAlign: 'center', isResize: true
    });
    column.push({
        field: 'workshop1', title: '车间1',
        width: 60, titleAlign: 'center', columnAlign: 'center', isResize: true
    });
    column.push({
        field: 'workshop2', title: '车间2',
        width: 60, titleAlign: 'center', columnAlign: 'center', isResize: true
    });
    return column;
}

/**
 * 初始化VUE组件
 */
let vm = new Vue({
    el: '#sparePartTableDiv',
    data: function () {
        return {
            pageSize: 20,
            pageIndex: 1,
            total: 0,
            pageSizeOption: [10, 50, 100, 200, 300, 1000],
            tableData: [],
            columns: createColumnVue()
        }
    },
    methods: {
        //
        selectALL(selection) {
            console.log('select-aLL', selection);
        },

        selectChange(selection, rowData) {
            console.log('select-change', selection, rowData);
        },

        selectGroupChange(selection) {
            console.log('select-group-change', selection);
        },
        pageChange(pageIndex) {

            this.pageIndex = pageIndex;
            this.getTableData();
            console.log(pageIndex)
        },
        pageSizeChange(pageSize) {
            this.pageIndex = 1;
            this.pageSize = pageSize;
            this.getTableData();
        },
        getTableData() {
            console.log('getTableData');
        },
        loadServerData() {
            axios.get(url, {
                params: {
                    projectId: projectId
                }
            })
                .then(function (response) {
                    console.log(response.data);
                    console.log(vm.$data.tableData)
                    console.log(vm.$data.total)
                    vm.$data.tableData = response.data.result;
                    vm.$data.total = response.data.totalCount;
                    console.log(vm.$data.tableData)
                    console.log(vm.$data.total)
                })
                .catch(function (error) {
                    console.log(error);
                });
        }
    },
    mounted() {
        this.loadServerData();
    },
});
