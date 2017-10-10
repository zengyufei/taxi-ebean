const qs = require('qs')
const { mock } = require('mockjs')
const R = require('ramda')
const _ = require('lodash')
const { mockUtils: { init, add, update, del, queryById, queryPage, queryByIds, reload, queryList } } = require('../utils/index.jsx')

const prefix = 'car'
const queryPageOption = ['account', 'mobile', 'roleId', 'locked', 'initPwd', 'available', 'realName', 'orgNo']
const timeOut = 1000

const queryPageUrl = `GET /${prefix}/queryPage.htm`
const queryByIdUrl = `GET /${prefix}/queryById.htm`
const queryByIdsUrl = `GET /${prefix}/queryByIds.htm`
const insertUrl = `POST /${prefix}/insert.htm`
const deleteByIdUrl = `GET /${prefix}/remoteById.htm`
const updateUrl = `POST /${prefix}/update.htm`
const updateNotNullUrl = `POST /${prefix}/update.htm`
const reloadUrl = `GET /${prefix}/reload.htm`

const primaryKey = 'id'
const defaultPageNo = 1
const defaultPageSize = 10

const mockOption = {
  'result|100': [{
    'id|+1': 1,
    carNo: /^07\d{2}-\d{4}$/,
    plateNumber: /^T\d{5}$/,
    carFrame: '@word(5,7)',
    propertyNumber: '@word(5,7)',
    ownershipNo: '@word(5,7)',
    ownershipBeginDate: '@date',
    ownershipEndDate: '@date',
    engineNumber: '@word(7)',
    drivingLicenseDate: '@date',
    'carType|+1': [
      'BYD_E6',
      'BYD_E5',
      'BM_EU220',
    ],
    roadTransportBeginDate: '@date',
    roadTransportEndDate: '@date',
    carColor: '红色',
    certificateNo: '@word(5,7)',
    carStatus: '运营',
  }],
}

// 数据持久
init(prefix, mock(mockOption).result)

module.exports = {

  /**
   * 查询
   */
  [queryPageUrl]: (req, res) => {
    const urlParam = qs.parse(req.query)
    const { pageNo = defaultPageNo, pageSize = defaultPageSize } = urlParam
    const pageList = queryPage(prefix, +pageNo, +pageSize, queryPageOption, urlParam)
    setTimeout(() => {
      res.json({
        result: pageList,
      })
    }, timeOut)
  },


  /**
   * 查询多个
   */
  [queryByIdsUrl]: (req, res) => {
    const urlParam = qs.parse(req.query)
    const primarykeyValue = urlParam.ids
    const many = queryByIds(prefix, primaryKey, primarykeyValue)
    const isQuery = !!many

    res.json({
      code: isQuery ? 200 : 400,
      msg: isQuery ? '查询成功' : '用户不存在',
      result: many,
    })
  },

  /**
   * 查询单个
   */
  [queryByIdUrl]: (req, res) => {
    const urlParam = qs.parse(req.query)
    const primarykeyValue = urlParam[primaryKey]
    const single = queryById(prefix, primaryKey, primarykeyValue)
    const isQuery = !!single

    res.json({
      code: isQuery ? 200 : 400,
      msg: isQuery ? '查询成功' : '用户不存在',
      result: single,
    })
  },


  /**
   * 新增
   */
  [insertUrl]: (req, res) => {
    let body = qs.parse(req.body)
    const list = queryList(prefix)
    body.id = list.length + 1
    body.createTime = '2017-07-01 17:00:00'
    body.lastLoginTime = '2017-07-01 17:00:00'
    body.loginIP = '192.168.83.219'
    body.createAccount = 'admin'
    add(prefix, body)
    setTimeout(() => {
      res.json({
        code: 200,
        msg: '新增成功',
      })
    }, timeOut)
  },

  /**
   * 删除
   */
  [deleteByIdUrl]: (req, res) => {
    const { id } = qs.parse(req.query)
    del(prefix, primaryKey, id)
    res.json({
      code: 200,
      msg: '删除成功',
    })
  },

  /**
   * 修改
   */
  [updateUrl] (req, res) {
    const body = qs.parse(req.body)
    update(prefix, primaryKey, body[primaryKey], body)
    setTimeout(() => {
      res.json({
        code: 200,
        msg: '修改成功',
      })
    }, timeOut)
  },

  /**
   * 修改不为空的字段
   */
  [updateNotNullUrl] (req, res) {
    const body = qs.parse(req.body)
    update(prefix, primaryKey, body[primaryKey], body)
    res.json({
      code: 200,
      msg: '修改成功',
    })
  },


  /**
   * 重新造数据
   */
  [reloadUrl]: (req, res) => {
    reload(prefix, mock(mockOption).result)
    res.json({
      code: 200,
      msg: '重刷数据完毕',
    })
  },
}
