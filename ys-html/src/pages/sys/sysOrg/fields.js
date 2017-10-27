module.exports = [
    {
        key: 'orgNo',
        name: '组织编号',
    },
    {
        key: 'parentOrgNo',
        name: '上级组织',
        type: 'parentOrgNo',
    },
    {
        key: 'orgName',
        name: '组织名称',
        required: false,
    },
    {
        key: 'priority',
        name: '排序',
        type: 'number',
    },
    {
        key: 'available',
        name: '有效性',
        enums: {
            true: '有效',
            false: '无效',
        },
        hasFeedback: false,
    },
    {
        key: 'description',
        name: '描述',
        type: 'textarea',
    },
    {
        key: 'address',
        name: '地址',
    },
]
