module.exports = [
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
        key: 'deviceId',
        name: '门口机',
        type: 'deviceId',
        render: (text, record) => record.deviceName,
        form: {
            required: true,
        },
    },
    {
        key: 'cardEnum',
        name: '类型',
        enums: {
            UserCard: '住户卡',
            ManageCard: '物管卡',
        },
        form: {
            required: true,
        },
    },
    {
        key: 'userId',
        name: '用户',
        render: (text, record) => record.realName,
        type: 'userId',
        form: {
            required: true,
        },
    },
    {
        key: 'successFailEnum',
        name: '结果',
        enums: {
            Success: '成功',
            Fail: '失败',
        },
        form: {
            required: true,
        },
    },
    {
        key: 'openDoorTime',
        name: '开门时间',
        type: 'datetime',
        form: {
            required: true,
        },
    },
]
