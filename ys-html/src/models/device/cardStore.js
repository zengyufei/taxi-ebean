import { extend } from 'ModelUtils'
import { Select, AutoComplete } from 'antd'

const Option = Select.Option
const prefix = 'card'
const storeName = `${prefix}Store`
const name = '物理卡'
const queryPageUrl = `${prefix}/queryPage`
const queryByPhoneUrl = `/user/queryByPhone`
const addUrl = `${prefix}/insert`
const updateUrl = `${prefix}/update`
const deleteByIdUrl = `${prefix}/removeById`
const getAllCommunity = `/community/queryAll`

const queryBuildingByCommunityIdUrl = `/building/queryByCommunityId`
const queryUnitByBuildingIdUrl = `/unit/queryByBuildingId`

export default extend({
    namespace: storeName,
    state: {
        visible: {
            add: false,
            update: false,
            updatePermission: false,
        },
        page: {},
        [prefix]: {},
        userInfoList: [],
    },
    effects: {
        * init({}, { formBindType, select }) {
            yield formBindType({
                auto: ({ showPlaceholder, field, initialValue }) => {
                    return {
                        input: (
                            <AutoComplete
                                optionLabelProp="value"
                                dataSource={initialValue}
                                style={{ width: 200 }}
                                onChange={field.onSelect}
                                placeholder={showPlaceholder}
                            />
                        ),
                    }
                },
            })
        },

        * queryPage(payload, { getMessage, update }) {
            const { result } = yield getMessage(queryPageUrl, payload)
            yield update({ page: result })
        },

        * queryByPhone(payload, { getMessage, update }) {
            const { result } = yield getMessage(queryByPhoneUrl, payload)
            yield update({ userInfoList: result })
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
                ]
            }
        },

        * update(payload, { postConfirmLoading, put, diff, select }) {
            const { pageNo, pageSize } = yield select(state => state[storeName].page)
            const oldObj = yield select(state => state[storeName][prefix])
            const newObj = { ...oldObj, ...payload }
            if (diff(oldObj, newObj)) {
                const { code, msg } = yield postConfirmLoading(updateUrl, newObj)
                if (code === 200) {
                    ZMsg.success(msg)
                    yield [
                        put('reload', { pageNo, pageSize }), // 刷新列表
                        put('hideVisible', { key: 'update' }), // 控制弹窗
                    ]
                }
            }
        },

        * updatePermission({}, { postConfirmLoading, put, select }) {
            const permissions = yield select(state => state[storeName].permissions)
            if (!!permissions) {
                const oldObj = yield select(state => state[storeName][prefix])
                const { pageNo, pageSize } = yield select(state => state[storeName].page)
                oldObj.permissions = permissions
                const { code, msg } = yield postConfirmLoading(updateUrl, oldObj)
                if (code === 200) {
                    ZMsg.success(msg)
                    yield [
                        put('reload', { pageNo, pageSize }), // 刷新列表
                        put('hideVisible', { key: 'updatePermission' }), // 控制弹窗
                    ]
                }
            }
        },

        * removeById({ id }, { get, put, select }) {
            const { pageNo, pageSize } = yield select(state => state[storeName].page)
            yield get(deleteByIdUrl, { id: +id })
            yield put('reload', { pageNo, pageSize }) // 刷新列表
        },

        * bind({}, { get, formBindType, syncGet }) {
            const allCommunity = yield get(getAllCommunity)
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

                buildingId: ({ initialValue, meta, field, inputProps, placeholder, isText, currentForm }) => {
                    const isOpten = !!(currentForm && currentForm.getFieldValue('communityId'))
                    let options = []
                    if (isOpten) {
                        let allBuilding = syncGet(queryBuildingByCommunityIdUrl, {
                            id: currentForm.getFieldValue('communityId'),
                        })
                        options = allBuilding.result.map(item => (
                            <Option key={item.id} value={`${item.id}`}>
                                {item.name}
                            </Option>
                        ))
                    }
                    let finalResult = {
                        input: (
                            <Select
                                disabled={!isOpten}
                                key="communitySelectForm"
                                allowClear
                                placeholder={field.placeholder || '请选择楼栋'}
                                showSearch
                                dropdownStyle={{ maxHeight: 400, overflow: 'auto' }}
                                treeDefaultExpandAll
                            >
                                {options}
                            </Select>
                        ),
                    }
                    initialValue && (finalResult.initialValue = `${initialValue}`)
                    return finalResult
                },

                unitId: ({ initialValue, meta, field, inputProps, placeholder, isText, currentForm }) => {
                    const isOpten = !!(currentForm && currentForm.getFieldValue('buildingId'))
                    let options = []
                    if (isOpten) {
                        let allUnit = syncGet(queryUnitByBuildingIdUrl, { id: currentForm.getFieldValue('buildingId') })
                        options = allUnit.result.map(item => (
                            <Option key={item.id} value={`${item.id}`}>
                                {item.name}
                            </Option>
                        ))
                    }
                    let finalResult = {
                        input: (
                            <Select
                                disabled={!isOpten}
                                key="buildingSelectForm"
                                allowClear
                                placeholder={field.placeholder || '请选择楼栋'}
                                showSearch
                                dropdownStyle={{ maxHeight: 400, overflow: 'auto' }}
                                treeDefaultExpandAll
                            >
                                {options}
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
