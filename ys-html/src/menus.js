import _ from 'lodash'

const config = {

  // 菜单按钮
  menus: [

    /**
     * 系统管理
     * @module 用户管理
     * @module 角色管理
     */
    {
      id: _.uniqueId(),
      name: '系统管理',
      icon: 'mail',
      permission: 'sys:*',
      children: [
        {
          id: _.uniqueId(),
          name: '组织机构管理',
          url: '/sysOrg',
          permission: 'sys:sysOrg:*',
        }, {
          id: _.uniqueId(),
          name: '用户管理',
          url: '/sysMember',
          permission: 'sys:sysMember:*',
        }, {
          id: _.uniqueId(),
          name: '角色管理',
          url: '/sysRole',
          permission: 'sys:sysRole:*',
        }, {
          id: _.uniqueId(),
          name: '资源管理',
          url: '/sysResource',
          permission: 'sys:sysResource:*',
        },
      ],
    },


    /**
     * 小区信息
     */
    {
      id: _.uniqueId(),
      name: '小区管理',
      icon: 'mail',
      permission: 'community:*',
      children: [
        {
          id: _.uniqueId(),
          name: '小区信息管理',
          url: '/community',
          permission: 'community:community:*',
        }, {
          id: _.uniqueId(),
          name: '楼栋信息管理',
          url: '/building',
          permission: 'community:building:*',
        }, {
          id: _.uniqueId(),
          name: '单元信息管理',
          url: '/unit',
          permission: 'community:unit:*',
        }, {
          id: _.uniqueId(),
          name: '房间信息管理',
          url: '/room',
          permission: 'community:room:*',
        },
      ],
    },


    /**
     * 设备信息
     */
    {
      id: _.uniqueId(),
      name: '设备管理',
      icon: 'mail',
      permission: 'device:*',
      children: [
        {
          id: _.uniqueId(),
          name: '设备信息',
          url: '/device',
          permission: 'device:device:*',
        },
      ],
    },

  ],

}
export default config
