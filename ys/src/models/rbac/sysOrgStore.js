import { Select } from 'antd'
import { extend } from 'ModelUtils'

const Option = Select.Option
const moduleName = '组织机构'
const prefix = 'sysOrg'
const queryPageUrl = `${prefix}/queryPage`
const addUrl = `${prefix}/insert`
const updateUrl = `${prefix}/updateNotNull`
const deleteByIdUrl = `${prefix}/deleteById`

export default extend({
  namespace: `${prefix}Store`,
  state: {
    visible: {
      add: false,
      update: false,
    },
    page: {},
    [prefix]: {},
  },
  effects: {

    * init({}, { tableBindType, formBindType }) {
      yield tableBindType({
        initPwd: text => {
          return +text === 0 ? '正常' : '未初始化'
        },
      })
      yield formBindType({
        invalid: ({ showPlaceholder }) => {
          return {
            input: (
              <Select key="invalid" allowClear placeholder={showPlaceholder || '请选择'}>
                <Option key="invalid0" value="true">有效</Option>
                <Option key="invalid1" value="false">无效</Option>
              </Select>
            ),
          }
        },
      })
    },

    * queryPage(payload, { getMessage, update }) {
      const { result } = yield getMessage(queryPageUrl, payload)
      yield update({ page: result })
    },

    /**
     * 不提示刷新分页，增删改操作使用
     */
    * reload(payload, { get, update }) {
      const { result } = yield get(queryPageUrl, payload)
      yield update({ page: result })
    },

    * add(payload, { postConfirmLoading, put, select }) {
      const { pageNo, pageSize } = yield select(({ sysOrgStore }) => sysOrgStore.page)
      const { code = 0, msg = '' } = yield postConfirmLoading(addUrl, payload)
      if (code === 200) {
        ZMsg.success(msg)
        yield [
          put('reload', { pageNo, pageSize }), // 刷新列表
          put('hideVisible', { key: 'add' }), // 控制弹窗
        ]
      }
    },

    * update(payload, { postConfirmLoading, put, diff, select }) {
      const { sysOrg = {}, page: { pageNo, pageSize } } = yield select(({ sysOrgStore }) => sysOrgStore)
      const newSysOrg = { ...sysOrg, ...payload }
      if (diff(sysOrg, newSysOrg)) {
        const { code, msg } = yield postConfirmLoading(updateUrl, newSysOrg)
        if (code === 200) {
          ZMsg.success(msg)
          yield [
            put('reload', { pageNo, pageSize }), // 刷新列表
            put('hideVisible', { key: 'update' }), // 控制弹窗
          ]
        }
      }
    },

    * deleteById({ id }, { get, put, select }) {
      const { pageNo, pageSize } = yield select(({ sysOrgStore }) => sysOrgStore.page)
      yield get(deleteByIdUrl, { id: +id })
      yield put('reload', { pageNo, pageSize }) // 刷新列表
    },

  },
  reducers: {},
  subscriptions: {
    setup({ dispatch, listen }) {
      listen(`/${prefix}`, () => {
        dispatch({
          type: 'queryPage',
        })
      })
    },
  },
})

