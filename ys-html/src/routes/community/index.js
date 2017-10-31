const registerModel = (app, model) => {
    if (!(app._models.filter(m => m.namespace === model.namespace).length === 1)) {
        app.model(model)
    }
}

module.exports = (app, auth) => {
    return [
        {
            path: 'community',
            getComponent(nextState, cb) {
                require.ensure(
                    [],
                    require => {
                        // if (auth('car:car:*')) {
                        if (true) {
                            registerModel(app, require('models/community/communityStore'))
                            cb(null, require('pages/community/community'))
                        }
                    },
                    'community',
                )
            },
        },

        {
            path: 'building',
            getComponent(nextState, cb) {
                require.ensure(
                    [],
                    require => {
                        // if (auth('car:car:*')) {
                        if (true) {
                            registerModel(app, require('models/community/buildingStore'))
                            cb(null, require('pages/community/building'))
                        }
                    },
                    'building',
                )
            },
        },

        {
            path: 'unit',
            getComponent(nextState, cb) {
                require.ensure(
                    [],
                    require => {
                        // if (auth('car:car:*')) {
                        if (true) {
                            registerModel(app, require('models/community/unitStore'))
                            cb(null, require('pages/community/unit'))
                        }
                    },
                    'unit',
                )
            },
        },

        {
            path: 'room',
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
        },
    ]
}
