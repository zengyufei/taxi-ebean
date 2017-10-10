module.exports = [
  {
    key: 'name',
    name: '楼栋名称',
    form: {
      required: true,
    },
  }, {
    key: 'alias',
    name: '楼栋别名',
    form: {
      required: true,
    },
  }, {
    key: 'communityId',
    name: '所属小区',
    render: (text, record) => record.communityName,
    type: 'communityId',
    form: {
      required: true,
    },
  },
]
