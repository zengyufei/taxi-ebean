import { Breadcrumb, Icon } from 'antd'
import { Link } from 'dva/router'
import { flatten } from 'utils/treeFlatten'
import _ from 'lodash'
import { menus } from 'src/menus'
import styles from './Bread.less'

const Bread = option => {
    const { location } = option
    let pathArray = []

    const menuArray = flatten(
        _.cloneDeep(menus),
        item => item.children,
        (item, parent) => {
            item.parent = parent
            delete item.children
            return item
        },
    )
    const menuMap = _.mapKeys(menuArray, item => item.url)

    const current = menuMap[location.pathname]
    if (!current) {
        pathArray.push({
            id: 'home',
            icon: 'laptop',
            name: projectConfig.breadName,
        })
    } else {
        // 递归查找父级
        const getPathArray = obj => {
            let temp = []
            const getPath = item => {
                if (item.parent) {
                    temp.unshift(item)
                    getPath(item.parent)
                } else {
                    temp.unshift(item)
                }
            }
            getPath(obj)
            return temp
        }
        pathArray = getPathArray(menuMap[location.pathname])
    }

    // 递归查找父级
    const breads = pathArray.map((item, key) => {
        const content = (
            <span>
                {item.icon ? <Icon type={item.icon} style={{ marginRight: 4 }}/> : ''}
                {item.name}
            </span>
        )
        return (
            <Breadcrumb.Item key={key}>
                {pathArray.length - 1 !== key ? content : <Link to={item.url}> {content}</Link>}
            </Breadcrumb.Item>
        )
    })

    return (
        <div className={styles.bread}>
            <Breadcrumb>
                <Breadcrumb.Item key="home" style={{ fontWeight: 800 }}>
                    <Link to="/" style={{ color: '#000' }}>{projectConfig.name}</Link>
                </Breadcrumb.Item>
                {breads}
            </Breadcrumb>
        </div>
    )
}

export default Bread
