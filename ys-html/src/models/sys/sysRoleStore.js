import { extend } from 'ModelUtils'

const moduleName = '角色'
const prefix = 'sysRole'
const queryPageUrl = `${prefix}/queryPage`
const addUrl = `${prefix}/insert`
const updateUrl = `${prefix}/update`
const deleteByIdUrl = `${prefix}/deleteById`

export default extend({
    namespace: `${prefix}Store`,
    state: {
        defaultExpandAllRows: false,
        visible: {
            add: false,
            update: false,
        },
        page: {},
        [prefix]: {
            resourceList: [],
        },
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

        * add(payload, { postConfirmLoading, put, select, localCache }) {
            const { page: { pageNo, pageSize }, sysRole } = yield select(({ sysRoleStore }) => sysRoleStore)
            const { code, msg } = yield postConfirmLoading(addUrl, { ...payload, resourceList: sysRole.resourceList })
            if (code === 200) {
                ZMsg.success(msg)
                yield [
                    put('reload', { pageNo, pageSize }), // 刷新列表
                    put('hideVisible', { key: 'add' }), // 控制弹窗
                ]
                const roleKey = 'roles'
                localCache.remove(roleKey)
            }
        },

        * update(payload, { postConfirmLoading, put, diff, select, localCache }) {
            const { sysRole, page: { pageNo, pageSize } } = yield select(({ sysRoleStore }) => sysRoleStore)
            const newSysRole = { ...sysRole, ...payload }
            if (diff(sysRole, newSysRole)) {
                const { code, msg } = yield postConfirmLoading(updateUrl, newSysRole)
                if (code === 200) {
                    ZMsg.success(msg)
                    yield [
                        put('reload', { pageNo, pageSize }), // 刷新列表
                        put('hideVisible', { key: 'update' }), // 控制弹窗
                    ]
                    const roleKey = 'roles'
                    localCache.remove(roleKey)
                }
            }
        },

        * removeById({ id }, { get, put, select }) {
            const { pageNo, pageSize } = yield select(({ sysRoleStore }) => sysRoleStore.page)
            yield get(deleteByIdUrl, { id: +id })
            yield put('reload', { pageNo, pageSize }) // 刷新列表
        },
    },
    reducers: {
        updatePermission(state, { resourceList }) {
            let { sysRole } = state
            sysRole.resourceList = resourceList.join(',')
            return { ...state, sysRole }
        },
    },
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
