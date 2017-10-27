module.exports = [
    {
        key: 'name',
        name: '小区名称',
        form: {
            required: true,
        },
    },
    {
        key: 'buildMax',
        name: '小区楼栋数',
        type: 'number',
        meta: {
            min: 1,
        },
        form: {
            required: true,
        },
    },
    {
        key: 'unitMax',
        name: '每栋单元数',
        type: 'number',
        meta: {
            min: 1,
        },
        form: {
            required: true,
        },
    },
    {
        key: 'floorMax',
        name: '每单元楼层最大值',
        type: 'number',
        meta: {
            min: 1,
        },
        form: {
            required: true,
        },
    },
    {
        key: 'roomMax',
        name: '每楼层房间数最大值',
        type: 'number',
        meta: {
            min: 1,
        },
        form: {
            required: true,
        },
    },
]
