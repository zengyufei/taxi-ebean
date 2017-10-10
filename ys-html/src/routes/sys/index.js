const registerModel = (app, model) => {
  if (!(app._models.filter(m => m.namespace === model.namespace).length === 1)) {
    app.model(model)
  }
}

module.exports = (app, auth) => {
  return [
    {
      path: 'sysOrg',
      getComponent(nextState, cb) {
        require.ensure([], require => {
          if (auth('sys:sysOrg:*')) {
            registerModel(app, require('models/sysStore'))
            registerModel(app, require('models/sys/sysOrgStore'))
            cb(null, require('pages/sys/sysOrg'))
          }
        }, 'sysOrg')
      },
    },
    {
      path: 'sysMember',
      getComponent(nextState, cb) {
        require.ensure([], require => {
          if (auth('sys:sysMember:*')) {
            registerModel(app, require('models/sysStore'))
            registerModel(app, require('models/sys/sysMemberStore'))
            cb(null, require('pages/sys/sysMember'))
          }
        }, 'sysMember')
      },
    },
    {
      path: 'sysRole',
      getComponent(nextState, cb) {
        require.ensure([], require => {
          if (auth('sys:sysRole:*')) {
            registerModel(app, require('models/sysStore'))
            registerModel(app, require('models/sys/sysRoleStore'))
            cb(null, require('pages/sys/sysRole'))
          }
        }, 'sysRole')
      },
    },
    {
      path: 'sysResource',
      onEnter() {
        auth('sys:sysResource:*')
      },
      getComponent(nextState, cb) {
        require.ensure([], require => {
          if (auth('sys:sysRole:*')) {
            registerModel(app, require('models/sysStore'))
            registerModel(app, require('models/sys/sysResourceStore'))
            cb(null, require('pages/sys/sysResource'))
          }
        }, 'sysResource')
      },
    },
  ]
}
