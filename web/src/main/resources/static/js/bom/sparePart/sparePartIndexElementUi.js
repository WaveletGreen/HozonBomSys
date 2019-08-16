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
function createElementUiTableColumn() {
    let column = [];

    // column.push(
    //     {width: 60, titleAlign: 'center', columnAlign: 'center', type: 'selection'}
    // ),
    column.push({label: "A/D/S", prop: "ads", width: 100, maxWidth: 400});
    column.push({label: "层级", prop: "hierarchy", width: 100, maxWidth: 400});
    column.push({prop: 'major', label: '专业', width: 100, maxWidth: 400});
    column.push({prop: 'level', label: '级别', width: 100, maxWidth: 400});
    column.push({prop: 'productivePartCode', label: '生产零件号', width: 200, maxWidth: 400});
    column.push({prop: 'productivePartName', label: '生产零件名称', width: 200, maxWidth: 400});
    column.push({prop: 'sparePartCode', label: '备件零件号', width: 200, maxWidth: 400});
    column.push({prop: 'sparePartName', label: '备件零件名称', width: 200, maxWidth: 400});
    column.push({prop: 'isSparePart', label: '是否备件', width: 100, maxWidth: 400});
    column.push({prop: 'unit', label: '单位', width: 100, maxWidth: 400});
    column.push({prop: 'department', label: '专业部门', width: 100, maxWidth: 400});
    column.push({prop: 'responsibleEngineer', label: '责任工程师', width: 200, maxWidth: 400});
    column.push({prop: 'supplier', label: '供应商', width: 200, maxWidth: 400});
    column.push({prop: 'code', label: '代码', width: 100, maxWidth: 400});
    column.push({prop: 'purchasingEngineer', label: '采购工程师', width: 200, maxWidth: 400});
    column.push({prop: 'remark', label: '备注', width: 200, maxWidth: 400});
    column.push({prop: 'partClass', label: '零件分类', width: 100, maxWidth: 400});
    column.push({prop: 'workshop1', label: '车间1', width: 100, maxWidth: 400});
    column.push({prop: 'workshop2', label: '车间2', width: 100, maxWidth: 400});
    return column;
}

/**
 * 初始化element UI table
 */
let vmx = new Vue({
    el: '#sparePartTableDivElementUi',
    data: function () {
        return {
            sortItem: {prop: 'productivePartCode', order: 'descending'},
            height: 300,//设置固定表头
            cols: [
                {label: "日期", prop: "date", type: "sort", width: 200},
                {label: "姓名", prop: "name", type: "sort", width: 300},
                {label: "地址", prop: "address", type: "normal"},
            ],
            tableDatax: [{
                productivePartCode: '2016-05-02',
                productivePartName: '王小虎',
                sparePartCode: '上海市普陀区金沙江路 1518 弄',
                id: 1
            }, {
                productivePartCode: '2016-05-04',
                productivePartName: '王小虎',
                sparePartCode: '上海市普陀区金沙江路 1517 弄',
                id: 2
            }, {
                productivePartCode: '2016-05-01',
                productivePartName: '王小虎',
                sparePartCode: '上海市普陀区金沙江路 1519 弄',
                id: 3
            }, {
                productivePartCode: '2016-05-03',
                productivePartName: '王小虎',
                sparePartCode: '上海市普陀区金沙江路 1516 弄',
                id: 4
            }],
            tableData: [{
                date: '2016-05-02',
                name: '王小虎',
                address: '上海市普陀区金沙江路 1518 弄',
                id: 1
            }, {
                date: '2016-05-04',
                name: '王小虎',
                address: '上海市普陀区金沙江路 1517 弄',
                id: 2
            }, {
                date: '2016-05-01',
                name: '王小虎',
                address: '上海市普陀区金沙江路 1519 弄',
                id: 3
            }, {
                date: '2016-05-03',
                name: '王小虎',
                address: '上海市普陀区金沙江路 1516 弄',
                id: 4
            }]
        }
    },
    methods: {
        formatter(row, column) {
            return row.address;
        },
        handleSelectionChange(val) {
            this.multipleSelection = val;
            console.log(val)
        },
        loadServerData() {
            axios.get(url, {
                params: {
                    projectId: getProjectUid()
                }
            })
                .then(function (response) {
                    log('-------测试--------');
                    console.log(response.data);
                    console.log(vmx.$data.tableDatax)
                    vmx.$data.tableDatax = response.data.result;
                    console.log(vmx.$data.tableDatax)
                    log('-------测试--------end');
                })
                .catch(function (error) {
                    console.log(error);
                });
        }
    },
    created() {
        this.cols = createElementUiTableColumn();
        console.log(this.cols);
    },
    mounted() {
        this.loadServerData();
    },
});
