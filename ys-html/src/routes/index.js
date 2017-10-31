import React from 'react'
import PropTypes from 'prop-types'
import { Router } from 'dva/router'
import App from 'pages/app'
import { authenticated, isLoginAndRedirectLoginPage } from 'utils/auth'

const registerModel = (app, model) => {
    if (!(app._models.filter(m => m.namespace === model.namespace).length === 1)) {
        app.model(model)
    }
}

const Routers = function ({ history, app }) {
    const routes = [
        {
            path: '/',
            component: App,
            /*indexRoute: {
                getComponent(nextState, cb) {
                    require.ensure(
                    [],
                    require => {
                        // if (auth('car:car:*')) {
                        if (true) {
                            registerModel(app, require('models/community/roomStore'))
                            cb(null, require('pages/community/room'))
                        }
                    },
                    'room',
                )
                },
            },*/
            /*getIndexRoute(nextState, cb) {
                /!*require.ensure(
                    [],
                    require => {
                        if (isLoginAndRedirectLoginPage()) {
                            //registerModel(app, require('models/homeStore'))
                            //cb(null, { component: require('pages/home') })
                        }
                    },
                    'home',
                )*!/
                require.ensure(
                    [],
                    require => {
                        // if (auth('car:car:*')) {
                        if (true) {
                            registerModel(app, require('models/community/roomStore'))
                            cb(null, require('pages/community/room'))
                        }
                    },
                    'room',
                )
            },*/
            onEnter(_, replaceState) {
                // 一进来变更 活动的 namespace
                app._store.dispatch({
                    type: 'appStore/changeActiveModel',
                    activeModel: `${_.location.pathname.replace('/', '')}Store`,
                })

                if (isLoginAndRedirectLoginPage()) {
                    if (_.location.pathname === '/') {
                        return replaceState('/room')
                    }
                }

            },
            onChange(prevState, nextState) {
                // 监听变更 活动的 namespace
                app._store.dispatch({
                    type: 'appStore/changeActiveModel',
                    activeModel: `${nextState.location.pathname.replace('/', '')}Store`,
                })
                prevState.location.pathname !== nextState.location.pathname &&
                app._store.dispatch({
                    type: 'communityTreeStore/updateState',
                    defaultBuildingIds: [],
                    defaultUnitIds: [],
                    defaultRoomIds: [],
                })
            },
            /* childRoutes: (r => {
        return r.keys().map(key => r(key));
    })(require.context('./', true, /^\.\/modules\/((?!\/)[\s\S])+\/route\.js$/)) */
            childRoutes: [
                ...require('./sys')(app, authenticated),
                ...require('./community')(app, authenticated),
                ...require('./device')(app, authenticated),
                ...require('./house')(app, authenticated),
            ],
        },
        require('./login')(app),
        require('./403'),
        require('./404'),
    ]

    return <Router history={history} routes={routes}/>
}

Routers.propTypes = {
    history: PropTypes.object,
    app: PropTypes.object,
}

export default Routers
