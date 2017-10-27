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
    {
        key: 'unitId',
        name: '所属单元',
        render: (text, record) => record.unitName,
        type: 'unitId',
        form: {
            addOrUpdate: {
                required: true,
                disabled: true,
            },
        },
    },
    {
        key: 'roomId',
        name: '所属房间',
        render: (text, record) => record.roomName,
        type: 'roomId',
        form: {
            addOrUpdate: {
                required: true,
                disabled: true,
            },
        },
    },
    {
        key: 'userId',
        name: '关联的用户',
        render: (text, record) => record.user.realName,
        type: 'userId',
        form: {
            addOrUpdate: {
                required: true,
            },
        },
    },
    {
        key: 'authFlag',
        name: '认证',
        type: 'boolean',
        form: {
            addOrUpdate: {
                required: true,
            },
        },
    },
    {
        key: 'bindCallFlag',
        name: '绑定电话呼叫',
        type: 'boolean',
        form: {
            addOrUpdate: {
                required: true,
            },
        },
    },
    {
        key: 'mark',
        name: '详情',
        form: {
            addOrUpdate: {
                required: true,
            },
        },
    },
    {
        key: 'stayEnum',
        name: '常住',
        enums: {
            Resident: '住户',
            Temporary: '临时',
        },
        form: {
            addOrUpdate: {
                required: true,
            },
        },
    },
    {
        key: 'authEnum',
        name: '状态',
        enums: {
            Pending: '待审核',
            NotPass: '未通过',
            Pass: '已通过',
        },
        form: {
            addOrUpdate: {
                required: true,
            },
        },
    },
    {
        key: 'applicationTime',
        name: '申请时间',
        type: 'datetime',
        form: {
            addOrUpdate: {
                required: true,
            },
        },
    },
]
