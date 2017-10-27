module.exports = [
    {
        key: 'userId',
        name: '业主',
        render: (text, record) => record.userInfo.user.realName,
        type: 'userId',
        form: {
            addOrUpdate: {
                submit: false,
                disabled: true,
                render: (text, record) => {
                    return record.userInfo.user.realName
                },
                required: true,
            },
        },
    },
    {
        key: 'content',
        name: '内容',
        form: {
            addOrUpdate: {
                required: true,
            },
        },
    },
    {
        key: 'complainEnum',
        name: '投诉类型',
        enums: {
            Theme: '主题',
            Reply: '回复',
        },
        form: {
            addOrUpdate: {
                required: true,
            },
        },
    },
    {
        key: 'userMark',
        name: '业主反馈',
        form: {
            addOrUpdate: {
                required: true,
            },
        },
    },
]
