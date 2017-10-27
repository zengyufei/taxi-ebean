const registerModel = (app, model) => {
    if (!(app._models.filter(m => m.namespace === model.namespace).length === 1)) {
        app.model(model)
    }
}

module.exports = (app, auth) => {
    return [
        {
            path: 'userInfo',
            getComponent(nextState, cb) {
                require.ensure(
                    [],
                    require => {
                        // if (auth('car:car:*')) {
                        if (true) {
                            registerModel(app, require('models/house/userInfoStore'))
                            cb(null, require('pages/house/userInfo'))
                        }
                    },
                    'userInfo',
                )
            },
        },

        {
            path: 'repair',
            getComponent(nextState, cb) {
                require.ensure(
                    [],
                    require => {
                        // if (auth('car:car:*')) {
                        if (true) {
                            registerModel(app, require('models/house/repairStore'))
                            cb(null, require('pages/house/repair'))
                        }
                    },
                    'repair',
                )
            },
        },

        {
            path: 'complain',
            getComponent(nextState, cb) {
                require.ensure(
                    [],
                    require => {
                        // if (auth('car:car:*')) {
                        if (true) {
                            registerModel(app, require('models/house/complainStore'))
                            cb(null, require('pages/house/complain'))
                        }
                    },
                    'complain',
                )
            },
        },
    ]
}
