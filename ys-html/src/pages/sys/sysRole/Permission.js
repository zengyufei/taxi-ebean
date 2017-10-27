import { connect } from 'dva'
import { Table, Checkbox } from 'antd'
import _ from 'lodash'

import { getColumns } from 'TableUtils'

const CheckboxGroup = Checkbox.Group

class PermissionComponet extends React.Component {
    static propTypes = {
        loading: PropTypes.bool,
        resourceList: PropTypes.array,
        sysStore: PropTypes.object.isRequired,
        updateState: PropTypes.func.isRequired,
    }

    state = {
        key: new Date(),
        expandedRowKeys: [],
        resources: this.props.sysStore.resources,
        ownList:
            this.props.resourceList && typeof this.props.resourceList === 'string'
                ? this.props.resourceList.split(',')
                : this.props.resourceList || [],
        resourceMapByparentId: this.props.sysStore.resourceMapByparentId,
    }

    onExpandedRowsChange(expandedRowKeys) {
        this.setState({ expandedRowKeys })
    }

    onChangePower(checkedThreeValues, secondSingleMenu, xx) {
        const { updateState } = this.props
        let { ownList, resourceMapByparentId } = this.state

        const threeArray = _.map(_.map(resourceMapByparentId[secondSingleMenu.id], 'id'), e => +e)
        if (+secondSingleMenu.level === 1) {
            if (checkedThreeValues.length) {
                checkedThreeValues = _.transform(
                    checkedThreeValues,
                    (result, id) => {
                        result.push(`${id}`)
                    },
                    [],
                )
                ownList = _.concat(ownList, checkedThreeValues)
            } else {
                _.remove(ownList, e => +e === +secondSingleMenu.id)
            }
        } else {
            _.remove(ownList, e => _.includes(threeArray, +e) || _.includes(threeArray, `${e}`))
            checkedThreeValues = _.transform(
                checkedThreeValues,
                (result, id) => {
                    result.push(`${id}`)
                },
                [],
            )
            ownList = _.concat(ownList, checkedThreeValues)

            if (checkedThreeValues.length === secondSingleMenu.childrenResNo.length) {
                _.remove(ownList, e => +e === +secondSingleMenu.id)
                ownList = _.concat(ownList, `${secondSingleMenu.id}`)
            } else if (checkedThreeValues.length === 0) {
                _.remove(ownList, e => +e === +secondSingleMenu.id)
            }
        }
        this.setState({ ownList, key: `${secondSingleMenu.id}${ownList.length}` })
        updateState(ownList)
    }

    render() {
        let { resources, ownList, resourceMapByparentId } = this.state
        const { loading, sysStore, updateState } = this.props
        const { resourceSecondTree = [] } = sysStore

        const operatorColumn = [
            {
                key: 'operator',
                name: '操作',
                // 扩展字段的render支持自定义渲染
                render: (text, record) => {
                    let options
                    let values = []
                    if (record.level === 1) {
                        options = [{ label: '查看', value: record.id }]
                        record.childrenResNo = [record.id]
                        if (_.includes(ownList, +record.id) || _.includes(ownList, `${record.id}`)) {
                            values = record.childrenResNo
                        }
                    } else if (record.level === 2) {
                        options = _.transform(
                            resourceMapByparentId[record.id],
                            (result, resource) => {
                                result.push({
                                    label: resource.name,
                                    value: resource.id,
                                })
                            },
                            [],
                        )
                        record.childrenResNo = _.transform(
                            resourceMapByparentId[record.id],
                            (result, resource) => {
                                result.push(resource.id)
                            },
                            [],
                        )
                        const idsArray = _.map(_.map(resourceMapByparentId[record.id], 'id'), e => +e)
                        if (_.intersection(ownList, idsArray).length === idsArray.length) {
                            values = record.childrenResNo
                            values.push(record.id)
                        } else {
                            values = _.transform(
                                resourceMapByparentId[record.id],
                                (result, resource) => {
                                    if (_.includes(ownList, +resource.id) || _.includes(ownList, `${resource.id}`)) {
                                        result.push(resource.id)
                                    }
                                },
                                [],
                            )
                            if (!values.length) {
                                _.remove(ownList, e => e === +record.id)
                            }
                        }
                    }
                    return (
                        <CheckboxGroup
                            options={options}
                            value={values}
                            onChange={checkedValues => this.onChangePower(checkedValues, record)}
                        />
                    )
                },
            },
        ]
        const tableColumns = getColumns(resourceFields)
            .enhance(operatorColumn)
            .values()

        const rowSelection = {
            onSelect: (record, selected) => {
                if (selected) {
                    if (record.level === 1) {
                        _.remove(ownList, e => e === +record.id)
                        ownList = _.concat(ownList, +record.id)
                    } else if (record.level === 2) {
                        const deleteArray = _.transform(
                            resourceMapByparentId[record.id],
                            (result, resource) => {
                                result.push(+resource.id)
                            },
                            [],
                        )
                        _.remove(ownList, e => _.includes(deleteArray, +e) || _.includes(deleteArray, `${e}`))
                        ownList = _.concat(
                            ownList,
                            +record.id,
                            _.transform(
                                resourceMapByparentId[record.id],
                                (result, resource) => {
                                    result.push(+resource.id)
                                },
                                [],
                            ),
                        )
                    }
                } else if (record.level === 1) {
                    _.remove(ownList, e => e === +record.id)
                } else if (record.level === 2) {
                    const deleteArray = _.transform(
                        resourceMapByparentId[record.id],
                        (result, resource) => {
                            result.push(+resource.id)
                        },
                        [],
                    )
                    _.remove(ownList, e => _.includes(deleteArray, +e) || _.includes(deleteArray, `${e}`))
                }
                this.setState({ ownList })
                updateState(ownList)
            },
            onSelectAll: selected => {
                if (selected) {
                    const allId = _.map(resources, 'id')
                    this.setState({ ownList: allId })
                    updateState(allId)
                } else {
                    this.setState({ ownList: [] })
                    updateState([])
                }
            },
            getCheckboxProps: record => ({
                defaultChecked: _.includes(ownList, +record.id) || _.includes(ownList, `${record.id}`),
            }),
        }

        const tableProp = {
            key: this.state.key,
            rowKey: record => record.id,
            columns: tableColumns,
            dataSource: resourceSecondTree,
            bordered: true,
            pagination: false,
            loading,
            defaultExpandAllRows: false,
            rowSelection,
            expandedRowKeys: this.state.expandedRowKeys,
            onExpandedRowsChange: this.onExpandedRowsChange.bind(this),
        }

        return <Table scroll={{ x: 1200 }} {...tableProp} />
    }
}

/**
 * 订阅 model
 */
function mapStateToProps({ loading, sysStore }) {
    return {
        loading: loading.models.sysStore,
        sysStore,
    }
}

function mapDispatchToProps(dispatch) {
    return {
        updateState(resourceList) {
            dispatch({
                type: 'sysRoleStore/updatePermission',
                resourceList,
            })
        },
    }
}

const resourceFields = [
    {
        key: 'name',
        name: '资源名称',
        width: '20%',
    },
    {
        key: 'permission',
        name: '权限标识',
        width: '20%',
    },
]

export default connect(mapStateToProps, mapDispatchToProps)(PermissionComponet)
