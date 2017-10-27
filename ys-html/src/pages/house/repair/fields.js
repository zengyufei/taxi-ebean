module.exports = [
    {
        key: 'communityId',
        name: '所属小区',
        render: (text, record) => record.userInfo.communityName,
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
        render: (text, record) => record.userInfo.buildingName,
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
        render: (text, record) => record.userInfo.unitName,
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
        render: (text, record) => record.userInfo.roomName,
        type: 'roomId',
        form: {
            addOrUpdate: {
                required: true,
                disabled: true,
            },
        },
    },
    {
        key: 'repairEnum',
        name: '报修类型',
        enums: {
            Leaking: '漏水',
            PowerOff: '断电',
            LightBad: '灯坏',
            ToLeak: '地漏堵',
            BadWindowsAndDoors: '门窗坏',
            LoadedLights: '装灯',
            RepairToilet: '修马桶',
            RepairWaterPipe: '修水管',
            RepairWlectrical: '修电器',
            Dredge: '疏通',
        },
        form: {
            addOrUpdate: {
                required: true,
            },
        },
    },
    {
        key: 'repairType',
        name: '如报修类型不够，则字符串辅助',
        form: {
            addOrUpdate: {
                required: true,
            },
        },
    },
    {
        key: 'mark',
        name: '报修备注',
        form: {
            addOrUpdate: {
                required: true,
            },
        },
    },
    {
        key: 'userId',
        name: '报修人',
        render: (text, record) => record.userInfo.user.realName,
        type: 'userId',
        form: {
            addOrUpdate: {
                required: true,
            },
        },
    },
    {
        key: 'promiseTime',
        name: '业主约定处理时间',
        type: 'datetime',
        form: {
            addOrUpdate: {
                required: true,
            },
        },
    },
]
