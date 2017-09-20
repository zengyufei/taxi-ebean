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
  const { loading, page, methods, form, res, register } = option
  const { onSearch, toDetail, toAdd, toEdit, toExport, onShowSizeChange, onChange, handlerUpload, generate } = methods

  /**
   * 上传文件
   */
  const importCar = {
    name: 'file',
    action: `${BASE_URL}/monthQuota/import.htm?token=${session.get(tokenSessionKey)}`,
    onChange: handlerUpload,
  }

  const btns = (
    <div>
      <ZButton permission="finance:monthQuota:insert">
        <Button type="primary" icon="plus-circle-o" onClick={toAdd}>新增</Button>&nbsp;
      </ZButton>
      <ZButton permission="finance:monthQuota:insert">
        <Button type="primary" icon="plus-circle-o" onClick={() => generate(false)}>生成上月</Button>&nbsp;
      </ZButton>
      <ZButton permission="finance:monthQuota:insert">
        <Button type="primary" icon="plus-circle-o" onClick={() => generate(true)}>生成当月</Button>&nbsp;
      </ZButton>
      <ZButton permission="finance:monthQuota:export">
        <Popconfirm title="是否确定要导出" onConfirm={toExport} >
          <Button type="primary" icon="export" >导出</Button>&nbsp;
        </Popconfirm>
      </ZButton>
      <ZButton permission="finance:monthQuota:import">
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
    searchCacheKey: 'monthQuota_condin',
    searchFields: getSearchFields(fields, ['carNo', 'plateNumber', 'yearMonth']).values(),
    fields: getFields(fields, local.get('monthQuota_condin') || ['carNo', 'plateNumber', 'yearMonth']).values(),
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
          <ZButton permission="finance:monthQuota:query">
            <Button type="primary" onClick={() => toDetail(record)}>详情</Button>&nbsp;
          </ZButton>
          <ZButton permission="finance:monthQuota:update">
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

function mapStateToProps({ loading, monthQuotaStore }) {
  return {
    loading: loading.models.monthQuotaStore,
    register: monthQuotaStore.register,
    res: monthQuotaStore.res,
    page: monthQuotaStore.page,
  }
}


const mapDispatchToProps = (dispatch, { form }) => {
  return {
    form,
    methods: {

      onSearch(values) {
        if (values) {
          if (values.yearMonth) {
            values.yearMonth = moment(new Date(values.yearMonth)).format('YYYYMM')
          }
        }
        dispatch({
          type: 'monthQuotaStore/queryPage',
          ...values,
        })
      },

      /* 详情 */
      toDetail(monthQuota) {
        dispatch({
          type: 'monthQuotaStore/toEdit',
          res: 'detail',
          monthQuota,
        })
      },
      /* 添加 */
      toAdd() {
        dispatch({
          type: 'monthQuotaStore/toRegister',
          res: 'add',
        })
      },
      /* 当月、上月 新增
       currentMonth 区分上月、当月
        */
      generate(currentMonth) {
        dispatch({
          type: 'monthQuotaStore/generate',
          currentMonth,
        })
      },
      /* 编辑 */
      toEdit(monthQuota) {
        dispatch({
          type: 'monthQuotaStore/toEdit',
          res: 'update',
          monthQuota,
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
        window.location.href = `${BASE_URL}/monthQuota/export.htm?token=${session.get(tokenSessionKey)}&${paramsForGet}`
      },


      onShowSizeChange(current, pageSize) { // 当几条一页的值改变后调用函数，current：改变显示条数时当前数据所在页；pageSize:改变后的一页显示条数
        let values = form.getFieldsValue()
        if (values) {
          if (values.yearMonth) {
            values.yearMonth = moment(new Date(values.yearMonth)).format('YYYYMM')
          }
        }
        dispatch({
          type: 'monthQuotaStore/queryPage',
          pageNo: current,
          pageSize,
          ...values,
        })
      },

      onChange(current, pageSize) { // 点击改变页数的选项时调用函数，current:将要跳转的页数
        let values = form.getFieldsValue()
        if (values) {
          if (values.yearMonth) {
            values.yearMonth = moment(new Date(values.yearMonth)).format('YYYYMM')
          }
        }
        dispatch({
          type: 'monthQuotaStore/queryPage',
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
            title: '导入结果',
            content: (
              info.file.response.msg
            ),
            onOk() {
              dispatch({
                type: 'monthQuotaStore/queryPage',
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
  name: '年月',
  key: 'yearMonth',
  type: 'yearMonth',
}, {
  name: '出勤天数',
  key: 'workDays',
}, {
  name: '月末状态',
  key: 'endStatus',
  enums: {
    WORKING: '在职',
    DIMISSION: '离职',
  },
}, {
  name: '应收月缴定额',
  key: 'standardAmount',
}, {
  name: '营运核减总金额',
  key: 'serviceSubAmount',
}, {
  name: '维修核减总金额',
  key: 'repairSubAmount',
}, {
  name: '事故核减总金额',
  key: 'accidentSubAmount',
}, {
  name: '实际月缴定额',
  key: 'realAmount',
}]

export default Form.create()(connect(mapStateToProps, mapDispatchToProps)(index))
