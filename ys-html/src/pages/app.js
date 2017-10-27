/*
 * @Author: zengyufei
 * @Date: 2017-08-04 14:16:53
 * @Last Modified by: zengyufei
 * @Last Modified time: 2017-10-24 09:21:03
 */
import { connect } from 'dva'
import { Sider, Header, Footer, Bread, styles } from 'components/layout'
import { Helmet } from 'react-helmet'
import classnames from 'classnames'
import CommunityTree from './tree'
import '../themes/index.less'
import './app.less'

const App = option => {
    const { location, children, methods, appStore, communityTreeStore } = option
    const { allCommunity } = communityTreeStore
    const { siderFold, isSmall, menuPopoverVisible, openKeys } = appStore

    const { changeOpenKeys, logout, switchMenuPopover, switchSider } = methods

    const isSysModule = /^\/sys|^\/$/.test(location.pathname)

    const headerProps = {
        siderFold,
        openKeys,
        isSmall,
        menuPopoverVisible,
        logout,
        switchMenuPopover,
        switchSider,
        changeOpenKeys,
    }

    const siderProps = {
        siderFold,
        location,
        openKeys,
        changeOpenKeys,
    }

    const breadProps = {
        location,
    }

    return (
        <div>
            <Helmet>
                <title>{projectConfig.name}</title>
                <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
                <link rel="icon" href={projectConfig.logo} type="image/x-icon"/>
            </Helmet>
            <div
                className={classnames(
                    styles.layout,
                    { [styles.fold]: isSmall ? false : siderFold },
                    { [styles.withnavbar]: isSmall },
                )}
            >
                {!isSmall ? (
                    <aside className={classnames(styles.sider, { [styles.light]: true })}>
                        <Sider {...siderProps} />
                    </aside>
                ) : (
                    ''
                )}
                <div className={styles.main}>
                    <Header {...headerProps} />
                    <Bread {...breadProps} />
                    <div className={styles.container}>
                        {!isSysModule &&
                        !!allCommunity.length && (
                            <div>
                                <CommunityTree/>
                            </div>
                        )}
                        <div className={styles.content}>
                            <div style={{ width: 1475, backgroundColor: '#fff' }}>{children}</div>
                        </div>
                    </div>
                    <div className={styles.footer}>
                        <Footer/>
                    </div>
                </div>
            </div>
        </div>
    )
}

const mapStateToProps = ({ appStore, communityTreeStore }) => ({
    appStore,
    communityTreeStore,
})

const mapDispatchToProps = dispatch => {
    return {
        methods: {
            changeOpenKeys(openKeys) {
                local.set(`openKeys`, openKeys)
                dispatch({
                    type: 'appStore/changeOpenKeys',
                    openKeys,
                })
            },

            logout() {
                dispatch({ type: 'appStore/logout' })
            },

            switchMenuPopover() {
                dispatch({ type: 'appStore/switchMenuPopver' })
            },
            switchSider() {
                dispatch({ type: 'appStore/switchSider' })
            },
        },
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(App)
