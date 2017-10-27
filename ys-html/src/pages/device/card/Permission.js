import { connect } from 'dva'
import { Table, Checkbox } from 'antd'
import _ from 'lodash'

import { getColumns } from 'TableUtils'

class PermissionComponet extends React.Component {
    static propTypes = {
        loading: PropTypes.bool,
        permissions: PropTypes.array,
        communityTreeStore: PropTypes.object.isRequired,
        updateState: PropTypes.func.isRequired,
    }

    state = {
        key: new Date(),
        expandedRowKeys: _.reduce(this.props.communityTreeStore.allBuilding, (result, e) => {
            result.push(e.communityId * 1000 + e.id * 100)
            return result
        }, []),
        defaultCommunity: this.props.communityTreeStore.defaultCommunity,
        permissions:
            this.props.permissions && typeof this.props.permissions === 'string'
                ? this.props.permissions.split(',')
                : this.props.permissions || [],
        allBuilding: this.props.communityTreeStore.allBuilding,
        allUnit: this.props.communityTreeStore.allUnit,
        treeData: _.reduce(this.props.communityTreeStore.allBuilding, function (result, x) {
            let building = _.cloneDeep(x)
            result[0].push(building)

            if (building.children === undefined) {
                building.children = []
            }
            result[1].forEach(function (unit) {
                if (unit.buildingId === building.id) {
                    building.children.push(_.cloneDeep(unit))
                }
            })
            return result
        }, [[], this.props.communityTreeStore.allUnit])[0],
    }

    onExpandedRowsChange(expandedRowKeys) {
        this.setState({ expandedRowKeys })
    }

    render() {
        // ES6 类中函数必须手动绑定
        this.onExpandedRowsChange = this.onExpandedRowsChange.bind(this)

        let { treeData, allBuilding, allUnit, permissions, defaultCommunity, expandedRowKeys } = this.state
        const { loading, updateState } = this.props

        const tableColumns = getColumns(resourceFields)
            .values()

        const rowSelection = {
            onSelect: (record, selected) => {
                if (selected) {
                    if (record.buildingId)
                        permissions.push(record.id)
                    else {
                        const unitIds = _.map(record.children, 'id')
                        permissions = _.union(permissions, unitIds)
                    }
                } else {
                    if (record.buildingId)
                        _.remove(permissions, function (x) {
                            return x === record.id
                        })
                    else {
                        const unitIds = _.map(record.children, 'id')
                        permissions = _.difference(permissions, unitIds)
                    }
                }
                this.setState({ permissions })
                updateState(permissions)
            },
            onSelectAll: selected => {
                if (selected) {
                    permissions = _.intersection(permissions, _.map(allBuilding, 'id'))
                    permissions = _.intersection(permissions, _.map(allUnit, 'id'))
                } else {
                    permissions = []
                }
                this.setState({ permissions })
                updateState(permissions)
            },
            selectedRowKeys: _.reduce(treeData, function (result, td) {
                if (!!td.children && !!td.children.length) {
                    const unitIds = _.map(td.children, 'id')
                    const unitLen = unitIds.length
                    const intersection = _.intersection(unitIds, permissions)

                    function duplicate(unitId) {
                        return unitId * 10 + td.communityId * 1000 + td.id * 100
                    }

                    const flatMap = _.flatMap(intersection, duplicate)
                    result.push(...flatMap)
                    if (unitLen - intersection.length <= 1) {
                        result.push(td.communityId * 1000 + td.id * 100)
                    }
                }
                return result
            }, []),
            /*getCheckboxProps: record => ({
                defaultChecked: _.reduce(treeData, function (result, td) {
                    if(record.buildingId) {
                        return _.includes(permissions, record.id)
                    } else {
                        const len = td.children.length
                        if(!!len){
                            const ids = _.map(td.children, 'id')
                            return !_.difference(ids, permissions).length;
                        }
                    }
                }, false),
            }),*/
        }

        const tableProp = {
            key: this.state.key,
            rowKey: record => {
                if (record.communityId) {
                    if (record.buildingId) {
                        if (record.unitId) {
                            return record.communityId * 1000 + record.buildingId * 100 + record.unitId * 10 + record.id
                        }
                        return record.communityId * 1000 + record.buildingId * 100 + record.id * 10
                    }
                    return record.communityId * 1000 + record.id * 100
                }
                return record.id * 1000
            },
            columns: tableColumns,
            dataSource: treeData,
            bordered: true,
            pagination: false,
            loading,
            defaultExpandAllRows: false,
            rowSelection,
            expandedRowKeys,
            onExpandedRowsChange: this.onExpandedRowsChange,
        }

        return <div>
            <div style={{ textAlign: 'center', paddingBottom: 10 }}>
                <span style={{ fontSize: 20 }}>
                    {defaultCommunity.name}
                </span>
            </div>
            <Table {...tableProp} />
        </div>
    }
}

/**
 * 订阅 model
 */
function mapStateToProps({ loading, communityTreeStore }) {
    return {
        loading: loading.models.communityTreeStore,
        communityTreeStore,
    }
}

function mapDispatchToProps(dispatch) {
    return {
        updateState(permissions) {
            dispatch({
                type: 'cardStore/updateState',
                permissions,
            })
        },
    }
}

const resourceFields = [
    {
        key: 'name',
        name: '楼栋',
        width: '70%',
    },
]

export default connect(mapStateToProps, mapDispatchToProps)(PermissionComponet)
