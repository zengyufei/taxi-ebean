import { menus } from 'src/menus'
import Menus from './Menu'

const Sider = options => {
  const { openKeys, isDarkTheme = projectConfig.theme, siderFold, location, changeOpenKeys } = options

  const menusProps = {
    siderFold,
    menus,
    isDarkTheme,
    location,
    openKeys,
    changeOpenKeys,
  }

  return (
    <div>
      <div style={isDarkTheme ? {} : { backgroundColor: '#fff' }}>
        <img alt={'logo'} src={projectConfig.logo} width="214px" />
      </div>
      <Menus {...menusProps} />
    </div>
  )
}

export default Sider
