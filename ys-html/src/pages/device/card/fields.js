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
        key: 'cardNo',
        name: '卡号',
        form: {
            addOrUpdate: {
                required: true,
            },
        },
    },
    {
        key: 'cardEnum',
        name: '卡类型',
        enums: {
            UserCard: '住户卡',
            ManageCard: '物管卡',
        },
        form: {
            addOrUpdate: {
                required: true,
            },
        },
    },
    {
        key: 'userId',
        name: '持卡人 id',
        type: 'auto',
        form: {
            addOrUpdate: {
                required: true,
            },
        },
    },
    {
        key: 'realName',
        name: '持卡人',
        render(text, record) {
            if (record.realName) {
                return record.realName
            } else {
                return record.userInfo && record.userInfo.user && record.userInfo.user.realName
            }
        },
        form: {
            addOrUpdate: {
                required: true,
            },
        },
    },
    {
        key: 'idCard',
        name: '身份证',
        render(text, record) {
            if (record.idCard) {
                return record.idCard
            } else {
                return record.userInfo && record.userInfo.user && record.userInfo.user.idCard
            }
        },
        form: {
            addOrUpdate: {
                required: true,
            },
        },
    },
    {
        key: 'phone',
        name: '电话',
        render(text, record) {
            if (record.phone) {
                return record.phone
            } else {
                return record.userInfo && record.userInfo.user && record.userInfo.user.phone
            }
        },
        form: {
            addOrUpdate: {
                required: true,
            },
        },
    },
    {
        key: 'roomNo',
        name: '房间号',
        render(text, record) {
            if (record.roomNo) {
                return record.roomNo
            } else {
                return record.userInfo && record.userInfo.roomName
            }
        },
        form: {
            addOrUpdate: {
                required: true,
            },
        },
    },
    {
        key: 'validDate',
        name: '有效期',
        type: 'date',
        form: {
            addOrUpdate: {
                required: true,
            },
        },
    },
    {
        key: 'validTime',
        name: '有效时段',
        type: 'datetime',
        form: {
            addOrUpdate: {
                required: true,
            },
        },
    },
]
