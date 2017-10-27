const registerModel = (app, model) => {
    if (!(app._models.filter(m => m.namespace === model.namespace).length === 1)) {
        app.model(model)
    }
}

module.exports = (app, auth) => {
    return [
        {
            path: 'device',
            getComponent(nextState, cb) {
                require.ensure(
                    [],
                    require => {
                        // if (auth('car:car:*')) {
                        if (true) {
                            registerModel(app, require('models/device/deviceStore'))
                            cb(null, require('pages/device/device'))
                        }
                    },
                    'device',
                )
            },
        },

        {
            path: 'openDoorLog',
            getComponent(nextState, cb) {
                require.ensure(
                    [],
                    require => {
                        // if (auth('car:car:*')) {
                        if (true) {
                            registerModel(app, require('models/device/openDoorLogStore'))
                            cb(null, require('pages/device/openDoorLog'))
                        }
                    },
                    'openDoorLog',
                )
            },
        },

        {
            path: 'catchPhoto',
            getComponent(nextState, cb) {
                require.ensure(
                    [],
                    require => {
                        // if (auth('car:car:*')) {
                        if (true) {
                            registerModel(app, require('models/device/catchPhotoStore'))
                            cb(null, require('pages/device/catchPhoto'))
                        }
                    },
                    'catchPhoto',
                )
            },
        },

        {
            path: 'card',
            getComponent(nextState, cb) {
                require.ensure(
                    [],
                    require => {
                        // if (auth('car:car:*')) {
                        if (true) {
                            registerModel(app, require('models/device/cardStore'))
                            cb(null, require('pages/device/card'))
                        }
                    },
                    'card',
                )
            },
        },
    ]
}
