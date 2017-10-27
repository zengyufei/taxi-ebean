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
        key: 'catchPhotoEnum',
        name: '留影类型',
        enums: {
            CardOpen: '刷卡开门',
            PhoneOpen: '手机开门',
            PasswordOpen: '密码开门',
            CallOpen: '呼叫留影',
            MoveOpen: '移动侦测',
            ButtonOpen: '按钮开门',
            FaceOpen: '人脸开门',
        },
        form: {
            required: true,
        },
    },
    {
        key: 'deviceId',
        name: '主叫设备',
        type: 'deviceId',
        render: (text, record) => record.deviceName,
        form: {
            required: true,
        },
    },
    {
        key: 'cardId',
        name: '业主/卡号',
        render: (text, record) => {
            if (record.catchPhotoEnum !== 'CardOpen' || record.catchPhotoEnum !== 'CallOpen' || record.catchPhotoEnum !== 'ButtonOpen') {
                return record.realName
            } else if (record.catchPhotoEnum === 'CardOpen') {
                return record.cardNo
            }
            return record.cardNo
        },
        form: {
            required: true,
        },
    },
]
