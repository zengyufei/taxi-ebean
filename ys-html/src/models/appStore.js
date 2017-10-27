import { routerRedux } from 'dva/router'
import { extend } from 'ModelUtils'
import { session } from 'utils/storage'

const prefix = 'app'

const { roleSessionKey, resourceSessionKey } = constant

const getRoleUrl = '/sysRole/getRole'
const getResourceUrl = '/sysResource/getResource'

export default extend({
    namespace: `${prefix}Store`,
    state: {
        activeModel: '',
        menuPopoverVisible: false,
        siderFold: false,
        isSmall: document.body.clientWidth < 769,
        openKeys: local.get('openKeys') || [],

        sysMember: {},
        currentPermission: session.get(resourceSessionKey) || {},
        sysResource: {},
    },
    effects: {
        * loadRole({}, { getMessage, put, sessionCache }) {
            let currentRole = session.get(roleSessionKey)
            if (!currentRole) {
                const { result } = yield getMessage(getRoleUrl)
                if (result) {
                    sessionCache.set(roleSessionKey, (currentRole = result))
                }
            }
            yield [put('loadResource')]
        },

        * loadResource({}, { getMessage, put, update, sessionCache }) {
            let currentPermission = session.get(resourceSessionKey)
            if (!currentPermission) {
                const { result } = yield getMessage(getResourceUrl)
                if (result) {
                    sessionCache.set(resourceSessionKey, (currentPermission = result))
                }
            }
            yield update({ currentPermission })
            yield put(routerRedux.push('/#/'))
        },

        * logout({}, { put, sessionCache }) {
            sessionCache.removeAll()
            yield put(routerRedux.push('/#/login'))
        },

        * resetPassword(payload, { postMessage, update }) {
            const { code } = yield postMessage('/sysMember/resetPassword', payload, {
                successMsg: '修改密码成功',
                errorMsg: '修改密码失败',
            })
            if (code === 200) {
                yield update({ visible: false })
            }
        },
    },
    reducers: {
        changeActiveModel(state, { activeModel }) {
            return { ...state, activeModel }
        },

        switchSider(state) {
            return { ...state, siderFold: !state.siderFold }
        },
        changeNavbar(state) {
            return { ...state, isSmall: document.body.clientWidth < 769 }
        },
        switchMenuPopver(state) {
            return { ...state, menuPopoverVisible: !state.menuPopoverVisible }
        },
        changeOpenKeys(state, { openKeys }) {
            return { ...state, openKeys }
        },
    },
    subscriptions: {
        setup({ dispatch }) {
            window.onresize = function () {
                dispatch({ type: 'changeNavbar' })
            }
        },
    },
})
