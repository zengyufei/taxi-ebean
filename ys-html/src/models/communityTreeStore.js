import { extend } from 'ModelUtils'
import { Cascader } from 'antd'

const prefix = 'communityTree'
// 当前小区所有楼栋单元
const queryDefaultCommunityId = '/community/queryDefaultCommunityId'

const queryAllCommunityUrl = '/community/queryAll'
const queryBuildingByCommunityIdUrl = '/building/queryByCommunityId'
const queryUnitByCommunityIdUrl = '/unit/queryByCommunityId'
const queryRoomByCommunityIdUrl = '/room/queryByCommunityId'

export default extend({
    namespace: `${prefix}Store`,
    state: {
        isSingle: true,
        defaultCommunity: {},
        defaultCommunityId: 0,
        defaultBuildingIds: [],
        defaultUnitIds: [],
        defaultRoomIds: [],

        allCommunity: [],
        allBuilding: [],
        allUnit: [],
        allRoom: [],
    },
    effects: {
        * queryAll({}, { get, update, select }) {
            const defaultCommunityId = yield select(({ communityTreeStore }) => communityTreeStore.defaultCommunityId)
            if (defaultCommunityId > 0) {
                {
                    const { result } = yield get(queryAllCommunityUrl)
                    yield update({ allCommunity: result })
                    if(result.length === 1) {
                        yield update({ defaultCommunity: result[0] })
                    }
                }
                {
                    const { result } = yield get(queryBuildingByCommunityIdUrl, { id: defaultCommunityId })
                    yield update({ allBuilding: result })
                }
                {
                    const { result } = yield get(queryUnitByCommunityIdUrl, { id: defaultCommunityId })
                    yield update({ allUnit: result })
                }
                {
                    const { result } = yield get(queryRoomByCommunityIdUrl, { id: defaultCommunityId })
                    yield update({ allRoom: result })
                }
            } else {
                const { result } = yield get(queryDefaultCommunityId)
                if (result > 0) {
                    yield update({ defaultCommunityId: result })
                    const queryAll = yield get(queryAllCommunityUrl)
                    yield update({ allCommunity: queryAll.result })
                    if(queryAll.result.length === 1) {
                        yield update({ defaultCommunity: queryAll.result[0] })
                    }
                    const queryByCommunityId = yield get(queryBuildingByCommunityIdUrl, { id: result })
                    yield update({ allBuilding: queryByCommunityId.result })
                    const queryAllUnit = yield get(queryUnitByCommunityIdUrl, { id: result })
                    yield update({ allUnit: queryAllUnit.result })
                    const queryAllRoom = yield get(queryRoomByCommunityIdUrl, { id: result })
                    yield update({ allRoom: queryAllRoom.result })
                }
            }
        },

        * queryDefaultCommunityId({}, { get, update, select }) {
            const defaultCommunityId = yield select(({ communityTreeStore }) => communityTreeStore.defaultCommunityId)
            if (defaultCommunityId <= 0) {
                const { result } = yield get(queryDefaultCommunityId)
                if (result > 0) {
                    yield update({ defaultCommunityId: result })
                }
            }
        },
    },
    reducers: {},
    subscriptions: {
        setup({ dispatch, listen }) {
            // 支持对多个path的监听
            listen({
                '/community': () => {
                    dispatch({
                        type: 'queryAll',
                    })
                },
                '/building': () => {
                    dispatch({
                        type: 'queryAll',
                    })
                },
                '/unit': () => {
                    dispatch({
                        type: 'queryAll',
                    })
                },
                '/room': () => {
                    dispatch({
                        type: 'queryAll',
                    })
                },
                '/device': () => {
                    dispatch({
                        type: 'queryAll',
                    })
                },
                '/openDoorLog': () => {
                    dispatch({
                        type: 'queryAll',
                    })
                },
                '/catchPhoto': () => {
                    dispatch({
                        type: 'queryAll',
                    })
                },
                '/card': () => {
                    dispatch({
                        type: 'queryAll',
                    })
                },
                '/repair': () => {
                    dispatch({
                        type: 'queryAll',
                    })
                },
                '/userInfo': () => {
                    dispatch({
                        type: 'queryAll',
                    })
                },
                '/complain': () => {
                    dispatch({
                        type: 'queryAll',
                    })
                },
            })
        },
    },
})
