import { extend } from 'ModelUtils'
import { Select } from 'antd'

const Option = Select.Option
const prefix = 'openDoorLog'
const storeName = `${prefix}Store`
const name = '开门记录'
const queryPageUrl = `${prefix}/queryPage`
const getAllCommunity = `/community/queryAll`

const queryBuildingByCommunityIdUrl = `/building/queryByCommunityId`
const queryUnitByBuildingIdUrl = `/unit/queryByBuildingId`
const queryRoomByUnitIdUrl = `/room/queryByUnitId`

export default extend({
    namespace: storeName,
    state: {
        page: {},
        [prefix]: {},
    },
    effects: {
        * init({}, {}) {
            //
        },

        * queryPage(payload, { getMessage, update }) {
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
                                key="unitSelectForm"
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

                roomId: ({ initialValue, meta, field, inputProps, placeholder, isText, currentForm }) => {
                    const isOpten = !!(currentForm && currentForm.getFieldValue('unitId'))
                    let options = []
                    if (isOpten) {
                        let allRoom = syncGet(queryRoomByUnitIdUrl, { id: currentForm.getFieldValue('unitId') })
                        options = allRoom.result.map(item => (
                            <Option key={item.id} value={`${item.id}`}>
                                {item.name}
                            </Option>
                        ))
                    }
                    let finalResult = {
                        input: (
                            <Select
                                disabled={!isOpten}
                                key="roomSelectForm"
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
