/*
 * @Author: zengyufei
 * @Date: 2017-08-25 15:02:18
 * @Last Modified by: zengyufei
 * @Last Modified time: 2017-08-25 15:02:18
 */
import { connect } from 'dva'
import { Form, Button, Popconfirm, Table, Upload, Modal } from 'antd'
import qs from 'qs'

import ZSearch from 'ZSearch'
import { getColumns } from 'TableUtils'
import { getFields, getSearchFields } from 'FormUtils'

import Add from './Add'
import Update from './Update'
import Detail from './Detail'

const { tokenSessionKey } = constant

let index = option => {
  const { loading, methods, form, res, register, page } = option
  const { onSearch, toDetail, toAdd, toEdit, toExport, onShowSizeChange, onChange, handlerUpload } = methods

  /**
   * 上传文件
   */
  const importCar = {
    name: 'file',
    action: `${BASE_URL}/driver/lostAndFound/import.htm?token=${session.get(tokenSessionKey)}`,
    onChange: handlerUpload,
  }

  const btns = (
    <div>
      <ZButton permission="driver:lostAndFound:insert">
        <Button type="primary" icon="plus-circle-o" onClick={toAdd}>新增</Button>&nbsp;
      </ZButton>
      <ZButton permission="driver:lostAndFound:export">
        <Popconfirm title="是否确定要导出" onConfirm={toExport} >
          <Button type="primary" icon="export" >导出</Button>&nbsp;
        </Popconfirm>
      </ZButton>
      <ZButton permission="driver:lostAndFound:import">
        <Upload {...importCar}>
          <Button type="primary" icon="download" >导入</Button>
        </Upload>
      </ZButton>
    </div>
  )
  const searchBarProps = {
    form,
    showLabel: true,
    showReset: true,
    btns,
    searchCacheKey: 'lostAndFound_condin',
    searchFields: getSearchFields(fields, ['carNo', 'plateNumber', 'handTime']).values(),
    fields: getFields(fields, local.get('lostAndFound_condin') || ['carNo', 'plateNumber', 'handTime']).values(),
    item: {
    },
    onSearch,
    onReset: onSearch,
  }

  const operatorColumn = [{
    key: 'operator',
    name: '操作',
    // 扩展字段的render支持自定义渲染
    render: (text, record) => {
      return (
        <span>
          <ZButton permission="driver:lostAndFound:query">
            <Button type="primary" onClick={() => toDetail(record)}>详情</Button>&nbsp;
          </ZButton>
          <ZButton permission="driver:lostAndFound:update">
            <Button type="primary" onClick={() => toEdit(record)}>编辑</Button>&nbsp;
          </ZButton>
        </span>
      )
    },
  }]
  const tableColumns = getColumns(fields).enhance(operatorColumn).values()

  let a
  if (res === 'add') {
    a = <Add key="add" />
  } else if (res === 'update') {
    a = <Update key="update" />
  } else if (res === 'detail') {
    a = <Detail key="detail" />
  }

  return (
    <div>
      {
        register ? a : <div>
          <div>
            <ZSearch {...searchBarProps} />
          </div>

          <Table
            scroll={{ x: 1200 }}
            rowKey="id"
            dataSource={(page && page.dataList) || []}
            columns={tableColumns}
            loading={loading}
            bordered
            pagination={{ // 分页
              current: (page && +page.pageNo),
              total: (page && +page.totalCount) || 0, // 总数量
              pageSize: (page && +page.pageSize) || 10, // 显示几条一页
              defaultPageSize: 10, // 默认显示几条一页
              showSizeChanger: true, // 是否显示可以设置几条一页的选项
              onShowSizeChange,
              onChange,
              showTotal() { // 设置显示一共几条数据
                return `共 ${(page && page.totalCount) || 0} 条数据`
              },
            }}
          />
        </div>
      }
    </div>
  )
}

const mapStateToProps = ({ loading, lostAndFoundStore }) => {
  return {
    loading: loading.models.lostAndFoundStore,
    register: lostAndFoundStore.register,
    res: lostAndFoundStore.res,
    page: lostAndFoundStore.page,
  }
}


const mapDispatchToProps = (dispatch, { form }) => {
  return {
    form,
    methods: {

      onSearch(values) {
        if (values) {
          if (values.handTime) {
            values.startTime = form.getFieldValue('handTime').format('YYYY-MM-DD')
            values.endTime = form.getFieldValue('handTime').format('YYYY-MM-DD')
            delete values.handTime
          }
        }
        dispatch({
          type: 'lostAndFoundStore/queryPage',
          ...values,
        })
      },

      /* 详情 */
      toDetail(lostAndFound) {
        dispatch({
          type: 'lostAndFoundStore/toEdit',
          res: 'detail',
          lostAndFound,
        })
      },
      /* 添加 */
      toAdd() {
        dispatch({
          type: 'lostAndFoundStore/toRegister',
          res: 'add',
        })
      },
      /* 编辑 */
      toEdit(lostAndFound) {
        dispatch({
          type: 'lostAndFoundStore/toEdit',
          res: 'update',
          lostAndFound,
        })
      },

      /* 删除 */
      confirm(id) {
        dispatch({
          type: 'lostAndFoundStore/deleteById',
          id,
        })
      },


      /* 导出 */
      toExport() {
        const carNo = form.getFieldValue('carNo')
        const plateNumber = form.getFieldValue('plateNumber')
        const params = {
          carNo,
          plateNumber,
        }
        // 删除空值、undefind
        Object.keys(params).map(v => params[v] || delete params[v])
        const paramsForGet = (params && qs.stringify(params)) || ''
        window.location.href = `${BASE_URL}/driver/lostAndFound/export.htm?token=${session.get(tokenSessionKey)}&${paramsForGet}`
      },


      onShowSizeChange(current, pageSize) { // 当几条一页的值改变后调用函数，current：改变显示条数时当前数据所在页；pageSize:改变后的一页显示条数
        let values = form.getFieldsValue();
        if (values) {
          if (values.handTime) {
            values.startTime = form.getFieldValue('handTime').format('YYYY-MM-DD')
            values.endTime = form.getFieldValue('handTime').format('YYYY-MM-DD')
            delete values.handTime
          }
        }
        dispatch({
          type: 'lostAndFoundStore/queryPage',
          pageNo: current,
          pageSize,
          ...values,
        })
      },

      onChange(current, pageSize) { // 点击改变页数的选项时调用函数，current:将要跳转的页数
        let values = form.getFieldsValue();
        if (values) {
          if (values.handTime) {
            values.startTime = form.getFieldValue('handTime').format('YYYY-MM-DD')
            values.endTime = form.getFieldValue('handTime').format('YYYY-MM-DD')
            delete values.handTime
          }
        }
        dispatch({
          type: 'lostAndFoundStore/queryPage',
          pageNo: current,
          pageSize,
          ...values,
        })
      },


      handlerUpload(info) {
        if (info.file.status !== 'uploading') {
          console.log('uploading')
        }
        if (info.file.status === 'done') {
          // console.log(`${info.file.name} file uploaded successfully`);
          console.log(info)
          Modal.info({
            name: '导入结果',
            content: (
              info.file.response.msg
            ),
            onOk() {
              dispatch({
                type: 'lostAndFoundStore/queryPage',
              })
            },
          })
        } else if (info.file.status === 'error') {
          console.log('error')
        }
      },

    },
  }
}

const fields = [{
  name: '自编号',
  key: 'carNo',
}, {
  name: '车牌号',
  key: 'plateNumber',
}, {
  name: '姓名',
  key: 'userName',
}, {
  name: '资格证',
  key: 'qualificationNo',
}, {
  name: '物品名称',
  key: 'articleName',
}, {
  name: '物品数量',
  key: 'articleCount',
}, {
  name: '物品金额',
  key: 'articleAmount',
}, {
  name: '上交时间',
  key: 'handTime',
  type: 'date',
}, {
  name: '归还时间',
  key: 'returnTime',
}, {
  name: '失主姓名',
  key: 'lostUserName',
}, {
  name: '失主电话',
  key: 'lostMobile',
}, {
  name: '归还经办人',
  key: 'returnOprator',
}]

export default Form.create()(connect(mapStateToProps, mapDispatchToProps)(index))
