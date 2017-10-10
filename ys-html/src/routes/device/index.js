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
        require.ensure([], require => {
          // if (auth('car:car:*')) {
          if (true) {
            registerModel(app, require('models/device/deviceStore'))
            cb(null, require('pages/device/device'))
          }
        }, 'device')
      },
    },

  ]
}
