module.exports = [
    {
        key: 'name',
        name: '单元名称',
        form: {
            addOrUpdate: {
                required: true,
            },
        },
    },
    {
        key: 'alias',
        name: '单元别名',
        type: 'edit',
        form: {
            addOrUpdate: {
                required: true,
            },
        },
    },
    {
        key: 'communityId',
        name: '所属小区',
        render: (text, record) => record.communityName,
        type: 'communityId',
        form: {
            addOrUpdate: {
                required: true,
            },
        },
    },
    {
        key: 'buildingId',
        name: '所属楼栋',
        render: (text, record) => record.buildingName,
        type: 'buildingId',
        form: {
            addOrUpdate: {
                required: true,
                disabled: true,
            },
        },
    },
]
