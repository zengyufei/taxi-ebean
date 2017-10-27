import { connect } from 'dva'
import { Tree, Select, Switch } from 'antd'

import styles from './tree.less'

const TreeNode = Tree.TreeNode
const Option = Select.Option

const aliasKey = 'aliasFlag'

class Demo extends React.Component {
    state = {
        key: new Date(),
        aliasFlag: local.get(aliasKey),
        selectProps: {},
    }

    componentWillReceiveProps() {
        const allCommunity = this.props.communityTreeStore.allCommunity
        if (allCommunity.length > 0) {
            this.setState({
                selectProps: {
                    defaultValue: !!allCommunity.length && allCommunity[0].id,
                },
            })
        }
    }

    changeNameDisplay(aliasFlag) {
        this.setState({ aliasFlag })
        local.set(aliasKey, aliasFlag)
    }

    render() {
        const activeModel = this.props.appStore.activeModel
        const {
            isSingle,
            defaultCommunityId,
            defaultBuildingIds,
            defaultUnitIds,
            defaultRoomIds,
            allCommunity,
            allBuilding,
            allUnit,
            allRoom,
        } = this.props.communityTreeStore

        const isCommunity = activeModel.startsWith('community')
        const isBuilding = activeModel.startsWith('building')
        const isUnit = activeModel.startsWith('unit')
        const isRoom = activeModel.startsWith('room')

        let buildingIds =
            (!!allBuilding.length || []) &&
            allBuilding.reduce((result, b) => {
                result.push(`building_${b.id}`)
                return result
            }, [])

        // 递归生成菜单
        const getAllUnit = (allUnits, id) => {
            const sss = []
            allUnits.length &&
            allUnits.forEach(item => {
                if (item.buildingId === id) {
                    sss.push(
                        <TreeNode title={this.state.aliasFlag ? item.alias : item.name} key={`unit_${item.id}`}/>,
                    )
                }
            })
            return sss
        }

        return (
            !isCommunity && (
                <div className={styles.tree}>
                    {!!allCommunity.length && (
                        <Select
                            defaultValue={`${defaultCommunityId}`}
                            className={styles.select}
                            onChange={e => this.props.onChange(e, activeModel)}
                            disabled={isSingle}
                        >
                            {allCommunity.map(item => (
                                <Option key={`${item.id}`} value={`${item.id}`}>
                                    {item.name}
                                </Option>
                            ))}
                        </Select>
                    )}
                    {!isBuilding &&
                    !!allBuilding.length && (
                        <div style={{ paddingLeft: 10 }}>
                            别名：<Switch
                            defaultChecked={this.state.aliasFlag}
                            onChange={this.changeNameDisplay.bind(this)}
                        />
                        </div>
                    )}
                    {!isBuilding && (
                        <Tree
                            key={`${isRoom}_${defaultCommunityId}${allBuilding.length}${allUnit.length}`}
                            checkable
                            expandedKeys={buildingIds}
                            onSelect={(selectedKeys, info) => this.props.onSelect(selectedKeys, info, activeModel)}
                            onCheck={(checkedKeys, info) => this.props.onCheck(checkedKeys, info, activeModel)}
                        >
                            {allBuilding.map(item => {
                                return (
                                    <TreeNode
                                        title={this.state.aliasFlag ? item.alias : item.name}
                                        key={`building_${item.id}`}
                                    >
                                        {!isUnit && getAllUnit(allUnit, item.id)}
                                    </TreeNode>
                                )
                            })}
                        </Tree>
                    )}
                </div>
            )
        )
    }
}

function mapStateToProps({ appStore, communityTreeStore }) {
    return {
        appStore,
        communityTreeStore,
    }
}

function mapDispatchToProps(dispatch) {
    return {
        onChange(id, activeModel) {
            dispatch({
                type: 'communityTreeStore/updateState',
                defaultCommunityId: id,
            })
            dispatch({
                type: 'communityTreeStore/queryAll',
            })
            dispatch({
                type: `${activeModel}/queryPage`,
            })
        },

        onSelect(selectedKeys, info, activeModel) {
            console.log('selected', selectedKeys, info)
        },

        onCheck(checkedKeys, info, activeModel) {
            let buildings = []
            let units = []
            checkedKeys.forEach(e => {
                if (/^building/.test(e)) {
                    buildings.push(e.split('_')[1])
                } else if (/^unit/.test(e)) {
                    units.push(e.split('_')[1])
                }
            })
            dispatch({
                type: 'communityTreeStore/updateState',
                defaultBuildingIds: buildings,
                defaultUnitIds: units,
            })
            dispatch({
                type: `${activeModel}/queryPage`,
                defaultBuildingIds: buildings,
                defaultUnitIds: units,
            })
            console.log('onCheck', buildings, units)
        },
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(Demo)
