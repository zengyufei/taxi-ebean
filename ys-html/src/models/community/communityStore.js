import { extend } from 'ModelUtils'

const prefix = 'community'
const storeName = `${prefix}Store`
const queryPageUrl = `${prefix}/queryPage`
const addUrl = `${prefix}/insert`

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
        * init({}, { tableBindType, formBindType }) {
            yield tableBindType({})
            yield formBindType({})
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

        * insert(payload, { postConfirmLoading, put, select }) {
            const { pageNo, pageSize } = yield select(state => state[storeName].page)
            const { code = 0, msg = '' } = yield postConfirmLoading(addUrl, payload)
            if (code === 200) {
                ZMsg.success(msg)
                yield [
                    put('reload', { pageNo, pageSize }), // 刷新列表
                    put('hideVisible', { key: 'add' }), // 控制弹窗
                    put('queryPage'),
                ]
            }
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
