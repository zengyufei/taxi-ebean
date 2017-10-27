module.exports = [
    {
        key: 'name',
        name: '设备名称',
        form: {
            required: true,
        },
    },
    {
        key: 'equNo',
        name: '设备号',
        form: {
            add: {
                required: true,
            },
            update: {
                disabled: true,
            },
        },
    },
    {
        key: 'equEnum',
        name: '设备类型',
        enums: {
            DoorMachine: '门口机',
            CenterMachine: '中心机',
            WallMachine: '围墙机',
            IndoorMachine: '室内机',
        },
        form: {
            required: true,
        },
    },
    {
        key: 'ip',
        name: 'Ip地址',
        form: {},
    },
    {
        key: 'mac',
        name: 'Mac地址',
        form: {},
    },
    {
        key: 'password',
        name: '密码',
        form: {},
    },
    {
        key: 'onlineEnum',
        name: '在线情况',
        enums: {
            Offline: '离线',
            Online: '在线',
        },
        form: false,
    },
    {
        key: 'times',
        name: 'IOS开门延时',
        form: {},
    },
    {
        key: 'keenNumb',
        name: '蓝牙开门灵敏度',
        form: {},
    },
    {
        key: 'communityId',
        name: '所属小区',
        render: (text, record) => record.communityName,
        type: 'communityId',
        form: {
            required: true,
        },
    },
    {
        key: 'buildingId',
        name: '所属楼栋',
        render: (text, record) => record.buildingName,
        type: 'buildingId',
        form: {
            required: true,
            disabled: true,
        },
    },
    {
        key: 'unitId',
        name: '所属单元',
        render: (text, record) => record.unitName,
        type: 'unitId',
        form: {
            required: true,
            disabled: true,
        },
    },
    {
        key: 'roomId',
        name: '所属房间',
        render: (text, record) => record.roomName,
        type: 'roomId',
        form: {
            required: true,
            disabled: true,
        },
    },
]
