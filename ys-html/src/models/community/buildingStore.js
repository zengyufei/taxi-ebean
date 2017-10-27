import { extend } from 'ModelUtils'
import { Select } from 'antd'

const Option = Select.Option
const prefix = 'building'
const storeName = `${prefix}Store`
const name = '楼栋'
const queryPageUrl = `${prefix}/queryPage`
const addUrl = `${prefix}/insert`
const updateUrl = `${prefix}/update`
const deleteByIdUrl = `${prefix}/removeById`
const getAllCommunity = `community/queryAll`

export default extend({
    namespace: storeName,
    state: {
        visible: {
            add: false,
            update: false,
        },
        page: {},
        [prefix]: {},
    },
    effects: {
        * init({}, {}) {
            //
        },

        * queryPage(payload, { get, getMessage, update }) {
            const { result } = yield getMessage(queryPageUrl, payload)
            yield update({ page: result })
        },

        /*
         * 不提示刷新分页，增删改操作使用
         */
        * reload(payload, { get, update }) {
            const { result } = yield get(queryPageUrl, payload)
            yield update({ page: result })
        },

        * insert(payload, { postConfirmLoading, put, select }) {
            const { pageNo, pageSize } = yield select(state => state[storeName].page)
            const { code = 0, msg = '' } = yield postConfirmLoading(addUrl, payload)
            if (code === 200) {
                ZMsg.success(msg)
                yield [
                    put('reload', { pageNo, pageSize }), // 刷新列表
                    put('hideVisible', { key: 'add' }), // 控制弹窗
                    put('communityTreeStore/queryAll'),
                ]
            }
        },

        * update(payload, { postConfirmLoading, put, select }) {
            const { pageNo, pageSize } = yield select(state => state[storeName].page)
            if (payload.diff) {
                const { code, msg } = yield postConfirmLoading(updateUrl, payload)
                if (code === 200) {
                    ZMsg.success(msg)
                    yield [
                        put('reload', { pageNo, pageSize }), // 刷新列表
                        put('hideVisible', { key: 'update' }), // 控制弹窗
                        put('communityTreeStore/queryAll'),
                    ]
                }
            }
        },

        * bind({}, { get, formBindType }) {
            let allCommunity = yield get(getAllCommunity)
            yield formBindType({
                communityId: ({ initialValue, field }) => {
                    let finalResult = {
                        input: (
                            <Select
                                key="communitySelectForm"
                                allowClear
                                placeholder={field.placeholder || '请选择小区'}
                                showSearch
                                dropdownStyle={{ maxHeight: 400, overflow: 'auto' }}
                                treeDefaultExpandAll
                            >
                                {allCommunity.result.map(item => (
                                    <Option key={item.id} value={`${item.id}`}>
                                        {item.name}
                                    </Option>
                                ))}
                            </Select>
                        ),
                    }
                    initialValue && (finalResult.initialValue = `${initialValue}`)
                    return finalResult
                },
            })
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
